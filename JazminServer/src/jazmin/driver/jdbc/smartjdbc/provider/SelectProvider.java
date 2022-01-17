package jazmin.driver.jdbc.smartjdbc.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;


import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.Query.OrderBy;
import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.Types;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.cache.EntityFieldInfo;
import jazmin.driver.jdbc.smartjdbc.cache.EntityInfo;
import jazmin.driver.jdbc.smartjdbc.cache.QueryFieldInfo;
import jazmin.driver.jdbc.smartjdbc.cache.QueryInfo;
import jazmin.driver.jdbc.smartjdbc.enums.AggregationFunction;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.JoinType;
import jazmin.driver.jdbc.smartjdbc.enums.OrderByType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Aggregation;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Join;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Joins;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SelectSql;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere.WhereStatment;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where;
import jazmin.driver.jdbc.smartjdbc.provider.where.WhereSqlBuilder;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class SelectProvider extends SqlProvider{
	//
	private static Logger logger=LoggerFactory.getLogger(SelectProvider.class);
	//
	protected EntityInfo entity;
	protected QueryInfo queryInfo;
	protected Class<?> entityClass;
	protected Query<?> query;
	protected boolean isSelectCount;
	protected boolean needPaging;
	protected boolean needOrderBy;
	protected List<EntityFieldInfo> selectFields;
	protected Set<String> includeFields;
	protected Set<String> excludeFields;//userName not user_name
	protected QueryWhere qw;
	protected List<EntityFieldInfo> groupBys;
	protected List<Aggregation> aggregationList;
	protected Joins joins;
	//
	public SelectProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
		this.selectFields=new ArrayList<>();
		this.includeFields=new LinkedHashSet<>();
		this.excludeFields=new LinkedHashSet<>();
		this.qw=QueryWhere.create();
		this.groupBys=new ArrayList<>();
		this.aggregationList=new ArrayList<>();
		this.needOrderBy=true;
	}
	//
	public SelectProvider entityClass(Class<?> entityClass) {
		this.entityClass=entityClass;
		return this;
	}
	//
	public SelectProvider selectCount() {
		this.isSelectCount=true;
		return this;
	}
	//
	public SelectProvider needPaging(boolean needPaging) {
		this.needPaging=needPaging;
		return this;
	}
	//
	public SelectProvider needOrderBy(boolean needOrderBy) {
		this.needOrderBy=needOrderBy;
		return this;
	}
	//
	public SelectProvider query(Query<?> query) {
		this.query=query;
		return this;
	}
	//
	public SelectProvider query(QueryWhere qw) {
		this.qw=qw;
		if(qw.getLimitEnd()>0) {
			needPaging(true);
		}
		return this;
	}
	//
	public SelectProvider select(String field) {
		return select(null, field);
	}
	//
	public SelectProvider select(String tableAlias,String name) {
		return select(tableAlias,name,null);
	}
	//
	public SelectProvider select(String tableAlias,String name, String asAlias) {
		return select(tableAlias, name,asAlias, false, null);
	}
	//
	public SelectProvider select(String tableAlias,String name,String asName,boolean distinct,String statFunction) {
		selectFields.add(createSelectField(tableAlias, name, 
				asName, distinct, statFunction));
		return this;
	}
	//
	public SelectProvider includeFields(Set<String> fields){
		if(fields!=null) {
			for (String field : fields) {
				includeFields.add(field);
			}
		}
		return this;
	}
	//
	public SelectProvider excludeFields(String ... fields){
		if(fields!=null) {
			for (String field : fields) {
				excludeFields.add(field);
			}
		}
		return this;
	}
	//
	protected EntityFieldInfo createSelectField(String tableAlias,String name,String asName,boolean distinct,String statFunction) {
		EntityFieldInfo sf=new EntityFieldInfo();
		sf.tableAlias=tableAlias;
		sf.name=name;
		sf.asName=asName;
		sf.distinct=distinct;
		sf.statFunction=statFunction;
		return sf;
	}
	//
	public SelectProvider where(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.EQ, value);
	}
	//
	public SelectProvider where(String key,Object value){
		return this.where(MAIN_TABLE_ALIAS,key, SqlOperator.EQ, value);
	}
	//
	public SelectProvider where(String alias,String key,SqlOperator op,Object value){
		qw.where(alias, key, op, value);
		return this;
	}
	//
	public SelectProvider whereSql(String sql,Map<String,Object> values){
		qw.whereSql(sql, values);
		return this;
	}
	//
	public SelectProvider groupBy(String field) {
		groupBy(MAIN_TABLE_ALIAS, field);
		return this;
	}
	//
	public SelectProvider groupBy(String tableAlias,String field) {
		groupBys.add(createGroupByField(tableAlias, field));
		return this;
	}
	//
	public SelectProvider groupBy(List<EntityFieldInfo> groupBys) {
		this.groupBys=groupBys;
		return this;
	}
	//
	public SelectProvider aggregationList(List<Aggregation> aggregationList) {
		this.aggregationList=aggregationList;
		return this;
	}
	//
	//
	protected EntityFieldInfo createGroupByField(String tableAlias,String name) {
		EntityFieldInfo groupByField=new EntityFieldInfo();
		groupByField.tableAlias=tableAlias;
		groupByField.name=name;
		return groupByField;
	}
	//
	public SelectProvider orderBy(String orderBy){
		qw.orderBy(orderBy);
		return this;
	}
	//
	public SelectProvider limit(int start,int limit){
		qw.limit(start, limit);
		return this;
	}
	//
	public SelectProvider limit(int end){
		qw.limit(end);
		return this;
	}
	//
	public SelectProvider forUpdate(){
		qw.isForUpdate();
		return this;
	}
	//
	public SelectProvider of(String of){
		qw.setOf(of);
		return this;
	}
	//
	//聚合函数
	protected void addAggregation(StringBuilder sql) {
		this.needPaging=false;
		if(groupBys.size()==0) {//如果没有分组则不能排序
			this.needOrderBy=false;
		}
		//
		for (EntityFieldInfo field : groupBys) {
			sql.append(getSelectFieldSql(field));
			sql.append(",");
		}
		//
		for (Aggregation aggregation : aggregationList) {
			sql.append("");
			String func=aggregation.func.trim();
			if(func.equalsIgnoreCase(AggregationFunction.COUNT.name())) {
				sql.append(" count(1) ");
			}
			if(func.equalsIgnoreCase(AggregationFunction.AVG.name())) {
				sql.append(" avg("+getExpr(aggregation)+") ");
			}
			if(func.equalsIgnoreCase(AggregationFunction.MAX.name())) {
				sql.append(" max("+getExpr(aggregation)+") ");
			}
			if(func.equalsIgnoreCase(AggregationFunction.MIN.name())) {
				sql.append(" min("+getExpr(aggregation)+") ");
			}
			if(func.equalsIgnoreCase(AggregationFunction.SUM.name())) {
				sql.append(" sum("+getExpr(aggregation)+") ");
			}
			if(aggregation.asName!=null) {
				sql.append(" as "+addIdentifier(aggregation.asName)).append(" ");
			}
			sql.append(",");
		}
		sql.deleteCharAt(sql.length()-1);
	}
	//
	private String getExpr(Aggregation aggregation) {
		StringBuilder sql=new StringBuilder();
		if(aggregation.distinct) {
			sql.append("distinct( ");
		}
		sql.append(aggregation.expr).append(" ");
		if(aggregation.distinct) {
			sql.append(") ");
		}
		return sql.toString();
	}
	//
	protected void getQueryFields(List<QueryFieldInfo> fieldList,Object obj,QueryInfo queryInfo){
		for (QueryFieldInfo fieldInfo : queryInfo.fieldList) {
			try {
				Field field=fieldInfo.field;
				if(!field.isAccessible()) {
					field.setAccessible(true);
				}
				Object reallyValue = field.get(obj);
				if (reallyValue == null) {
					continue;
				}
				fieldList.add(fieldInfo);
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new IllegalArgumentException(e);
			}
		}
		for (QueryInfo e : queryInfo.children) {
			try {
				if(!e.field.isAccessible()) {
					e.field.setAccessible(true);
				}
				Object reallyValue = e.field.get(obj);
				if (reallyValue == null) {
					continue;
				}
				getQueryFields(fieldList,reallyValue,e);
			} catch (Exception ex) {
				logger.error(ex.getMessage(),ex);
				throw new IllegalArgumentException(ex);
			}	
		}
	}
	//
	protected boolean isValidJoin(jazmin.driver.jdbc.smartjdbc.annotations.Join join) {
		if(join==null||join.type()==null) {
			return false;
		}
		if(join.table2().equals(void.class)) {
			throw new SmartJdbcException("Join table2 cannot be null");
		}
		if(join.table1Fields().length==0) {
			throw new SmartJdbcException("Join table1Fields cannot be null");
		}
		if(join.table2Fields().length==0) {
			throw new SmartJdbcException("Join table2Fields cannot be null");
		}
		if(join.table1Fields().length!=join.table2Fields().length) {
			throw new SmartJdbcException("Join table1Fields length not equal table2Fields length");
		}
		return true;
	}
	//
	protected void createJoins() {
		if(entity!=null) {
			joins=entity.joins;
		}
		if(joins==null) {
			joins=new Joins();
		}
		if(query==null) {
			return;
		}
		List<QueryFieldInfo> fieldInfos=new ArrayList<>();
		getQueryFields(fieldInfos,query,queryInfo);
		for (QueryFieldInfo fieldInfo : fieldInfos) {
			jazmin.driver.jdbc.smartjdbc.annotations.Join innerJoin=fieldInfo.join;
			jazmin.driver.jdbc.smartjdbc.annotations.Joins innerJoins=fieldInfo.joins;
			QueryField queryField=fieldInfo.queryField;
			String foreignKeyFields="";
			if(queryField!=null) {
				foreignKeyFields=queryField.foreignKeyFields();
			}
			if(innerJoin==null&&innerJoins==null&&StringUtil.isEmpty(foreignKeyFields)) {
				continue;
			}
			List<jazmin.driver.jdbc.smartjdbc.annotations.Join> innerJoinsList=new ArrayList<>();
			if(isValidJoin(innerJoin)) {
				innerJoinsList.add(innerJoin);
			}
			if(innerJoins!=null&&innerJoins.joins()!=null) {
				for (jazmin.driver.jdbc.smartjdbc.annotations.Join join : innerJoins.joins()) {
					if(isValidJoin(join)) {
						innerJoinsList.add(join);
					}
				}
			}
			if(innerJoinsList.size()>0) {//use annotation
				Join join=null;
				Class<?> table1=entityClass;
				String table1Alias=MAIN_TABLE_ALIAS;
				for (jazmin.driver.jdbc.smartjdbc.annotations.Join j: innerJoinsList) {
					join=createJoin(j.type(),table1,j.table2(),table1Alias,j.table2Alias(),
									j.table1Fields(),j.table2Fields());
					table1=join.table2;
					table1Alias=join.table2Alias;
				}
				fieldInfo.tableAlias=table1Alias;
			}else if(!StringUtil.isEmpty(foreignKeyFields)) {
				String[] foreignKeyIds=foreignKeyFields.split(",");
				Class<?> table1=entityClass;
				String table1Alias=MAIN_TABLE_ALIAS;
				Join join=null;
				for (String id : foreignKeyIds) {
					Field foreignKeyField=null;
					try {
						foreignKeyField=ClassUtils.getExistedField(table1,id);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
						throw new IllegalArgumentException(e.getMessage()+"/"+table1.getSimpleName());
					}
					ForeignKey foreignKey=foreignKeyField.getAnnotation(ForeignKey.class);
					if(foreignKey==null) {
						throw new IllegalArgumentException("@ForeignKey not found in "+
									entityClass.getSimpleName()+"."+foreignKeyField.getName());
					}
					Class<?> table2=foreignKey.entityClass();
					String table2Field=foreignKey.field();
					join=createJoin(JoinType.LEFT_JOIN,
							table1, table2,
							table1Alias,null,
							new String[] {id},new String[] {table2Field});
					table1=table2;
					table1Alias=join.table2Alias;
				}
				fieldInfo.tableAlias=table1Alias;
			}
		}
	}
	//
	/**
	 * 
	 * @param query
	 */
	protected void addWheres(Query<?> query) {
		if(query==null) {
			return;
		}
		Map<String,Object> paraMap=new HashMap<>();
		if(!query.getParams().isEmpty()) {
			paraMap.putAll(query.getParams());
		}
		createParaMap(paraMap, query, queryInfo);
		addWheres(qw.getWhere(), paraMap, query, queryInfo);
	}
	//
	private void createParaMap(Map<String,Object> paraMap,Object obj,QueryInfo queryInfo) {
		try {
			List<QueryFieldInfo> fields=queryInfo.fieldList;
			for (QueryFieldInfo info : fields) {
				Field field=info.field;
				try {
					if(!field.isAccessible()) {
						field.setAccessible(true);
					}
					Object value=field.get(obj);
					paraMap.put(info.fieldName, value);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new SmartJdbcException(e.getMessage());
				}
			}
			for (QueryInfo child : queryInfo.children) {
				if(!child.field.isAccessible()) {
					child.field.setAccessible(true);
				}
				Object childObj=child.field.get(obj);
				if(childObj==null) {
					continue;
				}
				createParaMap(paraMap, childObj, child);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	//
	protected void addWheres(Where w,Map<String,Object> paraMap,Object obj,QueryInfo queryInfo) {
		try {
			for (QueryFieldInfo info : queryInfo.fieldList) {
				Field field=info.field;
				if(!field.isAccessible()) {
					field.setAccessible(true);
				}
				Object value=field.get(obj);
				if(value==null) {
					continue;
				}
				QueryField queryField=field.getAnnotation(QueryField.class);
				String alias = info.tableAlias;
				if(alias==null) {
					if(!StringUtil.isEmpty(queryField.alias())) {
						alias=queryField.alias();
					}else {
						alias=SqlProvider.MAIN_TABLE_ALIAS;
					}
				}
				//
				if(queryField!=null&&!StringUtil.isEmpty(queryField.whereSql())) {//whereSql check first
					String whereSql=queryField.whereSql();
					w.whereSql(whereSql,paraMap);
				}else {
					String dbFieldName=convertFieldName(field.getName());
					if(queryField!=null&&(!StringUtil.isEmpty(queryField.field()))) {
						dbFieldName=convertFieldName(queryField.field());
					}
					SqlOperator operator=SqlOperator.EQ;//default eq
					if(queryField!=null) {
						operator=queryField.operator();
					}
					w.where(alias,dbFieldName,operator,value);
				}
			}
			//
			for (QueryInfo child : queryInfo.children) {
				if(!child.field.isAccessible()) {
					child.field.setAccessible(true);
				}
				Object childObj=child.field.get(obj);
				if(childObj==null) {
					continue;
				}
				Where wc=new Where();
				if(child.conditionType.equals(ConditionType.AND)) {
					w.and(wc);
				}else {
					w.or(wc);
				}
				addWheres(wc, paraMap, childObj, child);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	//
	protected int getSortFieldOrder(String[] sortFields,String fieldName) {
		if(sortFields==null||sortFields.length==0) {
			return 0;
		}
		int order=0;
		for (String field : sortFields) {
			order++;
			if(field.equals(fieldName)) {
				return order;
			}
		}
		return 0;
	}
	//
	protected void addOrderBy(Query<?> query) {
		List<String> orderByList=addOrderByList(query);
		for (String e : orderByList) {
			orderBy(e);
		}
	}
	//
	public List<String> addOrderByList(Query<?> query) {
		List<String> orderByList=new ArrayList<>();
		if(query==null) {
			return orderByList;
		}
		if(query.getOrderByList()==null||query.getOrderByList().isEmpty()) {
			if(Query.defaultOrderBy!=null) {
				return Arrays.asList(Query.defaultOrderBy);
			}else {
				return orderByList;
			}
		}
		for (OrderBy order : query.getOrderByList()) {
			String fieldName=order.field;
			if(fieldName==null) {
				continue;
			}
			StringBuilder orderby=new StringBuilder();
			if(!StringUtil.isEmpty(order.tableAlias)) {
				orderby.append(order.tableAlias).append(".");
			}
			String orderBy=order.type;
			String dbField=convertFieldName(fieldName);
			orderby.append(dbField);
			if(orderBy.equalsIgnoreCase(OrderByType.ASC.name())) {
				orderby.append(" asc ");
			}else if(orderBy.equalsIgnoreCase(OrderByType.DESC.name())) {
				orderby.append(" desc ");
			}
			orderByList.add(orderby.toString());
		}
		return orderByList;
	}
	//
	protected void addPaging() {
		if(query==null) {
			return;
		}
		this.limit(query.getStartPageIndex(),query.getPageSize());
	}
	//
	protected void buildSelectFields(){
		entity=EntityInfo.create(entityClass);
		for (EntityFieldInfo fieldInfo : entity.fieldList) {
			Field field=fieldInfo.field;
			if (Modifier.isStatic(field.getModifiers())|| Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			if(includeFields!=null&&!includeFields.isEmpty()&&(!includeFields.contains(field.getName()))){
				continue;
			}
			if(excludeFields.contains(field.getName())){
				continue;
			}	
			EntityField entityField = fieldInfo.entityField;
			if(entityField!=null&&entityField.ignoreWhenSelect()) {
				continue;
			}
			if(fieldInfo.tableAlias==null) {
				fieldInfo.tableAlias=SqlProvider.MAIN_TABLE_ALIAS;//默认主表
			}
			if(entityField==null) {
				selectFields.add(fieldInfo);
			}else if(Types.WRAP_TYPES.contains(field.getType())){//基本字段类型
				selectFields.add(fieldInfo);
			}else if(field.getGenericType() instanceof ParameterizedType){//List Set Map
				selectFields.add(fieldInfo);
			}else {//查询对象字段列表
				List<Field> subClassFields=ClassUtils.getPersistentFields((Class<?>)field.getGenericType());
				for (Field subClassField : subClassFields) {
					selectFields.add(createSelectField(fieldInfo.tableAlias, subClassField.getName(), 
							field.getName()+"$"+subClassField.getName(), false, null));
				}
			}
		}
	}
	//
	protected Join createJoin(JoinType type, 
			Class<?> table1,Class<?> table2,String table1Alias,String table2Alias,
			String[] table1Fields,String[] table2Fields) {
		return joins.addJoin(type, table1, table2,
				table1Alias, table2Alias, table1Fields, table2Fields);
	}
	//
	protected void addSelectFields(StringBuilder sql) {
		for (EntityFieldInfo field : selectFields) {
			sql.append(getSelectFieldSql(field));
			sql.append(",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" ");
	}
	//
	protected String getSelectFieldSql(EntityFieldInfo field) {
		StringBuilder sql=new StringBuilder();
		if(field.distinct) {
			sql.append(" distinct ");
		}
		if(StringUtil.isEmpty(field.statFunction)) {
			sql.append(field.tableAlias).append(".");
			sql.append(addIdentifier(convertFieldName(field.name))).append("");
			if(smartDataSource.isJsonb2Text()&&
					field.entityField!=null&&
					field.entityField.columnType().equals(ColumnType.JSONB)
					) {
				sql.append("::text"); 
			}
		}else {
			sql.append(field.statFunction);
		}
		if(field.asName!=null) {
			sql.append(" as ").append(addIdentifier(convertFieldName(field.asName)));
		}
		return sql.toString();
	}
	//
	protected String getFromSql() {
		StringBuilder sql=new StringBuilder();
		sql.append("\nfrom ").append(getTableName(entityClass)).append(" ").
				append(MAIN_TABLE_ALIAS).append(" ");
		//join
		createJoins();
		if(joins!=null) {
			for (Join join : joins.getJoinList()) {
				if(join.joinType.equals(JoinType.INNER_JOIN)) {
					sql.append("\ninner join  ");
				}
				if(join.joinType.equals(JoinType.LEFT_JOIN)) {
					sql.append("\nleft join  ");
				}
				if(join.joinType.equals(JoinType.RIGHT_JOIN)) {
					sql.append("\nright join  ");
				}
				addJoinSql(sql, join);
			}
		}
		return sql.toString();
	}
	//
	protected void addJoinSql(StringBuilder sql,Join join) {
		sql.append(getTableName(join.table2)).append(" ").append(join.table2Alias);
		sql.append(" on ");
		for(int i=0;i<join.table1Fields.length;i++) {
			sql.append(join.table1Alias).append("."+addIdentifier(convertFieldName(join.table1Fields[i]))+"=").
			append(join.table2Alias).append(".").append(addIdentifier(convertFieldName(join.table2Fields[i])));
			if(i<join.table1Fields.length-1) {
				sql.append(" and ");
			}
		}
		sql.append(" ");
	}
	//
	protected WhereStatment getWhereSql() {
		addWheres(query);
		return new WhereSqlBuilder(getDatabaseType(),qw).build();
	}
	//
	protected String getGroupBySql() {
		StringBuilder sql=new StringBuilder();
		if(groupBys.size()>0) {
			sql.append("\ngroup by ");
			for (EntityFieldInfo field : groupBys) {
				if(!StringUtil.isEmpty(field.tableAlias)) {
					sql.append(field.tableAlias).append(".");
				}
				sql.append(convertFieldName(field.name)).append(",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" ");
		}
		return sql.toString();
	}
	//
	protected String getOrderBySql() {
		if(isSelectCount) {
			return "";
		}
		if(!needOrderBy) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		addOrderBy(query);
		if (qw.getOrderBys().size()>0) {
			sql.append("\norder by ");
			for (String orderBy : qw.getOrderBys()) {
				sql.append(orderBy).append(",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" ");
		}
		return sql.toString();
	}
	//
	protected String getLimitSql() {
		if(isSelectCount) {
			return "";
		}
		if(!needPaging) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		addPaging();	
		if(qw.getLimitEnd()!=-1) {
			sql.append("\nlimit ").append(qw.getLimitStart()).append(",").append(qw.getLimitEnd()).append(" ");
		}
		return sql.toString();
	}
	//
	protected String getForUpdateSql() {
		if(qw.isForUpdate()) {
			return "\nfor update ";
		}
		return "";
	}
	//
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	public List<EntityFieldInfo> getSelectFields() {
		return selectFields;
	}
	
	public void setSelectFields(List<EntityFieldInfo> selectFields) {
		this.selectFields = selectFields;
	}
	
	//
	protected SqlBean build(StringBuilder selectSql) {
		if(query!=null) {
			queryInfo=QueryInfo.create(query.getClass());
		}
		SelectSql bean=new SelectSql();
		bean.selectSql=selectSql.toString();
		bean.fromSql=getFromSql();
		WhereStatment ws=getWhereSql();
		bean.whereSql=ws.sql;
		bean.groupBySql=getGroupBySql();
		bean.orderBySql=getOrderBySql();
		bean.limitSql=getLimitSql();
		bean.forUpdateSql=getForUpdateSql();
		bean.sql=bean.toSql();
		bean.parameters=ws.values;
		return bean;
	}
	//
	/**
	 * 
	 * @return
	 */
	protected SqlBean queryCount() {
		StringBuilder sql = new StringBuilder();
		sql.append("\nselect count(1) ");
		this.needPaging=false;
		return build(sql);
	}
	
	/**
	 * 
	 * @return
	 */
	protected SqlBean query() {
		StringBuilder sql = new StringBuilder();
		buildSelectFields();
		sql.append("\nselect ");
		if(aggregationList.size()==0&&groupBys.size()==0) {
			addSelectFields(sql);
		}else {
			addAggregation(sql);
		}
		return build(sql);
	}
		
	@Override
	public SqlBean build() {
		if(isSelectCount) {
			return queryCount();
		}else {
			return query();
		}
	}
}

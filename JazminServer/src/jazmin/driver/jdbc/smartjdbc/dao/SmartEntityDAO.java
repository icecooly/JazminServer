package jazmin.driver.jdbc.smartjdbc.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.hutool.core.util.ClassUtil;
import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.ResultSetHandler;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityDelete;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityInsert;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 */
public class SmartEntityDAO<T> extends BaseEntityDAO{
	//
	protected String tableName;
	protected Class<T> entityClass;
	//
	protected Class<T> getTypeClass(){
		return (Class<T>)ClassUtil.getTypeArgument(this.getClass());
	}
	//
	public String getTableName() {
		if(tableName!=null){
			return tableName;
		}
		Class<T> clazz=getEntityClass();
		tableName=getSmartDataSource().getTableName(clazz);
		return tableName;
	}
	
	public Class<T> getEntityClass() {
		if(entityClass!=null) {
			return entityClass;
		}
		entityClass=getTypeClass();
		return entityClass;
	}
	/**
	 * 
	 * @param o
	 * @param excludeFields
	 * @return
	 */
	public int insert(T o,String... excludeFields){
		return insert(o, true, excludeFields);
	}
	/**
	 * 
	 * @param bean
	 * @param withGenerateKey
	 * @param excludeFields
	 * @return
	 */
	public int insert(T bean,boolean withGenerateKey,String... excludeFields){
		return insert(EntityInsert.create(
				getSmartDataSource(), 
				bean, 
				withGenerateKey, excludeFields));
	}
	
	/**
	 * 
	 * @param insert
	 * @return
	 */
	public int insert(EntityInsert insert){
		SqlBean sqlBean=insertProvider().
				insert(insert).
				build();
		String sql=sqlBean.sql;
		Object[] parameters=sqlBean.parameters;
		int result=0;
		if(insert.withGenerateKey){
			result=executeWithGenKey(sql,parameters);		
		}else{
			executeUpdate(sql,parameters);
		}
		return result;
	}
	
	/**
	 * 更新（包括Null字段）
	 * @param bean
	 * @return
	 */
	public int updateIncludeNull(T bean){
		return update(bean,false,null);
	}
	
	/**
	 * 更新指定的字段(如果字段值为NULL,则不会更新)
	 * @param bean
	 * @param includeFields
	 * @return
	 */
	public int updateIncludeFields(T bean,String ... includeFields) {
		Set<String> includeFieldSet=null;
		if(includeFields!=null&&includeFields.length>0) {
			includeFieldSet=new LinkedHashSet<>();
			for (String includeField : includeFields) {
				includeFieldSet.add(includeField);
			}
		}
		return update(bean,includeFields);
	}
	
	/**
	 * 更新指定的字段(如果字段值为NULL,也会更新)
	 * @param bean
	 * @param includeFields
	 * @return
	 */
	public int updateIncludeFieldsIncludeNull(T bean,String ... includeFields) {
		Set<String> includeFieldSet=null;
		if(includeFields!=null&&includeFields.length>0) {
			includeFieldSet=new LinkedHashSet<>();
			for (String includeField : includeFields) {
				includeFieldSet.add(includeField);
			}
		}
		return update(bean,false,includeFieldSet);
	}
	
	/**
	 * 
	 * @param bean
	 * @param excludeFields
	 * @return
	 */
	public int updateExcludeFields(T bean,String ... excludeFields) {
		return update(bean,true,null,excludeFields);
	}
	
	/**
	 * 更新（不包括Null字段）
	 * @param bean
	 * @param includeFields
	 * @return
	 */
	public int update(T bean,
			String... includeFields){
		return update(bean,true,ArrayUtils.toSet(includeFields));
	}
	
	/**
	 * 
	 * @param bean
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public int update(T bean,
			Set<String> includeFields,
			String... excludeFields) {
		return update(bean,true,includeFields,excludeFields);
	}
	//
	/**
	 * 
	 * @param bean
	 * @param excludeNull
	 * @param excludeFields
	 * @return
	 */
	public int update(T bean,
			boolean excludeNull,
			Set<String> includeFields,
			String... excludeFields){
		return update(EntityUpdate.create(getSmartDataSource(), 
				bean, 
				excludeNull, 
				includeFields, 
				null,
				excludeFields));
	}
	
	/**
	 * 
	 * @param bean
	 * @param qw
	 * @param excludeFields
	 * @return
	 */
	public int update(T bean,
			QueryWhere qw,
			String... excludeFields){
		return update(bean, qw, true, null, excludeFields);
	}
	
	/**
	 * 
	 * @param bean
	 * @param qw
	 * @param excludeNull
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public int update(T bean,
			QueryWhere qw,
			boolean excludeNull,
			Set<String> includeFields,
			String... excludeFields){
		return update(EntityUpdate.create(getSmartDataSource(), 
				bean, 
				excludeNull, 
				includeFields, 
				qw, 
				excludeFields));
	}
	
	/**
	 * 
	 * @param update
	 * @return
	 */
	public int update(EntityUpdate update){
		SqlBean sqlBean=updateProvider().
				update(update).
				build();
		int result=executeUpdate(sqlBean.sql,sqlBean.parameters);
		return result;
	}
	
	/**
	 * 
	 * @param qw
	 * @return
	 */
	public int delete(QueryWhere qw){
		SqlBean sqlBean=deleteProvider().
				delete(new EntityDelete(getDatabaseType(), getTableName())).
				queryWhere(qw).
				build();
		int result=executeUpdate(sqlBean.sql,sqlBean.parameters);
		return result;
	}
	
	/**
	 * 
	 * @param qw
	 * @param excludeFields
	 * @return
	 */
	public T getEntity(QueryWhere qw,String ... excludeFields){
		return getEntity(qw, null,excludeFields);
	}
	
	/**
	 * 
	 * @param qw
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public T getEntity(QueryWhere qw,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(qw).
				includeFields(includeFields).
				excludeFields(excludeFields).
				build();
		return queryObject(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public T getEntity(Query<T> query,String ... excludeFields){
		Class<T> entityClass=getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryObject(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param provider
	 * @return
	 */
	public T getEntity(SelectProvider provider){
		SqlBean sqlBean=provider.build();
		return queryObject(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param qw
	 * @param excludeFields
	 * @return
	 */
	public List<T> getList(QueryWhere qw,String ... excludeFields){
		return getList(qw, null, excludeFields);
	}
	
	/**
	 * 
	 * @param qw
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public List<T> getList(QueryWhere qw,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(qw).
				includeFields(includeFields).
				excludeFields(excludeFields).
				needPaging(true).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public List<T> getList(Query<T> query,String ... excludeFields){
		return getList(query, null, excludeFields);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<T> getList(Query<T> query,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				includeFields(includeFields).
				excludeFields(excludeFields).
				needPaging(true).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param selectProvider
	 * @return
	 */
	public List<T> getList(SelectProvider selectProvider){
		SqlBean sqlBean=selectProvider.needPaging(true).build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param excludeFields
	 * @return
	 */
	public List<T> getAll(String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				excludeFields(excludeFields).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public List<T> getAll(QueryWhere query,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public List<T> getAll(QueryWhere query,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				includeFields(includeFields).
				excludeFields(excludeFields).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public List<T> getAll(Query<T> query,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		query.setPageSize(Integer.MAX_VALUE);
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public List<T> getAll(Query<T> query,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass();
		query.setPageSize(Integer.MAX_VALUE);
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				includeFields(includeFields).
				excludeFields(excludeFields).
				build();
		return queryList(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param qw
	 * @return
	 */
	public int getListCount(QueryWhere qw){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				selectCount().
				query(qw).
				needOrderBy(false).
				build();
		return queryForInteger(sqlBean.sql, sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public int getListCount(Query<T> query){
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				selectCount().
				query(query).
				needOrderBy(false).
				build();
		return queryForInteger(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param selectProvider
	 * @return
	 */
	public int getListCount(SelectProvider selectProvider){
		SqlBean sqlBean=selectProvider.selectCount().needOrderBy(false).build();
		return queryForInteger(sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param sql
	 * @param rowHandler
	 * @param parameters
	 * @return
	 */
	public List<T> queryList(
			String sql,
			ResultSetHandler<T> rowHandler, 
			Object... parameters) {
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForList(sqlBean.sql, rowHandler, sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public List<T> queryList(String sql,Object... parameters) {
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForList(sqlBean.sql, new ResultSetHandler<T>() {
			@Override
			public T handleRow(ResultSet row) throws Exception {
				return convertBean(entityClass,row);
			}}, sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public int queryCount(
			String sql,
			Object... parameters) {
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForInteger(sqlBean.sql, sqlBean.parameters);
	}
	/**
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public T queryObject(String sql,Object... parameters) {
		Class<T> entityClass=(Class<T>) getEntityClass();
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForObject(sqlBean.sql, new ResultSetHandler<T>() {
			@Override
			public T handleRow(ResultSet row) throws Exception {
				return convertBean(entityClass,row);
			}}, sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param sql
	 * @param rowHandler
	 * @param parameters
	 * @return
	 */
	public T queryObject(
			String sql,
			ResultSetHandler<T> rowHandler, 
			Object... parameters) {
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForObject(sqlBean.sql, rowHandler, sqlBean.parameters);
	}
}

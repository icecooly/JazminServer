package jazmin.driver.jdbc.smartjdbc.provider.impl.sqlserver;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.cache.EntityFieldInfo;
import jazmin.driver.jdbc.smartjdbc.cache.QueryInfo;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SelectSql;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 
 * @author skydu
 *
 */
public class SqlServerSelectProvider extends SelectProvider{
	//
	//
	public SqlServerSelectProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	
	@Override
	protected String getForUpdateSql() {
		if(!qw.isForUpdate()) {
			return "";
		}
		return "\nwith(UPDLOCK) ";
	}

	@Override
	protected SqlBean build(StringBuilder selectSql) {
		if(query!=null) {
			queryInfo= QueryInfo.create(query.getClass());
		}
		SqlServerBean bean=new SqlServerBean();
		bean.selectSql=selectSql.toString();
		bean.fromSql=getFromSql();
		QueryWhere.WhereStatment ws=getWhereSql();
		bean.whereSql=ws.sql;
		bean.groupBySql=getGroupBySql();
		bean.orderBySql=getOrderBySql();
		bean.forUpdateSql=getForUpdateSql();
		boolean needPaging = getNeedPaging();
		bean.parameters=ws.values;
		if (!needPaging) {
			bean.sql=bean.toSql();
			return bean;
		}
		// now ms to field name
		String orderByFieldName = addIdentifier(String.valueOf(System.currentTimeMillis()));
		EntityFieldInfo field = getOrderEntityField();
		bean.selectSql = selectSql.append(getSelectPaging(field, orderByFieldName)).toString();
		bean.sql = getPagingSql(bean.toSql(), orderByFieldName);
		return bean;
	}
	
	private String getSelectPaging(EntityFieldInfo field, String orderByFieldName) {
		String orderByFiled;
		if(StringUtil.isEmpty(field.statFunction)) {
			orderByFiled = convertFieldName(field.name);
		}else {
			orderByFiled = field.statFunction;
		}
		StringBuilder sbu = new StringBuilder();
		sbu.append(", ROW_NUMBER() OVER(ORDER BY ")
				.append(addIdentifier(orderByFiled))
				.append(") AS ").append(orderByFieldName);
		return sbu.toString();
	}
	
	private boolean getNeedPaging() {
		if(isSelectCount || !needPaging) {
			return false;
		}
		addPaging();
		return -1 != qw.getLimitEnd();
	}
	
	
	private String getPagingSql(String sql, String orderByFieldName) {
		StringBuilder sqlBuilder = new StringBuilder();
		String orderByFiled;
		sqlBuilder.append("select * from (").append(sql).append(") t where ")
				.append(orderByFieldName)
				.append(" between ")
				.append(qw.getLimitStart())
				.append(" and ")
				.append(qw.getLimitEnd())
		;
		return sqlBuilder.toString();
	}
	
	
	private EntityFieldInfo getOrderEntityField() {
		List<EntityFieldInfo> selectFields = getSelectFields();
		List<OrderByField> orderByFieldList = new ArrayList<>();
		for (EntityFieldInfo field : selectFields) {
			String fieldName = field.name;
			if ("id".equals(fieldName)) {
				return field;
			}
			EntityField entityField = field.entityField;
			ColumnType columnType = null;
			if (null != entityField) {
				columnType = entityField.columnType();
			}
			int orderByWeight = null != columnType ? 
					ColumnType.getOrderByWeight(columnType)
					:
					this.getClassOrderByWeight(field.field);
			OrderByField orderByField = new OrderByField();
			orderByField.field = field;
			orderByField.orderByWeight = orderByWeight;
			orderByFieldList.add(orderByField);
		}
		orderByFieldList.sort(Comparator.comparingInt(o -> o.orderByWeight));
		OrderByField orderByField = orderByFieldList.get(0);
		return orderByField.field;
	}

	/**
	 * orderby of class
	 * @param field
	 * @return
	 */
	private int getClassOrderByWeight(Field field) {
		Class<?> type = field.getType();
		if (Integer.class.equals(type) || int.class.equals(type)) {
			return 1;
		} else if (Long.class.equals(type) || long.class.equals(type)) {
			return 2;
		}else if (Float.class.equals(type) || float.class.equals(type)) {
			return 3;
		}else if (Double.class.equals(type) || double.class.equals(type)) {
			return 4;
		} else if (char.class.equals(type)) {
			return 5;
		} else if (String.class.equals(type)) {
			return 6;
		}  else if (Integer[].class.equals(type) || int[].class.equals(type)) {
			return 7;
		} else if (Long[].class.equals(type) || long[].class.equals(type)) {
			return 8;
		}else if (Float[].class.equals(type) || float[].class.equals(type)) {
			return 9;
		}else if (Double[].class.equals(type) || double[].class.equals(type)) {
			return 10;
		} else if (char[].class.equals(type)) {
			return 11;
		} else if (String[].class.equals(type)) {
			return 12;
		} else if (List.class.isAssignableFrom(type)) {
			return 13;
		} else if (Set.class.isAssignableFrom(type)) {
			return 14;
		} else {
			return 1000;
		}
	}
	
	private  static class OrderByField {
		public EntityFieldInfo field;
		public int orderByWeight;
	}
	
}

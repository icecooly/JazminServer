package jazmin.driver.jdbc.smartjdbc.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.ResultSetHandler;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityDelete;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityInsert;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;

/**
 * 
 * @author skydu
 */
public class SmartDAO extends BaseEntityDAO{
	//
	/**
	 * 
	 * @param o
	 * @param excludeFields
	 * @return
	 */
	public int insert(Object o,String... excludeFields){
		return insert(o, true, excludeFields);
	}
	
	/**
	 * 
	 * @param bean
	 * @param withGenerateKey
	 * @param excludeFields
	 * @return
	 */
	public int insert(Object bean,boolean withGenerateKey,String... excludeFields){
		EntityInsert insert=EntityInsert.create(
				getSmartDataSource(),
				bean,
				withGenerateKey,
				excludeFields);
		return insert(insert);
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
	 * 更新
	 * @param bean
	 * @param includeFields
	 * @return
	 */
	public int update(Object bean,
			String... includeFields){
		return update(bean,false,ArrayUtils.toSet(includeFields),null);
	}
	
	/**
	 * 
	 * @param bean
	 * @param queryWhere
	 * @return
	 */
	public int update(Object bean, QueryWhere queryWhere){
		return update(bean,false,null,queryWhere);
	}

	//
	/**
	 * 
	 * @param bean
	 * @param excludeNull 是否更新值为NULL的字段  true:不更新 false:更新
	 * @param includeFields 只更新这些指定的字段
	 * @param queryWhere where条件
	 * @param excludeFields 排除更新这些指定的字段
	 * @return
	 */
	public int update(Object bean,
			boolean excludeNull,
			Set<String> includeFields,
			QueryWhere queryWhere,
			String... excludeFields){
		return update(
				EntityUpdate.create(
				getSmartDataSource(), 
				bean, 
				excludeNull, 
				includeFields, 
				queryWhere, 
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
	 * @param entityClass
	 * @param qw
	 * @return
	 */
	public int delete(Class<?> entityClass,QueryWhere qw){
		return delete(new EntityDelete(
						getDatabaseType(), 
						getSmartDataSource().getTableName(entityClass)),qw);
	}
	
	/**
	 * 
	 * @param delete
	 * @param qw
	 * @return
	 */
	public int delete(EntityDelete delete, QueryWhere qw){
		SqlBean sqlBean=deleteProvider().
				delete(delete).
				queryWhere(qw).
				build();
		int result=executeUpdate(sqlBean.sql,sqlBean.parameters);
		return result;
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param qw
	 * @param excludeFields
	 * @return
	 */
	public <T> T getEntity(Class<T> entityClass,QueryWhere qw,String ... excludeFields){
		return getEntity(entityClass, qw, null,excludeFields);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param qw
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public <T> T getEntity(Class<T> entityClass,QueryWhere qw,
			Set<String> includeFields,
			String ... excludeFields){
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(qw).
				includeFields(includeFields).
				excludeFields(excludeFields).
				build();
		return queryObject(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getEntity(Query<?> query,String ... excludeFields){
		Class<T> entityClass=(Class<T>) getEntityClass(query);
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryObject(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param provider
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getEntity(SelectProvider provider){
		SqlBean sqlBean=provider.build();
		Class<T> clazz=(Class<T>) provider.getEntityClass();
		return queryObject(clazz,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param qw
	 * @param excludeFields
	 * @return
	 */
	public <T> List<T> getList(Class<T> entityClass,QueryWhere qw,String ... excludeFields){
		return getList(entityClass, qw, null, excludeFields);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param qw
	 * @param includeFields
	 * @param excludeFields
	 * @return
	 */
	public <T> List<T> getList(Class<T> entityClass,QueryWhere qw,Set<String> includeFields,String ... excludeFields){
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(qw).
				includeFields(includeFields).
				excludeFields(excludeFields).
				needPaging(true).
				build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public <T> List<T> getList(Query<T> query,String ... excludeFields){
		return getList(query, null, excludeFields);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public <T> List<T> getList(Query<T> query,Set<String> includeFields,String ... excludeFields){
		Class<T> entityClass=getEntityClass(query);
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				includeFields(includeFields).
				excludeFields(excludeFields).
				needPaging(true).
				build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param selectProvider
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(SelectProvider selectProvider){
		Class<T> entityClass=(Class<T>) selectProvider.getEntityClass();
		SqlBean sqlBean=selectProvider.needPaging(true).build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> getAll(Class<T> entityClass,String ... excludeFields){
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				excludeFields(excludeFields).
				build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public <T> List<T> getAll(Class<T> entityClass,QueryWhere query,String ... excludeFields){
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param query
	 * @param excludeFields
	 * @return
	 */
	public <T> List<T> getAll(Query<T> query,String ... excludeFields){
		Class<T> entityClass=getEntityClass(query);
		query.setPageSize(Integer.MAX_VALUE);
		SqlBean sqlBean=selectProvider().
				entityClass(entityClass).
				query(query).
				excludeFields(excludeFields).
				build();
		return queryList(entityClass,sqlBean.sql,sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param qw
	 * @return
	 */
	public int getListCount(Class<?> entityClass,QueryWhere qw){
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
	public int getListCount(Query<?> query){
		Class<?> entityClass=getEntityClass(query);
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
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> Class<T> getEntityClass(Query<T> query) {
		return (Class<T>) ClassUtils.getSuperClassGenricType(query.getClass());
	}
	
	/**
	 * 
	 * @param sql
	 * @param rowHandler
	 * @param parameters
	 * @return
	 */
	public <T> List<T> queryList(
			String sql,
			ResultSetHandler<T> rowHandler, 
			Object... parameters) {
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForList(sqlBean.sql, rowHandler, sqlBean.parameters);
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public <T> List<T> queryList(
			Class<T> entityClass,
			String sql,
			Object... parameters) {
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
		Integer count=queryForInteger(sqlBean.sql, sqlBean.parameters);
		if(count==null) {
			count=0;
		}
		return count;
	}
	/**
	 * 
	 * @param entityClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public final <T> T queryObject(
			Class<T> entityClass,
			String sql,
			Object... parameters) {
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
	public final <T> T queryObject(
			String sql,
			ResultSetHandler<T> rowHandler, 
			Object... parameters) {
		SqlBean sqlBean=SqlBean.build(sql, parameters);
		return queryForObject(sqlBean.sql, rowHandler, sqlBean.parameters);
	}
}

package jazmin.test.driver.jdbc.dao;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.dao.SmartDAO;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;

/**
 * 
 * @author skydu
 *
 */
public class BizDAO extends SmartDAO{

	//
	private static final Logger logger=LoggerFactory.getLogger(BizDAO.class);
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public int insert(Object bean){
		return insert(bean, true, "id");
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteById(Class<?> entityClass,Serializable id){
		delete(entityClass,QueryWhere.create().where("id", id));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public <T> T getById(Class<T> entityClass,Serializable id){
		return getEntity(entityClass,QueryWhere.create().where("id",id));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public <T> T getExistedById(Class<T> entityClass,Serializable id){
		T bean=getById(entityClass,id);
		if(bean==null) {;
			throw new SmartJdbcException("数据不存在");
		}
		return bean;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public <T> T getByIdForUpdate(Class<T> entityClass,Serializable id){
		return getEntity(entityClass,QueryWhere.create().where("id",id).forUpdate());
	}
	//
	/**
	 * 
	 * @param bean
	 * @param fieldName
	 * @param value
	 */
	public void setFieldValue(Object bean,String fieldName,Object value){
		try {
			Field field=bean.getClass().getField(fieldName);
			if(field!=null) {
				field.setAccessible(true);
				field.set(bean, value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	protected Object getFieldValue(Object bean,String fieldName){
		try {
			Field field=bean.getClass().getField(fieldName);
			if(field!=null) {
				return field.get(bean);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	//
}

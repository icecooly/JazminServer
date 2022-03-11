package jazmin.driver.jdbc.smartjdbc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.PrimaryKey;

/**
 * 
 * @author skydu
 *
 */
public class ClassUtils {

	/**
	 * 
	 * @return
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with
					// null...
				}
			}
		}
		return cl;
	}

	/**
	 * 获取类的所有字段(包括父类的)
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		Set<String> filedNames = new HashSet<>();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			try {
				Field[] list = c.getDeclaredFields();
				for (Field field : list) {
					String name = field.getName();
					if (filedNames.contains(name)) {
						continue;
					}
					filedNames.add(field.getName());
					fields.add(field);
				}
			} catch (Exception e) {
				throw new SmartJdbcException(e);
			}
		}
		return fields;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			if (genType instanceof Class) {
				return getSuperClassGenricType((Class<?>)genType);
			}
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (params.length==0) {
			return Object.class;
		}
		return (Class<?>) params[0];
	}
	
	
	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> clazz,String fieldName) {
		List<Field> fields=getFieldList(clazz);
		for (Field field : fields) {
			if(field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	//
	public static Field getExistedField(Class<?> clazz,String fieldName) {
		List<Field> fields=getFieldList(clazz);
		for (Field field : fields) {
			if(field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new SmartJdbcException("no such field "+fieldName+"/"+clazz.getSimpleName());
	}
	//
	public static String getSinglePrimaryKey(Class<?> clazz) {
		List<Field> list=getPrimaryKey(clazz);
		if(list.size()>1||list.size()==0) {
			throw new SmartJdbcException(clazz.getName()+" primaryKey column can only be one.");
		}
		return list.get(0).getName();
	}
	//
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getPrimaryKey(Class<?> clazz){
		List<Field> primaryKey=new ArrayList<>();
		List<Field> fields=getPersistentFields(clazz);
		Field idField=null;
		for (Field field : fields) {
			if(field.getAnnotation(PrimaryKey.class)!=null) {
				primaryKey.add(field);
			}
			if(field.getName().equals("id")) {
				idField=field;
			}
		}
		if(primaryKey.size()==0&&idField==null) {
			throw new SmartJdbcException("PrimaryKey not found in "+clazz.getName());
		}
		if(primaryKey.size()==0) {
			return Arrays.asList(idField);
		}
		return primaryKey;
	}
	//
	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	public static List<Field> getPersistentFields(Class<?> entityClass){
		List<Field> fields=new ArrayList<>();
		List<Field> fieldList=ClassUtils.getFieldList(entityClass);
		for (Field field : fieldList) {
			if(isPersistentField(field)) {
				fields.add(field);
			}
		}
		return fields;
	}
	

	/**
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isPersistentField(Field field) {
		if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
			return false;
		}
		EntityField entityField=field.getAnnotation(EntityField.class);
		if(entityField!=null) {
			return entityField.persistent();
		}
		return true;
	}
}

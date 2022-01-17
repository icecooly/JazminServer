package jazmin.driver.jdbc.smartjdbc.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;


/**
 * 
 * @author skydu
 *
 */
public class ArrayUtils {
	//
	private static Logger logger=LoggerFactory.getLogger(ArrayUtils.class);

	/**
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] convert(final Object value) {
		if(value==null) {
			return null;
		}
		Class<?> fieldType=value.getClass();
		if (fieldType.equals(int[].class)) {
			return ArrayUtils.convert((int[])value);
		}else if(fieldType.equals(short[].class)) {
			return ArrayUtils.convert((short[])value);
		}else if(fieldType.equals(byte[].class)) {
			return ArrayUtils.convert((byte[])value);
		}else if(fieldType.equals(long[].class)) {
			return ArrayUtils.convert((long[])value);
		}else if(fieldType.equals(float[].class)) {
			return ArrayUtils.convert((float[])value);
		}else if(fieldType.equals(double[].class)) {
			return ArrayUtils.convert((double[])value);
		}else if(fieldType.equals(Integer[].class)) {
			return ArrayUtils.convert((Integer[])value);
		}else if(fieldType.equals(Short[].class)) {
			return ArrayUtils.convert((Short[])value);
		}else if(fieldType.equals(Byte[].class)) {
			return ArrayUtils.convert((Byte[])value);
		}else if(fieldType.equals(Long[].class)) {
			return ArrayUtils.convert((Long[])value);
		}else if(fieldType.equals(String[].class)) {
			return ArrayUtils.convert((String[])value);
		}else if (value instanceof Collection) {
			return ((Collection)value).toArray();
		}
		return new Object[] {value};
	}
	//
	public static Object[] convert(final int[] t) {
		if(t==null) {
			return null;
		}
		Object[] ret=new Object[t.length];
		for(int i=0;i<t.length;i++) {
			ret[i]=t[i];
		}
		return ret;
	}
	//
	public static  Object[] convert(final byte[] t) {
		if(t==null) {
			return null;
		}
		Object[] ret=new Object[t.length];
		for(int i=0;i<t.length;i++) {
			ret[i]=t[i];
		}
		return ret;
	}
	//
	public static  Object[] convert(final short[] t) {
		if(t==null) {
			return null;
		}
		Object[] ret=new Object[t.length];
		for(int i=0;i<t.length;i++) {
			ret[i]=t[i];
		}
		return ret;
	}
	//
	public static Object[] convert(final long[] t) {
		if(t==null) {
			return null;
		}
		Object[] ret=new Object[t.length];
		for(int i=0;i<t.length;i++) {
			ret[i]=t[i];
		}
		return ret;
	}
	//
	public static  <T> Object[] convert(final T[] t) {
		if(t==null) {
			return null;
		}
		Object[] ret=new Object[t.length];
		for(int i=0;i<t.length;i++) {
			ret[i]=t[i];
		}
		return ret;
	}
	//
	public static Set<String> toSet(String ... includeFields){
		if(includeFields==null) {
			return null;
		}
		Set<String> set=new HashSet<>();
		for (String field : includeFields) {
			set.add(field);
		}
		return set;
	}
	//
	public static <T> boolean contains(List<T> array, String fieldName, Object target) {
		if (array == null) {
			return false;
		}
		for (Object bean : array) {
			Object v = getFieldValue(bean, fieldName);
			if(v==null) {
				return false;
			}
			if (v.equals(target)) {
				return true;
			}
		}
		return false;
	}
	
	//
	public static Object getFieldValue(Object bean, String fieldName) {
		try {
			return getFieldValue(bean, bean.getClass().getField(fieldName));
		} catch (Exception e) {
			logger.error("bean:{} fieldName:{}",bean.getClass(),fieldName);
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static Object getFieldValue(Object bean, Field f) {
		try {
			return f.get(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	
	//
	public static void main(String[] args) {
		List<String> list=Arrays.asList("sss","ss1");
		Object[] a=convert(list);
		System.out.println(DumpUtil.dump(a));
	}
}

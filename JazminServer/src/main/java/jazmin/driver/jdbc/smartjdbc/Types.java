package jazmin.driver.jdbc.smartjdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

/**
 * 
 * @author skydu
 *
 */
public class Types {

	/**
	 * 
	 */
	public static final HashSet<Class<?>> WRAP_TYPES=new HashSet<>();
	static{
		WRAP_TYPES.add(Boolean.class);
		WRAP_TYPES.add(Character.class);
		WRAP_TYPES.add(Byte.class);
		WRAP_TYPES.add(Short.class);
		WRAP_TYPES.add(Integer.class);
		WRAP_TYPES.add(Long.class);
		WRAP_TYPES.add(BigDecimal.class);
		WRAP_TYPES.add(BigInteger.class);
		WRAP_TYPES.add(Double.class);
		WRAP_TYPES.add(Float.class);
		WRAP_TYPES.add(String.class);
		WRAP_TYPES.add(Date.class);
		WRAP_TYPES.add(Timestamp.class);
		WRAP_TYPES.add(java.sql.Date.class);
		WRAP_TYPES.add(int.class);
		WRAP_TYPES.add(boolean.class);
		WRAP_TYPES.add(char.class);
		WRAP_TYPES.add(byte.class);
		WRAP_TYPES.add(short.class);
		WRAP_TYPES.add(int.class);
		WRAP_TYPES.add(long.class);
		WRAP_TYPES.add(float.class);
		WRAP_TYPES.add(double.class);
		WRAP_TYPES.add(Byte[].class);
		WRAP_TYPES.add(byte[].class);
		WRAP_TYPES.add(Short[].class);
		WRAP_TYPES.add(short[].class);
		WRAP_TYPES.add(Integer[].class);
		WRAP_TYPES.add(int[].class);
		WRAP_TYPES.add(Long[].class);
		WRAP_TYPES.add(long[].class);
		WRAP_TYPES.add(Float[].class);
		WRAP_TYPES.add(float[].class);
		WRAP_TYPES.add(Double[].class);
		WRAP_TYPES.add(double[].class);
		WRAP_TYPES.add(String[].class);
	}
	
	public static final HashSet<Class<?>> ARRAY_TYPES=new HashSet<>();
	static {
		ARRAY_TYPES.add(Byte[].class);
		ARRAY_TYPES.add(Short[].class);
		ARRAY_TYPES.add(Integer[].class);
		ARRAY_TYPES.add(Long[].class);
		ARRAY_TYPES.add(Float[].class);
		ARRAY_TYPES.add(Double[].class);
		ARRAY_TYPES.add(String[].class);
	}
	
}

package jazmin.driver.jdbc.smartjdbc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;

/**
 * 
 * @author skydu
 *
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
@Inherited  
public @interface EntityField {
	
	/**java字段 生成sql时会自动转化为数据库里的字段 eg:userName*/
	String field() default "";
	
	/**字段注释*/
	String comment() default "";
	
	/**是否自增*/
	boolean autoIncrement() default false;
	
	/**这个字段是别的表的关联字段  必须填对应的外键字段 可以有多个按照顺序依次以逗号分隔*/
	String foreignKeyFields() default "";
	
	/**查询时去重*/
	boolean distinct() default false;

	/**统计函数 eg:select min() */
	String statFunc() default "";
	
	/**查询时不映射sql*/
	boolean ignoreWhenSelect() default false;
	
	/**是否持久化到数据库*/
	boolean persistent() default true;
	
	/**字段类型*/
	ColumnType columnType() default ColumnType.NONE;
}
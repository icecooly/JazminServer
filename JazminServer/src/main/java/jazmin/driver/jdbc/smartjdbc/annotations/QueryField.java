package jazmin.driver.jdbc.smartjdbc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;

/**
 * 
 * @author skydu
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QueryField {
	/**别名*/
	public String alias() default "";//
	
	/**和表结构映射的字段名*/
	public String field() default "";
	
	/** 操作符 非字符串默认是EQ*/
	public SqlOperator operator() default SqlOperator.EQ;

	/** 自定义查询sql */
	public String whereSql() default "";//usage: (name like #{nameOrUserName} or userName like #{nameOrUserName})
	
	/**别的表的关联字段  必须填对应的外键字段 可以有多个按照顺序依次  逗号分隔*/
	String foreignKeyFields() default "";
}

package jazmin.driver.jdbc.smartjdbc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jazmin.driver.jdbc.smartjdbc.enums.JoinType;

/**
 * on 
 * 	table1Alias.table1Fields[0]=table2Alias.table2Fields[0] 
 * 	and 
 * 	table1Alias.table1Fields[1]=table2Alias.table2Fields[1]
 * ...
 * @author skydu
 *
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface Join {

	/**类型*/
	public JoinType type() default JoinType.LEFT_JOIN;
	
	/**表2*/
	public Class<?> table2();
	
	/**表2别名*/
	public String table2Alias() default "";

	/**table1 on fields*/
	public String[] table1Fields();

	/**table2 on fields*/
	public String[] table2Fields();
}
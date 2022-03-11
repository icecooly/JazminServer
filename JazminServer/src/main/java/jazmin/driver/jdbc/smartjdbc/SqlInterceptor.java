package jazmin.driver.jdbc.smartjdbc;

/**
 * 
 * @author skydu
 *
 */
public interface SqlInterceptor {

	/**
	 * 
	 * @param sql
	 * @param parameters
	 */
	void beforeExcute(String sql,Object ... parameters);

	/**
	 * 
	 * @param sql
	 * @param parameters
	 */
	void afterExcute(String sql,Object ... parameters);
}

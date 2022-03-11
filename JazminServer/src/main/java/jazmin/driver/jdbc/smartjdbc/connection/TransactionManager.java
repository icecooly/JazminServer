package jazmin.driver.jdbc.smartjdbc.connection;

import java.sql.Connection;

/**
 * 
 * @author skydu
 *
 */
public interface TransactionManager {

	/**
	 * 
	 * @return
	 */
	Connection getConnection();
	
	/**
	 * 
	 */
	void commit();
	
	/**
	 * 
	 */
	void rollback();
}

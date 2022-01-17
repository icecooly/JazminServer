package jazmin.driver.jdbc.smartjdbc.connection;

import java.sql.Connection;

/**
 * 
 * @author skydu
 *
 */
public class ConnectionHolder {
	//
	protected Connection connection;
	//
	protected boolean useTransaction;
	//
	/**
	 * @return the useTransaction
	 */
	public boolean isUseTransaction() {
		return useTransaction;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @param useTransaction the useTransaction to set
	 */
	public void setUseTransaction(boolean useTransaction) {
		this.useTransaction = useTransaction;
	}
}

package jazmin.test.driver.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.SmartJdbc;
import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.connection.ConnectionHolder;
import jazmin.driver.jdbc.smartjdbc.connection.TransactionManager;
import jazmin.driver.jdbc.smartjdbc.util.JdbcUtil;

/**
 * 
 * @author skydu
 *
 */
public class DefaultTransactionManager implements TransactionManager{
	//
	private static Logger logger = LoggerFactory.getLogger(DefaultTransactionManager.class);
	//
	protected static ThreadLocal<ConnectionHolder> connectionHolder = new ThreadLocal<>();

	/**
	 * 
	 */
	public void commit() {
		ConnectionHolder holder = connectionHolder.get();
		connectionHolder.set(null);
		if (holder == null || holder.getConnection() == null) {
			return;
		}
		try {
			if (holder.isUseTransaction()) {// use Transaction
				try {
					holder.getConnection().commit();
				} catch (SQLException e1) {
					logger.error(e1.getMessage(), e1);
				}
			}
		} finally {
			JdbcUtil.close(holder.getConnection());
		}
	}

	/**
	 * 
	 */
	public void rollback() {
		ConnectionHolder holder = connectionHolder.get();
		connectionHolder.set(null);
		if (holder == null || holder.getConnection() == null) {
			return;
		}
		try {
			if (holder.isUseTransaction()) {// use Transaction
				try {
					holder.getConnection().rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage(), e1);
				}
			}

		} finally {
			JdbcUtil.close(holder.getConnection());
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection openConnection() throws SQLException {
		SmartDataSource smartDataSource=SmartJdbc.getDatasource();
		if(smartDataSource==null) {
			throw new RuntimeException("DataSource not found");
		}
		return smartDataSource.getDataSource().getConnection();
	}

	/**
	 * 
	 */
	public Connection getConnection(){
		Connection conn=null;
		try {
			ConnectionHolder holder = connectionHolder.get();
			if(holder==null) {
				holder = new ConnectionHolder();
				connectionHolder.set(holder);
			}
			conn=holder.getConnection();
			if(conn==null) {
				conn=openConnection();
				if(holder.isUseTransaction()==conn.getAutoCommit()) {
					conn.setAutoCommit(!holder.isUseTransaction());
				}
				holder.setConnection(conn);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SmartJdbcException(e.getMessage());
		}
		return conn;
	}
}

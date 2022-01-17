package jazmin.driver.jdbc.smartjdbc.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;



/**
 * 
 * @author skydu
 *
 */
public class JdbcUtil {
	//
	private static Logger logger=LoggerFactory.getLogger(JdbcUtil.class);
	//
	/**
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}catch (SQLException ex) {
				logger.debug("Could not close JDBC Connection", ex);
			}catch (Throwable ex) {
				logger.debug("Unexpected exception on closing JDBC Connection", ex);
			}finally {
				if(logger.isDebugEnabled()){
					logger.debug("close Connection:{}",conn);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param stmt
	 */
	private static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException ex) {
				logger.trace("Could not close JDBC Statement", ex);
			}
			catch (Throwable ex) {
				logger.trace("Unexpected exception on closing JDBC Statement", ex);
			}
		}
	}
	
	/**
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException ex) {
				logger.trace("Could not close JDBC ResultSet", ex);
			}
			catch (Throwable ex) {
				logger.trace("Unexpected exception on closing JDBC ResultSet", ex);
			}
		}
	}
	
	/**
	 * 
	 * @param stmt
	 * @param rs
	 */
	public static void close(Statement stmt,ResultSet rs) {
		close(rs);
		close(stmt);
	}
}

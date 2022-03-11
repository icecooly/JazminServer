package jazmin.driver.jdbc.smartjdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;

/**
 * 
 * @author skydu
 *
 */
public class DriverManagerDataSource implements DataSource{
	//
	private static final Logger logger=LoggerFactory.getLogger(DriverManagerDataSource.class);
	//
	private String url;
	
	private String username;

	private String password;

	
	public DriverManagerDataSource() {
		
	}
	
	public DriverManagerDataSource(String url,String username,String password) {
		this.url=url;
		this.username=username;
		this.password=password;
	}
	
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("getLogWriter");
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");

	}

	/**
	 * 
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		throw new SQLException("DataSource of type [" + getClass().getName() +
				"] cannot be unwrapped as [" + iface.getName() + "]");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return iface.isInstance(this);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	/**
	 * 
	 * @param driverClassName
	 */
	public void setDriverClassName(String driverClassName) {
		String driverClassNameToUse = driverClassName.trim();
		try {
			Class.forName(driverClassNameToUse, true, ClassUtils.getDefaultClassLoader());
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Could not load JDBC driver class [" + driverClassNameToUse + "]", ex);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Loaded JDBC driver: " + driverClassNameToUse);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	}
}

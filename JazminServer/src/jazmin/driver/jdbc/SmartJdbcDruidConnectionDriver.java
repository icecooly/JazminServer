/**
 * 
 */
package jazmin.driver.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.alibaba.druid.pool.DruidDataSource;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.SmartJdbc;
import jazmin.driver.jdbc.smartjdbc.connection.TransactionManager;
import jazmin.core.Jazmin;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.misc.InfoBuilder;
import jazmin.server.console.ConsoleServer;

/**
 * 
 * @author skydu
 *
 */
public class SmartJdbcDruidConnectionDriver extends ConnectionDriver implements TransactionManager{
	//
	private static Logger logger=LoggerFactory.get(SmartJdbcDruidConnectionDriver.class);
	private DruidDataSource  dataSource;
	private SmartDataSource smartDataSource;
	//
	public SmartJdbcDruidConnectionDriver() throws SQLException {
		dataSource=new DruidDataSource();
		setInitialSize(20);
		setMinIdle(20);
		setMaxActive(50);
		setMaxWait(60000);
		setTimeBetweenEvictionRunsMillis(60000);
		setMinEvictableIdleTimeMillis(300000);
		setValidationQuery("SELECT 'x'");
		setTestWhileIdle(true);
		setTestOnBorrow(false);
		setTestOnReturn(false);
		setPoolPreparedStatements(false);
		setFilters("stat");
		//
		smartDataSource=new SmartDataSource(dataSource, this);
		smartDataSource.setFieldCamelCase(true);
		SmartJdbc.registerDataSource(smartDataSource);
		logger.info("registerDataSource smartDataSource:{}",smartDataSource);
	}
	//
	@Override
	public Connection getWorkConnection() throws SQLException {
		return dataSource.getConnection();
	}
	/**
	 * return jdbc connection url
	 * @return jdbc connection url
	 */
	public String getUrl() {
		return dataSource.getUrl();
	}
	/**
	 * return jdbc login timeout time
	 * @return jdbc login timeout time
	 */
	public int getLoginTimeout()  {
		return dataSource.getLoginTimeout();
	}
	
	/**
	 * return jdbc user
	 * @return jdbc user
	 */
	public String getUser() {
		return dataSource.getUsername();
	}
	
	public long getTimeBetweenLogStatsMillis() {
        return dataSource.getTimeBetweenLogStatsMillis();
    }
	//
	public boolean isOracle() {
        return dataSource.isOracle();
    }
	//
    public String getLastError() {
        Throwable error=dataSource.getLastError();
        if(error!=null) {
        		return error.getMessage();
        }
        return null;
    }

    public long getLastErrorTimeMillis() {
        return dataSource.getLastErrorTimeMillis();
    }

    public Date getLastErrorTime() {
        return dataSource.getLastErrorTime();
    }

    public long getLastCreateErrorTimeMillis() {
        return dataSource.getLastCreateErrorTimeMillis();
    }

    public Date getLastCreateErrorTime() {
        return dataSource.getLastCreateErrorTime();
    }

    public int getTransactionQueryTimeout() {
        return dataSource.getTransactionQueryTimeout();
    }

    public long getExecuteCount() {
        return dataSource.getExecuteCount();
    }
    
    public boolean isDupCloseLogEnable() {
        return dataSource.isDupCloseLogEnable();
    }
    
    public String getDriverClassName() {
        return dataSource.getDriverClassName();
    }
    
    public int getInitialSize() {
        return dataSource.getInitialSize();
    }
    
    public int getMaxActive() {
        return dataSource.getMaxActive();
    }
    
    public int getMinIdle() {
        return dataSource.getMinIdle();
    }
    
    public long getMaxWait() {
        return dataSource.getMaxWait();
    }
    
    public int getActiveCount() {
    		return dataSource.getActiveCount();
    }
    
    public int getActivePeak() {
    		return	dataSource.getActivePeak();
    }
	//
	/**
	 * 
	 * @param driverClass
	 */
	public void setDriverClass(String driverClass){
		dataSource.setDriverClassName(driverClass);
	}
	/**
	 * 
	 * @param jdbcUrl
	 */
	public void setUrl(String jdbcUrl){
		dataSource.setUrl(jdbcUrl);
	}
	
	/**
	 * 
	 * @param username
	 */
	public void setUser(String username){
		dataSource.setUsername(username);
	}
	
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password){
		dataSource.setPassword(password);
	}

	/**
	 * 
	 * @param initialSize
	 */
	public void setInitialSize(int initialSize){
		dataSource.setInitialSize(initialSize);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setMinIdle(int value){
		dataSource.setMinIdle(value);
	}
	
	/**
	 * 
	 * @param maxActive
	 */
	public void setMaxActive(int maxActive){
		dataSource.setMaxActive(maxActive);
	}
	
	/**
	 * 
	 * @param maxWaitMillis
	 */
	public void setMaxWait(long maxWaitMillis){
		dataSource.setMaxWait(maxWaitMillis);
	}
	
	/**
	 * 
	 * @param timeBetweenConnectErrorMillis
	 */
	public void setTimeBetweenConnectErrorMillis(long timeBetweenConnectErrorMillis){
		dataSource.setTimeBetweenConnectErrorMillis(timeBetweenConnectErrorMillis);
	}
	
	/**
	 * 
	 * @param timeBetweenEvictionRunsMillis
	 */
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	}
	
	/**
	 * 
	 * @param minEvictableIdleTimeMillis
	 */
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	}
	
	/**
	 * 
	 * @param validationQuery
	 */
	public void setValidationQuery(String validationQuery){
		dataSource.setValidationQuery(validationQuery);
	}
	
	/**
	 * 
	 * @param testWhileIdle
	 */
	public void setTestWhileIdle(boolean testWhileIdle){
		dataSource.setTestWhileIdle(testWhileIdle);
	}
	
	/**
	 * 
	 * @param testOnBorrow
	 */
	public void setTestOnBorrow(boolean testOnBorrow){
		dataSource.setTestOnBorrow(testOnBorrow);
	}
	
	/**
	 * 
	 * @param testOnReturn
	 */
	public void setTestOnReturn(boolean testOnReturn){
		dataSource.setTestOnReturn(testOnReturn);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setPoolPreparedStatements(boolean value){
		dataSource.setPoolPreparedStatements(value);
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setMaxPoolPreparedStatementPerConnectionSize(int size){
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(size);
	}
	
	/**
	 * 
	 * @param filters
	 * @throws SQLException 
	 */
	public void setFilters(String filters) throws SQLException{
		dataSource.setFilters(filters);
	}
	
	/**
	 * 
	 * @param removeAbandoned
	 */
	public void setRemoveAbandoned(boolean removeAbandoned){
		dataSource.setRemoveAbandoned(removeAbandoned);
	}
	
	/**
	 * 
	 * @param removeAbandonedTimeout
	 */
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout){
		dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
	}
	
	/**
	 * 
	 * @param logAbandoned
	 */
	public void setLogAbandoned(boolean logAbandoned){
		dataSource.setLogAbandoned(logAbandoned);
	}
	//--------------------------------------------------------------------------
	@Override
	public void init() throws Exception {
		super.init();
		smartDataSource.init();
	}
	//
	@Override
	public void start() throws Exception {
		ConsoleServer cs=Jazmin.getServer(ConsoleServer.class);
		if(cs!=null){
			cs.registerCommand(SmartJdbcDruidDriverCommand.class);
		}
		smartDataSource.start();
	}
	//
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	//
	@Override
	public String info() {
		InfoBuilder op=InfoBuilder.create().format("%-30s:%-30s\n");
		op.section(getClass().getSimpleName());
		op.print("Version", dataSource.getVersion());
		op.print("DbType", dataSource.getDbType());
		op.print("ErrorCount", dataSource.getErrorCount());
		op.print("LastErrorTime", dataSource.getLastErrorTime());
		op.print("LastError", dataSource.getLastError());
		op.print("DriverClassName", dataSource.getDriverClassName());
		op.print("JdbcUrl", dataSource.getUrl());
		op.print("Username", dataSource.getUsername());
		op.print("InitialSize", dataSource.getInitialSize());
		op.print("MinIdle", dataSource.getMinIdle());
		op.print("MaxActive", dataSource.getMaxActive());
		op.print("MaxWait", dataSource.getMaxWait());
		op.print("ActiveCount", dataSource.getActiveCount());
		op.print("ActivePeak", dataSource.getActivePeak());
		op.print("ActivePeakTime", dataSource.getActivePeakTime());
		op.print("QueryTimeout",dataSource.getQueryTimeout());
		op.print("TimeBetweenConnectErrorMillis", dataSource.getTimeBetweenConnectErrorMillis());
		op.print("MinEvictableIdleTimeMillis", dataSource.getMinEvictableIdleTimeMillis());
		op.print("MaxEvictableIdleTimeMillis", dataSource.getMaxEvictableIdleTimeMillis());
		op.print("TimeBetweenEvictionRunsMillis",dataSource.getTimeBetweenEvictionRunsMillis());
		op.print("ValidationQuery", dataSource.getValidationQuery());
		op.print("TestWhileIdle", dataSource.isTestWhileIdle());
		op.print("TestOnBorrow", dataSource.isTestOnBorrow());
		op.print("TestOnReturn", dataSource.isTestOnReturn());
		op.print("CommitCount", dataSource.getCommitCount());
		op.print("RollbackCount", dataSource.getRollbackCount());
		op.print("ConnectCount", dataSource.getConnectCount());
		op.print("PoolPreparedStatements", dataSource.isPoolPreparedStatements());
		op.print("MaxPoolPreparedStatementPerConnectionSize", dataSource.getMaxPoolPreparedStatementPerConnectionSize());
		op.print("CachedPreparedStatementHitCount", dataSource.getCachedPreparedStatementHitCount());
		op.print("CachedPreparedStatementMissCount", dataSource.getCachedPreparedStatementMissCount());
		op.print("CachedPreparedStatementDeleteCount", dataSource.getCachedPreparedStatementDeleteCount());
		op.print("CachedPreparedStatementCount", dataSource.getCachedPreparedStatementCount());
		op.print("CachedPreparedStatementAccessCount", dataSource.getCachedPreparedStatementAccessCount());
		op.print("RemoveAbandonedTimeout",dataSource.getRemoveAbandonedTimeout());
		op.print("RemoveAbandonedCount",dataSource.getRemoveAbandonedCount());
		op.print("RemoveAbandoned",dataSource.isRemoveAbandoned());
		op.print("IsLogAbandoned",dataSource.isLogAbandoned());
		op.print("Filters", dataSource.getFilterClasses());
		op.print("dataBaseType", smartDataSource.getDatabaseType());
		return op.toString();
	}
	/**
	 * @return the dataSource
	 */
	public DruidDataSource getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * @return the smartDataSource
	 */
	public SmartDataSource getSmartDataSource() {
		return smartDataSource;
	}
	/**
	 * @param smartDataSource the smartDataSource to set
	 */
	public void setSmartDataSource(SmartDataSource smartDataSource) {
		this.smartDataSource = smartDataSource;
	}
}

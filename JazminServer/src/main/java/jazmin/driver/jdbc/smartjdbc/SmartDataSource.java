package jazmin.driver.jdbc.smartjdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import javax.sql.DataSource;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.connection.TransactionManager;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author skydu
 *
 */
public class SmartDataSource {
	//
	private static Logger logger=LoggerFactory.getLogger(SmartDataSource.class);
	//
	public static final String DEFAULT_DATASOURCE_INDEX = "master";
	//
	private DataSource dataSource;
	
	private DatabaseType databaseType;
	
	private List<SqlInterceptor> sqlInterceptors;
	
	private TransactionManager transactionManager;
	
	private boolean fieldCamelCase;
	
	private boolean bool2Int;
	
	private Map<String,DatabaseType> driverClassMapping;
	
	private String identifier="";
	
	/**
	 * javaFieldName->dbName
	 */
	private Function<String, String> convertFieldNameFunc = (fieldName) -> {
		return fieldName;
	};
	//
	public SmartDataSource(DataSource dataSource,TransactionManager transactionManager) {
		this.dataSource=dataSource;
		this.transactionManager=transactionManager;
		this.sqlInterceptors=new ArrayList<>();
		this.driverClassMapping=new HashMap<>();
	}
	//
	private void defaultDriverClassNameMapping() {
		driverClassMapping.put("com.mysql.cj.jdbc.Driver", DatabaseType.MYSQL);
		driverClassMapping.put("com.mysql.jdbc.Driver", DatabaseType.MYSQL);
		driverClassMapping.put("org.postgresql.Driver", DatabaseType.POSTGRESQL);
		driverClassMapping.put("com.kingbase8.Driver", DatabaseType.KINGBASE);
		driverClassMapping.put("sgcc.nds.jdbc.driver.NdsDriver", DatabaseType.NDS);
	}
	//
	private void loadingDriverClassNameMapping() {
		defaultDriverClassNameMapping();
		String fileName=System.getProperty("smartjdbc.driverclassmapping.file");
		if(fileName!=null){
			try {
				File bf=new File(fileName);
				try (InputStream input = new FileInputStream(bf)) {
		            Properties prop = new Properties();
		            prop.load(input);
		            Iterator<String> it=prop.stringPropertyNames().iterator();
		            while(it.hasNext()){
		                String driverClassName=it.next();
		                String type=prop.getProperty(driverClassName);
		                if(driverClassName==null||type==null) {
		                	continue;
		                }
		                DatabaseType databaseType=DatabaseType.valueOf(type.trim());
		                if(databaseType==null) {
		                	logger.warn("type not support {}",type);
		                	continue;
		                }
		                driverClassMapping.put(driverClassName.trim(), databaseType);
		            }
		        }
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	//
	public void init() throws Exception {
		Connection conn=null;
		try {
			loadingDriverClassNameMapping();
			conn=dataSource.getConnection();
			String driverClassName=DriverManager.getDriver(conn.getMetaData().getURL()).getClass().getName();
			databaseType=driverClassMapping.get(driverClassName);
			if(databaseType==null) {
				throw new SmartJdbcException("not support database "+driverClassName);
			}
			if(databaseType.equals(DatabaseType.MYSQL)) {
				identifier="`";
			}
			if(databaseType.equals(DatabaseType.POSTGRESQL)) {
				identifier="\"";
			}
			if(databaseType.equals(DatabaseType.KINGBASE)) {
				identifier="\"";
			}
			if(databaseType.equals(DatabaseType.NDS)) {
				identifier="";
			}
			logger.info("init driverClassName:{} databaseType:{} identifier:{}",
					driverClassName,databaseType,identifier);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
	}
	//
	public void start() throws Exception{
		
	}
	/**
	 * @return the convertFieldNameFunc
	 */
	public Function<String, String> getConvertFieldNameFunc() {
		return convertFieldNameFunc;
	}

	/**
	 * @param convertFieldNameFunc the convertFieldNameFunc to set
	 */
	public void setConvertFieldNameFunc(Function<String, String> convertFieldNameFunc) {
		this.convertFieldNameFunc = convertFieldNameFunc;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public String convertFieldName(String name) {
		if(fieldCamelCase) {
			return convertFieldNameCamelCase(name);
		}
		return convertFieldNameFunc.apply(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static  String convertFieldNameCamelCase(String name) {
		StringBuffer result = new StringBuffer();
		for (char c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				result.append("_");
			}
			result.append(Character.toLowerCase(c));
		}
		return result.toString();
	}
	//

	//
	/**
	 * @return the sqlInterceptors
	 */
	public List<SqlInterceptor> getSqlInterceptors() {
		return sqlInterceptors;
	}

	/**
	 * @param sqlInterceptors the sqlInterceptors to set
	 */
	public void setSqlInterceptors(List<SqlInterceptor> sqlInterceptors) {
		this.sqlInterceptors = sqlInterceptors;
	}

	/**
	 * 
	 * @param sqlInterceptor
	 */
	public void addSqlInterceptor(SqlInterceptor sqlInterceptor) {
		sqlInterceptors.add(sqlInterceptor);
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the databaseType
	 */
	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	/**
	 * @param databaseType the databaseType to set
	 */
	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * @return the transactionManager
	 */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @return the fieldCamelCase
	 */
	public boolean isFieldCamelCase() {
		return fieldCamelCase;
	}

	/**
	 * @param fieldCamelCase the fieldCamelCase to set
	 */
	public void setFieldCamelCase(boolean fieldCamelCase) {
		this.fieldCamelCase = fieldCamelCase;
	}

	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		if(transactionManager==null) {
			throw new SmartJdbcException("transactionManager is null");
		}
		return transactionManager.getConnection();
	}

	/**
	 * 
	 */
	public void commit() {
		transactionManager.commit();
	}
	
	/**
	 * 
	 */
	public void rollback() {
		transactionManager.rollback();
	}
	
	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	public String getTableName(Class<?> entityClass) {
		Entity entity=entityClass.getAnnotation(Entity.class);
		if (entity != null) {
			if(!StringUtil.isEmpty(entity.tableName())) {//tableName first
				return entity.tableName();
			}
		}
		throw new SmartJdbcException("tableName not found "+entityClass);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * 
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier=identifier;
	}
	/**
	 * @return the bool2Int
	 */
	public boolean isBool2Int() {
		return bool2Int;
	}
	/**
	 * @param bool2Int the bool2Int to set
	 */
	public void setBool2Int(boolean bool2Int) {
		this.bool2Int = bool2Int;
	}
	/**
	 * @return the driverClassMapping
	 */
	public Map<String, DatabaseType> getDriverClassMapping() {
		return driverClassMapping;
	}
	/**
	 * @param driverClassMapping the driverClassMapping to set
	 */
	public void setDriverClassMapping(Map<String, DatabaseType> driverClassMapping) {
		this.driverClassMapping = driverClassMapping;
	}
}

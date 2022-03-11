package jazmin.driver.jdbc.smartjdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author skydu
 *
 */
public class SmartJdbc {
	//

	public static final String DEFAULT_DATASOURCE_INDEX = "master";
	/**
	 * 
	 */
	private static Map<String, SmartDataSource> dataSources = new HashMap<>();
	//
	/**
	 * 
	 * @param dataSource
	 */
	public static void registerDataSource(SmartDataSource dataSource) {
		registerDataSource(DEFAULT_DATASOURCE_INDEX, dataSource);
	}
	
	/**
	 * 
	 * @param index
	 * @param dataSource
	 */
	public static void registerDataSource(String index, SmartDataSource dataSource) {
		dataSources.put(index, dataSource);
	}

	/**
	 * 
	 * @return
	 */
	public static SmartDataSource getDatasource() {
		return getDatasource(DEFAULT_DATASOURCE_INDEX);
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public static SmartDataSource getDatasource(String index) {
		SmartDataSource dataSource=dataSources.get(index);
		if(dataSource==null) {
			throw new RuntimeException("no datasource found for index:"+index);
		}
		return dataSource;
	}
	
}

package jazmin.driver.jdbc.smartjdbc;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;

/**
 * 
 * @author skydu
 *
 */
public class SmartJdbcUtils {

	/**
	 * 
	 * @param type
	 */
	public static boolean isBasePostgresql(DatabaseType type) {
		if (type.equals(DatabaseType.POSTGRESQL)||
				type.equals(DatabaseType.KINGBASE)||
				type.equals(DatabaseType.NDS)
				) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public static String identifier(DatabaseType type) {
		if(type.equals(DatabaseType.MYSQL)) {
			return "`";
		}
		if(isBasePostgresql(type)) {
			return "\"";
		}
		return "";
	}
}

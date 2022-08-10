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
		if(DatabaseType.MYSQL.equals(type)) {
			return "`";
		} else if(isBasePostgresql(type)) {
			return "\"";
		} 
		// sql server use [field] or "field"
		else if (DatabaseType.SQL_SERVER.equals(type)) {
			return "\"";
		}
		return "";
	}
	
	public static String addIdentifier(DatabaseType type, String fieldName) {
		String leftIdentifier = "";
		String rightIdentifier = "";
		if(DatabaseType.MYSQL.equals(type)) {
			leftIdentifier = "`";
			rightIdentifier = "`";
		} else if(isBasePostgresql(type)) {
			leftIdentifier = "\"";
			rightIdentifier = "\"";
		}
		// sql server use [field] or "field"
		else if (DatabaseType.SQL_SERVER.equals(type)) {
			leftIdentifier = "[";
			rightIdentifier = "]";
		}
		return new StringBuilder().append(leftIdentifier).append(fieldName).append(rightIdentifier).toString();
	}
}

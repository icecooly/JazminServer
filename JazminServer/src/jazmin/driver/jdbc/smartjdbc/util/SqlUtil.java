package jazmin.driver.jdbc.smartjdbc.util;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;

/**
 * 
 * @author skydu
 *
 */
public class SqlUtil {

	/*
	 * 
	 */
	public static String identifier(DatabaseType type) {
		if(type.equals(DatabaseType.MYSQL)) {
			return "`";
		}
		if(type.equals(DatabaseType.POSTGRESQL)) {
			return "\"";
		}
		if(type.equals(DatabaseType.KINGBASE)) {
			return "\"";
		}
		if(type.equals(DatabaseType.NDS)) {
			return "";
		}
		return "";
	}
}

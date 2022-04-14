package jazmin.driver.jdbc.smartjdbc.util;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;

/**
 * 
 * @author skydu
 *
 */
public class SqlUtil {

	public static String identifier(DatabaseType type) {
		if(type.equals(DatabaseType.MYSQL)) {
			return "`";
		}
		if(isBasePostgresql(type)) {
			return "\"";
		}
		return "";
	}
	
	public static boolean isBasePostgresql(DatabaseType type) {
		if(isPostgresql(type)||isKingbasesql(type)||isNds(type)) {
			return true;
		}
		return false;
	}
	
	public static boolean isPostgresql(DatabaseType type) {
		return type.equals(DatabaseType.POSTGRESQL);
	}
	
	public static boolean isKingbasesql(DatabaseType type) {
		return type.equals(DatabaseType.KINGBASE);
	}
	
	public static boolean isNds(DatabaseType type) {
		return type.equals(DatabaseType.NDS);
	}
	
	public static boolean isMysql(DatabaseType type) {
		return type.equals(DatabaseType.MYSQL);
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.entity;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;

/**
 * 
 * @author skydu
 *
 */
public class Entity {

	public String tableName;//t_user  not  "t_user"
	
	public DatabaseType databaseType;
	
	public Entity(DatabaseType databaseType, String tableName) {
		this.databaseType=databaseType;
		this.tableName=tableName;
	}
}

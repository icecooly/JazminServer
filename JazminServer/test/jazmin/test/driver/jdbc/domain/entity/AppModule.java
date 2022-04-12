package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.PrimaryKey;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app_module")
public class AppModule {

	@PrimaryKey
	public String appId;
	
	@PrimaryKey
	public String id;
	
	public String name;
	
	public String icon;
}

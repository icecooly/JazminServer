package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app_module")
public class AppModule {

	public String appId;
	
	public String id;
	
	public String name;
}

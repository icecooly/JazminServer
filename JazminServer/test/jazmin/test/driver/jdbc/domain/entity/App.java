package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app")
public class App {

	public String id;
	
	public String name;
}

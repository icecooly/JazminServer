package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.LeftJoin;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app_table")
public class AppTable {

	public String id;
	
	public String appId;
	
	public String moduleId;
	
	@LeftJoin(table2 = AppModule.class,table1Fields = {"appId","moduleId"}, table2Fields = {"appId","id"})
	@EntityField(field = "name")
	public String moduleName;
}

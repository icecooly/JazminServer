package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.Join;
import jazmin.driver.jdbc.smartjdbc.annotations.LeftJoin;
import jazmin.driver.jdbc.smartjdbc.annotations.PrimaryKey;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.JoinType;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app_table_field")
public class AppTableField {

	@PrimaryKey
	public String id;
	
	@PrimaryKey
	public String appId;
	
	public String tableId;
	
	public String moduleId;
	
	@LeftJoin(table2 = AppModule.class,table1Fields = {"appId","moduleId"}, table2Fields = {"appId","id"})
	@EntityField(field = "name")
	public String moduleName;
	
	@LeftJoin(table2 = AppModule.class,table1Fields = {"appId","moduleId"}, table2Fields = {"appId","id"})
	@EntityField(field = "icon")
	public String moduleIcon;
	
	//
	public static class AppTableQuery extends Query<AppTableField>{
		
		@Join(type = JoinType.INNER_JOIN, table2 = AppModule.class,table1Fields = {"appId","moduleId"}, table2Fields = {"appId","id"})
		@QueryField(field = "name")
		public String moduleName;
	}
	
}

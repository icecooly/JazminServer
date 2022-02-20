package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_role")
public class Role extends BaseEntity{

	/**角色名称*/
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

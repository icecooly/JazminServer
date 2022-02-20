package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;

/**
 * 部门
 * @author skydu
 *
 */
@Entity(tableName = "t_department")
public class Department extends BaseEntity{

	private String name;
	
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

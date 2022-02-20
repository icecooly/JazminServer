package jazmin.test.driver.jdbc.domain.vo;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_user")
public class UserSimple {
	
	private Integer id;
	
	
	private String name;
	
	
	private Integer test;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getTest() {
		return test;
	}


	public void setTest(Integer test) {
		this.test = test;
	}
	
	

}

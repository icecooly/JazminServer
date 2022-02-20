package jazmin.test.driver.jdbc.domain.vo;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName="t_user")
public class UserStat {

	private Integer gender;

	@EntityField(statFunc="count",field="id")
	private Integer num;

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
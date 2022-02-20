package jazmin.test.driver.jdbc.domain.entity;

import java.util.Date;

import jazmin.driver.jdbc.smartjdbc.annotations.PrimaryKey;

/**
 * 
 * @author skydu
 *
 */
public abstract class BaseEntity {
	
	@PrimaryKey
	private Integer id;
	
	private Date createTime;
	
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	//
	
}

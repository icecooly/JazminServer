package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.PrimaryKey;

import java.util.Date;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_app_server")
public class AppServer {

	public String appId;
	
//	@PrimaryKey
//	public String id;
	
	public int mobileNo;
	
	public String name;
	
	public String icon;
	
	public Date createTime;
	
	public  Date updateTime;
}

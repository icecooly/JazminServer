package jazmin.test.driver.jdbc.domain.entity;

import java.util.List;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
/**
 * 文章
 * @author skydu
 *
 */
@Entity(tableName = "t_article")
public class Article extends BaseEntity{
	//
	public static final int STATUS_待审核=1;
	public static final int STATUS_审核通过=2;
	public static final int STATUS_审核未通过=3;
	//
	/**标题*/
	private String title;
	
	/**内容*/
	private String content;
	
	/**状态*/
	private Integer status;

	@ForeignKey(entityClass = User.class)
	private Integer createUserId;
	
	@ForeignKey(entityClass = User.class)
	private Integer updateUserId;
	
	/**创建人名称*/
	@EntityField(foreignKeyFields="createUserId",field="name",persistent = false)
	private String createUserName;
	
	@EntityField(foreignKeyFields="createUserId",field="mobileNo",persistent = false)
	private String createUserMobileNo;
	
	/**创建人所在部门名称*/
	@EntityField(foreignKeyFields="createUserId,departmentId",field="name",persistent = false)
	private String createUserDepartmentName;
	
	/***/
	@EntityField(foreignKeyFields="updateUserId",persistent = false)
	private User updateUser;
	
	private List<User> favoriteUserList;
	
	//
	public Article() {
		
	}
	//
	public Article(String title) {
		this.title=title;
	}
	
}

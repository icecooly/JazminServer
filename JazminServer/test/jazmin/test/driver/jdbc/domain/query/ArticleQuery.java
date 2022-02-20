package jazmin.test.driver.jdbc.domain.query;

import java.util.List;

import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.annotations.Join;
import jazmin.driver.jdbc.smartjdbc.annotations.Joins;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryConditionType;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.test.driver.jdbc.domain.entity.Article;
import jazmin.test.driver.jdbc.domain.entity.ArticleUserLike;
import jazmin.test.driver.jdbc.domain.entity.Department;
import jazmin.test.driver.jdbc.domain.entity.User;
/**
 * 
 * @author skydu
 *
 */
public class ArticleQuery extends Query<Article>{

	@QueryField(field = "id", operator = SqlOperator.IN)
	private List<Integer> idInList;
	
	@QueryField
	private String title;
	
	@QueryField
	private Integer status;
	
	@QueryField(whereSql=" (title like concat('%',#{titleOrContent},'%') or content like concat('%','${titleOrContent}','%'))")
	private String titleOrContent;
	
	@Join(table2=User.class,table1Fields= {"createUserId"},table2Fields = {"id"})
	@QueryField(field="name")
	private String createUserName;

	@QueryField(field="mobileNo",foreignKeyFields = "createUserId")
	private String createUserMobileNo;

	@Join(table2=User.class,table1Fields= {"updateUserId"},table2Fields= {"id"})
	@QueryField(field="name")
	private String updateUserName;
	
	@QueryField(field="status")
	private int[] statusList;
	
	/**likeUserId喜爱的文章*/
	@Join(table2=ArticleUserLike.class,table1Fields = {"id"},table2Fields= {"articleId"})
	@QueryField(field="userId")
	private Integer likeUserId;
	
	@QueryField(field="name",operator = SqlOperator.LIKE,foreignKeyFields="createUserId,departmentId")
	private String createUserDepartmentName;
	
	@Joins(joins = {
			@Join(table2 = User.class,table1Fields = {"createUserId"},table2Fields = {"id"}),
			@Join(table2 = Department.class,table1Fields = {"departmentId"},table2Fields = {"id"})
			})
	@QueryField(field="name",operator = SqlOperator.LIKE_RIGHT)
	private String createUserDepartmentName2;
	
	//
	@QueryConditionType(ConditionType.OR)
	private IdListOrTitle idListOrTitle;
	//
	public static class IdListOrTitle {
		//
		@QueryField(operator = SqlOperator.LIKE)
		private String title;
		
		@QueryField(field = "id", operator = SqlOperator.IN)
		private List<Integer> idInList;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<Integer> getIdInList() {
			return idInList;
		}

		public void setIdInList(List<Integer> idInList) {
			this.idInList = idInList;
		}
		
		

	}
	public List<Integer> getIdInList() {
		return idInList;
	}
	public void setIdInList(List<Integer> idInList) {
		this.idInList = idInList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitleOrContent() {
		return titleOrContent;
	}
	public void setTitleOrContent(String titleOrContent) {
		this.titleOrContent = titleOrContent;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserMobileNo() {
		return createUserMobileNo;
	}
	public void setCreateUserMobileNo(String createUserMobileNo) {
		this.createUserMobileNo = createUserMobileNo;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public int[] getStatusList() {
		return statusList;
	}
	public void setStatusList(int[] statusList) {
		this.statusList = statusList;
	}
	public Integer getLikeUserId() {
		return likeUserId;
	}
	public void setLikeUserId(Integer likeUserId) {
		this.likeUserId = likeUserId;
	}
	public String getCreateUserDepartmentName() {
		return createUserDepartmentName;
	}
	public void setCreateUserDepartmentName(String createUserDepartmentName) {
		this.createUserDepartmentName = createUserDepartmentName;
	}
	public String getCreateUserDepartmentName2() {
		return createUserDepartmentName2;
	}
	public void setCreateUserDepartmentName2(String createUserDepartmentName2) {
		this.createUserDepartmentName2 = createUserDepartmentName2;
	}
	public IdListOrTitle getIdListOrTitle() {
		return idListOrTitle;
	}
	public void setIdListOrTitle(IdListOrTitle idListOrTitle) {
		this.idListOrTitle = idListOrTitle;
	};
	//
	
}

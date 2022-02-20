package jazmin.test.driver.jdbc.domain.entity;

import java.util.Date;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
/**
 *用户
 * @author skydu
 *
 */
@Entity(tableName = "t_user")
public class User extends BaseEntity{
	//
	public static final short GENDER_男=1;
	public static final short GENDER_女=2;
	
	public static final short STATUS_在职=1;
	public static final short STATUS_离职=2;

	
	/**用户名*/
	private String userName;
	
	/**姓名*/
	private String name;
	
	/**手机号*/
	private String mobileNo;
	
	/**性别*/
	private Short gender;
	
	/**工作状态*/
	private Short status;
	
	/**年龄*/
	private Integer age;
	
	/**所属部门*/
	@ForeignKey(entityClass=Department.class)
	private Integer departmentId;
	
	/**角色列表*/
	@EntityField(columnType = ColumnType.JSONB)
	private List<Integer> roleIdList;
	
	/**最后登录时间*/
	private Date lastLoginTime;
	
	/**分表文章数*/
	private Long articleNum;
	
	/**创建人*/
	@ForeignKey(entityClass=User.class)
	private Integer createUserId;
	
	/**最后更新人*/
	@ForeignKey(entityClass=User.class)
	private Integer updateUserId;
	
	/**所属部门名称*/
	@EntityField(foreignKeyFields="departmentId",field="name",persistent = false)
	private String departmentName;
	
	private Double height;
	
	private Long no;
	
	private Boolean isStudent;
	
	private String setting;
	
	private Short[] shortArray;
	
	private Integer[] intArray;

	private Long[] longArray;

	private Float[] floatArray;

	private String[] stringArray;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<Integer> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(Long articleNum) {
		this.articleNum = articleNum;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public Short[] getShortArray() {
		return shortArray;
	}

	public void setShortArray(Short[] shortArray) {
		this.shortArray = shortArray;
	}

	public Integer[] getIntArray() {
		return intArray;
	}

	public void setIntArray(Integer[] intArray) {
		this.intArray = intArray;
	}

	public Long[] getLongArray() {
		return longArray;
	}

	public void setLongArray(Long[] longArray) {
		this.longArray = longArray;
	}

	public Float[] getFloatArray() {
		return floatArray;
	}

	public void setFloatArray(Float[] floatArray) {
		this.floatArray = floatArray;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}
}
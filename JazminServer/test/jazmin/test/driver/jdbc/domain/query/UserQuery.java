package jazmin.test.driver.jdbc.domain.query;

import java.util.List;

import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.annotations.Join;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.test.driver.jdbc.domain.entity.User;
/**
 * 
 * @author skydu
 *
 */
public class UserQuery extends Query<User>{

	@QueryField(operator = SqlOperator.LIKE)
	private String userName;
	
	@QueryField(operator = SqlOperator.LIKE)
	private String name;
	
	@QueryField(operator = SqlOperator.IS_NULL,field = "userName")
	private Boolean userNameIsNull;
	
	@QueryField
	private Integer gender;
	
	@QueryField(field ="age",operator=SqlOperator.GT)
	private Integer gtAge;
	
	@QueryField(field ="age",operator=SqlOperator.LT)
	private Integer ltAge;
	
	@QueryField(field ="status",operator=SqlOperator.IN)
	private List<Integer> statusInList;
	
	@QueryField(field ="status",operator=SqlOperator.NOT_IN)
	private List<Integer> statusNotInList;
	
	@QueryField(field ="status",operator=SqlOperator.IN)
	private Integer[] statusInList2;
	
	
	@QueryField(operator = SqlOperator.JSON_CONTAINS_ANY,field = "roleIdList")
	private Integer roleId; 
	
	@QueryField(operator = SqlOperator.JSON_CONTAINS_ANY)
	private Integer[] roleIdList; 
	
	@QueryField(operator = SqlOperator.JSON_NOT_CONTAINS_ANY,field = "roleIdList")
	private Integer notRoleId; 
	
	@QueryField(operator = SqlOperator.JSON_NOT_CONTAINS_ANY,field = "roleIdList")
	private Integer[] notRoleIdList; 
	
	@Join(table2 = User.class,table2Alias = "b1",table1Fields ={"createUserId"},table2Fields ={"id"})
	@QueryField(field = "name")
	private String createUserName;
	
	@QueryField(foreignKeyFields = "departmentId",field = "name")
	private String departmentName;
	
	@QueryField(foreignKeyFields = "departmentId",field = "name",operator = SqlOperator.LIKE)
	private String likeDepartmentName;
	
	@QueryField(field = "height",operator = SqlOperator.GE)
	private Double heightStart;
	
	@QueryField(field = "height",operator = SqlOperator.LE)
	private Double heightEnd;
	
	@QueryField
	private Boolean isStudent;
	
	@QueryField
	private Long no;
	
	@QueryField(whereSql = "a.setting is null")
	public Boolean settingIsNull;
	
	@QueryField(operator = SqlOperator.ARRAY_ANY)
	public List<Integer> intArray;
	
	@QueryField(operator = SqlOperator.ARRAY_ANY)
	public List<String> stringArray;
	
	@QueryField(operator = SqlOperator.ARRAY_CONTAINS,field = "stringArray")
	public String[] stringContains;
	
	@QueryField(operator = SqlOperator.ARRAY_NOT_CONTAINS,field = "stringArray")
	public String[] stringNotContains;

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

	public Boolean getUserNameIsNull() {
		return userNameIsNull;
	}

	public void setUserNameIsNull(Boolean userNameIsNull) {
		this.userNameIsNull = userNameIsNull;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getGtAge() {
		return gtAge;
	}

	public void setGtAge(Integer gtAge) {
		this.gtAge = gtAge;
	}

	public Integer getLtAge() {
		return ltAge;
	}

	public void setLtAge(Integer ltAge) {
		this.ltAge = ltAge;
	}

	public List<Integer> getStatusInList() {
		return statusInList;
	}

	public void setStatusInList(List<Integer> statusInList) {
		this.statusInList = statusInList;
	}

	public List<Integer> getStatusNotInList() {
		return statusNotInList;
	}

	public void setStatusNotInList(List<Integer> statusNotInList) {
		this.statusNotInList = statusNotInList;
	}

	public Integer[] getStatusInList2() {
		return statusInList2;
	}

	public void setStatusInList2(Integer[] statusInList2) {
		this.statusInList2 = statusInList2;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer[] getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(Integer[] roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Integer getNotRoleId() {
		return notRoleId;
	}

	public void setNotRoleId(Integer notRoleId) {
		this.notRoleId = notRoleId;
	}

	public Integer[] getNotRoleIdList() {
		return notRoleIdList;
	}

	public void setNotRoleIdList(Integer[] notRoleIdList) {
		this.notRoleIdList = notRoleIdList;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getLikeDepartmentName() {
		return likeDepartmentName;
	}

	public void setLikeDepartmentName(String likeDepartmentName) {
		this.likeDepartmentName = likeDepartmentName;
	}

	public Double getHeightStart() {
		return heightStart;
	}

	public void setHeightStart(Double heightStart) {
		this.heightStart = heightStart;
	}

	public Double getHeightEnd() {
		return heightEnd;
	}

	public void setHeightEnd(Double heightEnd) {
		this.heightEnd = heightEnd;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Boolean getSettingIsNull() {
		return settingIsNull;
	}

	public void setSettingIsNull(Boolean settingIsNull) {
		this.settingIsNull = settingIsNull;
	}

	public List<Integer> getIntArray() {
		return intArray;
	}

	public void setIntArray(List<Integer> intArray) {
		this.intArray = intArray;
	}

	public List<String> getStringArray() {
		return stringArray;
	}

	public void setStringArray(List<String> stringArray) {
		this.stringArray = stringArray;
	}

	public String[] getStringContains() {
		return stringContains;
	}

	public void setStringContains(String[] stringContains) {
		this.stringContains = stringContains;
	}

	public String[] getStringNotContains() {
		return stringNotContains;
	}

	public void setStringNotContains(String[] stringNotContains) {
		this.stringNotContains = stringNotContains;
	}
	
	
}

package jazmin.test.driver.jdbc.domain.query;

import jazmin.driver.jdbc.smartjdbc.annotations.QueryConditionType;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
/**
 * 
 * @author skydu
 *
 */
public class UserComplexQuery extends UserQuery{
	//
	public static class StatusAndMobile{
		@QueryField
		private Integer status;
		
		@QueryField(operator = SqlOperator.LIKE)
		private String mobileNo;

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		
		
	}
	//
	public static class NameOrUserNameOrDeptName{
		@QueryField
		private String name;
		
		@QueryField
		private String userName;
		
		@QueryField(foreignKeyFields = "departmentId",field = "name")
		private String deptName;
		
		@QueryConditionType(ConditionType.AND)
		private StatusAndMobile statusAndMobile;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public StatusAndMobile getStatusAndMobile() {
			return statusAndMobile;
		}

		public void setStatusAndMobile(StatusAndMobile statusAndMobile) {
			this.statusAndMobile = statusAndMobile;
		}
		
		//
		
	};
	
	@QueryConditionType(ConditionType.OR)
	private NameOrUserNameOrDeptName nameOrUserName;
	
	
	//
	@QueryField(operator = SqlOperator.IS_NOT_NULL)
	private Boolean nameIsNotNull;


	public NameOrUserNameOrDeptName getNameOrUserName() {
		return nameOrUserName;
	}


	public void setNameOrUserName(NameOrUserNameOrDeptName nameOrUserName) {
		this.nameOrUserName = nameOrUserName;
	}


	public Boolean getNameIsNotNull() {
		return nameIsNotNull;
	}


	public void setNameIsNotNull(Boolean nameIsNotNull) {
		this.nameIsNotNull = nameIsNotNull;
	}
	
	
}

package jazmin.test.driver.jdbc.domain.query;

import jazmin.driver.jdbc.smartjdbc.Query;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.test.driver.jdbc.domain.entity.AppModule;
import jazmin.test.driver.jdbc.domain.entity.AppServer;

import java.util.List;

/**
 * 
 * @author skydu
 *
 */
public class AppServerQuery extends Query<AppServer>{

	@QueryField(operator = SqlOperator.IN, field = "appId")
	private  List<String> appIdInList;
	
	@QueryField(operator = SqlOperator.LIKE)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAppIdInList() {
		return appIdInList;
	}

	public void setAppIdInList(List<String> appIdInList) {
		this.appIdInList = appIdInList;
	}
}

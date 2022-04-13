package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import java.util.ArrayList;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.SqlUtil;

/**
 * 
 * @author skydu
 *
 */
public class OperatorContext {
	//
	private DatabaseType databaseType;
	private Condition condition;
	private List<Object> parameters;
	//
	//
	public OperatorContext(SmartDataSource smartDataSource,DatabaseType databaseType) {
		this.databaseType=databaseType;
		parameters=new ArrayList<>();
	}
	//

	/**
	 * @return the parameters
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 
	 * @param parameter
	 */
	public void addParameter(Object parameter) {
		this.parameters.add(parameter);
	}

	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	/**
	 * 
	 * @return
	 */
	public String identifier() {
		return SqlUtil.identifier(databaseType);
	}

	/**
	 * @return the databaseType
	 */
	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	/**
	 * @param databaseType the databaseType to set
	 */
	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}
	
	

}

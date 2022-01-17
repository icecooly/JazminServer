package jazmin.driver.jdbc.smartjdbc;

import java.sql.ResultSet;

/**
 * 
 * @author skydu
 *
 */
public interface ResultSetHandler<T>{
	//
	public T handleRow(ResultSet rs) throws Exception;
}

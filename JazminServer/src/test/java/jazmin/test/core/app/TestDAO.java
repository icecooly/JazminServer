/**
 * 
 */
package jazmin.test.core.app;

import jazmin.core.app.AutoWired;
import jazmin.driver.jdbc.DruidConnectionDriver;

/**
 * @author yama
 * 31 Mar, 2015
 */
public class TestDAO {
	
	@AutoWired
	DruidConnectionDriver connectionDriver;
}

package jazmin.driver.jdbc.smartjdbc.provider.impl.nds;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlDeleteProvider;

/**
 * 
 * @author skydu
 *
 */
public class NdsDeleteProvider extends PostgresqlDeleteProvider{
	//
	public NdsDeleteProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
}

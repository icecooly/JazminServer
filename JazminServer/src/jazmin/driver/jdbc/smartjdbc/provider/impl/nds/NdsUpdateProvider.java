package jazmin.driver.jdbc.smartjdbc.provider.impl.nds;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlUpdateProvider;

/**
 * 
 * @author skydu
 *
 */
public class NdsUpdateProvider extends PostgresqlUpdateProvider{

	public NdsUpdateProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
}

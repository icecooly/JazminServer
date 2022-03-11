package jazmin.driver.jdbc.smartjdbc.provider.impl.nds;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlInsertProvider;

/**
 * 
 * @author skydu
 *
 */
public class NdsInsertProvider extends PostgresqlInsertProvider{

	public NdsInsertProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
}

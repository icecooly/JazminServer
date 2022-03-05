package jazmin.driver.jdbc.smartjdbc.provider.factory;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.UpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase.KingbaseUpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.mysql.MysqlUpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.nds.NdsUpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlUpdateProvider;

/**
 * 
 * @author skydu
 *
 */
public class UpdateProviderFactory {
	//
	public static UpdateProvider create(SmartDataSource smartDataSource) {
		DatabaseType type=smartDataSource.getDatabaseType();
		if(type.equals(DatabaseType.MYSQL)) {
			return new MysqlUpdateProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.POSTGRESQL)) {
			return new PostgresqlUpdateProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.KINGBASE)) {
			return new KingbaseUpdateProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.NDS)) {
			return new NdsUpdateProvider(smartDataSource);
		}
		throw new RuntimeException("unspoort database type "+type);
	}
}

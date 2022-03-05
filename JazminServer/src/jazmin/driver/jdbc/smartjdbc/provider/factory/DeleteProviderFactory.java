package jazmin.driver.jdbc.smartjdbc.provider.factory;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.DeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase.KingbaseDeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.mysql.MysqlDeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.nds.NdsDeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlDeleteProvider;

/**
 * 
 * @author skydu
 *
 */
public class DeleteProviderFactory {
	//
	public static DeleteProvider create(SmartDataSource smartDataSource) {
		DatabaseType type=smartDataSource.getDatabaseType();
		if(type.equals(DatabaseType.MYSQL)) {
			return new MysqlDeleteProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.POSTGRESQL)) {
			return new PostgresqlDeleteProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.KINGBASE)) {
			return new KingbaseDeleteProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.NDS)) {
			return new NdsDeleteProvider(smartDataSource);
		}
		throw new RuntimeException("unspoort database type "+type);
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.factory;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase.KingbaseSelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.mysql.MysqlSelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.nds.NdsSelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlSelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.sqlserver.SqlServerSelectProvider;

/**
 * 
 * @author skydu
 *
 */
public class SelectProviderFactory {
	//
	public static SelectProvider create(SmartDataSource smartDataSource) {
		DatabaseType type=smartDataSource.getDatabaseType();
		if(type.equals(DatabaseType.MYSQL)) {
			return new MysqlSelectProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.POSTGRESQL)) {
			return new PostgresqlSelectProvider(smartDataSource);
		}
		if(DatabaseType.SQLSERVER.equals(type)) {
			return new SqlServerSelectProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.KINGBASE)) {
			return new KingbaseSelectProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.NDS)) {
			return new NdsSelectProvider(smartDataSource);
		}
		throw new RuntimeException("unspoort database type "+type);
	}
}

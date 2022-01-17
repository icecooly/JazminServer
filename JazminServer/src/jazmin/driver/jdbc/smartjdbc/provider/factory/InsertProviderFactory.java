package jazmin.driver.jdbc.smartjdbc.provider.factory;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.InsertProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase.KingbaseInsertProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.mysql.MysqlInsertProvider;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlInsertProvider;

/**
 * 
 * @author skydu
 *
 */
public class InsertProviderFactory {
	//
	public static InsertProvider create(SmartDataSource smartDataSource) {
		DatabaseType type=smartDataSource.getDatabaseType();
		if(type.equals(DatabaseType.MYSQL)) {
			return new MysqlInsertProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.POSTGRESQL)) {
			return new PostgresqlInsertProvider(smartDataSource);
		}
		if(type.equals(DatabaseType.KINGBASE)) {
			return new KingbaseInsertProvider(smartDataSource);
		}
		throw new RuntimeException("unspoort database type "+type);
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.impl.mysql;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;

/**
 * 
 * @author skydu
 *
 */
public class MysqlSelectProvider extends SelectProvider{
	//
	public MysqlSelectProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}

	@Override
	protected String getForUpdateSql() {
		if(!qw.isForUpdate()) {
			return "";
		}
		// mysql notwait need mysql.version 8 +
		String sql=super.getForUpdateSql();
		if (qw.isForUpdateNoWait()) {
			sql += " nowait ";
		}
		return sql;
	}
	
}

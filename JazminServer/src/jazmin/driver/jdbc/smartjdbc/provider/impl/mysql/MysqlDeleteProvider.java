package jazmin.driver.jdbc.smartjdbc.provider.impl.mysql;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.DeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.WhereSqlBuilder;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere.WhereStatment;

/**
 * 
 * @author skydu
 *
 */
public class MysqlDeleteProvider extends DeleteProvider{

	public MysqlDeleteProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	
	@Override
	public SqlBean build() {
		StringBuilder sql=new StringBuilder();
		String tableName=addIdentifier(delete.tableName);
		sql.append("delete "+MAIN_TABLE_ALIAS+" from ").append(tableName).append(" ").append(MAIN_TABLE_ALIAS).append(" ");
		WhereStatment ws=new WhereSqlBuilder(smartDataSource, getDatabaseType(),queryWhere).build();
		sql.append(ws.sql);
		return SqlBean.build(sql.toString(),ws.values);
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.DeleteProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere.WhereStatment;
import jazmin.driver.jdbc.smartjdbc.provider.where.WhereSqlBuilder;

/**
 * 
 * @author skydu
 *
 */
public class KingbaseDeleteProvider extends DeleteProvider{
	//
	public KingbaseDeleteProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	//
	@Override
	public SqlBean build() {
		StringBuilder sql=new StringBuilder();
		String tableName=addIdentifier(delete.tableName);
		sql.append("delete from ").append(tableName).append(" ").append(MAIN_TABLE_ALIAS).append(" ");
		WhereStatment ws=new WhereSqlBuilder(smartDataSource,getDatabaseType(),queryWhere).build();
		sql.append(ws.sql);
		return SqlBean.build(sql.toString(),ws.values);
	}
}

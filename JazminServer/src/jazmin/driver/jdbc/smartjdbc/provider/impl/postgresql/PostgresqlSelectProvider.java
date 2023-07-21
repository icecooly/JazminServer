package jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;

/**
 * 
 * @author skydu
 *
 */
public class PostgresqlSelectProvider extends SelectProvider{
	//
	//
	public PostgresqlSelectProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	
	@Override
	protected String getLimitSql() {
		if(isSelectCount) {
			return "";
		}
		if(!needPaging) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		addPaging();	
		if(qw.getLimitEnd()!=-1) {
			sql.append("\nlimit ").append(qw.getLimitEnd()).append(" offset ").append(qw.getLimitStart()).append(" ");
		}
		return sql.toString();
	}
	
	@Override
	protected String getForUpdateSql() {
		if(!qw.isForUpdate()) {
			return "";
		}
		String sql=super.getForUpdateSql();
		
		if(qw.getOf()==null) {
			sql+="\nof "+SqlProvider.MAIN_TABLE_ALIAS;
		}else {
			sql+="\nof "+qw.getOf();
		}
		if (qw.isForUpdateNoWait()) {
			sql += " nowait ";
		}
		return sql;
	}
}

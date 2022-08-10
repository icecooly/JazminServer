package jazmin.driver.jdbc.smartjdbc.provider.impl.sqlserver;

import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;

/**
 * 
 * @author liutao
 *
 */
public class SqlServerBean extends SqlBean {

	public String selectSql;// select a.*,b.name ....

	public String fromSql;// from t_bug a inner join t_user i0
	
	public String joinSql;// inner join t_user i0

	public String whereSql;// where ...

	public String groupBySql;// group by ...

	public String orderBySql;// order by ...

	public String limitSql;// limit 0,10

	public String forUpdateSql;// for update
	
	//
	public String toSql() {
		StringBuilder sql=new StringBuilder();
		sql.append(selectSql);
		sql.append(fromSql);
		if(joinSql!=null) {
			sql.append(joinSql);
		}
		if(forUpdateSql!=null) {
			sql.append(forUpdateSql);
		}
		if(whereSql!=null) {
			sql.append(whereSql);
		}
		if(groupBySql!=null) {
			sql.append(groupBySql);
		}
		if(orderBySql!=null) {
			sql.append(orderBySql);
		}
		if(limitSql!=null) {
			sql.append(limitSql);
		}
		return sql.toString();
	}
}

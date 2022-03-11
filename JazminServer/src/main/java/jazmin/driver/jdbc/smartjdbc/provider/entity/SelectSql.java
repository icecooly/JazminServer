package jazmin.driver.jdbc.smartjdbc.provider.entity;

/**
 * 
 * @author skydu
 *
 */
public class SelectSql extends SqlBean{

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
		StringBuffer sql=new StringBuffer();
		sql.append(selectSql);
		sql.append(fromSql);
		if(joinSql!=null) {
			sql.append(joinSql);
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
		if(forUpdateSql!=null) {
			sql.append(forUpdateSql);
		}
		return sql.toString();
	}
}

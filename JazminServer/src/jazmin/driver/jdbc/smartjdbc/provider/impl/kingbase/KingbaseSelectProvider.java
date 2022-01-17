package jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.cache.EntityFieldInfo;
import jazmin.driver.jdbc.smartjdbc.enums.JoinType;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Join;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class KingbaseSelectProvider extends SelectProvider{
	//
	//
	public KingbaseSelectProvider(SmartDataSource smartDataSource) {
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
		return sql;
	}
	
	@Override
	protected void addSelectFields(StringBuilder sql) {
		for (EntityFieldInfo field : selectFields) {
			if(StringUtil.isEmpty(field.statFunction)) {
				if(qw.isForUpdate()&&!field.tableAlias.equals(SqlProvider.MAIN_TABLE_ALIAS)){
					continue;
				}
			}
			sql.append(getSelectFieldSql(field));
			sql.append(",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" ");
	}
	
	@Override
	protected String getFromSql() {
		StringBuilder sql=new StringBuilder();
		sql.append("\nfrom ").append(getTableName(entityClass)).append(" ").
				append(MAIN_TABLE_ALIAS).append(" ");
		//join
		createJoins();
		if(joins!=null) {
			for (Join join : joins.getJoinList()) {
				if(qw.isForUpdate()) {
					if(join.joinType.equals(JoinType.LEFT_JOIN)||
							join.joinType.equals(JoinType.RIGHT_JOIN)
							) {
						continue;
					}
				}
				if(join.joinType.equals(JoinType.INNER_JOIN)) {
					sql.append("\ninner join  ");
				}
				if(join.joinType.equals(JoinType.LEFT_JOIN)) {
					sql.append("\nleft join  ");
				}
				if(join.joinType.equals(JoinType.RIGHT_JOIN)) {
					sql.append("\nright join  ");
				}
				addJoinSql(sql, join);
			}
		}
		return sql.toString();
		
	}
}

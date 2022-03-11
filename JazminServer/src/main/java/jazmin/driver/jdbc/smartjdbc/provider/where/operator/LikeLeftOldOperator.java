package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class LikeLeftOldOperator extends ColumnOperator{

	public LikeLeftOldOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "like";
	}
	
	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		if(c.key==null||c.value==null) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		sql.append(getColumnSql());
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append(" ");
		String v=c.value.toString().replaceAll("'", "''");
		sql.append("concat('%',"+"'"+v+"')");
		sql.append(" ");
		return sql.toString();
	}
}

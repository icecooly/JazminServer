package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class NotLikeRightOldOperator extends ColumnOperator{

	public NotLikeRightOldOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "not like";
	}
	
	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		if(c.key==null) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		String columnSql=getColumnSql();
		sql.append("(");
		sql.append(columnSql);
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append(" ");
		String v=c.value.toString().replaceAll("'", "''");
		sql.append("concat("+"'"+v+"','%')");
		sql.append(" ");
		sql.append(" or ");
		sql.append(getColumnSql());
		sql.append(" is null");
		sql.append(")");
		return sql.toString();
	}
}

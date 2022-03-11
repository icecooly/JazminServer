package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class NeOperator extends ColumnOperator{

	public NeOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "<>";
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
		sql.append(getValueSql());
		sql.append(" ");
		sql.append(" or ");
		sql.append(getColumnSql());
		sql.append(" is null");
		sql.append(")");
		ctx.addParameter(c.value);
		return sql.toString();
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class NotInOperator extends ColumnOperator{
	//
	public NotInOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "not in";
	}

	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		String column=c.key;
		Object value=c.value;
		if(column==null||value==null) {
			return "";
		}
		Object[] values=ArrayUtils.convert(value);
		if(values==null||values.length==0) {
			return "";
		}
		String columnSql=getColumnSql();
		StringBuilder sql=new StringBuilder();
		sql.append("(");
		sql.append(columnSql);
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append("( ");
		for (int i = 0; i < values.length; i++) {
			sql.append(" ?,");
			ctx.addParameter(values[i]);
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		sql.append(" ");
		sql.append(" or ");
		sql.append(columnSql);
		sql.append(" is null");
		sql.append(")");
		return sql.toString();
	}
}

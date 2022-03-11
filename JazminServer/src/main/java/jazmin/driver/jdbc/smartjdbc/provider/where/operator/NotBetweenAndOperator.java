package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class NotBetweenAndOperator extends ColumnOperator{

	public NotBetweenAndOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "";
	}

	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		String column = c.key;
		Object value = c.value;
		if (column == null || value == null) {
			return "";
		}
		Object[] values = ArrayUtils.convert(value);
		if(value==null||values.length!=2) {
			return "";
		}
		StringBuilder sql = new StringBuilder();
		//
		String columnSql=getColumnSql();
		sql.append("(");
		sql.append(columnSql);
		sql.append(" not between ? and ? ");
		sql.append(" or ");
		sql.append(columnSql);
		sql.append(" is null");
		sql.append(")");
		ctx.addParameter(values[0]);
		ctx.addParameter(values[1]);
		
		return sql.toString();
	}


}

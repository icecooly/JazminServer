package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class ArrayAnyOperator extends ColumnOperator {

	public ArrayAnyOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "";
	}

	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		DatabaseType type = ctx.getDatabaseType();
		String column = c.key;
		Object value = c.value;
		if (column == null || value == null) {
			return "";
		}
		Object[] values = ArrayUtils.convert(value);
		if(values==null||values.length==0) {
			return "";
		}
		StringBuilder sql = new StringBuilder();
		//
		if (type.equals(DatabaseType.POSTGRESQL)||
				type.equals(DatabaseType.KINGBASE)) {
			sql.append("( ");
			for (int i = 0; i < values.length; i++) {
				Object v=values[i];
				ctx.addParameter(v);
				sql.append("?=any(").append(getColumnSql()).append(")");
				if (i != (values.length - 1)) {
					sql.append(" or ");
				}
			}
			sql.append(") ");
		}
		return sql.toString();
	}

}

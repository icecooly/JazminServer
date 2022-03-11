package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class ArrayNotContainsOperator extends ColumnOperator {

	public ArrayNotContainsOperator(OperatorContext ctx) {
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
		if(value==null||values.length==0) {
			return "";
		}
		StringBuilder sql = new StringBuilder();
		//
		if (isBasePostgresql(type)) {
			sql.append("( not ");
			ctx.addParameter(value);
			sql.append(getColumnSql()).append("@>").append("?");
			sql.append(") ");
		}
		return sql.toString();
	}

}

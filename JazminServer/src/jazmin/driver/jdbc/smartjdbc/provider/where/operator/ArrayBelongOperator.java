package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class ArrayBelongOperator extends ColumnOperator {

	public ArrayBelongOperator(OperatorContext ctx) {
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
			sql.append("( ");
			ctx.addParameter(value);
			String columnSql=getColumnSql();
			sql.append(columnSql).append("<@").append("?");
			sql.append(" and array_length(").append(columnSql).append(")>=1 ");
			sql.append(") ");
		}
		return sql.toString();
	}

}

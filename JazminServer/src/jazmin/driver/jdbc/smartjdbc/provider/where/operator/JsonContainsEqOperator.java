package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.JsonContain;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;

/**
 * 
 * @author skydu
 *
 */
public class JsonContainsEqOperator extends ColumnOperator {

	public JsonContainsEqOperator(OperatorContext ctx) {
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
		JsonContain jsonContain=c.jsonContain;
		StringBuilder sql = new StringBuilder();
		if (type.equals(DatabaseType.MYSQL)) {
			sql.append("( ");
			sql.append("json_length(").append(getColumnSql()).append(")="+values.length);
			for (int i = 0; i < values.length; i++) {
				sql.append(" and ");
				if(jsonContain==null||jsonContain.objectField==null) {
					sql.append(" json_contains(").append(getColumnSql()).append(",JSON_ARRAY(?)").append(") ");
				}else {
					sql.append(" json_contains(").append(getColumnSql()).append(",JSON_OBJECT('"+jsonContain.objectField+"',?)").append(") ");
				}
				ctx.addParameter(values[i]);
			}
			sql.append(") ");
		}
		if (type.equals(DatabaseType.POSTGRESQL)||
				type.equals(DatabaseType.KINGBASE)) {
			sql.append("( ");
			sql.append("jsonb_array_length(").append(getColumnSql()).append(")="+values.length);
			for (int i = 0; i < values.length; i++) {
				sql.append(" and ");
				Object v=values[i];
				if(jsonContain==null||jsonContain.objectField==null) {
					if(v instanceof String) {
						sql.append(getColumnSql()).append("@>'\""+values[i]+"\"'");
					}else {
						sql.append(getColumnSql()).append("@>'"+values[i]+"'");
					}
				}else {
					sql.append(getColumnSql()).append("@>'[{\""+jsonContain.objectField+"\":\""+values[i]+"\"}]'");
				}
			}
			sql.append(") ");
		}
		return sql.toString();
	}

}

package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.JsonContain;
import jazmin.driver.jdbc.smartjdbc.util.ArrayUtils;
import jazmin.util.JSONUtil;

/**
 * 
 * @author skydu
 *
 */
public class JsonBelongOperator extends ColumnOperator {

	public JsonBelongOperator(OperatorContext ctx) {
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
		if (isBasePostgresql(type)) {
			sql.append("( ");
			if(jsonContain==null||jsonContain.objectField==null) {
				sql.append(getColumnSql()).append("<@'"+JSONUtil.toJson(values)+"'");
			}else {
				sql.append("jsonb_id_to_array("+getColumnSql()+")").append("<@'"+JSONUtil.toJson(values)+"'");
			}
			sql.append(") ");
		}
		return sql.toString();
	}

}

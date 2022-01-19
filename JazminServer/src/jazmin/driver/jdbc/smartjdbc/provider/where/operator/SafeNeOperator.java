package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class SafeNeOperator extends ColumnOperator{

	public SafeNeOperator(OperatorContext ctx) {
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
		DatabaseType type = ctx.getDatabaseType();
		if (type.equals(DatabaseType.POSTGRESQL)||
				type.equals(DatabaseType.KINGBASE)) {
			sql.append(getColumnSql());
			sql.append(" ");
			sql.append("is distinct from");
			sql.append(" ");
			sql.append(getValueSql());
			sql.append(" ");
			ctx.addParameter(c.value);
		}else if (type.equals(DatabaseType.MYSQL)){
			sql.append("(");
			sql.append(getColumnSql());
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
		}
		return sql.toString();
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class IsNotNullOperator extends ColumnOperator{

	public IsNotNullOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "is not null";
	}
	
	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		String column=c.key;
		if(column==null) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		sql.append(getColumnSql());
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append(" ");
		return sql.toString();
	}

}

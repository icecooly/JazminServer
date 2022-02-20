package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class LikeOldOperator extends ColumnOperator{

	public LikeOldOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "like";
	}
	
	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		if(c.key==null||c.value==null) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		sql.append(getColumnSql());
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append(" ");
		sql.append("concat('%',"+"'"+c.value.toString()+"','%')");
		sql.append(" ");
		return sql.toString();
	}

}

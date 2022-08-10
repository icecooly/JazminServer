package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public abstract class ColumnOperator extends Operator{
	
	public ColumnOperator(OperatorContext ctx) {
		super(ctx);
	}
	//
	/**
	 * 
	 * @return
	 */
	public abstract String getOperatorSql();
	/**
	 * 
	 */
	@Override
	public String build() {
		Condition c=getCtx().getCondition();
		if(c.key==null) {
			return "";
		}
		StringBuilder sql=new StringBuilder();
		sql.append(getColumnSql());
		sql.append(" ");
		sql.append(getOperatorSql());
		sql.append(" ");
		sql.append(getValueSql());
		sql.append(" ");
		ctx.addParameter(c.value);
		return sql.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getColumnSql() {
		StringBuilder sql=new StringBuilder();
		Condition c=getCtx().getCondition();
		if(c.isColumn) {
			if(c.alias!=null) {
				sql.append(c.alias).append(".");
			}
			sql.append(ctx.addIdentifier(c.key));
		}else {
			sql.append(c.key);
		}
		if(c.keyFunc!=null) {
			String columnSql=sql.toString();
			sql=new StringBuilder();
			sql.append(c.keyFunc);
			sql.append("(");
			sql.append(columnSql);
			sql.append(") ");
		}
		if(c.keyCast!=null) {
			sql.append(c.keyCast);
		}
		return sql.toString();
	}

	/**
	 * 
	 * @return
	 */
	protected String getValueSql() {
		return "?";
	}
}

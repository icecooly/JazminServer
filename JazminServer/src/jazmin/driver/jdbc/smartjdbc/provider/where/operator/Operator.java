package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import jazmin.driver.jdbc.smartjdbc.SmartJdbcUtils;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;

/**
 * 
 * @author skydu
 *
 */
public abstract class Operator {
	//
	protected OperatorContext ctx;
	//
	public Operator(OperatorContext ctx) {
		this.ctx=ctx;
	}
	//
	public abstract String build();
	//
	/**
	 * @return the ctx
	 */
	public OperatorContext getCtx() {
		return ctx;
	}
	/**
	 * @param ctx the ctx to set
	 */
	public void setCtx(OperatorContext ctx) {
		this.ctx = ctx;
	}
	/**
	 * 
	 * @param type
	 * @return
	 */
	public boolean isBasePostgresql(DatabaseType type) {
		return SmartJdbcUtils.isBasePostgresql(type);
	}
}

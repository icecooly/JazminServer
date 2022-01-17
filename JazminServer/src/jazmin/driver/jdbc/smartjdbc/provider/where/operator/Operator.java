package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

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
	
}

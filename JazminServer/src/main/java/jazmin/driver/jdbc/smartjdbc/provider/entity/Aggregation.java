package jazmin.driver.jdbc.smartjdbc.provider.entity;

/**
 * 
 * @author skydu
 *
 */
public class Aggregation {
	/** 聚合函数 count,avg,sum,max,min */
	public String func;// AVG([DISTINCT] expr)
	/** 可能是公式 */
	public String expr;
	/** 是否去重 */
	public boolean distinct;
	/***/
	public String asName;
}
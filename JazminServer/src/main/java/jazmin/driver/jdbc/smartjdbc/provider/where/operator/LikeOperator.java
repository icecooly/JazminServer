package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

/**
 * 
 * @author skydu
 *
 */
public class LikeOperator extends ColumnOperator{

	public LikeOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "like";
	}
	
	@Override
	protected String getValueSql() {
		return "concat('%',?,'%')";
	}
	

}

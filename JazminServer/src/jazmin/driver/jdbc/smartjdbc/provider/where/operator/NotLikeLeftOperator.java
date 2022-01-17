package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

/**
 * 
 * @author skydu
 *
 */
public class NotLikeLeftOperator extends ColumnOperator{

	public NotLikeLeftOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "not like";
	}

	@Override
	protected String getValueSql() {
		return "concat('%',?)";
	}
}

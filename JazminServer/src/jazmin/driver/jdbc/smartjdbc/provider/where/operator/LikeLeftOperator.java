package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

/**
 * 
 * @author skydu
 *
 */
public class LikeLeftOperator extends ColumnOperator{

	public LikeLeftOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		if(isBasePostgresql(ctx.getDatabaseType())) {
			return "ilike";
		}
		return "like";
	}

	@Override
	protected String getValueSql() {
		return " concat('%',?) ";
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

/**
 * 
 * @author skydu
 *
 */
public class EqOperator extends ColumnOperator{

	public EqOperator(OperatorContext ctx) {
		super(ctx);
	}

	@Override
	public String getOperatorSql() {
		return "=";
	}

}

package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;

/**
 * 
 * @author skydu
 *
 */
public class OperatorBuilder {
	//
	private static Map<String, Operator> customOperators=new ConcurrentHashMap<>();
	//
	private static Logger logger=LoggerFactory.getLogger(OperatorBuilder.class);

	
	/**
	 * 
	 * @param ctx
	 * @return
	 */
	public static Operator build(OperatorContext ctx) {
		Condition w=ctx.getCondition();
		SqlOperator operator=w.operator;
		DatabaseType databaseType=ctx.getDatabaseType();
		if(operator==null) {
			operator=SqlOperator.EQ;//default
		}
		if(operator.equals(SqlOperator.CUSTOM)) {
			return getCustomOperator(w);
		}
		if(operator.equals(SqlOperator.EQ)) {
			return new EqOperator(ctx);
		}
		if(operator.equals(SqlOperator.NE)) {
			return new NeOperator(ctx);
		}
		if(operator.equals(SqlOperator.IN)) {
			return new InOperator(ctx);
		}
		if(operator.equals(SqlOperator.NOT_IN)) {
			return new NotInOperator(ctx);
		}
		if(operator.equals(SqlOperator.LIKE)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new LikeOldOperator(ctx);
			}
			return new LikeOperator(ctx);
		}
		if(operator.equals(SqlOperator.NOT_LIKE)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new NotLikeOldOperator(ctx);
			}
			return new NotLikeOperator(ctx);
		}
		if(operator.equals(SqlOperator.LIKE_LEFT)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new LikeLeftOldOperator(ctx);
			}
			return new LikeLeftOperator(ctx);
		}
		if(operator.equals(SqlOperator.LIKE_RIGHT)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new LikeRightOldOperator(ctx);
			}
			return new LikeRightOperator(ctx);
		}
		if(operator.equals(SqlOperator.NOT_LIKE_LEFT)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new NotLikeLeftOldOperator(ctx);
			}
			return new NotLikeLeftOperator(ctx);
		}
		if(operator.equals(SqlOperator.NOT_LIKE_RIGHT)) {
			if(databaseType.equals(DatabaseType.NDS)) {
				return new NotLikeRightOldOperator(ctx);
			}
			return new NotLikeRightOperator(ctx);
		}
		if(operator.equals(SqlOperator.GT)) {
			return new GtOperator(ctx);
		}
		if(operator.equals(SqlOperator.GE)) {
			return new GeOperator(ctx);
		}
		if(operator.equals(SqlOperator.LT)) {
			return new LtOperator(ctx);
		}
		if(operator.equals(SqlOperator.LE)) {
			return new LeOperator(ctx);
		}
		if(operator.equals(SqlOperator.IS_NULL)) {
			return new IsNullOperator(ctx);
		}
		if(operator.equals(SqlOperator.IS_NOT_NULL)) {
			return new IsNotNullOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_CONTAINS_ANY)) {
			return new JsonContainsAnyOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_NOT_CONTAINS_ANY)) {
			return new JsonNotContainsAnyOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_CONTAINS_ALL)) {
			return new JsonContainsAllOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_CONTAINS_EQ)) {
			return new JsonContainsEqOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_CONTAINS_NE)) {
			return new JsonContainsNeOperator(ctx);
		}
		if(operator.equals(SqlOperator.ARRAY_ANY)) {
			return new ArrayAnyOperator(ctx);
		}
		if(operator.equals(SqlOperator.ARRAY_NOT_ANY)) {
			return new ArrayNotAnyOperator(ctx);
		}
		if(operator.equals(SqlOperator.ARRAY_CONTAINS)) {
			return new ArrayContainsOperator(ctx);
		}
		if(operator.equals(SqlOperator.ARRAY_NOT_CONTAINS)) {
			return new ArrayNotContainsOperator(ctx);
		}
		if(operator.equals(SqlOperator.BETWEEN_AND)) {
			return new BetweenAndOperator(ctx);
		}
		if(operator.equals(SqlOperator.NOT_BETWEEN_AND)) {
			return new NotBetweenAndOperator(ctx);
		}
		if(operator.equals(SqlOperator.JSON_BELONG)) {
			return new JsonBelongOperator(ctx);
		}
		if(operator.equals(SqlOperator.ARRAY_BELONG)) {
			return new ArrayBelongOperator(ctx);
		}
		return null;
	}
	//
	private static Operator getCustomOperator(Condition w) {
		if(w.customOperator==null) {
			throw new SmartJdbcException("no custom operator found ");
		}
		Operator operator=customOperators.get(w.customOperator);
		if(operator==null) {
			throw new SmartJdbcException("no custom operator found "+w.customOperator);
		}
		return operator;
	}
	//
	public static void registerCustomOperator(String customOperator, Operator operator) {
		if(customOperator==null||operator==null) {
			throw new IllegalArgumentException();
		}
		customOperators.put(customOperator, operator);
		logger.info("registerCustomOperator {}",customOperator);
	}
}

package jazmin.driver.jdbc.smartjdbc.provider.where;

import java.util.LinkedList;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere.WhereStatment;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.provider.where.operator.Operator;
import jazmin.driver.jdbc.smartjdbc.provider.where.operator.OperatorBuilder;
import jazmin.driver.jdbc.smartjdbc.provider.where.operator.OperatorContext;
import jazmin.driver.jdbc.smartjdbc.provider.where.operator.WhereSqlOperator;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class WhereSqlBuilder {
	//
	private QueryWhere queryWhere;
	private DatabaseType databaseType;
	private StringBuilder sql;
	private List<Object> values;
	//
	public WhereSqlBuilder(DatabaseType databaseType, QueryWhere queryWhere) {
		this.databaseType=databaseType;
		this.queryWhere=queryWhere;
		this.sql=new StringBuilder();
		this.values=new LinkedList<Object>();
	}
	//

	/**
	 * 
	 * @return
	 */
	public WhereStatment build(){
		WhereStatment statment=new WhereStatment();
		sql.append("\nwhere 1=1 ");
		appendWhereSql(queryWhere.where, null);
		sql.append(" ");
		statment.sql=sql.toString();
		statment.values=values.toArray();
		return statment;
	}
	
	/**
	 * 
	 * @param conditionType
	 * @return
	 */
	private String getConditionTypeSql(ConditionType conditionType) {
		return " "+conditionType.name().toLowerCase()+" ";
	}
	
	/**
	 * 
	 * @param w
	 * @return
	 */
	private boolean haveCondition(Where w) {
		if (w != null && w.conditionList != null && w.conditionList.size() > 0) {
			return true;
		}
		if (w != null && w.children != null) {
			for (Where child : w.children) {
				boolean childHaveCondition = haveCondition(child);
				if (childHaveCondition) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param where
	 * @param parentWhere
	 */
	public void appendWhereSql(Where where, Where parentWhere) {
		boolean haveCondition = haveCondition(where);
		if (!haveCondition) {
			return;
		}
		ConditionType type=ConditionType.AND;
		if(parentWhere!=null) {
			type=parentWhere.conditionType;	
		}
		sql.append(getConditionTypeSql(type)).append("( ");//and ( 或 or(
		//
		List<Condition> conditions = where.conditionList;
		int conditonCount=0;
		if (conditions.size() > 0) {
			int index = 0;
			OperatorContext ctx = new OperatorContext(databaseType);
			ctx.setParameters(values);
			String conditionType=getConditionTypeSql(where.conditionType);
			for (Condition c : conditions) {
				String operatorSql = null;
				if (c.key != null) {
					ctx.setCondition(c);
					Operator operator = OperatorBuilder.build(ctx);
					operatorSql = operator.build();
				} else if (c.whereSql != null) {
					WhereSqlOperator whereSqlOperator = new WhereSqlOperator(ctx, c);
					operatorSql = whereSqlOperator.build();
				}
				if (!StringUtil.isEmpty(operatorSql)) {
					conditonCount++;
					if (index > 0) {
						sql.append(conditionType);
					}
					sql.append(operatorSql);
					index++;
				}
			} // for
		}
		if (conditonCount==0) {
			if(where.conditionType.equals(ConditionType.AND)) {
				sql.append(" 1=1  ");
			}else {
				sql.append(" 1=2  ");
			}
		}
		//
		if (where.children != null && where.children.size() > 0) {
			for (Where w : where.children) {
				appendWhereSql(w, where);
			}
		}
		//
		sql.append("  )  ");
	}
}

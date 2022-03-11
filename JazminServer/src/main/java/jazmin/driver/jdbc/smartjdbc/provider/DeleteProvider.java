package jazmin.driver.jdbc.smartjdbc.provider;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityDelete;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;

/**
 * 
 * @author skydu
 *
 */
public abstract class DeleteProvider extends SqlProvider{
	//
	public DeleteProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	//
	protected EntityDelete delete;
	protected QueryWhere queryWhere;
	//
	public DeleteProvider delete(EntityDelete delete) {
		this.delete=delete;
		return this;
	}
	
	public DeleteProvider queryWhere(QueryWhere queryWhere) {
		this.queryWhere=queryWhere;
		return this;
	}
}

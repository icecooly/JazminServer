package jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.provider.UpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate.EntityUpdateField;

/**
 * 
 * @author skydu
 *
 */
public class PostgresqlUpdateProvider extends UpdateProvider{

	public PostgresqlUpdateProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	
	@Override
	public String getValueSql(EntityUpdateField field) {
		String sql="?";
		if(field!=null&&field.columnType!=null) {
			if(field.columnType.equals(ColumnType.JSONB)) {
				sql+="::jsonb"; 
			}
		}
		return sql+",";
	}
}

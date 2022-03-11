package jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.provider.InsertProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityInsert.EntityInsertField;

/**
 * 
 * @author skydu
 *
 */
public class PostgresqlInsertProvider extends InsertProvider{

	public PostgresqlInsertProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	
	@Override
	public String getValueSql(EntityInsertField field) {
		String sql="?";
		if(field!=null&&field.columnType!=null) {
			if(field.columnType.equals(ColumnType.JSONB)) {
				sql+="::jsonb"; 
			}
		}
		return sql+",";
	}
}

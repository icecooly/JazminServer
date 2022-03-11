package jazmin.driver.jdbc.smartjdbc.provider.impl.kingbase;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.provider.UpdateProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate.EntityUpdateField;

/**
 * 
 * @author skydu
 *
 */
public class KingbaseUpdateProvider extends UpdateProvider{

	public KingbaseUpdateProvider(SmartDataSource smartDataSource) {
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

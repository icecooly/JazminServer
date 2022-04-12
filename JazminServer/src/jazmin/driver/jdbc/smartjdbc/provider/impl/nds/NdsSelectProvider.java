package jazmin.driver.jdbc.smartjdbc.provider.impl.nds;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.cache.EntityFieldInfo;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.provider.impl.postgresql.PostgresqlSelectProvider;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class NdsSelectProvider extends PostgresqlSelectProvider {
	//
	//
	public NdsSelectProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}

	@Override
	protected String getSelectFieldSql(EntityFieldInfo field) {
		StringBuilder sql = new StringBuilder();
		if (field.distinct) {
			sql.append(" distinct ");
		}
		if (StringUtil.isEmpty(field.statFunction)) {
			sql.append(field.tableAlias).append(".");
			sql.append(addIdentifier(convertFieldName(field.name))).append("");
			if (field.entityField != null && field.entityField.columnType().equals(ColumnType.JSONB)) {
				sql.append("::text");
			}
			if (field.entityField != null && (
					field.entityField.columnType().equals(ColumnType.VARCHAR_ARRAY)||
					field.entityField.columnType().equals(ColumnType.TEXT_ARRAY)||
					field.entityField.columnType().equals(ColumnType.INT_ARRAY)||
					field.entityField.columnType().equals(ColumnType.FLOAT_ARRAY)
					)) {
				sql.append("::text");
			}
		} else {
			sql.append(field.statFunction);
		}
		if (field.asName != null) {
			sql.append(" as ").append(addIdentifier(convertFieldName(field.asName)));
		}
		return sql.toString();
	}

}

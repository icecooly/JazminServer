package jazmin.driver.jdbc.smartjdbc.provider.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;

/**
 * 
 * @author skydu
 *
 */
public class EntityInsert extends Entity{
	//
	public boolean withGenerateKey;
	public List<EntityInsertField> fieldList;
	//
	public EntityInsert(DatabaseType databaseType, String tableName) {
		super(databaseType, tableName);
		this.fieldList=new ArrayList<>();
	}
	//
	public static class EntityInsertField{
		public String column;
		public Object value;
		public ColumnType columnType;
		//
		public EntityInsertField(ColumnType columnType, String column,Object value) {
			this.columnType=columnType;
			this.column=column;
			this.value=value;
		}
	}
	//
	public static EntityInsert create(SmartDataSource datasource, Object bean, boolean withGenerateKey, String... excludeFields) {
		EntityInsert insert=new EntityInsert(datasource.getDatabaseType(), datasource.getTableName(bean.getClass()));
		insert.withGenerateKey=withGenerateKey;
		Set<String> excludesNames = new TreeSet<String>();
		if(excludeFields!=null) {
			for (String e : excludeFields) {
				excludesNames.add(e);
			}
		}
		Class<?>type=bean.getClass();
		List<Field> list=ClassUtils.getPersistentFields(type);
		for (Field f : list) {
			if (excludesNames.contains(f.getName())) {
				continue;
			}
			if(!ClassUtils.isPersistentField(f)) {
				continue;
			}
			EntityField entityField=f.getAnnotation(EntityField.class);
			if(entityField!=null&&entityField.autoIncrement()) {
				continue;
			}
			ColumnType columnType=null;
			if(entityField!=null) {
				columnType=entityField.columnType();
			}
			String column = datasource.convertFieldName(f.getName());
			try {
				f.setAccessible(true);
				Object fieldValue=f.get(bean);
				insert.fieldList.add(new EntityInsertField(columnType, column, fieldValue));
			} catch (Exception e) {
				throw new SmartJdbcException(e);
			}
		}
		return insert;
	}
}

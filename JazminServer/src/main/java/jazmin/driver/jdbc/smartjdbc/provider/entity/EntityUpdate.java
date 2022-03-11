package jazmin.driver.jdbc.smartjdbc.provider.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.enums.ColumnType;
import jazmin.driver.jdbc.smartjdbc.enums.DatabaseType;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;

/**
 * 
 * @author skydu
 *
 */
public class EntityUpdate extends Entity{
	//
	public QueryWhere queryWhere;
	public List<EntityUpdateField> fieldList;
	public boolean excludeNull;
	//
	public EntityUpdate(DatabaseType databaseType, String tableName) {
		super(databaseType, tableName);
		fieldList=new ArrayList<>();
	}
	//
	public EntityUpdate excludeNull(boolean excludeNull) {
		this.excludeNull=excludeNull;
		return this;
	}
	//
	public EntityUpdate queryWhere(QueryWhere queryWhere) {
		this.queryWhere=queryWhere;
		return this;
	}
	//
	public static class EntityUpdateField{
		public String column;
		public Object value;
		public ColumnType columnType;
		//
		public EntityUpdateField(ColumnType columnType, String column,Object value) {
			this.columnType=columnType;
			this.column=column;
			this.value=value;
		}
	}
	//
	public static EntityUpdate create(SmartDataSource datasource, Object bean,
			boolean excludeNull, Set<String> includeFields, 
			QueryWhere queryWhere, String... excludeFields) {
		EntityUpdate update=new EntityUpdate(datasource.getDatabaseType(),datasource.getTableName(bean.getClass()));
		update.queryWhere=queryWhere;
		update.excludeNull=excludeNull;
		Class<?>entityClass=bean.getClass();
		Set<String> excludesNames = new TreeSet<String>();
		for (String e : excludeFields) {
			excludesNames.add(e);
		}
		List<Field> fields=ClassUtils.getFieldList(entityClass);
		for (Field f : fields) {
			if(includeFields!=null&&!includeFields.isEmpty()&&(!includeFields.contains(f.getName()))){
				continue;
			}
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
			String column = datasource.convertFieldName(f.getName());
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			ColumnType columnType=null;
			if(entityField!=null) {
				columnType=entityField.columnType();
			}
			try {
				if(!f.isAccessible()) {
					f.setAccessible(true);
				}
				Object fieldValue=f.get(bean);
				update.fieldList.add(new EntityUpdateField(columnType, column, fieldValue));
				//
				if(update.queryWhere==null) {//默认where主键
					update.queryWhere=QueryWhere.create();
					List<Field> primaryKey=ClassUtils.getPrimaryKey(bean.getClass());
					for (Field field : primaryKey) {
						if(!field.isAccessible()) {
							field.setAccessible(true);
						}
						update.queryWhere.where(datasource.convertFieldName(field.getName()),field.get(bean));
					}
				}
			} catch (Exception e) {
				throw new SmartJdbcException(e);
			}
		}
		//
		return update;
		
	}
}

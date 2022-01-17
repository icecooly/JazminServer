package jazmin.driver.jdbc.smartjdbc.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
import jazmin.driver.jdbc.smartjdbc.annotations.LeftJoin;
import jazmin.driver.jdbc.smartjdbc.enums.JoinType;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Join;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Joins;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class EntityInfo {

	public Class<?> entityClass;
	
	public List<EntityFieldInfo> fieldList;//=ClassUtils.getFieldList(entityClass);
	
	public Joins joins;//left join tableName alias on 
	//
	public EntityInfo() {
		this.joins=new Joins();
	}
	//
	public static EntityInfo create(Class<?> entityClass) {
		EntityInfo info=new EntityInfo();
		info.entityClass=entityClass;
		List<Field> fields=ClassUtils.getFieldList(entityClass);
		List<EntityFieldInfo> fieldList=new ArrayList<>();
		Map<String,Field> fieldMap=new HashMap<>();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) || 
					Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			EntityFieldInfo fieldInfo=EntityFieldInfo.create(field);
			fieldList.add(fieldInfo);
			fieldMap.put(field.getName(), field);
		}
		info.fieldList=fieldList;
		info.addLeftJoins();
		return info;
	}
	//
	private void addLeftJoins() {
		for (EntityFieldInfo fieldInfo : fieldList) {
			Field field=fieldInfo.field;
			if (Modifier.isStatic(field.getModifiers())|| Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			EntityField entityField = fieldInfo.entityField;
			LeftJoin leftJoin=fieldInfo.leftJoin;
			if(leftJoin!=null) {
				Join join=addJoin(JoinType.LEFT_JOIN,SqlProvider.MAIN_TABLE_ALIAS,
						entityClass,leftJoin.table2(),leftJoin.table1Fields(),leftJoin.table2Fields());
				fieldInfo.tableAlias=join.table2Alias;
			}else if(entityField!=null&&!StringUtil.isEmpty(entityField.foreignKeyFields())) {
				String foreignKeyId = entityField.foreignKeyFields();
				String[] foreignKeyIds=foreignKeyId.split(",");
				Class<?> table1=entityClass;
				String table1Alias=SqlProvider.MAIN_TABLE_ALIAS;
				Join join=null;
				for (String id : foreignKeyIds) {
					Field foreignKeyField=null;
					try {
						foreignKeyField=ClassUtils.getExistedField(table1,id);
					} catch (Exception e) {
						throw new IllegalArgumentException(e.getMessage()+"/"+table1.getSimpleName());
					}
					ForeignKey foreignKey=foreignKeyField.getAnnotation(ForeignKey.class);
					if(foreignKey==null) {
						throw new IllegalArgumentException("@ForeignKey not found in "+
									entityClass.getSimpleName()+"."+foreignKeyField.getName());
					}
					Class<?> table2=foreignKey.entityClass();
					join=addJoin(JoinType.LEFT_JOIN,table1Alias,table1, table2,
							new String[] {id},
							new String[]{ClassUtils.getSinglePrimaryKey(table2)});
					table1=table2;
					table1Alias=join.table2Alias;
				}
				fieldInfo.tableAlias=join.table2Alias;
			}
		}
		
	}
	//
	protected Join addJoin(JoinType type,String table1Alias,Class<?> table1,Class<?> table2,
			String[] table1Fields, String[] table2Fields) {
		return joins.addJoin(type, table1, table2, 
				table1Alias, null, table1Fields, table2Fields);
	}
	//
	public String info() {
		StringBuilder info=new StringBuilder();
		info.append("\nEntityInfo[\n").append(entityClass.getName());
		for (EntityFieldInfo field : fieldList) {
			info.append(field.info());
		}
		info.append(joins.info());
		info.append("\n]");
		return info.toString();
	}
}

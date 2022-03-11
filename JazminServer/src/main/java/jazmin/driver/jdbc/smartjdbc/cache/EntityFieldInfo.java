package jazmin.driver.jdbc.smartjdbc.cache;

import java.lang.reflect.Field;

import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.LeftJoin;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class EntityFieldInfo {

	public Field field;
	
	public EntityField entityField;
	
	public LeftJoin leftJoin;
	
	public String tableAlias;
	
	public String name;
	
	public String asName;
	
	public boolean distinct;
	
	public String statFunction;
	
	//
	public static EntityFieldInfo create(Field field) {
		EntityFieldInfo fieldInfo=new EntityFieldInfo();
		fieldInfo.field=field;
		EntityField entityField=field.getAnnotation(EntityField.class);
		fieldInfo.entityField=entityField;
		fieldInfo.leftJoin=field.getAnnotation(LeftJoin.class);
		fieldInfo.name=field.getName();
		if(entityField!=null) {
			fieldInfo.distinct=entityField.distinct();
			fieldInfo.statFunction=entityField.statFunc();
			if(!StringUtil.isEmpty(entityField.field())&&!entityField.field().equals(field.getName())) {
				fieldInfo.name=entityField.field();
				fieldInfo.asName=field.getName();
			}
		}
		return fieldInfo;
	}
	//
	public String info() {
		StringBuilder info=new StringBuilder();
		info.append("\nEntityFieldInfo[").append(tableAlias).append(".").append(name);
		if(asName!=null) {
			info.append(" as ").append(asName);
		}
		info.append("]");
		return info.toString();
	}
}

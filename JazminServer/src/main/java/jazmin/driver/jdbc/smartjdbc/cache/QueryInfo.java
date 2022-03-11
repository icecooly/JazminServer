package jazmin.driver.jdbc.smartjdbc.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.annotations.QueryConditionType;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.util.ClassUtils;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class QueryInfo {
	
	public Class<?> clazz;
	
	public Field field;

	public String fullName;//parent.fullName.field.getName()
	
	public ConditionType conditionType;
	
	public List<QueryFieldInfo> fieldList;
	
	public List<QueryInfo> children;
	
	//
	public QueryInfo(String parentFullName,Field field,Class<?> clazz,ConditionType conditionType) {
		this.field=field;
		this.clazz=clazz;
		this.conditionType=conditionType;
		this.children=new ArrayList<>();
		StringBuilder fullName=new StringBuilder();
		if((!StringUtil.isEmpty(parentFullName))) {
			fullName.append(parentFullName).append(".");
		}
		if(field!=null) {
			fullName.append(field.getName());
		}
		this.fullName=fullName.toString();
	}
	//
	public static QueryInfo create(Class<?> clazz) {
		QueryInfo info=new QueryInfo(null,null,clazz,ConditionType.AND);
		info=create0(info);
		return info;
	}
	//
	private static QueryInfo create0(QueryInfo info) {
		List<Field> fields=ClassUtils.getFieldList(info.clazz);
		List<QueryFieldInfo> fieldList=new ArrayList<>();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) || 
					Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			QueryConditionType fConditionType = field.getAnnotation(QueryConditionType.class);
			if (fConditionType!=null) {
				QueryInfo child=new QueryInfo(info.fullName,field,field.getType(),fConditionType.value());
				info.children.add(child);
				create0(child);
				continue;
			}
			QueryField queryField = field.getAnnotation(QueryField.class);
			if (queryField== null) {
				continue;
			}
			QueryFieldInfo fieldInfo=QueryFieldInfo.create(info.fullName, field);
			fieldList.add(fieldInfo);
		}
		info.fieldList=fieldList;
		return info;
	}
}

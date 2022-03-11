package jazmin.driver.jdbc.smartjdbc.cache;

import java.lang.reflect.Field;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.annotations.Join;
import jazmin.driver.jdbc.smartjdbc.annotations.Joins;
import jazmin.driver.jdbc.smartjdbc.annotations.QueryField;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class QueryFieldInfo {

	public Field field;
	
	public Class<?> fieldType;
	
	public String fieldName;//fullName.field.getName()
	
	public Join join;
	
	public Joins joins;
	
	public QueryField queryField;
	
	public List<Join> joinsList;
	
	public String tableAlias;
	//
	public static QueryFieldInfo create(String fullName, Field field) {
		QueryFieldInfo fieldInfo=new QueryFieldInfo();
		fieldInfo.field=field;
		fieldInfo.fieldType=field.getType();
		if(StringUtil.isEmpty(fullName)) {
			fieldInfo.fieldName=field.getName();
		}else {
			fieldInfo.fieldName=fullName+"."+field.getName();
		}
		fieldInfo.queryField=field.getAnnotation(QueryField.class);
		fieldInfo.join=field.getAnnotation(Join.class);
		fieldInfo.joins=field.getAnnotation(Joins.class);
		return fieldInfo;
	}
	
}

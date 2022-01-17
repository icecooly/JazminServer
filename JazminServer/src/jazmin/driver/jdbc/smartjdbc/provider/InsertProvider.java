package jazmin.driver.jdbc.smartjdbc.provider;

import java.util.ArrayList;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.Types;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityInsert;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityInsert.EntityInsertField;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.util.JSONUtil;

/**
 * 
 * @author skydu
 *
 */
public abstract class InsertProvider extends SqlProvider{
	//
	protected EntityInsert insert;
	//
	public InsertProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	//
	public InsertProvider insert(EntityInsert insert){
		this.insert=insert;
		return this;
	}
	//
	public String getValueSql(EntityInsertField field) {
		return "?,";
	}
	
	@Override
	public SqlBean build() {
		StringBuilder sql=new StringBuilder();
		sql.append("insert into ").append(addIdentifier(insert.tableName)).append("(");
		List<Object>fieldList=new ArrayList<Object>();
		StringBuilder values=new StringBuilder();
		for (EntityInsertField field : insert.fieldList) {
			if(field.value==null) {
				continue;
			}
			values.append(getValueSql(field));
			sql.append("").append(addIdentifier(field.column)).append(",");
			if(!Types.WRAP_TYPES.contains(field.value.getClass())){
				fieldList.add(JSONUtil.toJson(field.value));
			}else{
				fieldList.add(field.value);
			}
		}
		//
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		sql.append("values(");
		sql.append(values.toString());
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		SqlBean insertSql=new SqlBean();
		insertSql.sql=sql.toString();
		insertSql.parameters=fieldList.toArray();
		return insertSql;
	}

}

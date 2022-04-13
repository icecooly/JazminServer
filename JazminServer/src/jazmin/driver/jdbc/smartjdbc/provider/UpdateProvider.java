package jazmin.driver.jdbc.smartjdbc.provider;

import java.util.ArrayList;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.SmartDataSource;
import jazmin.driver.jdbc.smartjdbc.Types;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate;
import jazmin.driver.jdbc.smartjdbc.provider.entity.EntityUpdate.EntityUpdateField;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.provider.where.WhereSqlBuilder;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere.WhereStatment;
import jazmin.driver.jdbc.smartjdbc.util.JSONUtil;

/**
 * 
 * @author skydu
 *
 */
public class UpdateProvider extends SqlProvider{
	//
	protected EntityUpdate update;
	
	//
	public UpdateProvider(SmartDataSource smartDataSource) {
		super(smartDataSource);
	}
	//
	public UpdateProvider update(EntityUpdate update) {
		this.update=update;
		return this;
	}
	//
	public String getValueSql(EntityUpdateField field) {
		return "?,";
	}
	//
	@Override
	public SqlBean build() {
		StringBuilder sql=new StringBuilder();
		sql.append("update ").append(addIdentifier(update.tableName)).append(" ").append(MAIN_TABLE_ALIAS).append(" ");
		List<Object>fieldList=new ArrayList<Object>();
		sql.append("set ");
		for (EntityUpdateField field: update.fieldList) {
			Object fieldValue=field.value;
			if(update.excludeNull&&fieldValue==null) {
				continue;
			}
			sql.append(" ").append(addIdentifier(field.column)).append("=");
			if(fieldValue==null) {
				sql.append("null,");
				continue;
			}
			sql.append(getValueSql(field));
			if(fieldValue!=null&&!Types.WRAP_TYPES.contains(fieldValue.getClass())){
				fieldList.add(JSONUtil.toJson(fieldValue));
			}else{
				fieldList.add(fieldValue);
			}
		}
		sql.deleteCharAt(sql.length()-1);
		//
		QueryWhere queryWhere=update.queryWhere;
		if(queryWhere!=null) {
			WhereStatment ws=new WhereSqlBuilder(getDatabaseType(),queryWhere).build();
			sql.append(ws.sql);
			for(Object o:ws.values){
				fieldList.add(o);
			}
		}
		//
		SqlBean updateSql=new SqlBean();
		updateSql.sql=sql.toString();
		updateSql.parameters=fieldList.toArray();
		//
		return SqlBean.build(sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
	}
}

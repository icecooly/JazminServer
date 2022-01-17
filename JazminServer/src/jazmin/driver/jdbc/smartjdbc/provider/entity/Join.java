package jazmin.driver.jdbc.smartjdbc.provider.entity;

import jazmin.driver.jdbc.smartjdbc.enums.JoinType;

/**
 * 
 * @author skydu
 *
 */
public class Join {
	public JoinType joinType;
	public String table1Alias;
	public Class<?> table1;
	public String table2Alias;
	public Class<?> table2;
	public String[] table1Fields;
	public String[] table2Fields;
	//
	public Join() {
		
	}
	//
	public Join(JoinType joinType) {
		this.joinType=joinType;
	}
	//
	public String info() {
		StringBuilder info=new StringBuilder();
		info.append("\n");
		info.append(table1.getSimpleName()).append(" ");
		info.append(joinType.name()).append(" ").append(table2.getSimpleName()).append(" ").append(table2Alias);
		if(table1Fields!=null&&table2Fields!=null) {
			info.append(" on ");
			for(int i=0;i<table1Fields.length;i++) {
				info.append(table1Alias).append(".").append(table1Fields[i]).append("=").append(table2Alias).append(".").append(table2Fields[i]);
			}
		}
		return info.toString();
	}
}
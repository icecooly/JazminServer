package jazmin.driver.jdbc.smartjdbc.provider.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jazmin.driver.jdbc.smartjdbc.enums.JoinType;
import jazmin.driver.jdbc.smartjdbc.util.StringUtil;

/**
 * 
 * @author skydu
 *
 */
public class Joins {
	private List<Join> joinList=new ArrayList<>();;
	private String prefix="l";
	//
	public Joins() {
	}
	//
	public Joins(String prefix) {
		this.prefix = prefix;
	}
	//
	private Join getSameJoin(JoinType joinType, Class<?> table1, Class<?> table2, String table1Alias, String[] table1Fields,
			String[] table2Fields) {
		for (Join j : joinList) {
			if(!j.joinType.equals(joinType)) {
				continue;
			}
			if (!Arrays.equals(j.table1Fields, table1Fields)) {
				continue;
			}
			if (!Arrays.equals(j.table2Fields, table2Fields)) {
				continue;
			}
			if (!j.table1.equals(table1)) {
				continue;
			}
			if (!j.table2.equals(table2)) {
				continue;
			}
			if (!j.table1Alias.equals(table1Alias)) {
				continue;
			}
			return j;
		}
		return null;

	}
	//
	public Join addJoin(JoinType joinType,
			Class<?> table1, Class<?> table2, 
			String table1Alias, String table2Alias, 
			String[] table1Fields, String[] table2Fields) {
		Join join=getSameJoin(joinType, table1, table2, table1Alias, table1Fields, table2Fields);
		if(join!=null) {
			return join;
		}
		join = new Join(joinType);
		join.table1 = table1;
		join.table2 = table2;
		join.table1Fields = table1Fields;
		join.table2Fields = table2Fields;
		join.table1Alias = table1Alias;
		if(StringUtil.isEmpty(table2Alias)) {
			join.table2Alias=prefix+(joinList.size()+1);
		}else {
			join.table2Alias=table2Alias;
		}
		joinList.add(join);
		return join;
	}
	//
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return the joinList
	 */
	public List<Join> getJoinList() {
		return joinList;
	}
	/**
	 * @param joinList the joinList to set
	 */
	public void setJoinList(List<Join> joinList) {
		this.joinList = joinList;
	}
	//
	public String info() {
		StringBuilder info=new StringBuilder();
		info.append("\nJoins[");
		for (Join join : joinList) {
			info.append(join.info());
		}
		info.append("]");
		return info.toString();
	}
}

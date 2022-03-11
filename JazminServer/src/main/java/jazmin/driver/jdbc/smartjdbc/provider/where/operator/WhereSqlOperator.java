package jazmin.driver.jdbc.smartjdbc.provider.where.operator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jazmin.driver.jdbc.smartjdbc.SmartJdbcException;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;

/**
 * 
 * @author skydu
 *
 */
public class WhereSqlOperator extends Operator{

	
	public WhereSqlOperator(OperatorContext ctx,Condition condition) {
		super(ctx);
		this.condition=condition;
	}

	public Condition condition;
	
	@Override
	public String build() {
		if(!preParseSql()) {
			return condition.whereSql;
		}
		return parseSql();
	}
	
	//
	public boolean preParseSql() {
		String[] regexs= {"\\#\\{[a-zA-Z_$][a-zA-Z0-9_$]*\\}","\\$\\{[a-zA-Z_$][a-zA-Z0-9_$]*\\}"};
		for (String regex : regexs) {
			Pattern p=Pattern.compile(regex);
			Matcher m = p.matcher(condition.whereSql);
			if(m.find()) { 
			    return true;
			}
		}
		return false;
	}
	
	//
	/**
	 * 
	 * @return
	 */
	public String parseSql() {
		String whereSql=condition.whereSql;
		@SuppressWarnings("unchecked")
		Map<String,Object> paraMap=(Map<String, Object>) condition.value;
		if(paraMap==null) {
			paraMap=new HashMap<>();
		}
		Pattern p=Pattern.compile("\\#\\{([a-zA-Z_][a-zA-Z0-9_]*)\\}");
		Matcher m = p.matcher(whereSql);
		Pattern $p=Pattern.compile("\\$\\{([a-zA-Z_][a-zA-Z0-9_]*)\\}");
		Matcher $m = $p.matcher(whereSql);
		String newSql=whereSql;
		Object[] values=null;
		List<String> groups=new ArrayList<>();
		while(m.find()) { 
		    groups.add(m.group(1));
		}
		newSql=m.replaceAll("?");
		values=new Object[groups.size()];
		int i=0;
		for (String group : groups) {
			Object value=paraMap.get(group);
			if(value==null) {
				throw new SmartJdbcException("paraMap "+group+" not found.\nsql:"+whereSql+
						"\nall can choose paras is:"+paraMap.keySet()); 
			}
			values[i++]=value;
		}
		//
		while($m.find()) { 
			String group=$m.group(1);
			String replaceGroup="\\$\\{"+group+"\\}";
			Object value=paraMap.get(group);
			if(value==null) {
				throw new SmartJdbcException(group+" not found.\nsql:"+whereSql+
						"\nall can choose paras is:"+paraMap.keySet()); 
			}
			if(value instanceof String) {
				newSql=newSql.replaceAll(replaceGroup,value.toString());
			}else if(value instanceof Collection<?>){
				StringBuilder in=new StringBuilder();
				Collection<?> array=(Collection<?>)value;
				in.append("(");
				for (Object v : array.toArray()) {
					in.append(v).append(",");
				}
				if(in.length()>1) {
					in.deleteCharAt(in.length()-1);
				}
				in.append(")");
				newSql=newSql.replaceAll(replaceGroup,in.toString());
			}else {
				newSql=newSql.replaceAll(replaceGroup,value.toString());
			}
		}
		for (Object value : values) {
			ctx.addParameter(value);
		}
		return newSql;
	}
	
}

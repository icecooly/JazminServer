package jazmin.driver.jdbc.smartjdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;

/**
 * 
 * @author icecooly
 *
 */
public class Query<T> {
	//
	private int pageIndex;
	
	private int pageSize;
	
	private List<OrderBy> orderByList;
	
	private Map<String,Object> params;
	//
	public static Integer defaultPageSize=20;
	public static String defaultOrderBy;//id desc
	//
	@SuppressWarnings("serial")
	public static class OrderBy implements Serializable{
		//
		public String tableAlias;
		public String field;//userName
		public String type;//ASC DESC
		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}
		/**
		 * @param field the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		//
		public OrderBy() {
			
		}
		//
		public OrderBy(String tableAlias,String field,String type) {
			this.tableAlias=tableAlias;
			this.field=field;
			this.type=type;
		}
	}
	//
	public Query(){
		pageSize=20;//
		pageIndex=1;//从1开始
		if(defaultPageSize!=null) {
			pageSize=defaultPageSize;
		}
		orderByList=new ArrayList<>();
		params=new HashMap<>();
	}
	//
	public int getStartPageIndex(){
		return (pageIndex-1)*pageSize;
	}
	//
	public Query<?> orderBy(String field,String orderby) {
		return orderBy(SqlProvider.MAIN_TABLE_ALIAS, field, orderby);
	}
	//
	public Query<?> orderBy(String tableAlias,String field,String orderby) {
		if(orderByList==null) {
			orderByList=new ArrayList<>();
		}
		orderByList.add(new OrderBy(tableAlias,field, orderby));
		return this;
	}
	//
	public Query<?> param(String key,Object value){
		params.put(key, value);
		return this;
	}
	//
	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the orderByList
	 */
	public List<OrderBy> getOrderByList() {
		return orderByList;
	}
	/**
	 * @param orderByList the orderByList to set
	 */
	public void setOrderByList(List<OrderBy> orderByList) {
		this.orderByList = orderByList;
	}
	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}

package jazmin.driver.jdbc.smartjdbc.provider.where;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.driver.jdbc.smartjdbc.provider.SqlProvider;

/**
 * 
 * @author skydu
 *
 */
public class Where {
	//
	public String uuid;
	public ConditionType conditionType;
	public List<Where> children;
	public List<Condition> conditionList;
	//
	public static class Condition{
		public String alias;
		public String key;
		public String keyCast;
		public String keyFunc;
		public Object value;
		public SqlOperator operator;
		public String customOperator;
		public String whereSql;
		public boolean isColumn;
		public JsonContain jsonContain;
		//
		public Condition() {
			this.isColumn=true;
		}
	}
	//
	public static class JsonContain{
		public String objectField;
	}

	public Where() {
		this.uuid=UUID.randomUUID().toString();
		this.children = new LinkedList<>();
		this.conditionList=new LinkedList<>();
	}
	
	public Where(ConditionType conditionType) {
		this();
		this.conditionType=conditionType;
	}
	//
	public Where where(String key, SqlOperator op) {
		return where(key, op, null);
	}
	//
	public Where where(String key, SqlOperator op, Object value) {
		return where(SqlProvider.MAIN_TABLE_ALIAS, key, op, value);
	}
	//
	public Where where(String alias, String key, SqlOperator op, Object value) {
		return where(alias, key, op, value, true, null, null);
	}
	//
	public Where where(Condition condition) {
		conditionList.add(condition);
		return this;
	}
	//
	public Where where(String alias, String key, SqlOperator op, Object value, 
			boolean isColumn, JsonContain jsonContain, String keyCast) {
		return where(alias, key, op, value, isColumn, jsonContain, keyCast, null);
	}
	//
	public Where where(String alias, String key, SqlOperator op, Object value, 
			boolean isColumn, JsonContain jsonContain, String keyCast, String keyFunc) {
		Condition w = new Condition();
		w.alias = alias;
		w.key = key;
		w.operator = op;
		w.value = value;
		w.isColumn=isColumn;
		w.jsonContain=jsonContain;
		w.keyCast=keyCast;
		w.keyFunc=keyFunc;
		conditionList.add(w);
		return this;
	}
	//
	public Where whereSql(String whereSql, Map<String,Object> values) {
		Condition w = new Condition();
		w.whereSql=whereSql;
		w.value=values;
		conditionList.add(w);
		return this;
	}
	//
	public Where and(Where w) {
		w.conditionType = ConditionType.AND;
		children.add(w);
		return this;
	}
	//
	public Where or(Where w) {
		w.conditionType = ConditionType.OR;
		children.add(w);
		return this;
	}
	//
	//
	/**
	 * key 等于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where eq(String key,Object value){
		return this.where(key,SqlOperator.EQ, value);
	}
	
	/**
	 * alias.key 等于
	 * @param alias 表别名
	 * @param key
	 * @param value
	 * @return
	 */
	public Where eq(String alias,String key,Object value){
		return this.where(alias,key,SqlOperator.EQ, value);
	}
	
	/**
	 * key 不等于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where ne(String key,Object value){
		return this.where(key, SqlOperator.NE, value);
	}
	
	/**
	 * alias.key 不等于
	 * @param alias 表别名
	 * @param key
	 * @param value
	 * @return
	 */
	public Where ne(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.NE, value);
	}
	
	/**
	 * key 小于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where lt(String key,Object value){
		return this.where(key, SqlOperator.LT, value);
	}
	
	/**
	 * alias.key 小于
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where lt(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.LT, value);
	}
	
	/**
	 * key 小于等于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where le(String key,Object value){
		return this.where(key, SqlOperator.LE, value);
	}
	
	/**
	 * alias.key 小于等于
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where le(String alias,String key,Object value){
		return this.where(alias, key, SqlOperator.LE, value);
	}
	
	/**
	 * key 大于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where gt(String key,Object value){
		return this.where(key, SqlOperator.GT, value);
	}
	
	/**
	 * alias.key 大于
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where gt(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.GT, value);
	}
	
	/**
	 * key 大于等于
	 * @param key
	 * @param value
	 * @return
	 */
	public Where ge(String key,Object value){
		return this.where(key, SqlOperator.GE, value);
	}
	
	/**
	 * alias.key大于等于
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where ge(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.GE, value);
	}
	
	/**
	 * key LIKE '%值%'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where like(String key,Object value){
		return this.where(key, SqlOperator.LIKE, value);
	}
	
	/**
	 * alias.key LIKE '%值%'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where like(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.LIKE, value);
	}
	
	/**
	 * key NOT LIKE '%值%'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLike(String key,Object value){
		return this.where(key,SqlOperator.NOT_LIKE, value);
	}
	
	/**
	 * alias.key NOT LIKE '%值%'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLike(String alias,String key,Object value){
		return this.where(alias,key,SqlOperator.NOT_LIKE, value);
	}
	
	/**
	 * key LIKE '%值'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where likeLeft(String key,Object value){
		return this.where(key, SqlOperator.LIKE_LEFT, value);
	}
	
	/**
	 * alias.key LIKE '%值'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where likeLeft(String alias,String key,Object value){
		return this.where(alias, key, SqlOperator.LIKE_LEFT, value);
	}
	
	/**
	 * key NOT LIKE '%值'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLikeLeft(String key,Object value){
		return this.where(key, SqlOperator.NOT_LIKE_LEFT, value);
	}
	
	/**
	 * alias.key NOT LIKE '%值'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLikeLeft(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.NOT_LIKE_LEFT, value);
	}
	
	/**
	 * key LIKE '值%'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where likeRight(String key,Object value){
		return this.where(key, SqlOperator.LIKE_RIGHT, value);
	}
	
	/**
	 * alias.key LIKE '值%'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where likeRight(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.LIKE_RIGHT, value);
	}
	
	/**
	 * key NOT LIKE '值%'
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLikeRight(String key,Object value){
		return this.where(key, SqlOperator.NOT_LIKE_RIGHT, value);
	}
	
	/**
	 * alias.key NOT LIKE '值%'
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notLikeRight(String alias,String key,Object value){
		return this.where(alias,key, SqlOperator.NOT_LIKE_RIGHT, value);
	}
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param value
	 * @return
	 */
	public Where in(String key,Object value) {
		return this.where(key, SqlOperator.IN, value);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where in(String alias,String key,Object value) {
		return this.where(alias,key, SqlOperator.IN, value);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notin(String key,Object value) {
		return this.where(key, SqlOperator.NOT_IN, value);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param value
	 * @return
	 */
	public Where notin(String alias,String key,Object value) {
		return this.where(alias,key, SqlOperator.NOT_IN, value);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,Collection<?> values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,Collection<?> values) {
		return this.where(alias,key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String key,int[] values) {
		return this.where(key, SqlOperator.IN, values);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String alias,String key,int[] values) {
		return this.where(alias,key, SqlOperator.IN, values);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,int[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,int[] values) {
		return this.where(alias,key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String key,short[] values) {
		return this.where(key, SqlOperator.IN, values);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String alias,String key,short[] values) {
		return this.where(alias,key, SqlOperator.IN, values);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,short[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,short[] values) {
		return this.where(alias,key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String key,long[] values) {
		return this.where(key, SqlOperator.IN, values);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String alias,String key,long[] values) {
		return this.where(alias,key, SqlOperator.IN, values);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,long[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,long[] values) {
		return this.where(alias,key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String key,byte[] values) {
		return this.where(key, SqlOperator.IN, values);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String alias,String key,byte[] values) {
		return this.where(alias,key, SqlOperator.IN, values);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,byte[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,byte[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String key,String[] values) {
		return this.where(key, SqlOperator.IN, values);
	}
	
	/**
	 * alias.key in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where in(String alias,String key,String[] values) {
		return this.where(alias,key, SqlOperator.IN, values);
	}
	
	/**
	 * key not in (值1，值2...)
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String key,String[] values) {
		return this.where(key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * alias.key not in (值1，值2...)
	 * @param alias
	 * @param key
	 * @param values
	 * @return
	 */
	public Where notin(String alias,String key,String[] values) {
		return this.where(alias,key, SqlOperator.NOT_IN, values);
	}
	
	/**
	 * key IS NULL
	 * @param key
	 * @return
	 */
	public Where isNull(String key){
		return this.where(key, SqlOperator.IS_NULL,null);
	}
	
	/**
	 * alias.key IS NULL
	 * @param alias
	 * @param key
	 * @return
	 */
	public Where isNull(String alias,String key){
		return this.where(alias,key,SqlOperator.IS_NULL,null);
	}
	
	/**
	 * key IS NOT NULL
	 * @param key
	 * @return
	 */
	public Where isNotNull(String key){
		return this.where(key, SqlOperator.IS_NOT_NULL,null);
	}
	
	/**
	 * alias.key IS NOT NULL
	 * @param alias
	 * @param key
	 * @return
	 */
	public Where isNotNull(String alias,String key){
		return this.where(alias,key, SqlOperator.IS_NOT_NULL,null);
	}
}
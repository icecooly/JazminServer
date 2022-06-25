/**
 * 
 */
package jazmin.server.web.mvc;

import java.util.HashMap;
import java.util.Map;

import jazmin.util.DumpIgnore;

/**
 * @author yama
 * 29 Dec, 2014
 */
@DumpIgnore
public class Context {
	View view;
	Throwable exception;
	Request request;
	Response response;
	Map<String,Object>contextMap;
	int errorCode;
	//
	public Context() {
		contextMap=new HashMap<String, Object>();
	}
	//
	public void put(String key,Object v){
		contextMap.put(key, v);
	}
	//
	public View view(){
		return view;
	}
	//
	public View view(View v){
		this.view=v;
		return v;
	}
	//
	public Request request(){
		return request;
	}
	//
	public Response response(){
		return response;
	}
	//
	private String getRequired(String key,boolean required){
		String ss=request.queryParams(key);
		if(required&&ss==null){
			throw new IllegalArgumentException("key:"+key+" required");
		}
		return ss;
	}
	//
	public Boolean getBoolean(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Boolean.valueOf(ss);
	}
	public Boolean getBooleanOrDefault(String key,Boolean dv){
		Boolean ss=getBoolean(key);
		return ss==null?dv:ss;
	}
	//
	public Boolean  getBoolean(String key){
		return getBoolean(key, false);
	}
	//
	public String getString(String key,boolean required){	
		String ss=getRequired(key, required);
		return ss;
	}
	//
	public String getStringOrDefault(String key,String dv){	
		String s= getString(key,false);
		return s==null?dv:s;
	}
	//
	public String getString(String key){	
		return getString(key,false);
	}
	//
	public Long  getLong(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Long.valueOf(ss);
	}
	//
	public Long  getLongOrDefault(String key,Long dv){
		Long v= getLong(key,false);
		return v==null?dv:v;
	}
	//
	public Long  getLong(String key){
		return getLong(key,false);
	}
	//
	public Integer  getInteger(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Integer.valueOf(ss);
	}
	//
	public Integer  getIntegerOrDefault(String key,Integer dv){
		Integer v= getInteger(key,false);
		return v==null?dv:v;
	}
	//
	public Integer  getInteger(String key){
		return getInteger(key,false);
	}
	//
	public Short  getShort(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Short.valueOf(ss);
	}
	//
	public Short  getShort(String key){
		return getShort(key,false);
	}
	//
	public Short  getShortOrDefault(String key,Short dv){
		Short v= getShort(key,false);
		return v==null?dv:v;
	}
	//
	public Float  getFloat(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Float.valueOf(ss);
	}
	//
	public Float  getFloat(String key){
		return getFloat(key,false);
	}
	public Float  getFloatOrDefault(String key,Float dv){
		Float v =getFloat(key,false);
		return v==null?dv:v;
	}
	//
	public Double  getDouble(String key,boolean required){
		String ss=getRequired(key, required);
		return ss==null||ss.trim().isEmpty()?null:Double.valueOf(ss);
	}
	//
	public Double  getDoubleOrDefault(String key,Double dv){
		Double v= getDouble(key,false);
		return v==null?dv:v;
	}
	//
	public Double  getDouble(String key){
		return getDouble(key,false);
	}
	//
	public void clearException(){
		exception=null;
	}
	//
	@Override
	public String toString() {
		return request.toString();
	}
}

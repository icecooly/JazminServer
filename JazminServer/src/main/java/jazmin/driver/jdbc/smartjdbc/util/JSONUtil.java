package jazmin.driver.jdbc.smartjdbc.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author icecooly
 *
 */
public class JSONUtil {
	//
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	//
	public static String toJson(Object obj,boolean disableCircularReferenceDetect){
		return JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);
	}
	//
	public static String toJson(Object obj,SerializerFeature... features){
		return JSON.toJSONString(obj,features);
	}
	//
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String str,Class<?>t){
		return (T) JSON.parseObject(str, t);
	}
	//
	@SuppressWarnings("unchecked")
	public static <T> List<T> fromJsonList(String str,Class<?>t){
		return  (List<T>) JSON.parseArray(str, t);	
	}
	//
	/**
	 * convert json string to Map
	 * @param json
	 * @param keyType
	 * @param valueType
	 * @return
	 */
	public static <K, V> Map<K, V> fromJsonMap(String json, Class<K> keyType,  Class<V> valueType) {
	     return JSON.parseObject(json,new TypeReference<Map<K, V>>(keyType, valueType) {});
	}
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param json
	 * @param keyType
	 * @param valueType
	 * @return
	 */
	public static <K, V> Map<K, List<V>> fromJsonMapListValue(String json, Class<K> keyType,  Class<V> valueType) {
	     return JSON.parseObject(json,new TypeReference<Map<K, List<V>>>(keyType, valueType) {});
	}
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param json
	 * @param keyType
	 * @param valueType
	 * @return
	 */
	public static <K, V> Map<K, Set<V>> fromJsonMapSetValue(String json, Class<K> keyType,  Class<V> valueType) {
	     return JSON.parseObject(json,new TypeReference<Map<K, Set<V>>>(keyType, valueType) {});
	}
	/**
	 * 
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> Set<T> fromJsonSet(String json,Class<T>t){
		return  JSON.parseObject(json,new TypeReference<Set<T>>(){});
	}
}

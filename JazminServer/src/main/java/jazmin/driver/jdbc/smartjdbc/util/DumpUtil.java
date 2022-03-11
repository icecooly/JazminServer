package jazmin.driver.jdbc.smartjdbc.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author icecooly
 *
 */
public class DumpUtil {
	//
	public static String dump(Object o){
		if(o==null){
			return "<NULL>";
		}
		return JSON.toJSONString(o,SerializerFeature.PrettyFormat,
				SerializerFeature.WriteDateUseDateFormat);
	}
}

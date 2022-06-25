package jazmin.server.msg.codec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yama
 * @date Jun 5, 2014
 */
public class ResponseMessage {
	
	
	/** status ok*/
	public static final int SC_OK=0;
	/**bad message format*/
	public static final int SC_BAD_MESSAGE=0x1;
	/**app exception*/
	public static final int SC_APP_EXCEPTION=0x2;
	/**system exception*/
	public static final int SC_SYSTEM_EXCEPTION=0x3;
	/**user kicked by server*/
	public static final int SC_KICKED=0x4;
	/**user send too many messages*/
	public static final int SC_REQUEST_RATE_TOO_HIGH=0x5;
	/**illegal arguments type or count*/
	public static final int SC_ILLEGA_ARGUMENT=0x6;
	/**sync service repeat call*/
	public static final int SC_SYNC_SERVICE=0x7;
	/**repeat attack attack*/
	public static final int SC_REPEAT_ATTACK=0x8;
	//
	public int messageType;
	public byte[] rawData;
	public int requestId;
	public String serviceId;
	public int statusCode;
	public String statusMessage;
	public Object responseObject;
	//
	public static Map<String,String>emptyHashMap=new HashMap<>();
	public ResponseMessage() {
		responseObject=emptyHashMap;
		statusCode=SC_OK;
	}
}

/**
 * 
 */
package jazmin.test.util;


import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.util.DumpUtil;
import jazmin.util.IOUtil;

/**
 * @author yama
 * 6 Jan, 2015
 */
public class UtilTest {
	
	static Logger logger=LoggerFactory.getLogger(UtilTest.class);
	//
	public static void main(String[] args) throws Exception{
		String sourceURI="http://cocostudio.download.appget.cn/Cocos2D-X/cocos2d-x-3.2.zip";
		String destFilePath="/tmp/xxx";
		
		logger.info("download file from {} to {}",sourceURI,destFilePath);
		AtomicInteger lastPercent=new AtomicInteger(-1);
		IOUtil.copyFile(sourceURI, destFilePath,(total,current)->{
			if(total<=0){
				logger.warn("total length is 0.no progress output");
				return;
			}
			float percent=((float)current/(float)total)*100;
			if(percent>=lastPercent.intValue()+10||lastPercent.intValue()<0){
				lastPercent.set((int)percent);
				logger.info("total {} current {} percent {}%",
						dumpByte(total),
						dumpByte(current),
						String.format("%.2f",percent));
			}
		});
		logger.info("file saved to {}",destFilePath);
		
	}
	//
	private static String dumpByte(long byteCount){
    	return DumpUtil.byteCountToString(byteCount);
    }
}

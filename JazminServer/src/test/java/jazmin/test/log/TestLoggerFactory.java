/**
 * 
 */
package jazmin.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yama
 * 25 Jan, 2016
 */
public class TestLoggerFactory {
	//
	private static Logger logger=LoggerFactory.getLogger(TestLoggerFactory.class);
	//
	
	public static void main(String[] args) throws Exception{
		//
		logger.debug("11111");
		logger.info("22222");
		logger.error("33333");
	}

}

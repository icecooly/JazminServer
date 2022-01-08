/**
 * 
 */
package jazmin.test.log;

import jazmin.core.Jazmin;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;

/**
 * @author yama
 * 25 Jan, 2016
 */
public class TestLoggerFactory {
	//
	private static Logger logger=LoggerFactory.get(TestLoggerFactory.class);
	//
	
	public static void main(String[] args) throws Exception{
		LoggerFactory.setLevel("DEBUG");
		LoggerFactory.setFile("/tmp/test.log", true);
	
		//
		logger.debug("11111");
		logger.info("22222");
		logger.error("33333");
		//
//		Jazmin.start();
		//
		LoggerFactory.setLevel("ERROR");
		logger.debug("44444");
		System.out.println("logger:"+logger.getClass().getClassLoader());
		logger.info("55555");
		logger.error("66666");
		LoggerFactory.setLevel("INFO");
		logger.info("77777");
	}

}

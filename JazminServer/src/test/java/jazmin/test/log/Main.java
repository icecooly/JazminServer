package jazmin.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author skydu
 *
 */
public class Main {

	private static Logger logger=LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		logger.error("xxxxxx");
	}
}

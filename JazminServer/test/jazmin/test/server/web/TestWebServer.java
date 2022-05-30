/**
 * 
 */
package jazmin.test.server.web;

import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import jazmin.log.LoggerFactory;
import jazmin.server.web.WebServer;

/**
 * @author yama
 * 29 Dec, 2014
 */
public class TestWebServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//
		LoggerFactory.setLevel("INFO");
		LoggerFactory.setFile("/tmp/" + TestWebServer.class.getSimpleName() + ".log", true);
		//
		//
		Jazmin.setServerName("boss"+System.getProperty("user.name"));
		//
		WebServer ws=new WebServer();
		ws.setEnableJettyLogger(true);
		ws.addWar("/","/Users/skydu/eclipse-workspace/Jetty/release/DemoWebSystem-1.0.war");
		Jazmin.addServer(ws);
		ws.setPort(8888);
		Jazmin.start();
		
	}

}

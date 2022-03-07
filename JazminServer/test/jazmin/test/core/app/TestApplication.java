/**
 * 
 */
package jazmin.test.core.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.core.app.AutoWired;
import jazmin.driver.http.HttpClientDriver;
import jazmin.driver.jdbc.SmartJdbcDruidConnectionDriver;
import jazmin.driver.rpc.JazminRpcDriver;
import jazmin.log.LoggerFactory;
import jazmin.server.msg.MessageServer;
import jazmin.server.rpc.RpcServer;
import jazmin.test.core.app.TestAction.TestActionImpl;

/**
 * @author yama
 * 31 Mar, 2015
 */
public class TestApplication extends Application {
	@AutoWired
	TestActionImpl testAction;
	//
	@Override
	public void init() throws Exception {
		
	}
	//
	public void start() {
		System.err.println(testAction.testService);
		System.err.println(testAction.testService.testDAO);
		System.err.println(testAction.testService.testDAO.connectionDriver);
	}
	//
	public static void runBiz() throws Exception{
		LoggerFactory.setLevel("ALL");
		LoggerFactory.setFile("/tmp/" + TestApplication.class.getSimpleName() + ".log", true);
		Jazmin.setServerName("TableItBizSystem");
		String dbUser=null;
		String dbPassword=null;
		String dbUrl=null;
		String dbDriverClass=null;
		try (InputStream input = new FileInputStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbUser=prop.getProperty("dbUser");
            dbPassword=prop.getProperty("dbPassword");
            dbUrl=prop.getProperty("dbUrl");
            dbDriverClass=prop.getProperty("dbDriverClass");
        }
		SmartJdbcDruidConnectionDriver driver = new SmartJdbcDruidConnectionDriver();
		driver.setDriverClass(dbDriverClass);
		driver.setUrl(dbUrl);
		driver.setUser(dbUser);
		driver.setPassword(dbPassword);
		Jazmin.addDriver(driver);
		//
		JazminRpcDriver rpcDriver = new JazminRpcDriver();
		rpcDriver.addRemoteServer("TableItAccountSystem", "app", "127.0.0.1", 9996);
		Jazmin.addDriver(rpcDriver);
		//
		HttpClientDriver httpClientDriver = new HttpClientDriver();
		Jazmin.addDriver(httpClientDriver);
		//
		//
		RpcServer rpcServer = new RpcServer();
		rpcServer.setPort(9972);
		Jazmin.addServer(rpcServer);
		//
		MessageServer messageServer = new MessageServer();
		messageServer.setPort(0);
		messageServer.setWebSocketPort(8981);
		Jazmin.addServer(messageServer);
		//
		Jazmin.loadApplication("/Users/skydu/git/TableItBizSystem/release/TableItBizSystem.jaz");
		//
		System.setProperty("informat.pluginconfig","/Users/skydu/Desktop/plugin.xml");
		//
		Jazmin.start();
	}
	//
	public static void main(String[] args)throws Exception{
		runBiz();
	}
}

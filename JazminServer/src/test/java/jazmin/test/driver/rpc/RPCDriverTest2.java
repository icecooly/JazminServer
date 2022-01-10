package jazmin.test.driver.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import jazmin.server.console.ConsoleServer;
import jazmin.server.rpc.RpcServer;
import jazmin.test.server.rpc.TestRemoteService.TestRemoteServiceImpl;
import jazmin.test.server.rpc.TestRemoteServiceAsync;

/**
 * 
 * @author yama
 * 16 Jan, 2015
 */
public class RPCDriverTest2 {
	//

	static Logger logger=LoggerFactory.getLogger(RPCDriverTest2.class);
	//
	public static void main(String[] args) {
		JazminRpcDriver driver=new JazminRpcDriver();
		driver.setPrincipal("a"+System.currentTimeMillis());
		driver.addRemoteServer("test","test","localhost",6001);
		Jazmin.addDriver(driver);
		Jazmin.addServer(new ConsoleServer());
		RpcServer rpcServer=new RpcServer();
		rpcServer.registerService(new TestRemoteServiceImpl());
		Jazmin.addServer(rpcServer);
		Jazmin.start();
		TestRemoteServiceAsync async=driver.createAsync(TestRemoteServiceAsync.class, "test");
		async.methodA((i,e)->{
			logger.info(i);
		});
		async.methodA((i,e)->{
			logger.info(i);
		});
	}

}

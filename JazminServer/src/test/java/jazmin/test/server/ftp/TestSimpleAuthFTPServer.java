/**
 * 
 */
package jazmin.test.server.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jazmin.core.Jazmin;
import jazmin.server.console.ConsoleServer;
import jazmin.server.ftp.FtpServer;
import jazmin.server.ftp.FtpUserInfo;
import jazmin.server.ftp.SimpleAuthCallback;
import jazmin.server.ftp.SimpleUserManager;

/**
 * @author g2131
 *
 */
public class TestSimpleAuthFTPServer {
	private static Logger logger=LoggerFactory.getLogger(TestSimpleAuthFTPServer.class);
	//
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Jazmin.start();
		FtpServer server=new FtpServer();
		server.setPort(2221);
		//
		server.setUserManager(new SimpleUserManager(new SimpleCallback()));
		//
		Jazmin.addServer(server);
		Jazmin.addServer(new ConsoleServer());
		Jazmin.start();
	}
	//
	static class SimpleCallback implements SimpleAuthCallback{

		@Override
		public FtpUserInfo authenticate(String user, String password)
				throws Exception {
			logger.info("authxxxxx");
			if(user.equals("admin")&&password.equals("123")){
				FtpUserInfo uu=new FtpUserInfo();
				uu.userName="admin";
				uu.homeDirectory=".";
				return uu;
			}else{
				return null;
			}
		}
		//
		@Override
		public FtpUserInfo getUserByName(String user) throws Exception {
			System.err.println("getUserByName");
			FtpUserInfo uu=new FtpUserInfo();
			uu.userName="admin";
			uu.homeDirectory=".";
			return uu;
		}
		
	}
	//
}

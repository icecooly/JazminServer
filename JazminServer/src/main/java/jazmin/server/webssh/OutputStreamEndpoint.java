/**
 * 
 */
package jazmin.server.webssh;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yama
 *
 */
public class OutputStreamEndpoint implements PeerEndpoint{
	private static Logger logger=LoggerFactory.getLogger(OutputStreamEndpoint.class);
	OutputStream os;
	public OutputStreamEndpoint(OutputStream os){
		this.os=os;
	}
	//
	@Override
	public void close() {
		try {
			os.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void write(String msg) {
		try {
			os.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	//
	@Override
	public String toString() {
		return "OS:"+os;
	}
}

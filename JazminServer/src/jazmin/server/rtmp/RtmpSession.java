/**
 * 
 */
package jazmin.server.rtmp;

import java.util.Date;

/**
 * @author yama
 *
 */
public class RtmpSession {
	ServerHandler serverHandler;

	/**
	 * @return
	 * @see ServerHandler#getBytesRead()
	 */
	public long getBytesRead() {
		return serverHandler.getBytesRead();
	}

	/**
	 * @return
	 * @see ServerHandler#getBytesWritten()
	 */
	public long getBytesWritten() {
		return serverHandler.getBytesWritten();
	}

	/**
	 * @return
	 * @see ServerHandler#getClientId()
	 */
	public String getClientId() {
		return serverHandler.getClientId();
	}

	/**
	 * @return
	 * @see ServerHandler#getPlayName()
	 */
	public String getPlayName() {
		return serverHandler.getPlayName();
	}

	/**
	 * @return
	 * @see ServerHandler#getStreamId()
	 */
	public int getStreamId() {
		return serverHandler.getStreamId();
	}

	/**
	 * @return
	 * @see ServerHandler#getBufferDuration()
	 */
	public int getBufferDuration() {
		return serverHandler.getBufferDuration();
	}

	/**
	 * @return
	 * @see ServerHandler#getRemoteHost()
	 */
	public String getRemoteHost() {
		return serverHandler.getRemoteHost();
	}

	/**
	 * @return
	 * @see ServerHandler#getRemotePort()
	 */
	public int getRemotePort() {
		return serverHandler.getRemotePort();
	}

	/**
	 * @return
	 * @see ServerHandler#getCreateTime()
	 */
	public Date getCreateTime() {
		return serverHandler.getCreateTime();
	}

	/**
	 * @return
	 * @see ServerHandler#getTcURL()
	 */
	public String getTcURL() {
		return serverHandler.getTcURL();
	}
	//
	public void close(){
		serverHandler.close();
	}
	//

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RtmpSession [getBytesRead()=" + getBytesRead()
				+ ", getBytesWritten()=" + getBytesWritten()
				+ ", getClientId()=" + getClientId() + ", getPlayName()="
				+ getPlayName() + ", getStreamId()=" + getStreamId()
				+ ", getBufferDuration()=" + getBufferDuration()
				+ ", getRemoteHost()=" + getRemoteHost() + ", getRemotePort()="
				+ getRemotePort() + ", getCreateTime()=" + getCreateTime()
				+ ", getTcURL()=" + getTcURL() + "]";
	}
	
}

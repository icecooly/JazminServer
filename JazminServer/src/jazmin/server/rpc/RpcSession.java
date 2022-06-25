/**
 * 
 */
package jazmin.server.rpc;

import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.LongAdder;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;

/**
 * @author yama
 * 23 Dec, 2014
 */
public class RpcSession {
	private static Logger logger=LoggerFactory.get(RpcSession.class);
	//
	boolean enableSSL;
	String remoteHostAddress;
	int remotePort;
	String principal;
	String credential;
	Set<String>topics;
	Channel channel;
	Date createTime;
	boolean disablePushMessage;
	private LongAdder sentPackageCount;
	private LongAdder receivedPackageCount;
	private LongAdder pushedPackageCount;
	private LongAdder totalNetworkTime;
	private long maxNetworkTime=0;
	private long minNetworkTime=Long.MAX_VALUE;
	String cluster;
	boolean authed;
	//
	public RpcSession(){
		topics=new TreeSet<String>();
		createTime=new Date();
		sentPackageCount=new LongAdder();
		receivedPackageCount=new LongAdder();
		pushedPackageCount=new LongAdder();
		totalNetworkTime=new LongAdder();
		authed=false;
	}


	/**
	 * @return the enableSSL
	 */
	public boolean isEnableSSL() {
		return enableSSL;
	}


	/**
	 * @param enableSSL the enableSSL to set
	 */
	public void setEnableSSL(boolean enableSSL) {
		this.enableSSL = enableSSL;
	}


	/**
	 * @return the cluster
	 */
	public String getCluster() {
		return cluster;
	}
	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	/**
	 * get principal of this session
	 * @return
	 */
	public String getPrincipal() {
		return principal;
	}
	/**
	 * set principal of this session
	 * @param principal
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	/**
	 * return credential of thi session
	 * @return
	 */
	public String getCredential() {
		return credential;
	}
	/**
	 * set credential of this this session
	 * @param credential the credential
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}
	/**
	 * set remote host address of this session
	 * @param remoteHostAddress
	 */
	public void setRemoteHostAddress(String remoteHostAddress) {
		this.remoteHostAddress = remoteHostAddress;
	}
	/**
	 * set remote port of this session
	 * @param remotePort
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	//
	void setChannel(Channel channel){
		this.channel=channel;
		if(channel!=null){
			SocketAddress remoteAddr=channel.remoteAddress();
			if(remoteAddr!=null){
				InetSocketAddress addr=(InetSocketAddress) remoteAddr;
				InetAddress ad=addr.getAddress();
				remoteHostAddress=ad.getHostAddress();
				remotePort=addr.getPort();
			}		
		}
	}
	/**
	 * return remote host address of this connection
	 */
	public String getRemoteHostAddress() {
		return remoteHostAddress;
	}
	/**
	 * return remote port of this connection
	 */
	public int getRemotePort() {
		return remotePort;
	}
	/**
	 * return true if this connection is already connected
	 */
	public boolean isConnected(){
		if(channel==null){
			return false;
		}
		return channel.isActive();
	}
	/**
	 * close this session 
	 */
	public void close(){
		try {
			if(channel==null){
				return;
			}
			channel.close().sync();
			channel=null;
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}
	}
	/**
	 * get total received package count in bytes
	 * @return total received package count in bytes
	 */
	public long getReceivedPackageCount(){
		return receivedPackageCount.longValue();
	}
	/**
	 * get total pushed package count in bytes
	 * @return total received package count in bytes
	 */
	public long getPushedPackageCount(){
		return pushedPackageCount.longValue();
	}
	/**
	 * get total sent package count in bytes
	 * @return total sent package count in bytes
	 */
	public long getSentPackageCount(){
		return sentPackageCount.longValue();
	}
	//
	public long getTotalNetworkTime(){
		return totalNetworkTime.longValue();
	}
	//
	public long getMinNetworkTime(){
		return minNetworkTime;
	}
	//
	public long getMaxNetworkTime(){
		return maxNetworkTime;
	}
	//
	void receivePackage(RpcMessage message){
		message.reveicedTime=System.currentTimeMillis();
		long networkTime=message.reveicedTime-message.sentTime;
		totalNetworkTime.add(networkTime);
		if(networkTime>maxNetworkTime){
			maxNetworkTime=networkTime;
		}
		if(networkTime<minNetworkTime){
			minNetworkTime=networkTime;
		}
		receivedPackageCount.increment();
	}
	void pushPackage(){
		pushedPackageCount.increment();
	}
	/**
	 * subscribe event 
	 * @param topic
	 */
	public void subscribe(String topic){
		topics.add(topic);
	}
	/**
	 * get topic list of this session
	 * @return
	 */
	public Set<String> getTopics() {
		return topics;
	}
	/**
	 * return create time of thi session
	 * @return
	 */
	public Date getCreateTime(){
		return createTime;
	}
	/**
	 * return if client disable push message 
	 * @return if client disable push message 
	 */
	public boolean isDisablePushMessage(){
		return disablePushMessage;
	}
	/**
	 * set  whether or not client disable server push message 
	 * @param dpm whether or not  client disable server push message 
	 */
	public void setDisablePushMessage(boolean dpm){
		disablePushMessage=dpm;
	}
	/**
	 * write message to other side
	 */
	void write(RpcMessage message){
		if(channel!=null){
			if(message.sentTime==0){
				message.sentTime=System.currentTimeMillis();
			}
			channel.writeAndFlush(message);
			sentPackageCount.increment();			
		}
	}
	//
	@Override
	public String toString() {
		return "[RPCSession]"+principal+"/"+remoteHostAddress+":"+remotePort+"/auth:"+authed+"/ssl:"+enableSSL;
	}
}

package jazmin.server.file;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.misc.io.IOWorker;

import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;

/**
 * @author yama
 * 23 Dec, 2014
 */
public class FileClient {
	private static final Logger logger=LoggerFactory.get(FileClient.class);
	//
	private EventLoopGroup group;
	private Bootstrap bootstrap;
	HttpDataFactory factory;
	AsyncHttpClientConfig.Builder clientConfigBuilder;
	AsyncHttpClientConfig clientConfig;
	AsyncHttpClient asyncHttpClient;
	
	//
	public FileClient() {
		clientConfigBuilder=new Builder();
		clientConfigBuilder.setUserAgent("JazminFileClient");
		clientConfigBuilder.setAsyncHttpClientProviderConfig(new NettyAsyncHttpProviderConfig());
		clientConfig=clientConfigBuilder.build();
		asyncHttpClient = new AsyncHttpClient(clientConfig);
		initNettyConnector();
	}
	//
	public void stop(){
		factory.cleanAllHttpData();
		group.shutdownGracefully();
	}
	//
	private void initNettyConnector(){
		IOWorker worker=new IOWorker("FileClientIO",1);
		group = new NioEventLoopGroup(1,worker);
		bootstrap = new Bootstrap();
		factory= new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); 
		// Disk if MINSIZE exceed
	    DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
	    DiskFileUpload.baseDirectory = null; // system temp directory
	    DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
	    DiskAttribute.baseDirectory = null; // system temp directory
		//clientHandler=new RpcClientHandler(this);
		ChannelInitializer <SocketChannel>channelInitializer=
				new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("codec", new HttpClientCodec());
		        pipeline.addLast("inflater", new HttpContentDecompressor());
		        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
		        pipeline.addLast("handler", new HttpUploadClientHandler());
			}
		};
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 32*1024) 
        .option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 8*1024)
		.handler(channelInitializer);
	}
	
	//
	/**
	 * upload file to server
	 * @param serverUrl
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String upload(String serverUrl,File file) throws Exception {
		if(!file.exists()){
			throw new IllegalArgumentException("can not find file "+file);
		}
		URI simpleURI=new URI(serverUrl);
		String host=simpleURI.getHost();
        int port=simpleURI.getPort();
        String path=simpleURI.getPath();
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        Channel channel = future.sync().channel();
        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, path);
        
        HttpPostRequestEncoder bodyRequestEncoder =
                new HttpPostRequestEncoder(factory, request, true); 
        bodyRequestEncoder.addBodyFileUpload("file", file, "application/x-zip-compressed", false);
        request = bodyRequestEncoder.finalizeRequest();
        request.headers().setLong("Content-Length", file.length());
        channel.write(request);
        if (bodyRequestEncoder.isChunked()) { 
            channel.write(bodyRequestEncoder);
        }
        channel.flush();
        bodyRequestEncoder.cleanFiles();
        channel.closeFuture().sync();
        String result=channel.attr(HttpUploadClientHandler.ATTR_RESULT).get();
        return result;
    }
	/**
	 * 
	 * @author yama
	 * 9 Apr, 2016
	 */
	public static class DownloadAsyncHandler implements AsyncHandler<String>{
		private File tempFile;
		private FileOutputStream tempFileOutputStream;
		private long totalBytes;
		private String fileId;
		private File file;
		public DownloadAsyncHandler(String fileId,File file) {
			this.fileId=fileId;
			this.file=file;
		}
		//
		@Override
		public com.ning.http.client.AsyncHandler.STATE onBodyPartReceived(
				HttpResponseBodyPart part) throws Exception {
			byte partBytes[]=part.getBodyPartBytes();
			tempFileOutputStream.write(partBytes);
			return STATE.CONTINUE;
		}

		@Override
		public String onCompleted() throws Exception {
			if(tempFileOutputStream!=null){
				if(logger.isDebugEnabled()){
					logger.debug("complete fetch {}",fileId);
				}
				//
				try{
					tempFileOutputStream.flush();
					tempFileOutputStream.close();		
				}catch(Exception e){
					logger.catching(e);	
				}
				
				if(!file.exists()){
					Files.move(tempFile.toPath(),file.toPath(), StandardCopyOption.ATOMIC_MOVE);
					if(logger.isDebugEnabled()){
						logger.debug("move file {} to {}",tempFile,file);
					}
				}else{
					logger.warn("file {} already exists,delete temp file {}",file,tempFile);
					tempFile.delete();
				}
			}
			return "";
		}

		@Override
		public com.ning.http.client.AsyncHandler.STATE onHeadersReceived(
				HttpResponseHeaders headers) throws Exception {
			String len=headers.getHeaders().getFirstValue("Content-Length");
			if(len!=null){
				totalBytes=Long.valueOf(len);
			}
			if(logger.isDebugEnabled()){
				logger.debug("got length {} bytes from fileId {}",totalBytes,fileId);
			}
			tempFile=new File(file.getAbsoluteFile()+".tmp");
			if(!tempFile.getParentFile().exists()){
				boolean success=tempFile.getParentFile().mkdirs();
				if(!success){
					logger.error("can not mkdir {}",tempFile.getParentFile());
				}
			}	
			tempFile.createNewFile();
			tempFileOutputStream=new FileOutputStream(tempFile);
			return STATE.CONTINUE;
		}

		@Override
		public com.ning.http.client.AsyncHandler.STATE onStatusReceived(
				HttpResponseStatus status) throws Exception {
			if(logger.isDebugEnabled()){
				logger.debug("got status {} {}",status.getUri(),status.getStatusCode());
			}
			if(status.getStatusCode()!=200){
				return STATE.ABORT;
			}
			return STATE.CONTINUE;
		}

		@Override
		public void onThrowable(Throwable e) {
			if(tempFileOutputStream!=null){
				try {
					tempFileOutputStream.close();
				} catch (IOException e1) {
					logger.catching(e1);
				}
			}
			if(tempFile!=null){
				boolean success=tempFile.delete();
				logger.debug("delete temp file {} result {}",tempFile,success);
			}
			if(e instanceof IOException){
				logger.warn("uri {} catch exception {}",fileId,e);
			}else{
				logger.catching(e);
			}
		}
		
	}
	/**
	 * download file from server
	 * @param url
	 * @param targetFile
	 * @param handler
	 */
	public void download(String url,File targetFile)throws Exception{
		download(url, targetFile,60*10);
	}
	//
	/**
	 * download file from server
	 * @param url
	 * @param targetFile
	 * @param handler
	 */
	public void download(String url,File targetFile,int timeoutSeconds)throws Exception{
		DownloadAsyncHandler asyncHandler=new DownloadAsyncHandler(url,targetFile);
		asyncHttpClient.prepareGet(url).execute(asyncHandler).get(timeoutSeconds,TimeUnit.SECONDS);
	}
}

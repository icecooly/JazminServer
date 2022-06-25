/**
 * 
 */
package jazmin.server.cdn;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;


/**
 * @author yama
 *
 */
public class OtherRequestWorker extends RequestWorker{
	
	OtherRequestWorker(CdnServer cdnServer, ChannelHandlerContext ctx,
			DefaultHttpRequest request) {
		super(cdnServer, ctx, request);
	}

	public void processRequest(){
		sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	public void handleHttpContent(DefaultHttpContent content) {
		
	}

	@Override
	public void channelClosed() {
		
	}
	
}

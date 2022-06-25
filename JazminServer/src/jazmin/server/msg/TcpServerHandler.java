package jazmin.server.msg;

import java.io.IOException;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.msg.codec.RequestMessage;
/**
 * 
 * @author yama
 * 26 Dec, 2014
 */
@Sharable
public class TcpServerHandler extends ChannelHandlerAdapter{
	private static Logger logger=LoggerFactory.get(TcpServerHandler.class);
	private static final AttributeKey<Session> SESSION_KEY=
								AttributeKey.valueOf("s");
	private MessageServer messageServer;
	public TcpServerHandler(MessageServer messageServer) {
		this.messageServer=messageServer;
	}
	//
	@Override
    public void channelInactive(ChannelHandlerContext ctx) 
    		throws Exception {
    	Session session=ctx.channel().attr(SESSION_KEY).get();
    	messageServer.sessionDisconnected(session);
	}
	//
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) 
    		throws Exception {
		//
		RequestMessage reqMessage=(RequestMessage) msg;
		Session session=ctx.channel().attr(SESSION_KEY).get();
		messageServer.receiveMessage(session, reqMessage);
	}
	//
	@Override
	public void channelActive(ChannelHandlerContext ctx) 
			throws Exception {
		Session session=new Session(new NettyNetworkChannel(ctx.channel()));
		ctx.channel().attr(SESSION_KEY).set(session);
		messageServer.sessionCreated(session);
	}
	//
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if(evt instanceof IdleStateEvent){  
			Session session=ctx.channel().attr(SESSION_KEY).get();
			messageServer.sessionIdle(session);
        }    
	}
    //
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
    		throws Exception {
    	if(cause instanceof IOException){
    		logger.warn("exception on channal:"+ctx.channel()+","+cause.getMessage());
    	}else{
    		logger.error("exception on channal:"+ctx.channel(),cause);	
    	}
    	ctx.close();
    }
}

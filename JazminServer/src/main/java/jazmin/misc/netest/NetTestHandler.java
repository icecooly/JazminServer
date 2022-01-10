package jazmin.misc.netest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author yama
 * 9 May, 2015
 */
public class NetTestHandler extends ChannelHandlerAdapter {
	private static Logger logger=LoggerFactory.getLogger(NetTestHandler.class);
	NetTestClient netTestClient;
	public NetTestHandler(NetTestClient client) {
		this.netTestClient=client;
	}
	//
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	try {
			netTestClient.onConnect();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    	try {
			netTestClient.onDisConnect();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	try {
    		ByteBuf buffer=(ByteBuf) msg;
    		netTestClient.onMessage(Unpooled.copiedBuffer(buffer));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    }
    //
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error(cause.getMessage(),cause);
    	ctx.close();
    }
}
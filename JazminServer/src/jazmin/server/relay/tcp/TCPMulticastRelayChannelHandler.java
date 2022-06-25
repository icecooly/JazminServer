/**
 * 
 */
package jazmin.server.relay.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.relay.RelayChannel;

/**
 * @author yama
 * 26 Apr, 2015
 */
@Sharable
public class TCPMulticastRelayChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private static Logger logger=LoggerFactory.get(RelayChannel.class);
	//
	TCPMulticastRelayChannel relayChannel;
	public TCPMulticastRelayChannelHandler(TCPMulticastRelayChannel relayChannel) {
		this.relayChannel=relayChannel;
	}
	//
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			ByteBuf buffer) throws Exception {
		ByteBuf buf= Unpooled.copiedBuffer(buffer);
		relayChannel.dataFromPeer(
				(InetSocketAddress) ctx.channel().remoteAddress(),
				ctx.channel(),
				buf.array());
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
	}
	//
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel active :"+ctx.channel());
		relayChannel.peerConnectionActive(ctx.channel());
	}
	//
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel inactive :"+ctx.channel());
		relayChannel.peerConnectionInactive(ctx.channel());
		
	}
}

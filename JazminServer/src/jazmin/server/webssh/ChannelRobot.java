/**
 * 
 */
package jazmin.server.webssh;

/**
 * @author yama
 *
 */
public class ChannelRobot implements ChannelListener{
	@Override
	public void onOpen(WebSshChannel channel) {
		
	}
	//
	@Override
	public void onClose(WebSshChannel channel) {
		
	}
	//
	@Override
	public void onMessage(WebSshChannel channel, String message) {
		
	}
	//
	@Override
	public void onTicket(WebSshChannel channel, long ticket) {
		
	}
	//
	@Override
	public void onInput(WebSshChannel channel, String message) {
		
	}
	//
	@Override
	public boolean inputSendToServer() {
		return true;
	}
}

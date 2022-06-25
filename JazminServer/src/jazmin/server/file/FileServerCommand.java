package jazmin.server.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jazmin.core.Jazmin;
import jazmin.server.console.builtin.ConsoleCommand;
import jazmin.util.DumpUtil;

/**
 * 
 * @author yama 26 Dec, 2014
 */
public class FileServerCommand extends ConsoleCommand {
	private FileServer server;

	public FileServerCommand() {
		super(true);
		id = "filesrv";
		desc = "file server ctrl command";
		addOption("i", false, "show server information.", this::showServerInfo);
		addOption("r", false, "show file requests.", this::showRequests);
		//
		server = Jazmin.getServer(FileServer.class);
	}
	//
	@Override
	public void run() throws Exception {
		if (server == null) {
			out.println("can not find SipServer.");
			return;
		}
		super.run();
	}
	//
	private void showServerInfo(String args) {
		out.println(server.info());
	}
	//
	private Map<String,Long>lastTransferBytes=new HashMap<String, Long>();
	//
	private void showRequests(String args){
    	List<FileOpt>requests=server.getFileRequests();
    	out.format("total %d requests\n",requests.size());
    	String format="%-5s %-10s %-20s %-20s %-15s %-15s %-10s %-30s\n";
    	out.printf(format,
				"#",
				"ID",
    			"REMOTEADDRESS",
    			"TRANS",
    			"RATE",
    			"CREATETIME",
    			"SOURCE",
    			"URI");
    	int idx=1;
    	long totalRate=0;
    	for(FileOpt r:requests){
    		//
    		long lastBytes=lastTransferBytes.getOrDefault(r.id,r.transferedBytes);
    		String transfer=DumpUtil.byteCountToString(r.transferedBytes)+"/"+
    				DumpUtil.byteCountToString(r.totalBytes);
    		long currentRate=r.transferedBytes-lastBytes;
    		String rate=DumpUtil.byteCountToString(currentRate)+"/s";
    		totalRate+=currentRate;
    		//
    		out.printf(format,
        			idx++,
        			r.id,
        			r.remoteAddress.getAddress().getHostAddress()+":"+r.remoteAddress.getPort(),
        			transfer,
        			rate,
        			formatDate(r.createTime),
        			r.sourceType,
        			r.uri);
    		lastTransferBytes.put(r.id,r.transferedBytes);
    	}
    	//
    	out.println("total rate:"+DumpUtil.byteCountToString(totalRate)+"/s");
    }
}

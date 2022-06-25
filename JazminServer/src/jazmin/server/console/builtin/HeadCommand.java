package jazmin.server.console.builtin;

import java.util.List;

import jazmin.util.IOUtil;


/**
 * 
 * @author yama 26 Dec, 2014
 */
public class HeadCommand extends ConsoleCommand {
	public HeadCommand() {
		super(false);
		id = "head";
		desc = "display first lines of strings";
		addOption("n", true, "line number", null);
		addOption("v", false, "invert select", null);	
	}
	//
	@Override
	protected void run() throws Exception {
		if(!isPiped()){
			printHelp();
			return;
		}
		List<String>inputLines=IOUtil.getContentList(inStream);
		int number=inputLines.size();
		try{
			if(cli.hasOption('n')){
				number=Integer.valueOf(cli.getOptionValue('n'));
			}
		}catch(Exception e){}
		if(cli.hasOption('v')){
			//skip
			for(int i=number;i<inputLines.size();i++){
				out.println(inputLines.get(i));
			}
		}else{
			for(int i=0;i<number&&i<inputLines.size();i++){
				out.println(inputLines.get(i));
			}	
		}
	}
	
}

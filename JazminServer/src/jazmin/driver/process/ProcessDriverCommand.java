package jazmin.driver.process;
import java.util.List;

import jazmin.core.Jazmin;
import jazmin.server.console.builtin.ConsoleCommand;
import jazmin.util.DumpUtil;
/**
 * 
 * @author yama
 * 26 Dec, 2014
 */
public class ProcessDriverCommand extends ConsoleCommand {
    private ProcessDriver driver;
	public ProcessDriverCommand() {
    	super(true);
    	id="process";
    	desc="process driver ctrl command";
    	addOption("i",false,"show driver information.",this::showDriverInfo);
    	addOption("p",false,"show process list.",this::showProcesses);
    	addOption("pi",true,"show process info.",this::showProcessInfo);
    	driver=Jazmin.getDriver(ProcessDriver.class);
    }
	//
	@Override
	public void run() throws Exception{
		 if(driver==null){
			 out.println("can not find ProcessDriver.");
			 return;
		 }
		 super.run();
	}
    //
    private void showDriverInfo(String args){
    	out.println(driver.info());
	}
    //
    private void showProcesses(String args){
		String format="%-5s : %-20s %-30s %-10s\n";
		int i=1;
		List<ProcessInfo> ps=driver.getProcesses();
		out.println("total "+ps.size()+" processes");
		out.format(format,
				"#",
				"ID",
				"EXEC",
				"CREATETIME");	
		for(ProcessInfo p:ps){
			out.format(format,
					i++,
					p.getId(),
					p.getCommands()[0],
					formatDate(p.createTime));
		}
	}
    private void showProcessInfo(String args){
    	ProcessInfo pi=driver.getProcess(args.trim());
    	if(pi==null){
    		out.println("can not found process with id:"+args);
    		return;
    	}
    	//
    	out.println(DumpUtil.dump(pi));
    }
}

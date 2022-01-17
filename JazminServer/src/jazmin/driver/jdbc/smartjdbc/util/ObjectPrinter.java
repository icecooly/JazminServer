package jazmin.driver.jdbc.smartjdbc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author icecooly
 *
 */
public class ObjectPrinter {
	//
	private static final int TOTAL_LENGTH=100;
	//
	private StringWriter sw;
	private PrintWriter pw;
	private String format;
	public ObjectPrinter() {
		this("%-20s:%-20s\n");
	}
	public ObjectPrinter(String format) {
		sw=new StringWriter();
		pw=new PrintWriter(sw);
		this.format=format;
	}
	//
	public ObjectPrinter print(Object ...args){
		pw.printf(format, args);
		return this;
	}
	//
	public ObjectPrinter section(String title){
		int index=0;
		for(int i=0;i<(TOTAL_LENGTH-title.length())/2;i++){
			pw.append("-");
			index++;
		}
		pw.append(title);
		index+=title.length();
		for(int i=index;i<TOTAL_LENGTH;i++){
			pw.append("-");
		}
		pw.append("\n");
		return this;
	}
	//
	public ObjectPrinter secondSection(String title){
		pw.append("<");
		pw.append(title);
		pw.append(">");
		pw.append("\n");
		return this;
	}
	//
	public ObjectPrinter println(Object s){
		pw.println(s);
		return this;
	}
	//
	public String toString(){
		return sw.toString();
	}
}

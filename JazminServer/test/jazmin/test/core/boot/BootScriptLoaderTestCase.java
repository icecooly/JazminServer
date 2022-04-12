package jazmin.test.core.boot;

import java.io.FileInputStream;

import jazmin.core.boot.BootScriptLoader;

/**
 * 
 * @author skydu
 *
 */
public class BootScriptLoaderTestCase {

	public static void main(String[] args) throws Exception {
		BootScriptLoader bsl=new BootScriptLoader(new FileInputStream("script/jazmin.js"));
		bsl.load();
	}
}

package jazmin.core.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;

/**
 * 
 * @author yama 4 Jan, 2015
 */
public class JazminClassloader extends URLClassLoader {
	//
	private static Logger logger=LoggerFactory.get(JazminClassloader.class);
	//
	public JazminClassloader(File workImage,List<String> extendJarList) throws MalformedURLException {
		super(getJarUrl(workImage,extendJarList),JazminClassloader.class.getClassLoader());
	}
	//
	static void getJarFiles(List<URL>result,File dir) {
		if(dir.isFile()&&dir.getAbsolutePath().endsWith(".jar")) {
			try {
				result.add(dir.toURI().toURL());
			} catch (MalformedURLException e) {
				logger.catching(e);
			}
		}
		if(dir.isDirectory()) {
			File children[]=dir.listFiles();
			for(File e:children) {
				JazminClassloader.getJarFiles(result,e);
			}
		}
	}
	//
	static URL[] getJarUrl(File workImage,List<String> extendJarList) {
		List<URL> urls=new ArrayList<>();
		try {
			urls.add(workImage.toURI().toURL());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		}
		String expandPath=workImage.getAbsolutePath();
		expandPath=expandPath.substring(0,expandPath.length()-4);
		//
		File expandLibFile=new File(expandPath+"/lib");
		if(expandLibFile.exists()) {
			getJarFiles(urls,expandLibFile);
		}
		//
		if(extendJarList!=null&&!extendJarList.isEmpty()) {
			for (String jarPath : extendJarList) {
				File extendJar=new File(jarPath);
				if(!extendJar.exists()) {
					logger.error("jar file not found "+jarPath);
					continue;
				}
				getJarFiles(urls,extendJar);
			}
		}
		//
		logger.info("classpath urls:");
		urls.forEach((u)->{
			logger.info("URI:{}",u);
		});
		return urls.toArray(new URL[urls.size()]);
	}

}

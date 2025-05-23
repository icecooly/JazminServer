package jazmin.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

/**
 * 
 * @author yama 22 Dec, 2014
 */
public class LoggerFactory {

	private static Map<String, Level> logLevelMap = new HashMap<String, Level>();
	static {
		logLevelMap.put("ALL", Level.ALL);
		logLevelMap.put("DEBUG", Level.DEBUG);
		logLevelMap.put("INFO", Level.INFO);
		logLevelMap.put("WARN", Level.WARN);
		logLevelMap.put("ERROR", Level.ERROR);
		logLevelMap.put("FATAL", Level.FATAL);
		logLevelMap.put("OFF", Level.OFF);

	}
	private static Level level;
	private static boolean isConsoleLogEnabled;
	private static List<jazmin.log.Logger> allLogList;
	private static Log4j2ConfigurationFactory configFactory = new Log4j2ConfigurationFactory();
	static {
		//
		isConsoleLogEnabled = true;
		level = Level.INFO;
		PluginManager.addPackage("jazmin.log");
		ConfigurationFactory.setConfigurationFactory(configFactory);
		configFactory.getConfiguration().start();
		allLogList = new ArrayList<>();
	}

	//
	public static void disableConsoleLog() {
		if (!isConsoleLogEnabled) {
			return;
		}
		isConsoleLogEnabled = false;
		configFactory.getConfiguration().disableConsoleOutput();
	}

	//
	public static boolean isConsoleLogEnabled() {
		return isConsoleLogEnabled;
	}

	/**
	 * @param file String
	 */
	public static void setFile(String file) {
		configFactory.getConfiguration().setFile(file);
	}

	/**
	 * @param file String
	 */
	public static void setFile(String file, boolean immediateFlush) {
		configFactory.getConfiguration().setFile(file, immediateFlush);
	}

	public static void setFile(String file, boolean immediateFlush,  boolean disableColor) {
		configFactory.getConfiguration().setFile(file, immediateFlush, disableColor);
	}

	//
	public static String getFile() {
		return configFactory.getConfiguration().getFile();
	}

	//
	/**
	 */
	public static Logger get(Class<?> clazz) {
		org.apache.logging.log4j.Logger logger = LogManager.getLogger(clazz);
		jazmin.log.Logger warpLogger = new jazmin.log.Logger(logger);
		allLogList.add(warpLogger);
		return warpLogger;
	}

	public static Logger getLogger(Class<?> clazz) {
		return get(clazz);
	}

	// set log level
	/**
	 * 
	 */
	public static void setLevel(String logLevel) {
		Level theLevel = logLevelMap.get(logLevel);
		if (theLevel == null) {
			throw new IllegalArgumentException("can not find specified log level:" + logLevel);
		} else {
			configFactory.getConfiguration().setLogLevel(theLevel);
			level = theLevel;
			for (Logger logger : LoggerFactory.getLoggers()) {
				org.apache.logging.log4j.Logger realLogger = logger.getRealLogger();
				// 这个类需要单独setlevel
				if (realLogger instanceof org.apache.logging.log4j.core.Logger) {
					org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) realLogger;
					coreLogger.setLevel(theLevel);
				}
			}
		}
	}

	//
	public static String getLevel() {
		return level.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static List<Logger> getLoggers() {
		ArrayList<Logger> allLogs = new ArrayList<Logger>();
		synchronized (allLogList) {
			allLogs.addAll(allLogList);
		}
		return allLogs;
	}

	//
	public static void stop() {
		configFactory.getConfiguration().stop();
	}
}

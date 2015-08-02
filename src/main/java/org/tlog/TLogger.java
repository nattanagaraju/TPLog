package org.tlog;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TLogger{
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static boolean isInfo = new Boolean(TLogEnvProperties.getProp("INFO"));
	static{
		TLogger.initialize();
	}
	
	private static String buildMsg(String source, String methodName, String message){
		StringBuilder builder = new StringBuilder(source);
		builder.append(".").append(methodName).append(": ").append(message);
		return builder.toString();
	}
	
	public static void logInfo(String source, String methodName, String message){
		if(isInfo){
			logger.info(buildMsg(source, methodName, message));
		}
	}
	
	public static void logError(String source, String methodName, String message){
		logger.log(TLogLevel.ERROR, buildMsg(source, methodName, message));
	}
	
	public static void logError(String source, String methodName, String message, Throwable throwable){
		logger.log(TLogLevel.ERROR, buildMsg(source, methodName, message), throwable);
	}
	
	public static void logWarning(String source, String methodName, String message){
		logger.log(TLogLevel.WARNING, buildMsg(source, methodName, message));
	}
	
	public static void logWarning(String source, String methodName, String message, Throwable throwable){
		logger.log(TLogLevel.WARNING, buildMsg(source, methodName, message), throwable);
	}
	
	public static void logTPReport(String message){
		logger.log(TLogLevel.REPORT, message);
	}
	
	private static void initialize(){
		try {
			// suppress the logging output to the console
			Map<String, Logger> loggers = new HashMap<String, Logger>();
			loggers.put("logger", logger);
			for(Entry<String, Logger> entry: loggers.entrySet()){
				Logger log = entry.getValue();
				log.setUseParentHandlers(false);
				Handler[] handlers = log.getHandlers();
			    for(Handler hand: handlers){
			    	 if (hand instanceof ConsoleHandler) {
			    		 log.removeHandler(hand);
			   	    }
			    }
			    // log handler
			    Handler handler = null;
			    if(entry.getKey() == "reportlogger"){
				    handler = new FileHandler("C:\\report.log");
				    log.setLevel(TLogLevel.REPORT);
			    }else{
			    	 handler = new FileHandler("C:\\info.log");
					    log.setLevel(Level.INFO);
			    }
			    handler.setFormatter(new TLogFormatter());
			    log.addHandler(handler);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}

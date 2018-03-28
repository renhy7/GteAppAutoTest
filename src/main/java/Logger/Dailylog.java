package Logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Dailylog {
	private static Logger logger = Logger.getLogger("Dailylog");
	private static boolean flag = false;
	private static synchronized void getPropertyFile(){
		try{
			PropertyConfigurator.configure("log4j.properties");
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void getFlag(){
		if(flag == false){
			getPropertyFile();
		}
	}
	public static void logInfo(Object message){
		getFlag();
		logger.info(message);
		
	}
	public static void logError(Object message, String string){
		getFlag();
		logger.error(message);
	}
	public static void logError(Object message){
		getFlag();
		logger.error(message);
	}
	public static void logError(Object message, Throwable t){
		getFlag();
		logger.error(message, t);
	}
	public static void logWarn(Object message){
		getFlag();
		logger.warn(message);
	}

}

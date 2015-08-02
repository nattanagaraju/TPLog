package org.tlog;

import java.util.Map;

public class TPath {
	private static ThreadLocal<Map<String, Object>> threadlocal = new ThreadLocal<Map<String, Object>>();
	
	public static void setThreadVal(Map<String, Object> val){
		threadlocal.set(val);
	}
	public static void removeThreadVal(){
		threadlocal.remove();
	}
	public static Map<String, Object> getThreadVal(){
		return threadlocal.get();
	}
	
	public static void startTPath(String compName){
		int index = ((Integer)getThreadVal().get("tcount")) + 1;
		
		getThreadVal().put("tcount", index);
		getThreadVal().put(compName, index);
		getThreadVal().put("comp_index_"+index+"_start", System.currentTimeMillis());
		getThreadVal().put("comp_index_"+index+"_msg", new StringBuilder("{TPath-"+compName).append("{"));
	}
	
	public static void endTPath(String compName, String source, String methodName, 
			String statusMsg, String statusCode){
		int index = (Integer)getThreadVal().get(compName);
		StringBuilder builder = (StringBuilder)getThreadVal().get("comp_index_"+index+"_msg");
		builder.append(source).append(".").append(methodName);
		builder.append("~").append(statusMsg).append("~").append(statusCode);
		builder.append("~").append(System.currentTimeMillis()-(Long)getThreadVal().get("comp_index_"+index+"_start"));
		builder.append("~").append(TPath.getThreadVal().get("context-root"));
		
	}
	
	public static void addTPath(String tpath, String compName){
		int index = (Integer)getThreadVal().get(compName);
		((StringBuilder)getThreadVal().get("comp_index_"+index+"_msg")).append(tpath);
	}
	
	public static String execTPathReport(String compName, Map<String, Object> logParams){
		int index = (Integer)getThreadVal().get(compName);
		int totIndex = (Integer)getThreadVal().get("tcount");
		StringBuilder logBuilder = null;
		StringBuilder tempBuilder = null;
		for(int i=totIndex; i>=index; i--){
			tempBuilder = new StringBuilder();
			tempBuilder.append(((StringBuilder)getThreadVal().get("comp_index_"+i+"_msg")).toString()).append("}}");
			if(i > 1){
				logBuilder = (StringBuilder)getThreadVal().get("comp_index_"+(i-1)+"_msg");
				logBuilder.append(tempBuilder.toString());
			}else{
				logBuilder = tempBuilder;
			}
		}
		if(logParams != null){
			logBuilder.append("{");
			logBuilder.append("LogParams: ").append(logParams.toString());
			logBuilder.append("}");
		}
		TLogger.logTPReport(logBuilder.toString());
		return logBuilder.toString(); 
	}
	
}

package com.Jarvis.Library;


public class FrameWorkConstants {


	private static final String RESOURCEPATH=System.getProperty("user.dir")+"/src/test/resources";
	private static final String filePath=FrameWorkConstants.getResourcepath()+"C:\\Users\\t\\eclipse-workspace-1\\JARVIS_Protect\\src\\main\\resources\\Excel\\PortfolioSyncUpload.xlsx";
	
	
	
	private FrameWorkConstants() {
	}
	
	public static String getFilePath() {
		return filePath;
	}
	
	public static String getResourcepath() {
		return RESOURCEPATH;
	}

}

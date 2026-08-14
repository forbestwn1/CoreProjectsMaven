package com.nosliw.common.utils;

public class HAPUtilityFileName {
	
	public static String getValidFileName(String fileFullName){
		int index = fileFullName.lastIndexOf("/");
		if(index==-1) {
			index = fileFullName.lastIndexOf("\\");
		}
		
		String path = null;
		String fileName = null;
		if(index==-1) {
			fileName = fileFullName;
		} else{
			fileName = fileFullName.substring(index+1);
			path = fileFullName.substring(0, index+1);
		}
		
		fileName = encodeName(fileName);

		String out = "";
		if(path!=null) {
			out = out + path;
		}
		out = out + fileName;
		return out;
	}

	public static String encodeName(String name) {
		String out = name;
		char[] invalidChars = {'|', ':', '*'};
		for(char invalidChar : invalidChars){
			out = out.replace(invalidChar, '_');
		}
		return out;
	}

	public static String normalizeFolderPath(String folder) {
		if(folder.endsWith("/")||folder.endsWith("\\")) {
			return folder;
		} else {
			return folder + "/";
		}
	}
}

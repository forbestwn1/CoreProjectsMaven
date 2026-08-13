package com.nosliw.common.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HAPUtilityFileNio {

	public static String readFile(Path path, String...subPath){
		try {
			return Files.readString(buildPath(path, subPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readFile(String filePath){
		Path path = buildPath(filePath); 
		return readFile(path);
	}
	
	
	
	public static List<Path> getChildrenPath(Path path){
		try {
			return Files.list(path).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<Path>();
		}
	}
	
	public static Path buildPath(Path path, String...segs) {
		Path out = path;
		for(String seg : segs) {
			out = out.resolve(seg);
		}
		return out;
	}
	
	public static Path buildPath(String basePath, String... segs) {
		Path path = null;
		try {
			path = Paths.get(new URI(basePath));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		
		if(path!=null) {
			path = buildPath(path, segs);
		}
		return path;
	}
	
	public static String getLastNameOfPath(Path path) {
		return path.getFileName().toString();
	}
	
}

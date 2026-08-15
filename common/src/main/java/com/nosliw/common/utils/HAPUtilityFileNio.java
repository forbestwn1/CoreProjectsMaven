package com.nosliw.common.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nosliw.common.serialization.HAPUtilityJson;

public class HAPUtilityFileNio {

	public static Path getOrCreateFolder(Path folderPath) {
		try {
			return Files.createDirectories(folderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deletePath(Path pathToBeDeleted) {
		if(isPathExists(pathToBeDeleted)) {
			try (Stream<Path> walk = Files.walk(pathToBeDeleted)) {
	            walk.sorted(Comparator.reverseOrder())
	                .forEach(path -> {
	                    try {
	                        Files.delete(path);
	                    } catch (IOException e) {
	                        System.err.printf("Failed to delete %s: %s%n", path, e.getMessage());
	                    }
	                });
	        } catch (IOException e) {
	            System.err.println("Failed to walk the path: " + e.getMessage());
	        }
		}
	}
	
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
	
	public static String writeFile(Path folder, String fileName, String content){
		fileName = HAPUtilityFileName.getValidFileName(fileName);
		
		try {
			Files.createDirectories(folder);
			Files.writeString(HAPUtilityFileNio.buildPath(folder, fileName), content==null?"":content);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String writeJsonFile(Path folder, String fileName, String content) {    
		return writeFile(folder, fileName, HAPUtilityJson.formatJson(content));     
	}
	
	public static List<Path> getChildrenSortedByName(Path folder){
		return sortFiles(getChildrenPath(folder));
	}
	
	public static List<Path> getChildrenPath(Path path){
		try {
			if(!isPathExists(path)) {
				return new ArrayList<Path>();
			}
			
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
	
	public static String getFileNameWithoutExtension(Path path) {
		String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        return baseName;
	}
	
	public static boolean isPathExists(Path path) {    return Files.exists(path);       }
	
	public static boolean isFile(Path path) {    return Files.isRegularFile(path);     }
	public static boolean isDictory(Path path) {    return !Files.isRegularFile(path);     }
	
	public static List<Path> sortFiles(List<Path> files){
		List<Path> sortedList = new ArrayList<Path>(files);
		Collections.sort(sortedList, new Comparator<Path>() {

			@Override
			public int compare(Path arg0, Path arg1) {
				return arg0.getFileName().toString().compareTo(arg1.getFileName().toString());
			}
		});
		return sortedList;
	}

}

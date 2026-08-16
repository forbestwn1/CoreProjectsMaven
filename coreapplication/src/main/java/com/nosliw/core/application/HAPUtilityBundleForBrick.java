package com.nosliw.core.application;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPUtilityBundleForBrick {

	public static String normalizePathWithBranch(String path, String defaultBranch) {
		String out = path;
		if(path==null||!path.startsWith(HAPConstantShared.SYMBOL_KEYWORD)) {
			out = HAPUtilityNamingConversion.cascadePath(defaultBranch, path);
		}
		return out;
	}
	
	public static HAPComplexPath getBrickFullPathInfo(String path, String defaultBranch) {
		HAPPath pathNorm = new HAPPath(normalizePathWithBranch(path, defaultBranch));
		Pair<String, HAPPath> pathPair = pathNorm.trimFirst();
		return new HAPComplexPath(pathPair.getLeft(), pathPair.getRight());
	}

	public static HAPComplexPath getBrickFullPathInfo(String path) {
		return getBrickFullPathInfo(new HAPPath(path));
	}
	
	public static HAPComplexPath getBrickFullPathInfo(HAPPath path) {
		Pair<String, HAPPath> pathPair = path.trimFirst();
		return new HAPComplexPath(pathPair.getLeft(), pathPair.getRight());
	}

}

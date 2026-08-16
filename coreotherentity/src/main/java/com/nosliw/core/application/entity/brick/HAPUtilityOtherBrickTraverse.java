package com.nosliw.core.application.entity.brick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPHandlerBrickWrapper;
import com.nosliw.core.application.HAPHandlerDownward;
import com.nosliw.core.application.HAPUtilityBrick;
import com.nosliw.core.application.HAPWithBrick;

public class HAPUtilityOtherBrickTraverse {

	public static void traverseTreeWithLocalBrick(HAPBundleForBrick bundle, String rootName, HAPHandlerDownward processor, HAPManagerApplicationBrick brickMan, Object data) {
		traverseTree(
			bundle, 
			rootName,
			new HAPHandlerBrickWrapper(processor) {
				@Override
				protected boolean isValidAttribute(HAPAttributeInBrick attr) {
					if(attr.getValueWrapper() instanceof HAPWithBrick) {
						return true;
					}
					return false;
				}
			}, 
			brickMan,
			data);
	}

	public static void traverseTree(HAPBundleForBrick bundle, String rootName, HAPHandlerDownward processor, HAPManagerApplicationBrick brickMan, Object data) {
		Set<HAPPath> validPath = new HashSet<HAPPath>();
		
		traversePreTree(bundle, new HAPPath(rootName), processor, brickMan, data, validPath);
		
		traversePostTree(bundle, new HAPPath(rootName), processor, brickMan, data, validPath);
	}
	
	private static void traversePreTree(HAPBundleForBrick bundle, HAPPath path, HAPHandlerDownward processor, HAPManagerApplicationBrick brickMan, Object data, Set<HAPPath> validPath) {
		validPath.add(path);
		if(processor.processBrickNode(bundle, path, data)) {
			HAPBrick leafBrick = HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
			
			if(leafBrick!=null) {
				//only process child for brick
				List<HAPAttributeInBrick> attrsExe = leafBrick.getAttributes();
				for(HAPAttributeInBrick attrExe : attrsExe) {
					HAPPath attrPath = new HAPPath(path).appendSegment(attrExe.getName());
					traversePreTree(bundle, attrPath, processor, brickMan, data, validPath);
				}
			}
		}
	}

	private static void traversePostTree(HAPBundleForBrick bundle, HAPPath path, HAPHandlerDownward processor, HAPManagerApplicationBrick brickMan, Object data, Set<HAPPath> validPath) {
		if(validPath.contains(path)) {
			processor.postProcessBrickNode(bundle, path, data);
			HAPBrick leafBrick = HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
			
			if(leafBrick!=null) {
				//only process child for brick
				List<HAPAttributeInBrick> attrsExe = leafBrick.getAttributes();
				for(HAPAttributeInBrick attrExe : attrsExe) {
					HAPPath attrPath = new HAPPath(path).appendSegment(attrExe.getName());
					traversePostTree(bundle, attrPath, processor, brickMan, data, validPath);
				}
			}
		}
	}

}

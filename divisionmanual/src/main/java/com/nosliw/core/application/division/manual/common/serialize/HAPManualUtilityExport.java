package com.nosliw.core.application.division.manual.common.serialize;

import java.nio.file.Path;

import org.json.JSONObject;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPAdapter;
import com.nosliw.core.application.brick.HAPAttributeInBrick;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPHandlerDownward;
import com.nosliw.core.application.brick.HAPUtilityBrick;
import com.nosliw.core.application.brick.HAPWrapperValue;
import com.nosliw.core.application.brick.HAPWrapperValueOfBrick;
import com.nosliw.core.application.common.brick.serialize.HAPUtilityExport;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrickTraverse;

public class HAPManualUtilityExport {

	public static HAPBundleForBrick deserializeBundle(JSONObject bundleJsonObj, HAPServiceParseEntity parseService, HAPManagerApplicationBrick brickMan, HAPManualManagerBrick manualBrickManager) {
		if(bundleJsonObj!=null) {
			HAPBundleForBrick out = (HAPBundleForBrick)parseService.parseEntityJSONExplicit(bundleJsonObj, HAPBundleForBrick.ENTITYNAMEFORSERIALIZE);
			for(String rootName : out.getAllRootBrickName()) {
				HAPUtilityOtherBrickTraverse.traverseTreeWithLocalBrick(out, rootName, new HAPHandlerDownward() {

					@Override
					public boolean processBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
						
						HAPBrick brick = HAPUtilityBrick.getDescdentBrickLocal(bundle, path);
						if(brick instanceof HAPManualBrick) {
							HAPManualBrick manualBrick = (HAPManualBrick)brick;
							manualBrick.setBundle(out);
							manualBrick.setManualBrickManager((HAPManualManagerBrick)data);
						}
						
						HAPAttributeInBrick attr = HAPUtilityBrick.getDescendantAttribute(bundle, path);
						if(attr!=null) {
							for(HAPAdapter adapter : attr.getAdapters()) {
								HAPWrapperValue valueWrapper = adapter.getValueWrapper();
								if(HAPConstantShared.ENTITYATTRIBUTE_VALUETYPE_BRICK.equals(valueWrapper.getValueType())) {
									HAPBrick brickInAdapter = ((HAPWrapperValueOfBrick)	valueWrapper).getBrick();
									if(brickInAdapter instanceof HAPManualBrick) {
										HAPManualBrick manualBrick = (HAPManualBrick)brickInAdapter;
										manualBrick.setBundle(out);
										manualBrick.setManualBrickManager((HAPManualManagerBrick)data);
									}
								}
							}
						}
						
						return true;
					}

					@Override
					public void postProcessBrickNode(HAPBundleForBrick bundle, HAPPath path, Object data) {
					}
					
				}, brickMan, manualBrickManager);
			}
			return out;
		}
		return null;
	}

	public static HAPBundleForBrick deserializeBundle(String bunldJsonStr, HAPServiceParseEntity parseService, HAPManagerApplicationBrick brickMan, HAPManualManagerBrick manualBrickManager) {
		if(bunldJsonStr!=null) {
			JSONObject bundleJsonObj = new JSONObject(bunldJsonStr);
			return deserializeBundle(bundleJsonObj, parseService, brickMan, manualBrickManager);
		}
		return null;
	}
	
	public static HAPBundleForBrick importBundle(Path importFolder, HAPServiceParseEntity parseService, HAPManagerApplicationBrick brickMan, HAPManualManagerBrick manualBrickManager) {
		return deserializeBundle(HAPUtilityExport.importBundle(importFolder), parseService, brickMan, manualBrickManager);
	}

}

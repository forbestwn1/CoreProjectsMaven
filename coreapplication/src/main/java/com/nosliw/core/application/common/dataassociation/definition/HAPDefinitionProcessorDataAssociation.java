package com.nosliw.core.application.common.dataassociation.definition;

import java.util.Map;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.dataassociation.HAPDataAssociation;
import com.nosliw.core.data.criteria.HAPCriteriaHelper;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPDefinitionProcessorDataAssociation {

	public static HAPDataAssociation processDataAssociation(
			HAPDefinitionDataAssociation daDef,
			HAPPath baseBlockPath, 
			HAPPath secondBlockPath,
		    Map<String, HAPPath> aliasMapping,
			HAPBundleForBrick currentBundle, 
			String rootBrickName,
			HAPCriteriaHelper criteriaHelper,
			HAPManagerResource resourceMan,
			HAPRuntimeInfo runtimeInfo) 
	{
		
		HAPDataAssociation out = null;
		String daType = daDef.getType();
		if(daType.equals(HAPConstantShared.DATAASSOCIATION_TYPE_MAPPING)) {
			out = HAPDefinitionProcessorMappingDataAssociation.processValueMapping((HAPDefinitionDataAssociationMapping)daDef, baseBlockPath, secondBlockPath, aliasMapping, currentBundle, rootBrickName, criteriaHelper, resourceMan, runtimeInfo);
		}
		
		return out;
	}
}

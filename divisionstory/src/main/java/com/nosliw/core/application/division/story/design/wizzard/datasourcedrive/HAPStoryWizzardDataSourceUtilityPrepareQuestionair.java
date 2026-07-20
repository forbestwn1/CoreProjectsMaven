package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.List;
import java.util.Set;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionair;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairGroup;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemDynamic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemStatic;
import com.nosliw.core.application.entity.datasource.HAPServiceProfile;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.application.entity.uitag.HAPUITagInfo;
import com.nosliw.core.application.entity.uitag.HAPUITageQueryData;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPDataType;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPDataTypeManager;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;

public class HAPStoryWizzardDataSourceUtilityPrepareQuestionair {

	public static HAPStoryWizzardQuestionair prepareChooseUIQuestionair(HAPServiceProfile dataSrouceProfile, HAPManagerUITag uiTagMan, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan) {
		HAPInteractiveTask dataSourceInterface = dataSrouceProfile.getInterface();

		//root group
		HAPStoryWizzardQuestionairGroup out = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEGROUP);

		//data source profile info
		HAPStoryWizzardQuestionairItemStatic dataSourceInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceInfoStatic(dataSrouceProfile));
		out.addItem(dataSourceInfoStaticQ);
		
		//group for requests
		HAPStoryWizzardQuestionairGroup serviceRequestGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTGROUP);
		for(HAPDefinitionParmRequest requestParm : dataSourceInterface.getRequestParms()) {
			HAPDataTypeCriteria dataTypeCriteria = requestParm.getDataDefinition().getCriteria();
			
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMGROUP);
			
			//parm static info
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic(requestParm), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMINFO);
			parmGroupQ.addItem(parmInfoStaticQ);
			
			//group for parm dynamic info
			HAPStoryWizzardQuestionairGroup parmDynamicGroupQ = new HAPStoryWizzardQuestionairGroup();
			parmGroupQ.addItem(parmDynamicGroupQ);
			
			//dynamic of is constant
			HAPStoryWizzardQuestionairItemDynamic parmIsConstantQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic(false), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMISCONSTANT);
			parmDynamicGroupQ.addItem(parmIsConstantQ);

			//dynamic of constant value
			HAPData initData = HAPUtilityDataDefinition.getInitData(requestParm.getDataDefinition());
			HAPStoryWizzardQuestionairItemDynamic parmConstantValueQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(initData), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMCONSTANTVALUE);
			parmDynamicGroupQ.addItem(parmConstantValueQ);

			//dynamic of uitag
			HAPUITageQueryData uiTagQuery = new HAPUITageQueryData(dataTypeCriteria);
			uiTagQuery.setIOMode(HAPConstantShared.IO_DIRECTION_IN);
			HAPUITagInfo uiTagInfo = uiTagMan.getDefaultUITagData(uiTagQuery);
			HAPStoryWizzardUITagInfo wizzardUITagInfo = new HAPStoryWizzardUITagInfo(uiTagInfo.getName(), uiTagInfo.getAttributes());
			wizzardUITagInfo.setAttribute(uiTagInfo.getAttributeForData(), requestParm.getName());
			
			HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic(wizzardUITagInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMUITAG);
			parmDynamicGroupQ.addItem(parmUITagChooseQ);
			
			serviceRequestGroupQ.addItem(parmGroupQ);
			
		}
		out.addItem(serviceRequestGroupQ);
		
		//group for response
		HAPStoryWizzardQuestionairGroup serviceResponseGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEGROUP);
		
		for(HAPDefinitionParmResponse responseParm : dataSourceInterface.getResult(HAPConstantShared.SERVICE_RESULT_SUCCESS).getOutput()) {
			HAPDataTypeCriteria dataTypeCriteria = responseParm.getDataDefinition().getCriteria();
		
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMGROUP);
			
			//parm static info
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic(responseParm), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMINFO);
			parmGroupQ.addItem(parmInfoStaticQ);
			
			//group for parm dynamic info
			HAPStoryWizzardQuestionairGroup parmDynamicGroupQ = new HAPStoryWizzardQuestionairGroup();
			parmGroupQ.addItem(parmDynamicGroupQ);

			//group for response data
			HAPStoryWizzardQuestionairGroup parmDataGroupQ = prepareQuestionairForResponseData(dataTypeCriteria, responseParm.getName(), uiTagMan, dataTypeHelper, dataTypeMan);
			parmGroupQ.addItem(parmDataGroupQ);
			
			serviceResponseGroupQ.addItem(parmGroupQ);
		}
		
		out.addItem(serviceResponseGroupQ);
		
		return out;
	}
	
	private static HAPStoryWizzardQuestionairGroup prepareQuestionairForResponseData(HAPDataTypeCriteria dataTypeCriteria, String dataVariableName, HAPManagerUITag uiTagMan, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan) {
		//data type criter group
		HAPStoryWizzardQuestionairGroup out = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATA);

		//data static info
		HAPStoryWizzardQuestionairItemStatic dataCriteriaInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic(dataTypeCriteria));
		out.addItem(dataCriteriaInfoStaticQ);

		//child info (name)
		HAPEntityInfo dataEntityInfo = new HAPEntityInfoImp();
		dataEntityInfo.setName(dataVariableName);
		HAPStoryWizzardQuestionairItemStatic dataEntityInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(dataEntityInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_ENTITYINFO);
		out.addItem(dataEntityInfoStaticQ);
		
		//dyanmic of is show 
		HAPStoryWizzardQuestionairItemDynamic dataIsShowQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseIsShowDynamic(true), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATAISSHOW);
		out.addItem(dataIsShowQ);
		
		//dynamic of uitag
		HAPUITageQueryData uiTagQuery = new HAPUITageQueryData(dataTypeCriteria);
		uiTagQuery.setIOMode(HAPConstantShared.IO_DIRECTION_OUT);
		HAPUITagInfo uiTagInfo = uiTagMan.getDefaultUITagData(uiTagQuery);
		HAPStoryWizzardUITagInfo wizzardUITagInfo = new HAPStoryWizzardUITagInfo(uiTagInfo.getName(), uiTagInfo.getAttributes());
		wizzardUITagInfo.setAttribute(uiTagInfo.getAttributeForData(), dataVariableName);
		
		HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic(wizzardUITagInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATAUITAG);
		out.addItem(parmUITagChooseQ);

		//for complex data type
		Set<HAPDataTypeId> dataTypeIds = dataTypeCriteria.getValidDataTypeId(dataTypeHelper);
		HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
		HAPDataType dataType = dataTypeMan.getDataType(dataTypeId);
		boolean isComplex = dataType.getIsComplex();
		if(isComplex) {
			
			//group for all children
			HAPStoryWizzardQuestionairGroup childrenGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATACHILDREN);
			out.addItem(childrenGroupQ);
			
			if(dataTypeId.getFullName().contains("array")){
				//child group
				HAPStoryWizzardQuestionairGroup childGroupQ = new HAPStoryWizzardQuestionairGroup();

				//child info (name)
				HAPEntityInfo entityInfo = new HAPEntityInfoImp();
				entityInfo.setName(HAPConstantShared.NAME_DEFAULT);
				HAPStoryWizzardQuestionairItemStatic childEntityInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(entityInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATACHILDINFO);
				childGroupQ.addItem(childEntityInfoStaticQ);
				
				//child data criteria
				childGroupQ.addItem(prepareQuestionairForResponseData(HAPUtilityCriteria.getElementCriteria(dataTypeCriteria), "element", uiTagMan, dataTypeHelper, dataTypeMan));
				
				childrenGroupQ.addItem(childGroupQ);
			}
			else if(dataTypeId.getFullName().contains("map")){
				//map
				List<String> names = HAPUtilityCriteria.getCriteriaChildrenNames(dataTypeCriteria);
				for(String name : names) {
					//child group
					HAPStoryWizzardQuestionairGroup childGroupQ = new HAPStoryWizzardQuestionairGroup();
					
					//child info (name)
					HAPEntityInfo entityInfo = new HAPEntityInfoImp();
					entityInfo.setName(name);
					HAPStoryWizzardQuestionairItemStatic childEntityInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(entityInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATACHILDINFO);
					childGroupQ.addItem(childEntityInfoStaticQ);

					//child data criteria
					childGroupQ.addItem(prepareQuestionairForResponseData(HAPUtilityCriteria.getChildCriteria(dataTypeCriteria, name), dataVariableName+"."+name, uiTagMan, dataTypeHelper, dataTypeMan));
					
					childrenGroupQ.addItem(childGroupQ);
				}
			}
		}

        return out;		
	}
	
	
	
}

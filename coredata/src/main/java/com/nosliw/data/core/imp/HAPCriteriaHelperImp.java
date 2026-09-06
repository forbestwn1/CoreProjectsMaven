package com.nosliw.data.core.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Sets;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPOperationParm;
import com.nosliw.core.data.HAPRelationship;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.data.HAPUtilityExecuteTask;
import com.nosliw.core.data.criteria.HAPCriteriaHelper;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaAny;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaExpression;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaId;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaIds;
import com.nosliw.core.data.criteria.HAPDataTypeSubCriteriaGroup;
import com.nosliw.core.data.criteria.HAPDataTypeSubCriteriaGroupImp;
import com.nosliw.core.data.criteria.HAPParserCriteriaImp;
import com.nosliw.core.data.expression.HAPExpressionData;
import com.nosliw.core.data.expression.HAPInfoRuntimeTaskExecuteDataExpresion;
import com.nosliw.core.data.expression.HAPUtilityExpressionData;
import com.nosliw.core.data.matcher.HAPMatcher;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.runtime.HAPRuntimeManager;

public class HAPCriteriaHelperImp implements HAPCriteriaHelper{

	@Override
	public Set<HAPDataTypeCriteriaId> normalizeCriteria(Set<HAPDataTypeCriteriaId> dataTypeCriteriaIds1){
		List<HAPDataTypeCriteriaId> dataTypeCriteriaIds = new ArrayList<HAPDataTypeCriteriaId>(dataTypeCriteriaIds1);
		Set<HAPDataTypeCriteriaId> out = new HashSet<HAPDataTypeCriteriaId>();
		if(dataTypeCriteriaIds.size()==0){}
		else if(dataTypeCriteriaIds.size()==1) {
			out.add(dataTypeCriteriaIds.get(0));
		} else{
			out.addAll(dataTypeCriteriaIds1);
			Set<HAPDataTypeCriteriaId> removes = new HashSet<HAPDataTypeCriteriaId>();
			for(int i=0; i< dataTypeCriteriaIds.size()-1; i++){
				for(int j=i+1; j<dataTypeCriteriaIds.size(); j++){
					if(this.convertable(dataTypeCriteriaIds.get(i), dataTypeCriteriaIds.get(j))!=null){
						removes.add(dataTypeCriteriaIds.get(i));
					}
					else if(this.convertable(dataTypeCriteriaIds.get(j), dataTypeCriteriaIds.get(i))!=null){
						removes.add(dataTypeCriteriaIds.get(j));
					}
				}
			}
			out.removeAll(removes);
		}
		return out;
	}

	@Override
	public HAPMatchers buildMatchers(HAPDataTypeCriteria from, HAPDataTypeCriteria to){
		return this.convertable(from, to);
	}

	
	@Override
	public HAPMatchers convertable(HAPDataTypeCriteria sourceCriteria, HAPDataTypeCriteria targetCriteria) {
		if(targetCriteria==null){
			if(sourceCriteria==null) {
				return new HAPMatchers();
			} else {
				return null;
			} 
		}
		else if(targetCriteria==HAPDataTypeCriteriaAny.getCriteria()){
			return new HAPMatchers();
		}
		else{
			HAPMatchers out = new HAPMatchers();
			Set<HAPDataTypeCriteriaId> sourceIdCriteriaSet = sourceCriteria.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> targetIdCriteriaSet = this.normalizeCriteria(targetCriteria.getValidDataTypeCriteriaId(this));
			
			for(HAPDataTypeCriteriaId sourceIdCriteria : sourceIdCriteriaSet){
				boolean match = false;
				for(HAPDataTypeCriteriaId targetIdCriteria : targetIdCriteriaSet){
					HAPMatcher matcher = this.convertableIdCriteria(sourceIdCriteria, targetIdCriteria);
					if(matcher!=null){
						match = true;
						out.addMatcher(matcher);
						break;
					}
				}
				if(!match) {
					return null;
				}
			}
			return out;
		}
	}


	
	@Override
	public HAPDataTypeCriteria and(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2) {
		if(criteria1==null || criteria2==null){
			return null;
		}
		else if(criteria1.equals(HAPDataTypeCriteriaAny.getCriteria()) && criteria2.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return HAPDataTypeCriteriaAny.getCriteria();
		}
		if(criteria1.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return criteria2;
		}
		else if(criteria2.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return criteria1;
		}
		else{
			Set<HAPDataTypeCriteriaId> dataTypesIdCriteria1 = criteria1.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> dataTypesIdCriteria2 = criteria2.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> andDataTypeIdCriterias = Sets.intersection(dataTypesIdCriteria1, dataTypesIdCriteria2);
			return this.buildDataTypeCriteria(andDataTypeIdCriterias);
		}
	}

	@Override
	public HAPDataTypeCriteria buildDataTypeCriteria(Set<HAPDataTypeCriteriaId> dataTypeCriterias) {
		HAPDataTypeCriteria out = null;
		if(dataTypeCriterias.size()==1){
			out = dataTypeCriterias.iterator().next();
		}
		else{
			out = new HAPDataTypeCriteriaIds(dataTypeCriterias);
		}
		return out;
	}

	@Override
	public HAPDataTypeCriteria merge(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2) {
		
		if(criteria1==null) {
			return criteria2;
		}
		if(criteria2==null || criteria2==HAPDataTypeCriteriaAny.getCriteria()) {
			return criteria1;
		}
		
		List<HAPDataTypeCriteriaId> criterias1 = new ArrayList(criteria1.getValidDataTypeCriteriaId(this));
		List<HAPDataTypeCriteriaId> leaves1 = this.getLeafCriteriaIds(criterias1);
		
		List<HAPDataTypeCriteriaId> criterias2 = new ArrayList(criteria2.getValidDataTypeCriteriaId(this));
		List<HAPDataTypeCriteriaId> leaves2 = this.getLeafCriteriaIds(criterias2);

		Set<HAPDataTypeCriteriaId> out = new HashSet<HAPDataTypeCriteriaId>();
		for(int i=0; i<leaves1.size(); i++){
			for(int j=0; j<leaves2.size(); j++){
				if(this.convertableIdCriteria(leaves1.get(i), leaves2.get(i))!=null){
					out.add(leaves2.get(i));
					break;
				}
				else if(this.convertableIdCriteria(leaves2.get(i), leaves1.get(i))!=null){
					out.add(leaves1.get(i));
					break;
				}
			}
		}
		if(out.size()==0) {
			return null;
		} else {
			return new HAPDataTypeCriteriaIds(out);
		}
	}

	
	@Override
	public HAPMatcher convertableIdCriteria(HAPDataTypeCriteriaId sourceIdCriteria, HAPDataTypeCriteriaId targetIdCriteria){
		HAPMatcher out = null;
		HAPRelationship relationship = this.convertable(sourceIdCriteria.getDataTypeId(), targetIdCriteria.getDataTypeId());
		if(relationship!=null){
			out = new HAPMatcher(sourceIdCriteria.getDataTypeId(), relationship);

			HAPDataTypeSubCriteriaGroup sourceSubCriterias = sourceIdCriteria.getSubCriteria();
			HAPDataTypeSubCriteriaGroup targetSubCriterias = targetIdCriteria.getSubCriteria();

			if(targetSubCriterias!=null){
				Set<String> targetSubNames = new HashSet(targetSubCriterias.getDefinedSubCriteriaNames());
				Set<String> sourceSubNames = new HashSet(sourceSubCriterias.getDefinedSubCriteriaNames());
				for(String targetSubName : targetSubNames){
					HAPMatchers matchers = null;
					HAPDataTypeCriteria targetSubCriteria = targetSubCriterias.getSubCriteria(targetSubName);
					HAPDataTypeCriteria sourceSubCriteria = sourceSubCriterias.getSubCriteria(targetSubName);
					if(sourceSubCriteria==null || sourceSubCriteria==HAPDataTypeCriteriaAny.getCriteria()){
						//no sub criteria by same name in source, fail
						return null;
					}
					else{
						matchers = this.buildMatchers(sourceSubCriteria, targetSubCriteria);
						if(matchers!=null){
							out.addSubMatchers(targetSubName, matchers);
						} else {
							return null;
						}
					}
					sourceSubNames.remove(targetSubName);
				}
				
				for(String sourceSubName : sourceSubNames){
					if(targetSubCriterias.isOpen()){
						//any
					}
				}
				
			}
			if(out!=null) {
				this.processSubMatcher(out);
			}
		}
		return out;
	}

	//Remove sub matchers if they don't do the real convert (target and source are same data type)
	private void processSubMatcher(HAPMatcher parentMatcher){
		boolean canRemove = true;
		Map<String, HAPMatchers> subMatchers = parentMatcher.getSubMatchers();
		for(String subName : subMatchers.keySet()){
			if(!canRemove) {
				break;
			}
			HAPMatchers matchers = subMatchers.get(subName);
			Map<HAPDataTypeId, HAPMatcher> matchByDataTypes = matchers.getMatchers();
			for(HAPDataTypeId dataTypeId : matchByDataTypes.keySet()){
				if(!canRemove) {
					break;
				}
				HAPMatcher matcher = matchByDataTypes.get(dataTypeId);
				this.processSubMatcher(matcher);
				
				if((!HAPUtilityBasic.isEquals(matcher.getRelationship().getSource(), matcher.getRelationship().getTarget())) && 
						(matcher.getSubMatchers()==null||matcher.getSubMatchers().isEmpty())){
					canRemove = false;
				}
			}
		}
		
		if(canRemove){
			parentMatcher.removeAllSubMatcher();
		}
	}
	
	
	private List<HAPDataTypeCriteriaId> getLeafCriteriaIds(List<HAPDataTypeCriteriaId> criteriaIds){
		List<HAPDataTypeCriteriaId> out = new ArrayList(criteriaIds);
		
		int i = 0; 
		while(i<out.size()){
			int j = i+1;
			boolean increasI = true;
			while(j<out.size()){
				if(this.convertableIdCriteria(out.get(j), out.get(i))!=null){
					out.remove(i);
					increasI = false;
					break;
				}
				else if(this.convertableIdCriteria(out.get(i), out.get(j))!=null){
					out.remove(j);
				}
				else{
					j++;
				}
			}
			if(increasI) {
				i++;
			}
		}
		return out;
	}

	@Override
	public HAPDataTypeCriteriaId getDataTypeCriteriaByData(HAPData data) {
		HAPDataTypeId dataTypeId = data.getDataTypeId();
		HAPDataTypeImp dataType = this.m_dataAccess.getDataType(dataTypeId);
//		HAPInfo info = dataType.getInfo();
//		if(info!=null){
//			if("true".equals(info.getValue(HAPDataTypeInfoImp.COMPLEX)))		hasChild = true;
//		}

		boolean hasChild = dataType.getIsComplex();
		HAPDataTypeSubCriteriaGroupImp group = null;
		if(hasChild){
			List<HAPOperationParm> parmsDataGetChildrenNames = new ArrayList<HAPOperationParm>();
			parmsDataGetChildrenNames.add(new HAPOperationParm(data));
			HAPServiceData serviceDataChildNames = HAPUtilityExecuteTask.executeDataOperationSync(data.getDataTypeId(), HAPConstantShared.DATAOPERATION_COMPLEX_GETCHILDRENNAMES, parmsDataGetChildrenNames, this.getRuntimeExecutor());
			HAPData getChildrenNamesResultData = (HAPData)serviceDataChildNames.getData();
			try {
				JSONArray getChildrenNamesResultJsonArray = new JSONArray(getChildrenNamesResultData.getValue().toString());
				for(int i=0; i<getChildrenNamesResultJsonArray.length(); i++){
					if(group==null) {
						group = new HAPDataTypeSubCriteriaGroupImp(false);
					}
					JSONObject childNameDataJson = getChildrenNamesResultJsonArray.getJSONObject(i);
					String childName = childNameDataJson.getString(HAPData.VALUE);
					List<HAPOperationParm> parmsDataGetChildData = new ArrayList<HAPOperationParm>();
					parmsDataGetChildData.add(new HAPOperationParm(data));
					parmsDataGetChildData.add(new HAPOperationParm("name", HAPUtilityData.buildDataWrapperFromJson(childNameDataJson)));
					HAPServiceData serviceDataChildData = HAPUtilityExecuteTask.executeDataOperationSync(data.getDataTypeId(), HAPConstantShared.DATAOPERATION_COMPLEX_GETCHILDDATA, parmsDataGetChildData, this.getRuntimeExecutor());
					HAPData getChildDataResultData = (HAPData)serviceDataChildData.getData();
					group.addSubCriteria(childName, this.getDataTypeCriteriaByData(getChildDataResultData));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return new HAPDataTypeCriteriaId(data.getDataTypeId(), group);
	}

	@Override
	public void processExpressionCriteria(HAPDataTypeCriteria criteria, Map<String, HAPData> parms) {
		Set<HAPDataTypeCriteriaExpression> expCriterias = new HashSet<HAPDataTypeCriteriaExpression>();
		this.discoverExpressionCriteria(criteria, expCriterias);
		for(HAPDataTypeCriteriaExpression expCriteria : expCriterias){
			String expressionStr = expCriteria.getExpression();
			HAPExpressionData dataExpression = HAPUtilityExpressionData.buildDataExpression(expressionStr, this.m_dataExpressionParser);

			HAPInfoRuntimeTaskExecuteDataExpresion exeDataExpressionTaskInfo = new HAPInfoRuntimeTaskExecuteDataExpresion();
			exeDataExpressionTaskInfo.setDataExpression(dataExpression);
			
			for(String parmName : parms.keySet()) {
				exeDataExpressionTaskInfo.addVariableData(parmName, parms.get(parmName));
			}
			
			HAPServiceData serviceData = this.m_runtimeMan.getRuntimeExecutor(HAPRuntimeManager.RUNTIME_JS_RHION).executeTaskSync(exeDataExpressionTaskInfo);
			JSONObject serviceDataJson = (JSONObject)serviceData.getData();
			HAPData expressionResult = HAPUtilityData.buildDataWrapperFromObject(serviceDataJson);

			
//			HAPServiceData serviceData = null;  //this.m_runtime.executeExpressionSync(expressionStr, parms);
//			HAPData expressionResult = (HAPData)serviceData.getData();

			String criteriaStr = expressionResult.getValue().toString();
			HAPDataTypeCriteria solidCriteria = HAPParserCriteriaImp.getInstance().parseCriteria(criteriaStr);
			expCriteria.setSolidCriteria(solidCriteria);
		}
	}

	private void discoverExpressionCriteria(HAPDataTypeCriteria criteria, Set<HAPDataTypeCriteriaExpression> expCriterias){
		if(criteria.getType().equals(HAPConstantShared.DATATYPECRITERIA_TYPE_EXPRESSION)){
			expCriterias.add((HAPDataTypeCriteriaExpression)criteria);
		}
		else{
			List<HAPDataTypeCriteria> children = criteria.getChildren();
			for(HAPDataTypeCriteria child : children){
				this.discoverExpressionCriteria(child, expCriterias);
			}
		}
	}
	

}

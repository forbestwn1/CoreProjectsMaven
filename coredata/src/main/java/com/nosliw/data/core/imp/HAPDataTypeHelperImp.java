package com.nosliw.data.core.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPDataTypeOperation;
import com.nosliw.core.data.HAPRelationship;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaExpression;
import com.nosliw.core.data.expression.definition.HAPParserDataExpression;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.runtime.execute.HAPExecutorRuntime;
import com.nosliw.data.core.imp.runtime.js.HAPModuleRuntimeJS;

@Component
public class HAPDataTypeHelperImp implements HAPDataTypeHelper{

	private HAPRuntimeManager m_runtimeMan;
	private HAPDataAccessDataType m_dataAccess = null;
	private HAPExecutorRuntime m_runtimeExecutor;

	@Autowired
	private HAPParserDataExpression m_dataExpressionParser;
	
//	public HAPDataTypeHelperImp(HAPRuntimeManager runtimeMan, HAPModuleRuntimeJS jsRuntimeModule){
//		this.m_runtime = runtimeMan.getDefaultRuntime();
//		this.m_dataAccess = jsRuntimeModule.getDataTypeDataAccess();
//	}
	
	@Autowired
	private void setRuntimeManager(HAPRuntimeManager runtimeMan) {
		this.m_runtimeMan = runtimeMan;	}
	
	@Autowired
	private void setRuntimeJSModule(HAPModuleRuntimeJS jsRuntimeModule) {
		this.m_dataAccess = jsRuntimeModule.getDataTypeDataAccess();
	}
	
	@Override
	public Set<HAPRelationship> getRootDataTypeRelationship(HAPDataTypeId dataTypeId){
		List<HAPRelationshipImp> rootRelationships = this.m_dataAccess.getRelationships(dataTypeId, HAPConstantShared.DATATYPE_RELATIONSHIPTYPE_ROOT);
		return new HashSet<HAPRelationship>(rootRelationships);
	}
	
	@Override
	public HAPDataTypeOperation getOperationInfoByName(HAPDataTypeId dataTypeInfo, String name) {
		return this.m_dataAccess.getDataTypeOperation(dataTypeInfo, name);
	}
	
	@Override
	public Set<HAPDataTypeId> getAllDataTypeInRange(HAPDataTypeId from, HAPDataTypeId to) {
		Set<HAPDataTypeId> out = null;
		Set<HAPDataTypeId> toSet = null;
		Set<HAPDataTypeId> fromSet = null;
		
		if(to!=null){
			toSet = new HashSet<HAPDataTypeId>();
			HAPDataTypePictureImp toPic = this.m_dataAccess.getDataTypePicture(to);
			Set<HAPRelationship> relationships = (Set<HAPRelationship>)toPic.getRelationships();
			for(HAPRelationship relationship : relationships){
				toSet.add(relationship.getTarget());
			}
		}

		if(from!=null){
			fromSet = new HashSet<HAPDataTypeId>();
			HAPDataTypeFamilyImp fromFamily = this.m_dataAccess.getDataTypeFamily(from);
			Set<HAPRelationship> relationships = (Set<HAPRelationship>)fromFamily.getRelationships();
			for(HAPRelationship relationship : relationships){
				fromSet.add(relationship.getSource());
			}
		}
		
		if(to==null) {
			out = fromSet;
		} else if(from==null) {
			out = toSet;
		} else {
			out = Sets.intersection(fromSet, toSet);
		}
		return out;
	}

	@Override
	public Set<HAPDataTypeId> getRootDataTypeId(HAPDataTypeId dataTypeId) {
		Set<HAPRelationship> rootRelationships = this.getRootDataTypeRelationship(dataTypeId);
		Set<HAPDataTypeId> out = new HashSet<HAPDataTypeId>();
		for(HAPRelationship rootRelationship : rootRelationships){
			out.add(rootRelationship.getTarget());
		}
		return out;
	}

	@Override
	public List<HAPDataTypeOperation> getDataTypeOperations(HAPDataTypeId baseDataTypeId, HAPDataTypeId resultDataTypeId){
		return (List)this.m_dataAccess.getDataTypeOperations(baseDataTypeId, resultDataTypeId);
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
	
	private HAPExecutorRuntime getRuntimeExecutor() {
		if(this.m_runtimeExecutor==null) {
			this.m_runtimeExecutor = this.m_runtimeMan.getDefaultRuntimeExecutor();
		}
		return this.m_runtimeExecutor;
	}

}

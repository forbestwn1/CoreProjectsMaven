package com.nosliw.core.application.common.withvariable;

import java.util.Map;

import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.core.application.common.structure.reference.HAPConfigureResolveElementReference;
import com.nosliw.core.application.common.valueport.HAPIdElement;
import com.nosliw.core.data.criteria.HAPInfoCriteria;

public interface HAPContainerVariableInfo extends HAPSerializable{

	public static String VARIABLES = "variables";

	public static String VARIABLEID = "variableId";

	public static String VARIABLENAME = "variableName";

	public static String CRITERIA = "criteria";

	public String addVariable(String variableName, String varIODirection, HAPConfigureResolveElementReference resolveConfigure);
	public boolean isEmpty();
	
	public HAPInfoCriteria getVaraibleCriteriaInfo(String key);
	public Map<String, HAPInfoCriteria> getVariableCriteriaInfos();
	public HAPIdElement getVariableId(String key);
	public Map<String, HAPIdElement> getVariables();

/*	
	public String addVariable(HAPIdElement variableId) {
		if(variableId==null) {
			throw new RuntimeException();
		}
		
		if(m_criteriaInfosByKey.get(variableId)!=null) {
			//already exist
			for(String key : this.m_variableIdByName.keySet()) {
				if(variableId.equals(this.m_variableIdByName.get(key))) {
					return key;
				}
			}
			return null;
		}
		else {
			//brand new
			String key = this.m_nextId + "";
			this.m_nextId++;
//			this.m_variableIdByName.put(key, variableId);
//			this.m_criteriaInfosByKey.put(variableId, new HAPInfoCriteria());
			return key;
		}
	}
*/	
}

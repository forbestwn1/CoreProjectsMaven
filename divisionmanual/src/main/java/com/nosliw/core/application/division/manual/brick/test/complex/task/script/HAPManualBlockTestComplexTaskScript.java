package com.nosliw.core.application.division.manual.brick.test.complex.task.script;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.variable.HAPVariableInfo;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.test.complex.task.script.HAPBlockTestComplexTaskScript;
import com.nosliw.core.application.common.withvariable.HAPWithVariableDebugExecutable;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.resource.HAPResourceId;

public class HAPManualBlockTestComplexTaskScript extends HAPManualBrickImp implements HAPBlockTestComplexTaskScript{

	public HAPManualBlockTestComplexTaskScript() {
		super(HAPEnumBrickType.TEST_COMPLEX_TASK_SCRIPT_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(HAPWithVariableDebugExecutable.VARIABLE, new LinkedHashMap<String, HAPVariableInfo>());
	}

	@Override
	public Map<String, Object> getParms() {		return (Map<String, Object>)this.getAttributeValueOfValue(PARM);	}
	public void setParms(Map<String, Object> parms) {	this.setAttributeValueWithValue(PARM, parms);	}

	@Override
	public HAPResourceId getScriptResourceId() {   return (HAPResourceId)this.getAttributeValueOfValue(SCRIPTRESOURCEID);  }
	public void setScriptResourceId(HAPResourceId resourceId) {    this.setAttributeValueWithValue(SCRIPTRESOURCEID, resourceId);  }
	
	@Override
	public Map<String, HAPVariableInfo> getVariables() {    return (Map<String, HAPVariableInfo>)this.getAttributeValueOfValue(VARIABLE);  }
	public void addVariable(String name, HAPVariableInfo varInfo) {     this.getVariables().put(name, varInfo);      }

	@Override
	public HAPEntityOrReference getTaskInterface() {    return this.getAttributeValueOfBrick(TASKINTERFACE);  }
	@Override
	public void setTaskInterface(HAPEntityOrReference taskInterface) {   this.setAttributeValueWithBrick(TASKINTERFACE, taskInterface);     }

	@Override
	public HAPEntityOrReference getExpressionInterface() {  return this.getAttributeValueOfBrick(EXPRESSIONINTERFACE);  }
	@Override
	public void setExpressionInterface(HAPEntityOrReference expressionInterface) {   this.setAttributeValueWithBrick(EXPRESSIONINTERFACE, expressionInterface);     }

}
 
@Component
class HAPManualBlockTestComplexTaskScript_parser extends HAPManualBrick_parser{

	public HAPManualBlockTestComplexTaskScript_parser() {
		super(HAPManualBlockTestComplexTaskScript.class, HAPEnumBrickType.TEST_COMPLEX_TASK_SCRIPT_100);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualBlockTestComplexTaskScript out = new HAPManualBlockTestComplexTaskScript();
		this.parseBrickJson((JSONObject)obj, out, parseService);
		return out;
	}
	
	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPBlockTestComplexTaskScript.PARM:
		{
			Map<String, Object> out = new LinkedHashMap<String, Object>();
			JSONObject parmsJsonObj = (JSONObject)obj;
			for(Object key : parmsJsonObj.keySet()) {
				String name = (String)key;
				out.put(name, parmsJsonObj.get(name));
			}
			return out;
		}
    	case HAPBlockTestComplexTaskScript.SCRIPTRESOURCEID:
    	{
    		return HAPFactoryResourceId.newInstance(obj);
    	}
    	case HAPBlockTestComplexTaskScript.VARIABLE:
    	{
    		Map<String, HAPVariableInfo> out = new LinkedHashMap<String, HAPVariableInfo>();
			JSONObject varsJsonObj = (JSONObject)obj;
			for(Object key : varsJsonObj.keySet()) {
				String name = (String)key;
				out.put(name, (HAPVariableInfo)parseService.parseEntityJSONExplicit(varsJsonObj.getJSONObject(name), HAPVariableInfo.ENTITYNAMEFORSERIALIZE));
			}
			return out;
    	}
	    }
		return null;
	}
	
}

package com.nosliw.core.application.brick.spec.expression.scriptexpression.library;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.variable.HAPVariableInfo;
import com.nosliw.common.variable.HAPWithVariable;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBrick;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;
import com.nosliw.core.application.common.dataexpression.HAPWithDataExpression;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.application.common.interactive.HAPWithInteractiveExpression;
import com.nosliw.core.application.common.scriptexpressio.HAPExpressionScript;
import com.nosliw.core.application.common.withvariable.HAPContainerVariableInfo;
import com.nosliw.core.application.valueport.HAPGroupValuePorts;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.xxx.application.valueport.HAPWithValuePortGroup;

@HAPEntityWithAttribute
public class HAPElementInLibraryScriptExpression extends HAPBasicBrick implements HAPWithInteractiveExpression, HAPWithVariable, HAPWithValuePortGroup, HAPWithDataExpression{

	public HAPElementInLibraryScriptExpression(HAPIdBrickType brickTypeId) {
		super(brickTypeId);
		// TODO Auto-generated constructor stub
	}

	@HAPAttribute
	public static String EXPRESSION = "expression";
	
	@HAPAttribute
	public static String RESULTMATCHERS = "resultMatchers";

	private HAPExpressionScript m_scriptExpression;
	
	private HAPMatchers m_resultMatchers;

	private HAPInteractiveExpression m_interactive;
	
	private HAPContainerVariableInfo m_variableInfo;
	
	private HAPContainerDataExpression m_dataExpressionGroup;
	
//	public HAPElementInLibraryScriptExpression() {
//		this.m_variableInfo = new HAPContainerVariableInfo();
//		this.m_dataExpressionGroup = new HAPContainerDataExpression();
//	}
	
	public HAPExpressionScript getExpression() {	return m_scriptExpression;	}
	public void setExpression(HAPExpressionScript scriptExpression) {	this.m_scriptExpression = scriptExpression;	}

	@Override
	public HAPContainerDataExpression getDataExpressions() {    return this.m_dataExpressionGroup;     }
	
	
	public HAPMatchers getResultMatchers() {		return this.m_resultMatchers;	}
	public void setResultMatchers(HAPMatchers matchers) {    this.m_resultMatchers = matchers;    }

	public HAPInteractiveExpression getInteractive() {    return this.m_interactive;      }
	public void setInteractive(HAPInteractiveExpression interactive) {    this.m_interactive = interactive;     }
	
//	@Override
//	public HAPContainerVariableInfo getVariablesInfo() {   return this.m_variableInfo;  }
	public void setVariablesInfo(HAPContainerVariableInfo varsInfo) {    this.m_variableInfo = varsInfo;     }
	
//	@Override
//	public HAPGroupValuePorts getExternalValuePortGroup() {   return this.m_interactive.getExternalValuePortGroup();  }
	
//	@Override
//	public HAPGroupValuePorts getInternalValuePortGroup() {   return this.m_interactive.getInternalValuePortGroup();  }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(EXPRESSIONINTERFACE, HAPManagerSerialize.getInstance().toStringValue(this.m_interactive, HAPSerializationFormat.JSON));
		jsonMap.put(HAPWithVariable.VARIABLEINFOS, HAPManagerSerialize.getInstance().toStringValue(this.getVariablesInfo(), HAPSerializationFormat.JSON));
		jsonMap.put(EXPRESSION, HAPManagerSerialize.getInstance().toStringValue(this.getExpression(), HAPSerializationFormat.JSON));
		jsonMap.put(RESULTMATCHERS, HAPUtilityJson.buildJson(this.getResultMatchers(), HAPSerializationFormat.JSON));
		jsonMap.put(DATAEXPRESSIONS, this.m_dataExpressionGroup.toStringValue(HAPSerializationFormat.JSON));
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSION, HAPManagerSerialize.getInstance().toStringValue(this.getExpression(), HAPSerializationFormat.JAVASCRIPT));
	}
	
//	@Override
//	protected void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo, HAPManagerResource resourceManager) {
//		super.buildResourceDependency(dependency, runtimeInfo, resourceManager);
//		//get converter resource id from var converter in expression 
//		HAPMatchers matchers = this.getResultMatchers();
//		if(matchers!=null){
//			dependency.addAll(matchers.getResourceDependency(runtimeInfo, resourceManager));
//		}
//	}

	@Override
	public void addVariableInfo(HAPVariableInfo variableInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWithVariableEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPInteractiveExpression getExpressionInterface() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, HAPVariableInfo> getVariablesInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HAPGroupValuePorts getInternalValuePortGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HAPGroupValuePorts getExternalValuePortGroup() {
		// TODO Auto-generated method stub
		return null;
	}

}

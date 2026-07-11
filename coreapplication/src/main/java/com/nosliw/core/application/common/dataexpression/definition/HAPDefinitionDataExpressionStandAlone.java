package com.nosliw.core.application.common.dataexpression.definition;

import java.util.List;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.common.dataexpression.HAPDataExpressionStandAlone;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDefinitionDataExpressionStandAlone extends HAPSerializableImp{

	private HAPInteractiveExpression m_interactiveExpression;
	
	private String m_expressionStr;
	
	private HAPDefinitionDataExpression m_expression;
	
	public HAPDefinitionDataExpressionStandAlone() {
	}
	
	public HAPInteractiveExpression getExpressionInteractive() {   return this.m_interactiveExpression;    }
	public void setExpressionInteractive(HAPInteractiveExpression interactive) {   this.m_interactiveExpression = interactive;       }
	
	public List<HAPDefinitionParmRequest> getRequestParms() {  return this.m_interactiveExpression.getRequestParms();  }
	public void addRequestParm(HAPDefinitionParmRequest requestParm) {	this.getRequestParms().add(requestParm);	}
	
	public HAPDefinitionParmResponse getResult() {   return this.m_interactiveExpression.getResult();  } 
	
	public String getExpressionStr() {    return this.m_expressionStr;     }
	public void setExpressionStr(String expressionStr) {    this.m_expressionStr = expressionStr;      }
	
	public HAPDefinitionDataExpression getExpression() {	return this.m_expression;	}
	public void setExpression(HAPDefinitionDataExpression expression) {   this.m_expression = expression;     }

	public static HAPDefinitionDataExpressionStandAlone parse(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		HAPDefinitionDataExpressionStandAlone out = new HAPDefinitionDataExpressionStandAlone();
		out.setExpressionInteractive(HAPInteractiveExpression.parse(jsonObj, entityParseService));
		out.setExpressionStr(jsonObj.getString(HAPDataExpressionStandAlone.EXPRESSION));
		return out;
	}
	
}

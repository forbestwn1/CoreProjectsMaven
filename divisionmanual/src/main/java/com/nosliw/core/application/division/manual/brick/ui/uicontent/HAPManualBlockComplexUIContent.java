package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.container.HAPBrickContainer;
import com.nosliw.core.application.brick.container.HAPBrickContainerList;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInAttribute;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInContent;
import com.nosliw.core.application.common.scriptexpressio.HAPContainerScriptExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockComplexUIContent extends HAPManualBrickImp implements HAPBlockComplexUIContent{

	public HAPManualBlockComplexUIContent() {
		super(HAPEnumBrickType.UICONTENT_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCONTENT, new ArrayList<HAPUIEmbededScriptExpressionInContent>());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE, new ArrayList<HAPUIEmbededScriptExpressionInAttribute>());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE, new ArrayList<HAPUIEmbededScriptExpressionInAttribute>());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONS, new HAPContainerScriptExpression());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.TAGALIASMAPPING, new LinkedHashMap<String, String>());
	}
	
	@Override
	public String getHtml() {    return (String)this.getAttributeValueOfValue(HAPBlockComplexUIContent.HTML);  }
	public void setHtml(String html) {    this.setAttributeValueWithValue(HTML, html);      }

	@Override
	public Map<String, String> getTagAliasMapping(){       return (Map<String, String>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.TAGALIASMAPPING);        }
	public void addAllTagAliasMapping(Map<String, String> mapping) {     this.getTagAliasMapping().putAll(mapping);       }

	@Override
	public HAPBrickContainerList getCustomerTags() {    return (HAPBrickContainerList)this.getAttributeValueOfBrickLocal(CUSTOMERTAG);   }
	
	@Override
	public List<HAPUIEmbededScriptExpressionInContent> getScriptExpressionInContent() {    return (List<HAPUIEmbededScriptExpressionInContent>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCONTENT);  }

	@Override
	public List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInNormalTagAttribute() {return (List<HAPUIEmbededScriptExpressionInAttribute>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE);  }

	@Override
	public List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInCustomerTagAttribute() {return (List<HAPUIEmbededScriptExpressionInAttribute>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE);  }

	@Override
	public HAPContainerScriptExpression getScriptExpressions() {   return (HAPContainerScriptExpression)this.getAttributeValueOfValue(SCRIPTEXPRESSIONS);  }

	@Override
	public HAPBrickContainer getTasks() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

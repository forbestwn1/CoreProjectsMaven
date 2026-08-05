package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.container.HAPBrickContainer;
import com.nosliw.core.application.brick.container.HAPBrickContainerList;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInAttribute;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInContent;
import com.nosliw.core.application.common.scriptexpressio.HAPContainerScriptExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

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

@Component
class HAPManualBlockComplexUIContent_parser extends HAPManualBrick_parser{

	public HAPManualBlockComplexUIContent_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBlockComplexUIContent.class, HAPEnumBrickType.UICONTENT_100);
	}
	
	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPBlockComplexUIContent.TAGALIASMAPPING:
		{
			Map<String, String> out = new LinkedHashMap<String, String>();
			JSONObject attrJsonObj = (JSONObject)obj;
			for(Object key : attrJsonObj.keySet()) {
				String name = (String)key;
				out.put(name, attrJsonObj.getString(name));
			}
			return out;
		}
		case HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCONTENT:
		{
			List<HAPUIEmbededScriptExpressionInContent> out = new ArrayList<HAPUIEmbededScriptExpressionInContent>();
			JSONArray jsonArray = (JSONArray)obj; 
			for(int i=0; i<jsonArray.length(); i++) {
				HAPUIEmbededScriptExpressionInContent item = new HAPUIEmbededScriptExpressionInContent();
				item.buildObject(jsonArray.get(i), HAPSerializationFormat.JSON);
				out.add(item);
			}
			return out;
		}
		case HAPBlockComplexUIContent.SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE:
		{
			List<HAPUIEmbededScriptExpressionInAttribute> out = new ArrayList<HAPUIEmbededScriptExpressionInAttribute>();
			JSONArray jsonArray = (JSONArray)obj; 
			for(int i=0; i<jsonArray.length(); i++) {
				HAPUIEmbededScriptExpressionInAttribute item = new HAPUIEmbededScriptExpressionInAttribute();
				item.buildObject(jsonArray.get(i), HAPSerializationFormat.JSON);
				out.add(item);
			}
			return out;
		}
		case HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE:
		{
			List<HAPUIEmbededScriptExpressionInAttribute> out = new ArrayList<HAPUIEmbededScriptExpressionInAttribute>();
			JSONArray jsonArray = (JSONArray)obj; 
			for(int i=0; i<jsonArray.length(); i++) {
				HAPUIEmbededScriptExpressionInAttribute item = new HAPUIEmbededScriptExpressionInAttribute();
				item.buildObject(jsonArray.get(i), HAPSerializationFormat.JSON);
				out.add(item);
			}
			return out;
		}
		case HAPBlockComplexUIContent.HTML:
			return obj;
		}
		
		return null;     
	}
	
}

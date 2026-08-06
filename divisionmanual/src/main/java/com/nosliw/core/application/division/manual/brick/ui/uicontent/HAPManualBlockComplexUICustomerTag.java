package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUICustomerTag;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIId;
import com.nosliw.core.application.common.style.HAPUIStyle;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.application.entity.uitag.HAPUITagDefinition;
import com.nosliw.core.application.entity.uitag.HAPUITagDefinitionAttribute;
import com.nosliw.core.resource.HAPResourceId;

public class HAPManualBlockComplexUICustomerTag extends HAPManualBrickImp implements HAPBlockComplexUICustomerTag{

	public HAPManualBlockComplexUICustomerTag() {
		super(HAPEnumBrickType.UICUSTOMERTAG_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(HAPBlockComplexUICustomerTag.ATTRIBUTE, new LinkedHashMap<String, String>());
		this.setAttributeValueWithValue(HAPBlockComplexUICustomerTag.ATTRIBUTEDEFINITION, new LinkedHashMap<String, HAPUITagDefinitionAttribute>());
		this.setAttributeValueWithValue(HAPBlockComplexUICustomerTag.METADATA, new LinkedHashMap<String, String>());
	}
	
	public String getUITagId() {   return (String)this.getAttributeValueOfValue(UITAGID);  }
	public void setUITagId(String uiTagId) {    this.setAttributeValueWithValue(UITAGID, uiTagId);      }

	@Override
	public String getUIId() {   return (String)this.getAttributeValueOfValue(UIID);  }
	public void setUIId(String uiId) {    this.setAttributeValueWithValue(UIID, uiId);      }

	public void addTagAttribute(String attrName, String attrValue) {     this.getTagAttributes().put(attrName, attrValue);        }
	public Map<String, String> getTagAttributes(){   return (Map<String, String>)this.getAttributeValueOfValue(HAPBlockComplexUICustomerTag.ATTRIBUTE);      }

	public HAPUITagDefinition getUITagDefinition() {    return (HAPUITagDefinition)this.getAttributeValueOfValue(HAPBlockComplexUICustomerTag.TAGDEFINITION);      }
	public void setUITagDefinition(HAPUITagDefinition tagDef) {    this.setAttributeValueWithValue(HAPBlockComplexUICustomerTag.TAGDEFINITION, tagDef);     }

	public void addMetaData(String key, String value) {    this.getMetaData().put(key, value);      }
	public Map<String, String> getMetaData(){   return (Map<String, String>)this.getAttributeValueOfValue(HAPBlockComplexUICustomerTag.METADATA);     }
	
	
	public String getBase() {   return (String)this.getAttributeValueOfValue(BASE);    }
	public void setBase(String base) {     this.setAttributeValueWithValue(BASE, base);      }
	
	public HAPResourceId getScriptResourceId(){    return (HAPResourceId)this.getAttributeValueOfValue(SCRIPTRESOURCEID);      }
	public void setScriptResourceId(HAPResourceId resourceId) {    this.setAttributeValueWithValue(SCRIPTRESOURCEID, resourceId);      }

	public Map<String, HAPUITagDefinitionAttribute> getAttributeDefinitions(){   return (Map<String, HAPUITagDefinitionAttribute>)this.getAttributeValueOfValue(HAPBlockComplexUICustomerTag.ATTRIBUTEDEFINITION);      }
	public void addAttributeDefinition(HAPUITagDefinitionAttribute attrDef) {    this.getAttributeDefinitions().put(attrDef.getName(), attrDef);     }

	@Override
	public HAPBlockComplexUIContent getUIContent() {    return (HAPBlockComplexUIContent)this.getAttributeValueOfBrickLocal(UICONTENT);   }

	@Override
	public HAPUIStyle getStyle() {      return (HAPUIStyle)this.getAttributeValueOfValue(STYLE);   }

}

@Component
class HAPManualBlockComplexUICustomerTag_parser extends HAPManualBrick_parser{

	public HAPManualBlockComplexUICustomerTag_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBlockComplexUICustomerTag.class, HAPEnumBrickType.UICUSTOMERTAG_100);
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName) {
		case HAPWithUIContent.STYLE:
		{
			JSONObject jsonObj = (JSONObject)obj; 
			HAPUIStyle out = new HAPUIStyle();
			out.buildObject(jsonObj, HAPSerializationFormat.JSON);
			return out;
		}
		case HAPBlockComplexUICustomerTag.ATTRIBUTE:
		{
			Map<String, String> out = new LinkedHashMap<String, String>();
			JSONObject attrJsonObj = (JSONObject)obj;
			for(Object key : attrJsonObj.keySet()) {
				String name = (String)key;
				out.put(name, attrJsonObj.getString(name));
			}
			return out;
		}
		case HAPBlockComplexUICustomerTag.METADATA:
		{
			Map<String, String> out = new LinkedHashMap<String, String>();
			JSONObject attrJsonObj = (JSONObject)obj;
			for(Object key : attrJsonObj.keySet()) {
				String name = (String)key;
				out.put(name, attrJsonObj.getString(name));
			}
			return out;
		}
		case HAPBlockComplexUICustomerTag.TAGDEFINITION:
		{
			JSONObject jsonObj = (JSONObject)obj; 
			return parseService.parseEntityJSONExplicit(jsonObj.getJSONObject(HAPBlockComplexUICustomerTag.TAGDEFINITION), HAPUITagDefinition.class.getName());
		}
		case HAPBlockComplexUICustomerTag.ATTRIBUTEDEFINITION:
		{
			JSONObject jsonObj = (JSONObject)obj; 
			for(Object key : jsonObj.keySet()) {
				String name = (String)key;
				return HAPUITagDefinitionAttribute.parseUITagDefinitionAttribute(jsonObj.getJSONObject(name), parseService);
			}
			
		}
		case HAPBlockComplexUICustomerTag.UITAGID:
		case HAPBlockComplexUICustomerTag.BASE:
		case HAPWithUIId.UIID:
			return obj;
		}
		
		return null;     
	}

}

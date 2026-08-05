package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIPage;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.common.style.HAPUIStyle;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPManualBlockComplexUIPage extends HAPManualBrickImp implements HAPBlockComplexUIPage{

	public HAPManualBlockComplexUIPage() {
		super(HAPEnumBrickType.UIPAGE_100);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public HAPBlockComplexUIContent getUIContent() {    return (HAPBlockComplexUIContent)this.getAttributeValueOfBrickLocal(UICONTENT);   }

	@Override
	public HAPUIStyle getStyle() {      return (HAPUIStyle)this.getAttributeValueOfValue(STYLE);   }


	@Override
	public String getStyleScript() {    return (String)this.getAttributeValueOfValue(STYLESCRIPT);    }
	public void setStyleScript(String styleScript) {      this.setAttributeValueWithValue(STYLESCRIPT, styleScript);          }

}

@Component
class HAPManualBlockComplexUIPage_parser extends HAPManualBrick_parser{

	public HAPManualBlockComplexUIPage_parser(HAPManagerApplicationBrick brickManager) {
		super(brickManager, HAPManualBlockComplexUIPage.class, HAPEnumBrickType.UIPAGE_100);
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
		case HAPBlockComplexUIPage.STYLESCRIPT:
			return obj;
		}
		
		return null;     
	}

}

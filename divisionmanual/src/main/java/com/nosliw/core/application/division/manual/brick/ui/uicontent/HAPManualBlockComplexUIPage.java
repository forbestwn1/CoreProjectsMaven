package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIPage;
import com.nosliw.core.application.common.style.HAPUIStyle;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualBlockComplexUIPage extends HAPManualBrickImp implements HAPBlockComplexUIPage{

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

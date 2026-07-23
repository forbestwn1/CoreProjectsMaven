package com.nosliw.core.application.brick.ui.uicontent;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.style.HAPUIStyle;

@HAPEntityWithAttribute
public interface HAPWithUIContent {

	@HAPAttribute
	static final public String UICONTENT = "UIContent";  

	@HAPAttribute
	static final public String STYLE = "style";

	HAPBlockComplexUIContent getUIContent();
	
	HAPUIStyle getStyle();
	
}

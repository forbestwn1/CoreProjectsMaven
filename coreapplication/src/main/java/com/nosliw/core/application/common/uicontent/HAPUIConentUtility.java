package com.nosliw.core.application.common.uicontent;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPUIConentUtility {

	/*
	 * build custom tag name based on tag basic name
	 */
	public static String makeCustomTagName(String tag){
		return HAPUtilityNamingConversion.createKeyword(tag, HAPConstantShared.CUSTOMTAG_PREFIX);
	}
	
	public static boolean isDataKeyAttribute(String attribute){
		return attribute.startsWith(HAPConstantShared.UIRESOURCE_CUSTOMTAG_KEYATTRIBUTE_PREFIX+HAPConstantShared.UIRESOURCE_ATTRIBUTE_DATABINDING);
	}
	
	public static String makeKeyAttribute(String attribute){
		return HAPConstantShared.UIRESOURCE_CUSTOMTAG_KEYATTRIBUTE_PREFIX+attribute;
	}
	
	/*
	 * check whether a attribute name is key attribute: attribute name with some particular prefix
	 * if yes, return basic attribute name
	 * if no, return null
	 */
	public static String isKeyAttribute(String attribute){
		return HAPUtilityNamingConversion.getKeyword(attribute, HAPConstantShared.UIRESOURCE_CUSTOMTAG_KEYATTRIBUTE_PREFIX);
	}


}

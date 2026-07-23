package com.nosliw.core.application.common.style;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.application.HAPAttributeInBrick;
import com.nosliw.core.application.brick.container.HAPBrickContainerList;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUICustomerTag;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIPage;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIId;
import com.steadystate.css.parser.CSSOMParser;

public class HAPUIStyleUtility {

	public static String process(HAPBlockComplexUIPage page) {
		List<String> out = new ArrayList<String>();
		processWithUIContent(out, page, new ArrayList<String>());
		
//		return String.join(" ", out).replaceAll("(\\r|\\n)", "");
		
		return StringEscapeUtils.escapeEcmaScript(String.join(" ", out)).replaceAll("(\\r|\\n)", "");
		
//		return HAPUtilityJson.escape(String.join(" ", out)).replaceAll("(\\r|\\n)", "");
	}
	
	private static void processWithUIContent(List<String> output, HAPWithUIContent withUIContent, List<String> selectorParent) {
		List<String> selector = new ArrayList<>(selectorParent);

		String uiId = null;
		if(withUIContent instanceof HAPWithUIId) {
			uiId = ((HAPWithUIId)withUIContent).getUIId();
			selector.add(buildSelector(uiId));
		}
	
		HAPUIStyle style = withUIContent.getStyle();
		if(style!=null&&HAPUtilityBasic.isStringNotEmpty(style.getDefinition())) {
			String selectorStr = String.join(" ", selector);
			try {
		         // parse and create a stylesheet composition
				 InputSource source = new InputSource(new StringReader(style.getDefinition()));
		         CSSOMParser parser = new CSSOMParser();
		         CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);
		         CSSRuleList ruleList = stylesheet.getCssRules();
		         for (int i = 0; i < ruleList.getLength(); i++) 
		         {
		           CSSRule rule = ruleList.item(i);
		           if (rule instanceof CSSStyleRule) 
		           {
		               CSSStyleRule styleRule=(CSSStyleRule)rule;
		               String selectorText = styleRule.getSelectorText();
		               styleRule.setSelectorText(selectorStr + " " + selectorText);
		            }
		          }
		          output.add(stylesheet.toString());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		HAPBlockComplexUIContent uiContent = withUIContent.getUIContent();
		HAPBrickContainerList customerTagContainer = uiContent.getCustomerTags();
		
		List<HAPAttributeInBrick> elementAttrs = customerTagContainer.getElements();
		for(HAPAttributeInBrick eleAttr : elementAttrs) {
			HAPBlockComplexUICustomerTag customerTag = (HAPBlockComplexUICustomerTag)eleAttr.getValueWrapper().getValue();
			processWithUIContent(output, customerTag, selector);
		}
	}
	
	private static String buildSelector(String id) {
		
		return "["+HAPConstantShared.UIRESOURCE_ATTRIBUTE_STYLEID+"='"+id+"']";
	}

}

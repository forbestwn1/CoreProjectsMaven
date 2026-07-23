package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.common.style.HAPUIStyle;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpComplex;

public abstract class HAPManualPluginParserBlockComplexWithUIContent extends HAPManualDefinitionPluginParserBrickImpComplex{

	public HAPManualPluginParserBlockComplexWithUIContent(HAPIdBrickType brickTypeId,
			Class<? extends HAPManualDefinitionBrick> brickClass, HAPManualManagerBrick manualBrickMan,
			HAPManagerApplicationBrick brickMan) {
		super(brickTypeId, brickClass, manualBrickMan, brickMan);
	}

	@Override
	protected void parseDefinitionContentHtml(HAPManualDefinitionBrick brickManualDef, Object obj, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockComplxWithUIContent uiWithContent = (HAPManualDefinitionBlockComplxWithUIContent)brickManualDef;
		
		Element ele = null;
		if(obj instanceof String) {
			Document doc = Jsoup.parse((String)obj, "UTF-8");
			ele = doc.body();
		}
		else if(obj instanceof Element) {
			ele = (Element)obj;
		}
		
		this.parseStyle(ele, uiWithContent, parseContext);

		parseDefinitionContentElement(uiWithContent, ele, parseContext);
	}

	//parse style 
	private void parseStyle(Element element, HAPManualDefinitionBlockComplxWithUIContent withUIContent, HAPManualDefinitionContextParse parserContext) {
		List<Element> styleEles = HAPUtilityUIResourceParser.getChildElementsByTag(element, "style1");
		if(!styleEles.isEmpty()) {
			Element ele = styleEles.get(0);
			HAPUIStyle style = new HAPUIStyle();
			style.setDefinition(ele.html());
			style.setId(withUIContent.getUIId());
			withUIContent.setStyle(style);
			
			styleEles.stream().forEach(e->e.remove());
		}
	}
	
	abstract protected void parseDefinitionContentElement(HAPManualDefinitionBlockComplxWithUIContent uiWithContent, Element element, HAPManualDefinitionContextParse parseContext);
}

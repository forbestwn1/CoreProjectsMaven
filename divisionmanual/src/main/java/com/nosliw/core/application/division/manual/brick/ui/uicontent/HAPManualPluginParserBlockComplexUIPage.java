package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.List;

import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPWithUIContent;
import com.nosliw.core.application.division.manual.common.valuecontext.HAPManualParserValueContext;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;
import com.nosliw.core.xxx.application1.HAPWithValueContext;

public class HAPManualPluginParserBlockComplexUIPage extends HAPManualPluginParserBlockComplexWithUIContent{

	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManualPluginParserBlockComplexUIPage(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan, HAPServiceParseEntity entityParseService) {
		super(HAPEnumBrickType.UIPAGE_100, HAPManualDefinitionBlockComplexUIPage.class, manualDivisionEntityMan, brickMan);
		this.m_entityParseService = entityParseService;
	}

	@Override
	protected void parseDefinitionContentElement(HAPManualDefinitionBlockComplxWithUIContent uiWithContent, Element element, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockComplexUIPage uiPage = (HAPManualDefinitionBlockComplexUIPage)uiWithContent;
		List<Element> pageEles = HAPUtilityUIResourceParser.getChildElementsByTag(element, "page");
		if(!pageEles.isEmpty()) {
			this.parseValueContext(pageEles.get(0), uiPage, parseContext, m_entityParseService);
		}
		
		this.parseBrickAttributeHtml(uiPage, element, HAPWithUIContent.UICONTENT, HAPEnumBrickType.UICONTENT_100, null, parseContext);
	}

	private void parseValueContext(Element ele, HAPManualDefinitionBrick brickManualDef, HAPManualDefinitionContextParse parseContext, HAPServiceParseEntity entityParseService) {
		List<Element> valueContextEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, HAPWithValueContext.VALUECONTEXT);
		for(Element valueContextEle : valueContextEles){
			HAPManualParserValueContext.parseValueContextContentJson(brickManualDef.getValueContextBrick(), new JSONObject(Parser.unescapeEntities(valueContextEle.html(), false)), parseContext, entityParseService);
			break;
		}
		for(Element valueContextEle : valueContextEles) {
			valueContextEle.remove();
		}
	}
	

}

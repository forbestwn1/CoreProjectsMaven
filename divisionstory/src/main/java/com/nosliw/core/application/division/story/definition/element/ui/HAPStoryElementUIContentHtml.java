package com.nosliw.core.application.division.story.definition.element.ui;

import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryParserElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementUIContentHtml extends HAPStoryElement{

	public static final String CHILD_CHILDREN = "children";
	
	public static final String HTML = "html";
	
	private String m_html;
	
	public HAPStoryElementUIContentHtml() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_UICONTENT_HTML));
	}
	
	public HAPStoryElementUIContentHtml(String html) {
		this();
		this.m_html = html;
	}
	
	public static HAPPath getAddChildChildPath() {	   return HAPStoryUtilityElement.getAddElementChildPath(new HAPPath(CHILD_CHILDREN));   }

	
	public String getHtml() {    return this.m_html;       }
	void setHtml(String html) {     this.m_html = html;     }

	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(HTML, StringEscapeUtils.escapeJson(m_html));
	}
	
}

@Component
class HAPStoryElementUIContentHtml__HAPEntityParsable extends HAPStoryParserElement{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_UICONTENT_HTML;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementUIContentHtml element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		element.setHtml(StringEscapeUtils.unescapeJson(jsonObj.getString(HAPStoryElementUIContentHtml.HTML)));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementUIContentHtml out = new HAPStoryElementUIContentHtml();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

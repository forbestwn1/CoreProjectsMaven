package com.nosliw.core.application.division.story.design.wizzard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardQuestionairGroup extends HAPStoryWizzardQuestionair{

	@HAPAttribute
	public static final String ITEM = "item";
	
	private List<HAPStoryWizzardQuestionair> m_items;
	
    public HAPStoryWizzardQuestionairGroup(String tag) {
    	super(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_GROUP, tag);
    	this.m_items = new ArrayList<HAPStoryWizzardQuestionair>();
    }
    
    public HAPStoryWizzardQuestionairGroup() {
    	this(null);
    }
    
	public void addItem(HAPStoryWizzardQuestionair item) {    this.m_items.add(item);    }
	public List<HAPStoryWizzardQuestionair> getItems(){     return this.m_items;      }

	@Override
	void clear() {
		for(HAPStoryWizzardQuestionair item : this.m_items) {
			item.clear();
		}
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ITEM, HAPManagerSerialize.getInstance().toStringValue(this.m_items, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPStoryWizzardQuestionairGroup_HAPEntityParsable extends HAPStoryWizzardQuestionair_Parsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYDESIGN_QUESTIONTYPE_GROUP;  }

    @Override
	protected void parseQuestionair(HAPStoryWizzardQuestionair questionair, JSONObject jsonObj, HAPServiceParseEntity parseService) {
    	super.parseQuestionair(questionair, jsonObj, parseService);

		HAPStoryWizzardQuestionairGroup out = (HAPStoryWizzardQuestionairGroup)questionair;

		JSONArray itemArray = jsonObj.optJSONArray(HAPStoryWizzardQuestionairGroup.ITEM);
		if(itemArray!=null) {
			for(int i=0; i<itemArray.length(); i++) {
				JSONObject itemObj = itemArray.getJSONObject(i);
				HAPStoryWizzardQuestionair item = (HAPStoryWizzardQuestionair)parseService.parseEntityJSONImplicitAttribute(itemObj, HAPStoryWizzardQuestionair.TYPE, HAPStoryWizzardQuestionair.PARSE_DOMAIN);
				out.addItem(item);
			}
		}
    }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryWizzardQuestionairGroup out = new HAPStoryWizzardQuestionairGroup();
		this.parseQuestionair(out, (JSONObject)obj, parseService);
		return out;
	}

}


package com.nosliw.core.application.division.story.design.change;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public abstract class HAPStoryChangeItem extends HAPEntityInfoImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "story.change.item";
	
	@HAPAttribute
	public static final String CHANGETYPE = "changeType";

	@HAPAttribute
	public static final String REVERTCHANGES = "revertChanges";

	@HAPAttribute
	public static final String REVERTABLE = "revertable";

	@HAPAttribute
	public static final String EXTENDFROM = "extendFrom";

	@HAPAttribute
	public static final String EXTENDED = "extended";

	private String m_changeType;
	
	private List<HAPStoryChangeItem> m_revertChanges;

	private boolean m_revertable;

	private String m_extendedFrom;
	
	private boolean m_extended;
	
	public HAPStoryChangeItem(String changeType) {
		this.m_revertChanges = new ArrayList<HAPStoryChangeItem>();
		this.m_changeType = changeType;
		this.m_revertable = true;
		this.m_extended = false;
	}

	public String getChangeType() {    return this.m_changeType;    }
	
	public void addRevertChange(HAPStoryChangeItem changeItem) {  this.m_revertChanges.add(changeItem);      }
	public void setRevertChanges(List<HAPStoryChangeItem> revertChanges) {    this.m_revertChanges = revertChanges;      }
	public List<HAPStoryChangeItem> getRevertChanges(){     return this.m_revertChanges;       }
	
	public boolean isRevertable() {    return this.m_revertable;     }
	public void setRevertable(boolean revertable) {     this.m_revertable = revertable;      }
	
	public String getExtendFrom() {    return this.m_extendedFrom;      }
	public void setExtendFrom(String extendFrom) {     this.m_extendedFrom = extendFrom;       }
	
	public boolean isExtended() {   return this.m_extended;     }
	public void setExtended() {    this.m_extended = true;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CHANGETYPE, this.m_changeType);
		if(this.m_revertChanges!=null) {
			jsonMap.put(REVERTCHANGES, HAPUtilityJson.buildJson(this.m_revertChanges, HAPSerializationFormat.JSON));
		}
		jsonMap.put(REVERTABLE, this.m_revertable+"");
		typeJsonMap.put(REVERTABLE, Boolean.class);
		jsonMap.put(EXTENDFROM, this.m_extendedFrom);
		jsonMap.put(EXTENDED, this.m_extended+"");
		typeJsonMap.put(EXTENDED, Boolean.class);
	}
}

abstract class HAPStoryChangeItem__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeItem changeItem, HAPServiceParseEntity parseService) {
		Object revertableObj = jsonObj.opt(HAPStoryChangeItem.REVERTABLE);
		if(revertableObj!=null) {
			changeItem.setRevertable((Boolean)revertableObj);
		} 

		changeItem.setExtendFrom((String)jsonObj.opt(HAPStoryChangeItem.EXTENDFROM));

		if(jsonObj.opt(HAPStoryChangeItem.EXTENDED)!=null) {
			if(jsonObj.getBoolean(HAPStoryChangeItem.EXTENDED)) {
				changeItem.setExtended();
			}
		}

		JSONArray revertChangesArray = jsonObj.optJSONArray(HAPStoryChangeItem.REVERTCHANGES);
		if(revertChangesArray!=null) {
			for(int i=0; i<revertChangesArray.length(); i++) {
				changeItem.addRevertChange((HAPStoryChangeItem)parseService.parseEntityJSONImplicitAttribute(revertChangesArray.getJSONObject(i), HAPStoryChangeItem.CHANGETYPE, HAPStoryChangeItem.PARSABLEENTITYDOMAIN));
			}
		}
	}
	
	@Override
	public String getDomain() {   return HAPStoryChangeItem.PARSABLEENTITYDOMAIN;   }

}


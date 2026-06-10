package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

@HAPEntityWithAttribute
public class HAPStoryIdElement  extends HAPSerializableImp implements HAPStoryReferenceElement{

	@HAPAttribute
	public static final String TYPE = "type";

	@HAPAttribute
	public static final String ID = "id";

	private HAPStoryIdElementType m_typeId;
	
	private String m_id;
	
	public HAPStoryIdElement() {}
	
	public HAPStoryIdElement(String key) {
		this.parseKey(key);
	}

	public HAPStoryIdElement(String id, HAPStoryIdElementType typeId) {
		this.m_typeId = typeId;
		this.m_id = id;
	}
	
	@Override
	public String getEntityOrReferenceType() {  return HAPConstantShared.STORY_ELEMENT_REFERENCE_ID;  }

	public HAPStoryIdElementType getTypeId() {    return this.m_typeId;     }
	
	public String getId() {    return this.m_id;    }

	public String getKey() {      
		return this.m_typeId==null? this.getId() : HAPUtilityNamingConversion.cascadeLevel2(new String[]{this.getId(), this.getTypeId().getKey()});
	}
	
	public void parseKey(String key) {
		String[] segs = HAPUtilityNamingConversion.parseLevel2(key);
		this.m_id = segs[0];
		if(segs.length>=2) {
			this.m_typeId = new HAPStoryIdElementType(segs[1]);
		}
	}
	
	@Override
	public HAPStoryReferenceElement cloneElementReference() {
		HAPStoryIdElement out = new HAPStoryIdElement();
		out.m_typeId = this.m_typeId;
		out.m_id = this.m_id;
		return out;
	}

	@Override
	protected String buildLiterate(){
		return this.getKey();
	}

	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		parseKey(literateValue);
		return true;
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		Object idObj = jsonObj.opt(ID);
		if(idObj!=null) {
			this.m_id = (String)idObj;
		}
		Object typeObj = jsonObj.opt(TYPE);
		if(typeObj!=null) {
			this.m_typeId = new HAPStoryIdElementType((String)typeObj);
		}
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.m_typeId.toString());
		jsonMap.put(ID, this.m_id);
	}
	
	@Override
	public int hashCode() {
		return this.getKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean out = false;
		if(obj instanceof HAPStoryIdElement) {
			HAPStoryIdElement eleId = (HAPStoryIdElement)obj;
			if(HAPUtilityBasic.isEquals(this.m_typeId, eleId.m_typeId)) {
				if(HAPUtilityBasic.isEquals(this.m_id, eleId.m_id)) {
					out = true;
				}
			}
		}
		return out;
	}
}

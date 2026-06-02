package com.nosliw.core.application.division.story.design.change;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryMetaDataChildElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryChangeInfoConnectionContainer extends HAPStoryChangeInfoConnection{

	public final static String CHILDPATH = "childPath";
	
	public final static String METADATA = "metaData";
	
	private HAPPath m_childPath;
	
	private HAPStoryMetaDataChildElement m_metaData;
	
    public HAPStoryChangeInfoConnectionContainer() {
    	super(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN);
	}
	
    public HAPStoryChangeInfoConnectionContainer(HAPPath path, HAPStoryMetaDataChildElement metaData) {
    	this();
    	this.m_childPath = path;
    	this.m_metaData = metaData;
    }	

    public HAPStoryChangeInfoConnectionContainer(HAPPath path) {
    	this(path, null);
    }	

    public HAPPath getChildPath() {    return this.m_childPath;     }
    public void setChildPath(HAPPath path) {     this.m_childPath = path;        }
    
    public HAPStoryMetaDataChildElement getMetaData() {     return this.m_metaData;       }
    public void setMetaData(HAPStoryMetaDataChildElement metaData) {     this.m_metaData = metaData;       }
 
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CHILDPATH, this.m_childPath.toString());
		if(this.m_metaData!=null) {
			jsonMap.put(METADATA, this.m_metaData.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

@Component
class HAPStoryChangeInfoConnectionContainer_HAPEntityParsable extends HAPStoryChangeInfoConnection_HAPEntityParsable{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryChangeInfoConnectionContainer changeItem, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, changeItem, parseService);
		changeItem.setChildPath(new HAPPath(jsonObj.getString(HAPStoryChangeInfoConnectionContainer.CHILDPATH)));

		JSONObject metaDataJsonObj = jsonObj.optJSONObject(HAPStoryChangeInfoConnectionContainer.METADATA);
		if(metaDataJsonObj!=null) {
			changeItem.setMetaData((HAPStoryMetaDataChildElement)parseService.parseEntityJSONImplicitAttribute(metaDataJsonObj, HAPStoryMetaDataChildElement.TYPE, HAPStoryMetaDataChildElement.PARSABLEENTITYDOMAIN));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryChangeInfoConnectionContainer out = new HAPStoryChangeInfoConnectionContainer();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

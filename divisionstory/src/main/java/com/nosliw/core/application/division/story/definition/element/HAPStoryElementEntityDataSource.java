package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfo;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfoParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithCommand;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementEntityDataSource extends HAPStoryElementImpWithEntityInfo implements HAPStoryElementWithCommand{
	
	public static final HAPStoryIdElementType TYPEID = new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_SERVICE);

	public static final String COMMAND_EXECUTE = "execute";
	
	public static final String SERVICEID = "serviceId";
	
	private String m_serviceId;
	
	public HAPStoryElementEntityDataSource() {
		this(null, null);
	}
	
	public HAPStoryElementEntityDataSource(String serviceId, HAPEntityInfo dataSourceInfo) {
		super(TYPEID, dataSourceInfo);
	}

	public static HAPPath buildPathToCommandExecute() {
		return HAPStoryElementWithCommand.getAddCommandChildPath(HAPStoryElementEntityDataSource.COMMAND_EXECUTE);
	}
	
	void setServiceId(String serviceId) {     this.m_serviceId = serviceId;      }
	
	protected void cloneToStoryElement(HAPStoryElementEntityDataSource storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_serviceId = this.m_serviceId;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementEntityDataSource out = new HAPStoryElementEntityDataSource();
		this.cloneToStoryElement(out);
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(SERVICEID, this.m_serviceId);
	}
	
}

@Component
class HAPStoryElementEntityDataSource__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {      return HAPConstantShared.STORYNODE_TYPE_SERVICE;      }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementEntityDataSource element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		String serviceId = (String)jsonObj.opt(HAPStoryElementEntityDataSource.SERVICEID);
		if(serviceId!=null) {
			element.setServiceId(serviceId);
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementEntityDataSource out = new HAPStoryElementEntityDataSource();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

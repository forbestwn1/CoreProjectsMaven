package com.nosliw.core.service.staticresource;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPStaticRequestInfoFolder extends HAPStaticRequestInfo{

	@HAPAttribute
	public static final String FOLDER = "folder";

	private String m_folder;

	public HAPStaticRequestInfoFolder() {
		super(HAPConstantShared.STATIC_REQUEST_TYPE_FOLDER);
	}
	
	public HAPStaticRequestInfoFolder(String folder) {
		this();
		this.m_folder = folder;
	}
	
	public String getFolder() {		return this.m_folder;	}
	public void setFolder(String folder) {    this.m_folder = folder;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(FOLDER, this.m_folder);
	}
	
}

@Component
class HAPStaticRequestInfoFolder__HAPEntityParsable extends HAPStaticRequestInfo__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.STATIC_REQUEST_TYPE_FOLDER;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticRequestInfoFolder staticRequestInfoFolder, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticRequestInfoFolder, parseService);
		staticRequestInfoFolder.setFolder(jsonObj.getString(HAPStaticRequestInfoFolder.FOLDER));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticRequestInfoFolder out = new HAPStaticRequestInfoFolder();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

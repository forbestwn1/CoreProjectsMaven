package com.nosliw.core.service.staticresource;

import java.io.File;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStaticRequestInfoFolder extends HAPStaticRequestInfo{

	@HAPAttribute
	public static final String FOLDER = "folder";

	private File m_folder;

	public HAPStaticRequestInfoFolder() {
		super(HAPStaticRequestInfo.STATIC_TYPE_FOLDER);
	}
	
	public HAPStaticRequestInfoFolder(File folder) {
		this();
		this.m_folder = folder;
	}
	
	public File getFolder() {		return this.m_folder;	}
	public void setFolder(File folder) {    this.m_folder = folder;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(FOLDER, this.m_folder.getAbsolutePath());
	}
	
}

@Component
class HAPStaticRequestInfoFolder__HAPEntityParsable extends HAPDataDefinition__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPStaticRequestInfo.STATIC_TYPE_FOLDER;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticRequestInfoFolder staticRequestInfoFolder, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticRequestInfoFolder, parseService);
		staticRequestInfoFolder.setFolder(new File(jsonObj.getString(HAPStaticRequestInfoFolder.FOLDER)));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticRequestInfoFolder out = new HAPStaticRequestInfoFolder();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

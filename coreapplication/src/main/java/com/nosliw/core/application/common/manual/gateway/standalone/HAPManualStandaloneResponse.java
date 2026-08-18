package com.nosliw.core.application.common.manual.gateway.standalone;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.manual.HAPManualContentProvider;
import com.nosliw.core.application.common.manual.HAPManualContentProviderText;

@HAPEntityWithAttribute
public class HAPManualStandaloneResponse extends HAPSerializableImp implements HAPEntityParsable{
	
	@HAPAttribute
	public static final String ID = "id";

	@HAPAttribute
	public static final String CONTENTPROVIDER = "contentProvider";
	
	private String m_id;
	
	private HAPManualContentProvider m_contentProvider;

	public HAPManualStandaloneResponse() {}

	public HAPManualStandaloneResponse(HAPManualContentProvider contentProvider) {
		this.m_contentProvider = contentProvider;
	}
	
	public HAPManualContentProvider getContentProvider() {     return this.m_contentProvider;      }
	public void setContentProvider(HAPManualContentProvider contentProvider) {     this.m_contentProvider = contentProvider;       }
	
	public String getId() {     return this.m_id;      }
	public void setId(String id) {     this.m_id = id;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ID, this.m_id);
		jsonMap.put(CONTENTPROVIDER, this.m_contentProvider.toStringValue(HAPSerializationFormat.JSON));
	}

}

@Component
class HAPManualStandaloneResponse_parse implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPManualStandaloneResponse.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualStandaloneResponse out = new HAPManualStandaloneResponse();
		
		JSONObject jsonObj = (JSONObject)obj;

		out.setId((String)jsonObj.opt(HAPManualStandaloneResponse.ID));
		
		JSONObject contentProviderJsonObj = jsonObj.optJSONObject(HAPManualStandaloneResponse.CONTENTPROVIDER);
		if(contentProviderJsonObj!=null) {
			String type = contentProviderJsonObj.getString(HAPManualContentProvider.TYPE);
			if(type.equals(HAPConstantShared.MANUAL_CONTENTPROVIDER_TYPE_TEXT)) {
				out.setContentProvider((HAPManualContentProvider)parseService.parseEntityJSONExplicit(contentProviderJsonObj, HAPManualContentProviderText.class.getName()));
			}
		}
		
		return out;
	}
	
}

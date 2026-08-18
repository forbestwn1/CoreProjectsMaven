package com.nosliw.core.application.common.manual;

import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.brick.HAPIdBrickType;

public class HAPManualInfoContent extends HAPSerializableImp{

	public static final String FORMAT = "format";
	
	public static final String CONTENT = "content";
	
	public static final String BRICKTYPEID = "brickTypeId";
	
	private HAPSerializationFormat m_format;
	
	private String m_content;
	
	//brick type id if not provided
	private HAPIdBrickType m_brickTypeId;

	public HAPManualInfoContent() {}
	
	public HAPManualInfoContent(String content, HAPIdBrickType brickTypeId) {
		this(content, null, brickTypeId);
	}

	public HAPManualInfoContent(String content, HAPSerializationFormat format) {
		this(content, format, null);
	}
	
	public HAPManualInfoContent(String content, HAPSerializationFormat format, HAPIdBrickType brickTypeId) {
		this.m_format = format;
		if(this.m_format==null) {
			this.m_format = HAPSerializationFormat.JSON;
		}
		this.m_content = content;
		this.m_brickTypeId = brickTypeId;
	}
	
	public HAPSerializationFormat getFormat() {     return this.m_format;      }
	
	public String getContent() {      return this.m_content;        }
	
	public void setContent(String content) {      this.m_content = content;       }
	
	public HAPIdBrickType getBrickTypeId() {    return this.m_brickTypeId;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		if(this.m_format!=null) {
			jsonMap.put(FORMAT, this.m_format.toString());
		}
		if(this.m_content!=null) {
			jsonMap.put(CONTENT, StringEscapeUtils.escapeJson(this.m_content));
		}
		if(this.m_brickTypeId!=null) {
			jsonMap.put(BRICKTYPEID, this.m_brickTypeId.toStringValue(HAPSerializationFormat.JSON));
		}
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		this.m_content = StringEscapeUtils.unescapeJson((String)jsonObj.opt(CONTENT));
		
		String formatJsonStr = (String)jsonObj.opt(FORMAT);
		if(formatJsonStr!=null) {
			this.m_format = HAPSerializationFormat.valueOf(formatJsonStr);
		}
		
		JSONObject brickTypeIdJsonObj = jsonObj.optJSONObject(BRICKTYPEID);
		if(brickTypeIdJsonObj!=null) {
			this.m_brickTypeId = new HAPIdBrickType();
			this.m_brickTypeId.buildObject(brickTypeIdJsonObj, HAPSerializationFormat.JSON);
		}
		
		return true;
	}

}

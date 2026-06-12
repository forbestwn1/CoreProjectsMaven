package com.nosliw.core.application.division.manual.core;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrickType;

public class HAPManualInfoContent {

	private HAPSerializationFormat m_format;
	
	private String m_content;
	
	//brick type id if not provided
	private HAPIdBrickType m_brickTypeId;

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
	
}

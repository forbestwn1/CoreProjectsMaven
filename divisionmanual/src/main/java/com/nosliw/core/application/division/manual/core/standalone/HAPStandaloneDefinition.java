package com.nosliw.core.application.division.manual.core.standalone;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrickType;

public class HAPStandaloneDefinition {

	private String m_content;
	
	private HAPSerializationFormat m_format;
	
	//brick type id if not provided
	private HAPIdBrickType m_brickTypeId;

	public HAPStandaloneDefinition() {
		
	}
	
	public HAPStandaloneDefinition(String content, HAPSerializationFormat format, HAPIdBrickType brickTypeId) {
		this.m_content = content;
		this.m_format = format;
		this.m_brickTypeId = brickTypeId;
	}
	

	public String getContent() {    return this.m_content;      }
	
	public HAPSerializationFormat getFormat() {     return this.m_format;      }
	
	public HAPIdBrickType getBrickTypeId() {    return this.m_brickTypeId;      }
	
}

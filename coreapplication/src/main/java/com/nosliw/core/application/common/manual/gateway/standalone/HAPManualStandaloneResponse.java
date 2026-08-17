package com.nosliw.core.application.common.manual.gateway.standalone;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.application.common.manual.HAPManualContentProvider;

public class HAPManualStandaloneResponse extends HAPSerializableImp{
	
	private String m_id;
	
	private HAPManualContentProvider m_contentProvider;
	
	public HAPManualStandaloneResponse(HAPManualContentProvider contentProvider) {
		this.m_contentProvider = contentProvider;
	}
	
	public HAPManualContentProvider getContentProvider() {     return this.m_contentProvider;      }
	
	public String getId() {     return this.m_id;      }
	public void setId(String id) {     this.m_id = id;      }

}

package com.nosliw.core.application.entity.uitag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.common.uitag.HAPUITagInfo;
import com.nosliw.core.application.common.uitag.HAPUITageQueryData;

@Component
public class HAPServiceUITag {

	@Autowired
	private HAPManagerUITag m_uiTagMan;
	
	public HAPUITagInfo getDefaultUITagData(HAPUITageQueryData query) {
		return this.m_uiTagMan.getDefaultUITagData(query);
	}

	
	
}

package com.nosliw.core.application.division.manual.gateway;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.entity.uitag.HAPUITagDefinition;
import com.nosliw.core.data.matcher.HAPMatchersCombo;

@HAPEntityWithAttribute
public class HAPUITagStandaloneApplication {

	@HAPAttribute
    public static final String UITAGID = "uiTagId";
	
	@HAPAttribute
    public static final String ATTRIBUTES = "attributes";
	
	@HAPAttribute
    public static final String DATADEFINITION = "dataDefinition";
	
	@HAPAttribute
    public static final String MATCHERS = "matchers";
	
	private String m_uiTagId;
	
	private HAPUITagDefinition m_uiTagDefinition;
	
	private Map<String, String> m_attributes;
	
	private HAPDataDefinition m_dataDefinition;
	
	private Map<String, HAPMatchersCombo> m_matchers;

	
	
}

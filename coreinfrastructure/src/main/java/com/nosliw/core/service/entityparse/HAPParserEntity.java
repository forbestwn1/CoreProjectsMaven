package com.nosliw.core.service.entityparse;

public interface HAPParserEntity {

	String getEntityType();
	
	HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService);
	
}

package com.nosliw.common.serialization;

public interface HAPParserEntity {

	String getEntityType();
	
	HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService);
	
}

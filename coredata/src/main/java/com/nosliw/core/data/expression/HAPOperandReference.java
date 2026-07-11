package com.nosliw.core.data.expression;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.variable.HAPIdVariable;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.resource.HAPResourceId;

public interface HAPOperandReference extends HAPOperand{

	@HAPAttribute
	public static final String RESOURCEID = "resourceId";

	@HAPAttribute
	public static final String VARMAPPING = "varMapping";
	
	@HAPAttribute
	public static final String VARRESOLVE = "varResolve";
	
	@HAPAttribute
	public static final String VARMATCHERS = "varMatchers";
	
	HAPResourceId getResourceId();
	
	Map<String, HAPOperand> getMapping();

	Map<String, HAPMatchers> getMatchers();

	Map<String, HAPIdVariable> getResolvedVariable();
	
}

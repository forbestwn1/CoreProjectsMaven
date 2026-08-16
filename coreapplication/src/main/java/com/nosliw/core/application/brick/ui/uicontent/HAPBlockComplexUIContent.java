package com.nosliw.core.application.brick.ui.uicontent;

import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainerList;
import com.nosliw.core.application.common.scriptexpressio.HAPContainerScriptExpression;
import com.nosliw.core.application.common.task.HAPWithBrickTasks;

@HAPEntityWithAttribute
public interface HAPBlockComplexUIContent extends HAPBrick, HAPWithBrickTasks{

	@HAPAttribute
	static final public String HTML = "html";

	@HAPAttribute
	static final public String CUSTOMERTAG = "customerTag";  
	
	@HAPAttribute
	static final public String SCRIPTEXPRESSIONINCONTENT = "scriptExpressionInContent";

	@HAPAttribute
	static final public String SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE = "scriptExpressionInNormalTagAttribute";
	
	@HAPAttribute
	static final public String SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE = "scriptExpressionInCustomerTagAttribute";

	@HAPAttribute
	static final public String SCRIPTEXPRESSIONS = "scriptExpressions";

	@HAPAttribute
	static final public String TAGALIASMAPPING = "tagAliasMapping";

	String getHtml();
	
	HAPBrickContainerList getCustomerTags();
	
	List<HAPUIEmbededScriptExpressionInContent> getScriptExpressionInContent();
	
	List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInNormalTagAttribute();
	
	List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInCustomerTagAttribute();
	
	HAPContainerScriptExpression getScriptExpressions();
	
	//uiid - alias for tag in content
	Map<String, String> getTagAliasMapping();

	
	
	
	@HAPAttribute
	static final public String SCRIPT = "scripttaskgroup";  
	
	@HAPAttribute
	static final public String SERVICE = "service";  
	
}

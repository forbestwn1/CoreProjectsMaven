package com.nosliw.core.application.division.manual.brick.ui.uicontent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

//import org.apache.commons.lang.StringEscapeUtils;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.ui.uicontent.HAPBlockComplexUIContent;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInAttribute;
import com.nosliw.core.application.brick.ui.uicontent.HAPUIEmbededScriptExpressionInContent;
import com.nosliw.core.application.common.constant.HAPDefinitionConstant;
import com.nosliw.core.application.common.parentrelation.HAPManualDefinitionBrickRelation;
import com.nosliw.core.application.common.scriptexpressio.definition.HAPDefinitionContainerScriptExpression;
import com.nosliw.core.application.division.manual.brick.container.HAPManualDefinitionBrickContainer;
import com.nosliw.core.application.division.manual.brick.container.HAPManualDefinitionBrickContainerList;
import com.nosliw.core.application.division.manual.common.task.HAPManualDefinitionWithBrickTasks;
import com.nosliw.core.application.division.manual.core.HAPManualEnumBrickType;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionAttributeInBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionWrapperValueBrick;
import com.nosliw.core.xxx.application1.HAPWithValueContext;

public class HAPManualDefinitionBlockComplexUIContent extends HAPManualDefinitionBrick  implements HAPManualDefinitionWithBrickTasks{

	private static final String ID_INDEX = "idIndex";
	
	private static final String CONSTANTFROMPARENT = "constantFromParent";

	public HAPManualDefinitionBlockComplexUIContent() {
		super(HAPEnumBrickType.UICONTENT_100);
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONS, new HAPDefinitionContainerScriptExpression());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCONTENT, new ArrayList<HAPUIEmbededScriptExpressionInContent>());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE, new ArrayList<HAPUIEmbededScriptExpressionInAttribute>());
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE, new ArrayList<HAPUIEmbededScriptExpressionInAttribute>());
		this.setAttributeValueWithValue(CONSTANTFROMPARENT, new LinkedHashMap<String, HAPDefinitionConstant>());
		this.setAttributeValueWithValue(ID_INDEX, new Integer(0));
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.TAGALIASMAPPING, new LinkedHashMap<String, String>());
	}

	@Override
	public void init() {
		this.setAttributeValueWithBrick(HAPWithValueContext.VALUECONTEXT, this.getManualBrickManager().newBrickDefinition(HAPManualEnumBrickType.VALUECONTEXT_100));
		this.setAttributeValueWithBrick(HAPBlockComplexUIContent.CUSTOMERTAG, this.getManualBrickManager().newBrickDefinition(HAPEnumBrickType.CONTAINERLIST_100));
	}
	
	public void addCustomerTag(HAPManualDefinitionBlockComplexUICustomerTag customerTag) {
		HAPManualDefinitionBrickContainerList container = this.getCustomerTagContainer();
		HAPManualDefinitionAttributeInBrick attr = new HAPManualDefinitionAttributeInBrick();
		attr.setValueWrapper(new HAPManualDefinitionWrapperValueBrick(customerTag));
		
		for(HAPManualDefinitionBrickRelation relation : customerTag.getParentRelations()) {
			attr.addRelation(relation);
		}
		
		Map<String, String> customerTagMetaData = customerTag.getMetaData();
		for(String name : customerTagMetaData.keySet()) {
			attr.getInfo().setValue(name, customerTagMetaData.get(name));
		}
		
		container.addElementWithAttribute(attr);
	}
	
	private HAPManualDefinitionBrickContainerList getCustomerTagContainer() {
		return (HAPManualDefinitionBrickContainerList)this.getAttributeValueOfBrick(HAPBlockComplexUIContent.CUSTOMERTAG);
	}

	public Map<String, String> getTagAliasMapping(){       return (Map<String, String>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.TAGALIASMAPPING);        }
	public void addTagAliasMapping(String uiid, String alias) {     this.getTagAliasMapping().put(uiid, alias);        }
	
	public String getHtml() {    return (String)this.getAttributeValueOfValue(HAPBlockComplexUIContent.HTML);  }
	public void setHtml(String html) {    
		this.setAttributeValueWithValue(HAPBlockComplexUIContent.HTML, StringEscapeUtils.escapeHtml4(html).replaceAll("(\\r|\\n)", ""));      }
	
	public List<HAPUIEmbededScriptExpressionInContent> getScriptExpressionInContent(){   return (List<HAPUIEmbededScriptExpressionInContent>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCONTENT);       }
	public void addScriptExpressionInContent(HAPUIEmbededScriptExpressionInContent scriptExpressionInContent) {   this.getScriptExpressionInContent().add(scriptExpressionInContent);    }

	public List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInNormalTagAttribute(){   return (List<HAPUIEmbededScriptExpressionInAttribute>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINNORMALTAGATTRIBUTE);       }
	public void addScriptExpressionInNormalTagAttribute(HAPUIEmbededScriptExpressionInAttribute scriptExpressionInAttribute) {   this.getScriptExpressionInNormalTagAttribute().add(scriptExpressionInAttribute);    }
	
	public List<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionInCustomerTagAttribute(){   return (List<HAPUIEmbededScriptExpressionInAttribute>)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONINCUSTOMERTAGATTRIBUTE);       }
	public void addScriptExpressionInCustomerTagAttribute(HAPUIEmbededScriptExpressionInAttribute scriptExpressionInAttribute) {   this.getScriptExpressionInCustomerTagAttribute().add(scriptExpressionInAttribute);    }
	
	public HAPDefinitionContainerScriptExpression getScriptExpressions() {    return (HAPDefinitionContainerScriptExpression)this.getAttributeValueOfValue(HAPBlockComplexUIContent.SCRIPTEXPRESSIONS);      }

	public Map<String, HAPDefinitionConstant> getConstantsFromParent(){    return (Map<String, HAPDefinitionConstant>)this.getAttributeValueOfValue(CONSTANTFROMPARENT);   }
	public void addConstantFromParent(HAPDefinitionConstant constant) {    this.getConstantsFromParent().put(constant.getName(), constant);        }

	@Override
	public HAPManualDefinitionBrickContainer getTasks() {   return (HAPManualDefinitionBrickContainer)this.getAttributeValueOfBrick(HAPManualDefinitionWithBrickTasks.TASK);   }
	public void addTask(HAPManualDefinitionBrick taskBrickWrapper) {    this.getTasks().addElementWithBrick(taskBrickWrapper);    }
	
	@Override
	public Map<String, HAPDefinitionConstant> getConstantDefinitions(){
		Map<String, HAPDefinitionConstant> out = new LinkedHashMap<String, HAPDefinitionConstant>();
		out.putAll(super.getConstantDefinitions());
		out.putAll(this.getConstantsFromParent());
		return out;
	}
	
	@Override
	public String generateId() {
		int idIndex = (Integer)this.getAttributeValueOfValue(ID_INDEX);
		idIndex++;
		this.setAttributeValueWithValue(ID_INDEX, new Integer(idIndex));
		return idIndex+"";
	}
	
}

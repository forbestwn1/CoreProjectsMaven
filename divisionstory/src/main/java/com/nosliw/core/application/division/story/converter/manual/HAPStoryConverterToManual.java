package com.nosliw.core.application.division.story.converter.manual;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.application.common.structure.HAPElementStructureLeafData;
import com.nosliw.core.application.common.structure.HAPRootInStructure;
import com.nosliw.core.application.common.structure.HAPValueContextDefinitionImp;
import com.nosliw.core.application.common.structure.HAPValueStructureImp;
import com.nosliw.core.application.common.structure.HAPWrapperValueStructureDefinitionImp;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.division.manual.core.process.HAPManualContentProviderText;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryChildElement;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsWrapper;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithVariable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentHtml;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentTagCustom;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIPage;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIWrapperContent;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryMetaDataChildElementUI;

public class HAPStoryConverterToManual {

	public static HAPManualContentProviderText convert(HAPStoryStory story) {
		HAPManualContentProviderText out = new HAPManualContentProviderText();
		
		//get module element (root)
		HAPStoryElementEntityModule moduleElement = (HAPStoryElementEntityModule)story.getElement(new HAPStoryAliasElement(HAPStoryStory.ALIAS_ROOT));
		
		InputStream moduleTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "module.temp");
		Map<String, String> moduleTemplateParms = new LinkedHashMap<String, String>();
		out.setMainContent(new HAPManualInfoContent(HAPStringTemplateUtil.getStringValue(moduleTemplateStream, moduleTemplateParms), HAPEnumBrickType.MODULE_100));
		
		//get page elemtns
		List<HAPStoryContainerChildrenElementsWrapper> pagesChildren = moduleElement.getChildCollection(HAPStoryElementEntityModule.CHILD_PAGE);
		for(HAPStoryContainerChildrenElementsWrapper pageChild : pagesChildren) {
			HAPStoryElementUIPage pageElement = (HAPStoryElementUIPage)story.getElement(pageChild.getChildElement().getElementId());
			HAPIdBrick pageBrickId = new HAPIdBrick( HAPEnumBrickType.UIPAGE_100, null, pageElement.getEntityInfo().getName());
			out.addLocalBrickContent(pageBrickId, new HAPManualInfoContent(convertPage(pageElement, story) , HAPSerializationFormat.HTML));
		}
		
		return out;
	}
	
	private static String convertPage(HAPStoryElementUIPage pageElement, HAPStoryStory story) {
		InputStream pageTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "page.temp");
		Map<String, String> pageTemplateParms = new LinkedHashMap<String, String>();

		HAPStoryElementUIWrapperContent pageContentWrapperElement = (HAPStoryElementUIWrapperContent)story.getElement(pageElement.getChildElement(HAPStoryElementUIPage.CHILD_CONTENTWRAPPER).getElementId());
		pageTemplateParms.put("html", convertUIContentWrapper(pageContentWrapperElement, story));
		
		return HAPStringTemplateUtil.getStringValue(pageTemplateStream, pageTemplateParms);
	}
	
	private static String convertUIContentWrapper(HAPStoryElementUIWrapperContent contentWrapperElement, HAPStoryStory story) {
		InputStream templateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "contentwrapper.temp");
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		HAPStoryChildElement contentChild = contentWrapperElement.getChildElement(HAPStoryElementUIWrapperContent.CHILD_CONTENT);
		if(contentChild!=null) {
			templateParms.put("html", convertUIContent(contentChild.getElementId(), story));
		}
		
		InputStream valueContextTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "valuecontextinui.temp");
		Map<String, String> valueContexTemplateParms = new LinkedHashMap<String, String>();

		String valueContextJsonStr = convertWithVariable(contentWrapperElement, story, false);
		if(valueContextJsonStr!=null) {
			valueContexTemplateParms.put("valueContext", valueContextJsonStr);
			templateParms.put("valueContext", HAPStringTemplateUtil.getStringValue(valueContextTemplateStream, valueContexTemplateParms));
		}
		
		return HAPStringTemplateUtil.getStringValue(templateStream, templateParms);
	}
	
	private static String convertUIContent(HAPStoryIdElement elementId, HAPStoryStory story) {
		String out = null;
		HAPStoryElement storyElement = story.getElement(elementId);
		String elementType = storyElement.getElementType().getElementType();
		if(elementType.equals(HAPConstantShared.STORYNODE_TYPE_UICONTENT_HTML)) {
			out = convertHTMLContent((HAPStoryElementUIContentHtml)storyElement, story);
		}
		else if(elementType.equals(HAPConstantShared.STORYNODE_TYPE_UICONTENT_CUSTOMTAG)) {
			out = convertCustomTagContent((HAPStoryElementUIContentTagCustom)storyElement, story);
		}
		return out;
	}

	private static String convertCustomTagContent(HAPStoryElementUIContentTagCustom uiTagElement, HAPStoryStory story) {
		InputStream templateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "customtag.temp");
		Map<String, String> templateParms = new LinkedHashMap<String, String>();

		templateParms.put("tagname", uiTagElement.getTagId());
		
		StringBuffer attContent = new StringBuffer();
		Map<String, String> attributes = uiTagElement.getAttributes();
		for(String name : attributes.keySet()) {
			attContent.append(name + "=\"" + attributes.get(name) + "\" ");
		}
		templateParms.put("attributes", attContent.toString());
		
		HAPStoryElementUIWrapperContent contentWrapperElement = (HAPStoryElementUIWrapperContent)story.getElement(uiTagElement.getChildElement(HAPStoryElementUIContentTagCustom.CHILD_CONTENTWRAPPER).getElementId());
		templateParms.put("html", convertUIContentWrapper(contentWrapperElement, story));
		
		return HAPStringTemplateUtil.getStringValue(templateStream, templateParms);
	}

	
	private static String convertHTMLContent(HAPStoryElementUIContentHtml htmlElement, HAPStoryStory story) {
		String html = htmlElement.getHtml();
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		
		Map<String, StringBuffer> appendContents = new LinkedHashMap<String, StringBuffer>();
		for(HAPStoryContainerChildrenElementsWrapper childWrapper : htmlElement.getChildCollection(HAPStoryElementUIContentHtml.CHILD_CHILDREN)) {
			HAPStoryMetaDataChildElementUI childMetaData = (HAPStoryMetaDataChildElementUI)childWrapper.getChildElement().getMetaData();
			String metaDataType = childMetaData.getType();
			String slotName = childMetaData.getSlotName();
			String childContent = convertUIContent(childWrapper.getChildElement().getElementId(), story);
			if(metaDataType.equals(HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIINJECT)) {
				templateParms.put(slotName, childContent);
			}
			else if(metaDataType.equals(HAPConstantShared.STORY_CHILD_METADATA_TYPE_UIAPPEND)) {
				StringBuffer appendContent = appendContents.get(slotName);
				if(appendContent==null) {
					appendContent = new StringBuffer();
					appendContents.put(slotName, appendContent);
				}
				appendContent.append(childContent);
			}
		}
		
		for(String slotName : appendContents.keySet()) {
			templateParms.put(slotName, appendContents.get(slotName).toString());
		}
		
		return HAPStringTemplateUtil.getStringValue(html, templateParms);
	}
	
	
	private static String convertWithVariable(HAPStoryElementWithVariable withVariableElement, HAPStoryStory story, boolean returnSthWithEmpty) {
		HAPValueContextDefinitionImp valueContext = new HAPValueContextDefinitionImp();
		
		HAPValueStructureImp valueStructure = new HAPValueStructureImp();
		
		List<HAPStoryContainerChildrenElementsWrapper> variablesChildren =((HAPStoryElement)withVariableElement).getChildCollection(HAPStoryElementWithVariable.CHILD_VARIABLE);
		
		if(!returnSthWithEmpty&&variablesChildren.size()==0) {
			return null;
		}
		
		for(HAPStoryContainerChildrenElementsWrapper variableChild : variablesChildren) {
			HAPStoryElementAccessoryVariable variableElement = (HAPStoryElementAccessoryVariable)story.getElement(variableChild.getChildElement().getElementId());
			String variableName = variableElement.getEntityInfo().getName();
			
			HAPStoryElementEndPointIOVariable variableEndpoint = (HAPStoryElementEndPointIOVariable)story.getElement(variableElement.getChildElement(HAPStoryElementWithEndPoint.CHILD_ENDPOINT).getElementId());
			
			HAPRootInStructure rootInStructure = new HAPRootInStructure(new HAPElementStructureLeafData(HAPUtilityDataDefinition.toWritableDataDefinition(variableEndpoint.getDataDefinition())), variableElement.getEntityInfo());
			valueStructure.addRoot(rootInStructure);
		}
		
		valueContext.addValueStructure(new HAPWrapperValueStructureDefinitionImp(valueStructure));
		
		return valueContext.toStringValue(HAPSerializationFormat.JSON);
	}
	
}

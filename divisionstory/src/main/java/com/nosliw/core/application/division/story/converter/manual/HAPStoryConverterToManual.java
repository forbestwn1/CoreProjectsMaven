package com.nosliw.core.application.division.story.converter.manual;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
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
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsWrapper;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithVariable;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIPage;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIWrapperContent;

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
		String valueContextJsonStr = convertWithVariable(pageContentWrapperElement, story);
		pageTemplateParms.put("valueContext", valueContextJsonStr);
		
		pageContentWrapperElement.getChildCollection(HAPStoryElementWithVariable.CHILD_VARIABLE);
		
		return HAPStringTemplateUtil.getStringValue(pageTemplateStream, pageTemplateParms);
		
	}
	
	
	private static String convertWithVariable(HAPStoryElementWithVariable withVariableElement, HAPStoryStory story) {
		HAPValueContextDefinitionImp valueContext = new HAPValueContextDefinitionImp();
		
		HAPValueStructureImp valueStructure = new HAPValueStructureImp();
		
		List<HAPStoryContainerChildrenElementsWrapper> variablesChildren =((HAPStoryElement)withVariableElement).getChildCollection(HAPStoryElementWithVariable.CHILD_VARIABLE);
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

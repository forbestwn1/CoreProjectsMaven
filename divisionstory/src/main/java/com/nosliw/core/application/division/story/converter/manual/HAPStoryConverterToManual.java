package com.nosliw.core.application.division.story.converter.manual;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.interpolate.HAPStringTemplate;
import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickInBundle;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.application.common.interactive.HAPUtilityInteractiveTaskValuePort;
import com.nosliw.core.application.common.structure.HAPElementStructureLeafData;
import com.nosliw.core.application.common.structure.HAPRootInStructure;
import com.nosliw.core.application.common.structure.HAPValueContextDefinitionImp;
import com.nosliw.core.application.common.structure.HAPValueStructureImp;
import com.nosliw.core.application.common.structure.HAPWrapperValueStructureDefinitionImp;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.division.manual.core.process.HAPManualContentProviderText;
import com.nosliw.core.application.division.story.definition.HAPStoryAlias;
import com.nosliw.core.application.division.story.definition.HAPStoryChildElement;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsCollection;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsWrapper;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithVariable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryCommand;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOConstant;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEndPointIOVariable;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityDataSource;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentHtml;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentTagCustom;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIPage;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIWrapperContent;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryMetaDataChildElementUI;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociation;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociationComplex;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableCommand;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableSequence;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableUIPagePresent;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryTunnel;
import com.nosliw.core.application.valueport.HAPIdValuePort;
import com.nosliw.core.application.valueport.HAPIdValuePortInBundle;

public class HAPStoryConverterToManual {

	public static HAPManualContentProviderText convert(HAPStoryStory story) {
		HAPManualContentProviderText out = new HAPManualContentProviderText();
		
		//get module element (root)
		HAPStoryElementEntityModule moduleElement = (HAPStoryElementEntityModule)story.getElement(new HAPStoryAlias(HAPStoryStory.ALIAS_ROOT));
		
		InputStream moduleTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "module.temp");
		Map<String, String> moduleTemplateParms = new LinkedHashMap<String, String>();
		
		//get page elements
		List<String> pageElementList = new ArrayList<String>();
		List<HAPStoryContainerChildrenElementsWrapper> pagesChildren = moduleElement.getChildCollection(HAPStoryElementEntityModule.CHILD_PAGE);
		for(HAPStoryContainerChildrenElementsWrapper pageChild : pagesChildren) {
			HAPStoryElementUIPage pageElement = (HAPStoryElementUIPage)story.getElement(pageChild.getChildElement().getElementId());
			HAPIdBrick pageBrickId = new HAPIdBrick( HAPEnumBrickType.UIPAGE_100, HAPConstantShared.BRICK_DIVISION_MANUAL, pageElement.getEntityInfo().getName());
			out.addLocalBrickContent(pageBrickId, new HAPManualInfoContent(convertPage(pageElement, story) , HAPSerializationFormat.HTML));
			
			pageElementList.add(HAPStoryUtilityConverter.convertToBrickWrapper(pageElement.getElementId(), pageElement.getEntityInfo(), pageBrickId));
		}
		moduleTemplateParms.put("pages", pageElementList.toString());
		
		//get tasks
		List<String> tasksList = new ArrayList<String>();
		Set<HAPStoryRunnable> runnables = story.getRunnables();
		for(HAPStoryRunnable runnable : runnables) {
			String runnableType = runnable.getRunnableType();
			if(runnableType.equals(HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND)) {
				HAPStoryRunnableCommand commandRunnable = (HAPStoryRunnableCommand)runnable;
		
				HAPStoryDataAssociationComplex requestDataAssociation = commandRunnable.getRequestDataAssociation();
				String requestDAContent = converDataAssociation(requestDataAssociation, story);
				
				HAPStoryDataAssociationComplex responseDataAssociation = commandRunnable.getResponseDataAssociations().get(HAPConstantShared.TASK_RESULT_SUCCESS);
				String responseDAContent = null;
				if(responseDataAssociation!=null) {
					converDataAssociation(responseDataAssociation, story);
				}
				
				HAPStoryElement commandWrapperEle = HAPStoryUtilityStory.getDescendantElement(commandRunnable.getPathToCommand(), story);
				if(commandWrapperEle.getElementType().getElementType().equals(HAPConstantShared.STORYNODE_TYPE_SERVICE)) {
					HAPStoryElementEntityDataSource dataSourceElement = (HAPStoryElementEntityDataSource)commandWrapperEle;
					tasksList.add(new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "task_datasource.temp"))
							.setParm("taskId", runnable.getId())
			    			.setParm("requestDataAssociation", requestDAContent)
			    			.setParm("responseDataAssociation", responseDAContent)
			    			.setParm("dataSourceId", dataSourceElement.getServiceId())
				    		.getContent());
				}
			}
			else if(runnableType.equals(HAPConstantShared.STORYNODE_TYPE_TASK_PRESENTPAGE)) {
				HAPStoryRunnableUIPagePresent presentPageRunnable = (HAPStoryRunnableUIPagePresent)runnable;
				
				InputStream presentPageTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "task_presentpage.temp");
				Map<String, String> presentPageTemplateParms = new LinkedHashMap<String, String>();
				
				presentPageTemplateParms.put("pageId", presentPageRunnable.getPage());
				presentPageTemplateParms.put("taskName", presentPageRunnable.getId());
				presentPageTemplateParms.put("alias", presentPageRunnable.getId());
				
				tasksList.add(HAPStringTemplateUtil.getStringValue(presentPageTemplateStream, presentPageTemplateParms));
			}
			else if(runnableType.equals(HAPConstantShared.STORYNODE_TYPE_TASK_SEQUENCE)) {
				HAPStoryRunnableSequence sequenceRunnable = (HAPStoryRunnableSequence)runnable;
				
				InputStream sequenceTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "task_sequence.temp");
				Map<String, String> sequenceTemplateParms = new LinkedHashMap<String, String>();
				
				List<String> subRunnables = new ArrayList<String>();
				for(String subRunnable : sequenceRunnable.getRunnables()) {
					InputStream subRunnableTemplateStream = HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "task_sequence_item.temp");
					Map<String, String> subRunnableTemplateParms = new LinkedHashMap<String, String>();
					subRunnableTemplateParms.put("taskAlias", subRunnable);
					subRunnables.add(HAPStringTemplateUtil.getStringValue(subRunnableTemplateStream, subRunnableTemplateParms));
				}
				
				sequenceTemplateParms.put("subTasks", subRunnables.toString());
				tasksList.add(HAPStringTemplateUtil.getStringValue(sequenceTemplateStream, sequenceTemplateParms));
			}
		}
		moduleTemplateParms.put("tasks", tasksList.toString());
		
		out.setMainContent(new HAPManualInfoContent(HAPStringTemplateUtil.getStringValue(moduleTemplateStream, moduleTemplateParms), HAPEnumBrickType.MODULE_100));
		
		return out;
	}
	
	private static String converDataAssociation(HAPStoryDataAssociationComplex dataAssociationComplex, HAPStoryStory story) {
		List<String> tunnelsContent = new ArrayList<String>();

		for(HAPStoryDataAssociation dataAssociation : dataAssociationComplex.getDataAssociations()) {
			Pair<HAPStoryPath, HAPIdValuePortInBundle> pSource = buildTunnelBase(dataAssociation.getSourceBasePath(), dataAssociation.getSourceSubPath(), story);
			Pair<HAPStoryPath, HAPIdValuePortInBundle> pTarget = buildTunnelBase(dataAssociation.getTargetBasePath(), dataAssociation.getTargetSubPath(), story);
			
			for(HAPStoryTunnel tunnel : dataAssociation.getTunnels()) {
				tunnelsContent.add(convertTunnel(tunnel, pSource.getLeft(), pSource.getRight(), pTarget.getLeft(), pTarget.getRight(), story));
			}
		}
		
		return new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "dataassociation.temp")).
    			setParm("tunnels", tunnelsContent.toString()).
	    		getContent();			
	}

	private static Pair<HAPStoryPath, HAPIdValuePortInBundle> buildTunnelBase(HAPStoryPath basePath, HAPPath subPath, HAPStoryStory story) {
		HAPIdValuePortInBundle valuePortIdInBundle = null;
		
		HAPIdValuePort valuePortId = null;
		HAPStoryElement entityElement =  HAPStoryUtilityStory.getDescendantElement(basePath, story);
		if(entityElement.getElementType().equals(HAPStoryElementEntityDataSource.TYPEID)) {
			String[] subPathSegs = subPath.getPathSegments();
			String commandPath = subPathSegs[0];
			String requestOrResponse = subPathSegs[1];
			if(requestOrResponse.equals(HAPStoryElementAccessoryCommand.CHILD_REQUEST)) {
				valuePortId = new HAPIdValuePort(HAPConstantShared.VALUEPORTGROUP_TYPE_INTERACTIVETASK, HAPConstantShared.VALUEPORT_NAME_INTERACT_REQUEST);
			}
			else if(requestOrResponse.equals(HAPStoryElementAccessoryCommand.CHILD_RESPONSE)) {
				String responseResult = subPathSegs[2];
				valuePortId = new HAPIdValuePort(HAPConstantShared.VALUEPORTGROUP_TYPE_INTERACTIVETASK, HAPUtilityInteractiveTaskValuePort.buildResultValuePortName(responseResult));
			}
		}
		else {
			valuePortId = new HAPIdValuePort(HAPConstantShared.VALUEPORTGROUP_TYPE_VALUECONTEXT, HAPConstantShared.VALUEPORT_TYPE_VALUECONTEXT);
		}
		
		HAPIdBrickInBundle brickIdInBundle = new HAPIdBrickInBundle();
		brickIdInBundle.setAlias(entityElement.getElementId().getKey());
		
		valuePortIdInBundle = new HAPIdValuePortInBundle(brickIdInBundle, HAPConstantShared.VALUEPORTGROUP_SIDE_EXTERNAL, valuePortId);
		
		return Pair.of(new HAPStoryPath(basePath.getBaseStoryElementId(), basePath.getPath().appendPath(subPath)), valuePortIdInBundle);
	}
	
	private static String convertTunnel(HAPStoryTunnel tunnel, HAPStoryPath sourceBasePath, HAPIdValuePortInBundle sourceValuePort, HAPStoryPath targetBasePath, HAPIdValuePortInBundle targetValuePort, HAPStoryStory story) {
		HAPStoryContainerChildrenElementsCollection endPointWrapperCollectionSource = HAPStoryUtilityStory.getDescendantCollection(sourceBasePath, story);
		HAPStoryContainerChildrenElementsCollection endPointWrapperCollectionTarget = HAPStoryUtilityStory.getDescendantCollection(targetBasePath, story);
		
		HAPStoryIdElement endPointWrapperEleIdSource = ((HAPStoryContainerChildrenElementsWrapper)endPointWrapperCollectionSource.getChildContainer(tunnel.getSource())).getChildElement().getElementId();
		HAPStoryIdElement endPointWrapperEleIdTarget = ((HAPStoryContainerChildrenElementsWrapper)endPointWrapperCollectionTarget.getChildContainer(tunnel.getTarget())).getChildElement().getElementId();
		
		String source = null;
		
		HAPStoryElement sourceEndpointEle = HAPStoryUtilityStory.getDescendantElement(new HAPStoryPath(endPointWrapperEleIdSource, new HAPPath(HAPStoryElementWithEndPoint.CHILD_ENDPOINT)), story);
		if(sourceEndpointEle.getElementType().equals(HAPStoryElementEndPointIOConstant.TYPE)) {
			HAPStoryElementEndPointIOConstant constantEndPointElement = (HAPStoryElementEndPointIOConstant)sourceEndpointEle;
			source = new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "tunnel_source_constant.temp")).
	    			setParm("constantData", constantEndPointElement.getData().toStringValue(HAPSerializationFormat.JSON)).
		    		getContent();			
		}
		else if(sourceEndpointEle.getElementType().equals(HAPStoryElementEndPointIOVariable.TYPE)) {
			HAPStoryElementEndPointIOVariable variableEndPointElement = (HAPStoryElementEndPointIOVariable)sourceEndpointEle;
			source = new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "tunnel_source_variable.temp"))
					.setParm("variableName", tunnel.getSource())
					.setParm("valuePortIdInBundle", sourceValuePort.toStringValue(HAPSerializationFormat.JSON))
					.getContent();			
		}
		
		HAPStoryElement targetEndpointEle = HAPStoryUtilityStory.getDescendantElement(new HAPStoryPath(endPointWrapperEleIdTarget, new HAPPath(HAPStoryElementWithEndPoint.CHILD_ENDPOINT)), story);
		HAPStoryElementEndPointIOVariable targetEndPointElement = (HAPStoryElementEndPointIOVariable)targetEndpointEle;
		String target = new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "tunnel_target.temp"))
				.setParm("variableName", tunnel.getTarget())
				.setParm("valuePortIdInBundle", targetValuePort.toStringValue(HAPSerializationFormat.JSON))
				.getContent();			

		return new HAPStringTemplate(HAPUtilityFile.getInputStreamOnClassPath(HAPStoryConverterToManual.class, "tunnel.temp")).
				setParm("source", source).
				setParm("target", target).
				getContent();
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

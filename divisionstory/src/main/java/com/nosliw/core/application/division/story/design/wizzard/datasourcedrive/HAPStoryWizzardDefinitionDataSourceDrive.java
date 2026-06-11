package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryAlias;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithDataSource;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryCommand;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityDataSource;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentHtml;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentTagCustom;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIPage;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIUtility;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIWrapperContent;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryMetaDataChildElementUIAppend;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryMetaDataChildElementUIInject;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryTunnel;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociation;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociationComplex;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableCommand;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableSequence;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryRunnableUIPagePresent;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.application.division.story.design.HAPStoryDesignStep;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnectionContainer;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemElementNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemRunnableNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeUtility;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryDesignMetadataStepWizard;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionair;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairGroup;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemDynamic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemStatic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardRequestDataNext;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardStepDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardUtilityQuestion;
import com.nosliw.core.application.entity.datasource.HAPManagerService;
import com.nosliw.core.application.entity.datasource.HAPServiceProfile;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.application.entity.uitag.HAPUITagInfo;
import com.nosliw.core.application.entity.uitag.HAPUITageQueryData;
import com.nosliw.core.data.HAPDataType;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPDataTypeManager;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardDefinitionDataSourceDrive extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	private final static HAPStoryAlias ALIAS_ELEMENT_MODULE = new HAPStoryAlias(HAPStoryStory.ALIAS_ROOT, false);
	private final static HAPStoryAlias ALIAS_ELEMENT_DATASOURCE = new HAPStoryAlias("dataSource", false);
//	private final static HAPStoryAliasElement ALIAS_ELEMENT_UIPAGE = new HAPStoryAliasElement("uiPage", false);
	
	private List<HAPStoryWizzardStepDefinition> m_stepDefinitions;
	
	private HAPServiceParseEntity m_entityParseService;
	
	private HAPManagerService m_serviceMan;
	
	private HAPDataTypeHelper m_dataTypeHelper;
	
	private HAPDataTypeManager m_dataTypeMan;
	
	private HAPManagerUITag m_uiTagMan;
	
	public HAPStoryWizzardDefinitionDataSourceDrive(HAPServiceParseEntity entityParseService, HAPManagerService serviceMan, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan, HAPManagerUITag uiTagMan) {
		this.m_entityParseService = entityParseService;
		this.m_serviceMan = serviceMan;
		this.m_dataTypeHelper = dataTypeHelper;
		this.m_dataTypeMan = dataTypeMan;
		this.m_uiTagMan = uiTagMan;
		
		this.m_stepDefinitions = new ArrayList<HAPStoryWizzardStepDefinition>();
		
		HAPStoryWizzardStepDefinition step1 = new HAPStoryWizzardStepDefinition();
		step1.setId(STEP_SELECTDATASOURCE);
		
		HAPStoryWizzardStepDefinition step2 = new HAPStoryWizzardStepDefinition();
		step2.setId(STEP_CUSTOMIZEUI);

		this.m_stepDefinitions.add(step1);
		this.m_stepDefinitions.add(step2);
	}
	
	@Override
	public void initDesign(HAPStoryDesign design) {
		//init step
		design.newInitStep();
		HAPStoryDesignSessionChange changeSession = design.newChangeReqestSession();
		changeSession.addChangeItemNew(new HAPStoryElementEntityModule(), ALIAS_ELEMENT_MODULE);
		changeSession.commit();

		//service step
		HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_SELECTDATASOURCE));
		stepMetaData.setQuestionair(new HAPStoryWizzardQuestionairItemDynamic());
		design.newStep(stepMetaData);
	}
	
	private void setAnsweredQuestionairToStep(HAPStoryDesignStep step, HAPStoryWizzardQuestionair answeredQuestionair) {
		HAPStoryDesignMetadataStepWizard stepMetaData = (HAPStoryDesignMetadataStepWizard)step.getMetaData();
		stepMetaData.setQuestionair(answeredQuestionair);
	}
	
	//error: attach error to answer
	//next : next step name, question
	@Override
	public void processNext(HAPStoryDesign design, HAPStoryWizzardRequestDataNext request) {
		HAPStoryDesignMetadataStepWizard stepData = request.getStepData();
		String stepName = stepData.getStepDefinition().getName();
		
		if(STEP_SELECTDATASOURCE.equals(stepName)) {
			//validation lifecycle first

			//validation answer
			HAPStoryWizzardQuestionairItemDynamic questionair = (HAPStoryWizzardQuestionairItemDynamic)stepData.getQuestionair();
			HAPStoryWizzardQuestionValueDataSourceChooseDynamic choosServiceQuestion = (HAPStoryWizzardQuestionValueDataSourceChooseDynamic)questionair.getChangedValue();

			//set answer
			this.setAnsweredQuestionairToStep(design.getCurrentStep(), questionair);
			
			String dataSourceId = choosServiceQuestion.getDataSourceName();
			HAPServiceProfile dataSrouceProfile = this.m_serviceMan.getServiceProfile(dataSourceId, null);
			HAPInteractiveTask dataSourceInterface = dataSrouceProfile.getInterface();

			//apply answer for data source selection
			HAPStoryDesignSessionChange changeSession = design.newChangeReqestSession();
			this.applyDataSourceSelection(changeSession, dataSourceId, dataSrouceProfile);
			changeSession.commit();
			
	        //prepare next step + questionair
			HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_CUSTOMIZEUI));
			stepMetaData.setQuestionair(this.prepareChooseUIQuestionair(dataSrouceProfile));
			design.newStep(stepMetaData);
		}
		else if(STEP_CUSTOMIZEUI.equals(stepName)) {
			HAPStoryWizzardQuestionairGroup questionair = (HAPStoryWizzardQuestionairGroup)stepData.getQuestionair();

			HAPStoryDesignSessionChange changeSession = design.newChangeReqestSession();
			
			//add page to module
			Pair<HAPStoryChangeItemElementNew, HAPStoryChangeItemElementNew> pagePair = HAPStoryElementUIUtility.newUIPage(changeSession, ALIAS_ELEMENT_MODULE, null);
			HAPStoryChangeItemElementNew newPageContentWrapperChange = pagePair.getRight();
			HAPStoryChangeItemElementNew newPageChange = pagePair.getLeft();
			HAPStoryIdElement pageElementId = newPageChange.getElementId();
			
			//add root content to content wrapper
			HAPStoryChangeItemElementNew newRootContentChange = HAPStoryWizzardUtility.newUIContentHtmlFromFile(changeSession, "main.html");
			changeSession.addChangeConnectionNew(newPageContentWrapperChange.getElementId(), newRootContentChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementUIWrapperContent.CHILD_CONTENT)));

			//add request content and inject it to "request" slot
			HAPStoryChangeItemElementNew newRequestContentChange = HAPStoryWizzardUtility.newUIContentHtmlFromFile(changeSession, "request.html");
			changeSession.addChangeConnectionNew(newRootContentChange.getElementId(), newRequestContentChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIInject("request")));
			
			//add response content and inject it to "response" slot
			HAPStoryChangeItemElementNew newResponseContentChange = HAPStoryWizzardUtility.newUIContentHtmlFromFile(changeSession, "response.html");
			changeSession.addChangeConnectionNew(newRootContentChange.getElementId(), newResponseContentChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIInject("response")));

			HAPStoryIdElement dataSourceElementId = changeSession.getElement(ALIAS_ELEMENT_DATASOURCE).getElementId();
			
			//data association between page and data source request
			HAPStoryDataAssociation requestDataAssociationForVariable = new HAPStoryDataAssociation(
					new HAPStoryPath(pageElementId, null), HAPStoryElementUIPage.buildPathToVariableCollection(),
					new HAPStoryPath(dataSourceElementId, null), HAPStoryElementEntityDataSource.buildPathToCommandExecute().appendSegment(HAPStoryElementAccessoryCommand.CHILD_REQUEST),
					HAPConstantShared.DATAASSOCIATION_DIRECTION_DOWNSTREAM
					);

			HAPStoryDataAssociation requestDataAssociationForConstant = new HAPStoryDataAssociation(
					new HAPStoryPath(pageElementId, null), HAPStoryElementUIPage.buildPathToConstantCollection(),
					new HAPStoryPath(dataSourceElementId, null), HAPStoryElementEntityDataSource.buildPathToCommandExecute().appendSegment(HAPStoryElementAccessoryCommand.CHILD_REQUEST),
					HAPConstantShared.DATAASSOCIATION_DIRECTION_DOWNSTREAM
					);

			//request
			List<HAPStoryWizzardQuestionair> requestParmGroupQs = HAPStoryWizzardUtilityQuestion.findQuestionairsByTag(questionair, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMGROUP);
			for(HAPStoryWizzardQuestionair requestParmGroupQ : requestParmGroupQs) {
				HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = (HAPStoryWizzardQuestionairItemStatic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMINFO);
				HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic parmInfoValue = (HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic)parmInfoStaticQ.getValue();
				HAPDefinitionParmRequest parmDef = parmInfoValue.getParmDefinition();
				
				HAPStoryWizzardQuestionairItemDynamic parmIsConstantQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMISCONSTANT);
				HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic parmIsConstantQValue = (HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic)parmIsConstantQ.getValue();

				if(parmIsConstantQValue.getIsConstant()) {
					HAPStoryWizzardQuestionairItemDynamic parmConstantValueQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMCONSTANTVALUE);
					HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic constantValueInQ = (HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic)parmConstantValueQ.getValue();
					
					//add constant
					HAPStoryChangeItemElementNew newConstantChange = HAPStoryChangeUtility.buildNewAppendConstantChange(changeSession, newPageContentWrapperChange.getElementId(), constantValueInQ.getConstantData(), parmDef);

					//build tunnel between constant endpoint and command endpoint
					HAPStoryTunnel tunnel = new HAPStoryTunnel(parmDef.getName(), parmDef.getName());
					requestDataAssociationForConstant.addTunnel(tunnel);
				}
				else {
					HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMUITAG);
					HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic chooseUITagValue = (HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic)parmUITagChooseQ.getValue();
					HAPStoryWizzardUITagInfo uiTagInfo = chooseUITagValue.getUITagInfo();
					
					//add variable
					HAPStoryChangeItemElementNew newVariableChange = HAPStoryChangeUtility.buildNewAppendVariableChange(changeSession, newPageContentWrapperChange.getElementId(), parmDef.getDataDefinition(), parmDef);

					//build tunnel between variable endpoint and command endpoint
					HAPStoryTunnel tunnel = new HAPStoryTunnel(parmDef.getName(), parmDef.getName());
					requestDataAssociationForVariable.addTunnel(tunnel);
					
					//append input content
					HAPStoryChangeItemElementNew newRequestInputContentChange = HAPStoryWizzardUtility.newUIContentHtmlFromFile(changeSession, "input.html");
					changeSession.addChangeConnectionNew(newRequestContentChange.getElementId(), newRequestInputContentChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIAppend("input")));
					
					//inject label
					HAPStoryChangeItemElementNew newRequestInputLabelContentChange = HAPStoryWizzardUtility.newUIContentHtmlFromFile(changeSession, "inputlabel.html");
					changeSession.addChangeConnectionNew(newRequestInputContentChange.getElementId(), newRequestInputLabelContentChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIInject("label")));

					//inject title
					HAPStoryChangeItemElementNew newRequestInputLabelTitleChange = HAPStoryWizzardUtility.newUIContentHtml(changeSession, parmDef.getName());
					changeSession.addChangeConnectionNew(newRequestInputLabelContentChange.getElementId(), newRequestInputLabelTitleChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIInject("labeltitle")));
					
					//inject uiTag
					HAPStoryChangeItemElementNew uiTagChangeNew = changeSession.addChangeItemNew(new HAPStoryElementUIContentTagCustom(uiTagInfo.getTagName(), uiTagInfo.getAttributes()));
					changeSession.addChangeConnectionNew(newRequestInputContentChange.getElementId(), uiTagChangeNew.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementUIContentHtml.getAddChildChildPath(), new HAPStoryMetaDataChildElementUIInject("uitag")));
					
					//inject content wrapper into uitag
					HAPStoryChangeItemElementNew newUITagWrapperChange = changeSession.addChangeItemNew(new HAPStoryElementUIWrapperContent());
					changeSession.addChangeConnectionNew(uiTagChangeNew.getElementId(), newUITagWrapperChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementUIContentTagCustom.CHILD_CONTENTWRAPPER)));
					
					//inject error tag
					
				}

			}
//			HAPStoryChangeItemNew requestDataAssociationChangeNew = changeSession.addChangeItemNew(requestDataAssocationEle);
			
			//response
			List<HAPStoryWizzardQuestionair> responseParmGroupQs = HAPStoryWizzardUtilityQuestion.findQuestionairsByTag(questionair, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMGROUP);
			for(HAPStoryWizzardQuestionair responseParmGroupQ : responseParmGroupQs) {
				HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = (HAPStoryWizzardQuestionairItemStatic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(responseParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMINFO);
			
				HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(responseParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMUITAG);
				
			}

			//present page task
			HAPStoryRunnableUIPagePresent presentPageRunnable = new HAPStoryRunnableUIPagePresent();
			presentPageRunnable.setPage(pageElementId.getId());
			HAPStoryChangeItemRunnableNew presentPageRunableChangeNew = changeSession.addChangeItemNew(presentPageRunnable);
			
			//module init task
			HAPStoryRunnableSequence moculeInitRunnable = new HAPStoryRunnableSequence();
			moculeInitRunnable.addRunnable(presentPageRunableChangeNew.getRunnable().getId());
			HAPStoryChangeItemRunnableNew moduleInitRunableChangeNew = changeSession.addChangeItemNew(moculeInitRunnable);

			
			
			//build data source execute task
			HAPStoryRunnableCommand commandRunnable = new HAPStoryRunnableCommand();
			commandRunnable.setPathToCommand(new HAPStoryPath(dataSourceElementId, HAPStoryElementEntityDataSource.buildPathToCommandExecute()));
			HAPStoryDataAssociationComplex daComplex = new HAPStoryDataAssociationComplex();
			daComplex.addDataAssociation(requestDataAssociationForConstant);
			daComplex.addDataAssociation(requestDataAssociationForVariable);
			commandRunnable.setRequestDataAssociation(daComplex);
			
//			HAPStoryChangeItemRunnableNew commandRunChangeNew = changeSession.addChangeItemNew(commandRunnable);

			
			changeSession.commit();
			
			//end step
//			HAPStoryDesignMetadataStepEnd stepMetaData = new HAPStoryDesignMetadataStepEnd();
//			design.newStep(stepMetaData);
			
		}
		
	}
	
	private void applyDataSourceSelection(HAPStoryDesignSessionChange changeSession, String dataSourceId, HAPServiceProfile dataSrouceProfile) {
		//data source item
		//put data source under module
		HAPStoryChangeItemElementNew dataSourceChangeNew = changeSession.addChangeItemNew(new HAPStoryElementEntityDataSource(dataSourceId, dataSrouceProfile), ALIAS_ELEMENT_DATASOURCE);
		changeSession.addChangeConnectionNew(ALIAS_ELEMENT_MODULE, dataSourceChangeNew.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementWithDataSource.getAddDataSourceChildPath()));

		//command in data source
		HAPStoryIdElement commandInDataSourceEleId = HAPStoryChangeUtility.buildNewCommandChange(changeSession, dataSrouceProfile.getInterface(), null).getElementId();
		changeSession.addChangeConnectionNew(dataSourceChangeNew.getElementId(), commandInDataSourceEleId, new HAPStoryChangeInfoConnectionContainer(HAPStoryElementEntityDataSource.buildPathToCommandExecute()));
	}
	
	private HAPStoryWizzardQuestionair prepareChooseUIQuestionair(HAPServiceProfile dataSrouceProfile) {
		HAPInteractiveTask dataSourceInterface = dataSrouceProfile.getInterface();

		//root group
		HAPStoryWizzardQuestionairGroup out = new HAPStoryWizzardQuestionairGroup();

		//data source profile info
		HAPStoryWizzardQuestionairItemStatic dataSourceInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceInfoStatic(dataSrouceProfile));
		out.addItem(dataSourceInfoStaticQ);
		
		//group for requests
		HAPStoryWizzardQuestionairGroup serviceRequestGroupQ = new HAPStoryWizzardQuestionairGroup();
		for(HAPDefinitionParmRequest parmDef : dataSourceInterface.getRequestParms()) {
			HAPDataTypeCriteria dataTypeCriteria = parmDef.getDataDefinition().getCriteria();
			
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup(HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMGROUP);
			
			//parm static infor
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic(parmDef), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMINFO);
			parmGroupQ.addItem(parmInfoStaticQ);
			
			//group for parm dynamic info
			HAPStoryWizzardQuestionairGroup parmDynamicGroupQ = new HAPStoryWizzardQuestionairGroup();
			parmGroupQ.addItem(parmDynamicGroupQ);
			
			//dynamic of is constant
			HAPStoryWizzardQuestionairItemDynamic parmIsConstantQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic(false), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMISCONSTANT);
			parmDynamicGroupQ.addItem(parmIsConstantQ);

			//dynamic of constant value
			HAPStoryWizzardQuestionairItemDynamic parmConstantValueQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic(null), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMCONSTANTVALUE);
			parmDynamicGroupQ.addItem(parmConstantValueQ);

			//dynamic of uitag
			
			HAPUITagInfo uiTagInfo = this.m_uiTagMan.getDefaultUITagData(new HAPUITageQueryData(dataTypeCriteria));
			HAPStoryWizzardUITagInfo wizzardUITagInfo = new HAPStoryWizzardUITagInfo(uiTagInfo.getName());
			
			HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic(wizzardUITagInfo), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMUITAG);
			parmDynamicGroupQ.addItem(parmUITagChooseQ);
			
			serviceRequestGroupQ.addItem(parmGroupQ);
			
		}
		out.addItem(serviceRequestGroupQ);
		
		//group for response
		HAPStoryWizzardQuestionairGroup serviceResponseGroupQ = new HAPStoryWizzardQuestionairGroup();
		
		for(HAPDefinitionParmResponse resultParm : dataSourceInterface.getResult(HAPConstantShared.SERVICE_RESULT_SUCCESS).getOutput()) {
			HAPDataTypeCriteria dataTypeCriteria = resultParm.getDataDefinition().getCriteria();
		
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup();
			
			//parm static info
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic(resultParm), HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMINFO);
			parmGroupQ.addItem(parmInfoStaticQ);
			
			HAPStoryWizzardQuestionairGroup parmDataCriteriaGroupQ = this.prepareSelectUIForDataTypeCriteria(dataTypeCriteria);
			parmGroupQ.addItem(parmDataCriteriaGroupQ);
			
			serviceResponseGroupQ.addItem(parmDataCriteriaGroupQ);
		}
		
		out.addItem(serviceResponseGroupQ);
		
		return out;
	}
	

	private HAPStoryWizzardQuestionairGroup prepareSelectUIForDataTypeCriteria(HAPDataTypeCriteria dataTypeCriteria) {
		//data type criter group
		HAPStoryWizzardQuestionairGroup out = new HAPStoryWizzardQuestionairGroup();
		
		//parm static info
		HAPStoryWizzardQuestionairItemStatic dataCriteriaInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceResponseDataCriteriaInfoStatic(dataTypeCriteria), HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEDATACRITERIAINFO);
		out.addItem(dataCriteriaInfoStaticQ);

		//dynamic of uitag
		HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic(null), HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMUITAG);
		out.addItem(parmUITagChooseQ);

		//for complex data type
		Set<HAPDataTypeId> dataTypeIds = dataTypeCriteria.getValidDataTypeId(this.m_dataTypeHelper);
		HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
		HAPDataType dataType = this.m_dataTypeMan.getDataType(dataTypeId);
		boolean isComplex = dataType.getIsComplex();
		if(isComplex) {
			
			HAPStoryWizzardQuestionairGroup childrenDataCriteriaGroupQ = new HAPStoryWizzardQuestionairGroup();
			out.addItem(childrenDataCriteriaGroupQ);
			
			if(dataTypeId.getFullName().contains("array")){
				//child group
				HAPStoryWizzardQuestionairGroup childDataCriteriaGroupQ = new HAPStoryWizzardQuestionairGroup();

				//child info (name)
				HAPEntityInfo entityInfo = new HAPEntityInfoImp();
				entityInfo.setName(HAPConstantShared.NAME_DEFAULT);
				HAPStoryWizzardQuestionairItemStatic childEntityInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(entityInfo));
				childDataCriteriaGroupQ.addItem(childEntityInfoStaticQ);
				
				//child data criteria
				childDataCriteriaGroupQ.addItem(prepareSelectUIForDataTypeCriteria(HAPUtilityCriteria.getElementCriteria(dataTypeCriteria)));
				
				childrenDataCriteriaGroupQ.addItem(childDataCriteriaGroupQ);
			}
			else if(dataTypeId.getFullName().contains("map")){
				//map
				List<String> names = HAPUtilityCriteria.getCriteriaChildrenNames(dataTypeCriteria);
				for(String name : names) {
					//child group
					HAPStoryWizzardQuestionairGroup childDataCriteriaGroupQ = new HAPStoryWizzardQuestionairGroup();
					
					//child info (name)
					HAPEntityInfo entityInfo = new HAPEntityInfoImp();
					entityInfo.setName(name);
					HAPStoryWizzardQuestionairItemStatic childEntityInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceEntityInfoStatic(entityInfo));
					childDataCriteriaGroupQ.addItem(childEntityInfoStaticQ);

					//child data criteria
					childDataCriteriaGroupQ.addItem(prepareSelectUIForDataTypeCriteria(HAPUtilityCriteria.getChildCriteria(dataTypeCriteria, name)));
					
					childrenDataCriteriaGroupQ.addItem(childDataCriteriaGroupQ);
				}
			}
		}

        return out;		
	}
	
	
	@Override
	public List<HAPStoryWizzardStepDefinition> getStepsDefinition() {
		return this.m_stepDefinitions;
	}
	
	private HAPStoryWizzardStepDefinition getStepDefinition(String stepName) {
		for(HAPStoryWizzardStepDefinition stepDef : this.m_stepDefinitions) {
			if(stepDef.getName().equals(stepName)) {
				return stepDef;
			}
		}
		return null;
	}

}

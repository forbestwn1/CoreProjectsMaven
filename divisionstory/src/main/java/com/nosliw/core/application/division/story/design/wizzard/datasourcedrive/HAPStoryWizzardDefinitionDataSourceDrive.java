package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParm;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionResult;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementDataAssociation;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryTunnel;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityDataSource;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityUIPage;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementRunnableCommand;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnectionContainer;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;
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
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionair;
import com.nosliw.core.application.entity.service.HAPManagerService;
import com.nosliw.core.application.entity.service.HAPServiceProfile;
import com.nosliw.core.data.HAPDataType;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPDataTypeManager;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardDefinitionDataSourceDrive extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	private final static HAPStoryAliasElement ALIAS_ELEMENT_MODULE = new HAPStoryAliasElement("module", false);
	private final static HAPStoryAliasElement ALIAS_ELEMENT_DATASOURCE = new HAPStoryAliasElement("dataSource", false);
	private final static HAPStoryAliasElement ALIAS_ELEMENT_UIPAGE = new HAPStoryAliasElement("uiPage", false);
	
	private List<HAPStoryWizzardStepDefinition> m_stepDefinitions;
	
	private HAPServiceParseEntity m_entityParseService;
	
	private HAPManagerService m_serviceMan;
	
	private HAPDataTypeHelper m_dataTypeHelper;
	
	private HAPDataTypeManager m_dataTypeMan;
	
	public HAPStoryWizzardDefinitionDataSourceDrive(HAPServiceParseEntity entityParseService, HAPManagerService serviceMan, HAPDataTypeHelper dataTypeHelper, HAPDataTypeManager dataTypeMan) {
		this.m_entityParseService = entityParseService;
		this.m_serviceMan = serviceMan;
		this.m_dataTypeHelper = dataTypeHelper;
		this.m_dataTypeMan = dataTypeMan;
		
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
		stepMetaData.addQuestionair(new HAPStoryWizzardQuestionairItemDynamic());
		design.newStep(stepMetaData);
		
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
			Object changeValue = questionair.getChangedValue();
			HAPStoryWizzardQuestionValueDataSourceChooseDynamic choosServiceQuestion = (HAPStoryWizzardQuestionValueDataSourceChooseDynamic)this.parseQuestionValue((JSONObject)changeValue, HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID);
			
			String dataSourceId = choosServiceQuestion.getDataSourceName();
			HAPServiceProfile dataSrouceProfile = this.m_serviceMan.getServiceProfile(dataSourceId, null);
			HAPInteractiveTask dataSourceInterface = dataSrouceProfile.getInterface();

			//apply answer for data source selection
			this.applyDataSourceSelection(design, dataSourceId, dataSourceInterface);
			
	        //prepare next step + questionair
			HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_CUSTOMIZEUI));
			stepMetaData.addQuestionair(this.prepareChooseUIQuestionair(dataSrouceProfile));
			design.newStep(stepMetaData);
		}
		else if(STEP_CUSTOMIZEUI.equals(stepName)) {
			HAPStoryDesignSessionChange changeSession = design.newChangeReqestSession();
			
			//add page to module
			HAPStoryElementEntityUIPage uiPageItem = new HAPStoryElementEntityUIPage();
			HAPStoryChangeItemNew newPageChange = changeSession.addChangeItemNew(uiPageItem, ALIAS_ELEMENT_UIPAGE);
			changeSession.addChangeConnectionNew(ALIAS_ELEMENT_MODULE, newPageChange.getElementId(), new HAPStoryChangeInfoConnectionContainer());
			
			HAPStoryWizzardQuestionairGroup questionair = (HAPStoryWizzardQuestionairGroup)stepData.getQuestionair();

			//data association between page and data source request
			HAPStoryElementDataAssociation requestDataAssocationEle = new HAPStoryElementDataAssociation(newPageChange.getElementId(), null, ALIAS_ELEMENT_DATASOURCE, new HAPPath("command.request"), "normal");
			//request
			List<HAPStoryWizzardQuestionair> requestParmGroupQs = HAPStoryWizzardUtilityQuestion.findQuestionairsByTag(questionair, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMGROUP);
			for(HAPStoryWizzardQuestionair requestParmGroupQ : requestParmGroupQs) {
				HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = (HAPStoryWizzardQuestionairItemStatic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMINFO);
				HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic parmInfoValue = (HAPStoryWizzardQuestionValueDataSourceRequestParmInfoStatic)parmInfoStaticQ.getValue();
				HAPDefinitionParm parmDef = parmInfoValue.getParmDefinition();
				
				HAPStoryWizzardQuestionairItemDynamic parmIsConstantQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMISCONSTANT);
				HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic parmIsConstantQValue = (HAPStoryWizzardQuestionValueDataSourceRequestParmChooseIsConstantDynamic)parmIsConstantQ.getValue();

				String sourcePath;
				if(parmIsConstantQValue.getIsConstant()) {
					HAPStoryWizzardQuestionairItemDynamic parmConstantValueQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMCONSTANTVALUE);
					HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic constantValueInQ = (HAPStoryWizzardQuestionValueDataSourceRequestParmChooseConstantValueDynamic)parmConstantValueQ.getValue();
					
					//add constant
					HAPStoryChangeItemNew newConstantChange = HAPStoryChangeUtility.buildNewConstantChange(changeSession, newPageChange.getElementId(), constantValueInQ.getConstantData(), parmDef);
					sourcePath = "variable";
					
				}
				else {
					HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(requestParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMUITAG);
					
					//add variable
					HAPStoryChangeItemNew newVariableChange = HAPStoryChangeUtility.buildNewVariableChange(changeSession, newPageChange.getElementId(), parmDef.getDataDefinition(), parmDef);
					sourcePath = "constant";
					
					//add uitag
				}

				//build tunnel between variable and datasource request parm
				HAPStoryTunnel tunnel = new HAPStoryTunnel(new HAPPath(sourcePath+"."+parmDef.getName()+"endpoint"), new HAPPath(parmDef.getName()+"endpoint"));
				requestDataAssocationEle.addTunnel(tunnel);
			}
			HAPStoryChangeItemNew requestDataAssociationChangeNew = changeSession.addChangeItemNew(requestDataAssocationEle);
			
			
			//response
			List<HAPStoryWizzardQuestionair> responseParmGroupQs = HAPStoryWizzardUtilityQuestion.findQuestionairsByTag(questionair, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMGROUP);
			for(HAPStoryWizzardQuestionair responseParmGroupQ : responseParmGroupQs) {
				HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = (HAPStoryWizzardQuestionairItemStatic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(responseParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMINFO);
			
				HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = (HAPStoryWizzardQuestionairItemDynamic)HAPStoryWizzardUtilityQuestion.findSingleQuestionairByTag(responseParmGroupQ, HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMUITAG);
				
			}
			
			//build data source execute task
			HAPStoryChangeItemNew commandRunChangeNew = changeSession.addChangeItemNew(new HAPStoryElementRunnableCommand());
			//add command to command run
			changeSession.addChangeConnectionNew(commandRunChangeNew.getElementId(), changeSession.getElement(ALIAS_ELEMENT_DATASOURCE).getChild(new HAPPath("command")), new HAPStoryChangeInfoConnectionContainer());
			//add request data association to command run
			changeSession.addChangeConnectionNew(commandRunChangeNew.getElementId(), requestDataAssociationChangeNew.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath("request")));
			
			
			changeSession.commit();
		}
		
	}
	
	private void applyDataSourceSelection(HAPStoryDesign design, String dataSourceId, HAPInteractiveTask dataSourceInterface) {
		HAPStoryDesignSessionChange changeSession = design.newChangeReqestSession();
		
		//data source item
		//put data source under module
		changeSession.addChangeItemNew(new HAPStoryElementEntityDataSource(dataSourceId, dataSourceInterface), ALIAS_ELEMENT_DATASOURCE);
		changeSession.addChangeConnectionNew(ALIAS_ELEMENT_MODULE, ALIAS_ELEMENT_DATASOURCE, new HAPStoryChangeInfoConnectionContainer());

		//command in data source
		HAPStoryIdElement commandInDataSourceEleId = HAPStoryChangeUtility.buildNewCommandChange(changeSession, dataSourceInterface, null).getElementId();
		changeSession.addChangeConnectionNew(ALIAS_ELEMENT_DATASOURCE, commandInDataSourceEleId, new HAPStoryChangeInfoConnectionContainer());
		
		changeSession.commit();
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
		for(HAPDefinitionParm parmDef : dataSourceInterface.getRequestParms()) {
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
			HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceRequestParmChooseUIDynamic(null), HAPConstantShared.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMUITAG);
			parmDynamicGroupQ.addItem(parmUITagChooseQ);
			
		}
		out.addItem(serviceRequestGroupQ);
		
		//group for response
		HAPStoryWizzardQuestionairGroup serviceResponseGroupQ = new HAPStoryWizzardQuestionairGroup();
		
		for(HAPDefinitionResult resultParm : dataSourceInterface.getResult(HAPConstantShared.SERVICE_RESULT_SUCCESS).getOutput()) {
			HAPDataTypeCriteria dataTypeCriteria = resultParm.getDataDefinition().getCriteria();
		
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup();
			
			//parm static infor
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic(new HAPStoryWizzardQuestionValueDataSourceResponseParmInfoStatic(resultParm), HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMINFO);
			parmGroupQ.addItem(parmInfoStaticQ);
			
			//dynamic of uitag
			HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic(new HAPStoryWizzardQuestionValueDataSourceResponseParmChooseUIDynamic(null), HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCERESPONSEPARMUITAG);
			parmGroupQ.addItem(parmUITagChooseQ);

			
			Set<HAPDataTypeId> dataTypeIds = dataTypeCriteria.getValidDataTypeId(this.m_dataTypeHelper);
			HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
			HAPDataType dataType = this.m_dataTypeMan.getDataType(dataTypeId);
			boolean isComplex = dataType.getIsComplex();
			if(isComplex) {
				if(dataTypeId.getFullName().contains("array")){
					
				}
				else if(dataTypeId.getFullName().contains("map")){
					
				}
			}

			
		}
		
		out.addItem(serviceResponseGroupQ);
		
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

	private Object parseQuestionValue(JSONObject jsonObj, String valueType) {
		return this.m_entityParseService.parseEntityJSONExplicit(jsonObj, HAPStoryWizzardValueInQuestionair.PARSER_DOMAIN, valueType);
	}
	
}

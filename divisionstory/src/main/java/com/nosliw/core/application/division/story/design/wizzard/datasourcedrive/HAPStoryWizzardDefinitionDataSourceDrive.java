package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParm;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.brick.element.HAPStoryElementDataSource;
import com.nosliw.core.application.division.story.brick.element.HAPStoryElementModule;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignRequestChangeGroup;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnectionContainer;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemConnectionNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryDesignMetadataStepWizard;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionair;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairGroup;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemDynamic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardQuestionairItemStatic;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardRequestDataNext;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardStepDefinition;
import com.nosliw.core.application.division.story.design.wizzard.HAPStoryWizzardValueInQuestionair;
import com.nosliw.core.application.entity.service.HAPManagerService;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryWizzardDefinitionDataSourceDrive extends HAPStoryWizzardDefinition{

	public static final String STEP_SELECTDATASOURCE = "selectDataSource"; 
	public static final String STEP_CUSTOMIZEUI = "customizeUI"; 
	
	private final static HAPStoryAliasElement ALIAS_ELEMENT_MODULE = new HAPStoryAliasElement("module", false);
	private final static HAPStoryAliasElement ALIAS_ELEMENT_DATASOURCE = new HAPStoryAliasElement("dataSource", false);
	
	private List<HAPStoryWizzardStepDefinition> m_stepDefinitions;
	
	private HAPServiceParseEntity m_entityParseService;
	
	private HAPManagerService m_serviceMan;
	
	public HAPStoryWizzardDefinitionDataSourceDrive(HAPServiceParseEntity entityParseService, HAPManagerService serviceMan) {
		this.m_entityParseService = entityParseService;
		this.m_serviceMan = serviceMan;
		
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
		HAPStoryDesignRequestChangeGroup changeGroup = new HAPStoryDesignRequestChangeGroup();
		changeGroup.addChangeItem(new HAPStoryChangeItemNew(new HAPStoryElementModule(), ALIAS_ELEMENT_MODULE));
		design.applyChanges(changeGroup);
		design.commitStep();

		//service step
		HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_SELECTDATASOURCE));
		stepMetaData.addQuestionair(new HAPStoryWizzardQuestionairItemDynamic());
		design.newStep(stepMetaData);
		design.commitStep();
		
	}
	
	//error: attach error to answer
	//next : next step name, question
	@Override
	public void processNext(HAPStoryDesign design, HAPStoryWizzardRequestDataNext request) {
		HAPStoryDesignMetadataStepWizard stepData = request.getStepData();
		String stepName = stepData.getStepDefinition().getName();
		
		//validation lifecycle first
		
		//validation answer
		HAPStoryWizzardQuestionairItemDynamic questionair = (HAPStoryWizzardQuestionairItemDynamic)stepData.getQuestionairs().get(0);
		Object changeValue = questionair.getChangedValue();
		HAPStoryWizzardQuestionValueDynamicDataSourceChoose choosServiceQuestion = (HAPStoryWizzardQuestionValueDynamicDataSourceChoose)this.parseQuestionValue((JSONObject)changeValue, HAPConstantShared.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID);
		
		String dataSourceId = choosServiceQuestion.getDataSourceName();
		HAPInteractiveTask dataSourceInterface = this.m_serviceMan.getServiceProfile(dataSourceId, null).getInterface();

		//apply answer
		this.applyDataSourceSelection(design, dataSourceId, dataSourceInterface);
		
        //prepare next step + questionair
		HAPStoryDesignMetadataStepWizard stepMetaData = new HAPStoryDesignMetadataStepWizard(this.getStepDefinition(STEP_SELECTDATASOURCE));
		stepMetaData.addQuestionair(this.prepareChooseUIQuestionair(dataSourceInterface));
		design.newStep(stepMetaData);
		design.commitStep();
		
	}

	private HAPStoryWizzardQuestionair prepareChooseUIQuestionair(HAPInteractiveTask dataSourceInterface) {
		//root group
		HAPStoryWizzardQuestionairGroup serviceInterfaceGroupQ = new HAPStoryWizzardQuestionairGroup();
		
		//group for requests
		HAPStoryWizzardQuestionairGroup serviceRequestGroupQ = new HAPStoryWizzardQuestionairGroup();
		for(HAPDefinitionParm parDef : dataSourceInterface.getRequestParms()) {
			
			//group for parm
			HAPStoryWizzardQuestionairGroup parmGroupQ = new HAPStoryWizzardQuestionairGroup();
			HAPDataTypeCriteria dataTypeCriteria = parDef.getDataDefinition().getRuleCriteria();
			
			//parm static infor
			HAPStoryWizzardQuestionairItemStatic parmInfoStaticQ = new HAPStoryWizzardQuestionairItemStatic();
			
			//group for parm dynamic info
			HAPStoryWizzardQuestionairGroup parmDynamicGroupQ = new HAPStoryWizzardQuestionairGroup();
			
			//dynamic of is constant
			HAPStoryWizzardQuestionairItemDynamic parmIsConstantQ = new HAPStoryWizzardQuestionairItemDynamic();

			//dynamic of constant value
			HAPStoryWizzardQuestionairItemDynamic parmConstantValueQ = new HAPStoryWizzardQuestionairItemDynamic();

			//dynamic of uitag
			HAPStoryWizzardQuestionairItemDynamic parmUITagChooseQ = new HAPStoryWizzardQuestionairItemDynamic();
			
			
			//child criteria
//			HAPDataTypeCriteriaId 
		}
		
		
		//group for response
		HAPStoryWizzardQuestionairGroup serviceResponseGroupQ = new HAPStoryWizzardQuestionairGroup();
		
	}
	
	private void applyDataSourceSelection(HAPStoryDesign design, String dataSourceId, HAPInteractiveTask dataSourceInterface) {
		HAPStoryDesignRequestChangeGroup changeGroup = new HAPStoryDesignRequestChangeGroup();
		
		//data source item
		HAPStoryElementDataSource dataSourceItem = new HAPStoryElementDataSource(dataSourceId, dataSourceInterface);
		changeGroup.addChangeItem(new HAPStoryChangeItemNew(dataSourceItem, ALIAS_ELEMENT_DATASOURCE));
		changeGroup.addChangeItem(new HAPStoryChangeItemConnectionNew(ALIAS_ELEMENT_MODULE, ALIAS_ELEMENT_DATASOURCE, new HAPStoryChangeInfoConnectionContainer()));
		
		//put data source under module
		
		
		design.applyChanges(changeGroup);
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

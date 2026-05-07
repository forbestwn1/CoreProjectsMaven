package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.core.application.division.story.HAPStoryStory;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;
import com.nosliw.core.application.division.story.design.change.HAPStoryManagerChange;

//dynamic, 
//    store all the information about how story was build
//    support transaction
public class HAPStoryDesign extends HAPEntityInfoImp{

	private HAPStoryManagerChange m_changeMan;

	private String m_builderId;
	
	private HAPStoryStory m_story;
	
	private List<HAPStoryDesignStep> m_changeHistory;
	
	public HAPStoryDesign(String id, String builderId) {
		this.setId(id);
		this.m_builderId = builderId;
	}
	
	public String getBuilderId() {  return this.m_builderId;     }
	
	public HAPStoryStory getStory() {    return this.m_story;     }
	
	
	public void newStep(HAPStoryDesignMetadataStep metaData) {
		HAPStoryDesignStep step = new HAPStoryDesignStep(metaData);
		this.m_changeHistory.add(step);
	}

	public void applyChanges(HAPStoryDesignRequestChange changeRequest) {
		List<HAPStoryChangeItem> changes = new ArrayList<HAPStoryChangeItem>();
		if(changeRequest.isExtend()) {
			this.m_changeMan.applyChanges(this.m_story, changeRequest.getChanges(), changes);
		}
		else {
			this.m_changeMan.applyChanges(this.m_story, changeRequest.getChanges());
			changes.addAll(changeRequest.getChanges());
		}

		HAPStoryDesignStep step = this.getCurrentStep();
		step.addChanges(changeRequest.getChanges(), changes);
	}
	
	public void removeStep() {
		this.rollBackStep();
		this.m_changeHistory.remove(this.m_changeHistory.size()-1);
	}
	
	public void rollBackStep() {
		HAPStoryDesignStep step = this.getCurrentStep();
		this.m_changeMan.revertChange(m_story, step.getChanges());
		step.getMetaData().clear();
	}
	
	public void commitStep() {
		
	}
	
	public List<HAPStoryDesignMetadataStep> getStepInfos(){
		List<HAPStoryDesignMetadataStep> out = new ArrayList<HAPStoryDesignMetadataStep>();
		for(HAPStoryDesignStep step : this.m_changeHistory) {
			out.add(step.getMetaData());
		}
		return out;      
	}
	
	public HAPStoryDesignStep getCurrentStep() {}
	
	private HAPStoryDesignStep getStepById(String stepId) {}
	
	private String getStepId(HAPStoryDesignInfoStep stepInfo) {}
	
	private String getNextStep(String stepId) {}
	
	private String getPreviousStep(String stepId) {}

	private String getFirstStep() {}
	
	private String getLastStep() {}
	
}

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
public class HAPStoryDesignStory extends HAPEntityInfoImp{

	private HAPStoryManagerChange m_changeMan;

	private String m_builderId;
	
	private HAPStoryStory m_story;
	
	private List<HAPStoryDesignStep> m_changeHistory;
	
	public HAPStoryDesignStory(String id, String builderId) {
		this.setId(id);
		this.m_builderId = builderId;
	}
	
	public String getBuilderId() {  return this.m_builderId;     }
	
	public HAPStoryStory getStory() {    return this.m_story;     }
	
	
	public void newStep(HAPStoryInfoStep stepInfo) {
		HAPStoryDesignStep step = new HAPStoryDesignStep(stepInfo);
		this.m_changeHistory.add(step);
	}

	public void applyChanges(HAPStoryRequestChange changeRequest) {
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
	
	public void rollBackStep() {
		HAPStoryDesignStep step = this.getCurrentStep();
		this.m_changeMan.revertChange(m_story, step.getChanges());
	}
	
	public void commitStep() {
		
	}
	
	
	private HAPStoryDesignStep getCurrentStep() {}
	
	private HAPStoryDesignStep getStepById(String stepId) {}
	
	private String getStepId(HAPStoryInfoStep stepInfo) {}
	
	private String getNextStep(String stepId) {}
	
	private String getPreviousStep(String stepId) {}

	private String getFirstStep() {}
	
	private String getLastStep() {}
	
}

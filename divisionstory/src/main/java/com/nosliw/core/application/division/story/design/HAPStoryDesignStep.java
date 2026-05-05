package com.nosliw.core.application.division.story.design;

import java.util.List;

import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

public class HAPStoryDesignStep {

	private HAPStoryDesignInfoStep m_stepInfo;
	
	private List<HAPStoryChangeItem> m_requestChanges;
	
	//store all changes
	private List<HAPStoryChangeItem> m_allChanages;
	
	public HAPStoryDesignStep(HAPStoryDesignInfoStep stepInfo) {
		this.m_stepInfo = stepInfo;
	}
	

	public void addChanges(List<HAPStoryChangeItem> requestChanges, List<HAPStoryChangeItem> changes) {
		
	}
	
    public List<HAPStoryChangeItem> getChanges() {    return this.m_allChanages;      }


}

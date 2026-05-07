package com.nosliw.core.application.division.story.design;

import java.util.List;

import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

public class HAPStoryDesignStep {

	private HAPStoryDesignMetadataStep m_metaData;
	
	private List<HAPStoryChangeItem> m_requestChanges;
	
	//store all changes
	private List<HAPStoryChangeItem> m_allChanages;
	
	public HAPStoryDesignStep(HAPStoryDesignMetadataStep metaData) {
		this.m_metaData = metaData;
	}

	public void clear() {
		this.m_allChanages.clear();
		this.m_requestChanges.clear();
		this.m_metaData.clear();
	}

	public HAPStoryDesignMetadataStep getMetaData() {     return this.m_metaData;      }
	
	public void addChanges(List<HAPStoryChangeItem> requestChanges, List<HAPStoryChangeItem> changes) {
		
	}
	
    public List<HAPStoryChangeItem> getChanges() {    return this.m_allChanages;      }


}

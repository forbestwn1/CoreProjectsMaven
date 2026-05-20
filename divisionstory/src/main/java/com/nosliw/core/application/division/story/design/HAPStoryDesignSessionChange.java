package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.core.application.division.story.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.HAPStoryStory;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;
import com.nosliw.core.application.division.story.brick.HAPStoryReferenceElement;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnection;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemConnectionNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;

public class HAPStoryDesignSessionChange {

	private List<HAPStoryChangeItem> m_changeItems;
	
	private HAPStoryDesign m_storyDesign;
	
	private HAPStoryDesignConfigureSessionChange m_configure;
	
	public HAPStoryDesignSessionChange(HAPStoryDesign storyDesign) {
		this(storyDesign, null);
	}
	
	public HAPStoryDesignSessionChange(HAPStoryDesign storyDesign, HAPStoryDesignConfigureSessionChange configure) {
		this.m_storyDesign = storyDesign;
		this.m_configure = configure==null?new HAPStoryDesignConfigureSessionChange() : configure;
		this.m_changeItems = new ArrayList<HAPStoryChangeItem>();
	}

	public void commit() {
		this.m_storyDesign.applyChanges(m_changeItems, m_configure);
	}
	
	public void rollback() {
		
	}
	
	public List<HAPStoryChangeItem> getChangeItems(){   return this.m_changeItems;    }
	public void addChangeItem(HAPStoryChangeItem changeItem) {
		this.m_changeItems.add(changeItem);     
	}
	public void addChangeItems(List<HAPStoryChangeItem> changeItems) {
		for(HAPStoryChangeItem changeItem : changeItems) {
			this.addChangeItem(changeItem);
		}
	}
	
	public HAPStoryChangeItemNew addChangeItemNew(HAPStoryElement storyElement) {
		return this.addChangeItemNew(storyElement, null);
	}
	
	public HAPStoryChangeItemNew addChangeItemNew(HAPStoryElement storyElement, HAPStoryAliasElement alias) {
		if(storyElement.getElementId()==null) {
			this.getStory().buildElementId(storyElement);
		}
		HAPStoryChangeItemNew out = new HAPStoryChangeItemNew(storyElement, alias);
		this.m_changeItems.add(out);
		return out;
	}

	public HAPStoryChangeItemConnectionNew addChangeConnectionNew(HAPStoryReferenceElement elementRefSource, HAPStoryReferenceElement elementRefTarget, HAPStoryChangeInfoConnection connectionInfo) {
		HAPStoryChangeItemConnectionNew out = new HAPStoryChangeItemConnectionNew(elementRefSource, elementRefTarget, connectionInfo);
		this.m_changeItems.add(out);
		return out;
	}

	
	public HAPStoryDesignConfigureSessionChange getConfigure() {    return this.m_configure;       }
	
	private HAPStoryStory getStory() {    return this.m_storyDesign.getStory();       }

}

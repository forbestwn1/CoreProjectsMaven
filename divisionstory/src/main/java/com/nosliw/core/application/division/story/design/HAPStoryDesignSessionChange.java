package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAlias;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnection;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemConnectionNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemElementNew;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemRunnableNew;

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

	public HAPStoryElement getElement(HAPStoryReferenceElement eleRef) {
		return this.getStory().getElement(eleRef);
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
	
	public HAPStoryChangeItemElementNew addChangeItemNew(HAPStoryElement storyElement) {		return this.addChangeItemNew(storyElement, null);	}
	
	public HAPStoryChangeItemElementNew addChangeItemNew(HAPStoryElement storyElement, HAPStoryAlias alias) {
		if(storyElement.getElementId()==null) {
			this.getStory().buildElementId(storyElement);
		}
		HAPStoryChangeItemElementNew out = new HAPStoryChangeItemElementNew(storyElement, alias);
		this.m_changeItems.add(out);
		return out;
	}

	public HAPStoryChangeItemRunnableNew addChangeItemNew(HAPStoryRunnable storyRunnable) {		return this.addChangeItemNew(storyRunnable, null);	}
	
	public HAPStoryChangeItemRunnableNew addChangeItemNew(HAPStoryRunnable storyRunnable, HAPStoryAlias alias) {
		if(storyRunnable.getId()==null) {
			this.getStory().buildRunnableId(storyRunnable);
		}
		HAPStoryChangeItemRunnableNew out = new HAPStoryChangeItemRunnableNew(storyRunnable, alias);
		this.m_changeItems.add(out);
		return out;
	}

	
	public HAPStoryChangeItemConnectionNew addChangeConnectionNew(HAPStoryReferenceElement elementIdSource, HAPStoryReferenceElement elementIdTarget, HAPStoryChangeInfoConnection connectionInfo) {
		HAPStoryChangeItemConnectionNew out = new HAPStoryChangeItemConnectionNew(getElementId(elementIdSource), getElementId(elementIdTarget), connectionInfo);
		this.m_changeItems.add(out);
		return out;
	}

	public HAPStoryDesignConfigureSessionChange getConfigure() {    return this.m_configure;       }

	private HAPStoryIdElement getElementId(HAPStoryReferenceElement eleRef) {
		HAPStoryStory story = this.getStory();
		HAPStoryIdElement out = null;
		String refType = eleRef.getEntityOrReferenceType();
		if(HAPConstantShared.STORY_ELEMENT_REFERENCE_ID.equals(refType)) {
			out = (HAPStoryIdElement)eleRef;
		}
		else if(HAPConstantShared.STORY_ELEMENT_REFERENCE_ALIAS.equals(refType)) {
			out = story.getElement(eleRef).getElementId();
		}
		return out;
	}
	
	private HAPStoryStory getStory() {    return this.m_storyDesign.getStory();       }

}

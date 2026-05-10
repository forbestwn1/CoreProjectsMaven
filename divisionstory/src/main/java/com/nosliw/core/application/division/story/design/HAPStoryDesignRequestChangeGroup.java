package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

public class HAPStoryDesignRequestChangeGroup {

	private List<HAPStoryChangeItem> m_changeItems;
	
	private Boolean m_extend;
	
	public HAPStoryDesignRequestChangeGroup() {
		this(true);
	}
	
	public HAPStoryDesignRequestChangeGroup(Boolean extend) {
		this.m_changeItems = new ArrayList<HAPStoryChangeItem>();
		this.m_extend = extend;
		if(this.m_extend==null) {
			this.m_extend = true;
		}
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
	
	public boolean isExtend() {   return this.m_extend;    }
	public void notExtend() {   this.m_extend = false;    }

}

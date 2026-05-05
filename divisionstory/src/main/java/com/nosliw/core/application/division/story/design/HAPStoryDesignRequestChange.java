package com.nosliw.core.application.division.story.design;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItem;

public class HAPStoryDesignRequestChange {

	private List<HAPStoryChangeItem> m_changes;
	
	private Boolean m_extend;
	
	public HAPStoryDesignRequestChange(Boolean extend) {
		this.m_changes = new ArrayList<HAPStoryChangeItem>();
		this.m_extend = extend;
		if(this.m_extend==null) {
			this.m_extend = true;
		}
	}

	public List<HAPStoryChangeItem> getChanges(){   return this.m_changes;    }
	public void addChange(HAPStoryChangeItem change) {
		this.m_changes.add(change);     
	}
	public void addChanges(List<HAPStoryChangeItem> changes) {
		for(HAPStoryChangeItem change : changes) {
			this.addChange(change);
		}
	}
	
	public boolean isExtend() {   return this.m_extend;    }
	public void notExtend() {   this.m_extend = false;    }

}

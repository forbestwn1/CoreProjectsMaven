package com.nosliw.core.application.division.story.design;

public class HAPStoryDesignConfigureSessionChange {

	private Boolean m_extend;
	
	public HAPStoryDesignConfigureSessionChange() {
		this(null);
	}
	
    public HAPStoryDesignConfigureSessionChange(Boolean extend) {
		this.m_extend = extend;
		if(this.m_extend==null) {
			this.m_extend = true;
		}
    	
    }
	
	public boolean isExtend() {   return this.m_extend;    }
	public void notExtend() {   this.m_extend = false;    }

}

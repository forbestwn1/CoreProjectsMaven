package com.nosliw.core.application.division.story.design.change;

import java.util.List;

import com.nosliw.core.application.division.story.HAPStoryStory;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryManagerChange {

	public void revertChange(HAPStoryStory story, List<HAPStoryChangeItem> changeItems) {
		//apply in revert sequence
	}
	
	//apply change, not triggured extend changes to story
	public void applyChanges(HAPStoryStory story, List<HAPStoryChangeItem> changeItems) {		
	}

	//apply change and triggued extend changes to story
	//allChanges : story all changes
	public void applyChanges(HAPStoryStory story, List<HAPStoryChangeItem> changeItems, List<HAPStoryChangeItem> allChanges) {
	}
	
	//apply change, not triggured extend changes to story
	public void applyChange(HAPStoryStory story, HAPStoryChangeItem changeItem) {		
	}
	
	//apply change and triggued extend changes to story
	//allChanges : story all changes
	public HAPStoryElement applyChange(HAPStoryStory story, HAPStoryChangeItem change, List<HAPStoryChangeItem> allChanges) {
		return null;
	}	
	
}

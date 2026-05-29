package com.nosliw.core.application.division.story.definition.element.ui;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeInfoConnectionContainer;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;

public class HAPStoryElementUIUtility {

	public static Pair<HAPStoryChangeItemNew, HAPStoryChangeItemNew> newUIPage(HAPStoryDesignSessionChange changeSession, HAPStoryReferenceElement parentElement, HAPEntityInfo entityInfo) {
		//add page to module
		HAPStoryChangeItemNew newPageChange = changeSession.addChangeItemNew(new HAPStoryElementUIPage(entityInfo));
		changeSession.addChangeConnectionNew(parentElement, newPageChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(HAPStoryElementEntityModule.getAddPageChildPath()));
		
		//add content wrapper to page
		HAPStoryChangeItemNew newPageUIWrapperChange = changeSession.addChangeItemNew(new HAPStoryElementUIWrapperContent());
		changeSession.addChangeConnectionNew(newPageChange.getElementId(), newPageUIWrapperChange.getElementId(), new HAPStoryChangeInfoConnectionContainer(new HAPPath(HAPStoryElementUIPage.CHILD_CONTENTWRAPPER)));
		return Pair.of(newPageChange, newPageUIWrapperChange);
	}

	
	public static HAPStoryChangeItemNew newUIContentHtml(HAPStoryDesignSessionChange changeSession, String content) {
		
	}
	
	
	
}

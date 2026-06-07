package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentHtml;
import com.nosliw.core.application.division.story.design.HAPStoryDesignSessionChange;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemNew;

public class HAPStoryWizzardUtility {

	public static HAPStoryChangeItemNew newUIContentHtmlFromFile(HAPStoryDesignSessionChange changeSession, String fileName) {
		String content = HAPUtilityFile.readFile(HAPStoryWizzardUtility.class, fileName);
		HAPStoryElementUIContentHtml htmlContentElement = new HAPStoryElementUIContentHtml(content);
		return changeSession.addChangeItemNew(htmlContentElement);
	}
	
	public static HAPStoryChangeItemNew newUIContentHtml(HAPStoryDesignSessionChange changeSession, String content) {
		HAPStoryElementUIContentHtml htmlContentElement = new HAPStoryElementUIContentHtml(content);
		return changeSession.addChangeItemNew(htmlContentElement);
	}
	
}

package com.nosliw.core.application.division.story.design;

import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryElementUIContentHtml;
import com.nosliw.core.application.division.story.design.change.HAPStoryChangeItemElementNew;

public class HAPStoryDesignUtilityUI {

	public static HAPStoryChangeItemElementNew newUIContentHtmlFromFile(HAPStoryDesignSessionChange changeSession, String fileName, Class className) {
		String content = HAPUtilityFile.readFile(className, fileName);
		HAPStoryElementUIContentHtml htmlContentElement = new HAPStoryElementUIContentHtml(content);
		return changeSession.addChangeItemNew(htmlContentElement);
	}
	
	public static HAPStoryChangeItemElementNew newUIContentHtml(HAPStoryDesignSessionChange changeSession, String content) {
		HAPStoryElementUIContentHtml htmlContentElement = new HAPStoryElementUIContentHtml(content);
		return changeSession.addChangeItemNew(htmlContentElement);
	}
	
}

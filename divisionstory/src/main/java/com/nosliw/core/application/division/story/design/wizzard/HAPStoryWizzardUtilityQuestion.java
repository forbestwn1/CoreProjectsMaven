package com.nosliw.core.application.division.story.design.wizzard;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryWizzardUtilityQuestion {

	public static void cleanError(HAPStoryWizzardQuestionair questionair) {
		String type = questionair.getType();
		if(type.equals(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC)) {
			HAPStoryWizzardQuestionairItemDynamic dynamicQ = (HAPStoryWizzardQuestionairItemDynamic)questionair;
			dynamicQ.setError(null);
		}
		else if(type.equals(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_GROUP)) {
			HAPStoryWizzardQuestionairGroup groupQ = (HAPStoryWizzardQuestionairGroup)questionair;
			for(HAPStoryWizzardQuestionair item : groupQ.getItems()) {
				cleanError(item);
			}
		}
	}
	
	public static HAPStoryWizzardQuestionair findSingleQuestionairByTag(HAPStoryWizzardQuestionair questionair, String tag){
		HAPStoryWizzardQuestionair out = null;
		List<HAPStoryWizzardQuestionair> qs = findQuestionairsByTag(questionair, tag);
		if(qs.size()>0) {
			out = qs.get(0);
		}
		return out;
	}
	
	
	public static List<HAPStoryWizzardQuestionair> findQuestionairsByTag(HAPStoryWizzardQuestionair questionair, String tag){
		List<HAPStoryWizzardQuestionair> out = new ArrayList<HAPStoryWizzardQuestionair>();
		findQuestionairByTag(questionair, tag, out);
		return out;
	}
	
	private static void findQuestionairByTag(HAPStoryWizzardQuestionair questionair, String tag, List<HAPStoryWizzardQuestionair> output) {
		if(questionair.isTag(tag)) {
			output.add(questionair);
		}
		if(questionair.getType().equals(HAPConstantShared.STORYDESIGN_QUESTIONTYPE_GROUP)) {
			HAPStoryWizzardQuestionairGroup groupQ = (HAPStoryWizzardQuestionairGroup)questionair;
			for(HAPStoryWizzardQuestionair item : groupQ.getItems()) {
				findQuestionairByTag(item, tag, output);
			}
		}
	}
	
	
}

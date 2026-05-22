package com.nosliw.core.application.division.story.design.change;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;

@Component
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
		for(HAPStoryChangeItem change : changeItems) {
			applyChange(story, change, allChanges);
		}
	}
	
	//apply change, not triggured extend changes to story
	public void applyChange(HAPStoryStory story, HAPStoryChangeItem changeItem) {
		applySingleChange(story, changeItem, null, true);
	}
	
	//apply change and triggued extend changes to story
	//allChanges : story all changes
	public HAPStoryElementImp applyChange(HAPStoryStory story, HAPStoryChangeItem change, List<HAPStoryChangeItem> allChanges) {
		allChanges.add(change);
		
		List<HAPStoryChangeItem> extendChanges = new ArrayList<HAPStoryChangeItem>();
		HAPStoryElementImp element = applySingleChange(story, change, extendChanges, true);
		
		for(HAPStoryChangeItem extendChange : extendChanges) {
			applyChange(story, extendChange, allChanges);
		}
		return element;
	}	
	
	
	
	
	
	//apply change to story
	//     extendedChanges :  sometimes, one change may trigue more extend changes
	//     saveRevert : whether save revert information when apply change.
	//return story element related with change
	private HAPStoryElementImp applySingleChange(HAPStoryStory story, HAPStoryChangeItem changeItem, List<HAPStoryChangeItem> extendChanges, boolean saveRevert) {
		HAPStoryElementImp out = null;
		String changeType = changeItem.getChangeType();
		if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_NEW)) {
			HAPStoryChangeItemNew changeNew = (HAPStoryChangeItemNew)changeItem;
			HAPStoryElementImp element = changeNew.getElement();
			HAPStoryAliasElement alias = changeNew.getAlias();
			HAPStoryIdElement oldAliasEleId = null;
			if(alias!=null) {
				oldAliasEleId = story.getElementId(alias.getName());
			}
			out = story.addElement(element, alias);
			changeNew.setElement(out.cloneStoryElement());
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemDelete(out.getElementId()));
				if(alias!=null && !alias.isTemporary()) {
					if(oldAliasEleId!=null) {
						revertChanges.add(new HAPStoryChangeItemAlias(alias, oldAliasEleId));
					}
				}
				changeItem.setRevertChanges(revertChanges);
			}
		}		
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_DELETE)) {
			HAPStoryChangeItemDelete changeDelete = (HAPStoryChangeItemDelete)changeItem;
			changeDelete.processAlias(story);
			HAPStoryAliasElement alias = story.getAlias(changeDelete.getTargetElementId());
			HAPStoryElementImp element = story.deleteElement(changeDelete.getTargetElementId());
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemNew(element, alias));
				changeItem.setRevertChanges(revertChanges);
			}
		}
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW)) {
			HAPStoryChangeItemConnectionNew changeConnectionNew = (HAPStoryChangeItemConnectionNew)changeItem;
			HAPStoryElementImp sourceEle = story.getElement(changeConnectionNew.getSourceElementId());
			HAPStoryElementImp targetEle = story.getElement(changeConnectionNew.getTargetElementId());
			HAPStoryChangeInfoConnection connectionInfo = changeConnectionNew.getConnectionInfo();
			if(connectionInfo.getConnectionType().equals(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN)) {
				sourceEle.addChild(targetEle);
				if(ifRevertable(saveRevert, changeItem)) {
					List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
					HAPStoryChangeItemConnectionDelete deleteChange = new HAPStoryChangeItemConnectionDelete(changeConnectionNew.getSourceElementId(), changeConnectionNew.getTargetElementId(), changeConnectionNew.getConnectionInfo());
					revertChanges.add(deleteChange);
					changeItem.setRevertChanges(revertChanges);
				}
			}
		}

		if(extendChanges!=null) {
			changeItem.setExtended();
		}
		return out;
	}
	
	private boolean ifRevertable(boolean saveRevert, HAPStoryChangeItem changeItem) {
		if(!saveRevert) {
			return false;
		}
		return changeItem.isRevertable();
	}
	
}

package com.nosliw.core.application.division.story.design.change;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryChildElement;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsSingle;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
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
	public HAPStoryElement applyChange(HAPStoryStory story, HAPStoryChangeItem change, List<HAPStoryChangeItem> allChanges) {
		allChanges.add(change);
		
		List<HAPStoryChangeItem> extendChanges = new ArrayList<HAPStoryChangeItem>();
		HAPStoryElement element = applySingleChange(story, change, extendChanges, true);
		
		for(HAPStoryChangeItem extendChange : extendChanges) {
			applyChange(story, extendChange, allChanges);
		}
		return element;
	}	
	
	
	
	
	
	//apply change to story
	//     extendedChanges :  sometimes, one change may trigue more extend changes
	//     saveRevert : whether save revert information when apply change.
	//return story element related with change
	private HAPStoryElement applySingleChange(HAPStoryStory story, HAPStoryChangeItem changeItem, List<HAPStoryChangeItem> extendChanges, boolean saveRevert) {
		HAPStoryElement out = null;
		String changeType = changeItem.getChangeType();
		if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_NEW)) {
			HAPStoryChangeItemNew changeNew = (HAPStoryChangeItemNew)changeItem;
			HAPStoryElement element = changeNew.getElement();
			HAPStoryAliasElement alias = changeNew.getAlias();
			out = story.addElement(element, alias);
			changeNew.setElement(out.cloneStoryElement());
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges =new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemDelete(out.getElementId()));
				changeItem.setRevertChanges(revertChanges);
			}
		}		
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_DELETE)) {
			HAPStoryChangeItemDelete changeDelete = (HAPStoryChangeItemDelete)changeItem;
			changeDelete.processAlias(story);
			HAPStoryAliasElement alias = story.getAlias(changeDelete.getTargetElementId());
			HAPStoryElement element = story.deleteElement(changeDelete.getTargetElementId());
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemNew(element, alias));
				changeItem.setRevertChanges(revertChanges);
			}
		}
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_NEW)) {
			HAPStoryChangeItemConnectionNew changeConnectionNew = (HAPStoryChangeItemConnectionNew)changeItem;
			HAPStoryElement sourceEle = story.getElement(changeConnectionNew.getSourceElementId());
			HAPStoryElement targetEle = story.getElement(changeConnectionNew.getTargetElementId());
			HAPStoryChangeInfoConnection connectionInfo = changeConnectionNew.getConnectionInfo();
			if(connectionInfo.getConnectionType().equals(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN)) {
				HAPStoryChangeInfoConnectionContainer containConnectionInfo = (HAPStoryChangeInfoConnectionContainer)connectionInfo;
				HAPPath childPath = sourceEle.addChild(new HAPStoryChildElement(targetEle.getElementId(), containConnectionInfo.getMetaData()), containConnectionInfo.getChildPath());
				if(ifRevertable(saveRevert, changeItem)) {
					List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
					HAPStoryChangeItemConnectionDelete deleteChange = new HAPStoryChangeItemConnectionDelete(changeConnectionNew.getSourceElementId(), changeConnectionNew.getTargetElementId(), new HAPStoryChangeInfoConnectionContainer(childPath));
					revertChanges.add(deleteChange);
					changeItem.setRevertChanges(revertChanges);
				}
			}
		}
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_CONNECTION_DELETE)) {
			HAPStoryChangeItemConnectionDelete changeConnectionDelete = (HAPStoryChangeItemConnectionDelete)changeItem;
			HAPStoryElement sourceEle = story.getElement(changeConnectionDelete.getSourceElementId());
			HAPStoryElement targetEle = story.getElement(changeConnectionDelete.getTargetElementId());
			HAPStoryChangeInfoConnection connectionInfo = changeConnectionDelete.getConnectionInfo();
			if(connectionInfo.getConnectionType().equals(HAPConstantShared.STORYCONNECTION_TYPE_CONTAIN)) {
				HAPStoryChangeInfoConnectionContainer containConnectionInfo = (HAPStoryChangeInfoConnectionContainer)connectionInfo;
				HAPStoryContainerChildrenElementsSingle childElementSingle = sourceEle.removeChild(containConnectionInfo.getChildPath());
				if(ifRevertable(saveRevert, changeItem)) {
					List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
					HAPStoryChangeItemConnectionNew newChange = new HAPStoryChangeItemConnectionNew(changeConnectionDelete.getSourceElementId(), changeConnectionDelete.getTargetElementId(), new HAPStoryChangeInfoConnectionContainer(containConnectionInfo.getChildPath(), childElementSingle.getChildElement().getMetaData()));
					revertChanges.add(newChange);
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

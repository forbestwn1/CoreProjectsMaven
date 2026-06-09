package com.nosliw.core.application.division.story.design.change;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryAlias;
import com.nosliw.core.application.division.story.definition.HAPStoryChildElement;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsWrapper;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryRunnable;
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
	public void applyChange(HAPStoryStory story, HAPStoryChangeItem change, List<HAPStoryChangeItem> allChanges) {
		allChanges.add(change);
		
		List<HAPStoryChangeItem> extendChanges = new ArrayList<HAPStoryChangeItem>();
		applySingleChange(story, change, extendChanges, true);
		
		for(HAPStoryChangeItem extendChange : extendChanges) {
			applyChange(story, extendChange, allChanges);
		}
	}	
	
	
	
	
	
	//apply change to story
	//     extendedChanges :  sometimes, one change may trigue more extend changes
	//     saveRevert : whether save revert information when apply change.
	//return story element related with change
	private void applySingleChange(HAPStoryStory story, HAPStoryChangeItem changeItem, List<HAPStoryChangeItem> extendChanges, boolean saveRevert) {
		String changeType = changeItem.getChangeType();
		if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_ELEMENT_NEW)) {
			HAPStoryChangeItemElementNew changeNew = (HAPStoryChangeItemElementNew)changeItem;
			HAPStoryElement element = changeNew.getElement();
			HAPStoryAlias alias = changeNew.getAlias();
			element = story.addElement(element, alias);
			changeNew.setElement(element);
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges =new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemElementDelete(element.getElementId()));
				changeItem.setRevertChanges(revertChanges);
			}
		}		
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_ELEMENT_DELETE)) {
			HAPStoryChangeItemElementDelete changeDelete = (HAPStoryChangeItemElementDelete)changeItem;
			HAPStoryAlias alias = story.getElementAlias(changeDelete.getTargetElementId());
			HAPStoryElement element = story.deleteElement(changeDelete.getTargetElementId());
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemElementNew(element, alias));
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
				HAPStoryContainerChildrenElementsWrapper childElementSingle = sourceEle.removeChild(containConnectionInfo.getChildPath());
				if(ifRevertable(saveRevert, changeItem)) {
					List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
					HAPStoryChangeItemConnectionNew newChange = new HAPStoryChangeItemConnectionNew(changeConnectionDelete.getSourceElementId(), changeConnectionDelete.getTargetElementId(), new HAPStoryChangeInfoConnectionContainer(containConnectionInfo.getChildPath(), childElementSingle.getChildElement().getMetaData()));
					revertChanges.add(newChange);
					changeItem.setRevertChanges(revertChanges);
				}
			}
		}
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_NEW)) {
			HAPStoryChangeItemRunnableNew changeNew = (HAPStoryChangeItemRunnableNew)changeItem;
			HAPStoryRunnable runnable = changeNew.getRunnable();
			HAPStoryAlias alias = changeNew.getAlias();
			runnable = story.addRunnable(runnable, alias);
			changeNew.setRunnable(runnable);
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges =new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemRunnableDelete(runnable.getId()));
				changeItem.setRevertChanges(revertChanges);
			}
		}
		else if(changeType.equals(HAPConstantShared.STORYDESIGN_CHANGETYPE_RUNNABLE_DELETE)) {
			HAPStoryChangeItemRunnableDelete changeDelete = (HAPStoryChangeItemRunnableDelete)changeItem;
			HAPStoryAlias alias = story.getRunnableAlias(changeDelete.getRunnableId());
			String runnableId = changeDelete.getRunnableId();
			HAPStoryRunnable runnable = story.deleteRunnable(runnableId);
			//revert changes info
			if(ifRevertable(saveRevert, changeItem)) {
				List<HAPStoryChangeItem> revertChanges = new ArrayList<HAPStoryChangeItem>();
				revertChanges.add(new HAPStoryChangeItemRunnableNew(runnable, alias));
				changeItem.setRevertChanges(revertChanges);
			}
		}
		
		if(extendChanges!=null) {
			changeItem.setExtended();
		}
	}
	
	private boolean ifRevertable(boolean saveRevert, HAPStoryChangeItem changeItem) {
		if(!saveRevert) {
			return false;
		}
		return changeItem.isRevertable();
	}
	
}

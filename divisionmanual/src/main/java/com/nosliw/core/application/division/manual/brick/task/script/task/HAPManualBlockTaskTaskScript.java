package com.nosliw.core.application.division.manual.brick.task.script.task;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.parm.HAPParms;
import com.nosliw.common.parm.HAPWithParms;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.HAPIdBrickInBundle;
import com.nosliw.core.application.brick.HAPWithBrickReferences;
import com.nosliw.core.application.brick.spec.task.script.task.HAPBlockTaskTaskScript;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockTaskTaskScript;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.resource.HAPResourceId;

public class HAPManualBlockTaskTaskScript extends HAPManualBrickImp implements HAPBlockTaskTaskScript{

	public HAPManualBlockTaskTaskScript() {
		super(HAPEnumBrickType.TASK_TASK_SCRIPT_100);
	}

	@Override
	public void init() {
		super.init();
		this.setAttributeValueWithValue(HAPWithBrickReferences.BRICKREFERENCES, new LinkedHashMap<String, HAPIdBrickInBundle>());
	}
	
	@Override
	public HAPEntityOrReference getTaskInterface() {    return this.getAttributeValueOfBrick(TASKINTERFACE);  }
	@Override
	public void setTaskInterface(HAPEntityOrReference taskInterface) {   this.setAttributeValueWithBrick(TASKINTERFACE, taskInterface);     }

	@Override
	public HAPResourceId getScriptResourceId() {   return (HAPResourceId)this.getAttributeValueOfValue(SCRIPTRESOURCEID);  }
	public void setScriptResourceId(HAPResourceId resourceId) {    this.setAttributeValueWithValue(SCRIPTRESOURCEID, resourceId);  }
	
	@Override
	public HAPParms getParms() {   return (HAPParms)this.getAttributeValueOfValue(HAPWithParms.PARM);  }
	@Override
	public void setParms(HAPParms parms) {   this.setAttributeValueWithValue(HAPWithParms.PARM, parms);  }

	@Override
	public Map<String, HAPIdBrickInBundle> getBrickReferences() {    return (Map<String, HAPIdBrickInBundle>)this.getAttributeValueOfValue(HAPWithBrickReferences.BRICKREFERENCES);   }
	@Override
	public void addBrickReference(String name, HAPIdBrickInBundle brickRef) {      this.getBrickReferences().put(name, brickRef);           }

}
 
@Component
class HAPManualBlockTaskTaskScript_parser extends HAPManualBrick_parser{

	public HAPManualBlockTaskTaskScript_parser() {
		super(HAPManualBlockTaskTaskScript.class, HAPEnumBrickType.TASK_TASK_SCRIPT_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockTaskTaskScript());	
	}

}

package com.nosliw.core.application.brick.imp.basic;

import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.parm.HAPParms;
import com.nosliw.common.parm.HAPWithParms;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.task.script.task.HAPBlockTaskTaskScript;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockTaskTaskScript;
import com.nosliw.core.resource.HAPResourceId;

public class HAPBasicBlockTaskTaskScript extends HAPBasicBrick implements HAPBlockTaskTaskScript{

	public HAPBasicBlockTaskTaskScript() {
		super(HAPEnumBrickType.TASK_TASK_SCRIPT_100);
		this.setParms(new HAPParms());
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

}

@Component
class HAPBasicBlockTaskTaskScript_parser extends HAPBasicBrick_parser{

	public HAPBasicBlockTaskTaskScript_parser() {
		super(HAPBasicBlockTaskTaskScript.class, HAPEnumBrickType.TASK_TASK_SCRIPT_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockTaskTaskScript());	
	}

}

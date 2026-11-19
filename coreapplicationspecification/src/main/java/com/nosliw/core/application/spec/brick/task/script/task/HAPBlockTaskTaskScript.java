package com.nosliw.core.application.spec.brick.task.script.task;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.parm.HAPWithParms;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPWithBrickReferences;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;
import com.nosliw.core.application.common.script.HAPWithScriptReference;

@HAPEntityWithAttribute
public interface HAPBlockTaskTaskScript extends HAPBrick, HAPWithBlockInteractiveTask, HAPWithScriptReference, HAPWithParms, HAPWithBrickReferences{

}


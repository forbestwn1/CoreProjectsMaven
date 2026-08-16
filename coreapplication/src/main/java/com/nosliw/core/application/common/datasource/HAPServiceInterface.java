package com.nosliw.core.application.common.datasource;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;

@HAPEntityWithAttribute
public class HAPServiceInterface {

	@HAPAttribute
	public static String INFO = "info";

	@HAPAttribute
	public static String INTERFACE = "interface";

	@HAPAttribute
	public static String TAG = "tag";

	@HAPAttribute
	public static String DISPLAY = "display";

	private String[] m_tags;
	
	private HAPEntityInfo m_entityInfo;
	
	private HAPInteractiveTask m_taskInterface;
	
	public HAPInteractiveTask getTaskInterface() {		return this.m_taskInterface;	}
	public void setTaskInterface(HAPInteractiveTask taskInterface) {     this.m_taskInterface = taskInterface;    }
	
	public HAPEntityInfo getEntityInfo() {	return this.m_entityInfo;	}
	public void setEntityInfo(HAPEntityInfo entityInfo) {     this.m_entityInfo = entityInfo;       }
}

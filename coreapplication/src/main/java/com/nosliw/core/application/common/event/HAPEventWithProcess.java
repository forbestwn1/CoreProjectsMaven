package com.nosliw.core.application.common.event;

import java.util.List;

public interface HAPEventWithProcess {

	List<HAPEventProcess> getEventProcesses();

	void addEventProcess(HAPEventProcess eventProcess);
	
}

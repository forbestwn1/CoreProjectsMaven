package com.nosliw.core.application.division.manual.core.standalone;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;

public class HAPStandaloneDefinition {

	private String m_content;
	
	private HAPSerializationFormat m_format;
	
	//brick type id if not provided
	private HAPIdBrickType m_brickTypeId;

	private List<HAPEventEmitter> m_eventExpose;
	
	private List<HAPCommandProcess> m_commandExpose;

	public HAPStandaloneDefinition() {
	}
	
	public HAPStandaloneDefinition(String content, HAPSerializationFormat format, HAPIdBrickType brickTypeId) {
		this.m_eventExpose = new ArrayList<HAPEventEmitter>();
		this.m_commandExpose = new ArrayList<HAPCommandProcess>();
		this.m_content = content;
		this.m_format = format;
		this.m_brickTypeId = brickTypeId;
	}
	
	public String getContent() {    return this.m_content;      }
	
	public HAPSerializationFormat getFormat() {     return this.m_format;      }
	
	public HAPIdBrickType getBrickTypeId() {    return this.m_brickTypeId;      }

	public List<HAPEventEmitter> getExposeEvents(){     return this.m_eventExpose;     }
	public void addExposeEvent(HAPEventEmitter eventEmitter) {      this.m_eventExpose.add(eventEmitter);       }
	
	public List<HAPCommandProcess> getExposeCommands(){     return this.m_commandExpose;        }
	public void addExposeCommand(HAPCommandProcess command) {     this.m_commandExpose.add(command);       }
	
}

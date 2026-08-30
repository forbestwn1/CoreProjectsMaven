package com.nosliw.core.application.division.manual.core.definition;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.manual.HAPManualContentProvider;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;

public class HAPManualDefinitionContextParse {

	private HAPManualContentProvider m_contentProvider;
	
	private String m_brickDivision;
	
	private HAPManualManagerBrick m_manualBrickMan;
	
	private HAPManagerApplicationBrick m_brickManager;
	
	private HAPServiceParseEntity m_parseService;
	
	public HAPManualDefinitionContextParse(HAPManualContentProvider contentProvider, String brickDivision, HAPManualManagerBrick manualBrickMan, HAPManagerApplicationBrick brickMan, HAPServiceParseEntity parseService) {
		this.m_contentProvider = contentProvider;
		this.m_brickDivision = brickDivision;
		this.m_manualBrickMan = manualBrickMan;
		this.m_brickManager = brickMan;
		this.m_parseService = parseService;
	}
	
	public HAPManualContentProvider getContentProvider() {    return this.m_contentProvider;    }
	
	public String getBrickDivision() {   return this.m_brickDivision;   }
	
	public HAPManualManagerBrick getManualBrickManager() {    return this.m_manualBrickMan;      }
	
	public HAPManagerApplicationBrick getBrickManager() {   return this.m_brickManager;     }

	public HAPServiceParseEntity getParseService() {      return this.m_parseService;       }
	
}

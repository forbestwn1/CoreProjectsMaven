package com.nosliw.core.application.division.manual.core.definition;

import com.nosliw.core.application.HAPManagerApplicationBrick;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContentProvider;

public class HAPManualDefinitionContextParse {

	private HAPManualContentProvider m_contentProvider;
	
	private String m_brickDivision;
	
	private HAPManualManagerBrick m_manualBrickMan;
	
	private HAPManagerApplicationBrick m_brickManager;
	
	public HAPManualDefinitionContextParse(HAPManualContentProvider contentProvider, String brickDivision, HAPManualManagerBrick manualBrickMan, HAPManagerApplicationBrick brickMan) {
		this.m_contentProvider = contentProvider;
		this.m_brickDivision = brickDivision;
		this.m_manualBrickMan = manualBrickMan;
		this.m_brickManager = brickMan;
	}
	
	public HAPManualContentProvider getContentProvider() {    return this.m_contentProvider;    }
	
	public String getBrickDivision() {   return this.m_brickDivision;   }
	
	public HAPManualManagerBrick getManualBrickManager() {    return this.m_manualBrickMan;      }
	
	public HAPManagerApplicationBrick getBrickManager() {   return this.m_brickManager;     }

}

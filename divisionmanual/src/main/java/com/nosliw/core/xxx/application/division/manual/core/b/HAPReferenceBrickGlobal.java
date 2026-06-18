package com.nosliw.core.xxx.application.division.manual.core.b;

import com.nosliw.core.application.HAPBundleForBrick;

public class HAPReferenceBrickGlobal {

	//which bundle this brick belong
	private HAPBundleForBrick m_bundle;
	//export name from this bundle
	private String m_exportName;

	public HAPReferenceBrickGlobal(HAPBundleForBrick bundle, String exportName) {
		this.m_bundle = bundle;
		this.m_exportName = exportName;
	}

	public HAPBundleForBrick getBundle() {    return this.m_bundle;    }
	public String getExportName() {   return this.m_exportName;   }
}

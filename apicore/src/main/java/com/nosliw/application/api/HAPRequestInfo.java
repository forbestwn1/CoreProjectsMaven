package com.nosliw.application.api;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

import jakarta.servlet.http.HttpServletRequest;

@HAPEntityWithAttribute
public class HAPRequestInfo extends HAPSerializableImp{

	@HAPAttribute
	public static String CLIENTID = "clientId";

	@HAPAttribute
	public static String COMMAND = "command";
	
	@HAPAttribute
	public static String PARMS = "parms";
	
	private String m_clientId;
	
	private String m_command;
	
	private String m_parms;
	
	public HAPRequestInfo() {}

	public HAPRequestInfo(String str) {
		String[] segs = str.split("&");
		for(String seg : segs) {
			String[] values = seg.split("=");
			if(values[0].equals(CLIENTID)) {
				this.m_clientId = values[1];
			}
			else if(values[0].equals(COMMAND)) {
				this.m_command = values[1];
			}
			else if(values[0].equals(PARMS)) {
				this.m_parms = values[1];
			}
		}
	}

	
	public HAPRequestInfo(HttpServletRequest request) {
		this.m_clientId = request.getParameter(CLIENTID);
		this.m_command = request.getParameter(COMMAND);
		this.m_parms = request.getParameter(PARMS);
	}
	
	public HAPRequestInfo(String clientId, String command, String data){
		this.m_clientId = clientId;
		this.m_command = command;
		this.m_parms = data;
	}
	
	public String getClientId(){  return this.m_clientId;  }
	
	public String getCommand(){  return this.m_command;  }
	
	public String getParms(){   return this.m_parms;   }
	
	@Override
	protected boolean buildObjectByJson(Object json){  
		JSONObject jsonObj = (JSONObject)json;
		this.m_clientId = (String)jsonObj.opt(CLIENTID);
		this.m_command = (String)jsonObj.opt(COMMAND);
		this.m_parms = (String)jsonObj.opt(PARMS);
		
		return true;  
	}
	
}

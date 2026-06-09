package com.nosliw.core.application.division.story.definition.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;
import com.nosliw.core.application.division.story.definition.element.ui.HAPStoryTunnel;

//data association between two collection of elements which is type of ElementWithEndPoint-
public class HAPStoryDataAssociation extends HAPSerializableImp{

	public static final String ENTITYPATH1 = "entityPath1";
	public static final String ENTITYPATH2 = "entityPath2";
	public static final String DIRECTION = "direction";
	public static final String TUNNEL = "tunnel";
	
	//path to collection of ElementWithEndPoint element
	public HAPStoryPath m_path1;
	
	//path to collection of ElementWithEndPoint element
	public HAPStoryPath m_path2;
	
	//data direction (in, out, both)
	private String m_direction;
	
	//mutiple tunnel within data association
	private List<HAPStoryTunnel> m_tunnels;
	
	public HAPStoryDataAssociation() {
		this.m_tunnels = new ArrayList<HAPStoryTunnel>();
	}
	
	public HAPStoryDataAssociation(HAPStoryPath path1, HAPStoryPath path2, String direction) {
		this();
		this.m_path1 = path1;
		this.m_path2 = path2;
		this.m_direction = direction;
	}

	public void addTunnel(HAPStoryTunnel tunnel) {      this.m_tunnels.add(tunnel);      }
	public List<HAPStoryTunnel> getTunnels(){     return this.m_tunnels;       }
	

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ENTITYPATH1, this.m_path1.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ENTITYPATH2, this.m_path2.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(DIRECTION, this.m_direction);
		jsonMap.put(TUNNEL, HAPManagerSerialize.getInstance().toStringValue(m_tunnels, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
	
		this.m_path1 = new HAPStoryPath();
		this.m_path1.buildObject(jsonObj.getJSONObject(ENTITYPATH1), HAPSerializationFormat.JSON);
		
		this.m_path2 = new HAPStoryPath();
		this.m_path2.buildObject(jsonObj.getJSONObject(ENTITYPATH1), HAPSerializationFormat.JSON);

		this.m_direction = jsonObj.getString(DIRECTION);
		
		JSONArray tunnelJsonArray = jsonObj.getJSONArray(TUNNEL);
		for(int i=0; i<tunnelJsonArray.length(); i++) {
			HAPStoryTunnel tunnel = new HAPStoryTunnel();
			tunnel.buildObject(tunnelJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
		}
		
		return true;  
	}

}

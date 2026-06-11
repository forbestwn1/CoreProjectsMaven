package com.nosliw.core.application.division.story.definition.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.division.story.definition.HAPStoryPath;

//data association between two collection of elements which is type of ElementWithEndPoint-
public class HAPStoryDataAssociation extends HAPSerializableImp{

	public static final String SOURCEBASEPATH = "sourceBasePath";
	public static final String SOURCESUBPATH = "sourceSubPath";
	public static final String TARGEBASEPATH = "targetBasePath";
	public static final String TARGETSUBPATH = "targetSubPath";
	public static final String DIRECTION = "direction";
	public static final String TUNNEL = "tunnel";
	
    //path to entity that match to value port
	public HAPStoryPath m_sourceBasePath;
	
	//path to collection of ElementWithEndPoint element
    public HAPPath m_sourceSubPath;
    
	public HAPStoryPath m_targetBasePath;
	
	//path to collection of ElementWithEndPoint element
    public HAPPath m_targetSubPath;

    //data direction (in, out, both)
	private String m_direction;
	
	//mutiple tunnel within data association
	private List<HAPStoryTunnel> m_tunnels;
	
	public HAPStoryDataAssociation() {
		this.m_tunnels = new ArrayList<HAPStoryTunnel>();
	}
	
	public HAPStoryDataAssociation(HAPStoryPath sourceBasePath, HAPPath sourceSubPath, HAPStoryPath targetBasePath, HAPPath targetSubPath, String direction) {
		this();
		this.m_sourceBasePath = sourceBasePath;
		this.m_sourceSubPath = sourceSubPath;
		this.m_targetBasePath = targetBasePath;
		this.m_targetSubPath = targetSubPath;
		this.m_direction = direction;
	}

	public HAPStoryPath getSourceBasePath() {    return this.m_sourceBasePath;    }
	public HAPStoryPath getTargetBasePath() {    return this.m_targetBasePath;    }
	
	public HAPPath getSourceSubPath() {     return this.m_sourceSubPath;         }
	public HAPPath getTargetSubPath() {     return this.m_targetSubPath;        }
	
	public void addTunnel(HAPStoryTunnel tunnel) {      this.m_tunnels.add(tunnel);      }
	public List<HAPStoryTunnel> getTunnels(){     return this.m_tunnels;       }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(SOURCEBASEPATH, this.m_sourceBasePath.toStringValue(HAPSerializationFormat.JSON));
		if(this.m_sourceSubPath!=null) {
			jsonMap.put(SOURCESUBPATH, this.m_sourceSubPath.toString());
		}
		jsonMap.put(TARGEBASEPATH, this.m_targetBasePath.toStringValue(HAPSerializationFormat.JSON));
		if(this.m_targetSubPath!=null) {
			jsonMap.put(TARGETSUBPATH, this.m_targetSubPath.toString());
		}
		jsonMap.put(DIRECTION, this.m_direction);
		jsonMap.put(TUNNEL, HAPManagerSerialize.getInstance().toStringValue(m_tunnels, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
	
		this.m_sourceBasePath = new HAPStoryPath();
		this.m_sourceBasePath.buildObject(jsonObj.getJSONObject(SOURCEBASEPATH), HAPSerializationFormat.JSON);
		
		this.m_sourceSubPath = new HAPPath((String)jsonObj.opt(HAPStoryDataAssociation.SOURCESUBPATH));
		
		this.m_targetBasePath = new HAPStoryPath();
		this.m_targetBasePath.buildObject(jsonObj.getJSONObject(SOURCEBASEPATH), HAPSerializationFormat.JSON);

		this.m_targetSubPath = new HAPPath((String)jsonObj.opt(HAPStoryDataAssociation.TARGETSUBPATH));
		
		this.m_direction = jsonObj.getString(DIRECTION);
		
		JSONArray tunnelJsonArray = jsonObj.getJSONArray(TUNNEL);
		for(int i=0; i<tunnelJsonArray.length(); i++) {
			HAPStoryTunnel tunnel = new HAPStoryTunnel();
			tunnel.buildObject(tunnelJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			this.addTunnel(tunnel);
		}
		
		return true;  
	}

}

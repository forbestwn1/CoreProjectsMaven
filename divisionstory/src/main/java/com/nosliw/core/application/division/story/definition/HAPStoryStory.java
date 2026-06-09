package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;

//static information
//no transaction
@HAPEntityWithAttribute
public class HAPStoryStory extends HAPEntityInfoImp{

	public static final String ALIAS_ROOT = "__root___";
	
	@HAPAttribute
	public static final String IDINDEX = "idIndex";
	
	@HAPAttribute
	public static final String ELEMENT = "element";
	
	@HAPAttribute
	public static final String ELEMENTBYALIAS = "elementByAlias";
	
	@HAPAttribute
	public static final String ALIASBYELEMENTID = "aliasByElementId";
	
	private int m_index = 0;
	
	//all elements
	private Map<HAPStoryIdElement, HAPStoryElement> m_elements;
	
	//alias
	private Map<String, HAPStoryIdElement> m_elementByAliase;
	private Map<HAPStoryIdElement, String> m_aliaseByElementId;
	
	
	//all runnable(task)
	private Map<String, HAPStoryRunnable> m_runnables;
	
	//alias for runnable
	private Map<String, String> m_runnableByAliase;
	private Map<String, String> m_aliaseByRunnableId;
	
	public HAPStoryStory() {
		this.m_elements = new LinkedHashMap<HAPStoryIdElement, HAPStoryElement>();
		this.m_elementByAliase = new LinkedHashMap<String, HAPStoryIdElement>();
		this.m_aliaseByElementId = new LinkedHashMap<HAPStoryIdElement, String>();
		
		this.m_runnables = new LinkedHashMap<String, HAPStoryRunnable>();
		this.m_runnableByAliase = new LinkedHashMap<String, String>();		this.m_aliaseByRunnableId = new LinkedHashMap<String, String>();
	}
	
	public int getIndex() {     return this.m_index;     }
	public void setIndex(int index) {     this.m_index = index;      }

	public Set<HAPStoryRunnable> getRunnables(){    return new HashSet<>(this.m_runnables.values());      }
	
	public HAPStoryRunnable getRunnable(String runnableId) {
		return this.m_runnables.get(runnableId);
	}

	public HAPStoryAlias getRunnableAlias(String runnableId) {
		for(String alias : this.m_runnableByAliase.keySet()) {
			if(runnableId.equals(this.m_runnableByAliase.get(alias))) {
				return new HAPStoryAlias(alias);
			}
		}
		return null;
	}
	

	public HAPStoryRunnable addRunnable(HAPStoryRunnable runnable, HAPStoryAlias alias) {
		HAPStoryRunnable out = this.addRunnable(runnable);
		this.setRunnableAlias(out.getId(), alias);
		return out;
	}
	
	public HAPStoryRunnable addRunnable(HAPStoryRunnable runnable) {		
		HAPStoryRunnable out = runnable;
		if(out.getId()==null) {
			this.buildRunnableId(out);
		}
		this.m_runnables.put(out.getId(), out);
		return out;
	}
	
	public void setRunnableAlias(String runnableId, HAPStoryAlias alias) {
		//set alias
		if(alias!=null) {
			this.m_runnableByAliase.put(alias.getName(), runnableId);
			this.m_aliaseByRunnableId.put(runnableId, alias.getName());
		}
	}

	public HAPStoryRunnable deleteRunnable(String runnableId) {
		HAPStoryRunnable out = this.m_runnables.remove(runnableId);
		String alias = this.m_aliaseByRunnableId.remove(runnableId);
		this.m_runnableByAliase.remove(alias);
		return out;
	}

	
	public HAPStoryElement getElement(HAPStoryReferenceElement eleRef) {
		HAPStoryElement out = null;
		
		String refType = eleRef.getEntityOrReferenceType();
		if(refType.equals(HAPConstantShared.STORY_ELEMENT_REFERENCE_ID)) {
			HAPStoryIdElement eleId = (HAPStoryIdElement)eleRef;
			out = this.m_elements.get(eleId);
		}
		else if(refType.equals(HAPConstantShared.STORY_ELEMENT_REFERENCE_ALIAS)) {
			HAPStoryAlias eleAlias = (HAPStoryAlias)eleRef;
			out = this.m_elements.get(this.m_elementByAliase.get(eleAlias.getName()));
		}
		return out;
	}
	
	public HAPStoryIdElement getElementId(String alias) {	return this.m_elementByAliase.get(alias);	}
	
	public HAPStoryAlias getElementAlias(HAPStoryIdElement eleId) {
		for(String alias : this.m_elementByAliase.keySet()) {
			if(eleId.equals(this.m_elementByAliase.get(alias))) {
				return new HAPStoryAlias(alias);
			}
		}
		return null;
	}
	
	public HAPStoryElement addElement(HAPStoryElement element) {
		HAPStoryElement out = element;
		if(out.getElementId()==null) {
			this.buildElementId(out);
		}
		this.m_elements.put(out.getElementId(), out);
		return out;
	}
	
	public void setElementAlias(HAPStoryIdElement eleId, HAPStoryAlias alias) {
		//set alias
		if(alias!=null) {
			this.m_elementByAliase.put(alias.getName(), eleId);
			this.m_aliaseByElementId.put(eleId, alias.getName());
		}
	}
	
	public HAPStoryElement addElement(HAPStoryElement element, HAPStoryAlias alias) {
		//add element
		HAPStoryElement out = this.addElement(element);
		
		//set alias
		setElementAlias(out.getElementId(), alias);
		
		return out;
	}
	
	public HAPStoryElement deleteElement(HAPStoryIdElement eleId) {
		HAPStoryElement out = this.m_elements.remove(eleId);
		String alias = this.m_aliaseByElementId.remove(eleId);
		this.m_elementByAliase.remove(alias);
		return out;
	}
	
	public void buildElementId(HAPStoryElement element) {
		this.m_index++;
		element.setElementId(new HAPStoryIdElement(element.getElementType().getKey() + this.m_index));
	}

	public void buildRunnableId(HAPStoryRunnable runnable) {
		this.m_index++;
		runnable.setId(runnable.getRunnableType()+this.m_index);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(IDINDEX, this.m_index+"");
		typeJsonMap.put(IDINDEX, Integer.class);
		
		List<String> listElements = new ArrayList<String>();
		for(HAPStoryIdElement eleId : this.m_elements.keySet()) {
			listElements.add(this.m_elements.get(eleId).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(ELEMENT, HAPUtilityJson.buildArrayJson(listElements.toArray(new String[0])));
		
		Map<String, String> mapElementByAliase = new LinkedHashMap<String, String>();
		for(String alias : this.m_elementByAliase.keySet()) {
			mapElementByAliase.put(alias, this.m_elementByAliase.get(alias).getKey());
		}
		jsonMap.put(ELEMENTBYALIAS, HAPUtilityJson.buildMapJson(mapElementByAliase));
	}

}

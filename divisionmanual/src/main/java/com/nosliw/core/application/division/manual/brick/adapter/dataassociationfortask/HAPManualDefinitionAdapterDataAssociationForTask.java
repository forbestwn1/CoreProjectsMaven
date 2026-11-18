package com.nosliw.core.application.division.manual.brick.adapter.dataassociationfortask;

import com.nosliw.core.application.common.dataassociation.definition.HAPDefinitionDataAssociationForTask;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPManualDefinitionAdapterDataAssociationForTask extends HAPManualDefinitionBrick{

	public static final String DEFINITION = "definition";

	public HAPManualDefinitionAdapterDataAssociationForTask() {
		super(HAPEnumBrickType.DATAASSOCIATIONFORTASK_100);
	}
	
	public void setDataAssciation(HAPDefinitionDataAssociationForTask dataAssciation) {    this.setAttributeValueWithValue(DEFINITION, dataAssciation);    }
	public HAPDefinitionDataAssociationForTask getDataAssociation() {   return (HAPDefinitionDataAssociationForTask)this.getAttributeValueOfValue(DEFINITION);     }
	
}

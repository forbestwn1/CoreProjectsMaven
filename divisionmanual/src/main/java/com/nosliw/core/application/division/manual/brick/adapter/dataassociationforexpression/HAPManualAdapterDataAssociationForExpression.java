package com.nosliw.core.application.division.manual.brick.adapter.dataassociationforexpression;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.adapter.dataassociationforexpression.HAPAdapterDataAssociationForExpression;
import com.nosliw.core.application.common.dataassociation.HAPDataAssociationForExpression;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;

public class HAPManualAdapterDataAssociationForExpression extends HAPManualBrickImp implements HAPAdapterDataAssociationForExpression{

	public HAPManualAdapterDataAssociationForExpression() {
		super(HAPEnumBrickType.DATAASSOCIATIONFOREXPRESSION_100);
	}

	public void setDataAssciation(HAPDataAssociationForExpression dataAssciation) {    this.setAttributeValueWithValue(DATAASSOCIATION, dataAssciation);    }
	@Override
	public HAPDataAssociationForExpression getDataAssociation() {   return (HAPDataAssociationForExpression)this.getAttributeValueOfValue(DATAASSOCIATION);     }
}

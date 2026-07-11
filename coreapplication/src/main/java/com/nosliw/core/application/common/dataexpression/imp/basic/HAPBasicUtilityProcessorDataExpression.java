package com.nosliw.core.application.common.dataexpression.imp.basic;

import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForExecute;
import com.nosliw.core.application.common.constant.HAPDefinitionConstant;
import com.nosliw.core.application.common.structure.HAPElementStructure;
import com.nosliw.core.application.common.structure.HAPElementStructureLeafData;
import com.nosliw.core.application.common.structure.reference.HAPConfigureResolveElementReference;
import com.nosliw.core.application.common.withvariable.HAPContainerVariableInfo;
import com.nosliw.core.application.resource.HAPUtilityBrickResource;
import com.nosliw.core.application.valueport.HAPUtilityResovleElement;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.expression.HAPOperand;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandReference;
import com.nosliw.core.data.expression.imp.basic.HAPBasicExpressionData;
import com.nosliw.core.data.expression.imp.basic.HAPBasicHandlerOperand;
import com.nosliw.core.data.expression.imp.basic.HAPBasicOperandConstant;
import com.nosliw.core.data.expression.imp.basic.HAPBasicOperandReference;
import com.nosliw.core.data.expression.imp.basic.HAPBasicOperandVariable;
import com.nosliw.core.data.expression.imp.basic.HAPBasicUtilityOperand;
import com.nosliw.core.data.expression.imp.basic.HAPBasicWrapperOperand;
import com.nosliw.core.resource.HAPFactoryResourceId;
import com.nosliw.core.resource.HAPManagerResource;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.xxx.application.valueport.HAPInfoElementResolve;

public class HAPBasicUtilityProcessorDataExpression {
	
	
	public static void processConstant(HAPBasicExpressionData dataExpression, Map<String, HAPDefinitionConstant> constantsDef) {
		HAPBasicUtilityOperand.traverseAllOperand(dataExpression.getOperandWrapper(), new HAPBasicHandlerOperand(){
			@Override
			public boolean processOperand(HAPBasicWrapperOperand operandWrapper, Object data) {
				String opType = operandWrapper.getOperandType();
				if(opType.equals(HAPConstantShared.EXPRESSION_OPERAND_CONSTANT)){
					HAPBasicOperandConstant constantOperand = (HAPBasicOperandConstant)operandWrapper.getOperand();
					if(constantOperand.getData()==null) {
						HAPData constantData = constantsDef.get(constantOperand.getName()).getData();
						constantOperand.setData(constantData);
					}
				}
				return true;
			}
		}, null);
	}
	
	public static void resolveReferenceVariableMapping(HAPBasicExpressionData dataExpression, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		HAPBasicUtilityOperand.traverseAllOperand(dataExpression.getOperandWrapper(), new HAPBasicHandlerOperand(){
			@Override
			public boolean processOperand(HAPBasicWrapperOperand operandWrapper, Object data) {
				String opType = operandWrapper.getOperandType();
				if(opType.equals(HAPConstantShared.EXPRESSION_OPERAND_REFERENCE)){
					HAPBasicOperandReference referenceOperand = (HAPBasicOperandReference)operandWrapper.getOperand();
					HAPDefinitionOperandReference referenceOperandDef = (HAPDefinitionOperandReference)referenceOperand.getOperandDefinition();

					HAPResourceId refResourceId = HAPFactoryResourceId.newInstance(referenceOperandDef.getReference());
					referenceOperand.setResourceId(refResourceId);
					HAPBundleForExecute bundleExe = HAPUtilityBrickResource.getResourceData(refResourceId, resourceMan, runtimeInfo).getBundle();
					HAPBrick referedResourceBrick = bundleExe.getBrick();
					
					Map<String, HAPOperand> referenceMapping = referenceOperand.getMapping();
					for(String varName : referenceMapping.keySet()) {
						HAPInfoElementResolve varInfo = HAPUtilityResovleElement.resolveNameFromExternal(varName, HAPConstantShared.IO_DIRECTION_IN, referedResourceBrick, null, bundleExe.getValueStructureDomain());
						HAPElementStructure eleStructure = varInfo.getElementStructure();
						String eleType = eleStructure.getType();
						if(eleType.equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA)) {
							HAPElementStructureLeafData dataEle = (HAPElementStructureLeafData)eleStructure;
							referenceOperand.addResolvedVariable(varName, varInfo.getElementId(), dataEle.getCriteria());
						} else {
							throw new RuntimeException();
						}
					}
				}
				return true;
			}
		}, null);
	}
	
	public static void resolveVariable(HAPBasicExpressionData dataExpression, HAPContainerVariableInfo varInfoContainer, HAPConfigureResolveElementReference resolveConfigure) {
		HAPBasicUtilityOperand.traverseAllOperand(dataExpression.getOperandWrapper(), new HAPBasicHandlerOperand() {
			@Override
			public boolean processOperand(HAPBasicWrapperOperand operand, Object data) {
				String opType = operand.getOperandType();
				if(opType.equals(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE)){
					HAPBasicOperandVariable variableOperand = (HAPBasicOperandVariable)operand.getOperand();
					String variableKey = varInfoContainer.addVariable(variableOperand.getVariableName(), HAPConstantShared.IO_DIRECTION_OUT, resolveConfigure);
					variableOperand.setVariableKey(variableKey);
				}
				return true;
			}
		}, null);
	}
}

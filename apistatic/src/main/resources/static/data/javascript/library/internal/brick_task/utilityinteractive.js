//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONCONSTANT;
var node_createServiceRequestInfoSequence;
var node_utilityNamedVariable;

//*******************************************   Start Node Definition  ************************************** 	

var node_interactiveUtility = function(){

	var loc_getRequestValuesFromValuePort = function(valuePortContainer, valuePortGroupType, handlers, request){
		var valueStructures = valuePortContainer.getValueStructuresByGroupTypeAndValuePortName(valuePortGroupType, node_COMMONCONSTANT.VALUEPORT_TYPE_INTERACTIVE_REQUEST);
		for(var i in valueStructures){
			return valueStructures[i].getAllElementsValuesRequest(handlers, request);
		}
	};

	var loc_out = {
		
		getResultValuePortNameByResultName : function(resultName){
			return node_COMMONCONSTANT.VALUEPORT_NAME_INTERACT_RESULT + node_COMMONCONSTANT.SEPERATOR_PREFIX + resultName;
		},
		
		getGetTaskRequestValuesFromValuePortRequest : function(valuePortContainer, handlers, request){
			return loc_getRequestValuesFromValuePort(valuePortContainer, node_COMMONCONSTANT.VALUEPORTGROUP_TYPE_INTERACTIVETASK, handlers, request);
		},
	
		getGetExpressionRequestValuesFromValuePortRequest : function(valuePortContainer, handlers, request){
			return loc_getRequestValuesFromValuePort(valuePortContainer, node_COMMONCONSTANT.VALUEPORTGROUP_TYPE_INTERACTIVEEXPRESSION, handlers, request);
		},
	
		getSetTaskResultToValuePortRequest : function(taskResult, valuePortContainer, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			
			var resultName = taskResult.resultName;
			var resultValue = taskResult.resultValue;
			
			out.addRequest(node_utilityNamedVariable.setValuesPortValueRequest(valuePortContainer, node_COMMONCONSTANT.VALUEPORTGROUP_TYPE_INTERACTIVETASK, this.getResultValuePortNameByResultName(resultName), resultValue));
			return out;			
		},
		
		getSetExpressionResultToValuePortRequest : function(expressionResult, valuePortContainer, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(node_utilityNamedVariable.setValuePortValueRequest(
				valuePortContainer, 
				node_COMMONCONSTANT.VALUEPORTGROUP_TYPE_INTERACTIVEEXPRESSION, 
				node_COMMONCONSTANT.VALUEPORT_TYPE_INTERACTIVE_RESULT,
				node_COMMONCONSTANT.NAME_ROOT_RESULT, 
				expressionResult));
			return out;
		}
	};
	
	return loc_out;
}();

//*******************************************   End Node Definition  ************************************** 	
//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("valueport.utilityNamedVariable", function(){node_utilityNamedVariable = this.getData();});

//Register Node by Name
packageObj.createChildNode("interactiveUtility", node_interactiveUtility); 

})(packageObj);

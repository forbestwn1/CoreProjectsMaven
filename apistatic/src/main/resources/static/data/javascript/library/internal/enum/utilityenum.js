//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_basicUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_enumUtility = function(){
	
	var loc_getEnumDataSetFromCodeTableRequest = function(codeTableId, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		var gatewayParm = {};
		gatewayParm[node_COMMONATRIBUTECONSTANT.GATEWAYCODETABLE_PARMS_GETCODETABLE_ID] = codeTableId;
		out.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
				node_COMMONCONSTANT.GATEWAY_CODETABLE, 
				node_COMMONATRIBUTECONSTANT.GATEWAYCODETABLE_COMMAND_GETCODETABLE, 
				gatewayParm,
				{
					success : function(requestInfo, codeTable){
						return codeTable[node_COMMONATRIBUTECONSTANT.CODETABLE_DATASET];
					}
				}
		));
		return out;
	};
	
	var loc_getEnumDataSetFromEnumRuleRequest = function(enumRuleDef, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);

		var enumDataSet = enumRuleDef[node_COMMONATRIBUTECONSTANT.DATARULEENUM_DATASET];

		if(enumDataSet==undefined){
			//for enum code, try get enum data set
			out.addRequest(loc_getEnumDataSetFromCodeTableRequest(enumRuleDef[node_COMMONATRIBUTECONSTANT.DATARULEENUM_ENUMCODE], {
				success : function(request, dataSet){
					enumDataSet = dataSet;
				}
			}));
		}

		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
			return enumDataSet;
		}));
		
		return out;
	};
	
	var loc_out = {

		getEnumDataSetFromCodeTableReqeust : function(codeTableId, handlers, request){
			loc_getEnumDataSetFromCodeTableRequest(codeTableId, handlers, request);
		},
		
		getEnumDataSetFromEnumRuleRequest : function(enumRuleDef, handlers, request){
			return loc_getEnumDataSetFromEnumRuleRequest(enumRuleDef, handlers, request);
		}
	};

	return loc_out;
}();

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("enumUtility", node_enumUtility); 

})(packageObj);

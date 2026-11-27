//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSimple;
var node_createDataAssociation;
var node_taskExecuteUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createDataAssociationForExpressionAdapterPlugin = function(){
	
	var loc_out = {

		getNewAdapterRequest : function(adapterDefinition, handlers, request){
			return node_createServiceRequestInfoSimple({}, function(request){
				return loc_createDataAssociationForExpressionAdapter(adapterDefinition);
			}, handlers, request);
		},
	};

	return loc_out;
};


var loc_createDataAssociationForExpressionAdapter = function(dataAssociationExpression){
	
	var loc_dataAssociationForExpression;
	var loc_dataAssociationIn;
	var loc_dataAssociationOut;

	var loc_init = function(dataAssociationExpression){
		loc_dataAssociationForExpression = dataAssociationExpression.getAttributeValue(node_COMMONATRIBUTECONSTANT.ADAPTERDATAASSOCIATIONFOREXPRESSION_DATAASSOCIATION);
		loc_dataAssociationIn = node_createDataAssociation(loc_dataAssociationForExpression[node_COMMONATRIBUTECONSTANT.DATAASSOCIATIONFOREXPRESSION_IN]);
		loc_dataAssociationOut = node_createDataAssociation(loc_dataAssociationForExpression[node_COMMONATRIBUTECONSTANT.DATAASSOCIATIONFOREXPRESSION_OUT]);
	};
	
	var loc_out = {
		
		getExecuteTaskRequest : function(baseEntityCore, taskSetup, handlers, request){
			
			var onInitTaskRequest = function(handlers, request){
				return loc_dataAssociationIn.getExecuteRequest(baseEntityCore, handlers, request);
			};
			
			var onFinishTaskRequest = function(taskResult, handlers, request){
				return loc_dataAssociationOut.getExecuteRequest(baseEntityCore, handlers, request);
			};
			
			return node_taskExecuteUtility.getExecuteEntityTaskRequest(baseEntityCore, taskSetup, onInitTaskRequest, onFinishTaskRequest, handlers, request);
		}
		
	};
	
	loc_init(dataAssociationExpression);
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("iovalue.createDataAssociation", function(){node_createDataAssociation = this.getData();});
nosliw.registerSetNodeDataEvent("task.taskExecuteUtility", function(){node_taskExecuteUtility = this.getData();});


//Register Node by Name
packageObj.createChildNode("createDataAssociationForExpressionAdapterPlugin", node_createDataAssociationForExpressionAdapterPlugin); 

})(packageObj);

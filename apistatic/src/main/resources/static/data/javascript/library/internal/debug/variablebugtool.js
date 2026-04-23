//get/create package
var packageObj = library.getChildPackage();    


(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_getComponentLifecycleInterface;
	var node_getComponentInterface;
	var node_getEntityTreeNodeInterface;
	var node_createServiceRequestInfoSimple;
	var node_requestServiceProcessor;
	var node_getComponentManagementInterface;
	var node_componentUtility;
	var node_getStateMachineDefinition;
	var node_getEntityObjectInterface;
	var node_complexEntityUtility;
	var node_valueInVarOperationServiceUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createVariableDebuger = function(variable, name){
	
	var loc_variable = variable;

	var loc_name = name;
	
	var loc_view;
	
	var loc_valueView;
	
	var loc_updateDataDisplay = function(){
		loc_variable.executeDataOperationRequest(node_valueInVarOperationServiceUtility.createGetOperationService(), {
			success : function(request, data){
				var value;
    		    value = JSON.stringify(data, null, 4);
				loc_valueView.text(value);
			}	
		});
	};
	
	
    var loc_init =function(){
		
    	loc_view = $('<div/>');
		loc_view.append($('<br>'+loc_name+':<br>'));
        loc_valueView = $('<textarea rows="6" cols="150" style="resize: none; border:solid 1px;" data-role="none"></textarea>');
        	
		loc_valueView.bind('change', function(){
			var value = loc_valueView.val();
			if(value==undefined || value==""){}
			else {
				value = JSON.parse(varInfo.view.val());
			}
			var operationService = node_valueInVarOperationServiceUtility.createSetOperationService("", value);
			loc_variable.executeDataOperationRequest(operationService);
		});
		
    	loc_variable.registerDataChangeEventListener(undefined, function(eventName, eventData){
			loc_updateDataDisplay();
		});
		
		loc_updateDataDisplay();
	};
	
	var loc_out = {
		
		getVariable : function(){
			return loc_variable;
		},
		
		getView : function(){
			return loc_view;
		}
		
	};
	
	loc_init();
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentLifecycleInterface", function(){node_getComponentLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentInterface", function(){node_getComponentInterface = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.getEntityTreeNodeInterface", function(){node_getEntityTreeNodeInterface = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentManagementInterface", function(){node_getComponentManagementInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.componentUtility", function(){node_componentUtility = this.getData();});
nosliw.registerSetNodeDataEvent("component.getStateMachineDefinition", function(){node_getStateMachineDefinition = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.getEntityObjectInterface", function(){node_getEntityObjectInterface = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.complexEntityUtility", function(){  node_complexEntityUtility = this.getData();});

nosliw.registerSetNodeDataEvent("variable.context.createContextVariablesGroup", function(){  node_createContextVariablesGroup = this.getData();});
nosliw.registerSetNodeDataEvent("variable.context.createContextVariableInfo", function(){  node_createContextVariableInfo = this.getData();});
nosliw.registerSetNodeDataEvent("variable.valueinvar.utility", function(){  node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("variable.valueinvar.operation.valueInVarOperationServiceUtility", function(){node_valueInVarOperationServiceUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createVariableDebuger", node_createVariableDebuger); 

})(packageObj);

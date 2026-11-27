//get/create package
var packageObj = library.getChildPackage();    


(function(packageObj){
	//get used node
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

nosliw.registerSetNodeDataEvent("variable.valueinvar.operation.valueInVarOperationServiceUtility", function(){node_valueInVarOperationServiceUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createVariableDebuger", node_createVariableDebuger); 

})(packageObj);

//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_getEntityObjectInterface;
var node_utilityNamedVariable;
	var node_createRuntimeEnvironment;

//*******************************************   Start Node Definition  ************************************** 	

var node_taskUtility = function(){


  var loc_out = {
	  
	createTaskRuntimeEnv : function(values){
		return node_createRuntimeEnvironment(nosliw.runtime.runtimeEnv, values);
	},
	  
    createTaskFunctionWithSettingValuePortValues : function(valuePortGroupType, valuePortName, setValueByName){
        		
		return function(coreEntity, handlers, request){
			//set event data to value port
			var valuePortContainer = node_getEntityObjectInterface(coreEntity).getExternalValuePortContainer();
							
			return node_utilityNamedVariable.setValuesPortValueRequest(
				valuePortContainer,
				valuePortGroupType,
				valuePortName,
				setValueByName,
				handlers, request);
		};
	},

    createTaskFunctionWithSettingValuePortValuesByGroupName : function(valuePortGroupName, valuePortName, setValueByName){
        		
		return function(coreEntity, handlers, request){
			//set event data to value port
			var valuePortContainer = node_getEntityObjectInterface(coreEntity).getExternalValuePortContainer();
							
			return node_utilityNamedVariable.setValuesPortValueByGroupNameRequest(
				valuePortContainer,
				valuePortGroupName,
				valuePortName,
				setValueByName,
				handlers, request);
		};
	}
  };

  return loc_out;
}();

//*******************************************   End Node Definition  ************************************** 	
//populate dependency node data
nosliw.registerSetNodeDataEvent("complexentity.getEntityObjectInterface", function(){node_getEntityObjectInterface = this.getData();});
nosliw.registerSetNodeDataEvent("valueport.utilityNamedVariable", function(){node_utilityNamedVariable = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.entity.createRuntimeEnvironment", function(){node_createRuntimeEnvironment = this.getData();});

//Register Node by Name
packageObj.createChildNode("taskUtility", node_taskUtility); 

})(packageObj);

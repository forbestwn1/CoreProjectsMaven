//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_makeObjectWithApplicationInterface;
	var node_interactiveUtility;
	var node_createTaskCore;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createDataServiceEntityPlugin = function(){
	
	var loc_out = {

		getCreateEntityCoreRequest : function(entityDef, internalValuePortContainerId, externalValuePortContainerId, bundleCore, configure, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				return loc_createDataServiceProvider(entityDef, configure);
			}, handlers, request);
		},
	};

	return loc_out;
};


var loc_createDataServiceProvider = function(serviceProvider, configure){
	
	var loc_serviceProvider = serviceProvider;
	
	var loc_configure = configure;
	
	var loc_envInterface = {};

	var loc_taskCore;
	
	var loc_taskResult;


	var loc_getExecuteTaskRequest = function(parms, handlers, request){

		var result = {
		    "resultName": "success",
		    "resultValue": {
		        "outputInService1": {
		            "dataTypeId": "test.string;1.0.0",
		            "valueFormat": "JSON",
		            "value": "default value of parm111111",
		            "info": {}
		        }
		    }
		};

		/*
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
			return result;
		}));
		return out;
		*/

		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		out.addRequest(nosliw.runtime.getDataService().getExecuteDataServiceRequest(loc_serviceProvider.getAttributeValue(node_COMMONATRIBUTECONSTANT.BLOCKSERVICEPROVIDER_SERVICEID)[node_COMMONATRIBUTECONSTANT.KEYSERVICE_ID], parms, {
			success: function(rquest, resultValue){
				return resultValue;
			}
		}));
		return out;
		
	};
	
	var loc_facadeTaskCore = {
		//return a task
		getTaskCore : function(){
			return loc_taskCore;
		},
	};

	var loc_init = function(serviceProvider, configure){
		loc_taskCore = node_createTaskCore(loc_out, loc_out);;
	};

	var loc_out = {
		
		setEnvironmentInterface : function(envInterface){		loc_envInterface = envInterface;	},
		
		getTaskExecuteRequest : function(taskRuntimeEnv, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
    		var valuePortContainer = loc_envInterface[node_CONSTANT.INTERFACE_ENTITY].getInternalValuePortContainer();
			
			out.addRequest(node_interactiveUtility.getGetTaskRequestValuesFromValuePortRequest(valuePortContainer, {
				success : function(request, parmsValue){
					return loc_getExecuteTaskRequest(parmsValue, {
						success : function(request, serviceResult){
							return node_interactiveUtility.getSetTaskResultToValuePortRequest(serviceResult, valuePortContainer, {
								success : function(){
									loc_taskResult = serviceResult;
									return serviceResult;
								}
							});
						}
					});
				}
			}));
			
			return out;
		},
		
		getTaskResult : function(){   return loc_taskResult;    }
		
	};

    loc_init(serviceProvider, configure);
	loc_out = node_makeObjectWithApplicationInterface(loc_out, node_CONSTANT.INTERFACE_APPLICATIONENTITY_FACADE_TASK, loc_facadeTaskCore);
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.interfacedef.makeObjectWithApplicationInterface", function(){node_makeObjectWithApplicationInterface = this.getData();});
nosliw.registerSetNodeDataEvent("task.interactiveUtility", function(){	node_interactiveUtility = this.getData();	});
nosliw.registerSetNodeDataEvent("task.createTaskCore", function(){node_createTaskCore = this.getData();});


//Register Node by Name
packageObj.createChildNode("createDataServiceEntityPlugin", node_createDataServiceEntityPlugin); 

})(packageObj);

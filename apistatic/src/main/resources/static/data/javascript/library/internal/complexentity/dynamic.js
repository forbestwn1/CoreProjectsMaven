
//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
var node_COMMONATRIBUTECONSTANT;
var node_makeObjectWithType;
var node_createConfigure;
	var node_basicUtility;
//*******************************************   Start Node Definition  ************************************** 	

//bundle is executable resource unit
var node_createDynamicCore = function(dynamicDef, bundleCore, configure){

	var loc_dynamicDef;
	var loc_bundleCore;
	
	var loc_dynamicInput;
	var loc_currentTaskEntityCore;

	var loc_configure;
	var loc_configureValue;
	
	var loc_parentView;
	var loc_outputView;
	
	var loc_init = function(dynamicDef, bundleCore, configure){
		loc_dynamicDef = dynamicDef;
		loc_bundleCore = bundleCore;
		loc_configure = configure;
		loc_configureValue = node_createConfigure(loc_configure).getConfigureValue();

	};

	var loc_updateTaskResultView = function(taskResult){
		var resultStr = node_basicUtility.stringify(taskResult);
		console.log(resultStr);
		loc_outputView.val(resultStr);
	};

	var loc_out = {

		setEnvironmentInterface : function(envInterface){	loc_envInterface = envInterface;	},

		getDynamicInput : function(){
			return loc_bundleCore.getDynamicInputContainer().getDynamicInput(loc_dynamicDef[node_COMMONATRIBUTECONSTANT.VALUEOFDYNAMIC_DYNAMICID]);
		},

		getEntityInitRequest : function(handlers, request){
//			return loc_bundleCore.getDynamicInputContainer().prepareDyanmicInputRequest(loc_dynamicDef[node_COMMONATRIBUTECONSTANT.VALUEOFDYNAMIC_DYNAMICID], handlers, request);
		},

		updateView : function(view){    
/*
			loc_parentView = view;
			
			var entityCore = this.getDynamicInput().getDynamicCoreEntity();
			var taskFacade = node_getApplicationInterface(entityCore, node_CONSTANT.INTERFACE_APPLICATIONENTITY_FACADE_TASK);
			if(taskFacade!=undefined){
				var taskTrigueView = $('<button>Execute Task</button>');
				taskTrigueView.click(function() {
					var out = node_createServiceRequestInfoSequence();
					out.addRequest(node_taskExecuteUtility.getExecuteEntityTaskWithAdapterRequest(loc_out, undefined, undefined, {
						success : function(request, taskResult){
							loc_updateTaskResultView(taskResult);
							return taskResult;
						}
					}));
					node_requestServiceProcessor.processRequest(out);
				});
				loc_parentView.append(taskTrigueView);
	
				loc_outputView =  $('<textarea rows="5" cols="200"></textarea>');
				loc_parentView.append(loc_outputView);
				
			}
*/			
		},
		







/*
		getValuePortContainerProviderEntityCore : function(){    return loc_getDynamicInputEntityCore();      },

		getDataType1: function(){    return  "dyanmic";   },

		setDynamicInput : function(dynamicInput){   loc_dynamicInput = dynamicInput;       },

		getExecuteTaskWithAdapterRequest : function(adapterName, taskSetup, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			
			var taskFactory = node_getApplicationInterface(loc_dynamicInput.getDynamicTaskFactoryEntity(), node_CONSTANT.INTERFACE_APPLICATIONENTITY_FACADE_FACTORY);
			out.addRequest(taskFactory.getCreateTaskEntityRequest({
				success : function(request, entityCore){
					loc_currentTaskEntityCore = entityCore;
					var taskCore = node_getApplicationInterface(entityCore, node_CONSTANT.INTERFACE_APPLICATIONENTITY_FACADE_TASK).getTaskCore();
					taskCore.addTaskSetup(loc_dynamicInput.getTaskSetup());
					return node_taskExecuteUtility.getExecuteEntityTaskWithAdapterRequest(loc_out, adapterName, taskSetup);
				}
			}));
			return out;
		},

		getPreInitRequest : function(handlers, request){   },

*/		
	};
	
	loc_init(dynamicDef, bundleCore, configure);
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_DYNAMIC);

	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.interfacedef.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("configure.createConfigure", function(){node_createConfigure = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});


//Register Node by Name
packageObj.createChildNode("createDynamicCore", node_createDynamicCore); 

})(packageObj);

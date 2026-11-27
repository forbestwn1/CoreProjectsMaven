//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
var node_getObjectType;
var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoError;
	var node_createStateMachine;
	var node_createStateMachineDef;
	var node_SMCommandInfo;
	var node_SMTransitDefinition;
var node_createComponentState;
	var node_createStateMachineTask;

//*******************************************   Start Node Definition  ************************************** 	

var INTERFACENAME = "componentLifecycle";

/**
 * INIT : 
 * 		this is the init state of statemachine, so component should be initilized before enter this state.
 * 		there is no callback for entering this state 
 * 		during initialization, all the one time task should be done here 
 * ACTIVE : component is working
 * SUSPENDED: component working paused
 * DEAD: component dead
 */



var node_createLifecycleTask = function(next, rootComponent, request){
	var lifecycleEntity = rootComponent.getLifecycleEntity();

	var currentStatus = lifecycleEntity.getStateMachine().getCurrentState();
	//interprect next to array nexts
	var nexts = node_getStateMachineDefinition().processNext(currentStatus, next);
	//process init-active 
	if(currentStatus==node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT && nexts[0]==node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE){
		var backupStateData = rootComponent.getBackupState().getStateValue(request);
		if(backupStateData!=undefined){
			nexts = [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED].concat(nexts);
		}
	}
	
	var descendants = lifecycleEntity.getAllDescendants();
	return node_createStateMachineTask(nexts, descendants, {
		transitSuccess : function(transit){
			if(!(transit.from==node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE&&transit.to==node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED)){
				rootComponent.getBackupState().clear();
			}
		},
		transitFail : function(transit){
			
		},
		transitRollback : function(transit){
			
		},
	});
};



//lifecycle runtime context in component tree
//use this object to build a tree, so that all element on the tree in sync regarding lifecycle
var node_createLifeCycleRuntimeContext = function(id){
	
	var loc_id = id;
	
	var loc_lifecycleEntity;
	var loc_dumyStateMachine;
	var loc_children = {};
	
	var loc_getDumyStateMachine = function(){
		if(loc_dumyStateMachine==undefined)		loc_dumyStateMachine = node_createStateMachine(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, undefined, loc_id);
		return loc_dumyStateMachine;
	};
	
	var loc_out = {
		
		prv_getAllDescendants : function(outArray){ 
			//only with lifecycle entity
			outArray.push(this);
			_.each(loc_children, function(child){
				child.prv_getAllDescendants(outArray);
			});
		},
			
		setLifecycleEntity : function(lifecycleEntity){  loc_lifecycleEntity = lifecycleEntity;	},
		
		getId : function(){    return loc_id;    },	
		
		getStateMachine : function(){
			if(loc_lifecycleEntity!=undefined)		return loc_lifecycleEntity.getStateMachine();
			else		return loc_getDumyStateMachine();
		},
		
		getTaskCallBack : function(){  return loc_lifecycleEntity==undefined?undefined:loc_lifecycleEntity.getTaskCallBack();  },
		
		getChildren : function(){   return loc_children;   },
		createChild : function(id){
			out = node_createLifeCycleRuntimeContext(id);
			loc_children[id] = out;
			return out;
		},

		getAllDescendants : function(){
			var out = [];
			this.prv_getAllDescendants(out);
			return out;
		},
	};
	
	return loc_out;
};

//single lifecycle engine for 
var node_createComponentLifecycleEntity = function(withState, withLifecycle, thisContext, id){
	
	var loc_withState = withState;
	
	var loc_withLifecycle = withLifecycle;

	var loc_thisContext = thisContext;
	
	var loc_stateMachine;

	var loc_getState = function(){		return loc_withState.getBackupState();	};

	//state
	var loc_componentState = node_createComponentState(loc_getState(), 
		function(handlers, request){
			return loc_withState.getGetStateDataRequest==undefined?undefined:loc_withState.getGetStateDataRequest(handlers, request);
		},
		function(stateData, handlers, request){
			return loc_withState.getRestoreStateDataRequest==undefined?undefined:loc_withState.getRestoreStateDataRequest(stateData, handlers, request);
		}
	);

	//init statemachine for component
	var loc_init = function(withState, withLifecycle, thisContext, id){

		//component lifecycle call back methods
		var lifecycleCallback = {};

		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESTORE] = function(request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("loc_getActivateLiefCycleCallBackRequestRequest", {}), undefined, request);
			//prepare roll back data
			out.addRequest(loc_getSaveStateDataForRollBackRequest());
			//restore back up data
			out.addRequest(loc_getRestoreStateRequest());
			//component job
			out.addRequest(loc_getComponentLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESTORE));
			return out;
		};
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND_REVERSE] = function(request){	return loc_getReverseLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESOURCE_REVERSE, undefined, request);	};	

		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND] = function(request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("loc_getSuspendLiefCycleCallBackRequestRequest", {}), undefined, request);
			//prepare roll back data
			out.addRequest(loc_getSaveStateDataForRollBackRequest());
			//save back up data
			out.addRequest(loc_getBackupStateRequest());
			//component job
			out.addRequest(loc_getComponentLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND));
			return out;
		};
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND_REVERSE] = function(request){	return loc_getReverseLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND_REVERSE, undefined, request);	};	
		
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE] = function(request){  return loc_getNormalLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE, undefined, request);  }
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE_REVERSE] = function(request){	return loc_getReverseLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE_REVERSE, undefined, request);	};	

		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE]= function(request){	return loc_getNormalLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE, undefined, request); };
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE_REVERSE] = function(request){	return loc_getReverseLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE_REVERSE, undefined, request);	};	
		
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESUME] = function(request){	return loc_getNormalLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESUME, undefined, request);	};
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESUME_REVERSE] = function(request){	return loc_getReverseLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESUME_REVERSE, undefined, request);	};	
		
		lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DESTROY] = function(request){	return loc_getNormalLiefCycleCallBackRequestRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DESTROY, undefined, request);	};


		//build statemachine, start with init status
		loc_stateMachine = node_createStateMachine(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, loc_thisContext, id);
		//register callback
		//state transit callback method name follow naming conversion: from_to  or  _from_to for reverse 
		_.each(node_getStateMachineDefinition().getAllTransits(), function(transit, i){	
			var from = transit.from;
			var to = transit.to;
			loc_stateMachine.registerTransitCallBack(transit, 
				function(){
					var fun = lifecycleCallback[transit.from+"_"+transit.to];
					if(fun!=undefined)   return fun.apply(loc_thisContext, arguments);
					else  return true;
				}, 
				function(){
					var fun = lifecycleCallback["_"+transit.from+"_"+transit.to];
					if(fun!=undefined)   return fun.apply(loc_thisContext, arguments);
					else  return true;
				});
		});
		
	};

	var loc_getComponentLifeCycleRequest = function(lifecycleName, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
			var componentLifecycleResule = loc_withLifecycle.getLifeCycleRequest(lifecycleName);
			var entityType = node_getObjectType(componentLifecycleResule);
			if(node_CONSTANT.TYPEDOBJECT_TYPE_ERROR==entityType){
				return node_createServiceRequestInfoError(componentLifecycleResule);
			}
			else if(node_CONSTANT.TYPEDOBJECT_TYPE_REQUEST==entityType){
				return componentLifecycleResule;
			}
		}));
		
		return out;
	};
	
	//call back method for normal lifecycle change
	var loc_getNormalLiefCycleCallBackRequestRequest = function(lifecycleName, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("loc_getNormal"+lifecycleName+"LifeCycleCallBackRequestRequest", {}), handlers, request);
		//prepare roll back data
		out.addRequest(loc_getSaveStateDataForRollBackRequest());
		//execute complex lifecycle call back
		out.addRequest(loc_getComponentLifeCycleRequest(lifecycleName));
		return out;
	};
	
	//call back method for reverse lifecycle change
	var loc_getReverseLiefCycleCallBackRequestRequest = function(lifecycleName, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("loc_getReverse"+lifecycleName+"LifeCycleCallBackRequestRequest", {}), handlers, request);
		//roll back data from state
		out.addRequest(loc_getRestoreStateDataForRollBackRequest());
		//clear backup state
		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){  loc_clearBackupState(request)  }));
		//execute complex lifecycle call back
		out.addRequest(loc_getComponentLifeCycleRequest(lifecycleName));
		return out;
	};

	var loc_getSaveStateDataForRollBackRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("getSaveStateDataForRollBack", {}), handlers, request);
		out.addRequest(loc_componentState.getSaveStateDataForRollBackRequest());
		return out;
	};
	
	var loc_getRestoreStateDataForRollBackRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("getRestoreStateDataForRollBack", {}), handlers, request);
		out.addRequest(loc_componentState.getRestoreStateDataForRollBackRequest());
		return out;
	};
	
	var loc_getBackupStateRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("getBackupStateRequest", {}), handlers, request);
		out.addRequest(loc_componentState.getBackupStateRequest());
		return out;
	};

	var loc_getRestoreStateRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("getRestoreStateRequest", {}), handlers, request);
		out.addRequest(loc_componentState.getRestoreStateRequest());
		return out;
	};
	
	var loc_clearBackupState = function(request){
		loc_getState().clear(request);
	};

	var loc_out = {

		getStateMachine : function(){  return loc_stateMachine;   },

		getTaskCallBack : function(){  
			//call back when start a statemachine task
			return {
				startTask : function(){		
					loc_componentState.initDataForRollBack();
				},
				endTask : function(){		
					loc_componentState.clearDataFroRollBack();
				}
			};
		},

	};

	loc_init(withState, withLifecycle, thisContext, id);
	
	return loc_out;
};

var node_getStateMachineDefinition = function(){
	var loc_stateMachineDef = node_createStateMachineDef(
			[
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED, false),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT),
				new node_SMTransitDefinition(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE),
			],
			
			[
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_ACTIVATE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_DEACTIVATE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_SUSPEND, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_RESUME, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
			
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_DESTROY, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_DESTROY, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_DESTROY, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD]),
				
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_RESTART, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
				new node_SMCommandInfo(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_COMMAND_RESTART, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
			]
		);
	return loc_stateMachineDef;
};


//*******************************************   End Node Definition  ************************************** 	
//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.interfacedef.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoError", function(){node_createServiceRequestInfoError = this.getData();});
nosliw.registerSetNodeDataEvent("statemachine.createStateMachine", function(){node_createStateMachine = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.SMCommandInfo", function(){node_SMCommandInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.createStateMachineDef", function(){node_createStateMachineDef = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.SMTransitDefinition", function(){node_SMTransitDefinition = this.getData();	});
nosliw.registerSetNodeDataEvent("component.createComponentState", function(){node_createComponentState = this.getData();});
nosliw.registerSetNodeDataEvent("statemachine.createStateMachineTask", function(){node_createStateMachineTask = this.getData();});

//Register Node by Name
packageObj.createChildNode("createComponentLifecycleEntity", node_createComponentLifecycleEntity); 
packageObj.createChildNode("createLifeCycleRuntimeContext", node_createLifeCycleRuntimeContext); 
packageObj.createChildNode("getStateMachineDefinition", node_getStateMachineDefinition); 
packageObj.createChildNode("createLifecycleTask", node_createLifecycleTask); 

})(packageObj);

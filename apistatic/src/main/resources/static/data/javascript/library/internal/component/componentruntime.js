//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_getComponentInterface;
	var node_createComponentCoreComplex;
	var node_componentUtility;
var node_makeObjectWithType;
//*******************************************   Start Node Definition  ************************************** 	

//runtime is the one that 
//        expose lifecycle and interface inteface
//        manage backup
//        manage roll back
//		  have one core and multiple decorations
//parms:
//    componentCore core object
//    decoration
//    runtimeContext : other infor related with runtime obj, rootView, backupState
var node_createComponentRuntime = function(componentCore, decorationInfos, request){
	
	var loc_componentCoreComplex;

	var loc_init = function(componentCore, decorationInfos, request){
		//build core complex using core and decoration
		loc_componentCoreComplex = node_createComponentCoreComplex(componentCore, decorationInfos);
	};

	var loc_getComponentCore = function(){   return loc_componentCoreComplex.getCore();   };

	var loc_out = {

		getId : function(){   return loc_componentCoreComplex.getId();  },
			
		getLifecycleEntity : function(){	return loc_componentCoreComplex.getLifecycleEntity();	},
		
		getBackupState : function(){  return loc_componentCoreComplex.getBackupState();	},
		
		getCoreEntity : function(){   return loc_componentCoreComplex.getCore();   },
		
		getDecorations : function(){   return loc_componentCoreComplex.getDecorations();   },
		
		getPreInitRequest : function(handlers, request){	
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("PreInit", {}), handlers, request);
			out.addRequest(loc_componentCoreComplex.getPreInitRequest());
			return out;
		},
		
		getPostInitRequest : function(handlers, request){	
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("PostInit", {}), handlers, request);
			out.addRequest(loc_componentCoreComplex.getPostInitRequest());
			return out;
		},
		
		getLifeCycleRequest : function(transitName, handlers, request){
			var task = node_componentUtility.createLifecycleTask(transitName, this);
			return task.getProcessRequest(handlers, request);
		},
		
		setEnvironmentInterface : function(name, envInterface){		loc_componentCoreComplex.setEnvironmentInterface(name, envInterface);		},	
			
		updateBackupStateObject : function(backupStateObj){		loc_componentCoreComplex.updateBackupStateObject(backupStateObj);		},

		updateLifecycleEntityObject : function(lifecycleEntityObj){		loc_componentCoreComplex.updateLifecycleEntityObject(lifecycleEntityObj);		},
			
		updateView : function(view){		return loc_componentCoreComplex.updateView(view);		},
		
		registerEventListener : function(listener, handler, thisContext){  return loc_componentCoreComplex.registerEventListener(listener, handler, thisContext);   },
		unregisterEventListener : function(listener){   return loc_componentCoreComplex.unregisterEventListener(listener);  },
		registerValueChangeEventListener : function(listener, handler, thisContext){   return loc_componentCoreComplex.registerValueChangeEventListener(listener, handler, thisContext);   },
		unregisterValueChangeEventListener : function(listener){   return loc_componentCoreComplex.unregisterValueChangeEventListener(listener);    },
		
		
		
		

		
		
		
				
		//combo request(preinit, runtimeContext, postInit)
		getInitRequest111111 : function(runtimeContext, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("getInitRequestRuntime", {}), handlers, request);

			out.addRequest(loc_componentCoreComplex.getPreInitRequest({
				success : function(request){
					return loc_componentCoreComplex.getUpdateRuntimeContextRequest(runtimeContext, {
						success : function(request){
							return loc_componentCoreComplex.getPostInitRequest();
						}
					});
				}
			}));

			return out;
		},

		setInterfaceEnv1111 : function(interfaceEnv){		loc_interfaceEnv = interfaceEnv;	},
			
		getUpdateSystemDataRequest1111 : function(domain, systemData, handlers, request){
			return loc_getComponentCore().getUpdateSystemDataRequest(domain, systemData, handlers, request);
		},
		
		//component management interface 
		getContextIODataSet1111 :  function(){  return loc_getContextIODataSet();  },
		getExecuteCommandRequest1111 : function(command, parms, handlers, request){
			var out = node_componentUtility.getProcessNosliwCommand(loc_out, command, parms, handlers, request);
			if(out==undefined){
				out = loc_componentCoreComplex.getExecuteCommandRequest(command, parms, handlers, request);    
			}
			return out;
		},
	};
	
	loc_init(componentCore, decorationInfos, request);
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_COMPONENTRUNTIME);
	
	loc_out.id = nosliw.generateId();
	loc_out.dataType = node_getComponentInterface(loc_componentCoreComplex.getCore()).getDataType();
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("component.getComponentInterface", function(){node_getComponentInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.createComponentCoreComplex", function(){node_createComponentCoreComplex = this.getData();});
nosliw.registerSetNodeDataEvent("component.componentUtility", function(){node_componentUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.interfacedef.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});

//Register Node by Name
packageObj.createChildNode("createComponentRuntime", node_createComponentRuntime); 

})(packageObj);

//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSequence;
var node_createServiceRequestInfoSimple;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUIContentWrapperDebuggerPlugin = function(){
	
	var loc_out = {

		getCreateEntityCoreRequest : function(complexEntityDef, internalValuePortContainerId, externalValuePortContainerId, bundleCore, configure, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				return loc_createUIContentWrapperDebuggerComponentCore(complexEntityDef, internalValuePortContainerId, bundleCore, configure);
			}, handlers, request);
		}

	};

	return loc_out;
};

var loc_createUIContentWrapperDebuggerComponentCore = function(complexEntityDef, valueContextId, bundleCore, configure){
	
	var loc_complexEntityDef = complexEntityDef;
	var loc_valueContextId = valueContextId;
	var loc_bundleCore = bundleCore;
	var loc_envInterface = {};
	
	var loc_childContainer;

	var loc_out = {

		getEntityInitRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(loc_envInterface[node_CONSTANT.INTERFACE_ENTITY].createAttributeRequest(node_COMMONATRIBUTECONSTANT.BLOCKCOMPLEXUIWRAPPERCONTENTINCUSTOMERTAGDEBUGGER_CHILD, undefined, {
				success : function(request, childNode){
					loc_childContainer = childNode.getChildValue().getCoreEntity();
				}
			}));
			return out;
		},

		
		getPreInitRequest : function(handlers, request){
		},

		updateView : function(view){
			var wrapperView = $("<div/>");
    		var childValuePortContainer = loc_envInterface[node_CONSTANT.INTERFACE_ENTITY].getInternalValuePortContainer();

			wrapperView.append($("<br>ValuePortContainerId contentwrapperdbugger: "+childValuePortContainer.getId()+"</br>"));
			wrapperView.append(loc_childContainer.updateView(wrapperView));

			view.append(wrapperView);
			return view;
		},
		
		getPostInitRequest : function(handlers, request){
		},

		setEnvironmentInterface : function(envInterface){	loc_envInterface = envInterface;	},
	};
	
	return loc_out;	
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createUIContentWrapperDebuggerPlugin", node_createUIContentWrapperDebuggerPlugin); 

})(packageObj);

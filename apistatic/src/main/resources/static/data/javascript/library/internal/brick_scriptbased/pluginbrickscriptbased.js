//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
var node_ServiceInfo;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createScriptBasedPlugin = function(){
	
	var loc_out = {
		
		getCreateEntityCoreRequest : function(complexEntityDef, internalValuePortContainerId, externalValuePortContainerId, bundleCore, configure, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("createScriptBasedCoreEntity"), handlers, request);

			//run script
			out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
				var scriptFun = complexEntityDef.getAttributeValue(node_COMMONATRIBUTECONSTANT.WITHSCRIPT_SCRIPT);
				return scriptFun(complexEntityDef, internalValuePortContainerId, bundleCore, configure);
			}));

			return out;
		},
	};

	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createScriptBasedPlugin", node_createScriptBasedPlugin); 

})(packageObj);

//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_ResourceId;
	var node_resourceUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createTestComplexTaskScriptPlugin = function(){
	
	var loc_out = {

		getCreateEntityCoreRequest : function(complexEntityDef, internalValuePortContainerId, externalValuePortContainerId, bundleCore, configure, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("createScriptCoreEntity"), handlers, request);

			var resourceId = new node_ResourceId(complexEntityDef.getAttributeValue(node_COMMONATRIBUTECONSTANT.WITHSCRIPTREFERENCE_SCRIPTRESOURCEID));
			
			out.addRequest(nosliw.runtime.getResourceService().getGetResourcesRequest(resourceId, {
				success : function(requestInfo, resourceTree){
					var scriptFun = node_resourceUtility.getResourceFromTree(resourceTree, resourceId).resourceData[node_COMMONATRIBUTECONSTANT.WITHSCRIPT_SCRIPT];
					var rawComplexEntityCore = scriptFun(complexEntityDef, internalValuePortContainerId, bundleCore, configure);
					return rawComplexEntityCore;
	 			}
			}));
			
			return out;
		},
	};

	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("resource.entity.ResourceId", function(){node_ResourceId = this.getData();	});
nosliw.registerSetNodeDataEvent("resource.utility", function(){node_resourceUtility = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createTestComplexTaskScriptPlugin", node_createTestComplexTaskScriptPlugin); 

})(packageObj);

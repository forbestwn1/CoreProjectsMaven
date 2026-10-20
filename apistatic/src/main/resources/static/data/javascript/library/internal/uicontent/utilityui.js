//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoService;
	var node_DependentServiceRequestInfo;
	var node_resourceUtility;
	var node_buildServiceProvider;
	var node_ServiceInfo;
	var node_requestServiceProcessor;
	var node_createContextElementInfo;
	var node_dataUtility;
	var node_basicUtility;
	var node_getObjectType;
	var node_complexEntityUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_uiUtility = function(){
	
	var loc_getPage = function(uiEntity){
		var currentUIEntity = uiEntity;
		while(currentUIEntity!=undefined){
			var brickType = node_getObjectType(currentUIEntity)[node_COMMONATRIBUTECONSTANT.IDBRICKTYPE_BRICKTYPE];
			if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICONTENT){
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICUSTOMERTAG){
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UIPAGE){
				return currentUIEntity;
			}
		}
	};
		
	var loc_queryDownwards : function(uiEntity, query, results){
		var brickType = node_getObjectType(uiEntity)[node_COMMONATRIBUTECONSTANT.IDBRICKTYPE_BRICKTYPE];
		if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICONTENT){
			results.push(...uiEntity.queryCustomTagLocally(query));
    		_.each(uiEntity.getChildrenCustomTags(), function(childCustomTag, i){
				loc_queryInUIEntity(childCustomTag, query, results);
			});
		}
		else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICUSTOMERTAG){
			_.each(uiEntity.getAllChildrenCustomerNode(), function(childNodeInfo, i){
				loc_queryInUIEntity(childNodeInfo.node.getChildValue().getCoreEntity(), query, results);
			});
		}
		else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UIPAGE){
			loc_queryInUIEntity(uiEntity.getUIContent(), query, results);
		}
	};
	
	var loc_queryUpwards : function(uiEntity, query, results){
		var currentUIEntity = uiEntity;
		whild(true){
			var brickType = node_getObjectType(currentUIEntity)[node_COMMONATRIBUTECONSTANT.IDBRICKTYPE_BRICKTYPE];
			if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICONTENT){
				results.push(...uiEntity.queryCustomTagLocally(query));
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICUSTOMERTAG){
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UIPAGE){
				break;
			}
		}
	};

	var loc_out = {
		
		getPage : function(uiEntity){
			return loc_getPage(uiEntity);
		},
		
		queryInPage : function(currentUIEntity, query){
			var out = [];
			loc_queryDownwards(loc_getPage(currentUIEntity), query, out);
			return out;
		},
		
		queryInSiblingDownwards : function(currentUIEntity, query){
			var out = [];
			loc_queryDownwards(currentUIEntity.getParentUIEntity(), query, out);
			return out;
		},
		
		queryInSiblingUpwards : function(currentUIEntity, query){
			var out = [];
			loc_queryUpwards(currentUIEntity, query, out);
			return out;
		}
	};
	
	return loc_out;		
		
}();

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoService", function(){node_createServiceRequestInfoService = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.entity.DependentServiceRequestInfo", function(){node_DependentServiceRequestInfo = this.getData();});
nosliw.registerSetNodeDataEvent("resource.utility", function(){node_resourceUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("variable.context.createContextElementInfo", function(){node_createContextElementInfo = this.getData();});
nosliw.registerSetNodeDataEvent("variable.valueinvar.utility", function(){node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.interfacedef.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.complexEntityUtility", function(){node_complexEntityUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("uiUtility", node_uiUtility); 

})(packageObj);

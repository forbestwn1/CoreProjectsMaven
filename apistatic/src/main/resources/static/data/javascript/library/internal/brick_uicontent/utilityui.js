//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
var node_getObjectType;
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
		
	var loc_queryDownwards = function(uiEntity, query, results){
		var brickType = node_getObjectType(uiEntity)[node_COMMONATRIBUTECONSTANT.IDBRICKTYPE_BRICKTYPE];
		if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICONTENT){
			results.push(...uiEntity.queryCustomTagLocally(query));
    		_.each(uiEntity.getChildrenCustomTags(), function(childCustomTag, i){
				loc_queryDownwards(childCustomTag, query, results);
			});
		}
		else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICUSTOMERTAG){
			_.each(uiEntity.getAllChildrenCustomerNode(), function(childNodeInfo, i){
				loc_queryDownwards(childNodeInfo.node.getChildValue().getCoreEntity(), query, results);
			});
		}
		else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UIPAGE){
			loc_queryDownwards(uiEntity.getUIContent(), query, results);
		}
	};
	
	var loc_queryUpwards = function(uiEntity, query, results){
		var currentUIEntity = uiEntity;
		while(true){
			var brickType = node_getObjectType(currentUIEntity)[node_COMMONATRIBUTECONSTANT.IDBRICKTYPE_BRICKTYPE];
			if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICONTENT){
				results.push(...currentUIEntity.queryCustomTagLocally(query));
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UICUSTOMERTAG){
				currentUIEntity = currentUIEntity.getParentUIEntity();
			}
			else if(brickType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_UIPAGE){
				break;
			}
			else{
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
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.interfacedef.getObjectType", function(){node_getObjectType = this.getData();});

//Register Node by Name
packageObj.createChildNode("uiUtility", node_uiUtility); 

})(packageObj);

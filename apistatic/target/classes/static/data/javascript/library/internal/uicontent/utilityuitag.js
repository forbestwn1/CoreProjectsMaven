//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoService;
    var node_uiContentUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_uiTagUtility = function(){
	
	var loc_out = {
		
		queryCustomTag : function(currentCustomTag, query){
			var results = node_uiContentUtility.queryCustomTag(currentCustomTag, query);
			return results.filter(customTag=>customTag.getUIId()!=currentCustomTag.getUIId());
        },		
		
		getUITagFunctionRequest : function(uiTagDefinition, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);

			var uiTagBase = uiTagDefinition[node_COMMONATRIBUTECONSTANT.UITAGDEFINITION_BASE];
			var uiTagName = uiTagDefinition[node_COMMONATRIBUTECONSTANT.ENTITYINFO_NAME];
			
			if(uiTagName.startsWith("debug_test")){
				var node_uiTagFunction = nosliw.getNodeData("uitag."+uiTagName);
				out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
					return { fun : node_uiTagFunction};
				}));
			}
			else{
				var resourceId = uiTagDefinition[node_COMMONATRIBUTECONSTANT.UITAGDEFINITION_SCRIPTRESOURCEID];
				out.addRequest(nosliw.runtime.getResourceService().getGetResourceDataRequest(resourceId, {
					success : function(requestInfo, resourceData){
						var tagDefScriptFun = resourceData[node_COMMONATRIBUTECONSTANT.WITHSCRIPT_SCRIPT];
						return { fun : tagDefScriptFun };
					}
				}));
			}
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
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoService", function(){node_createServiceRequestInfoService = this.getData();});
nosliw.registerSetNodeDataEvent("uicontent.uiContentUtility", function(){node_uiContentUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("uiTagUtility", node_uiTagUtility); 

})(packageObj);

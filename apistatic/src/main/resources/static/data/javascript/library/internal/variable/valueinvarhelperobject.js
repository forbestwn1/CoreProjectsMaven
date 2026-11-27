//get/create package
var packageObj = library.getChildPackage("valueinvar");    

(function(packageObj){
//get used node
var node_CONSTANT;
var node_dataUtility;
var node_createServiceRequestInfoSimple;
var node_ServiceInfo;
var node_objectOperationUtility;
//*******************************************   Start Node Definition  ************************************** 	
var node_createDataTypeHelperObject = function(){
	
	/*
	 * get attribute value according to the path
	 */
	var loc_getObjectAttributeByPath = function(obj, prop) {
		if(obj==undefined)  return;
		if(prop==undefined || prop=='')  return obj;
		
	    var parts = prop.split('.'),
	        last = parts.pop(),
	        l = parts.length,
	        i = 1,
	        current = parts[0];

	    if(current==undefined)  return obj[last];
	    
	    while((obj = obj[current]) && i < l) {
	        current = parts[i];
	        i++;
	    }

	    if(obj) {
	        return obj[last];
	    }
	};

	
	var loc_out = {		

			//get child value by path
			getChildValueRequest : function(parentValue, path, handlers, requester_parent){
				return node_createServiceRequestInfoSimple(new node_ServiceInfo("GetChildValueFromObjectValue", {"parent":parentValue, "path":path}), function(requestInfo){
					var out = loc_getObjectAttributeByPath(parentValue, path);
					return node_dataUtility.cloneValue(out);
				}, handlers, requester_parent);
			},
			
			//do operation on value
			getDataOperationRequest : function(value, dataOperationService, handlers, requester_parent){
				return node_createServiceRequestInfoSimple(new node_ServiceInfo("GetDataOperationFromObjectValue", {"value":value, "dataOperationService":dataOperationService}), function(requestInfo){
					return node_objectOperationUtility.objectOperation(value, dataOperationService);
				}, handlers, requester_parent);
			},
			
			//loop through elements under value
			getGetElementsRequest : function(value, handlers, request){
				if(value==undefined)  return;
				
				return node_createServiceRequestInfoSimple(new node_ServiceInfo("GetElements", {"value":value}), function(requestInfo){
					var elements = [];
					if(_.isArray(value)){
						//for array
						_.each(value, function(eleValue, index){
							elements.push({
								value : node_dataUtility.cloneValue(eleValue)
							});
						}, this);
					}
					else if(_.isObject(value)){
						//for object
						_.each(value, function(eleValue, name){
							elements.push({
								id : name,
								value : node_dataUtility.cloneValue(eleValue)
							});
						}, this);
					} 
					return elements;
				}, handlers, request);
			}, 
			
			//clean up resource in value
			destroyValue : function(value){},
			
			getWrapperType : function(){	return node_CONSTANT.DATA_TYPE_OBJECT;		},
	
	};
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("variable.valueinvar.utility", function(){node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.objectOperationUtility", function(){node_objectOperationUtility = this.getData();});


nosliw.registerSetNodeDataEvent("variable.wrapper.wrapperFactory", function(){
	//register wrapper faction
	this.getData().registerDataTypeHelper([node_CONSTANT.DATA_TYPE_OBJECT], node_createDataTypeHelperObject());
});

})(packageObj);

//get/create package
var packageObj = library.getChildPackage("valueinvar");    

(function(packageObj){
//get used node
var node_CONSTANT;
var node_createServiceRequestInfoSimple;
//*******************************************   Start Node Definition  ************************************** 	
var node_createDataTypeHelperDynamic = function(){
	
	var loc_out = {		

			//get child value by path
			getChildValueRequest : function(parentValue, path, handlers, requester_parent){
				return node_createServiceRequestInfoSimple({}, function(){
					return parentValue;
				}, handlers, requester_parent);
			},
			
			//do operation on value
			getDataOperationRequest : function(value, dataOperationService, handlers, requester_parent){
			},
			
			//loop through elements under value
			getGetElementsRequest : function(value, handlers, request){
			}, 
	
			destroyValue : function(value){},
			
			getWrapperType : function(){	return node_CONSTANT.DATA_TYPE_DYNAMIC;		},
	
	};
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});


nosliw.registerSetNodeDataEvent("variable.wrapper.wrapperFactory", function(){
	//register wrapper faction
	this.getData().registerDataTypeHelper([node_CONSTANT.DATA_TYPE_DYNAMIC], node_createDataTypeHelperDynamic());
});

})(packageObj);

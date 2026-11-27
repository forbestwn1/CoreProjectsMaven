//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_createServiceRequestInfoSimple;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_getExecuteNoneDataAssociationRequest = function(inputDataSet, association, outputIODataSet, name, handlers, request){
	return node_createServiceRequestInfoSimple(undefined, function(request){
		return outputIODataSet;
	}, handlers, request);
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});

//Register Node by Name
packageObj.createChildNode("getExecuteNoneDataAssociationRequest", node_getExecuteNoneDataAssociationRequest); 

})(packageObj);

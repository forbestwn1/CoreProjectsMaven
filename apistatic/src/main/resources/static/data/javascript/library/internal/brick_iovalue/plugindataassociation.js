//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSimple;
var node_createDataAssociation;
//*******************************************   Start Node Definition  ************************************** 	

var node_createDataAssociationAdapterPlugin = function(){
	
	var loc_out = {

		getNewAdapterRequest : function(adapterDefinition, handlers, request){
			return node_createServiceRequestInfoSimple({}, function(request){
				return loc_createDataAssociationAdapter(adapterDefinition);
			}, handlers, request);
		},
	};

	return loc_out;
};


var loc_createDataAssociationAdapter = function(dataAssociation){
	
	var loc_dataAssociationDef = dataAssociation.getAttributeValue(node_COMMONATRIBUTECONSTANT.ADAPTERDATAASSOCIATION_DATAASSOCIATION);
	var loc_dataAssociation = node_createDataAssociation(loc_dataAssociationDef);
	
	var loc_out = {
		
		getExecuteRequest : function(baseEntityCore, handlers, request){
			return loc_dataAssociation.getExecuteRequest(baseEntityCore, handlers, request);
		}
	};
	
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("iovalue.createDataAssociation", function(){node_createDataAssociation = this.getData();});

//Register Node by Name
packageObj.createChildNode("createDataAssociationAdapterPlugin", node_createDataAssociationAdapterPlugin); 

})(packageObj);

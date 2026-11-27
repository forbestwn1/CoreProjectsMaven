//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  **************************************
	
//interface for plug in for complex entity,
//it create component core object

var node_buildEntityPlugInObject = function(rawPluginObj){

	var interfaceDef = {
		//create component core object
		getCreateEntityCoreRequest : function(entityDef, valueContextId, bundleCore, configure, handlers, request){
			return {};
		}
	};
		
	return _.extend({}, interfaceDef, rawPluginObj);	
};


var node_buildAdapterPlugInObject = function(rawPluginObj){

	var interfaceDef = {
		//create component core object
		getNewAdapterRequest : function(adapterDefinition, baseCore, handlers, request){
			return node_createServiceRequestInfoSimple({}, function(request){
				return {
					getExecuteRequest : function(handlers, request){
						return;
					}
				};
			}, handlers, request);
		}
	};
		
	return _.extend({}, interfaceDef, rawPluginObj);	
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data



//Register Node by Name
packageObj.createChildNode("buildEntityPlugInObject", node_buildEntityPlugInObject); 
packageObj.createChildNode("buildAdapterPlugInObject", node_buildAdapterPlugInObject); 

})(packageObj);

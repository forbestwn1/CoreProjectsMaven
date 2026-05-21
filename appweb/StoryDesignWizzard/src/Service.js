/**
 * 
 */
//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoCommon;
	var node_requestServiceProcessor;
	var node_storyChangeUtility;
	var node_storyUtility;
	var node_designUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createComponentQuestionItemService = function(availableService){

	var loc_loadServices = function(that){
		var request = node_createServiceRequestInfoSequence(undefined, {
			success : function(request, serviceDefs){
				var services = [];
				_.each(serviceDefs, function(serviceDef, i){
					var displayResource = serviceDef[node_COMMONATRIBUTECONSTANT.INFOSERVICEINTERFACE_DISPLAY];
					var displayName = displayResource!=undefined?displayResource[node_COMMONATRIBUTECONSTANT.ENTITYINFO_DISPLAYNAME]:undefined;
					if(displayName==undefined)  displayName = serviceDef[node_COMMONATRIBUTECONSTANT.ENTITYINFO_DISPLAYNAME];   
					var service = {
						id : serviceDef[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID], 
						name : serviceDef[node_COMMONATRIBUTECONSTANT.ENTITYINFO_NAME], 
						displayName : displayName, 
						description : serviceDef[node_COMMONATRIBUTECONSTANT.ENTITYINFO_DESCRIPTION], 
						sinterface :  serviceDef[node_COMMONATRIBUTECONSTANT.SERVICEPROFILE_INTERFACE],
					};
					services.push(service);
				});
				that.services = services;
				
				var service = loc_getCurrentServiceById(that, that.currentServiceId);
				if(service!=undefined)    that.currentService = service;
				else that.currentService = {};
			}
		});

		var gatewayId = node_COMMONATRIBUTECONSTANT.RUNTIME_GATEWAY_SERVICE;
		var command = node_COMMONATRIBUTECONSTANT.GATEWAYSERVICE_COMMAND_SEARCHDEFINITION;
		var parms = {};
		var query = {};
		query[node_COMMONATRIBUTECONSTANT.QUERYSERVICEDEFINITION_KEYWORDS] = ['demo'];
		parms[node_COMMONATRIBUTECONSTANT.GATEWAYSERVICE_COMMAND_SEARCHDEFINITION_QUERY] = query;
		var gatewayRequest = nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(gatewayId, command, parms);
		request.addRequest(gatewayRequest);
		
		node_requestServiceProcessor.processRequest(request);
	};
	
	var loc_getCurrentServiceById = function(that, serviceId){
		for(var i in that.services){
			var service = that.services[i];
			if(service[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID]==serviceId){
				return service;
			}
		}
	};
	

	return loc_vueComponent;
};	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoCommon", function(){	node_createServiceRequestInfoCommon = this.getData();	});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.storyChangeUtility", function(){node_storyChangeUtility = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.storyUtility", function(){node_storyUtility = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.designUtility", function(){node_designUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createComponentQuestionItemService", node_createComponentQuestionItemService); 

})(packageObj);

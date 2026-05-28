/**
 * 
 */
const createComponentQuestionItemService = function(){

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
	var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
    var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");

	var loc_loadServices = function(handler){
		var request = node_createServiceRequestInfoSequence({}, {
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
				handler(services);
				return services;
			}
		});

		var gatewayId = node_COMMONCONSTANT.GATEWAY_SERVICE;
		var command = node_COMMONATRIBUTECONSTANT.GATEWAYSERVICE_COMMAND_SEARCHDEFINITION;
		var parms = {};
		var query = {};
		// query[node_COMMONATRIBUTECONSTANT.QUERYSERVICEDEFINITION_KEYWORDS] = ['demo'];
		parms[node_COMMONATRIBUTECONSTANT.GATEWAYSERVICE_COMMAND_SEARCHDEFINITION_QUERY] = query;
		var gatewayRequest = nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(gatewayId, command, parms);
		request.addRequest(gatewayRequest);
		
		node_requestServiceProcessor.processRequest(request);
	};
	
	return {
		getLoadServicesRequest : function(handler){
			loc_loadServices(handler);
		}
	};
};	

export default createComponentQuestionItemService;
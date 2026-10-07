var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_basicUtility;
	var node_createEventObject;
	var node_requestServiceProcessor;
	var node_ResourceId;
	
//*******************************************   Start Node Definition  ************************************** 	

var loc_disable = function(containerView){
	if (containerView.parent().length > 0){
		containerView.remove();		
	}
};

var loc_enable = function(parentView, containerView){
	if (containerView.parent().length == 0){
		parentView.append(containerView);
	}
};

var loc_createExpressionApp = function(){

	var loc_parentView;
	
	var loc_containerView = $("<div></div>");
	var loc_contentView = $("<span></span>");
	loc_containerView.append(loc_contentView);

	var loc_value;
	
	var loc_out = {
	
		enable : function(){      loc_enable(loc_parentView, loc_containerView);    },
		
		disable : function(){   loc_disable(loc_containerView);  },
	
		updateView : function(parentView){
			loc_parentView = parentView;
		},
		
		getSetValueRequest : function(value, handlers, request){
			var out = node_createServiceRequestInfoSimple(undefined, function(request){
				loc_value = value;
				loc_contentView.text(JSON.stringify(loc_value));
			}, handlers, request);
			return out;
		}
		
	};
	return loc_out;
};

var loc_createConstantApp = function(dataDefinition){

	var loc_dataDefinition = dataDefinition;
	
	var loc_parentView;
	var loc_containerView = $("<div></div>");

	var loc_standaloneApp;
	
	var loc_getInitStandAloneRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		var gatewayParm = {};

		var uiTagQueryForChange = {};
		uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_DATADEFINITION] = loc_dataDefinition;
		uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_IOMODE] = node_COMMONCONSTANT.IO_DIRECTION_OUT;

		var parm1 = {};
		parm1[node_COMMONATRIBUTECONSTANT.STORYMANAGERSTANDALONE_CEATESTANDALONE_PARM_UITAGQUERY] = uiTagQueryForChange;
		var providerRequest1 = {};
		providerRequest1[node_COMMONATRIBUTECONSTANT.MANUALSTANDALONEPROVIDERREQUEST_PARMS] = parm1;
		var item1 = {};
		item1[node_COMMONATRIBUTECONSTANT.MANUALSTANDALONEREQUEST_PROVIDERNAME] = node_COMMONCONSTANT.STANDALONE_PROVIDER_STORY;
		item1[node_COMMONATRIBUTECONSTANT.MANUALSTANDALONEREQUEST_PROVIDERREQUEST] = providerRequest1;

		var items = [];
		items.push(item1);

		var requestObj = {};
		requestObj[node_COMMONATRIBUTECONSTANT.MANUALSTANDALONESBUILDREQUEST_ITEM] = items;

		gatewayParm[node_COMMONATRIBUTECONSTANT.MANUALGATEWAYSTANDALONE_PARMS_REQUEST] = requestObj;

		out.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
			node_COMMONCONSTANT.GATEWAY_MANUAL_STANDALONE,
			node_COMMONATRIBUTECONSTANT.MANUALGATEWAYSTANDALONE_COMMAND_BUILD,
			gatewayParm,
			{
				success: function (requestInfo, resourceIds) {
					var bundleDef = nosliw.runtime.getResourceService().getResource(new node_ResourceId(resourceIds[0])).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];

					var out1 = node_createServiceRequestInfoSequence({}, handlers, request);
					out1.addRequest(nosliw.runtime.getComplexEntityService().getCreateApplicationRequest({ bundleDef: bundleDef }, undefined, {}, undefined, {
	    				success: function (requestInfo, application) {
							loc_standaloneApp = application;
							loc_containerView.append(loc_standaloneApp.getView());
							
							loc_standaloneApp.registerExposeEventListener(undefined, function(eventName, eventValue){
								if(eventName==node_COMMONCONSTANT.EVENT_UI_VALUE_CHANGE){
									loc_setConstantValue(eventValue);
									loc_eventObject.triggerEvent("change");
								}
								else if(eventName==node_COMMONCONSTANT.ERROR_VALIDATION_VALUE){
									loc_setConstantValue();
									loc_eventObject.triggerEvent("change");
								}
							});
			    		}
				    }));
				    return out1;
				}
			}
		));

		return out;
	};
	
	var loc_out = {
	
		enable : function(){      loc_enable(loc_parentView, loc_containerView);    },
			
		disable : function(){   loc_disable(loc_containerView);  },
	
		updateView : function(parentView){
			loc_parentView = parentView;
		},
		
		getSetValueRequest : function(value, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			
			if(loc_standaloneApp==undefined){
				out.addRequest(loc_getInitStandAloneRequest());
			}

			out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
				
				return loc_standaloneApp.executeExecuteCommandRequest("setData", {
							    "data" : value[node_COMMONATRIBUTECONSTANT.DATABUILDEXPRESSION_OPERAND][node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDCONSTANT_DATA]
							});
			}));
			
			return out;
		}
		
	};
	return loc_out;
};


var node_presentDataApp = function(dataDefinition){
	
	var loc_value;
	
	var loc_containerView = $("<div></div>");

    var loc_presents = {};
		
	var loc_init = function(dataDefinition){
		var loc_expressionApp = loc_createExpressionApp();
		var loc_constantApp = loc_createConstantApp(dataDefinition);
		loc_expressionApp.updateView(loc_containerView);
		loc_constantApp.updateView(loc_containerView);

		loc_presents.variable = loc_expressionApp;
		loc_presents.constant = loc_constantApp;
	};
	
	var loc_out = {
		
		getView : function(){
			return loc_containerView;
		},

		getInitRequest : function(){},
		
		getSetValueRequest : function(value, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			
			loc_value = value;
			var chosen = loc_value[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSIONCHOSEN];
			_.each(loc_presents, function(present, type){
				if(type==chosen){
					present.enable();
     				out.addRequest(present.getSetValueRequest(loc_value[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSION][type]));
				}
				else{
					present.disable();
				}
			});
			return out;
		},
		
		getValue : function(){
			return loc_value;
		},
		
	};
	
	loc_init(dataDefinition);
	return loc_out;

};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("resource.entity.ResourceId", function(){node_ResourceId = this.getData();});

//Register Node by Name
packageObj.createChildNode("presentDataApp", node_presentDataApp); 

})(packageObj);

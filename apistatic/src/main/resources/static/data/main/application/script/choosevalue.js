var loc_createExpression = function(dataDefinition, env){

	var loc_dataDefinition = dataDefinition;
	var loc_env = env;
		
	var loc_currentType;
	var loc_operandChooses = {};

	var loc_containerView = $("<div>Container of expression</div>");

	var loc_selectChooseTypeView = $("<select></select>");
	var loc_selectChooseTypeContainerView = $("<div>Please select value type: </div>");
	loc_selectChooseTypeContainerView.append(loc_selectChooseTypeView);

	var loc_chooseTypeContainerView = $("<div>Wrapper Container for choose root operand</div>");

	
	
	
	var loc_containerVnextView = $("<div></div>");
	var loc_containerOperationView = $("<div></div>");
	
	loc_containerVnextView.append(loc_containerOperationView);
	
	var loc_nextOperation;
	var loc_dataOperations;
	
	var loc_options;
	var loc_optionsByName = {};
	
	
	var loc_getUpdateTypeSelectionRequest = function(type, handlers, request){
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");

		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		if(loc_currentType!=null){
			loc_operandChooses[loc_currentType].disable();
		}
		
		loc_currentType = type;
		var currentChoose = loc_operandChooses[loc_currentType];
		if(currentChoose!=null){
			currentChoose.enable();
		}
		else{
			currentChoose = loc_createOperandChain(loc_chooseTypeContainerView, loc_env);
			loc_operandChooses[loc_currentType] = currentChoose;
			
			var operand;
			if(loc_currentType=="constant"){
				operand = loc_createOperandConstant(loc_dataDefinition);
			}
			else if(loc_currentType=="variable"){
				operand = loc_createOperandVariable(loc_optionsByName.variable.variables);
			}
			
			out.addRequest(currentChoose.addOperandRequest(operand, {
				success : function(request, operandWrapper){
					currentChoose.enable();
					operandWrapper.enable();
				}
			}));
		}
		
		return out;
	};
	
	var loc_getInitRequest = function(handlers, request){
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
		var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
		
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		loc_options = loc_env.getOptions(loc_dataDefinition);
		
		if(loc_options.length>1){
			_.each(loc_options, function(option, i){
				loc_selectChooseTypeView.append($('<option>', { value: option.type, text: option.type }));
				loc_optionsByName[option.type] = option;
			});
			loc_containerView.append(loc_selectChooseTypeContainerView);

			out.addRequest(loc_getUpdateTypeSelectionRequest(loc_options[0].type));
			
			loc_selectChooseTypeView.on("change", function(event){
				var value = loc_selectChooseTypeView.val();
				var request = loc_getUpdateTypeSelectionRequest(value);
    			node_requestServiceProcessor.processRequest(request);			
			});
		}
		else{
			out.addRequest(loc_getUpdateTypeSelectionRequest(loc_options[0].type));
		}
		
		loc_containerView.append(loc_chooseTypeContainerView);
		
		return out;
	};
	
	var loc_out ={
		
		getInitRequest : function(handlers, request){
			return loc_getInitRequest(handlers, request);
		},
		
		updateView : function(parentView){
			parentView.append(loc_containerView);
		}
		
	};
	
	return loc_out;
};

var loc_crateOperandWrapper = function(operand, env){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");

	var loc_operand = operand;
	var loc_env = env;

	var loc_parentView;
	
	var loc_containerView = $("<div></div>");
	var loc_operandContainerView = $("<div></div>");
	loc_containerView.append(loc_operandContainerView);
	
	var loc_nextButton;
	var loc_operationSelection;
	
	var loc_eventObject = node_createEventObject();
	
	var loc_varNextRequest = function(){
		var dataType = "test.date;1.0.0";
		loc_operationSelection = loc_createOperationSelection(loc_containerView, dataType);
		
		var out = node_createServiceRequestInfoSequence();
		out.addRequest(loc_operationSelection.getInitRequest({
			success : function(request){
				
				loc_operationSelection.registerListener(function(eventName, eventData){
					if(eventName=="selectOperation"){
						loc_eventObject.triggerEvent("newOperation", loc_createOperandOperation(eventData, loc_env, dataType, dataType));
					}
				});
				
				loc_operationSelection.enable();
			}
		}));
		node_requestServiceProcessor.processRequest(out);			
	};
	
	var loc_out = {
		
		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			if(loc_operand.getType()=="variable"){
				loc_nextButton = loc_createNextButton(loc_containerView);
				loc_nextButton.registerListener(function(eventName, eventData){
					if(eventName=="next"){
						loc_varNextRequest();
					}
    				else if(eventName=="back"){
					
	    			}
				});
				
				loc_operand.registerListener(function(eventName, eventData){
					
				});
			}
			else if(loc_operand.getType()=="constant"){
				loc_operand.registerListener(function(eventName, eventData){
					
				});
			}
			
			return loc_operand.getInitRequest(loc_operandContainerView, handlers, request);
		},
		
		enable : function(){
			loc_parentView.append(loc_containerView);
			loc_operand.enable();
		},

		disable : function(){
			loc_containerView.remove();
			loc_operand.disable();
		},
		
		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
				
	};
	return loc_out;
};

var loc_createOperationSelection = function(parentView, baseDataType){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

	var loc_eventObject = node_createEventObject();
	
	var loc_parentView = parentView;
	
	var loc_containerView = $("<div>Please select operation: </div>");
	var loc_selectOperationView = $("<select></select>");
	loc_containerView.append(loc_selectOperationView);

	var loc_baseDataType = baseDataType;
	var loc_dataOperations;
	
	var loc_getRelatedOperationsRequest = function(baseDatatType, resultDataType, handlers, request){
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");

		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		var gatewayParm = {};
		gatewayParm[node_COMMONATRIBUTECONSTANT.GATEWAYDATATYPE_COMMAND_GETRELATEDOPERATION_DATATYPE_BASE] = baseDatatType;
		gatewayParm[node_COMMONATRIBUTECONSTANT.GATEWAYDATATYPE_COMMAND_GETRELATEDOPERATION_DATATYPE_RESULT] = resultDataType;

		out.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
			node_COMMONCONSTANT.GATEWAY_DATATYPE,
			node_COMMONATRIBUTECONSTANT.GATEWAYDATATYPE_COMMAND_GETRELATEDOPERATION,
			gatewayParm,
			{
				success: function (requestInfo, dataOperations) {
				    return dataOperations;
				}
			}
		));
		
		return out;
	};

	var loc_out = {
		
		getInitRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(loc_getRelatedOperationsRequest(loc_baseDataType, loc_baseDataType, {
				success : function(request, dataOperations){
					loc_dataOperations = {};
					_.each(dataOperations, function(dataOperation, i){
						loc_selectOperationView.append($('<option>', { value: dataOperation.name, text: dataOperation.name }));
						loc_dataOperations[dataOperation.name] = dataOperation;
					});
					
					loc_selectOperationView.on("change", function(event){
						var value = loc_selectOperationView.val();
						loc_eventObject.triggerEvent("selectOperation", loc_dataOperations[value]);
					});
				}
			}));
			return out;
		},
		
		enable : function(){    loc_parentView.append(loc_selectOperationView);         },
		
		disable : function(){     loc_selectOperationView.remove();        },
		
    	registerListener : function(handler){
	    	return loc_eventObject.registerListener(undefined, undefined, handler, this);
	    },
	};
	
	return loc_out;
};

var loc_createNextButton = function(parentView){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");
	
	var loc_parentView = parentView;
	var loc_nextButtonView = $("<button></button>");
	loc_parentView.append(loc_nextButtonView);

	
	var loc_status = 0;
	var loc_statusInfo = [{"title":"-->", "event":"next"},{"title":"<--", "event":"back"} ];
	
	var loc_eventObject = node_createEventObject();

	var loc_updateStatus = function(){
		loc_nextButtonView.text(loc_statusInfo[loc_status].title);
	};

	loc_updateStatus();
	
	loc_nextButtonView.on("click", function(){
		loc_status = 1 - loc_status;
		loc_updateStatus();
		loc_eventObject.triggerEvent(loc_statusInfo[loc_status].event, "nextData");
	});
	
	var loc_out = {
		
		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
		
	};
	return loc_out;
};

var loc_createOperandChain = function(parentView, env){
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
	
	var loc_parentView = parentView;
	var loc_env = env;
	
	var loc_containerview = $("<div></div>");
	
	var loc_operandChain = [];
	
	var loc_addOperandRequest = function(operand, handlers, request){
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");

		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		var wrapper = loc_crateOperandWrapper(operand, loc_env);

		out.addRequest(wrapper.getInitRequest(loc_containerview, {
			success : function(request){
				loc_operandChain.push(wrapper);
				
				wrapper.registerListener(function(eventName, eventData){
					if(eventName=="newOperation"){
						var out = node_createServiceRequestInfoSequence(undefined, handlers);
						out.addRequest(loc_addOperandRequest(eventData));
						node_requestServiceProcessor.processRequest(out);
					}
				});
				
				return wrapper;
			}
		}));

		return out;			
	};
	
	var loc_out = {
		
		addOperandRequest : function(operand, handlers, request){
			return loc_addOperandRequest(operand, handlers, request)
		},
		
		enable : function(){
			loc_parentView.append(loc_containerview);
		},

		disable : function(){
			loc_containerview.remove();
		},
		
	};
	
	return loc_out;
};


var loc_createOperandOperation = function(dataOperation, env, resultDataType, baseDataType){

	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	
	var loc_dataOperation = dataOperation;

	var loc_env = env;
		
	var loc_eventObject = node_createEventObject();

	var loc_parentView;
	var loc_containerView = $("<div>Container for operation</div>");
	
	var loc_parms = [];
	
    var loc_out = {
	
		getType : function(){    return "operation";      },
		
		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			
			_.each(loc_dataOperation.parms, function(parm){
				if(parm.isBase!="true"){
					
					var datadefinition = {
					        "type" : "writable",   
							"criteria" : parm.criteria,
					};
					
					var parmInfo = {
						"definition" : parm,
						"choose" : loc_createExpression(datadefinition, loc_env),
    					"view" : $("<div></div>")
					};
					
					loc_containerView.append(parmInfo.view);
					
					loc_parms.push(parmInfo);
				}
			});
			
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			_.each(loc_parms, function(parmInfo){
				out.addRequest(parmInfo.choose.getInitRequest({
					success : function(request){
						parmInfo.choose.updateView(parmInfo.view);
					}
				}));
			});
			
			return out;			
		},

		enable : function(){
			loc_parentView.append(loc_containerView);
		},

		disable : function(){
			loc_containerView.remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
		
    };

    return loc_out;
};

var loc_createOperandConstant = function(dataDefinition){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");

	var loc_dataDefinition = dataDefinition;

	var loc_parentView;

	var loc_containerView = $("<div>Container for constant choose</div>");

	var loc_contantValueWrapperView = $("<div>Please choose constant value : </div>");
	loc_containerView.append(loc_contantValueWrapperView);

	
	var loc_standaloneApp;

	var loc_eventObject = node_createEventObject();

	var loc_out = {
		
		getType : function(){   return "constant";    },
		
		getInitRequest : function(parentView, handlers, request){
			var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
			var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
			var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
			var node_ResourceId = nosliw.getNodeData("resource.entity.ResourceId");
			
			loc_parentView = parentView;
			
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			var gatewayParm = {};

			var uiTagQueryForChange = {};
			uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_DATADEFINITION] = loc_dataDefinition;
			uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_IOMODE] = node_COMMONCONSTANT.IO_DIRECTION_IN;

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
     							loc_contantValueWrapperView.append(loc_standaloneApp.getView());
				    		}
					    }));
					    return out1;
					}
				}
			));

			return out;
		},
		
		enable : function(){
			loc_parentView.append(loc_containerView);
		},

		disable : function(){
			loc_containerView.remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
		
	};
	
	return loc_out;
};

var loc_createOperandVariable = function(varNames){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");

	var loc_varNames = varNames;
	var loc_varName;

	var loc_parentView;
	var loc_containerView = $("<div>Container for variable choose</div>");
	
	var loc_variableChooseViewContainer = $("<div>Please select variable name : </div>");
	var loc_variableChooseView = $("<select></select>");
	loc_variableChooseViewContainer.append(loc_variableChooseView);
	
	var loc_eventObject = node_createEventObject();
	
	var loc_out = {
		
		getType : function(){   return "variable";    },

		getInitRequest : function(parentView, handlers, request){
			var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
			loc_parentView = parentView;
			
			loc_containerView.append(loc_variableChooseViewContainer);
			_.each(loc_varNames, function(varName){
				loc_variableChooseView.append($('<option>', { value: varName, text: varName }));
			});
			loc_variableChooseView.on("change", function(event){
				loc_varName = loc_variableChooseView.val();
			});
			return node_createServiceRequestInfoSequence(undefined, handlers, request);
		},
		
		enable : function(){
			loc_parentView.append(loc_containerView);
		},
		
		disable : function(){
			loc_containerView.remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
	};

	return loc_out;
};

var createOperandBuildApp = function(dataDefinition){
	
	var loc_env = {
		
		getOptions : function(dataDefinition){
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

			var constantOption = {
				"type" : "constant"
			};
			var variableOption = {
				"type" : "variable",
				"variables" : ["today"]
			};
			
			var criteria = dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA];
			var out = [];
			if(criteria=="test.date;1.0.0"){
				out.push(constantOption);
    			out.push(variableOption);
			}
			else{
				out.push(constantOption);
			}
			return out;
		}
	};

	var loc_rootWrapper = loc_createExpression(dataDefinition, loc_env);
	
	var loc_contentWrapperView = $("<div>AppContainer</div>");
	
	var loc_out = {
		
		getInitRequest : function(handlers, request){
			return loc_rootWrapper.getInitRequest(handlers, request);
		},
		
		updateView : function(parentView){
			loc_rootWrapper.updateView(loc_contentWrapperView);
			parentView.append(loc_contentWrapperView);
		}
		
	};
	
	return loc_out;

};


var loc_createOperandChooseWrapper = function(dataDefinition, env){

	var loc_dataDefinition = dataDefinition;
	var loc_env = env;
		
	var loc_currentType;
	var loc_operandChooses = {};

	var loc_containerView = $("<div>Main container of wrapper</div>");

	var loc_selectChooseTypeView = $("<select></select>");
	var loc_chooseTypeContainerView = $("<div>Wrapper Container for choose</div>");

	var loc_containerVnextView = $("<div></div>");
	var loc_nextButtonView = $("<button>Next</button>");
	var loc_selectOperationView = $("<select></select>");
	var loc_containerOperationView = $("<div></div>");
	
	loc_containerVnextView.append(loc_nextButtonView);
	loc_containerVnextView.append(loc_selectOperationView);
	loc_containerVnextView.append(loc_containerOperationView);
	
	var loc_nextOperation;
	var loc_dataOperations;
	
	var loc_options;
	var loc_optionsByName = {};
	
	
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
			if(loc_currentType=="constant"){
				currentChoose = loc_createOperandChooseConstant(loc_dataDefinition);
			}
			else if(loc_currentType=="variable"){
				currentChoose = loc_createOperandChooseVariable(loc_optionsByName.variable.variables);
			}
			loc_operandChooses[loc_currentType] = currentChoose;
			out.addRequest(currentChoose.getInitRequest(loc_chooseTypeContainerView, {
				success : function(request){
					currentChoose.enable();
					
					currentChoose.registerListener(function(event){
						if(event=="selected"){
							loc_containerVnextView.show();
						}
					});
				}
			}));
		}
		
		return out;
	};
	
	var loc_getInitRequest = function(handlers, request){
		var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
		var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
		var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

		var node_basicUtility = nosliw.getNodeData("common.utility.basicUtility");
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
		
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		loc_options = loc_env.getOptions(loc_dataDefinition);
		
		if(loc_options.length>1){
			_.each(loc_options, function(option, i){
				loc_selectChooseTypeView.append($('<option>', { value: option.type, text: option.type }));
				loc_optionsByName[option.type] = option;
			});
			loc_containerView.append(loc_selectChooseTypeView);

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
		
//		loc_nextButtonView.hide();
		loc_containerView.append(loc_containerVnextView);
		
		loc_nextButtonView.on("click", function(){
			var out = node_createServiceRequestInfoSequence();
			out.addRequest(loc_getRelatedOperationsRequest("test.date;1.0.0", "test.date;1.0.0", {
				success : function(request, dataOperations){
					loc_dataOperations = {};
					_.each(dataOperations, function(dataOperation, i){
						loc_selectOperationView.append($('<option>', { value: dataOperation.name, text: dataOperation.name }));
						loc_dataOperations[dataOperation.name] = dataOperation;
					});
					
					loc_selectOperationView.on("change", function(event){
						var value = loc_selectOperationView.val();
    					loc_nextOperation = loc_createOperandChooseOperation(loc_dataOperations[value], loc_env);
						
						var out = node_createServiceRequestInfoSequence(undefined, handlers);
						out.addRequest(loc_nextOperation.getInitRequest(loc_containerOperationView, {
							success : function(request){
								loc_nextOperation.enable();
							}
						}));
						node_requestServiceProcessor.processRequest(out);
					});
					
					
				}
			}));

			
			node_requestServiceProcessor.processRequest(out);			
			
		});
		
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


var loc_createOperandChooseOperation = function(dataOperation, env, resultDataType, baseDataType){

	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	
	var loc_dataOperation = dataOperation;

	var loc_env = env;
		
	var loc_eventObject = node_createEventObject();

	var loc_parentView;
	var loc_containerView = $("<div>Container for operation</div>");
	
	var loc_parms = [];
	
    var loc_out = {
	
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
						"choose" : loc_createOperandChooseWrapper(datadefinition, loc_env),
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



var loc_createOperandChooseConstant = function(dataDefinition){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");

	var loc_dataDefinition = dataDefinition;

	var loc_parentView;
	var loc_containerView = $("<div>Container for constant choose</div>");

	var loc_standaloneApp;

	var loc_eventObject = node_createEventObject();

	var loc_out = {
		
		getType : function(){   return "constant";    },
		
		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			loc_parentView.append(loc_containerView);
			
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
				    		}
					    }));
					    return out1;
					}
				}
			));

			return out;
		},
		
		enable : function(){
			loc_containerView.append(loc_standaloneApp.getView());
		},

		disable : function(){
			$(loc_standaloneApp.getView()).remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
		
	};
	
	return loc_out;
};

var loc_createOperandChooseVariable = function(varNames){
	var node_createEventObject = nosliw.getNodeData("common.event.createEventObject");

	var loc_varNames = varNames;

	var loc_parentView;
	var loc_containerView = $("<div>Container for variable choose</div>");
	var loc_variableChooseView = $("<select></select>");
	var loc_variableDisplayView = $("<span></span>");
	
	var loc_eventObject = node_createEventObject();

	var loc_out = {
		
		getType : function(){   return "variable";    },

		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			loc_parentView.append(loc_containerView);
			
			if(loc_varNames.length>1){
				loc_containerView.append(loc_variableChooseView);
				_.each(loc_varNames, function(varName){
					loc_variableChooseView.append($('<option>', { value: varName, text: varName }));
				});
				loc_variableChooseView.on("change", function(event){
					//emmit value change to wrapper
					loc_eventObject.triggerEvent("select", {});
				});
			}
			else{
				loc_variableDisplayView.text(loc_varNames[0]);
				loc_containerView.append(loc_variableDisplayView);
				loc_eventObject.triggerEvent("select", {});
			}
			
		},
		
		enable : function(){
			loc_containerView.append(loc_variableChooseView);
		},
		
		disable : function(){
			loc_variableChooseView.remove();
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
			var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
			var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
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

	var loc_rootWrapper = loc_createOperandChooseWrapper(dataDefinition, loc_env);
	
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

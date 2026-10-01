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

var loc_createExpression = function(id, dataDefinition, env){
	var loc_id = id;
	
	var loc_eventObject = node_createEventObject();

	var loc_dataDefinition = dataDefinition;
	var loc_env = env;
		
	var loc_currentType;
	var loc_operandChooses = {};

	var loc_containerView = $("<div></div>");

	var loc_selectChooseTypeView = $("<select id=\""+loc_id+"\"></select>");
	var loc_selectChooseTypeLabelView = $("<label for=\""+loc_id+"\">Please select value type:</label>");
	var loc_selectChooseTypeContainerView = $("<span></span>");
	loc_selectChooseTypeContainerView.append(loc_selectChooseTypeLabelView).append(" ").append(loc_selectChooseTypeView);
	loc_selectChooseTypeContainerView.css(loc_css_selectContainer);
	loc_selectChooseTypeLabelView.css(loc_css_label);
	loc_selectChooseTypeView.css(loc_css_select);

	var loc_chooseTypeContainerView = $("<div></div>");

	
	var loc_options;
	var loc_optionsByName = {};
	
	var loc_getCurrentChain = function(){
		return loc_operandChooses[loc_currentType];
	};
	
	var loc_getUpdateTypeSelectionRequest = function(type, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		if(loc_currentType!=null){
			loc_getCurrentChain().disable();
		}
		
		loc_currentType = type;
		var currentChoose = loc_getCurrentChain();
		if(currentChoose!=null){
			currentChoose.enable();
		}
		else{
			currentChoose = loc_createOperandChain(loc_id+"_"+loc_currentType, loc_currentType, loc_chooseTypeContainerView, loc_env);
			loc_operandChooses[loc_currentType] = currentChoose;
			
			var operand;
			if(loc_currentType=="constant"){
				operand = loc_createOperandConstant(loc_dataDefinition);
			}
			else if(loc_currentType=="variable"){
				operand = loc_createOperandVariable(loc_dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA], loc_env);
			}
			
			out.addRequest(currentChoose.addOperandRequest(operand, {
				success : function(request, operandWrapper){
					currentChoose.enable();
				}
			}));
			
			currentChoose.registerListener(function(eventName, eventData){
				if(currentChoose.getRootType()==loc_currentType){
					if(eventName=="change"){
						loc_eventObject.triggerEvent(eventName);
					}
				}
			});
		}
		
		return out;
	};
	
	var loc_getInitRequest = function(handlers, request){
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
				var out = node_createServiceRequestInfoSequence(undefined);
				var value = loc_selectChooseTypeView.val();
				out.addRequest(loc_getUpdateTypeSelectionRequest(value, {
					success : function(request){
						loc_eventObject.triggerEvent("change");
					}
				}));
    			node_requestServiceProcessor.processRequest(out);
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
		},
		
		isReady : function(){
			if(loc_currentType==undefined)   return false;
			return loc_getCurrentChain().isReady();
		},
		
    	registerListener : function(handler){
	    	return loc_eventObject.registerListener(undefined, undefined, handler, this);
	    },
		
		destroy : function(){
			loc_containerView.remove();
			_.each(loc_operandChooses, function(choose){
				choose.destroy();
			});
        },
			
		buildExpression : function(constants){
			return loc_getCurrentChain().buildExpression(constants);
		},

    	buildExpressionObj : function(constants){
	    	return loc_getCurrentChain().buildExpressionObj(constants);
	    },

	};
	
	return loc_out;
};


var loc_createOperandChain = function(id, rootType, parentView, env){
	var loc_eventObject = node_createEventObject();
	
	var loc_id = id;
	
	var loc_rootType = rootType;
	var loc_parentView = parentView;
	var loc_env = env;
	
	var loc_containerview = $("<ul></ul>");
	
	var loc_operandChain = [];
	
	var loc_truncate = function(wrapper){
		for(var i=loc_operandChain.length-1; i>=0; i--){
			if(loc_operandChain[i]==wrapper){
				break;
			}
			else{
				if(i!=0){
					loc_operandChain[i-1].setNextInChain();
				}
				loc_operandChain[i].destroy();
				loc_operandChain.pop();
			}
		}
	};
	
	var loc_addOperandRequest = function(operand, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		operand.setId(loc_id+"_"+operand.getType()+"_"+loc_operandChain.length);
		
		var wrapper = loc_crateOperandWrapper(operand, loc_env);

		wrapper.registerListener(function(eventName, eventData){
			if(eventName=="change"){
				loc_eventObject.triggerEvent("change");
			}
			else if(eventName=="next"){
				var out = node_createServiceRequestInfoSequence(undefined, handlers);
				out.addRequest(loc_addOperandRequest(loc_createOperandOperation(wrapper.getDataType(), loc_env), {
					success : function(request){
						loc_eventObject.triggerEvent("change");
					}
				}));
				node_requestServiceProcessor.processRequest(out);
			}
			else if(eventName=="truncate"){
				loc_truncate(wrapper);
     			loc_eventObject.triggerEvent("change");
			}
		});

		out.addRequest(wrapper.getInitRequest(loc_containerview, {
			success : function(request){
				wrapper.enable();
				if(loc_operandChain.length!=0){
					loc_operandChain[loc_operandChain.length-1].setNextInChain(wrapper);
				}
				loc_operandChain.push(wrapper);
				return wrapper;
			}
		}));

		return out;			
	};
	
	var loc_out = {
		
		getRootType : function(){   return loc_rootType;      },
		
		buildExpression : function(constants){
			var out;
			for(var i in loc_operandChain){
				out = loc_operandChain[i].getOperand().buildExpression(out, constants);
			}
			return out;
		},

		addOperandRequest : function(operand, handlers, request){
			return loc_addOperandRequest(operand, handlers, request)
		},
		
		enable : function(){		loc_parentView.append(loc_containerview);		},

		disable : function(){		loc_containerview.remove();		},

		destroy : function(){
			this.disable();
			_.each(loc_operandChain, function(wrapper, i){
				wrapper.destroy();
			});
			loc_operandChain = [];
		},
		
		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
				
		isReady : function(){
			for(var i in loc_operandChain){
				if(!loc_operandChain[i].isReady()){
					return false;
				}
			}
		    return true;
		},

		buildExpression : function(constants){
			var out;
			for(var i in loc_operandChain){
				out = loc_operandChain[i].getOperand().buildExpression(out, constants);
			}
			return out;
		},

		buildExpressionObj : function(constants){
    		var out;
	    	for(var i in loc_operandChain){
		    	out = loc_operandChain[i].getOperand().buildExpressionObj(out, constants);
		    }
		    return out;
		}
				
	};
	
	return loc_out;
};

var loc_crateOperandWrapper = function(operand, env){
	var loc_operand = operand;
	var loc_env = env;

	var loc_parentView;
	
	var loc_containerView = $("<li></li>");
	var loc_operandContainerView = $("<div></div>");
	loc_containerView.append(loc_operandContainerView);

	var loc_buttonContainerView = $("<div></div>");
	loc_containerView.append(loc_buttonContainerView);

	var loc_nextButton;
	
	var loc_eventObject = node_createEventObject();
	
	var loc_nextInChain;

	var loc_updateNextButton = function(){
		if(loc_nextButton==undefined){
			loc_nextButton = loc_createNextButton(loc_buttonContainerView);
			loc_nextButton.registerListener(function(eventName, eventData){
				if(eventName=="next"){
					loc_eventObject.triggerEvent("next");
				}
				else if(eventName=="back"){
					loc_eventObject.triggerEvent("truncate");
				}
			});
		}

		if(loc_operand.isReady()){
			loc_nextButton.enable();

			if(loc_nextInChain!=undefined){
				loc_nextButton.setStatus("back");
			}
			else{
				loc_nextButton.setStatus("next");
			}
		}
		else{
			loc_nextButton.disable();
		}
		
	};
	
	var loc_out = {
		
		getOperand : function(){   return loc_operand;    },
		
		getDataType : function(){    return loc_operand.getDataType();    },
		
		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			var operandType = loc_operand.getType();
			if(operandType=="variable"||operandType=="operation"){
				loc_operand.registerListener(function(eventName, eventData){
					loc_eventObject.triggerEvent(eventName);
					loc_updateNextButton();
				});
			}
			else if(operandType=="constant"){
				loc_operand.registerListener(function(eventName, eventData){
					if(eventName=="change"){
						loc_eventObject.triggerEvent("change");
					}
				});
			}
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(loc_operand.getInitRequest(loc_operandContainerView, {
				success : function(request){
					loc_operand.enable();
    				loc_updateNextButton();
				}
			}));
			return out;
		},
		
		enable : function(){
			loc_parentView.append(loc_containerView);
		},

		disable : function(){
			loc_containerView.remove();
		},
		
		destroy : function(){
			this.disable();
			loc_operand.destroy();
			if(loc_nextButton!=undefined)    loc_nextButton.destroy();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
				
    	isReady : function(){
			return loc_operand.isReady();
	    },
		
		setNextInChain : function(nextInChain){
			loc_nextInChain = nextInChain;
		}
	};
	return loc_out;
};

var loc_createNextButton = function(parentView){
	var loc_parentView = parentView;
	
//	var loc_nextButtonView = $("<button></button>");
//	loc_parentView.append(loc_nextButtonView);

	var loc_containerView = $("<span></span>");
	var loc_nextabelView = $("<label></label>");
	var loc_nextButtonView = $("<button></button>");
	loc_containerView.append(loc_nextabelView).append(" ").append(loc_nextButtonView);
	loc_containerView.css(loc_css_selectContainer);
	loc_nextabelView.css(loc_css_label);
	
	var loc_status = 0;
	var loc_statusInfo = [
		{"name":"next", "label":"Need operation?", "title":"-->", "event":"next"},
		{"name":"back", "label":"Revers operation?", "title":"<--", "event":"back"} 
    ];
	
	var loc_eventObject = node_createEventObject();

	var loc_updateStatus = function(){
		loc_nextButtonView.text(loc_statusInfo[loc_status].title);
		loc_nextabelView.text(loc_statusInfo[loc_status].label);
	};

	loc_updateStatus();
	
	loc_nextButtonView.on("click", function(){
		loc_eventObject.triggerEvent(loc_statusInfo[loc_status].event);
		loc_status = 1 - loc_status;
		loc_updateStatus();
	});
	
	var loc_out = {
		
		setStatus : function(status){
			for(var i in loc_statusInfo){
				if(loc_statusInfo[i].name==status){
					loc_status = i;
					loc_updateStatus();
					return;
				}
			}
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
		
    	destroy : function(){
	    },

	};
	return loc_out;
};

//  baseDataType + env
//  operationOperand + env
var loc_createOperandOperation = function(arg1, env){
	var loc_env;
	
	var loc_id;

	var loc_dataOperation;
	var loc_baseDataType;
	var loc_operationName;
	
	var loc_parmsObj;
	var loc_parms = [];

	var loc_eventObject = node_createEventObject();

	var loc_parentView;
	var loc_containerView = $("<div></div>");
	
	var loc_operationSelection;
	var loc_operationSelectionContainerView = $("<div></div>");
	loc_containerView.append(loc_operationSelectionContainerView);

	var loc_init = function(arg1, env){
		loc_env = env;
		if(node_basicUtility.isStringValue(arg1)){
			loc_baseDataType = arg1;
        }
		else{
			loc_baseDataType = arg1[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_DATATYPEID]; 
			loc_operationName = arg1[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_OPERATION];
			loc_parmsObj = arg1[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_PARMS];
		}		
	};
	
	var loc_getInitParmsRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		_.each(loc_parms, function(parmInfo){
		    parmInfo.expression.registerListener(function(eventName, eventData){
			    if(eventName=="change"){
				    loc_eventObject.triggerEvent("change");
			    }
		    });
		    out.addRequest(parmInfo.expression.getInitRequest({
			    success : function(request){
				    parmInfo.expression.updateView(parmInfo.view);
			    }
		    }));
		});
		return out;
	};
	
	var loc_getInitNewOperationRequest = function(handlers, request){
		_.each(loc_dataOperation.parms, function(parm){
			if(parm.isBase!="true"){
				var datadefinition = {
				    "type" : "writable",   
					"criteria" : parm.criteria,
				};
				
				var parmInfo = {
					"definition" : parm,
					"expression" : loc_createExpression(loc_id+"_parm_"+parm.name, datadefinition, loc_env),
					"view" : $("<div>Container for parm: " +parm.name  + "</div>")
				};
				
				loc_containerView.append(parmInfo.view);
				
				loc_parms.push(parmInfo);
			}
		});
		return loc_getInitParmsRequest(handlers, request);
	};
	
	var loc_deleteParms = function(){
		_.each(loc_dataOperation.parms, function(parmInfo){
			if(parm.isBase!="true"){
				parmInfo.view.remove();
				parmInfo.expression.destroy();
				loc_parms = [];
			}
		});
	};
	
	var loc_getInitRequest = function(parentView, handlers, request){
		loc_parentView = parentView;
		
		loc_operationSelection = loc_createOperationSelection(loc_baseDataType, loc_operationName);
		loc_operationSelectionContainerView.append(loc_operationSelection.getView());

		loc_operationSelection.registerListener(function(eventName, eventData){
			if(eventName=="selectOperation"){
				loc_deleteParms();
				
				loc_dataOperation = loc_operationSelection.getCurrentDataOperation();
				loc_operationName = loc_dataOperation.name;
				loc_baseDataType = loc_dataOperation.source;

				loc_eventObject.triggerEvent("truncate");
				
				node_requestServiceProcessor.processRequest(loc_getInitNewOperationRequest());
			}
		});

		var out = node_createServiceRequestInfoSequence();
		out.addRequest(loc_operationSelection.getInitRequest({
			success : function(request){
				loc_dataOperation = loc_operationSelection.getCurrentDataOperation();
				loc_operationName = loc_dataOperation.name;
				loc_baseDataType = loc_dataOperation.source;
				
				if(loc_parmsObj==undefined){
					return loc_getInitNewOperationRequest();
				}
				else{
					
				}
			}
		}));
				
		return out;
	};
			
    var loc_out = {
	
		getType : function(){    return "operation";      },

		getDataType : function(){    return loc_dataOperation.target;        },
		
		getId : function(){     return loc_id;    },
		setId : function(id){    loc_id = id;        },

		getInitRequest : function(parentView, handlers, request){
			return loc_getInitRequest(parentView, handlers, request);
		},

		enable : function(){
			loc_parentView.append(loc_containerView);
		},

		disable : function(){		loc_containerView.remove();		},

		registerListener : function(handler){		return loc_eventObject.registerListener(undefined, undefined, handler, this);		},
		
		destroy : function(){
			_.each(loc_parms, function(parm, i){
				parm.expression.destroy();
			});
			
			this.disable();
		},

    	isReady : function(){
			for(var i in loc_parms){
				if(!loc_parms[i].expression.isReady()){
					return false;
				}
			}
			return true;
	    },
		
		buildExpression : function(previous, constants){
			var out = "!("+loc_baseDataType+")!."+loc_operationName+"(";
			out = out + previous
			for(var i in loc_parms){
				var parmInfo = loc_parms[i];
				out = out + ", " + parmInfo.definition.name + ":" + parmInfo.expression.buildExpression(constants);
			}
			out = out + ")";
			return out;
		},

		buildExpressionObj : function(previous, constants){
			var out = {};
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERAND_TYPE] = node_COMMONCONSTANT.EXPRESSION_OPERAND_OPERATION;
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_DATATYPEID] = loc_baseDataType; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_OPERATION] = loc_operationName; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_PARMS] = {}; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_BASE] = previous; 
			for(var i in loc_parms){
				var parmInfo = loc_parms[i];
				out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_PARMS][parmInfo.definition.name] = parmInfo.expression.buildExpressionObj(undefined, constants);
			}
			return out;
		}
		
    };

	loc_init(arg1, env);
    return loc_out;
};

var loc_createOperationSelection = function(baseDataType, currentOperation){
	var loc_baseDataType = baseDataType;
	var loc_currentOperation = currentOperation

	var loc_containerView = $("<span></span>");
	var loc_selectOperationLabelView = $("<label>Please select operation : </label>");
	var loc_selectOperationView = $("<select></select>");
	loc_containerView.append(loc_selectOperationLabelView).append(" ").append(loc_selectOperationView);
	loc_containerView.css(loc_css_selectContainer);
	loc_selectOperationLabelView.css(loc_css_label);
	loc_selectOperationView.css(loc_css_select);

	var loc_dataOperations;
	
	var loc_eventObject = node_createEventObject();

	var loc_getRelatedOperationsRequest = function(baseDatatType, resultDataType, handlers, request){
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
					
					if(loc_currentOperation==undefined){
						loc_currentOperation = dataOperations[0].name;
					}
					loc_selectOperationView.val(loc_currentOperation);
					
					loc_selectOperationView.on("change", function(event){
						loc_currentOperation = loc_selectOperationView.val();
						loc_eventObject.triggerEvent("selectOperation", loc_dataOperations[loc_currentOperation]);
					});
				}
			}));
			return out;
		},
		
		getView : function(){     return loc_containerView;        },
		
		destroy : function(){
			this.disable();
		},
		
    	registerListener : function(handler){
	    	return loc_eventObject.registerListener(undefined, undefined, handler, this);
	    },
		
		getCurrentDataOperation : function(){
			return loc_dataOperations[loc_currentOperation];
		}
	};
	
	return loc_out;
};

//    undefined + constantOperand
//    dataDefinition + constantOperand
//    dataDefinition
var loc_createOperandConstant = function(dataDefinition, arg1){
	var loc_id;
	
	var loc_dataDefinition;

	var loc_parentView;

	var loc_containerView = $("<div></div>");

	var loc_contantValueWrapperView = $("<div>Please choose constant value : </div>");
	loc_containerView.append(loc_contantValueWrapperView);

	var loc_standaloneApp;
	var loc_constantValue;

	var loc_eventObject = node_createEventObject();

	var loc_setConstantValue = function(constantValue){
		loc_constantValue = constantValue;
	};
	
	var loc_init = function(dataDefinition, arg1){
		if(dataDefinition!=undefined){
			loc_dataDefinition = dataDefinition;
		}
		
		if(arg1!=undefined){
			loc_constantValue = arg1;
			if(loc_dataDefinition==undefined){
				loc_dataDefinition = {};
				loc_dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA] = loc_constantValue.dataType;
			}
		}
	};
	
	var loc_registerConstantListener = function(){
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
	};
	
	var loc_out = {
		
		getType : function(){   return "constant";    },

		getDataType : function(){     return loc_dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA];      },
		
		getId : function(){     return loc_id;    },
		setId : function(id){    loc_id = id;        },

		getInitRequest : function(parentView, handlers, request){
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
								
								if(loc_constantValue!=undefined){
									return loc_standaloneApp.executeExecuteCommandRequest("setData", {
									    "data" : data
									}, {
										success : function(request){
											loc_registerConstantListener();
										}
									});
								}
								loc_registerConstantListener();
				    		}
					    }));
					    return out1;
					}
				}
			));

			return out;
		},
		
		isReady : function(){    return loc_constantValue!=undefined;       },

		enable : function(){		loc_parentView.append(loc_containerView);		},

		disable : function(){		loc_containerView.remove();		},

		destroy : function(){
			this.disable();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},

		buildExpression : function(previous, constants){
			constants[loc_id] = loc_constantValue;
			return "&(" + loc_id + ")&";
		},

		buildExpressionObj : function(previous, constants){
			var out = {};
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERAND_TYPE] = node_COMMONCONSTANT.EXPRESSION_OPERAND_CONSTANT;
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDCONSTANT_DATA] = loc_constantValue; 
			return out;
		}
		
	};
	
	loc_init(dataDefinition, arg1);
	return loc_out;
};

//two type of input
//   dataType + env
//   variableOperand + env
var loc_createOperandVariable = function(arg1, env){
	var loc_env;
	
	var loc_id;

	var loc_dataType;
	
	var loc_varNames;
	var loc_varName;

	var loc_parentView;
	var loc_containerView = $("<div></div>");
	
	var loc_variableChooseViewContainer = $("<span></span>");
	var loc_variableChooseLabelView = $("<label>Please select variable name : </label>");
	var loc_variableChooseView = $("<select></select>");
	loc_variableChooseViewContainer.append(loc_variableChooseLabelView).append(" ").append(loc_variableChooseView);
	loc_variableChooseViewContainer.css(loc_css_selectContainer);
	loc_variableChooseLabelView.css(loc_css_label);
	loc_variableChooseView.css(loc_css_select);
	
	var loc_eventObject = node_createEventObject();
	
	var loc_init = function(arg1, env){
		loc_env = env;
		if(node_basicUtility.isStringValue(arg1)){
			//dataType + env
			loc_dataType = arg1;
		}
		else{
			//variableOperand + env
			loc_varName = arg1[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDVARIABLE_VARIABLENAME];
			loc_dataType = loc_env.getDataTypeByVariable(loc_varName);
		}
		loc_varNames = loc_env.getVariablesByDataType(loc_dataType);
	};
	
	var loc_out = {
		
		getType : function(){   return "variable";    },

		getDataType : function(){    return loc_dataType;     },
		
		getId : function(){     return loc_id;    },
		setId : function(id){    loc_id = id;        },
		
		getInitRequest : function(parentView, handlers, request){
			loc_parentView = parentView;
			
			loc_containerView.append(loc_variableChooseViewContainer);
			_.each(loc_varNames, function(varName){
				loc_variableChooseView.append($('<option>', { value: varName, text: varName }));
			});
			if(loc_varName==undefined){
				loc_varName = loc_varNames[0];
			}
			loc_variableChooseView.val(loc_varName);
			loc_variableChooseView.on("change", function(event){
				loc_varName = loc_variableChooseView.val();
    			loc_eventObject.triggerEvent("change");
			});
			
			return node_createServiceRequestInfoSequence(undefined, handlers, request);
		},
		
		isReady : function(){    return loc_varName!=undefined;       },
		
		enable : function(){		loc_parentView.append(loc_containerView);		},
		
		disable : function(){		loc_containerView.remove();		},
		
		destroy : function(){
			this.disable();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},

    	buildExpression : function(previous, constants){
	    	return "?(" + loc_varName + ")?";
	    },
		
		buildExpressionObj : function(previous, constants){
			var out = {};
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERAND_TYPE] = node_COMMONCONSTANT.EXPRESSION_OPERAND_VARIABLE;
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDVARIABLE_VARIABLENAME] = loc_varName; 
			return out;
		}

	};

	loc_init(arg1, env);
	return loc_out;
};

var loc_css_selectContainer = {
	"white-space":"nowrap", "display":"inline-block"
};

var loc_css_label = {
	"font-weight":"bold", "margin-right":"6px"
};

var loc_css_select = {
		"display":"inline-block",
		"vertical-align":"middle",
		"margin-left":"4px",
		"padding":"2px 24px 2px 8px",
		"border":"1px solid #7a7a7a",
		"border-radius":"0",
		"background-color":"#fff",
		"font-size":"14px",
		"font-family":"inherit",
		"cursor":"pointer",
		"-webkit-appearance":"menulist",
		"-moz-appearance":"menulist",
		"appearance":"menulist"
};

var node_createValueApp = function(dataDefinition){
	var loc_eventObject = node_createEventObject();
		
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
		},
		
		getVariablesByDataType : function(dataType){
			if(dataType=="test.date;1.0.0"){
				return ["today"];
			}
		},
		
		getDataTypeByVariable : function(varName){
			if(varName=="today"){
				return "test.date;1.0.0";
			}
		}
	};

	var loc_expression = loc_createExpression("expression", dataDefinition, loc_env);
	
	var loc_containerView = $("<div>AppContainer</div>");
	
	var loc_buildExpression = function(){
		var out = {
			constants : {}
		};
		out.expression = loc_expression.buildExpression(out.constants);
        return out;		
	};
	
	var loc_buildExpressionObj = function(){
		var out = {
			constants : {}
		};
		out.expression = loc_expression.buildExpressionObj(out.constants);
	    return out;		
	};

	var loc_out = {
		
		getInitRequest : function(handlers, request){
			loc_expression.updateView(loc_containerView);
			loc_expression.registerListener(function(eventName, eventData){
				if(eventName=="change"){
//					console.log(JSON.stringify(loc_buildExpression()));
    				console.log(JSON.stringify(loc_buildExpressionObj()));
				}
			});
			
			return loc_expression.getInitRequest(handlers, request);
		},

		getView : function(){
			return loc_containerView;
		},
		
		setValue : function(value){
			
		},
		
		getValue : function(){
			
		}
		
	};
	
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
packageObj.createChildNode("createValueApp", node_createValueApp); 

})(packageObj);



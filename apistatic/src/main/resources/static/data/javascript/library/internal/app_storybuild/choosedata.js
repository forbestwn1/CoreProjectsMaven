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
	containerView.hide();
/*
	if (containerView.parent().length > 0){
		containerView.remove();		
	}
*/	
};

var loc_enable = function(parentView, containerView){
	containerView.show();
	if (containerView.parent().length == 0){
		parentView.append(containerView);
	}
};
	
var loc_createRootChoose = function(dataDefinition, env){
	
	var loc_eventObject;

	var loc_dataDefinition;
	var loc_env;
		
	var loc_currentChoose;
	
	var loc_options;
	var loc_optionsByName = {};

	var loc_containerView = $("<div></div>");
	
	var loc_selectChooseTypeView;
	
	var loc_init = function(dataDefinition, env){
		loc_dataDefinition = dataDefinition;
		loc_env = env;
		loc_eventObject = node_createEventObject();

		loc_options = loc_env.getOptions(loc_dataDefinition);

		if(loc_options.length>1){
			loc_selectChooseTypeView = $("<select id='chooseRootType'></select>");
			var loc_selectChooseTypeLabelView = $("<label for='chooseRootType'>Please select value type:</label>");
			var loc_selectChooseTypeContainerView = $("<span></span>");
			loc_selectChooseTypeContainerView.append(loc_selectChooseTypeLabelView).append(" ").append(loc_selectChooseTypeView);
			loc_selectChooseTypeContainerView.css(loc_css_selectContainer);
			loc_selectChooseTypeLabelView.css(loc_css_label);
			loc_selectChooseTypeView.css(loc_css_select);
			loc_containerView.append(loc_selectChooseTypeContainerView);

			_.each(loc_options, function(option, i){
				loc_selectChooseTypeView.append($('<option>', { value: option.type, text: option.type }));
				loc_optionsByName[option.type] = option;
			});

			loc_selectChooseTypeView.on("change", function(event){
//				var from = loc_currentChoose;
//				loc_currentChoose = loc_selectChooseTypeView.val();
				loc_eventObject.triggerEvent("change", loc_selectChooseTypeView.val());
			});
		}
    	loc_currentChoose = loc_options[0].type;
	};	
	
	var loc_out = {
		
		getCurrentChoose : function(){
			return loc_currentChoose;
		},
		
		setCurrentChoose : function(choose){
			loc_currentChoose = choose;
			if(loc_options.length>1){
				loc_selectChooseTypeView.val(loc_currentChoose);
			}
		},
		
		getView : function(){
			return loc_containerView;
		},
		
		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},
		
	};
	
	loc_init(dataDefinition, env);
	return loc_out;
};

//id + data definition + env
//valueChooseObj + undefined + env
var loc_createDataChoose = function(arg1, arg2, env){
	var loc_id;
	
	var loc_eventObject;

	var loc_dataDefinition;

	var loc_valueChooseObj;
	
	var loc_env;

	var loc_containerView = $("<div></div>");
	var loc_expressionContainerView = $("<div></div>");

	var loc_rootChoose;
	
	var loc_expressionsByType = {};

	var loc_getCurrentRootType = function(){	return loc_rootChoose.getCurrentChoose();	};

	var loc_getCurrentExpression = function(){	return loc_expressionsByType[loc_getCurrentRootType()];	};
	
	var loc_updateRootTypeSelection = function(rootType){
		if(loc_getCurrentRootType()!=null){
			loc_getCurrentExpression().disable();
		}

		loc_rootChoose.setCurrentChoose(rootType);
		var currentExpression = loc_getCurrentExpression();
		if(currentExpression!=null){
			currentExpression.enable();
		}
	};

	var loc_newRootOperand = function(rootType){
		var operand;
		if(rootType==node_COMMONCONSTANT.DATABUILD_CHOSEN_CONSTANT){
			operand = loc_createOperandConstant(loc_dataDefinition);
		}
		else if(rootType==node_COMMONCONSTANT.DATABUILD_CHOSEN_EXPRESSION){
			operand = loc_createOperandVariable(loc_dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA], loc_env);
		}
		return operand;
	};
		
	var loc_getInitNewRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);

		_.each(loc_expressionsByType, function(expression, rootType){
			var operand = loc_newRootOperand(rootType);
			out.addRequest(expression.addOperandRequest(operand));
		});
		
		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
			loc_updateRootTypeSelection(node_COMMONCONSTANT.DATABUILD_CHOSEN_CONSTANT);
			loc_registerListener();
		}));
		
		return out;
	};

	var loc_buildOperandChain = function(operand, dataDefinition){
		var out = [];
    	var operandType =  operand[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERAND_TYPE];
	    if(operandType==node_COMMONCONSTANT.EXPRESSION_OPERAND_VARIABLE){
		    out.push(loc_createOperandVariable(operand, loc_env));
	    }
	    else if(operandType==node_COMMONCONSTANT.EXPRESSION_OPERAND_CONSTANT){
		    out.push(loc_createOperandConstant(dataDefinition, operand));
	    }
	    else if(operandType==node_COMMONCONSTANT.EXPRESSION_OPERAND_OPERATION){
			var baseOut = loc_buildOperandChain(operand.base);
			for(var i in baseOut){
				out.push(baseOut[i]);
			}
		
    		out.push(loc_createOperandOperation(operand, loc_env));
	    }
		return out;
	};
	
	var loc_getInitExistingRequest = function(valueChooseObj, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		_.each(loc_expressionsByType, function(expression, rootType){
			var expressionObj = valueChooseObj[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSION][rootType];
			if(expressionObj!=null){
				var operandChains = loc_buildOperandChain(expressionObj[node_COMMONATRIBUTECONSTANT.DATABUILDEXPRESSION_OPERAND], loc_dataDefinition);
				_.each(operandChains, function(operand){
					out.addRequest(expression.addOperandRequest(operand));
				});
			}
			else{
				var operand = loc_newRootOperand(rootType);
				out.addRequest(expression.addOperandRequest(operand));
			}
		});
		
		out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
			loc_updateRootTypeSelection(valueChooseObj[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSIONCHOSEN]);
			loc_registerListener();
		}));
		return out;
	};
	
	var loc_registerListener = function(){
		loc_rootChoose.registerListener(function(eventName, eventData){
			if(eventName=="change"){
				loc_updateRootTypeSelection(eventData);
			}
		});
		_.each(loc_expressionsByType, function(expression){
			expression.registerListener(function(eventName, eventData){
				if(expression.getRootType()==loc_getCurrentRootType()){
					if(eventName=="change"){
						loc_eventObject.triggerEvent(eventName);
					}
				}
			});
		});
	};

	var loc_init = function(arg1, arg2, env){
		loc_env = env;
		if(arg2!=undefined){
			loc_id = arg1;
			loc_dataDefinition = arg2;
		}
		else{
			loc_valueChooseObj = arg1;
			loc_dataDefinition = loc_valueChooseObj[node_COMMONATRIBUTECONSTANT.DATABUILD_DATADEFINITION];
		}

		loc_eventObject = node_createEventObject();

		loc_rootChoose = loc_createRootChoose(loc_dataDefinition, loc_env);
		
		loc_containerView.append(loc_rootChoose.getView());
		loc_containerView.append(loc_expressionContainerView);
		
		loc_options = loc_env.getOptions(loc_dataDefinition);

		_.each(loc_options, function(option, name){
			var rootType = option.type;
			var expression = loc_createOperandExpression(loc_id+"_"+rootType, rootType, loc_expressionContainerView, loc_env);
			loc_expressionsByType[rootType] = expression;
		});

	};

	var loc_out ={
		
		getInitRequest : function(handlers, request){
			var out;
			if(loc_valueChooseObj==undefined){
				out = loc_getInitNewRequest(handlers, request);
			}
			else{
				out = loc_getInitExistingRequest(loc_valueChooseObj, handlers, request);
			}
			return out
		},
		
		updateView : function(parentView){
			parentView.append(loc_containerView);
		},
		
		isReady : function(){
			if(loc_getCurrentRootType()==undefined)   return false;
			return loc_getCurrentExpression().isReady();
		},
		
    	registerListener : function(handler){
	    	return loc_eventObject.registerListener(undefined, undefined, handler, this);
	    },
		
		destroy : function(){
			loc_containerView.remove();
			_.each(loc_expressionsByType, function(expression){
				expression.destroy();
			});
        },
			
    	getValue : function(){
			var out = {};
			out[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSIONCHOSEN] = loc_getCurrentRootType();
			out[node_COMMONATRIBUTECONSTANT.DATABUILD_DATADEFINITION] = loc_dataDefinition;

			var expressions = {};
			
			_.each(loc_expressionsByType, function(expression, rootType){
				expressions[rootType] = expression.getValue();
			});
			out[node_COMMONATRIBUTECONSTANT.DATABUILD_EXPRESSION] = expressions;

        	return out;
	    },

	};
	
	loc_init(arg1, arg2, env);
	return loc_out;
};


var loc_createOperandExpression = function(id, rootType, parentView, env){
	var loc_eventObject = node_createEventObject();
	
	var loc_id = id;
	
	var loc_rootType = rootType;
	var loc_parentView = parentView;
	var loc_env = env;
	
	var loc_containerView = $("<ul></ul>");
	
	var loc_operandChain = [];
	
	var loc_truncate = function(wrapper){
		for(var i=loc_operandChain.length-1; i>=0; i--){
			if(loc_operandChain[i]==wrapper){
				break;
			}
			else{
				loc_operandChain[i].destroy();
				loc_operandChain.pop();
    			if(i!=0){
	    			loc_operandChain[i-1].setNextInChain();
		    	}
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
				var out1 = node_createServiceRequestInfoSequence(undefined);
				out1.addRequest(loc_addOperandRequest(loc_createOperandOperation(wrapper.getDataType(), loc_env), {
					success : function(request){
						loc_eventObject.triggerEvent("change");
					}
				}));
				node_requestServiceProcessor.processRequest(out1);
			}
			else if(eventName=="truncate"){
				loc_truncate(wrapper);
     			loc_eventObject.triggerEvent("change");
			}
		});

		out.addRequest(wrapper.getInitRequest(loc_containerView, {
			success : function(request){
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
		
		addOperandRequest : function(operand, handlers, request){
			return loc_addOperandRequest(operand, handlers, request)
		},
		
		enable : function(){	loc_enable(loc_parentView, loc_containerView);   },

		disable : function(){		loc_disable(loc_containerView);	    },

		destroy : function(){
			loc_containerView.remove();
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

		getValue : function(){
			var out = {};
    		var operand;
	    	for(var i in loc_operandChain){
		    	operand = loc_operandChain[i].getOperand().getValue(operand);
		    }
			out[node_COMMONATRIBUTECONSTANT.DATABUILDEXPRESSION_OPERAND] = operand;
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
		
		var operandType = loc_operand.getType()
		if(operandType=="constant"){
    		if(loc_nextButton!=undefined){
				loc_nextButton.disable();
			}
			return;
		}
		
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
			parentView.append(loc_containerView);
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
			loc_updateNextButton();
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(loc_operand.getInitRequest(loc_operandContainerView, {
				success : function(request){
    				loc_updateNextButton();
				}
			}));
			return out;
		},
		
		destroy : function(){
			loc_containerView.remove();
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
			loc_updateNextButton();
		}
	};
	return loc_out;
};

var loc_createNextButton = function(parentView){
	var loc_parentView = parentView;
	
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

	var loc_enabled;
	
	var loc_updateStatus = function(){
		loc_nextButtonView.text(loc_statusInfo[loc_status].title);
		loc_nextabelView.text(loc_statusInfo[loc_status].label);
	};

	loc_updateStatus();
	
	loc_nextButtonView.bind("click", function(){
		loc_eventObject.triggerEvent(loc_statusInfo[loc_status].event);
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
			loc_enable(loc_parentView, loc_containerView);
    		loc_enabled = true;
		},
		
		disable : function(){
			loc_disable(loc_containerView);
    		loc_enabled = false;
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
		    parmInfo.valueChoose.registerListener(function(eventName, eventData){
			    if(eventName=="change"){
				    loc_eventObject.triggerEvent("change");
			    }
		    });
		    out.addRequest(parmInfo.valueChoose.getInitRequest({
			    success : function(request){
				    parmInfo.valueChoose.updateView(parmInfo.view);
			    }
		    }));
		});
		return out;
	};
	
	var loc_getInitExistingOperationRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		_.each(loc_parmsObj, function(parmObj, i){
			var parmInfo = {
				"name" : parmObj.name,
				"criteria" : parmObj.criteria,
				"valueChoose" : loc_createDataChoose(parmObj.value, undefined, loc_env),
				"view" : $("<div>Container for parm: " +parmObj.name  + "</div>")
			};

			loc_containerView.append(parmInfo.view);

			loc_parms.push(parmInfo);
		});
		out.addRequest(loc_getInitParmsRequest({
			success : function(request){
				loc_eventObject.triggerEvent("change");
			}
		}));
		return out;
	};
	
	var loc_getInitNewOperationRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		_.each(loc_dataOperation.parms, function(parm){
			if(parm.isBase!="true"){
				var datadefinition = {
				    "type" : "writable",   
					"criteria" : parm.criteria,
				};
				
				var parmInfo = {
					"name" : parm.name,
					"criteria" : parm.criteria,
					"valueChoose" : loc_createDataChoose(loc_id+"_parm_"+parm.name, datadefinition, loc_env),
					"view" : $("<div>Container for parm: " +parm.name  + "</div>")
				};
				
				loc_containerView.append(parmInfo.view);
				
				loc_parms.push(parmInfo);
			}
		});
		out.addRequest(loc_getInitParmsRequest({
			success : function(request){
				loc_eventObject.triggerEvent("change");
			}
		}));
		return out;
	};
	
	var loc_deleteParms = function(){
		_.each(loc_parms, function(parmInfo){
			parmInfo.view.remove();
			parmInfo.valueChoose.destroy();
			loc_parms = [];
		});
	};
	
	var loc_getInitRequest = function(parentView, handlers, request){
		loc_parentView = parentView;
		loc_parentView.append(loc_containerView);
		
		loc_operationSelection = loc_createOperationSelection(loc_baseDataType, loc_operationName);
		loc_operationSelectionContainerView.append(loc_operationSelection.getView());

		loc_operationSelection.registerListener(function(eventName, eventData){
			if(eventName=="selectOperation"){
				loc_deleteParms();
				
				loc_dataOperation = loc_operationSelection.getCurrentDataOperation();
				loc_operationName = loc_dataOperation.name;
				loc_baseDataType = loc_dataOperation.source;

				node_requestServiceProcessor.processRequest(loc_getInitNewOperationRequest({
					success : function(request){
						loc_eventObject.triggerEvent("truncate");
					}
				}));
			}
		});

		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		out.addRequest(loc_operationSelection.getInitRequest({
			success : function(request){
				loc_dataOperation = loc_operationSelection.getCurrentDataOperation();
				loc_operationName = loc_dataOperation.name;
				loc_baseDataType = loc_dataOperation.source;
				
				if(loc_parmsObj==undefined){
					return loc_getInitNewOperationRequest();
				}
				else{
					return loc_getInitExistingOperationRequest();
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

		registerListener : function(handler){		return loc_eventObject.registerListener(undefined, undefined, handler, this);		},
		
		destroy : function(){
			_.each(loc_parms, function(parm, i){
				parm.valueChoose.destroy();
			});
			loc_containerView.remove();
		},

    	isReady : function(){
			for(var i in loc_parms){
				if(!loc_parms[i].valueChoose.isReady()){
					return false;
				}
			}
			return true;
	    },
		
		getValue : function(previous){
			var out = {};
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERAND_TYPE] = node_COMMONCONSTANT.EXPRESSION_OPERAND_OPERATION;
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_DATATYPEID] = loc_baseDataType; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_OPERATION] = loc_operationName; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_PARMS] = {}; 
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_BASE] = previous; 
			
			var parms = [];
			for(var i in loc_parms){
				var parmInfo = loc_parms[i];
				var parm = {};
				
				parm[node_COMMONATRIBUTECONSTANT.DATABUILDPARMINOPERATIONOPERAND_NAME] = parmInfo.name;
				parm[node_COMMONATRIBUTECONSTANT.DATABUILDPARMINOPERATIONOPERAND_CRITERIA] = parmInfo.criteria;
				parm[node_COMMONATRIBUTECONSTANT.DATABUILDPARMINOPERATIONOPERAND_VALUE] = parmInfo.valueChoose.getValue();
				
				parms.push(parm);
			}
			out[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDOPERATION_PARMS] = parms;
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
			loc_constantValue = arg1[node_COMMONATRIBUTECONSTANT.DEFINITIONOPERANDCONSTANT_DATA];
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

    					var out1 = node_createServiceRequestInfoSequence();
	    				out1.addRequest(nosliw.runtime.getComplexEntityService().getCreateApplicationRequest({ bundleDef: bundleDef }, undefined, {}, undefined, {
		    				success: function (requestInfo, application) {
								loc_standaloneApp = application;
     							loc_contantValueWrapperView.append(loc_standaloneApp.getView());
								
								if(loc_constantValue!=undefined){
									return loc_standaloneApp.executeExecuteCommandRequest("setData", {
									    "data" : loc_constantValue
									}, {
										success : function(request){
											loc_registerConstantListener();
										}
									});
								}
								else{
									loc_registerConstantListener();
								}
				    		}
					    }));
					    return out1;
					}
				}
			));

			return out;
		},
		
		isReady : function(){    return loc_constantValue!=undefined;       },

		disable : function(){		loc_disable(loc_containerView);	    },

		destroy : function(){
			loc_containerView.remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},

		getValue : function(previous){
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
			loc_parentView.append(loc_containerView);
			
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
		
		destroy : function(){
			loc_containerView.remove();
		},

		registerListener : function(handler){
			return loc_eventObject.registerListener(undefined, undefined, handler, this);
		},

		getValue : function(previous){
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


var node_chooseDataApp = function(dataDefinition){
	var loc_eventObject = node_createEventObject();
		
	var loc_valueChoose;
	
	var loc_containerView = $("<div>AppContainer</div>");
	
	var loc_getValue = function(){
		return loc_valueChoose.getValue();
	};

	var loc_out = {
		
		getInitRequest : function(handlers, request){
		},

		getView : function(){
			return loc_containerView;
		},
		
		getSetValueRequest : function(value, handlers, request){
			if(loc_valueChoose!=undefined){
				loc_valueChoose.destroy();
			}
			if(value==undefined){
				loc_valueChoose = loc_createDataChoose("expression", dataDefinition, loc_envObj);
			}
			else{
				loc_valueChoose = loc_createDataChoose(value, undefined, loc_envObj);
			}
			loc_valueChoose.updateView(loc_containerView);
			loc_valueChoose.registerListener(function(eventName, eventData){
				if(eventName=="change"){
    				console.log(JSON.stringify(loc_getValue()));
				}
			});
					
			return loc_valueChoose.getInitRequest(handlers, request);
		},
		
		getValue : function(){
			return loc_getValue();
		},
		
		isReady : function(){
			return loc_valueChoose.isReady();
		},
		
	};
	
	return loc_out;

};

var loc_envObj = {
	
	getOptions : function(dataDefinition){
		var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

		var constantOption = {
			"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_CONSTANT
		};
		var variableOption = {
			"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_EXPRESSION,
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
packageObj.createChildNode("chooseDataApp", node_chooseDataApp); 

})(packageObj);



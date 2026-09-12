
var loc_createOperandChooseWrapper = function(dataDefinition, env){

	var loc_dataDefinition = dataDefinition;
	var loc_env = env;
		
	var loc_operandChooses = {};
	var loc_currentType;

	var loc_containerView = $("<div></div>");

	var loc_selectChooseTypeView = $("<select></select>");

	var loc_operationChains = [];
	
		
	
	
	
	var loc_dataType;
	
	var loc_isActive;

	var loc_chooseWrapperView = $("<div></div>");
	
	var loc_options;
	
	var loc_actionsView = $("<div></div>");
	
	
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
				currentChoose = loc_createOperandChooseVariable(variables);
			}
			out.addRequest(currentChoose.getInitRequest());
		}
		
		return out;
	}
	
	var loc_getInitRequest = function(handlers, request){
		var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
		var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
		var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

		var node_basicUtility = nosliw.getNodeData("common.utility.basicUtility");
		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
		
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		
		loc_options = loc_env.getOptions(loc_dataDefinition);
		
		if(options.length>1){
			_.each(options, function(option, i){
				loc_selectChooseTypeView.append($('<option>', { value: option.type, text: option.type }));
			});
			loc_containerView.append(loc_selectChooseTypeView);

			out.addRequest(loc_getUpdateTypeSelectionRequest(options[0].type));
			
			loc_selectChooseTypeView.onChange(function(value){
				loc_getUpdateTypeSelectionRequest(value);
			});
		}
		else{
			out.addRequest(loc_getUpdateTypeSelectionRequest(options.type));
		}
		
		loc_operandChoose.requestListener(function(event){
			if(event=="selected"){
				loc_tryNext();
			}
		});
		
		return out;
	};
	
	var loc_tryNext = function(){
		//show next icon
		
	loc_operationChains.push(loc_createOperandChooseWrapper());


	createOperandChooseOperation()


	//get available operation, provide options

	//build 
	};
	
	
	var loc_onChooseChange = function(){
		//when choose done, then enable action view. people can choose from
		var chooseType = loc_operandChoose.getType();
		if(chooseType=="variable"){
			//operand
		}
    	else if(chooseType=="constant"){
    		//operand
	    }
        else if(chooseType=="operand"){
        	//operand
        }
	};
	
	var loc_out ={
		
		
		
	};
	
	return loc_out;
};


var loc_createOperandChooseConstant = function(dataDefinition){
	var loc_dataDefinition;

	var loc_data;
	var loc_chooseConstantApp;

	var loc_containerView = $("<div></div>");
	
	var loc_standaloneApp;
	
	
	
	var loc_out = {
		
		getType : function(){},
		
		getInitRequest : function(handlers, request){
			
		},
		
		registerListener : function(){
			
		},
		
		
	};
	
	return loc_out;
};

var loc_createOperandChooseVariable = function(varNames){
	var loc_varDataType;

	var loc_varName;
	var loc_operandChoose;

	var loc_containerView = $("<div></div>");
	var loc_variableChooseView = $("<input></input>");

	
	var loc_out = {
		
		getInitRequest : function(handlers, request){
			//build input control
			
			
			loc_variableChooseView.onChange({
				//emmit value change to wrapper
				
			});
		},

		registerListener : function(){
			
		},
	};

	return loc_out;
	
};

var createOperandChooseOperation = function(resultDataType, baseDataType){
	var loc_resultDataType;
	var loc_base;

	var loc_operationDef;
	var loc_parms;

	
	
    var loc_out = {
	
		getInitRequest : function(handlers, request){
			
		},

		registerListener : function(){
			
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
				"type" : "variable"
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
	
	var loc_contentWrapperView = $("<div></div>");
	
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

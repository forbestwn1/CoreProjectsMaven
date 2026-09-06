var createOperandChooseWrapper = function(dataTypeOrOperandChoose){
	
	var loc_operandChoose = operandChoose;
	var loc_dataType;
	
	var loc_isActive;

	var loc_containerView = $("<div></div>");
	var loc_chooseWrapperView = $("<div></div>");
	var loc_selectChooseTypeView = $("<Input></Input>");

	
	var loc_actionsView = $("<div></div>");
	
	
	var loc_init = function(){
		loc_selectChooseTypeView.onSelect(function(select){
			if(select=="constant"){
				loc_operandChoose = createOperandChooseConstant(loc_dataType);
			}
			else if(select=="variable"){
				loc_operandChoose = createOperandChooseVariable(loc_dataType);
			}
			else if(select=="operation"){
				loc_operandChoose = createOperandChooseOperation(loc_dataType, loc_dataType);
			}
			
		});
		
		
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


var createOperandChooseConstant = function(dataType){
	var loc_dataType;

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

var createOperandChooseVariable = function(varDataType){
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

var createOperandBuildApp = function(){
	
	
	
};

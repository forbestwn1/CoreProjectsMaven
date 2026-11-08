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

var node_envObj = function(){

	var loc_variableInfos = [
		{
			"dataType" : "test.date;1.0.0",
			"variables" : [
				{
					"name":"nosliw_today",
					"text":"today"
				}
			]
		},
		{
			"dataType" : "test.time;1.0.0",
			"variables" : [
				{
					"name":"nosliw_now",
					"text":"now"
				}
			]
		},
	];
	
	var loc_variables = [
		{
			"name":"nosliw_today",
			"text":"today"
		}];
	
	var loc_getVariablesByDataType = function(dataType){
		for(var i in loc_variableInfos){
			var varInfo = loc_variableInfos[i];
			if(varInfo.dataType==dataType){
				return varInfo.variables;
			}
		}
	};
		
		
	var loc_out = {
		getOptions : function(dataDefinition){
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

			var constantOption = {
				"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_CONSTANT,
				"text" : "Value"
			};
			var variableOptionForDate = {
				"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_EXPRESSION,
				"variables" : loc_getVariablesByDataType("test.date;1.0.0"),
    			"text" : "Expression"
			};
			var variableOptionForTime = {
				"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_EXPRESSION,
				"variables" : loc_getVariablesByDataType("test.time;1.0.0"),
				"text" : "Expression"
			};
			
			var criteria = dataDefinition[node_COMMONATRIBUTECONSTANT.DATADEFINITION_CRITERIA];
			var out = [];
			if(criteria=="test.date;1.0.0"){
				out.push(constantOption);
				out.push(variableOptionForDate);
			}
			else if(criteria=="test.time;1.0.0"){
				out.push(constantOption);
				out.push(variableOptionForTime);
			}
			else{
				out.push(constantOption);
			}
			return out;
		},

		getVariablesByDataType : function(dataType){
			return loc_getVariablesByDataType(dataType);
		},

		getVariableTextByName : function(varName){
			for(var i in loc_variableInfos){
				var variables = loc_variableInfos[i].variables;
    			for(var i in variables){
	    			if(variables[i].name==varName){
		    			return variables[i].text;
			    	}
			    }
			}
		},

		getDataTypeByVariable : function(varName){
			for(var i in loc_variableInfos){
				var variables = loc_variableInfos[i].variables;
				for(var i in variables){
					if(variables[i].name==varName){
						return loc_variableInfos[i].dataType;
			    	}
			    }
			}
		}
	};
	
	return loc_out;
}();


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
packageObj.createChildNode("envObj", node_envObj); 

})(packageObj);

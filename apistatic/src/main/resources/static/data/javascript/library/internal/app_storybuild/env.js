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

	var loc_variables = [
		{
			"name":"nosliw_today",
			"text":"today"
		}];
		
	var loc_out = {
		getOptions : function(dataDefinition){
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");

			var constantOption = {
				"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_CONSTANT
			};
			var variableOption = {
				"type" : node_COMMONCONSTANT.DATABUILD_CHOSEN_EXPRESSION,
				"variables" : loc_variables
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
				return 	[
					{
						"name":"nosliw_today",
						"text":"today"
					}];
			};
		},

		getVariableTextByName : function(name){
			for(var i in loc_variables){
				if(loc_variables[i].name==name){
					return loc_variables[i].text;
				}
			}
		},

		getDataTypeByVariable : function(varName){
			if(varName=="nosliw_today"){
				return "test.date;1.0.0";
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

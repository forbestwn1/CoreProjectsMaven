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

var loc_dataBuild = {
	        "expressionChosen": "expression",
	        "expression": {
	            "constant": {
	                "operand": {
	                    "type": "constant",
	                    "data": {
	                        "dataTypeId": "test.date;1.0.0",
	                        "value": {
	                            "year": 2026,
	                            "month": 9,
	                            "date": 22
	                        }
	                    }
	                }
	            },
	            "expression": {
	                "operand": {
	                    "type": "operation",
	                    "dataTypeId": "test.date;1.0.0",
	                    "operation": "nextDate",
	                    "parms": [],
	                    "base": {
	                        "type": "operation",
	                        "dataTypeId": "test.date;1.0.0",
	                        "operation": "NDatesLater",
	                        "parms": [{
	                                "name": "n",
	                                "criteria": "test.integer;1.0.0",
	                                "value": {
	                                    "expressionChosen": "constant",
	                                    "expression": {
	                                        "constant": {
	                                            "operand": {
	                                                "type": "constant",
	                                                "data": {
	                                                    "dataTypeId": "test.integer;1.0.0",
	                                                    "value": 2
	                                                }
	                                            }
	                                        }
	                                    }
	                                }
	                            }
	                        ],
	                        "base": {
	                            "type": "operation",
	                            "dataTypeId": "test.date;1.0.0",
	                            "operation": "lastDate",
	                            "parms": [],
	                            "base": {
	                                "type": "variable",
	                                "variableName": "today"
	                            }
	                        }
	                    }
	                }
	            }
	        }
		};


			
var loc_dataDefinition = 
{
	        "criteria": "test.date;1.0.0",
	        "type": "writable"
};




var node_utility = function(){
	
	var loc_out = {
		
		getSampleDdataDefinition : function(){
			return loc_dataDefinition;
		},

		getSampleDataBuild : function(){
			return loc_dataBuild;
		},
		
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
packageObj.createChildNode("utility", node_utility); 

})(packageObj);

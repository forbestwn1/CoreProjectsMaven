//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSet;
	var node_createServiceRequestInfoSimple;
	var node_ServiceInfo;
	var node_createTagUITest;
	var node_requestServiceProcessor;
	var node_complexEntityUtility;
	var node_basicUtility;
	var node_createHandleEachElementProcessor;
	var node_namingConvensionUtility;
	var node_ruleUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestTime = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_out = {

		preInit : function(handlers, request){
		},
				
		updateView : function(currentData){
			if(currentData!=undefined){
				var value = currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
				loc_dataView.val(value.hour+":"+value.minute+":"+value.second);
			}
			else{
				loc_dataView.val();
			}
		},

		initViews : function(handlers, request){
			loc_dataView = $('<input type="time"></input>');

			loc_dataView.bind('change', function(){
				var date = new Date(loc_dataView.val());

				var segs = date.split(":");
				
				var currentData = {
					dataTypeId: "test.time;1.0.0",
					value: {
						hour : segs[0],
						minute : segs[1],
						second : segs[2]
					}
				};
				loc_envObj.onDataChange(currentData);
			});
			return loc_dataView;
		}
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){	node_createServiceRequestInfoSet = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("uitag.test.createTagUITest", function(){node_createTagUITest = this.getData();	});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.complexEntityUtility", function(){node_complexEntityUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("variable.orderedcontainer.createHandleEachElementProcessor", function(){node_createHandleEachElementProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("common.namingconvension.namingConvensionUtility", function(){node_namingConvensionUtility = this.getData();});
nosliw.registerSetNodeDataEvent("rule.ruleUtility", function(){node_ruleUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_time", node_createUICustomerTagTestTime); 

})(packageObj);

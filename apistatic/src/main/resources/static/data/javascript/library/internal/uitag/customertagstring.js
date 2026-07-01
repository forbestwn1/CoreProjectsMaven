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

var node_createUICustomerTagTestString = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_dataViewForEnum;
	
	var loc_enumDatas;
	
	var loc_out = {

		preInit : function(handlers, request){
			if(loc_envObj.isDataEnum()!=null){
				var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
				out.addRequest(envObj.getDataEnumRequest({
					success : function(request, enumDatas){
						loc_enumDatas = enumDatas;
					}
				}));
				return out;
			}
			
		},
				
		updateView : function(currentData){
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			if(loc_dataView!=undefined){
				loc_dataView.val(value);
			}
			else if(loc_dataViewForEnum!=undefined){
				loc_dataViewForEnum.val(value);
			}
		},

		initViews : function(handlers, request){
			if(envObj.isDataEnum()!=null){
				loc_dataViewForEnum = $('<select name="data"/>');
				for(var k in loc_enumDatas){
					var dataValue = loc_enumDatas[k][node_COMMONATRIBUTECONSTANT.DATA_VALUE];
					loc_dataViewForEnum.append($('<option key='+ k + ' value='+dataValue+'>' + dataValue +'</option>'));
				}
				loc_dataViewForEnum.bind('change', function(){
					var currentData = {
						dataTypeId: "test.string;1.0.0",
						value: loc_dataViewForEnum.val()
					};
					loc_envObj.onDataChange(currentData);
				});
				return loc_dataViewForEnum;
			}
			else{
				loc_dataView = $('<input type="text" style="border:solid 1px;" data-role="none" placeholder="event from reference customer tag"></input>');
				loc_dataView.bind('change', function(){
					var currentData = {
						dataTypeId: "test.string;1.0.0",
						value: loc_dataView.val()
					};
					loc_envObj.onDataChange(currentData);
				});
    			return loc_dataView;
			}
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
packageObj.createChildNode("debug_test_string", node_createUICustomerTagTestString); 

})(packageObj);

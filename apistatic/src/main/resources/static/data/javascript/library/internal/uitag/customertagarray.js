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

var node_createUICustomerTagTestArray = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	var loc_wrapperView;
	
	var loc_elements = [];
	
	var loc_dataVariable;
	
	    var loc_presentArrayRequest = function(arrayVariable, wrapperView, handlers, requestInfo){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, requestInfo);

			loc_handleEachElementProcessor = node_createHandleEachElementProcessor(arrayVariable, ""); 
			
			out.addRequest(loc_handleEachElementProcessor.getLoopRequest({
				success : function(requestInfo, eles){
					var addEleRequest = node_createServiceRequestInfoSequence(undefined, handlers, requestInfo);
					_.each(eles, function(ele, index){
						var variationPoints = {
							afterValueContext: function(complexEntityDef, valuePortContainerId, bundleCore, coreConfigure){
								var valuePortContainer = bundleCore.getValuePortDomain().getValuePortContainer(valuePortContainerId);
								var valueStructureRuntimeId = valuePortContainer.getValueStructureRuntimeIdByName("embeded_part1");
								var valueStructure = valuePortContainer.getValueStructure(valueStructureRuntimeId);
								valueStructure.addVariable(ele.elementVar, loc_envObj.getAttributeValue("arrayelement"));
								valueStructure.addVariable(ele.indexVar, loc_envObj.getAttributeValue("arrayindex"));
							}
						}
						addEleRequest.addRequest(loc_envObj.getCreateDefaultUIContentWithInitRequest(variationPoints, wrapperView, {
							success: function(request, uiConentNode){
	//							loc_elements.push(uiConentNode.getChildValue().getCoreEntity());
							}
						}));
					});
					addEleRequest.setParmData("processMode", "promiseBased");
					return addEleRequest;
				}
			}));
			
			return out;
		};
	
	
	var loc_out = {

		created : function(){
		},
		preInit : function(handlers, request){
			loc_dataVariable = loc_envObj.createVariableByName(loc_envObj.getAttributForData()[0]);
			
		},
		initViews : function(handlers, request){
			loc_dataView = $("<div/>");
			loc_wrapperView = $("<div/>");
			loc_dataView.append(loc_wrapperView);
			return loc_dataView;
		},
		postInit : function(request){
			return loc_presentArrayRequest(loc_dataVariable, loc_wrapperView);
		},
		destroy : function(request){
		},
		
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
packageObj.createChildNode("debug_test_array", node_createUICustomerTagTestArray); 

})(packageObj);

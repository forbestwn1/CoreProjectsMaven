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

var node_createUICustomerTagTestError = function(envObj){
	var loc_envObj = envObj;

	var loc_containerrView;

	var wrapperView = $("<div></div>");	
	var eventView = $('<textarea rows="2" cols="150" style="resize: none; border:solid 1px;" data-role="none" placeholder="event from reference customer tag"></textarea>');
	var errorView = $("<span style='color: red;' ></span>");

    var loc_referencedCustomTags;

    var loc_processReferencedCustomerTag = function(){
		var query = {
			elements : []
		};
		var queryStr = loc_envObj.getAttributeValue("reference_tag");
		if(queryStr!=undefined){
			var parmSegs = node_namingConvensionUtility.parseLevel1(queryStr);
			_.each(parmSegs, function(parmSeg){
    			var segs = node_namingConvensionUtility.parsePart(parmSeg);
				query.elements.push({
					key : segs[0],
					value : segs[1]
				});
			});
    		loc_referencedCustomTags = loc_envObj.queryCustomTagInstance(query);
    		_.each(loc_referencedCustomTags, function(customTag, i){
				customTag.registerEventListener(undefined, function(event, eventData){
					eventView.val(node_basicUtility.stringify({
						event : event,
						eventData : eventData
					}));

					var erroeMsg = "";					
					if(event==node_CONSTANT.ERROR_VALIDATION_VALUE){
						_.each(eventData, function(item){
							_.each(item.resultValue, function(r){{
								erroeMsg = erroeMsg + r.resultData.value + "\n";
							}});
						});
						
						errorView.text(erroeMsg);
						errorView.show();
					}
					else if(event==node_CONSTANT.EVENT_UI_VALUE_CHANGE){
						errorView.text("");
						errorView.hide();
					}
				});
			});
		}
	};

	var loc_out = {
		
		initViews : function(handlers, request){
			wrapperView.append(eventView);
			wrapperView.append(errorView);
			errorView.hide();
            loc_processReferencedCustomerTag();
			return wrapperView;
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
packageObj.createChildNode("debug_test_error", node_createUICustomerTagTestError); 

})(packageObj);

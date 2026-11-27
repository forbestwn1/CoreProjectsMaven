//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONCONSTANT;
var node_basicUtility;
var node_namingConvensionUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestError = function(envObj){
	var loc_envObj = envObj;

	var loc_containerrView;

	var wrapperView = $("<div></div>");	
	var eventView = $('<textarea rows="2" cols="150" style="resize: none; border:solid 1px;" data-role="none" placeholder="event from reference customer tag"></textarea>');
	var errorView = $("<span style='color: red;' ></span>");

    var loc_referencedCustomTags;

	var loc_processValidationResult = function(result){
		var erroeMsg = "";					
		_.each(result.resultValue, function(r){{
			var description = r.description;
			if(description==undefined){
				description = r.resultData.value;
			}
			
			erroeMsg = erroeMsg + description + "\n";
		}});
		return erroeMsg;
	};
	
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
    		loc_referencedCustomTags = loc_envObj.queryCustomTagInstanceUpwards(query);
    		_.each(loc_referencedCustomTags, function(customTag, i){
				customTag.registerEventListener(undefined, function(event, eventData){
					eventView.val(node_basicUtility.stringify({
						event : event,
						eventData : eventData
					}));

					var erroeMsg = "";					
					if(event==node_COMMONCONSTANT.ERROR_VALIDATION_VALUE){
						if(node_basicUtility.isArray(eventData)){
							_.each(eventData, function(item){
								erroeMsg = erroeMsg + loc_processValidationResult(item);
							});
							
						}
						else{
							erroeMsg = erroeMsg + loc_processValidationResult(eventData);
						}
/*						
						_.each(eventData, function(item){
							_.each(item.resultValue, function(r){{
								var description = r.description;
								if(description==undefined){
									description = r.resultData.value;
								}
								
								erroeMsg = erroeMsg + description + "\n";
							}});
						});
*/						
						errorView.text(erroeMsg);
						errorView.show();
					}
					else if(event==node_COMMONCONSTANT.EVENT_UI_VALUE_CHANGE){
						errorView.text("");
						errorView.hide();
					}
				});
			});
		}
	};

	var loc_out = {
		
		initViews : function(handlers, request){
//			wrapperView.append(eventView);
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
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.namingconvension.namingConvensionUtility", function(){node_namingConvensionUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_error", node_createUICustomerTagTestError); 

})(packageObj);

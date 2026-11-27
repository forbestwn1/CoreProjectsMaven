//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_createServiceRequestInfoSequence;
var node_requestServiceProcessor;
var node_createHandleEachElementProcessor;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestArrayReadOnly = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	var loc_elementWrapperView;
	var loc_emptyOutputView;
	
	var loc_dataVariable;
	
	var loc_updateView = function(){
		loc_removeElements();
		
		var request = loc_presentArrayRequest(loc_dataVariable, loc_elementWrapperView);
		node_requestServiceProcessor.processRequest(request);
	};
	
	var loc_removeElements = function(){
		loc_envObj.removeAllContent();
	};
	
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
						addEleRequest.addRequest(loc_envObj.getCreateDefaultUIContentWithInitRequest(index, variationPoints, wrapperView, {
							success: function(request, uiConentNode){
							}
						}));
					});
					addEleRequest.setParmData("processMode", "promiseBased");
					
					if(eles.length>0){
						loc_emptyOutputView.hide();
					}
					else{
						loc_emptyOutputView.show();
					}
					
					return addEleRequest;
				}
			}));
			
			return out;
		};
	
	
	var loc_out = {

		created : function(){},
		
		preInit : function(request){
			loc_dataVariable = loc_envObj.createVariableByName(envObj.getAttributForData()[0]);
		},
		
		initViews : function(handlers, request){
			loc_dataView = $("<div/>");
			loc_elementWrapperView = $("<div/>");
			loc_emptyOutputView = $("<span>No result</span>");
			loc_dataView.append(loc_elementWrapperView);
			loc_dataView.append(loc_emptyOutputView);
			loc_emptyOutputView.hide();
			return loc_dataView;
		},
		
		postInit : function(request){
			loc_updateView(request);
			
			loc_dataVariable.registerDataChangeEventListener(undefined, function(event, eventData, request){
				loc_updateView(request);
			}, this);
		},
		
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("variable.orderedcontainer.createHandleEachElementProcessor", function(){node_createHandleEachElementProcessor = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_data_array_readonly", node_createUICustomerTagTestArrayReadOnly); 

})(packageObj);

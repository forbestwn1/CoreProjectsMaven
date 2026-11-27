//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_createServiceRequestInfoSequence;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagStructureFrame = function(envObj){
	var loc_envObj = envObj;

    var loc_containerView = $("<div></div>");

	var loc_removeElements = function(){
		_.each(loc_elements, function(element, i){
			element.destroy();
		});
		loc_elements = [];
	};
	
	var loc_out = {

		created : function(){},
		
		preInit : function(request){
			var out = node_createServiceRequestInfoSequence(undefined, undefined, request);
			out.addRequest(loc_envObj.getCreateDefaultUIContentWithInitRequest(undefined, undefined, loc_containerView, {
				success: function(request, uiConentNode){
				}
			}));
			return out;
		},
		
		initViews : function(handlers, request){
			return loc_containerView;
		},
		
		postInit : function(request){
		},
		
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});

//Register Node by Name
packageObj.createChildNode("debug_test_structure_frame", node_createUICustomerTagStructureFrame); 

})(packageObj);

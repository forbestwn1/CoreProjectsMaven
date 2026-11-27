//get/create package
var packageObj = library.getChildPackage();    


(function(packageObj){
	//get used node
var node_componentUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createPackageDebugView = function(id, color){
	
	var loc_view = $('<div style="border-width:thick; border-style:solid; border-color:'+color+'">'+id+'</div>');
	var loc_wrapperView = $('<div style="border-style:dotted; border-color:red"></div>');
	loc_view.append(loc_wrapperView);
	
	var loc_content = "";

	var loc_out = {

		updateRuntimeContext : function(runtimeContext){   
			$(runtimeContext.view).append(loc_view);
			
			return node_componentUtility.makeNewRuntimeContext(runtimeContext, {
				view : loc_wrapperView.get()
			});
		}
	};
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("component.componentUtility", function(){node_componentUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createPackageDebugView", node_createPackageDebugView); 

})(packageObj);

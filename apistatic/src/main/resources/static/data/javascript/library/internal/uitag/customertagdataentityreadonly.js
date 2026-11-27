//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_basicUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestEntityReadOnly = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_currentData;
	
	var loc_out = {
		
		updateView : function(currentData){
			loc_currentData = currentData;
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			var content = "";
			if(value!=undefined){
				var contentArray = [];
				if(node_basicUtility.isArray(value)){
					_.each(value, function(item, i){
						contentArray.push(item.name);
					});
					content = JSON.stringify(contentArray);
				}
				else{
					content = value.name;
				}
			}
			
			loc_dataView.html(content);
		},

		initViews : function(handlers, request){
			loc_dataView = $('<div>Multiple Readonly View !!!!!!</div>');
			return loc_dataView;
		},

    	getCurrentData : function(){
	    	return loc_currentData;
	    }
			
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_data_entity_readonly", node_createUICustomerTagTestEntityReadOnly); 

})(packageObj);

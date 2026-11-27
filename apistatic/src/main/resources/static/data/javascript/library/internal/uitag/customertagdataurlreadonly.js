//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestUrlReadOnly = function(envObj){
	var loc_envObj = envObj;

	var loc_containerView;
    var loc_dataView;
	
	var loc_currentData;
	
	var loc_out = {
		
		updateView : function(currentData){
			loc_currentData = currentData;
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			loc_dataView = $("<a target='_blank' href='"+value+"'>Link</a>");
			loc_containerView.append(loc_dataView);

//			loc_dataView.attr('href', value);
		},

		initViews : function(handlers, request){
			loc_containerView = $('<div></div>');
//			loc_dataView = $('<a target="_blank" rel="noopener noreferrer">Link</a>');
			return loc_containerView;
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

//Register Node by Name
packageObj.createChildNode("debug_test_data_url_readonly", node_createUICustomerTagTestUrlReadOnly); 

})(packageObj);

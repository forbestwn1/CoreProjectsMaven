//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestDateReadOnly = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_currentData;
	
	var loc_out = {
		
		updateView : function(currentData){
			loc_currentData = currentData;
			if(currentData!=undefined){
				var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
				var date = new Date(value.year, value.month, value.date);
				loc_dataView.html(date.toLocaleDateString('en-CA') + "  " + date.toLocaleDateString('en-US', { weekday: 'long' }));
			}
			else{
				loc_dataView.html("");
			}
		},

		initViews : function(handlers, request){
			loc_dataView = $('<div></div>');
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

//Register Node by Name
packageObj.createChildNode("debug_test_data_date_readonly", node_createUICustomerTagTestDateReadOnly); 

})(packageObj);

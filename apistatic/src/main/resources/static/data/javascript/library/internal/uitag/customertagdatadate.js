//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestDate = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_getCurrentData = function(){
		var val = loc_dataView.val();
		if(val==""||val==undefined){
			return;
		}
		
		var date = new Date(val+"T00:00:00");
		var currentData = {
			dataTypeId: "test.date;1.0.0",
			value: {
				year : date.getFullYear(),
				month : date.getMonth(),
				date : date.getDate()
			}
		};
		return currentData;
	};

	
	var loc_out = {

		preInit : function(handlers, request){
		},
				
		updateView : function(currentData){
			if(currentData!=undefined){
				var value = currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
				var date = new Date(value.year, value.month, value.date);
    			loc_dataView.val(date.toLocaleDateString('en-CA'));
			}
			else{
				loc_dataView.val();
			}
		},

		initViews : function(handlers, request){
			loc_dataView = $('<input type="date"></input>');

			loc_dataView.bind('change', function(){
				loc_envObj.onDataChange(loc_getCurrentData());
			});
			
			return loc_dataView;
		},

    	getCurrentData : function(){
			return loc_getCurrentData();
	    }
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_data_date", node_createUICustomerTagTestDate); 

})(packageObj);

//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestTimeReadOnly = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_currentData;
	
	var loc_out = {

    	updateView : function(currentData){
			loc_currentData = currentData;
	    	if(currentData!=undefined){
		    	var value = currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
				var hour = value.hour==undefined?0:value.hour;
				var minute = value.minute==undefined?0:value.minute;
				var second = value.second==undefined?0:value.second;
			    loc_dataView.html(hour.toString().padStart(2, "0")+":"+minute.toString().padStart(2, "0")+":"+second.toString().padStart(2, "0"));
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
packageObj.createChildNode("debug_test_data_time_readonly", node_createUICustomerTagTestTimeReadOnly); 

})(packageObj);

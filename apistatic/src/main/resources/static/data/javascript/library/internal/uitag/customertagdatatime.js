//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestTime = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_getCurrentData = function(){
		var date = loc_dataView.val();
		if(date==""||date==undefined){
			return;
		}

		var segs = date.split(":");
		return {
			dataTypeId: "test.time;1.0.0",
			value: {
				hour : parseInt(segs[0]),
				minute : segs.length>=2?parseInt(segs[1]):0,
				second : segs.length>=3?parseInt(segs[2]):0
			}
		};
	};
	
	var loc_out = {

		preInit : function(handlers, request){
		},
				
		updateView : function(currentData){
			if(currentData!=undefined){
				var value = currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
				
				var hour = value.hour==undefined?0:value.hour;
				var minute = value.minute==undefined?0:value.minute;
				var second = value.second==undefined?0:value.second;
				
				loc_dataView.val(hour.toString().padStart(2, "0")+":"+minute.toString().padStart(2, "0")+":"+second.toString().padStart(2, "0"));
			}
			else{
				loc_dataView.val();
			}
		},

		initViews : function(handlers, request){
			loc_dataView = $('<input type="time"></input>');

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
packageObj.createChildNode("debug_test_data_time", node_createUICustomerTagTestTime); 

})(packageObj);

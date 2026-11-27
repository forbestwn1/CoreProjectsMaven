//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestInteger = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_getCurrentData = function(){
		return {
			dataTypeId: "test.integer;1.0.0",
			value: parseInt(loc_dataView.val())
		};
	};
	
	var loc_out = {

		preInit : function(handlers, request){
		},
				
		updateView : function(currentData){
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			loc_dataView.val(value+"");
		},

		initViews : function(handlers, request){
			loc_dataView = $('<input type="number" style="border:solid 1px;" data-role="none" placeholder="integer type value"></input>');

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
packageObj.createChildNode("debug_test_data_integer", node_createUICustomerTagTestInteger); 

})(packageObj);

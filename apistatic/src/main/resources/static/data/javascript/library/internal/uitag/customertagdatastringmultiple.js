//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSequence;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestStringMultiple = function(envObj){
	var loc_envObj = envObj;

    var loc_dataView;
	
	var loc_dataViewForEnum;
	
	var loc_enumDatas;
	
	var loc_getCurrentData = function(){
		return {
			isMultipleValue : true,
			dataTypeId: "test.string;1.0.0",
			value: loc_dataViewForEnum.val()
		};
	};
	
	var loc_out = {

		preInit : function(handlers, request){
			if(loc_envObj.isDataEnum()!=null){
				var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
				out.addRequest(envObj.getDataEnumRequest({
					success : function(request, enumDatas){
						loc_enumDatas = enumDatas;
					}
				}));
				return out;
			}
			
		},
				
		updateView : function(currentData){
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			if(loc_dataViewForEnum!=undefined){
				loc_dataViewForEnum.val(value);
			}
		},

		initViews : function(handlers, request){
			
			if(envObj.isDataEnum()!=null){
				loc_dataViewForEnum = $('<select name="data" multiple  style="border:solid 1px;" />');
				for(var k in loc_enumDatas){
					var dataValue = loc_enumDatas[k][node_COMMONATRIBUTECONSTANT.DATA_VALUE];
					loc_dataViewForEnum.append($('<option key="'+ k + '" value="'+dataValue+'">' + dataValue +'</option>'));
				}
				loc_dataViewForEnum.bind('change', function(){
					loc_envObj.onDataChange(loc_getCurrentData());
				});
				return loc_dataViewForEnum;
			}
			else{
				return $("<div>Multiple string view without enum!!!!!!!!</div>");
			}
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
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});

//Register Node by Name
packageObj.createChildNode("debug_test_data_string_multiple", node_createUICustomerTagTestStringMultiple); 

})(packageObj);

//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSet;
	var node_createServiceRequestInfoSimple;
	var node_ServiceInfo;
	var node_createTagUITest;
	var node_requestServiceProcessor;
	var node_complexEntityUtility;
	var node_basicUtility;
	var node_createHandleEachElementProcessor;
	var node_namingConvensionUtility;
	var node_ruleUtility;
	var node_createEntityCodeTable;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUICustomerTagTestEntity = function(envObj){
	var loc_envObj = envObj;

	var loc_containerView = $('<div>Hello Entity</div>');
	var loc_valueContainerView = $('<div/>');
	var loc_selectionContainerView = $('<div/>');
	loc_containerView.append(loc_valueContainerView);
	loc_containerView.append(loc_selectionContainerView);

	var loc_categorySelViews = [];
	var loc_dataSelView;
	
	var loc_dataInfo = [];
	
	var loc_codeTableEntity;
	var loc_categoryNames;

	var loc_updateCategory = function(index){
		//build current path		
		var categoryPath = [];
		for(var i=0; i<=index; i++){
			categoryPath.push(loc_categorySelViews[i].val());
		}
		
		//children
		var children = loc_codeTableEntity.getChildrenByPath(categoryPath);
		if(index<loc_categoryNames.length-1){
			//next category
			for(var i=index+1; i<loc_categorySelViews.length; i++){
				var categoryView = loc_categorySelViews[i];
				categoryView.empty();
				if(i==index+1){
					_.each(children, function(child){
						categoryView.append($("<option value='"+child+"'>"+child+"</option>"));
					});
				}
				else{
					categoryView.disable();
				}
			}
		}
		else{
			//entity
			loc_dataSelView.empty();
			_.each(children, function(child){
				var value = child.value;
				loc_dataSelView.append($("<option value='"+value.id+"'>"+value.name+"</option>"));
			});
		}
		
	};
		
	var loc_initCodeTableEntity = function(){
		loc_categoryNames = loc_codeTableEntity.getCategoryNames();
		_.each(loc_categoryNames, function(categoryName, i){
			var categoryView = $("<select name='"+i+"'/>");
			categoryView.on("change", function(){
				loc_updateCategory(i);
			});
			loc_categorySelViews.push(categoryView);
			loc_selectionContainerView.append(categoryView);
		});

		loc_dataSelView = $('<select name="data"/>');
		loc_dataSelView.on("change", function(){
			loc_selectEntity(loc_dataSelView.val());
		});
		loc_containerView.append(loc_dataSelView);
		
		loc_updateCategory(-1);
	}
	
	var loc_selectEntity = function(entityId){
		var entity = loc_codeTableEntity.getEntityById(entityId);
		loc_addValue(entity.value);
	};
	
	var loc_onValueChange = function(){
		var isMutipleValue = loc_isMutipleValue();
		
		var value;
		if(isMutipleValue){
			value = [];
			_.each(loc_dataInfo, function(dataInfo, i){
				value.push(dataInfo.value);
			});
		}
		else{
			value = loc_dataInfo.length==0? undefined : loc_dataInfo[0].value;
		}
		
		var currentData = {
			isMultipleValue : isMutipleValue,
			dataTypeId: "test.entity;1.0.0",
			value: value
		};
		loc_envObj.onDataChange(currentData);
	};
	
	var loc_removeValue = function(dataInfo){
		const index = loc_dataInfo.indexOf(dataInfo);
		if (index !== -1) {
		  loc_dataInfo.splice(index, 1);
		}
	};
	
	var loc_addValue = function(value, informEx){
		var dataInfo = {};
		dataInfo.value = value;
		dataInfo.view = $("<button>"+value.name+"  x"+"</button>");
		loc_valueContainerView.append(dataInfo.view);
		dataInfo.view.on("click", function(){
			loc_removeValue(dataInfo);
			loc_onValueChange();
		});
		
		if(loc_isMutipleValue()){
			loc_dataInfo.push(dataInfo);
		}
		else{
			loc_removeValue(loc_dataInfo[0]);
		}
		if(informEx!=false) loc_onValueChange();
	};
	
	var loc_updateDateInfo = function(values, informEx){
		_.each(loc_dataInfo, function(dataInfo, i){
			dataInfo.view.remove();
		});
		loc_dataInfo = [];
		_.each(values, function(value, i){
			loc_addValue(value, informEx);
		});
	};
	
	var loc_isMutipleValue = function(){
		return loc_envObj.getDataMode()==node_COMMONCONSTANT.UITAG_DATAMODE_MULTIPLE;
	};
	
	var loc_out = {

		preInit : function(handlers, request){
			if(loc_envObj.isDataEnum()!=null){
				var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
				out.addRequest(envObj.getCodeTableRequest({
					success : function(request, codeTable){
						loc_codeTableEntity = node_createEntityCodeTable(codeTable);
						loc_initCodeTableEntity();
					}
				}));
				return out;
			}
		},
				
		updateView : function(currentData){
			var value = currentData==undefined?undefined:currentData[node_COMMONATRIBUTECONSTANT.DATA_VALUE];
			loc_updateDateInfo(value, false);
		},

		initViews : function(handlers, request){
			return loc_containerView;
		}
	};
	
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){	node_createServiceRequestInfoSet = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("uitag.test.createTagUITest", function(){node_createTagUITest = this.getData();	});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("complexentity.complexEntityUtility", function(){node_complexEntityUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("variable.orderedcontainer.createHandleEachElementProcessor", function(){node_createHandleEachElementProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("common.namingconvension.namingConvensionUtility", function(){node_namingConvensionUtility = this.getData();});
nosliw.registerSetNodeDataEvent("rule.ruleUtility", function(){node_ruleUtility = this.getData();});
nosliw.registerSetNodeDataEvent("enum.createEntityCodeTable", function(){node_createEntityCodeTable = this.getData();});

//Register Node by Name
packageObj.createChildNode("debug_test_data_entity", node_createUICustomerTagTestEntity); 

})(packageObj);

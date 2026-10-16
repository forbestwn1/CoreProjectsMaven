//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_basicUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createEntityCodeTable = function(entityCodeTable){

	var loc_entityName;
	var loc_categoryNames = [];
	var loc_entitysByCategary;
	var loc_entityById = {};
	
	var loc_init = function(entityCodeTable){
		loc_entityName = entityCodeTable.name;
		loc_categoryNames = entityCodeTable.info.category;
		var dataSet = entityCodeTable[node_COMMONATRIBUTECONSTANT.DATARULEENUM_DATASET];
		
		if(loc_categoryNames==undefined){
			loc_entitysByCategary = dataSet;
		}
		else{
			loc_entitysByCategary = {};
    		_.each(dataSet, function(entity, i){
				loc_entityById[entity.value.id] = entity;
				var value = entity.value;
				var node = loc_entitysByCategary;
				for(var i=0; i<loc_categoryNames.length; i++){
					var name = value[loc_categoryNames[i]];
					var child = node[name];
					if(child==undefined){
						if(i==loc_categoryNames.length-1){
							child = [];
						}
						else{
							child = {};
						}
						node[name] = child;
					}
					node = child;
				}
				node.push(entity);
	    	});
		}
		
	};
	
	var loc_getAllEntities = function(node, output){
		if(node==undefined)   return;
		if(node_basicUtility.isArray(node)){
			output.push(node);
		}
		else{
			_.each(node, function(child, key){
				loc_getAllEntities(child, output);
			});
		}
	};
	
	var loc_out = {

		getEntityName : function(){    return loc_entityName;      },
		
		getEntityById : function(id){
			return loc_entityById[id];
		},
		
		getCategoryNames: function(){
			return loc_categoryNames;
		},
		
		getChildrenByPath : function(pathArray){
			var node = loc_entitysByCategary;
			for(var i in pathArray){
				node = node[pathArray[i]];
				if(node==undefined)   return;
			}
			if(node_basicUtility.isArray(node)){
				return node;
			}
			else{
				var out = [];
				_.each(node, function(child, i){
					out.push(i);
				});
				return out;
			}
		},
		
    	getEntitysByCategorys: function(categorys){
			if(categorys==undefined){
				return this.getEntitys();
			}
			else{
				var node = loc_entitysByCategary;
				for(var i in categorys){
					if(node==undefined) 	return;
					node = node[categorys[i]];
				}
    			var out = [];
	    		loc_getAllEntities(node, out);
		    	return out;
			}
	    },

    	getEntitys: function(){
			var out = [];
			loc_getAllEntities(loc_entitysByCategary, out);
			return out;
	    },

	};

	loc_init(entityCodeTable);
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createEntityCodeTable", node_createEntityCodeTable); 

})(packageObj);

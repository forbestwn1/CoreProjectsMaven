//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONCONSTANT;
	var node_COMMONATRIBUTECONSTANT;

//*******************************************   Start Node Definition  **************************************
	
//interface for ui tag core object
var node_buildStandaloneUITagRequest = function(dataDefinition, dataUITagId, attributes, handlers, request){
	
	
	
	
	
	var loc_out = {
		
		
		
		updataView : function(view){
			
		}
		
	};
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});

//Register Node by Name
packageObj.createChildNode("buildStandaloneUITagRequest", node_buildStandaloneUITagRequest); 

})(packageObj);

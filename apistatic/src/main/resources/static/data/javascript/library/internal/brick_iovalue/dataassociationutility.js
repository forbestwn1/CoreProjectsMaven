//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

var node_dataAssociationUtility = function(){
	
	var loc_out = {

		buildDataAssociationName : function(sourceType, sourceName, targetType, targetName){
			return sourceType+"_"+sourceName+"---"+targetType+"_"+targetName;
		},
		
	};
		
	return loc_out;
}();

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data

//Register Node by Name
packageObj.createChildNode("dataAssociationUtility", node_dataAssociationUtility); 

})(packageObj);

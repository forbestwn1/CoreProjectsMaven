//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	
//store simple value in client side
var node_createStoreService = function(){
	var loc_out = {
		saveData : function(domain, id, data){
			localStorage.setItem(domain+"_"+id, JSON.stringify(data));
		},
		
		retrieveData : function(domain, id){
			return JSON.parse(localStorage.getItem(domain+"_"+id));
		},
		
		clearData : function(domain, id){
			return localStorage.removeItem(domain+"_"+id);
		}
	}
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data

//Register Node by Name
packageObj.createChildNode("createStoreService", node_createStoreService); 

})(packageObj);

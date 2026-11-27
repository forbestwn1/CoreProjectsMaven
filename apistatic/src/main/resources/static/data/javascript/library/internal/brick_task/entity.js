//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

//task result 
//  resultName : name of the result
//  result: result value map (value name / value)
var node_TaskResult = function(resultName, resultValue){
	this.resultName = resultName;
	this.resultValue = resultValue; 
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data

//Register Node by Name
packageObj.createChildNode("TaskResult", node_TaskResult); 

})(packageObj);

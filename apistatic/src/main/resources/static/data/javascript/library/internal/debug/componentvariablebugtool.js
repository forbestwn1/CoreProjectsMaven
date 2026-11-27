//get/create package
var packageObj = library.getChildPackage();    


(function(packageObj){
	//get used node
var node_requestServiceProcessor;
var node_valueInVarOperationServiceUtility;
	
//*******************************************   Start Node Definition  ************************************** 	

var loc_createComponentVariableInspection = function(){
	var loc_vueComponent = {
		data : function(){
			return {
				variableId : "",
				variableValue : "",
			};
		},
		methods : {
			getVariableValue : function(event){
				var that = this;
				var varInfo = nosliw.runtime.getVariableManager().getVariableInfo(that.variableId);
				if(varInfo!=undefined){
					var request = varInfo.variable.getDataOperationRequest(node_valueInVarOperationServiceUtility.createGetOperationService(), {
						success : function(request, data){
							that.variableValue = JSON.stringify(data.value, null, 4);
						}
					});
					node_requestServiceProcessor.processRequest(request, false);
					
				}
			},
		},
		props : ['data'],
		template : `
			<div>
				<div>
				  <input v-model="variableId" placeholder="variable id"/>
				  <button v-on:click="getVariableValue">GetValue</button>
				  <textarea v-model="variableValue"  rows="30" cols="150"></textarea>
				</div>
		    </div>
		`
	};
	return loc_vueComponent;
};



var node_createVariableDebugView = function(view){
	
	var loc_view = $('<div></div>');
	loc_view = view;

	var loc_componentData = {
	};
	
	var loc_vue;
	
	var loc_init = function(){
		Vue.component('variable-inspection', loc_createComponentVariableInspection());

		loc_vue = new Vue({
			el: document.getElementById("variableDebug"),
//    		  root: "#complextreeDebug",
//			el: loc_view,
			data: loc_componentData,
			methods : {
			},
			watch: {
			},
			computed : {
			},
			template : `
				<div>
					<variable-inspection/>
				</div>
			`
		});
	};

	var loc_out = {
		
		getView : function(){   return loc_view;   },
		
	};
	
	loc_init();
	return loc_out;
};
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});

nosliw.registerSetNodeDataEvent("variable.valueinvar.operation.valueInVarOperationServiceUtility", function(){node_valueInVarOperationServiceUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createVariableDebugView", node_createVariableDebugView); 

})(packageObj);

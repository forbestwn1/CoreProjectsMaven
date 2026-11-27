//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
var node_createServiceRequestInfoSequence;
var node_createServiceRequestInfoSimple;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUIPagePlugin = function(){
	
	var loc_out = {

		getCreateEntityCoreRequest : function(complexEntityDef, internalValuePortContainerId, externalValuePortContainerId, bundleCore, configure, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				return loc_createUIPageComponentCore(complexEntityDef, internalValuePortContainerId, bundleCore, configure);
			}, handlers, request);
		}
	};

	return loc_out;
};

var loc_createUIPageComponentCore = function(complexEntityDef, valueContextId, bundleCore, configure){

	var loc_complexEntityDef = complexEntityDef;
	var loc_valueContextId = valueContextId;
	var loc_bundleCore = bundleCore;
	var loc_envInterface = {};
	
	var loc_styleScript;
	
	var loc_uiContent;

	var loc_wrapperView = $("<div></div>");
	
	var loc_getStyleScript = function(){
		var styleScript = loc_complexEntityDef.getAttributeValue(node_COMMONATRIBUTECONSTANT.BLOCKCOMPLEXUIPAGE_STYLESCRIPT);
		if(styleScript!=undefined){
			return _.unescape(styleScript);
		}
	};

	var loc_attachStyle = function(){
	//				var sheet = document.createElement('style')
	//				sheet.id = id;
	//				sheet.innerHTML = _.unescape(defScript);
	//				document.style.appendChild(sheet);
					
					$('<style>').text(loc_styleScript).appendTo(document.head);
	};
	
	var loc_out = {
		
		getEntityInitRequest : function(handlers, request){
			loc_styleScript = loc_getStyleScript();
			
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			out.addRequest(loc_envInterface[node_CONSTANT.INTERFACE_ENTITY].createBrickAttributeRequest(node_COMMONATRIBUTECONSTANT.WITHUICONTENT_UICONTENT, undefined, {
				success : function(request, childNode){
					loc_uiContent = childNode.getChildValue().getCoreEntity();
					loc_uiContent.setParentUIEntity(loc_out);
				}
			}));
			return out;
		},

		getUIContent : function(){
			return loc_uiContent;
		},
		
		updateView : function(view){
			loc_attachStyle();
			
			$(view).append(loc_wrapperView);
			node_getComponentInterface(loc_uiContent).updateView(loc_wrapperView);
			return loc_wrapperView;
		},
		
		setEnvironmentInterface : function(envInterface){
			loc_envInterface = envInterface;
		},
		
	};
	
	return loc_out;	
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});


//Register Node by Name
packageObj.createChildNode("createUIPagePlugin", node_createUIPagePlugin); 

})(packageObj);

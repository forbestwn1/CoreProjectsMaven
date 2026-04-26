nosliw.createNode("runtime.name", "browser");

nosliw.init = function(configure){

	nosliw.setConfigure(configure);
	
	var libNames = [
//		"external.Underscore;1.9.1",
//		"external.Backbone;1.3.3",
//		"core",
		"constant",
		"logging",
		"common",
		"data",
		"expression",
		"activity",
		"process",
		"sequence",
		"task",
		"taskscript",
		"taskflow",
		"scripttaskgroup",
		"request",
		"id",
		"resource",
		"variable",
		"rule",
		"remoteservice",
		"error",
		"runtime",
		"runtimebrowser",
//		"uiexpression",
		"uicommon",
		"uitag",
		"uinode",
//		"uipage",
		"uicontent",
		"dataservice",
		"debug",
		"configure",
		"component",
		"complexentity",
		"testcomponent",
		"entitycontainer",
		"brick_wrapperbrick",
		"module",
		"uimodule",
		"uiapp",
		"iovalue",
		"valueport",
		"scriptbased",
		"statemachine",
		"runtimebrowsertest",
		"security",
		"framework7",
	];

	var requestStaticInfos = [];
	_.each(libNames, function(libName, i){
		requestStaticInfos.push({
			"type" : "library",
			"domain" : "data.javascript.library.internal",
			"name" : libName
		});
	});
	
	nosliw.utility.requestLoadLibraryResources({"staticInfo" : requestStaticInfos}, configure.version, function(){
		  nosliw.registerNodeEvent("runtime", "active",
					function(eventName, nodeName) {
				  		$(document).trigger("nosliwActive");
			  		}
		  );
		  var runtime = nosliw.getNodeData("runtime.createRuntime")(nosliw.runtimeName);
		  runtime.interfaceObjectLifecycle.init();
	});
};

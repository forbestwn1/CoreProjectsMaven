
var createFetchTask = function(staticInfos, processData, processFile, callBackFun){
	var loc_staticInfos = staticInfos;
	var loc_processData = processData;
	var loc_processFile = processFile;
	var loc_callBackFun = callBackFun;
	
	var loc_out = {
		staticInfos : loc_staticInfos,
		processData : loc_processData,
		processFile : loc_processFile,
		callBackFun : loc_callBackFun
	};
	return loc_out;
};

var staticFetchTaskManager = function(){
	
	var loc_processedTasks = {};
	
	var loc_processFetchTask = function(fetchTask, callBackFun){
		var staticInfos = fetchTask.staticInfos;
		var processData = fetchTask.processData;
		var processFile = fetchTask.processFile;

		$.ajax({
			url : "../../../nosliw/static/fetch",
			type : "POST",
			dataType: "json",
			data : JSON.stringify(staticInfos),
			async : true,
			success : function(serviceData, status){
				var result = serviceData.data.item;
				
				var fileNumber = result.length;
				var count = 0;
				
				var processItem = function(){
					var item = result[count];
					
					if(item.type=="url"){
		               //for file
					    if(processFile!=undefined){
		                     processFile(item.uri);			    
					    }
						var scriptEle = document.createElement('script');
						scriptEle.setAttribute('src', item.uri);
						scriptEle.setAttribute('defer', "defer");
						scriptEle.setAttribute('type', 'text/javascript');

						scriptEle.onload = processNext;
						document.getElementsByTagName("head")[0].appendChild(scriptEle);
					}
					else if(item.type=="data"){
					    if(processData!=undefined){
		                     processData(item.data);			    
					    }
						processNext();
					}
					
				};
				
				var processNext = function(){
					count++;
					if(count>=fileNumber){
						if(callBackFun!=undefined){
						     callBackFun();
						}
					}
					else{
						processItem();
					}
				};
				
				processItem();
			},
			error: function(obj, textStatus, errorThrown){
				var kkkkk = 5555;
			},
		});
				
	};
	
	var loc_processFetchTasks = function(fetchTasks, i, callBackFun){
		var fetchTask = fetchTasks[i];
		if(loc_processedTasks[fetchTask.staticInfos.requestId]==null){
			loc_processFetchTask(fetchTask, function(){
				fetchTask.callBackFun({
					doFinish : function(){
						loc_processedTasks[fetchTask.staticInfos.requestId] = fetchTask;
						loc_nextTask(fetchTasks, i, callBackFun);
					}
				});
			});
		}
		else{
			loc_nextTask(fetchTasks, i, callBackFun);
		}
	};
	
	var loc_nextTask = function(fetchTasks, i, callBackFun){
		if(i>=fetchTasks.length-1){
			if(callBackFun!=undefined){
				callBackFun();
			}
		}
		else{
			loc_processFetchTasks(fetchTasks, i+1, callBackFun);
		}
	}
	
	var loc_out = {
		
		fetch : function(fetchTasks, callBackFun){
			loc_processFetchTasks(fetchTasks, 0, callBackFun);
		},
		
		
	};
	
	return loc_out;
}();


var createCoreStaticTask = function(){
	
	var requestStaticInfos = [];
	requestStaticInfos.push({
		"type" : "configure",
		"name" : "core"
	});

	var staticInfos = {
		      "staticInfo" : requestStaticInfos,
		      "requestId" : "mainAPP"
	};

	var configureData = {};

	var fetchTask = createFetchTask(staticInfos, 
			function(data){
	        	configureData = _.extend(configureData, data);
	        },
	        function(url){


	        	
	        },
	        function(env){
	        	nosliw.createNode("runtime.name", "browser");
	            
	        	configureData = _.extend(configureData, {
	        		logging : {
	        			module : ["process", "requestInfo", "requestManager"]
	        		}
	        	});
	        	nosliw.setConfigure(configureData);

	        	  nosliw.registerNodeEvent("runtime", "active",
	        				function(eventName, nodeName) {
	        			  		$(document).trigger("nosliwActive");
								env.doFinish();
	        		  		}
	        	  );
	        	  var runtime = nosliw.getNodeData("runtime.createRuntime")(nosliw.runtimeName);
	        	  runtime.interfaceObjectLifecycle.init();

	        }
	);

    return fetchTask;	
};



var fectchUtility = function(staticInfos, processData, processFile, callBackFun){


$.ajax({
	url : "../../../nosliw/static/fetch",
	type : "POST",
	dataType: "json",
	data : JSON.stringify(staticInfos),
	async : true,
	success : function(serviceData, status){
		var result = serviceData.data.item;
		
		var fileNumber = result.length;
		var count = 0;
		
		var processItem = function(){
			var item = result[count];
			
			if(item.type=="url"){
               //for file
			    if(processFile!=undefined){
                     processFile(item.uri);			    
			    }
				var scriptEle = document.createElement('script');
				scriptEle.setAttribute('src', item.uri);
				scriptEle.setAttribute('defer', "defer");
				scriptEle.setAttribute('type', 'text/javascript');

				scriptEle.onload = processNext;
				document.getElementsByTagName("head")[0].appendChild(scriptEle);
			}
			else if(item.type=="data"){
			    if(processData!=undefined){
                     processData(item.data);			    
			    }
				processNext();
			}
			
		};
		
		var processNext = function(){
			count++;
			if(count>=fileNumber){
				if(callBackFun!=undefined){
				     callBackFun();
				}
			}
			else{
				processItem();
			}
		};
		
		processItem();
	},
	error: function(obj, textStatus, errorThrown){
	},
});



};


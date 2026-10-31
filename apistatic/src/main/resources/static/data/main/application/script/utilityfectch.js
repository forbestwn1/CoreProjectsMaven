
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

const nosliw_libraryBaseUrl = new URL(document.currentScript.src).origin;

var createStaticFetchTaskManager = function(){
	
	var loc_processedTasks = {};
	
	var loc_processFetchTask = function(fetchTask, callBackFun){
		var staticInfos = fetchTask.staticInfos;
		var processData = fetchTask.processData;
		var processFile = fetchTask.processFile;

		const xhr = new XMLHttpRequest();
		const url = nosliw_libraryBaseUrl+"/nosliw/static/fetch";
		
		xhr.open("POST", url, true);
		xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

		// 3. Track response status and process data
		xhr.onload = function () {
		    if (xhr.status >= 200 && xhr.status < 300) {
				const serviceData = JSON.parse(xhr.responseText);
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
						
						var scriptEle;
						if(item.uri.endsWith(".css")){
							scriptEle = document.createElement('link');
							scriptEle.rel = 'stylesheet';
//							scriptEle.type = 'text/css';
							scriptEle.href = item.uri;	
							scriptEle.async = false;						
//							scriptEle.setAttribute('href ', item.uri);
//							scriptEle.setAttribute('rel', "stylesheet");
//							scriptEle.setAttribute('type', 'text/css');
                            scriptEle.onload = processNext;
							document.getElementsByTagName("head")[0].appendChild(scriptEle);
//                            document.head.appendChild(scriptEle);
						}
						else{
							scriptEle = document.createElement('script');
							scriptEle.setAttribute('src', item.uri);
							scriptEle.setAttribute('defer', "defer");
							scriptEle.setAttribute('type', 'text/javascript');
    						scriptEle.onload = processNext;
	    					document.getElementsByTagName("head")[0].appendChild(scriptEle);
						}
						
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
		    } else {
		        console.error("Server error:", xhr.status, xhr.statusText);
		    }
		};

		xhr.onerror = function () {
		    console.error("Network error occurred.");
		};

		xhr.send(JSON.stringify(staticInfos));		
	};
	
	var loc_processFetchTasks = function(fetchTasks, i, callBackFun){
		var fetchTask = fetchTasks[i];
		if(loc_processedTasks[fetchTask.staticInfos.requestId]==null){
			loc_processFetchTask(fetchTask, function(){
				var loc_doFinish = function(){
					loc_processedTasks[fetchTask.staticInfos.requestId] = fetchTask;
					loc_nextTask(fetchTasks, i, callBackFun);
				};
				
				if(fetchTask.callBackFun==undefined){
					loc_doFinish();
				}
				else{
					fetchTask.callBackFun({
						doFinish : function(){
							loc_doFinish();
						}
					});
				}
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
};


var createCoreStaticTask = function(){
	
	var staticInfos = {
		"staticInfo" : [
			{
				"type" : "configure",
				"name" : "core"
			}			
		],
		"requestId" : "Core"
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
/*	            
	        	configureData = _.extend(configureData, {
	        		logging : {
	        			module : ["process", "requestInfo", "requestManager"]
	        		}
	        	});
*/				
	        	nosliw.setConfigure(configureData);

	        	  nosliw.registerNodeEvent("runtime", "active",
	        				function(eventName, nodeName) {
								document.dispatchEvent(new Event('nosliwActive'));
//	        			  		$(document).trigger("nosliwActive");
								env.doFinish();
	        		  		}
	        	  );
	        	  var runtime = nosliw.getNodeData("runtime.createRuntime")(nosliw.runtimeName);
	        	  runtime.interfaceObjectLifecycle.init();

	        }
	);

    return fetchTask;	
};


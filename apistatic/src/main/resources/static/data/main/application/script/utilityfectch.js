
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
		
		var processItemInOrder = function(){
			var item = result[count];
			
			if(item.type=="file"){
               //for file
			    if(processFile!=undefined){
                     processFile(item.uri);			    
			    }
				var scriptEle = document.createElement('script');
				scriptEle.setAttribute('src', item.uri);
				scriptEle.setAttribute('defer', "defer");
				scriptEle.setAttribute('type', 'text/javascript');

				scriptEle.onload = callBack;
				document.getElementsByTagName("head")[0].appendChild(scriptEle);
			}
			else if(item.type=="data"){
			    if(processData!=undefined){
                     processData(item.data);			    
			    }
				count++;
				if(count>=fileNumber){
					finishProcessItems();
				}
				else{
    				processItemInOrder();
                }
			}
			
		};
		
		var finishProcessItems = function(){
		    if(callBackFun!=undefined){
		         callBackFun();
		    }
        };
		
		var callBack = function(){
			count++;
			if(count>=fileNumber){
				finishProcessItems();
			}
			else{
				processItemInOrder();
			}
		};
		
		processItemInOrder();
	},
	error: function(obj, textStatus, errorThrown){
	},
});



};


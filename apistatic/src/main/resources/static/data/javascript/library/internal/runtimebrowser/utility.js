nosliw.utility = function(){
	
	var loc_out = {
		
		getCoreApplicationBaseUrl : function(){
			return nosliw.getConfigureValue("coreUrl", "");
		},	
			
		buildNosliwUrl : function(url){
			return nosliw.getConfigureValue("gatewayUrl", "")+url;   //"http://localhost:8080"
		},
	};
	return loc_out;
}();

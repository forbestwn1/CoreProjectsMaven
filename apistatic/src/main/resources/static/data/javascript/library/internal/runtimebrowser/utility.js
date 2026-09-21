nosliw.utility = function(){
	
	var loc_out = {
		
		getServerBase : function(){
			return nosliw.getConfigureValue("gatewayUrl", "");
//			return nosliw.getConfigureValue("serverBase", "");
		},	
			
		buildNosliwUrl : function(url){
			return loc_out.getServerBase()+url;
		},
	};
	return loc_out;
}();

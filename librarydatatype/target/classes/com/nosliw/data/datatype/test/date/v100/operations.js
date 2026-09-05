//run seperately
//		which data type (name + version) belong to
//      dependency
//		each operation (operation name, script, dependency)

var dataTypeDefition = nosliw.getDataTypeDefinition("test.date");

//define what this data type globlely requires (operation, datatype, library)
dataTypeDefition.requires = {
};

dataTypeDefition.operations['todayDate'] = {
		operation : function(parms, context){
			var date = new Date();
			return {
				dataTypeId : "test.date;1.0.0",
				value : {
					year : date.getFullYear(),
					month : date.getMonth(),
					date : date.getDate()
				}
			};
		},
};

dataTypeDefition.operations['nextDate'] = {
		operation : function(parms, context){
			var date = new Date(this.getFullYear(), this.getMonth(), this.getDate());
			date.setDate(this.getDate()-1);

			return {
				dataTypeId : "test.string;1.0.0",
				value : {
					year : date.getFullYear(),
					month : date.getMonth(),
					date : date.getDate()
				}
			};
		},
};

dataTypeDefition.operations['lastDate'] = {
		operation : function(parms, context){
			var date = new Date(this.getFullYear(), this.getMonth(), this.getDate());
			date.setDate(this.getDate()+1);
			
			return {
				dataTypeId : "test.string;1.0.0",
				value : {
					year : date.getFullYear(),
					month : date.getMonth(),
					date : date.getDate()
				}
			};
		},
};

dataTypeDefition.operations['NDatesLater'] = {
		operation : function(parms, context){
			var date = new Date(this.getFullYear(), this.getMonth(), this.getDate());
			date.setDate(this.getDate()+parms.getParm("n").value);

			return {
				dataTypeId : "test.string;1.0.0",
				value : {
					year : date.getFullYear(),
					month : date.getMonth(),
					date : date.getDate()
				}
			};
		},
};

nosliw.addDataTypeDefinition(dataTypeDefition);

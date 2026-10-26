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
			var value = this.value;
			var date = new Date(value.year, value.month, value.date);
			date.setDate(value.date+1);

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

dataTypeDefition.operations['nextSaturaday'] = {
		operation : function(parms, context){
			var value = this.value;
			var date = new Date(value.year, value.month, value.date);

			const daysToAdd = (6 - date.getDay() + 7) % 7 || 0;
		    date.setDate(date.getDate() + daysToAdd);			
			
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

dataTypeDefition.operations['nextSunday'] = {
		operation : function(parms, context){
			var value = this.value;
			var date = new Date(value.year, value.month, value.date);

			const daysUntilSunday = date.getDay()==0? 0: 7 - date.getDay(); 
			date.setDate(date.getDate() + daysUntilSunday);			
			
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

dataTypeDefition.operations['NDatesLater'] = {
		operation : function(parms, context){
			var value = this.value;
			var date = new Date(value.year, value.month, value.date);
			date.setDate(this.value.date+parms.getParm("n").value);

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

dataTypeDefition.operations['lastDate'] = {
		operation : function(parms, context){
			var value = this.value;
			var date = new Date(value.year, value.month, value.date);
			date.setDate(value.date-1);
			
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

nosliw.addDataTypeDefinition(dataTypeDefition);

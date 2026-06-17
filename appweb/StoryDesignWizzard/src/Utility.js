

export var questionairUtility = function () {

    var loc_getAllItemsInGroup = function(questionair){
    	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
        return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM];
    };

    var loc_getChildQuestionairByValueType = function(questionair, valueType){
        var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
        
        var items = loc_getAllItemsInGroup(questionair);

        for(var i=0; i<items.length; i++){
            var item = items[i];
            var questionairType = item[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP){
                var out = loc_getChildQuestionairByValueType(item, valueType);
                if(out) return out;
            }
            else{
                if(items[i][node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_VALUETYPE] === valueType){
                    return items[i];
                }
            }

        }
        return null;
    };


    var loc_out = {

        getChildQuestionairByValueType :function(questionair, valueType){
            return loc_getChildQuestionairByValueType(questionair, valueType);
        },

        getValueFromQuestionairDynamic : function(questionair){
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
            if(questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ISDIRTY]==true){
                return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_CHANGEDVALUE];
            }
            else{
                return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_DEFAULTVALUE];
            }
        }


    };

    return loc_out;

}();

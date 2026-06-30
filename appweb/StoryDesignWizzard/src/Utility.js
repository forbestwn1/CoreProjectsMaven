
export const stateUtility = function(){


    var loc_out = {

        getCurrentStepName : function(designState){
            if(designState.steps.length!=0){
                return designState.steps[designState.currentStepUI].stepDefinition.name;
            }
        },

        buildCleanStepDirty : function(steps){
            let isStepDirty = [];
            for(let step in steps){
                isStepDirty.push(false);
            }
            return isStepDirty;
        },

        
    };
    return loc_out;

}();

export const questionairUtility = function () {

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

        getErrorMessageFromQuesionair : function(questionair){
        	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
            let error = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ERROR];
            if(error!=undefined){
                return error[node_COMMONATRIBUTECONSTANT.STORYWIZZARDERRORINQUESTIONAIR_MESSAGE];
            }
        },

        clearError : function(questionair){
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

            var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC){
                questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ERROR] = undefined;
            }
            else if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP){
                for(let child in questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM]){
                    this.clearError(child);
                }
            }

        },

        getChildQuestionairByValueType :function(questionair, valueType){
            return loc_getChildQuestionairByValueType(questionair, valueType);
        },

        getValueFromQuestionairItem : function(questionair){
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

            var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC){
                if(questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ISDIRTY]==true){
                    return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_CHANGEDVALUE];
                }
                else{
                    return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_DEFAULTVALUE];
                }
            }
            else if(questionairType==node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_STATIC){
                return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMSTATIC_VALUE];
            }
        }

    };

    return loc_out;

}();

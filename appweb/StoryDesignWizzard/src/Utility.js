
import { newDesignService, nextStepDesignService } from './Service'
import { designReducer, initialState, newDesign, updateDesignGlobal, nextStep, lastStep } from './reducers/designReducer';

export const naviationUtility = function () {

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var loc_out = {

        isLastStep : function(designState){
            const designSteps = designState.steps;

        },


        navigateToDesignFinish: function (setView) {
            if (typeof setView === 'function') {
                setView('designFinish');
            }
        },

        navigateToDesign: function (setView) {
            if (typeof setView === 'function') {
                setView('design');
            }
        },

        back: function (designState, designDispatch) {
            if (designState.currentStepUI != 0) {
                designDispatch(lastStep());
            }
        },

        next: function (designState, designDispatch) {
            const designSteps = designState.steps;
            const currentStep = designSteps ? designSteps[designState.currentStepUI] : undefined;
            if (designState.currentStepUI < designState.currentStepServer && designState.isStepDirty[designState.currentStepUI] == false) {
                designDispatch(nextStep());
            }
            else {
                nextStepDesignService(designState.designId, currentStep).then((response) => {
                    // Handle response
                    var steps = response.data.data.stepInfo;
                    var currentStepIndex = response.data.data.currentStep;
                    const currentStep = steps[currentStepIndex];
                    if(currentStep[node_COMMONATRIBUTECONSTANT.STORYDESIGNMETADATASTEP_TYPE]==node_COMMONCONSTANT.STORYDESIGN_STEP_METADATATYPE_END){
                        //last step

                    }
                    else{
                        //not last step, normal step
                        designDispatch(updateDesignGlobal(steps, currentStep));
                    }
                });
            }

        }

    };

    return loc_out;

}();

export const stateUtility = function () {


    var loc_out = {

        getCurrentStepName: function (designState) {
            if (designState.steps.length != 0) {
                return designState.steps[designState.currentStepUI].stepDefinition.name;
            }
        },

        buildCleanStepDirty: function (steps) {
            let isStepDirty = [];
            for (let step in steps) {
                isStepDirty.push(false);
            }
            return isStepDirty;
        },


    };
    return loc_out;

}();

export const questionairUtility = function () {

    var loc_getAllItemsInGroup = function (questionair) {
        var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
        return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM];
    };

    var loc_getDecendentQuestionairByTag = function (questionair, tag) {
        var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

        if (questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TAG] === tag) {
            return questionair;
        }

        var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
        if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP) {
            var children = loc_getAllItemsInGroup(questionair);
            for (let i in children) {
                var child = children[i];
                var result = loc_getDecendentQuestionairByTag(child, tag);
                if (result) return result;
            }
        }
    };

    var loc_getChildQuestionairByValueType = function (questionair, valueType) {
        var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

        var items = loc_getAllItemsInGroup(questionair);

        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            var questionairType = item[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP) {
                var out = loc_getChildQuestionairByValueType(item, valueType);
                if (out) return out;
            }
            else {
                if (items[i][node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_VALUETYPE] === valueType) {
                    return items[i];
                }
            }

        }
        return null;
    };

    var loc_getChildQuestionairByTag = function (questionair, tag) {
        var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
        var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

        var items = loc_getAllItemsInGroup(questionair);

        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            if (items[i][node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TAG] === tag) {
                return items[i];
            }
        }
        return null;
    };


    var loc_out = {

        getErrorMessageFromQuesionair: function (questionair) {
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
            var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
            let error = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ERROR];
            if (error != undefined) {
                return error[node_COMMONATRIBUTECONSTANT.STORYWIZZARDERRORINQUESTIONAIR_MESSAGE];
            }
        },

        clearError: function (questionair) {
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
            var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

            var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC) {
                questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ERROR] = undefined;
            }
            else if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_GROUP) {
                for (let child in questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_ITEM]) {
                    this.clearError(child);
                }
            }

        },

        getAllItemsInGroup: function (questionair) {
            return loc_getAllItemsInGroup(questionair);
        },

        getChildQuestionairByTag: function (questionair, tag) {
            return loc_getChildQuestionairByTag(questionair, tag);
        },

        getChildQuestionairByValueType: function (questionair, valueType) {
            return loc_getChildQuestionairByValueType(questionair, valueType);
        },

        getDecendentQuestionairByTag: function (questionair, tag) {
            return loc_getDecendentQuestionairByTag(questionair, tag);
        },

        getValueFromQuestionairItem: function (questionair) {
            var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
            var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

            var questionairType = questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIR_TYPE];
            if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_DYNAMIC) {
                if (questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_ISDIRTY] == true) {
                    return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_CHANGEDVALUE];
                }
                else {
                    return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMDYNAMIC_DEFAULTVALUE];
                }
            }
            else if (questionairType == node_COMMONCONSTANT.STORYDESIGN_QUESTIONTYPE_ITEM_STATIC) {
                return questionair[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONAIRITEMSTATIC_VALUE];
            }
        }

    };

    return loc_out;

}();

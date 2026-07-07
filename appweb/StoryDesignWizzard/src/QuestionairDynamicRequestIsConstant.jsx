import { useState, useEffect, useContext} from 'react'
import { questionairUtility } from './Utility'

export default function QuestionairDynamicRequestIsConstant({questionair, onChange}){ 
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var loc_questionair = questionair;
    var loc_onChange = onChange;

    const selected = questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT];

    var setSelectedDataSource = function(isConstant){
        loc_questionair.isDirty = true;
        loc_questionair.changedValue = {};
        loc_questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = loc_questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        loc_questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT] = isConstant;
        loc_onChange();
    };

    return (
        <>
        <div>
            <label>Choose is Constant:</label>
            <input type="checkbox" checked={selected} onChange={(e) => setSelectedDataSource(e.target.checked)}></input>
        </div>
        </>
    );

};

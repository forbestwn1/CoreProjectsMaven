import { useState, useEffect, useContext} from 'react'
import { DataSourceContext } from './DesignContext'

export default function QuestionairDynamicRequestIsConstant({questionair, onChange}){ 
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var loc_questionair = questionair;
    var loc_onChange = onChange;

    var setSelectedDataSource = function(isConstant){
        loc_questionair.isDirty = true;
        loc_questionair.changedValue = {};
        loc_questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT;
        loc_questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT] = isConstant;
        loc_onChange();
    };

    return (
        <>
            <label>Choose is Constant:</label>
            <input type="checkbox" switch onChange={(e) => setSelectedDataSource(e.target.checked)}></input>
        </>
    );


};

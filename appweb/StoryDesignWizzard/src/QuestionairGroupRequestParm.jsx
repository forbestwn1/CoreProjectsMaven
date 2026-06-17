import { useState, useReducer, useEffect } from 'react'
import Questionair from './Questionair'
import {questionairUtility} from './Utility'
import QuestionairDynamicRequestIsConstant from './QuestionairDynamicRequestIsConstant'
import QuestionairDynamicRequestConstantValue from './QuestionairDynamicRequestConstantValue'
import QuestionairDynamicRequestInputTag from './QuestionairDynamicRequestInputTag'

export default function QuestionairGroupRequestParm({ questionair, onChange }) {
  const [update, setUpdate] = useState(false);

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
  
  var questionairIsConstant = questionairUtility.getChildQuestionairByValueType(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT);
  var questionairConstantValue = questionairUtility.getChildQuestionairByValueType(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
  var questionairInputTag = questionairUtility.getChildQuestionairByValueType(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMUITAG);

  var loc_isConstant = questionairUtility.getValueFromQuestionairDynamic(questionairIsConstant)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT];

  var loc_onChange = function(isCnst){
    setUpdate(!update);
    onChange();
  };

  var loc_getQuestionairComponent = function(){
    if(loc_isConstant){
      return <QuestionairDynamicRequestConstantValue questionair={questionairConstantValue} />;
    }
    else{
      return <QuestionairDynamicRequestInputTag questionair={questionairInputTag}/>;
    }
  };

  return (
    <div className="questionnaire-group">
        request parm group questionair!!!!

        <QuestionairDynamicRequestIsConstant questionair={questionairIsConstant} onChange={loc_onChange} />
        {loc_getQuestionairComponent()}

    </div>
  );
}

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
  var questionairParmInfo = questionairUtility.getChildQuestionairByValueType(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMINFO);

  var loc_requestParmDef = questionairUtility.getValueFromQuestionairItem(questionairParmInfo)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMINFOSTATIC_REQUESTPARMDEF];
  var loc_requestParmName = loc_requestParmDef[node_COMMONATRIBUTECONSTANT.ENTITYINFO_NAME];
  var loc_dataDefinition = loc_requestParmDef[node_COMMONATRIBUTECONSTANT.DEFINITIONPARMREQUEST_DATADEFINITION];
  var loc_isConstant = questionairUtility.getValueFromQuestionairItem(questionairIsConstant)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT];

  var loc_onChange = function(isCnst){
    setUpdate(!update);
    onChange();
  };
  var loc_getQuestionairComponent = function(){
    if(loc_isConstant){
      return <QuestionairDynamicRequestConstantValue questionair={questionairConstantValue} datadefinition={loc_dataDefinition}/>;
    }
    else{
      return <QuestionairDynamicRequestInputTag questionair={questionairInputTag}/>;
    }
  };

  return (
    <div className="questionnaire-group">
        request parm group questionair!!!! : {loc_requestParmName}

        <QuestionairDynamicRequestIsConstant questionair={questionairIsConstant} onChange={loc_onChange} />
        {loc_getQuestionairComponent()}

    </div>
  );
}

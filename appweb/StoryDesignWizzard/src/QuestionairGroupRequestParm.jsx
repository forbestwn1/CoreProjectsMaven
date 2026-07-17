import { useState } from 'react'
import Questionair from './Questionair'
import {questionairUtility} from './Utility'
import QuestionairDynamicRequestIsConstant from './QuestionairDynamicRequestIsConstant'
import QuestionairDynamicRequestConstantValue from './QuestionairDynamicRequestConstantValue'
import QuestionairDynamicRequestInputTag from './QuestionairDynamicRequestInputTag'
import './QuestionairGroupRequestParm.css'

export default function QuestionairGroupRequestParm({ questionair, onChange }) {
  const [update, setUpdate] = useState(false);
  const [collapsed, setCollapsed] = useState(true);

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
      return <QuestionairDynamicRequestConstantValue questionair={questionairConstantValue} datadefinition={loc_dataDefinition} onChange={onChange}/>;
    }
    else{
      return <QuestionairDynamicRequestInputTag questionair={questionairInputTag}/>;
    }
  };

  return (
    <div className="request-parm-card">
      <button
        type="button"
        className="request-parm-toggle"
        onClick={() => setCollapsed(!collapsed)}
      >
        <span>{loc_requestParmName}</span>
        <span className="toggle-icon">{collapsed ? '+' : '-'}</span>
      </button>

      <div className={`request-parm-content ${collapsed ? 'collapsed' : ''}`}>
        <QuestionairDynamicRequestIsConstant questionair={questionairIsConstant} onChange={loc_onChange} />
        {loc_getQuestionairComponent()}
      </div>
    </div>
  );
}

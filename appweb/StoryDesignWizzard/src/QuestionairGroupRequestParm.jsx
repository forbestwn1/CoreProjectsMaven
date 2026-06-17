import Questionair from './Questionair'
import {questionairUtility} from './Utility'
import QuestionairDynamicRequestIsConstant from './QuestionairDynamicRequestIsConstant'
import QuestionairDynamicRequestConstantValue from './QuestionairDynamicRequestConstantValue'
import QuestionairDynamicRequestInputTag from './QuestionairDynamicRequestInputTag'

export default function QuestionairGroupRequestParm({ questionair, onChange }) {
	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
  
  var questionairIsConstant = questionairUtility.getChildQuestionairByValueType(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT);

  var onChange = function(){
    onChange();
  };

  return (
    <div className="questionnaire-group">
        request parm group questionair!!!!

        <QuestionairDynamicRequestIsConstant questionair={questionairIsConstant} onChange={onChange} />
        <QuestionairDynamicRequestConstantValue/>
        <QuestionairDynamicRequestInputTag/>



    </div>
  );
}

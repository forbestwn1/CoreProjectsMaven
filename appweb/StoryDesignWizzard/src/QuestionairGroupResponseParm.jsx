import QuestionairGroupResponseData from './QuestionairGroupResponseData'
import { questionairUtility } from './Utility'


export default function QuestionairGroupResponseParm({ questionair, onChange }) {
  
    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
  
  var dataQ = questionairUtility.getChildQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEDATA);

  return (
    <div className="questionnaire-group">

        <QuestionairGroupResponseData questionair={dataQ} onChange={onChange} />

    </div>
  );
}

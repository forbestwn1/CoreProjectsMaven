import QuestionairGroupRequest from './QuestionairGroupRequest'
import QuestionairGroupResponse from './QuestionairGroupResponse'
import ButtonRow from './ButtonRow'
import './QuestionairGroupDataSourceUICustomize.css'
import { questionairUtility } from './Utility'

export default function QuestionairGroupDataSourceUICustomize({ questionair, onChange }) {
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    let requestQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTGROUP);
    let responseQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEGROUP);

    return (
        <div className="request-response-wrapper">
            <div className="request-response-panels">
                <div className="panel request-panel">
                    <h3>Filter:</h3>
                    <QuestionairGroupRequest questionair={requestQ} onChange={onChange} />
                </div>

                <div className="panel response-panel">
                    <h3>Output:</h3>
                    <QuestionairGroupResponse questionair={responseQ} onChange={onChange} />
                </div>
            </div>

            <ButtonRow />
        </div>
    );
}

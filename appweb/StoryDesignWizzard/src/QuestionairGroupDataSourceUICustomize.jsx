import { questionairUtility } from './Utility'
import QuestionairGroupRequest from './QuestionairGroupRequest'
import QuestionairGroupResponse from './QuestionairGroupResponse'
import './QuestionairGroupDataSourceUICustomize.css'

export default function QuestionairGroupDataSourceUICustomize({ questionair, onChange }) {

    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    let requestQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTGROUP);
    let responseQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEGROUP);

    return (
        <div className="request-response-wrapper">
            <div className="request-response-panels">
                <div className="panel request-panel">
                    <h3>Request</h3>
                    <QuestionairGroupRequest questionair={requestQ} onChange={onChange} />
                </div>

                <div className="panel response-panel">
                    <h3>Response</h3>
                    <QuestionairGroupResponse questionair={responseQ} onChange={onChange} />
                </div>
            </div>

            <div className="button-row">
                <button type="button">
                    Back
                </button>
                <button type="button">
                    Next
                </button>
            </div>
        </div>
    );
}

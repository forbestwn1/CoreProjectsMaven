import { questionairUtility } from './Utility'
import QuestionairGroupRequestParm from './QuestionairGroupRequestParm'
import QuestionairGroupResponseParm from './QuestionairGroupResponseParm'
import './QuestionairGroupDataSourceUICustomize.css'

export default function QuestionairGroupDataSourceUICustomize({ questionair, onChange }) {

    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");


    let requestQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTPARMGROUP);
    let responseQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEPARMGROUP);


    return (
        <div className="request-response-wrapper">
            <div className="request-response-panels">
                <div className="panel request-panel">
                    <h3>Request</h3>
                    <QuestionairGroupRequestParm questionair={requestQ} onChange={onChange} />
                </div>

                <div className="panel response-panel">
                    <h3>Response</h3>
                    <QuestionairGroupResponseParm questionair={responseQ} onChange={onChange} />
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

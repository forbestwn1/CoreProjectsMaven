import { useState, useEffect, useContext } from 'react'
import { DesignContext, DesignDispatchContext, CacheContext } from './DesignContext'
import QuestionairGroupRequest from './QuestionairGroupRequest'
import QuestionairGroupResponse from './QuestionairGroupResponse'
import './QuestionairGroupDataSourceUICustomize.css'
import { questionairUtility, naviationUtility } from './Utility'

export default function QuestionairGroupDataSourceUICustomize({ questionair, onChange }) {
    const designDispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    let requestQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTGROUP);
    let responseQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEGROUP);

    var onNext = function () {
        naviationUtility.next(designState, designDispatch);
    };

    var onBack = function () {
        naviationUtility.back(designState, designDispatch);
    };


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
                <button type="button" onClick={onBack}>
                    Back
                </button>
                <button type="button" onClick={onNext}>
                    Next
                </button>
            </div>
        </div>
    );
}

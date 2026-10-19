import { useContext } from 'react'
import { CacheContext } from './DesignContext'
import QuestionairGroupRequest from './QuestionairGroupRequest'
import QuestionairGroupResponse from './QuestionairGroupResponse'
import ButtonRow from './ButtonRow'
import './QuestionairGroupDataSourceUICustomize.css'
import { questionairUtility } from './Utility'

export default function QuestionairGroupDataSourceUICustomize({ questionair, onChange }) {
    const cache = useContext(CacheContext);

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");
    var node_ruleUtility = nosliw.getNodeData("rule.ruleUtility");

    let requestQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCEREQUESTGROUP);
    let responseQ = questionairUtility.getDecendentQuestionairByTag(questionair, node_COMMONCONSTANT.STORYDESIGN_QUESTION_TAG_DATASOURCERESPONSEGROUP);

    let loc_getRequestConfigureValidateRequest = function(handlers, request){
        var out = node_createServiceRequestInfoSequence({}, handlers, request);

        var results = [];
        let parmsQ = questionairUtility.getAllItemsInGroup(requestQ);
        for(var i in parmsQ){
            let parmQ = parmsQ[i];

            var questionairIsConstant = questionairUtility.getChildQuestionairByValueType(parmQ, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMISCONSTANT);
            var questionairConstantValue = questionairUtility.getChildQuestionairByValueType(parmQ, node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEREQUESTPARMCONSTANTVALUE);
            var isConstant = questionairUtility.getValueFromQuestionairItem(questionairIsConstant)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEISCONSTANTDYNAMIC_ISCONSTANT];
            if(isConstant==true){
    			var uiTagAppsInfo = cache.current[questionairConstantValue.id].uiTagAppsInfo;
                out.addRequest(uiTagAppsInfo.forDisplay.application.getValidateDataRequest({
                    success : function(request, result){
                        results.push(result);
                    }
                }));
            }
        } 

        out.addRequest(node_createServiceRequestInfoSimple({}, function(request){
            if(results.length>0){
                return node_ruleUtility.createRuleValidationFailResult();
            }
            else{
                return node_ruleUtility.createRuleValidationSuccessResult();
            }
        }));

        return out;
    };


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

            <ButtonRow validationRequest={loc_getRequestConfigureValidateRequest} />
        </div>
    );
}

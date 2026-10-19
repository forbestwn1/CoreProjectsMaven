import { useContext, useState } from 'react'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import './ButtonRow.css'
import { naviationUtility } from './Utility'

export default function ButtonRow({ validationRequest }) {
    const designDispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);
    const [errorMessage, setErrorMessage] = useState();

	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
    var node_ruleUtility = nosliw.getNodeData("rule.ruleUtility");

    var onNext = function () {
        if(validationRequest==undefined){
            naviationUtility.next(designState, designDispatch);
        }
        else{
            var request = validationRequest({
                success : function(request, result){
                    if(node_ruleUtility.isRuleValidationSuccess(result)){
                         naviationUtility.next(designState, designDispatch);
                    }
                    else{
                        setErrorMessage("Validation failed, please check your input.");
                    }
                }       
            });
            node_requestServiceProcessor.processRequest(request);
        }
    };

    var onBack = function () {
        naviationUtility.back(designState, designDispatch);
    };

    return (
        <div className="button-row">
            {errorMessage !== undefined && (
                <span className="error-message">{errorMessage}</span>
            )}
            <button type="button" onClick={onBack}>
                Back
            </button>
            <button type="button" onClick={onNext}>
                Next
            </button>
        </div>
    );
}
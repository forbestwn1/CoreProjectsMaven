import { useState, useEffect, useContext } from 'react'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import { dirtyCurrentStep, updateDesignLocal } from './reducers/designReducer';
import { stateUtility } from "./Utility"
import Questionair from './Questionair';


export default function Step() {
    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    const designSteps = JSON.parse(JSON.stringify(designState.steps));
    const currentStep = designSteps ? designSteps[designState.currentStepUI] : undefined;
    const questionair = currentStep ? currentStep.questionair : undefined;
    const stepName = stateUtility.getCurrentStepName(designState);

    var onChange = function () {
        dispatch(updateDesignLocal(designSteps));
        dispatch(dirtyCurrentStep());
    };

    return (
        <>
            <div className="step-content">

                Hello Step  {stepName}!!!!

                {questionair && <Questionair questionair={questionair} onChange={onChange}></Questionair>}
            </div>
        </>
    );
};

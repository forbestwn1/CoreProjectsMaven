import { useState, useEffect, useContext } from 'react'
import { DesignContext, DesignDispatchContext } from './DesignContext'
import { dirtyCurrentStep, updateDesignLocal } from './reducers/designReducer';
import { stateUtility, naviationUtility } from "./Utility"
import Questionair from './Questionair';
import StepComplete from './StepComplete';


export default function Step() {
    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    const designSteps = JSON.parse(JSON.stringify(designState.steps));
    const currentStep = designSteps ? designSteps[designState.currentStepUI] : undefined;
    const stepDisplayInfo = stateUtility.getCurrentStepDisplayInfo(designState);
    const questionair = currentStep ? currentStep.questionair : undefined;
    const isFinishStep = naviationUtility.isFinishStep(designState);

    var onChange = function () {
        dispatch(updateDesignLocal(designSteps));
        dispatch(dirtyCurrentStep());
    };

    return (
        <>
            <div className="step-content">

                Hello Step  {stepDisplayInfo?stepDisplayInfo.name:null}!!!!

                {!isFinishStep ? questionair && (<Questionair questionair={questionair} onChange={onChange}></Questionair>) : (<StepComplete/> )}


            </div>
        </>
    );
};

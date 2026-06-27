import { useState, useEffect, useContext} from 'react'
import {createComponentQuestionItemService, nextStepDesignService} from './Service'
import { designReducer, initialState, updateDesign } from './reducers/designReducer';
import { DesignContext, DesignDispatchContext } from './DesignContext'
import QuestionairDynamicChooseDataSource from './QuestionairDynamicChooseDataSource';
import Questionair from './Questionair';

export default function StepChooseDataSource() {
    const [dataSources, setDataSources] = useState([]);
    const [selectedDataSource, setSelectedDataSource] = useState(null);

    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    const designSteps = JSON.parse(JSON.stringify(designState.stepInfo));
    const currentStep = designSteps[designState.currentStepUI];
    const questionair = currentStep.questionair;
    
    var onNext = function(){
        nextStepDesignService(designState.designId, currentStep).then((response) => {
            // Handle response
            dispatch(updateDesign(response.data.data.stepInfo));
        });
    };

    var onChange = function(){
        dispatch(updateDesign(designSteps));
    };

    return (
        <>
Hello ChooseDataSourceStep!!!!

           <Questionair questionair={questionair} onChange={onChange}></Questionair>

            <button onClick={onNext}>
                Next
            </button>

        </>
    );
};

import { useState, useEffect, useContext} from 'react'
import {createComponentQuestionItemService, nextStepDesignService} from './Service'
import { designReducer, initialState, updateDesign } from './reducers/designReducer';
import { DesignContext, DesignDispatchContext } from './DesignContext'
import QuestionairDynamicChooseDataSource from './QuestionairDynamicChooseDataSource';
import Questionair from './Questionair';

export default function ChooseDataSourceStep() {
    const [dataSources, setDataSources] = useState([]);
    const [selectedDataSource, setSelectedDataSource] = useState(null);

    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    const currentStep = 0;

    var onNext = function(){
        nextStepDesignService(designState.designId, designState.stepInfo[currentStep]).then((response) => {
            // Handle response
            dispatch(updateDesign(response.data.data.stepInfo));
        });
    };

    return (
        <>
Hello ChooseDataSourceStep!!!!

           <Questionair questionair={designState.stepInfo[currentStep].questionair} onChange={setSelectedDataSource}></Questionair>

            <button onClick={onNext}>
                Next
            </button>

        </>
    );
};

import { useState, useEffect, useContext} from 'react'
import {createComponentQuestionItemService, nextStepDesignService} from './Service'
import { designReducer, initialState, updateDesign } from './reducers/designReducer';
import { DesignContext, DesignDispatchContext } from './DesignContext'
import QuestionairDynamicChooseDataSource from './QuestionairDynamicChooseDataSource';
import {Questionair} from './Questionair';

export default function ChooseDataSourceStep() {
    const [dataSources, setDataSources] = useState([]);
    const [selectedDataSource, setSelectedDataSource] = useState(null);

    const dispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);

    var onNext = function(){
        nextStepDesignService(designState.designId, designState.stepInfo[designState.currentStep]).then((response) => {
            // Handle response
            dispatch(updateDesign(response.data.data.stepInfo));
        });
    };

    return (
        <>
           <Questionair questionair={designState.stepInfo[designState.currentStep].questionair} onChange={setSelectedDataSource}></Questionair>

Hello ChooseDataSourceStep!!!!

           <QuestionairDynamicChooseDataSource questionair={designState.stepInfo[designState.currentStep].questionair} onChange={setSelectedDataSource} />

            Choosed : {selectedDataSource}

            <label for="cars">Choose a DataSource:</label>
            <select name="DataSource" id="dataSource" onChange={(e) => setSelectedDataSource(e.target.value)}>
                {dataSources.map((source) => (
                    <option key={source.id} value={source.name}>
                        {source.name}
                    </option>
                ))}
            </select>

            <button onClick={onNext}>
                Next
            </button>

        </>
    );
};

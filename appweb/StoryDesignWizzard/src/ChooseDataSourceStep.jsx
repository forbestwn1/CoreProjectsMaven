import { useState, useEffect, useReducer} from 'react'
import {createComponentQuestionItemService, nextStepDesignService} from './Service'
import { storyReducer, STORY_ACTIONS, initialState, updateDesign } from './reducers/storyReducer';

export default function ChooseDataSourceStep() {
    const [state, dispatch] = useReducer(storyReducer, initialState);
    const [dataSources, setDataSources] = useState([]);
    const [selectedDataSource, setSelectedDataSource] = useState(null);

    useEffect(() => {
        const service = createComponentQuestionItemService();
        service.getLoadServicesRequest((services) => {
            setDataSources(services);
        });
    }, []);

    var onNext = function(){
        
        var requestData = {
            ...state.stepInfo[state.currentStep].questionair,
            isDirty : true,
            changedValue : {
                valueType : "dataSourceId",
                "dataSourceId" : selectedDataSource
            }
        };

        nextStepDesignService(state.designId, requestData).next((response) => {
            // Handle response
            updateDesign(response.data.data.stepInfo);

        });
    };

    return (
        <>
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

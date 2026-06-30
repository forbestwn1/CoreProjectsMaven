import { useState, useEffect, useContext} from 'react'
import { CacheContext } from './DesignContext'
import { questionairUtility } from './Utility'
import {createComponentQuestionItemService} from './Service'

export default function QuestionairDynamicChooseDataSource({questionair, onChange}){ 
    const cache = useContext(CacheContext);
    const [, forceUpdate] = useState(0);

    useEffect(() => {
        if(cache.current.dataSources==undefined){
            console.log("load all services response");
            const service = createComponentQuestionItemService();
            service.getLoadServicesRequest((services) => {
                cache.current.dataSources = services;
                forceUpdate(c=>c+1);
            });
        }
    }, []);

   	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var setSelectedDataSource = function(dataSourceId){
        questionair.isDirty = true;
        questionair.changedValue = {};
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = node_COMMONCONSTANT.STORYDESIGN_QUESTIONVALUE_TYPE_DATASOURCEID;
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCECHOOSEDYNAMIC_DATASOURCEID] = dataSourceId;
        onChange(dataSourceId);
    };

    const selected = questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCECHOOSEDYNAMIC_DATASOURCEID];

    return (
        <>
        <div>
            <label>Choose a DataSource:</label>
            <select name="DataSource" id="dataSource" onChange={(e) => setSelectedDataSource(e.target.value)}>
                {cache.current.dataSources&&cache.current.dataSources.map((source) => {
                    if(selected==source.name){
                        return (
                           <option key={source.id} selected value={source.name}>
                                {source.name}
                           </option>
                        );
                    }
                    else{
                        return (
                           <option key={source.id} value={source.name}>
                                {source.name}
                           </option>
                        );

                    }
                })}
            </select>

            {questionairUtility.getErrorMessageFromQuesionair(questionair)}

        </div>
        </>
    );
};

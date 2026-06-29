import { useState, useEffect, useContext} from 'react'
import { DataSourceContext } from './DesignContext'
import { questionairUtility } from './Utility'

export default function QuestionairDynamicChooseDataSource({questionair, onChange}){ 
    const dataSources = useContext(DataSourceContext);

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
                {dataSources.map((source) => {
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

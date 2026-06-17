import { useState, useEffect, useContext} from 'react'
import { DataSourceContext } from './DesignContext'

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

    return (
        <>
            <label>Choose a DataSource:</label>
            <select name="DataSource" id="dataSource" onChange={(e) => setSelectedDataSource(e.target.value)}>
                {dataSources.map((source) => (
                    <option key={source.id} value={source.name}>
                        {source.name}
                    </option>
                ))}
            </select>

        </>
    );


};

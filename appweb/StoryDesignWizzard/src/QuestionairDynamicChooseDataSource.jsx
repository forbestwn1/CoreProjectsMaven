import { useState, useEffect, useContext} from 'react'
import { DataSourceContext } from './DesignContext'

export default function QuestionairDynamicChooseDataSource({questionair, onChange}){ 
    const dataSources = useContext(DataSourceContext);

    var setSelectedDataSource = function(dataSourceId){
        questionair.isDirty = true;
        questionair.changedValue = {
            valueType : "dataSourceId",
            "dataSourceId" : dataSourceId
         };
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

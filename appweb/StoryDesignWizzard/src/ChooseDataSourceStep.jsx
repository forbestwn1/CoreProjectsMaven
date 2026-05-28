import { useState, useEffect } from 'react'
import createComponentQuestionItemService from './Service'

export default function ChooseDataSourceStep() {
    const [dataSources, setDataSources] = useState([]);
    const [selectedDataSource, setSelectedDataSource] = useState(null);

    useEffect(() => {
        const service = createComponentQuestionItemService();
        service.getLoadServicesRequest((services) => {
            setDataSources(services);
        });
    }, []);

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


        </>
    );
};

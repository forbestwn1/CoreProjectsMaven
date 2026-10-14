import { useState, useEffect, useContext } from 'react'
import { DesignContext, DesignDispatchContext, CacheContext } from './DesignContext'
import { questionairUtility, naviationUtility } from './Utility'
import { createComponentQuestionItemService } from './Service'
import ErrorInQuestionair from './ErrorInQuestionair'
import './QuestionairDynamicChooseDataSource.css'
import { setGlobalLoading } from './globalLoading'

export default function QuestionairDynamicChooseDataSource({ questionair, onChange }) {
    const cache = useContext(CacheContext);
    const designDispatch = useContext(DesignDispatchContext);
    const designState = useContext(DesignContext);
    const [, forceUpdate] = useState(0);

    useEffect(() => {
        if (cache.current[questionair.id] == undefined) {
            setGlobalLoading(true);
            console.log("load all services response");
            const service = createComponentQuestionItemService();
            service.getLoadServicesRequest((services) => {
                cache.current[questionair.id] = services;
                forceUpdate(c => c + 1);
                setGlobalLoading(false);
            });
        }
    }, []);


    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

    var setSelectedDataSource = function (dataSourceId) {
        questionair.isDirty = true;
        questionair.changedValue = {};
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCECHOOSEDYNAMIC_DATASOURCEID] = dataSourceId;
        onChange(dataSourceId);
    };

    const selected = questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCECHOOSEDYNAMIC_DATASOURCEID];
    const selectedDataSource = cache.current[questionair.id]?.find((source) => source.id === selected || source.name === selected);

    var onNext = function(){
        naviationUtility.next(designState, designDispatch);
    };

    return (
        <>
            <div className="split-panels">

                <div className="panel panel-left">
                    <div className="form-group">
                        <label htmlFor="dataSource">Please select data source *</label>
                        <select name="DataSource" id="dataSource" value={selected} onChange={(e) => setSelectedDataSource(e.target.value)}>
                            <option value="" disabled selected hidden>-- Please choose an data source --</option>
                            {cache.current[questionair.id] && cache.current[questionair.id].map((source) => {
                                return (<option key={source.id} value={source.id}>
                                    {source.name}
                                </option>);
                            })}
                        </select>

                        <ErrorInQuestionair questionair={questionair} />

                        <button type="button" className="build-app-button" onClick={onNext} disabled={!selected}>
                            Start building your own APP
                        </button>

                    </div>
                </div>

                <div className="panel panel-right">
                    <div className="source-description">
                        <h4>Description</h4>
                        <p>{selectedDataSource?.description || 'No description available.'}</p>
                    </div>
                </div>

            </div>
        </>
    );
};

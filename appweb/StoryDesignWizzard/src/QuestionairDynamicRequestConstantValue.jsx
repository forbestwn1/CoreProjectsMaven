import { useState, useEffect, useContext, useRef} from 'react'
import { CacheContext } from './DesignContext'

export default function QuestionairDynamicRequestConstantValue({questionair, datadefinition, onChange}){ 
	const cache = useContext(CacheContext);
    const contentRef = useRef(null);
    const [, forceUpdate] = useState(0);

    useEffect(() => {
		let uiTagAppInfo = cache.current[questionair.id];
        if(uiTagAppInfo==undefined){
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
			var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
			var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
			var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
			var node_ResourceId = nosliw.getNodeData("resource.entity.ResourceId");
			var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");

			var loc_questionair = questionair;
			var loc_bundleDef;

			var request = node_createServiceRequestInfoSequence(new node_ServiceInfo("constantValueUITag"));
			var gatewayParm = {};
			gatewayParm[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_DATADEFINITION] = datadefinition;

			request.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_GATEWAY,
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE,
				gatewayParm,
				{
					success: function (requestInfo, resourceIds) {
						loc_bundleDef = nosliw.runtime.getResourceService().getResource(new node_ResourceId(resourceIds[0])).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];

						var runtimeContext = {
						};

						return nosliw.runtime.getComplexEntityService().getCreateApplicationRequest({ bundleDef: loc_bundleDef }, undefined, runtimeContext, undefined, {
							success: function (requestInfo, application) {
								var variable = application.getVariable("data");
								cache.current[questionair.id] = { 
									application :application,
									variable : variable
								};

								variable.registerDataChangeEventListener(undefined, function(event, data){
									setSelectedConstantData(data);
								});
                                forceUpdate(c=>c+1);
							}
						});

					}
				}
			));
			node_requestServiceProcessor.processRequest(request);
		}
		else{
            $(uiTagAppInfo.application.getView()).remove();
	     	$(contentRef.current).append(uiTagAppInfo.application.getView());
		}
    });

    var setSelectedConstantData = function(data){
        questionair.isDirty = true;
        questionair.changedValue = {};
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
        questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSECONSTANTVALUEDYNAMIC_CONSTANTDATA] = data;
        onChange(data);
    };

    return (
        <>
        <div>
            constant value questionair!!!!
            <div ref={contentRef}>

            </div>
        </div>
        </>
    );

};

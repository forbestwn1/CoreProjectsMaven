import { useState, useEffect, useContext, useRef} from 'react'
import { DataSourceContext } from './DesignContext'

export default function QuestionairDynamicRequestConstantValue({questionair, datadefinition, onChange}){ 
    const contentRef = useRef(null);
	const [uiTagApp, setUiTagApp] = useState(null);

    useEffect(() => {
		if (!uiTagApp) {
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
//						loc_bundleDef = nosliw.runtime.getResourceService().getResource(new node_ResourceId(id, node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0")).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];
						var kkk = 5;

						var runtimeContext = {
						};

						return nosliw.runtime.getComplexEntityService().getCreateApplicationRequest({ bundleDef: loc_bundleDef }, undefined, runtimeContext, undefined, {
							success: function (requestInfo, application) {
								setUiTagApp(application);

								if(contentRef.current)
								{
									$(contentRef.current).append(application.getView());
								}
							}
						});

					}
				}
			));
			node_requestServiceProcessor.processRequest(request);
		}

		if(uiTagApp){
            $(uiTagApp.getView()).remove();
	     	$(contentRef.current).append(uiTagApp.getView());
		}

    });




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

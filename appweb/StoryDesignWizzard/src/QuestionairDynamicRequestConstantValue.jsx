import { useState, useEffect, useContext, useRef} from 'react'
import { DataSourceContext } from './DesignContext'

export default function QuestionairDynamicRequestConstantValue({questionair, datadefinition, onChange}){ 
    const contentRef = useRef(null);

    useEffect(() => {
    	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
    	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
    	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");


        var loc_questionair = questionair;
        var loc_content = contentRef.current;

		var request = node_createServiceRequestInfoSequence();
		var gatewayParm = {};
		gatewayParm[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_DATADEFINITION] = datadefinition;

		request.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_GATEWAY, 
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE, 
				gatewayParm,
				{
					success : function(requestInfo, bundleDef){
						var loc_bundleDef = nosliw.runtime.getResourceService().getResource(new node_ResourceId("12345678", node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_TRANSIENT, "1.0.0")).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];
						var kkk = 5;
					}
				}
		));
		node_requestServiceProcessor.processRequest(request);



    }, []);




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

import { useState, useEffect, useContext, useRef } from 'react'
import { CacheContext } from './DesignContext'
import './QuestionairDynamicRequestConstantValue.css'
import { questionairUtility, naviationUtility } from './Utility'

export default function QuestionairDynamicRequestConstantValue({ questionair, datadefinition, onChange }) {
	const cache = useContext(CacheContext);
	const contentRefForChange = useRef(null);
	const contentRefForDisplay = useRef(null);
	const [, forceUpdate] = useState(0);
	const [showPopup, setShowPopup] = useState(false);

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_createServiceRequestInfoSet = nosliw.getNodeData("request.request.createServiceRequestInfoSet");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
	var node_ResourceId = nosliw.getNodeData("resource.entity.ResourceId");
	var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");
	var node_valueInVarOperationServiceUtility = nosliw.getNodeData("variable.valueinvar.operation.valueInVarOperationServiceUtility");

	var loc_nameForChange = "forChange";
	var loc_nameForDisplay = "forDisplay";

	useEffect(() => {
		let uiTagAppInfo = cache.current[questionair.id];
		if (uiTagAppInfo == undefined) {

			var loc_questionair = questionair;
			var loc_bundleDefForChange;
			var loc_bundleDefForDisplay;

			var request = node_createServiceRequestInfoSequence(new node_ServiceInfo("constantValueUITag"));
			var gatewayParm = {};

			var uitages = [];

			var uiTagParmForChange = {};
			var uiTagParmForDisplay = {};

			var uiTagQueryForChange = {};
			var uiTagQueryForDisplay = {};
			uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_DATADEFINITION] = datadefinition;
			uiTagQueryForDisplay[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_DATADEFINITION] = datadefinition;
			uiTagQueryForChange[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_IOMODE] = node_COMMONCONSTANT.IO_DIRECTION_IN;
			uiTagQueryForDisplay[node_COMMONATRIBUTECONSTANT.UITAGEQUERYDATA_IOMODE] = node_COMMONCONSTANT.IO_DIRECTION_OUT;

			uiTagParmForChange[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM_UITAGQUERY] = uiTagQueryForChange;
			uiTagParmForDisplay[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM_UITAGQUERY] = uiTagQueryForDisplay;

			uiTagParmForChange[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM_DOMAIN] = loc_nameForChange;
			uiTagParmForDisplay[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM_DOMAIN] = loc_nameForDisplay;

			uitages.push(uiTagParmForChange);
			uitages.push(uiTagParmForDisplay);

			gatewayParm[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM] = uitages;

			request.addRequest(nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_GATEWAY,
				node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE,
				gatewayParm,
				{
					success: function (requestInfo, resourceIds) {
						loc_bundleDefForChange = nosliw.runtime.getResourceService().getResource(new node_ResourceId(resourceIds[0])).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];
						loc_bundleDefForDisplay = nosliw.runtime.getResourceService().getResource(new node_ResourceId(resourceIds[1])).resourceData[node_COMMONATRIBUTECONSTANT.RESOURCEDATAIMPTRANSIENT_VALUE];

						var out = node_createServiceRequestInfoSequence({});
						var uiTagAppsRequest = node_createServiceRequestInfoSet({}, {
							success: function (request, uiTagAppsResult) {
								var uiTagAppsInfo = uiTagAppsResult.getResults();
								cache.current[questionair.id] = uiTagAppsInfo;

								updateUITagForDisplay();
								updateUITagForChange();
								forceUpdate(c => c + 1);
							}
						});
						uiTagAppsRequest.addRequest(loc_nameForChange, buildUITagStandAloneAppRequest(loc_bundleDefForChange, loc_nameForChange));
						uiTagAppsRequest.addRequest(loc_nameForDisplay, buildUITagStandAloneAppRequest(loc_bundleDefForDisplay, loc_nameForDisplay));

						out.addRequest(uiTagAppsRequest);
						return out;
					}
				}
			));
			node_requestServiceProcessor.processRequest(request);
		}
		else {
			var forChangeAppInfo = loc_getUITappAppInfoForChange();
//			$(forChangeAppInfo.application.getView()).remove();
			$(contentRefForChange.current).append(forChangeAppInfo.application.getView());

			var forDisplayAppInfo = loc_getUITappAppInfoForDisplay();
//			$(forDisplayAppInfo.application.getView()).remove();
			$(contentRefForDisplay.current).append(forDisplayAppInfo.application.getView());
		}
	});


	var loc_getCurrentConstantData = function () {
		return questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSECONSTANTVALUEDYNAMIC_CONSTANTDATA];
	}


	var loc_getUITappAppInfoForDisplay = function(){
		return  cache.current[questionair.id][loc_nameForDisplay];
	};

	var loc_getUITappAppInfoForChange = function(){
		return  cache.current[questionair.id][loc_nameForChange];
	};


	var buildUITagStandAloneAppRequest = function (bundleDef, name, handlers, request) {
		var out = node_createServiceRequestInfoSequence({}, handlers, request);
		out.addRequest(nosliw.runtime.getComplexEntityService().getCreateApplicationRequest({ bundleDef: bundleDef }, undefined, {}, undefined, {
			success: function (requestInfo, application) {
				var variable = application.getVariable("data");

				return {
					application: application,
					variable: variable
				};
			}
		}));
		return out;

	};

	var updateUITagForDisplay = function () {
		var data = loc_getCurrentConstantData();
        var request = loc_getUITappAppInfoForDisplay().variable.getDataOperationRequest(node_valueInVarOperationServiceUtility.createSetOperationService("", data));
		node_requestServiceProcessor.processRequest(request);
	};

	var updateUITagForChange = function () {
		var data = loc_getCurrentConstantData();
        var request = loc_getUITappAppInfoForChange().variable.getDataOperationRequest(node_valueInVarOperationServiceUtility.createSetOperationService("", data));
		node_requestServiceProcessor.processRequest(request);
	};

	var setSelectedConstantData = function (data) {
		questionair.isDirty = true;
		questionair.changedValue = {};
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSECONSTANTVALUEDYNAMIC_CONSTANTDATA] = data;

		updateUITagForDisplay();
		onChange(data);
	};

	var openPopup = function (e) {
		if (e) e.preventDefault();
		setShowPopup(true);
	};

	var closePopup = function () {
		setShowPopup(false);
	};

	var onRestChangeValue = function () {
		updateUITagForChange();
	};

	var okAndClose = function () {
		try {
           var request = loc_getUITappAppInfoForChange().variable.getGetValueRequest({
			success: function (request, data) {
				setSelectedConstantData(data.value);
			}
		   });
			node_requestServiceProcessor.processRequest(request);
		} catch (e) {
			// ignore read errors
		}
		closePopup();
	};

	return (
		<>
			<div className="constant-value-root">
				<div className="constant-value-header">
					<span>constant value questionair!!!!</span>
					<a href="#" className="change-value-link" onClick={openPopup}>change value</a>
				</div>
				<div ref={contentRefForDisplay} className="constant-value-inline">
				</div>
			</div>

			{showPopup && (
				<div className="modal-overlay" onClick={closePopup}>
					<div className="modal-panel" onClick={(e) => e.stopPropagation()}>
						<div className="modal-header">
							<h3>Change Value</h3>
							<button className="modal-close" onClick={closePopup}>×</button>
						</div>
						<div className="modal-body" ref={contentRefForChange}></div>
						<div className="modal-footer">
							<button className="btn-secondary" onClick={closePopup}>Cancel</button>
							<button className="btn-reset" onClick={onRestChangeValue}>Reset</button>
							<button className="btn-primary" onClick={okAndClose}>OK</button>
						</div>
					</div>
				</div>
			)}
		</>
	);

};

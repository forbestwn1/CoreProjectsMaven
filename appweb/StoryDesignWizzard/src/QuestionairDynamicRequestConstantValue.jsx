import { useState, useEffect, useContext, useRef } from 'react'
import { CacheContext } from './DesignContext'
import './QuestionairDynamicRequestConstantValue.css'

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

	var loc_nameForchange = "forChange";
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

			uiTagParmForChange[node_COMMONATRIBUTECONSTANT.STORYGATEWAYSTANDALONE_COMMAND_CEATESTANDALONE_PARM_DOMAIN] = loc_nameForchange;
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

								uiTagAppsInfo[loc_nameForchange].variable.registerDataChangeEventListener(undefined, function (event, data) {
									//									setSelectedConstantData(data);
								});

								forceUpdate(c => c + 1);
							}
						});
						uiTagAppsRequest.addRequest(loc_nameForchange, buildUITagStandAloneAppRequest(loc_bundleDefForChange, loc_nameForchange));
						uiTagAppsRequest.addRequest(loc_nameForDisplay, buildUITagStandAloneAppRequest(loc_bundleDefForDisplay, loc_nameForDisplay));

						out.addRequest(uiTagAppsRequest);
						return out;
					}
				}
			));
			node_requestServiceProcessor.processRequest(request);
		}
		else {
			var forChangeAppInfo = uiTagAppInfo[loc_nameForchange];
			$(forChangeAppInfo.application.getView()).remove();
			$(contentRefForChange.current).append(forChangeAppInfo.application.getView());

			var forDisplayAppInfo = uiTagAppInfo[loc_nameForDisplay];
			$(forDisplayAppInfo.application.getView()).remove();
			$(contentRefForDisplay.current).append(forDisplayAppInfo.application.getView());
		}
	});

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

	var setSelectedConstantData = function (data) {
		questionair.isDirty = true;
		questionair.changedValue = {};
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSECONSTANTVALUEDYNAMIC_CONSTANTDATA] = data;
		onChange(data);
	};

	var openPopup = function (e) {
		if (e) e.preventDefault();
		// const uiTagAppInfo = cache.current[questionair.id];
		// if(uiTagAppInfo){
		// 	$(uiTagAppInfo.application.getView()).remove();
		// 	$(modalContentRef.current).append(uiTagAppInfo.application.getView());
		// }
		setShowPopup(true);
	};

	var closePopup = function () {
		// const uiTagAppInfo = cache.current[questionair.id];
		// if(uiTagAppInfo){
		// 	$(uiTagAppInfo.application.getView()).remove();
		// 	$(contentRef.current).append(uiTagAppInfo.application.getView());
		// }
		setShowPopup(false);
	};

	var okAndClose = function () {
		try {
			// const uiTagAppInfo = cache.current[questionair.id];
			// if(uiTagAppInfo && uiTagAppInfo.variable){
			// 	let val = undefined;
			// 	if(typeof uiTagAppInfo.variable.getValue === 'function') val = uiTagAppInfo.variable.getValue();
			// 	else if(typeof uiTagAppInfo.variable.getData === 'function') val = uiTagAppInfo.variable.getData();
			// 	if(val!==undefined){
			// 		setSelectedConstantData(val);
			// 	}
			// }
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
							<button className="btn-primary" onClick={okAndClose}>OK</button>
						</div>
					</div>
				</div>
			)}
		</>
	);

};

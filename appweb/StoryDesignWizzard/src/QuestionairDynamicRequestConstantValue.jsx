import { useState, useEffect, useContext, useRef } from 'react'
import { CacheContext } from './DesignContext'
import './QuestionairDynamicRequestConstantValue.css'
import { questionairUtility, naviationUtility } from './Utility'
import { setGlobalLoading } from './globalLoading'

export default function QuestionairDynamicRequestConstantValue({ questionair, datadefinition, onChange }) {
	const cache = useContext(CacheContext);
	const contentRefForChange = useRef(null);
	const contentRefForDisplay = useRef(null);
	const [, forceUpdate] = useState(0);
	const [showPopup, setShowPopup] = useState(false);
	const [saveDataEnable, setSaveDataEnable] = useState(false);

	var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
	var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");
	var node_createServiceRequestInfoSet = nosliw.getNodeData("request.request.createServiceRequestInfoSet");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
	var node_ResourceId = nosliw.getNodeData("resource.entity.ResourceId");
	var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");
	var node_valueInVarOperationServiceUtility = nosliw.getNodeData("variable.valueinvar.operation.valueInVarOperationServiceUtility");
	var node_chooseDataApp = nosliw.getNodeData("app_storybuild.chooseDataApp");
	var node_presentDataApp = nosliw.getNodeData("app_storybuild.presentDataApp");

	var loc_nameForChange = "forChange";
	var loc_nameForDisplay = "forDisplay";

	useEffect(() => {
		let questionairData = cache.current[questionair.id];
		if(questionairData==undefined){
			questionairData = {};
			cache.current[questionair.id] = questionairData;
		}

		if (questionairData.loading == undefined) {
			//never load before
			questionairData.loading = true;
			setGlobalLoading(true);

			var loc_questionair = questionair;

			var loc_displayApp = node_presentDataApp(datadefinition);
			var loc_changeApp = node_chooseDataApp(datadefinition);

			var uiTagAppsInfo = {};
			uiTagAppsInfo[loc_nameForDisplay] = {application: loc_displayApp};
			uiTagAppsInfo[loc_nameForChange] = {application: loc_changeApp};
			cache.current[questionair.id].uiTagAppsInfo = uiTagAppsInfo;

			var request = node_createServiceRequestInfoSequence(new node_ServiceInfo("constantValueUITag"));
			request.addRequest(loc_displayApp.getInitRequest());
			request.addRequest(loc_changeApp.getInitRequest());
			request.addRequest(node_createServiceRequestInfoSimple({}, function(request){
				updateUITagForDisplay();
				updateUITagForChange();
				questionairData.loading = false;
				setGlobalLoading(false);

				loc_changeApp.registerListener(function(eventName, eventValue){
					if(eventName=="change"){
						loc_updateSaveDataEnable();
					}
				});

				forceUpdate(c => c + 1);
			}));
			node_requestServiceProcessor.processRequest(request);
		}
		else if(questionairData.loading == false) {
			var forChangeAppInfo = loc_getUITappAppInfoForChange();
//			$(forChangeAppInfo.application.getView()).remove();
			$(contentRefForChange.current).append(forChangeAppInfo.application.getView());

			var forDisplayAppInfo = loc_getUITappAppInfoForDisplay();
//			$(forDisplayAppInfo.application.getView()).remove();
			$(contentRefForDisplay.current).append(forDisplayAppInfo.application.getView());
		}
	});


	var loc_updateSaveDataEnable = function(){
		var forChangeAppInfo = loc_getUITappAppInfoForChange();
		var value = forChangeAppInfo.application.isReady();
		setSaveDataEnable(value);
	};


	var loc_getCurrentValue = function () {
		return questionairUtility.getValueFromQuestionairItem(questionair)[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEDATABUILDDYNAMIC_DATABUILD];
	};

	var loc_getUITappAppInfoForDisplay = function(){	return  cache.current[questionair.id].uiTagAppsInfo[loc_nameForDisplay];	};

	var loc_getUITappAppInfoForChange = function(){		return  cache.current[questionair.id].uiTagAppsInfo[loc_nameForChange];	};

	var updateUITagForDisplay = function () {
		var data = loc_getCurrentValue();
		var request =loc_getUITappAppInfoForDisplay().application.getSetValueRequest(data);
		node_requestServiceProcessor.processRequest(request);
	};

	var updateUITagForChange = function () {
		var data = loc_getCurrentValue();
		var request =loc_getUITappAppInfoForChange().application.getSetValueRequest(data, {
			success: function(request){
				loc_updateSaveDataEnable();
			}
		});
		node_requestServiceProcessor.processRequest(request);
	};

	var setSelectedConstantData = function (dataBuild) {
		questionair.isDirty = true;
		questionair.changedValue = {};
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE] = questionair.defaultValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDVALUEINQUESTIONAIR_VALUETYPE];
		questionair.changedValue[node_COMMONATRIBUTECONSTANT.STORYWIZZARDQUESTIONVALUEDATASOURCEREQUESTPARMCHOOSEDATABUILDDYNAMIC_DATABUILD] = dataBuild;

		updateUITagForDisplay();
		onChange(dataBuild);
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

	var saveAndClose = function () {
		var value = loc_getUITappAppInfoForChange().application.getValue();
		setSelectedConstantData(value[node_COMMONATRIBUTECONSTANT.DATABUILDINFO_DATABUILD]);
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
							<button className="btn-primary" onClick={saveAndClose} disabled={!saveDataEnable}>Save</button>
						</div>
					</div>
				</div>
			)}
		</>
	);

};

import { useState, useReducer, useEffect, useRef } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './Design.css'
import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom'
import { newDesignService, nextStepDesignService } from './Service'
import { DesignContext, DesignDispatchContext, CacheContext } from './DesignContext'
import { designReducer, initialState, newDesign, updateDesignGlobal, nextStep, lastStep } from './reducers/designReducer';
import { stateUtility } from './Utility'
import Step from './Step'

export default function Design() {
  const [designState, designDispatch] = useReducer(designReducer, initialState);
  const cacheRef = useRef({});

  useEffect(() => {
    var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
    newDesignService().then((response) => {
      console.log("newDesignService response: ", response);
      designDispatch(newDesign(response.data.data[node_COMMONATRIBUTECONSTANT.STORYBUILDERRESPONSENEW_DESIGNID], response.data.data[node_COMMONATRIBUTECONSTANT.STORYBUILDERRESPONSENEW_STEPINFO]));
    }).catch((error) => {
      console.error("newDesignService error: ", error);
    });
  }, []);

  const designSteps = designState.steps;
  const currentStep = designSteps ? designSteps[designState.currentStepUI] : undefined;

  return (
    <>
      <div className="wizard-container">
        <div className="wizard-card">
          <div className="progress-bar">


            {designSteps.map((step, index) => {
              return (<div className={`step-wrapper`} key={index}>
                <div className={`step-arrow ${designState.currentStepUI >= index ? 'active' : ''}`}>
                  <span className="step-arrow-text">{stateUtility.getStepDisplayInfo(step).name}</span>
                </div>
                <div className="step-tooltip">{stateUtility.getStepDisplayInfo(step).description}</div>
              </div>);
            })}

          </div>
          <div>
            <DesignContext value={designState}>
              <DesignDispatchContext value={designDispatch}>
                <CacheContext value={cacheRef}>

                  <div className="form-container">

                    <Step></Step>
                  </div>
                </CacheContext>
              </DesignDispatchContext>
            </DesignContext>
          </div>

        </div>
      </div>

    </>
  )
}

import { useState, useReducer, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom'
import {newDesignService, createComponentQuestionItemService, nextStepDesignService} from './Service'
import { DesignContext, DesignDispatchContext, DataSourceContext } from './DesignContext'
import { designReducer, initialState, newDesign, updateDesignGlobal, nextStep, lastStep } from './reducers/designReducer';
import Step from './Step'

function App() {

  const [designState, designDispatch] = useReducer(designReducer, initialState);
  const [dataSources, setDataSources] = useState([]);

    useEffect(() => {
      console.log("load all services response");
        const service = createComponentQuestionItemService();
        service.getLoadServicesRequest((services) => {
            setDataSources(services);
        });
    }, []);


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
    const currentStep = designSteps?designSteps[designState.currentStepUI]:undefined;

    var onBack = function(){
      if(designState.currentStepUI!=0){
          designDispatch(lastStep());
      }
    };


    var onNext = function(){
        if(designState.currentStepUI<designState.currentStepServer && designState.isStepDirty[designState.currentStepUI]==true){
            designDispatch(nextStep());
        }
        else{
            nextStepDesignService(designState.designId, currentStep).then((response) => {
                // Handle response
                designDispatch(updateDesignGlobal(response.data.data.stepInfo, response.data.data.currentStep));
            });
        }

    };


  return (
    <>
    <div>
      <DesignContext value={designState}>
        <DesignDispatchContext value={designDispatch}>
          <DataSourceContext value={dataSources}>

<Step></Step>


          </DataSourceContext>
        </DesignDispatchContext>
      </DesignContext>
    </div>

    <div>

            <button onClick={onBack}>
                Back
            </button>

            <button onClick={onNext}>
                Next
            </button>

    </div>


    </>
  )
}

export default App

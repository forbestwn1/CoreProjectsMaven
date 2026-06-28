import { useState, useReducer, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import StepChooseDataSource from './StepChooseDataSource'
import StepCustomerizeUI from './StepCustomerizeUI'
import StepNewDesign from './StepNewDesign'
import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom'
import {newDesignService, createComponentQuestionItemService} from './Service'
import { DesignContext, DesignDispatchContext, DataSourceContext } from './DesignContext'
import { designReducer, initialState, newDesign } from './reducers/designReducer';


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


  return (
    <>
      <DesignContext value={designState}>
        <DesignDispatchContext value={designDispatch}>
          <DataSourceContext value={dataSources}>

      <BrowserRouter>

        <nav>
          <NavLink to="/new-design">New Design</NavLink>
          <br></br>
          <NavLink to="/choose-data-source">Choose Data Source</NavLink>
          <br></br>
          <NavLink to="/customerize-ui">Customerize UI</NavLink>
          <br></br>
        </nav>

        <Routes>
          <Route path="/new-design" element={<StepNewDesign />} />
          <Route path="/choose-data-source" element={<StepChooseDataSource />} />
          <Route path="/customerize-ui" element={<StepCustomerizeUI />} />
        </Routes>

      </BrowserRouter>

          </DataSourceContext>
        </DesignDispatchContext>
      </DesignContext>
    </>
  )
}

export default App

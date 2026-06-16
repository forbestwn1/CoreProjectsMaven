import { useState, useReducer, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import ChooseDataSourceStep from './ChooseDataSourceStep'
import CustomerizeUI from './CustomerizeUI'
import NewDesign from './NewDesign'
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
          <Route path="/new-design" element={<NewDesign />} />
          <Route path="/choose-data-source" element={<ChooseDataSourceStep />} />
          <Route path="/customerize-ui" element={<CustomerizeUI />} />
        </Routes>

      </BrowserRouter>

          </DataSourceContext>
        </DesignDispatchContext>
      </DesignContext>
    </>
  )
}

export default App

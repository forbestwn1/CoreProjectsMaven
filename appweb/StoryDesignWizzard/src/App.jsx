import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import ChooseDataSourceStep from './ChooseDataSourceStep'
import CustomerizeUI from './CustomerizeUI'
import NewDesign from './NewDesign'
import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom'
import {newDesignService} from './Service'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
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


    </>
  )
}

export default App

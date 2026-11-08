import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

if(typeof nosliw !== "undefined" && nosliw.status=="active"){
  createRoot(document.getElementById('root')).render(
    <App />
  )
}
else{
  document.addEventListener('nosliwActive', () => {
    createRoot(document.getElementById('root')).render(
      <App />
    )
  })
}

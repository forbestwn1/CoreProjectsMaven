import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
server: {
    allowedHosts: [
      'lastonemile.ca'
    ]
  },

  plugins: [react()],
resolve: {
  alias: {
    'react-dom/client': 'react-dom/profiling'
  }
}
})


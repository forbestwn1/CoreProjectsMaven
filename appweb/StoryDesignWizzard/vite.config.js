import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
server: {
    allowedHosts: [
      'ec2-99-79-189-69.ca-central-1.compute.amazonaws.com',
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


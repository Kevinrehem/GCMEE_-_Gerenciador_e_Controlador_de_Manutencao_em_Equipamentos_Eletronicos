import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { URL, fileURLToPath } from 'url' // <-- 1. Importe URL e fileURLToPath

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react({
      babel: {
        plugins: [['babel-plugin-react-compiler']],
      },
    }),
  ],
  resolve: {
    alias: {
      // 2. Use 'import.meta.url' para criar o caminho para 'src'
      "@": fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
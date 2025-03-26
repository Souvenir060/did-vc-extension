import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// GitHub Pages 通常部署在项目名称的子路径下，例如 username.github.io/repository-name/
// 这里使用"did-vc-extension"作为仓库名称
const repoName = "did-vc-extension"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  // 在生产环境中设置基本路径为仓库名称
  base: process.env.NODE_ENV === 'production' ? `/${repoName}/` : '/',
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      }
    }
  }
})

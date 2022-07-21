import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/css/global.css'

Vue.config.productionTip = false
import axios from 'axios'
axios.defaults.baseURL="http://10.14.155.46:8181"
new Vue({
  axios,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

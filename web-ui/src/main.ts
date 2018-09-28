// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import axios from 'axios';
import ElementUI from 'element-ui';

//@ts-ignore
import locale from 'element-ui/lib/locale/lang/en'; //Its ok if this line shows error in VSCode

import '@clr/icons';
import '@clr/icons/shapes/all-shapes';
import '@clr/icons/clr-icons.min.css';
import router from '@/router';
import store from '@/store';
import Rest from '@/rest/Rest';
import RestUtil from '@/rest/RestUtil';
import AppMenu from '@/menu/TopNav';
import i18n, {loadLang} from '@/lang/index';

import App from '@/App.vue';

Vue.config.productionTip = false;
Vue.use(ElementUI,{locale});

// A golbal directiove 'v-sw-focus' which will set focus upon inertion into DOM
Vue.directive('sw-focus', {
  // When the bound element is inserted into the DOM...
  inserted: function (el) {
    // Focus the element
    el.focus()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  template: '<App/>',
  components:{ App },
  router,
  store ,
  i18n,
  beforeCreate: function () {
    var me = this;

    //Load Language bundle (if not defined)
    if (this.$store.state.lang === undefined) {
      this.$store.commit('lang',"en");
    }
    loadLang(this.$store.state.lang);
    //Set the header Item
    if (this.$store.state.currentHeaderItem === undefined) {
      //this.$store.commit('currentHeaderItem',TopNav.items[0]);
    }

    //Set Axios Defaults and  global response Interceptor
    axios.defaults.timeout = RestUtil.ajaxTimeout;
    axios.interceptors.response.use(RestUtil.globalSuccessParser.bind(this), RestUtil.globalErrorParser.bind(this));

  },
  mounted(){
    let me = this;
    console.log("Mounted:(main.js)");

  }

})
import Vue from 'vue'
import Vuex from 'vuex'
import { MutationTree, ActionTree, GetterTree } from "vuex";
import axios from 'axios';
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)

const persistMutations:string[]=['baseUrl', 'lang','user','role', 'org','jwt','ldap','dashboardInterval']; // only these mutations will be persisted
const vuexLocal = new VuexPersistence({
  key:'sw_es',
  storage: window.localStorage,
  filter: function(mutation){
    return persistMutations.indexOf(mutation.type)>=0 ? true:false;
  }
})
export interface State {
  lang:string,
  baseUrl:string,
  dashboardInterval: string,
  currentPageTitle : string,
  currentHeaderItem: object // Includes the Sidemenu (refer app-shell/AppMenu.js)
  currentSideNavItem:object, // Includes the Sidemenu (refer AppMenu.js)
  ldapServers: string[] | boolean,
  ftpServers : string[] | boolean,
  jwt :string,
  user:string,
  role:string,
  org:string,
  serialNumber:string,
  licenseCodes:string,
  isHosted:boolean,
  isWindows:boolean,
  build:string,
  policyValidationMap:object|undefined,
  isServerAlive:boolean
}

const state:State = {
  lang:'en',
  baseUrl:'',
  dashboardInterval: '',
  currentPageTitle : '',
  currentHeaderItem: {}, // Includes the Sidemenu (refer app-shell/AppMenu.js)
  currentSideNavItem:{}, // Includes the Sidemenu (refer AppMenu.js)
  ldapServers:[],
  ftpServers:[],
  jwt :'',
  user:'',
  role:'',
  org:'',
  serialNumber:'',
  licenseCodes:'',
  isHosted:false,
  isWindows:false,
  build:'',
  policyValidationMap:undefined,
  isServerAlive:true
}

const getters: GetterTree<State, any> = {
  lang(state) {return state.lang;},
  baseUrl(state) {return state.baseUrl;},
  dashboardInterval(state) {return state.dashboardInterval;},
  currentPageTitle(state) {return state.currentPageTitle;},
  currentHeaderItem(state) {return state.currentHeaderItem;},
  currentSideNavItem(state) {return state.currentSideNavItem;},
  ldapServers(state){return state.ldapServers},
  ftpServers(state){
    if (state.ftpServers == null || state.ftpServers==false){
      console.log("ftp:false")
    }
    return state.ftpServers
  },
  jwt(state)  {return state.jwt; },
  user(state) {return state.user;},
  role(state) {return state.role;},
  org(state)  {return state.org; },
  serialNumber(state)  {return state.serialNumber; },
  licenseCodes(state)  {return state.licenseCodes; },
  isHosted(state)  {return state.isHosted; },
  isWindows(state) {return state.isWindows; },
  build(state)     {return state.build; },
  policyValidationMap(state) {return state.policyValidationMap;},
  isServerAlive(state){return state.isServerAlive}
};


const mutations: MutationTree<State> = {
  lang(state, payload) {state.lang = payload},
  baseUrl(state, payload) {
    //axios.defaults.baseURL = payload; //set axios global default
    state.baseUrl = payload;
  },
  dashboardInterval (state, payload:string) {state.dashboardInterval = payload;},
  currentPageTitle  (state, payload:string) {state.currentPageTitle   = payload},
  currentHeaderItem (state, payload)        {state.currentHeaderItem  = payload},
  currentSideNavItem(state, payload)        {state.currentSideNavItem = payload},
  ldapServers(state,payload:string[])       {state.ldapServers = payload},
  ftpServers(state,payload:string[])        {state.ftpServers = payload},
  jwt(state, payload) {
    axios.defaults.headers.common['Authorization'] = payload; //set axios global default auth header
    state.jwt = payload
  },
  user(state, payload:string)          {state.user = payload},
  role(state, payload:string)          {state.role = payload},
  org (state, payload:string)          {state.org  = payload},
  serialNumber (state, payload:string) {state.serialNumber  = payload},
  licenseCodes (state, payload:string) {state.licenseCodes  = payload},
  isHosted (state, payload:boolean)    {state.isHosted  = payload},
  isWindows(state, payload:boolean)    {state.isWindows = payload},
  build    (state, payload:string)     {state.build     = payload},
  policyValidationMap(state, payload)  {state.policyValidationMap= payload},
  isServerAlive(state, payload:boolean){state.isServerAlive = payload}
};
const actions: ActionTree<State, any> = {};

export default new Vuex.Store<State>({
  state,
  getters,
  mutations,
  actions,
  plugins: [vuexLocal.plugin]
});





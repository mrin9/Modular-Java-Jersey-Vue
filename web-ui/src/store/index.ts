import Vue from 'vue'
import Vuex from 'vuex'
import { MutationTree, ActionTree, GetterTree } from "vuex";
import axios from 'axios';
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)

const persistMutations:string[]=['lang','user','role', 'jwt']; // only these mutations will be persisted
const vuexLocal = new VuexPersistence({
  key:'sw_es',
  storage: window.localStorage,
  filter: function(mutation){
    return persistMutations.indexOf(mutation.type)>=0 ? true:false;
  }
})
export interface State {
  lang:string,
  currentPageTitle : string,
  currentHeaderItem: object // Includes the Sidemenu (refer app-shell/AppMenu.js)
  currentSideNavItem:object, // Includes the Sidemenu (refer AppMenu.js)
  jwt :string,
  user:string,
  role:string,
  userName:string,
  email:string,
  customerId:string,
  employeeId:string
}

const state:State = {
  lang:'en',
  currentPageTitle : '',
  currentHeaderItem: {}, // Includes the Sidemenu (refer app-shell/AppMenu.js)
  currentSideNavItem:{}, // Includes the Sidemenu (refer AppMenu.js)
  jwt :'',
  user:'',
  role:'',
  userName:'',
  email:'',
  customerId:'',
  employeeId:''
}

const getters: GetterTree<State, any> = {
  lang(state) {return state.lang;},
  currentPageTitle(state)   {return state.currentPageTitle;},
  currentHeaderItem(state)  {return state.currentHeaderItem;},
  currentSideNavItem(state) {return state.currentSideNavItem;},
  jwt(state)  {return state.jwt; },
  user(state) {return state.user;},
  role(state) {return state.role;},
  userName(state)   {return state.userName;},
  email(state)      {return state.email;},
  customerId(state) {return state.customerId;},
  employeeId(state) {return state.employeeId;},
};


const mutations: MutationTree<State> = {
  lang(state, payload) {state.lang = payload},
  currentPageTitle  (state, payload:string) {state.currentPageTitle   = payload},
  currentHeaderItem (state, payload)        {state.currentHeaderItem  = payload},
  currentSideNavItem(state, payload)        {state.currentSideNavItem = payload},
  jwt(state, payload) {
    axios.defaults.headers.common['Authorization'] = payload; //set axios global default auth header
    state.jwt = payload
  },
  user(state, payload:string)       {state.user = payload},
  role(state, payload:string)       {state.role = payload},
  userName(state, payload:string)   {state.userName = payload},
  email(state, payload:string)      {state.email = payload},
  customerId(state, payload:string) {state.customerId = payload},
  employeeId(state, payload:string) {state.employeeId = payload},


};
const actions: ActionTree<State, any> = {};

export default new Vuex.Store<State>({
  state,
  getters,
  mutations,
  actions,
  plugins: [vuexLocal.plugin]
});





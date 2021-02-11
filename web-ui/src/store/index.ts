import { createStore, MutationTree, ActionTree, GetterTree } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import axios from 'axios';

const localStoragePersist = createPersistedState({
  key: 'app-local',
  paths: ['user', 'userName', 'baseUrl', 'lang'],
  storage: window.localStorage,
});

const sessionStoragePersist = createPersistedState({
  key: 'app-session',
  paths: ['role', 'jwt', 'jwtTime'],
  storage: window.sessionStorage,
});

export interface State {
  lang: string;
  baseUrl: string;
  currentPageTitle: string;
  currentHeaderItem: Record<string, string>; // Includes the Sidemenu (refer app-shell/AppMenu.js)
  currentSideNavItem: Record<string, string>; // Includes the Sidemenu (refer AppMenu.js)
  jwt: string;
  jwtTime: string;
  user: string;
  role: string;
  userName: string;
  email: string;
  customerId: string;
  employeeId: string;
}

const getters: GetterTree<State, State> = {
  lang(state: State) { return state.lang; },
  baseUrl(state): string { return state.baseUrl; },
  currentPageTitle(state: State) { return state.currentPageTitle; },
  currentHeaderItem(state: State) { return state.currentHeaderItem; },
  currentSideNavItem(state: State) { return state.currentSideNavItem; },
  jwt(state: State) { return state.jwt; },
  jwtTime(state: State) { return state.jwtTime; },
  user(state: State) { return state.user; },
  role(state: State) { return state.role; },
  userName(state: State) { return state.userName; },
  email(state: State) { return state.email; },
  customerId(state: State) { return state.customerId; },
  employeeId(state: State) { return state.employeeId; },
};

const mutations: MutationTree<State> = {
  lang(state: State, payload) { state.lang = payload; },
  baseUrl(state, payload: string) { state.baseUrl = payload; },
  currentPageTitle(state: State, payload: string) { state.currentPageTitle = payload; },
  currentHeaderItem(state: State, payload) { state.currentHeaderItem = payload; },
  currentSideNavItem(state: State, payload) { state.currentSideNavItem = payload; },
  jwt(state: State, payload) {
    axios.defaults.headers.common.Authorization = payload; // set axios global default auth header
    state.jwt = payload;
  },
  jwtTime(state: State, payload) { state.jwtTime = payload; },
  user(state: State, payload: string) { state.user = payload; },
  role(state: State, payload: string) { state.role = payload; },
  userName(state: State, payload: string) { state.userName = payload; },
  email(state: State, payload: string) { state.email = payload; },
  customerId(state: State, payload: string) { state.customerId = payload; },
  employeeId(state: State, payload: string) { state.employeeId = payload; },
};

const actions: ActionTree<State, State> = {};

export default createStore({
  state(): State {
    return {
      lang: 'en',
      baseUrl: '',
      currentPageTitle: '',
      currentHeaderItem: {}, // Includes the Sidemenu (refer app-shell/AppMenu.js)
      currentSideNavItem: {}, // Includes the Sidemenu (refer AppMenu.js)
      jwt: '',
      jwtTime: '',
      user: '',
      role: '',
      userName: '',
      email: '',
      customerId: '',
      employeeId: '',
    };
  },
  getters,
  mutations,
  actions,
  plugins: [localStoragePersist, sessionStoragePersist],
});

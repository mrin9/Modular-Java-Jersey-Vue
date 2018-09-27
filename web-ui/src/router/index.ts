import Vue from 'vue'
import Router from 'vue-router'
import { RouterOptions, Location, RouteConfig, Route } from 'vue-router'
import store from '@/store/index'

import AppShell from '@/components/app-shell/AppShell.vue'
import appMenuUtil from '@/menu/AppMenu'

//login
import LoginPage from '@/views/LoginPage.vue'

//Charts
import Dashboard from '@/views/dashboard/Dashboard.vue'

//import SamplePage from '@/views/sample/SamplePage.vue'
import VuexTest from '@/views/sample/VuexTest.vue'
import ButtonPage from '@/views/sample/ButtonPage.vue'
import TabPage from '@/views/sample/TabPage.vue'
import NotifyPage from '@/views/sample/NotifyPage.vue'
import FontPage from '@/views/sample/FontPage.vue'
import FormPage from '@/views/sample/FormPage.vue'
import UnderConstructionPage from '@/views/sample/UnderConstructionPage.vue'
import TablePage from '@/views/sample/TablePage.vue'
import FrameworkPage from '@/views/sample/FrameworkPage.vue'
import DropdownPage from '@/views/sample/DropdownPage.vue'
import LogoPage from '@/views/sample/LogoPage.vue'
import HeaderPage from '@/views/sample/HeaderPage.vue'
import SideNavPage from '@/views/sample/SideNavPage.vue'


Vue.use(Router)

const router =  new Router({
  routes: [
    { path: '/',
      redirect: function(to){
        // if authenticated redirect to appshell else to login
        return '/login';
      }
    },
    { path: '/login'     , component: LoginPage    , meta: { permitAll: true } },
    { path: '/home'      , redirect:  '/home/monitor/dashboard', component:AppShell,
      children: [
        {
          path: 'monitor', redirect:'/home/monitor/dashboard', component: {render (c) { return c('router-view') } },
          children: [
            { path: 'dashboard'    , component:Dashboard  },
            { path: 'connections'  , component:{template: '<div>connections</div>'}  },
          ]
        },
        {
          path: 'investigate', redirect:'/home/investigate/junkbox', component: {render (c) { return c('router-view') } },
          children: [
            { path: 'connection-logs' , component:{template: '<div>Connection Logs</div>'}  },
            { path: 'capture-atp-logs', component:{template: '<div>Capture ATP Logs</div>'}  },
            { path: 'dmarc'           , component:{template: '<div>DMARC Report</div>'}  },
            { path: 'audit-trail'     , component:{template: '<div>Audit Trail</div>'}  },
            { path: 'diag'            , component:{template: '<div>Diagnostics</div>'}  },
          ]
        },
        {
          path: 'manage', redirect:'/home/manage/license',  component: {render (c) { return c('router-view') } },
          children: [
            { path: 'users'          , component:{template: '<div> Users </div>'}  },
            { path: 'network-mta'          , component: {template: '<div> Networks MTA </div>'}  },
            { path: 'report-networks'   , component:{template: '<div> Known Network Reports </div>'}  },
          ]
        },
        {
          path: 'components', redirect:'/home/components/button', component: {render (c) { return c('router-view') } },
          children: [
            { path: 'sample' , component:{template: '<div>Under Construction</div>'} },
            { path: 'button' , component:ButtonPage },
            { path: 'tab'    , component:TabPage },
            { path: 'notify' , component:NotifyPage },
            { path: 'font'   , component:FontPage },
            { path: 'form'   , component:FormPage },
            { path: 'vuex'   , component:VuexTest },
            { path: 'uc'     , component:UnderConstructionPage },
            { path: 'table'  , component:TablePage },
            { path: 'frameworks', component:FrameworkPage },
            { path: 'dd'     , component:DropdownPage },
            { path: 'logo'   , component:LogoPage },
            { path: 'header' , component:HeaderPage },
            { path: 'sidenav', component:SideNavPage },
          ]
        }
      ]
    }

  ]
})

router.beforeEach((to:Route, from:Route, next:Function) => {
  if (to.meta.permitAll) {
    next();
  }
  else {
    //console.log("Need Authentication: user:%s, role:%s, tokek:$s", store.state.user, store.state.role, store.state.jwt);
    if (!store.state.user || !store.state.role || !store.state.jwt ){
      next('/login')
    }
    else{
      next();
    }
  }

})

router.afterEach((to:Route, from:Route) => {
  //After navigation set currentHeaderItem
  //AppMenu.search() also expands the group if a matching path is found
  let navSection = appMenuUtil.search(to.path,'route');
  store.commit('currentHeaderItem',navSection.headerItem);
})

export default router;

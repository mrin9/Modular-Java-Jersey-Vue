import Vue from 'vue'
import Router from 'vue-router'
import { RouterOptions, Location, RouteConfig, Route } from 'vue-router'
import store from '@/store/index'

import AppShell from '@/components/app-shell/AppShell.vue'
import TopNav from '@/menu/TopNav'

//login
import LoginPage from '@/views/LoginPage.vue'

//Charts
import Dashboard from '@/views/dashboard/Dashboard.vue'

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
  //let navSection = appMenuUtil.search(to.path,'route');
  //store.commit('currentHeaderItem',navSection.headerItem);
})

export default router;

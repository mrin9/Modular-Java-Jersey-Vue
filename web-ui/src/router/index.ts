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
    { path: '/login'      , component: LoginPage                           , meta: { permitAll: true} },
    { path: '/register'   , component: { template: '<div>Register</div>'  }, meta: { permitAll: true} },
    { path: '/home'       , component: AppShell,
      children: [
        { path: 'my-orders'   , component:{ template: '<div>My Order</div>'   }},
        { path: 'my-profile' , component:{ template: '<div>My Profile</div>' }},
        { path: 'my-cart'    , component:{ template: '<div>My Cart</div>'    }},
        { path: 'manage'     , redirect:  '/manage/dashboard', component: { render(c) { return c('router-view') } },
          children: [
            { path: 'dashboard' , component: Dashboard },
            { path: 'users'     , component:{ template: '<div>Manage Users</div>'    }},
            { path: 'customers' , component:{ template: '<div>Manage Customers</div>'}},
            { path: 'orders'    , component:{ template: '<div>Manage Orders</div>'}},
            { path: 'products'  , component:{ template: '<div>Manage Products</div>' }},
            { path: 'employees' , component:{ template: '<div>Manage Employees</div>' }},
            { path: 'carts'     , component:{ template: '<div>Manage Carts</div>' }}
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

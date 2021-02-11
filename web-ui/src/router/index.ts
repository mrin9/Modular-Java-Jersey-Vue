import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import LoginPage from '@/views/LoginPage.vue';
import AppShell from '@/components/AppShell.vue';
import PageContainer from '@/components/PageContainer.vue';
import Users from '@/views/Users.vue';
import Customers from '@/views/Customers.vue';
import Orders from '@/views/Orders.vue';
import Products from '@/views/Products.vue';
import Employees from '@/views/Employees.vue';
import Register from '@/views/Register.vue';
import Dashboard from '@/views/Dashboard.vue';
import store from '@/store';
// import Home from '../views/Home.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: () => {
      console.log('check Is Authenticated');
      // if authenticated redirect to appshell else to login
      return '/login';
    },
  },
  { path: '/login', component: LoginPage, meta: { permitAll: true } },
  { path: '/register', component: Register, meta: { permitAll: true } },
  {
    path: '/home',
    redirect: '/home/manage/dashboard',
    component: AppShell,
    children: [
      {
        path: 'manage',
        redirect: '/home/manage/dashboard',
        component: PageContainer,
        children: [
          { path: 'dashboard', component: Dashboard },
          { path: 'users', component: Users },
          { path: 'customers', component: Customers },
          { path: 'orders', component: Orders },
          { path: 'products', component: Products },
          { path: 'employees', component: Employees },
        ],
      },
    ],
  },
  // the default route, when none of the above matches:
  {
    path: '/:pathMatch(.*)*',
    redirect: () => {
      if (store.getters.jwt) { // check if authenticated
        return '/home';
      }
      return '/login';
    },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});
export default router;

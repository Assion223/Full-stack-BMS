import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import store from '@/store'
import request from '@/utils/request'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: Manage,
    redirect: "/home",
    meta: { isAuth: true },
    children: [
      {
        path: 'user',
        name: 'User',
        meta: { name: "用户", isAuth: true },
        component: () => import('../views/User.vue'),
      },
      {
        path: 'home',
        name: 'Home',
        meta: { name: " ", isAuth: true },
        component: () => import('../views/Home.vue'),
      },
      {
        path: 'person',
        name: 'Person',
        meta: { name: "个人信息", isAuth: true },
        component: () => import('../views/Person.vue'),
      },
      {
        path: 'files',
        name: 'Files',
        meta: { name: "文件管理", isAuth: true },
        component: () => import('../views/Files.vue'),
      },
      {
        path: 'role',
        name: 'Role',
        meta: { name: "角色管理",isAuth: false },
        component: () => import('../views/Role.vue')
      },
      {
        path: 'menu',
        name: 'Menu',
        meta: { name: "菜单管理",isAuth: false },
        component: () => import('../views/Menu.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    meta: { isAuth: false },
    component: () => import('../views/Login.vue')
  },
  {
    path: '/registor',
    name: 'Registor',
    meta: { isAuth: false },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Registor.vue')
  },
  {
    path: '*',
    name: '404',
    meta: { isAuth: false },
    component: () => import('../views/404.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.meta.name)
  store.commit("setPath")
  if (to.meta.isAuth && (to.path !== '/login' || to.path !== '/registor')) {
    let user = localStorage.getItem("user") != null ? JSON.parse(localStorage.getItem('user'))[0].password : ""
    if (!user) {
      next({ name: "Login" })
    } else {
      request.interceptors.request.use(config => {
        config.headers['password'] = user;
        return config
      });
      request.get('/user/token', user).then(res => {
        if (res.code != "200") {
          next({ name: "Login" })
        }
      })
    }
  }
  next()
})

export default router

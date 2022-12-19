import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import Home from "../views/home.vue";
const routes:RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/dashboard',
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页',
                    author: true,
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/dashboard.vue")
            }, 
            {
                path: "/userslist",
                name: "userslist",
                meta: {
                    title: '用户列表',
                    author: true,
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/userslist.vue")
            },{
                path: "/travel",
                name: 'travel',
                meta: {
                    title: '交通路线',
                    author: true,
                },
                component:() => import(/* webpackChunkName: "travel" */ "../views/travel/travel.vue")
            },{
                path: "/car",
                name: 'car',
                meta: {
                    title: '汽车路线',
                    author: true,
                },
                component:() => import(/* webpackChunkName: "car" */ "../views/travel/car.vue")
            },{
                path: "/plane",
                name: 'plane',
                meta: {
                    title: '飞机航线',
                    author: true,
                },
                component:() => import(/* webpackChunkName: "plane" */ "../views/travel/plane.vue")
            },{
                path: "/train",
                name: 'train',
                meta: {
                    title: '火车路线',
                    author: true,
                },
                component:() => import(/* webpackChunkName: "train" */ "../views/travel/train.vue")
            },{
                path: "/table",
                name: "basetable",
                meta: {
                    title: '疫情风险低管理',
                    author: true,
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/table.vue")
            },  {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心',
                    author: true,
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/user.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录',
            author: false,
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/login.vue")
    },{
        path: "/:catchAll(.*)",
        redirect: "/404/"
    },{
        path:'/404/',
        name: '404',
        component: () => import (/* webpackChunkName: "404" */ '../views/404.vue')
    }
];
const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    if(to.meta.author && !sessionStorage.getItem("is_login")) {
        next({name:"Login"});
    } else {
        next();
    }
});

export default router;
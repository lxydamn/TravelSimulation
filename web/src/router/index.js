import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue"
import UserReCordView from '@/views/UserRecordView.vue';
import IntroduceCoderView from '@/views/IntroduceCoderView.vue';
import UserLoginView from '@/views/user/UserLoginView';
import UserRegisterView from '@/views/user/UserRegisterView'
import NotFoundView from '@/views/NotFoundView';
import store from "@/store";
const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/home/",
    meta: {
      author: true,
    }
  },
  {
    path: "/user/login/",
    name: "user_login",
    component: UserLoginView,
    meta: {
      author: false,
    }
  },
  {
    path: "/user/register/",
    name: "user_register",
    component: UserRegisterView,
    meta: {
      author: false,
    }
  },
  {
    path: "/home/",
    name: "map",
    component: HomeView,
    meta: {
      author: true,
    }
  },
  {
    path: "/user/info/",
    name: "info",
    component: UserReCordView,
    meta: {
      author: true,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
    meta: {
      author: false,
    }
  },
  {
    path: "/introduce/",
    name: "introduce",
    component: IntroduceCoderView,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) =>{
  if(to.meta.author && !store.state.user.is_login){
    next({name:"user_login"});
  }else{
    next();
  }
})
export default router

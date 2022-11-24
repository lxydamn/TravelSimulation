<template>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow bg-body rounded">
  <div class="container-fluid">
    <div class="navbar-brand">
      <svg class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"
           width="48" height="48"><path d="M615.616 800.352l-68.8 127.84H162.016l94.464-127.36h172.576l64.16-128.32h-100.128l-105.056 63.552H200A381.92 381.92 0 0 0 129.312 928H97.216C113.536 713.216 293.024 544 512 544s398.432 169.216 414.784 384H894.72a381.664 381.664 0 0 0-33.344-127.648h-245.76zM512 576a383.04 383.04 0 0 0-286.272 128.064h53.408l105.056-63.552h160.832l-96.16 192.32H272.608l-46.976 63.36h302.048l68.8-127.84h248.32A383.84 383.84 0 0 0 512 576zM252.576 131.84L553.6 175.04c74.304-33.024 124.736-53.056 152.128-60.352 45.44-12.064 110.272 20.896 110.272 55.552 0 19.744-16.448 40.128-50.88 66.432-332.224 165.824-502.752 249.536-520.992 255.68-21.984 7.328-44.032 3.584-65.472-11.104l-105.984-81.984 55.36-62.496 93.344 28.544 138.144-79.424-160.896-89.824 53.952-64.256z m-133.248 263.04l78.176 60.48c12.64 8.704 24.32 10.656 36.48 6.624 15.872-5.312 191.264-91.456 514.176-252.288 24.288-18.816 35.84-33.12 35.84-39.456 0-10.24-42.624-31.904-70.08-24.608-25.696 6.816-76.48 27.072-151.488 60.48l-4.224 1.92L265.6 166.016l-17.6 20.96 176.416 98.496L225.28 400l-86.912-26.56-19.04 21.472z"
                                        fill="#d5a422"></path></svg>
      <router-link class="navbar-brand" :to="{name:'home'}"> Travel Simulation </router-link>
    </div>


    <ul class="navbar-nav" v-if="$store.state.user.is_login">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
          {{ $store.state.user.username }}
        </a>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
          <li>
            <router-link class="dropdown-item" :to="{name: 'info'}">我的空间</router-link>
          </li>
          <li>
            <a class="dropdown-item" href="#" @click="logout">退出</a>
          </li>
        </ul>
      </li>
    </ul>
    <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
      <li class="nav-item">
        <router-link class="nav-link" :to="{name: 'user_login' }" role="button">
          登录
        </router-link>
      </li>
      <li :class="router_name">
        <router-link class="nav-link" :to="{name: 'user_register'}" role="button">
          注册
        </router-link>
      </li>
    </ul>

  </div>
</nav>


</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    const route = useRoute();
    let router_name = computed(() => route.name)

    const logout = () => {
      store.dispatch("logout");
    }

    return {
      router_name,
      logout
    }
  }
}

</script>
<style scoped>
.navbar-brand{
  font-weight:600;
  font-family: "Bodoni MT Poster Compressed";
  font-size: x-large;
  text-align: center;
}
.navbar-nav{
  font-weight: bolder;
}
.navbar{
  color: #FFFFFF;
}
</style>
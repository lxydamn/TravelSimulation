<template>
  <LoginCard v-if="!$store.state.user.pulling_info" style="margin-bottom: 15%">

    <div class="container" style="margin-top: 20px">

      <form @submit.prevent="login">
        <div class="form-floating mb-3" >
          <input v-model="username" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
          <label for="floatingInput">用户名</label>
        </div>
        <div class="form-floating" style="margin-top: 25px">
          <input v-model="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
          <label for="floatingPassword">密码</label>
        </div>
        <div class="d-grid gap-2 col-5 mx-auto">
          <div class="error-message">{{error_message}}</div>
          <button class="btn btn-primary" type="submit">登录</button>
        </div>
        <div style="margin-top: 20px">
          <router-link  :to="{name: 'user_register' }">
            没有账号？立即注册一个
          </router-link>
        </div>
      </form>

    </div>


  </LoginCard>

</template>

<script>
import LoginCard from '@/components/LoginCard';
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router/index'


export default {
  components:{
    LoginCard,
  },

  setup() {
      const store = useStore();
      let username = ref('');
      let password = ref('');
      let error_message = ref('');

      const jwt_token = localStorage.getItem("jwt_token");
      console.log(jwt_token);
      if(jwt_token) {
        store.commit("updateToken", jwt_token);
        store.dispatch("getinfo", {
          success() {
            router.push({name:"home"});
            store.commit("updatePulling_info", false);
          },
          error() {
            store.commit("updatePulling_info", false);
          }
        })
      } else {
          store.commit("updatePulling_info", false);
      }

      const login = () => {
        error_message.value ="";
        store.dispatch("login", {
          username:username.value,
          password:password.value,
          success() {
            store.dispatch("getinfo",{
              success() {
                router.push({name:'home'});
              }
            })
          },
          error() {
            error_message.value = "用户名或密码错误！"
          }
        })

    }

    return {
        username, error_message,password,
        login,
    }
  }
}
</script>

<style>
.d-grid{
  margin-top: 20px;
}
.btn{
  width: 150px;
  height: 40px;
}
.error-message{
  color: red;
}
</style>
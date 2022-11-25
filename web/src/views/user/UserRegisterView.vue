<template>
  <LoginCard style="margin-bottom: 15%">

    <div class="container" style="margin-top: 20px">
      <form @submit.prevent="register">
        <div class="form-floating mb-3" >
          <input v-model="username" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
          <label for="floatingInput">用户名</label>
        </div>
        <div class="form-floating" style="margin-top: 25px">
          <input v-model="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
          <label for="floatingPassword">密码</label>
        </div>
        <div class="form-floating" style="margin-top: 25px">
          <input v-model="confirmedPassword" type="password" class="form-control" id="floatingPassword" placeholder="Password">
          <label for="floatingPassword">请确认密码</label>
        </div>
        <div class="d-grid gap-2 col-5 mx-auto">
          <div class="error-message">{{error_message}}</div>
          <button class="btn btn-primary" type="submit">注册</button>
        </div>
      </form>

    </div>


  </LoginCard>
</template>

<script>
import LoginCard from '@/components/LoginCard';
import router from '@/router/index'
import $ from 'jquery';
import {ref} from 'vue';
export default {
  components:{
    LoginCard,
  },
  setup() {
    let username = ref('');
    let password = ref('');
    let confirmedPassword = ref('');
    let error_message = ref('');

    const register = () => {

      console.log("test")
      $.ajax({
        url:"http://localhost:3000/api/user/register/",
        type:"POST",
        data: {
          username : username.value,
          password : password.value,
          confirmedPassword: confirmedPassword.value,
        },
        success(resp) {
          if(resp.error_message === 'success') {
            router.push({name:"user_login"});
          } else {
            error_message = resp.error_message;
          }
        },
        error() {
          console.log("失败");
        }
      })
    }

    return {
      username,
      password,
      confirmedPassword,
      error_message,
      register,
    }

  }
}
</script>

<style scoped>
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
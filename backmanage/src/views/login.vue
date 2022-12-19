<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">后台管理系统</div>
			<el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
				<el-form-item prop="username">
					<el-input v-model="param.username" placeholder="username">
						<template #prepend>
							<el-button :icon="User"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input
						type="password"
						placeholder="password"
						v-model="param.password"
						@keyup.enter="submitForm(login)"
					>
						<template #prepend>
							<el-button :icon="Lock"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm(login)">登录</el-button>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useTagsStore } from '../store/tags';
import { useUserStore } from '../store/user';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import type { FormInstance, FormRules } from 'element-plus';
import { Lock, User } from '@element-plus/icons-vue';
import axios from 'axios';

interface LoginInfo {
	username: string;
	password: string;
}

const user = useUserStore();
const router = useRouter();
const param = reactive<LoginInfo>({
	username: 'admin',
	password: '123123'
});

const rules: FormRules = {
	username: [
		{
			required: true,
			message: '请输入用户名',
			trigger: 'blur'
		}
	],
	password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const login = ref<FormInstance>();

const submitForm = (formEl: FormInstance | undefined) => {

  const jwt_token = localStorage.getItem("jwt_token");

  if(jwt_token) {
	// 直接请求信息
	getinfo();

  } else {

	// 登录请求
	axios({
		url: "http://127.0.0.1:3000/api/user/token/",
        method: "POST",
        params: {
            username: param.username,
            password: param.password,
        }       
	}).then((res) => {
		if(res.data.error_message === 'success') {
			localStorage.setItem("jwt_token", res.data.token);
			user.$patch({
				token : res.data.token,
			})
			getinfo();

			ElMessage({
				showClose: true,
				message: "登录成功！",
				type: 'success',
			})
			
		} else {
			ElMessage({
				showClose: true,
				message: "登录失败！请确认密码是否正确",
				type: 'error',
			})
		}
	}).catch((resp) => {
    ElMessage({
      showClose: true,
      message: "登录失败！获取用户数据失败",
      type: 'error',
    })
  })
  }

};



const getinfo = () => {
	axios({
		url: "http://localhost:3000/api/user/info/",
		method:"get",
		headers:{
			Authorization: "Bearer " + localStorage.getItem("jwt_token"),
		}
	}).then((res) => {
    localStorage.setItem("is_login", "true");
		user.$patch({
			id : res.data.id,
			username :res.data.username,
			photo: res.data.photo,
      		is_login: true,
		})
    	router.push('/dashboard');
	})
}


const tags = useTagsStore();
tags.clearTags();

</script>

<style scoped>
.login-wrap {
	position: relative;
	width: 100%;
	height: 100%;
	background-image: url(../assets/img/login-bg.png);
	background-size: 100%;
}
.ms-title {
	width: 100%;
	line-height: 50px;
	text-align: center;
	font-size: 20px;
	color: rgba(5, 71, 141, 0.615);
	border-bottom: 1px solid #ddd;
}
.ms-login {
	position: absolute;
	left: 50%;
	top: 50%;
	width: 350px;
	margin: -190px 0 0 -175px;
	border-radius: 5px;
	background: rgba(255, 255, 255, 0.3);
	overflow: hidden;
}
.ms-content {
	padding: 30px 30px;
}
.login-btn {
	text-align: center;
}
.login-btn button {
	width: 100%;
	height: 36px;
	margin-bottom: 10px;
}
.login-tips {
	font-size: 12px;
	line-height: 30px;
	color: #fff;
}
</style>

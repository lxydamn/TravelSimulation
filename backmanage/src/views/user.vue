<template>
	<div>
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>基础信息</span>
						</div>
					</template>
					<div class="info">
						<div class="info-image">
							<el-avatar :size="100" :src="user.getPhoto" />
						</div>
						<div class="info-name">{{ user.getUsername }}</div>
						<div class="info-desc">不可能！我的代码怎么可能会有bug！</div>
					</div>
				</el-card>
	</div>
</template>

<script setup lang="ts" name="user">

import 'cropperjs/dist/cropper.css';
import {useUserStore} from "../store/user";

const user = useUserStore();
const name = localStorage.getItem('ms_username');





</script>

<style scoped>
.info {
	text-align: center;
	padding: 35px 0;
}
.info-image {
	position: relative;
	margin: auto;
	width: 100px;
	height: 100px;
	background: #f8f8f8;
	border: 1px solid #eee;
	border-radius: 50px;
	overflow: hidden;
}

.info-edit i {
	color: #eee;
	font-size: 25px;
}
.info-image:hover {
	opacity: 1;
}
.info-name {
	margin: 15px 0 10px;
	font-size: 24px;
	font-weight: 500;
	color: #262626;
}

</style>

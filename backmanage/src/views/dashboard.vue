<template v-show="user.is_login">
	<div>
		<el-row :gutter="20">
			<el-col :span="8">
				<el-card shadow="hover" class="mgb20" style="margin-top: 10px">
					<div class="user-info">
						<el-avatar :size="120" :src="user.getPhoto" />
						<div class="user-info-cont">
							<div class="user-info-name">{{ user.getUsername }}</div>
						</div>
					</div>
					<div class="user-info-list">
						<span>超级管理员</span>
					</div>
					
				</el-card>
				<el-card shadow="hover" style="height: 305px">
					<template #header>
						<div class="clearfix">
							<span>路线统计</span>
						</div>
					</template>
          <div class="pro-content">
            飞机航线
            <el-progress :percentage="71.3" color="#42b983"></el-progress>
          </div>
          <div class="pro-content">
            火车路线
            <el-progress :percentage="24.1" color="#F25E43FF"></el-progress>
          </div>
          <div class="pro-content">
            汽车路线
            <el-progress :percentage="13.7"></el-progress>
          </div>

				</el-card>
			</el-col>
			<el-col :span="16">
        <el-card class="box-card">
          <div class="grid-content grid-con-1">
            <el-icon class="grid-con-icon"><User /></el-icon>
            <div class="grid-cont-right">
              <div class="grid-num">{{data.user.length}}</div>
              <div>用户数量</div>
            </div>
          </div>
          <div class="grid-content grid-con-2">
            <el-icon class="grid-con-icon"><User /></el-icon>
            <div class="grid-cont-right">
              <div class="grid-num">{{data.plane.length}}</div>
              <div>飞机航线</div>
            </div>
          </div>
          <div class="grid-content grid-con-3">
            <el-icon class="grid-con-icon"><User /></el-icon>
            <div class="grid-cont-right">
              <div class="grid-num">{{data.train.length}}</div>
              <div>火车路线</div>
            </div>
          </div>
          <div class="grid-content grid-con-2">
            <el-icon class="grid-con-icon"><User /></el-icon>
            <div class="grid-cont-right">
              <div class="grid-num">{{data.car.length}}</div>
              <div>汽车路线</div>
            </div>
          </div>
          <div class="grid-content grid-con-1">
            <el-icon class="grid-con-icon"><User /></el-icon>
            <div class="grid-cont-right">
              <div class="grid-num">{{data.cities.length}}</div>
              <div>城市数量</div>
            </div>
          </div>
        </el-card>

			</el-col>
		</el-row>
	</div>
</template>

<script setup lang="ts" name="dashboard">
import {useUserStore} from "../store/user";
import {useDataStore} from "../store/data";
import axios from "axios";
const user = useUserStore();
const data = useDataStore();

const init = () => {
  // 请求users
  axios({
    url:"http://localhost:3000/api/back/users/",
    method:"GET",
    headers: {
      Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
    },
  }).then((res) => {
    data.user = res.data;
  })
  // 请求城市
  axios({
    url:"http://localhost:3000/api/back/getcities/",
    method:"GET",
    headers: {
      Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
    },
  }).then((resp) => {
    data.cities = resp.data;
  })

  // 请求路线
  axios({
    url:"http://localhost:3000/api/back/getpaths/",
    method:"GET",
    headers: {
      Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
    },
  }).then((resp) => {
    data.car = resp.data.car;
    data.train = resp.data.train;
    data.plane = resp.data.plane;
  })
}

init();


</script>

<style scoped>

.grid-content {
	display: flex;
	align-items: center;
  margin-top: 5px;
  height: 100px;
}
.pro-content{
  margin-top: 20px;
}
.grid-cont-right {
	flex: 1;
	text-align: center;
	font-size: 14px;
	color: #999;
}

.grid-num {
	font-size: 30px;
	font-weight: bold;
}

.grid-con-icon {
	font-size: 50px;
	width: 100px;
	height: 100px;
	text-align: center;
	line-height: 100px;
	color: #fff;
}

.grid-con-1 .grid-con-icon {
	background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
	color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
	background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
	color: rgb(100, 213, 114);
}

.grid-con-3 .grid-con-icon {
	background: rgb(242, 94, 67);
}


.grid-con-3 .grid-num {
	color: rgb(242, 94, 67);
}

.user-info {
	display: flex;
	text-align: center;
	align-items: center;
	padding-bottom: 20px;
	border-bottom: 2px solid #ccc;
	margin-bottom: 20px;
}

.user-info-cont {
	padding-left: 50px;
	flex: 1;
	font-size: 14px;
	color: #999;
}

.user-info-cont div:first-child {
	font-size: 30px;
	color: #222;
}

.user-info-list {
	font-size: 14px;
	color: #999;
	line-height: 25px;
}

.user-info-list span {
	font-size: 40px;
	color: black;
	margin-left: 100px;
}

.mgb20 {
	margin-bottom: 20px;
}
.box-card{
  margin-top: 10px;
}

</style>

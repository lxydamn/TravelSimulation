<template>
	<div class="sidebar">
		<el-menu
			class="sidebar-el-menu"
			:default-active="onRoutes"
			background-color="#324157"
			text-color="#bfcbd9"
			active-text-color="#20a0ff"
			unique-opened
			router
		>
			<template v-for="item in items">
				<template v-if="item.subs">
					<el-sub-menu :index="item.index" :key="item.index">
						<template #title>
							<i :class="item.icon" style="margin-right: 8px;"></i>
							<span> {{item.title}} </span>
						</template>
						<template v-for="subItem in item.subs">
							<el-menu-item :index="subItem.index">
								<i :class="subItem.icon"></i>
								{{ subItem.title }}
							</el-menu-item>
						</template>
					</el-sub-menu>
				</template>
				<template v-else>
					<el-menu-item :index="item.index" :key="item.index">
						<template #title>
							<i :class="item.icon"></i>
							<span> {{item.title}} </span>
						</template>
					</el-menu-item>
				</template>
			</template>
		</el-menu>
	</div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useSidebarStore } from '../store/sidebar';
import { useRoute } from 'vue-router';

const items = [
	{
		icon: 'el-icon-home',
		index: '/dashboard',
		title: '系统首页',
	},
	{
		icon: 'el-icon-list',
		index: '/table',
		title: '疫情风险地管理',
	},
	{
		icon: 'el-icon-list',
		index: '/userslist',
		title: '用户列表',
	},
	{
		icon: 'el-icon-travel',
		index: 'travel',
		title: '交通路线',
		subs: [
			{
				icon: 'el-icon-train',
				index: '/train',
				title: '火车路线',
			},
			{
				icon: 'el-icon-plane',
				index: '/plane',
				title: '飞机航线',
			},
			{
				icon: 'el-icon-car',
				index: '/car',
				title: '汽车路线',
			}
		]
	},
];

const route = useRoute();
const onRoutes = computed(() => {
	return route.path;
});

const sidebar = useSidebarStore();
</script>

<style scoped>
.el-icon-plane{
    background-image: url('../assets/img/plane.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}
.el-icon-car{
    background-image: url('../assets/img/car.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}
.el-icon-travel{
    background-image: url('../assets/img/travel.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}
.el-icon-train{
    background-image: url('../assets/img/train.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}
.el-icon-home{
    background-image: url('../assets/img/home.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}
.el-icon-list{
	background-image: url('../assets/img/list.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}

.el-icon-char{
	background-image: url('../assets/img/char.png');
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
}


.sidebar {
	display: block;
	position: absolute;
	left: 0;
	top: 70px;
	bottom: 0;
	overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
	width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
	width: 250px;
}
.sidebar > ul {
	height: 100%;
}
</style>

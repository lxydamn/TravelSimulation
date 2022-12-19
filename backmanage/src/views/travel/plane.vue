<template>
  <div>
    <div class="container">
      <el-button
          class="m-2"
          @click="addVisible = !addVisible"
          style="float: right;margin-bottom: 10px"
      >添加路线</el-button>

      <div class="table">
        <el-table :data="tableData" height="420" style="width: 100%">
          <el-table-column prop="id" label="ID" />
          <el-table-column prop="startCity" label="出发城市" />
          <el-table-column prop="endCity" label="到达城市" />
          <el-table-column prop="startTime" label="出发时间"/>
          <el-table-column prop="endTime" label="到达时间"/>
          <el-table-column prop="cost" label="花费(元)"/>
          <el-table-column label="操作" width="220" align="center">
            <template #default="scope">
              <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)">
                修改
              </el-button>
              <el-button text :icon="Delete" @click="handleDelete(scope.$index, scope.row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts" name="data">
import {Edit, Delete} from '@element-plus/icons-vue';
import {ref} from "vue";
import { useDataStore } from '../../store/data';
import axios from "axios";
import AdcodeToCity from "../../utils/querycity";
const data = useDataStore();

interface TableItem {
  id:number,
  startCity:string,
  endCity:string,
  startTime:Date,
  endTime:Date,
  cost:number,
}
let tableData = ref<TableItem[]>([]);
let addVisible = ref(false);

const handleEdit = (index: number, row: any) => {

}

const handleDelete = (index: number, row: any) => {

}

const init = () => {
  // 请求路线
  axios({
    url:"http://localhost:3000/api/back/getpaths/",
    method:"GET",
    headers: {
      Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
    }
  }).then((resp) => {
    tableData.value = resp.data.plane;

    for(let i of tableData.value) {
      i.startCity = AdcodeToCity.get(i.startCity);
      i.endCity = AdcodeToCity.get(i.endCity);
    }

  })


}
init();
</script>

<style scoped>

</style>
<template>
  <div>
    <div class="container">
      <div class="table">
        <el-table :data="tableData" height="420" style="width: 100%">
          <el-table-column prop="id" label="ID" />
          <el-table-column prop="username" label="用户名称" />
        </el-table>
      </div>

      <div class="pagination">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="query.pageIndex"
            :total="query.pageTotal"
            @current-change="refreshTable"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="userlist">
import {reactive, ref} from "vue";
import axios from "axios";

let query = reactive({
  pageIndex:1,
  pageSize:1,
  pageTotal:0,
})

let searchName = ref('');
let tableData = ref([]);
let users = ref([]);

const init = () => {
  axios({
      url:"http://localhost:3000/api/back/users/",
      method:"GET",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("jwt_token"),
      },
    }).then((res) => {
      users.value = res.data;
      query.pageTotal = users.value.length;
      refreshTable();
    })
}
init();


const refreshTable = () => {
  let val = (query.pageIndex - 1) * 10;
  tableData.value = users.value.slice(val, val + 10);
} 




</script>

<style scoped>

</style>

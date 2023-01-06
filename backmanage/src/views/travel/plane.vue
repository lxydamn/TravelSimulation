<template>
  <div>
    <div class="container">
      <el-button
          class="m-2"
          @click="addVisible = !addVisible"
          style="float: right;margin-bottom: 10px"
      >添加路线</el-button>

      <div class="table">
        <el-table :data="tableData" height="620" style="width: 100%">
          <el-table-column prop="id" label="ID" />
          <el-table-column prop="startCity" label="出发城市" />
          <el-table-column prop="endCity" label="到达城市" />
          <el-table-column prop="startTime" sortable label="出发时间"/>
          <el-table-column prop="endTime" sortable label="到达时间"/>
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

      <!-- 添加表单-->
      <el-dialog v-model="addVisible" title="添加路线" width="600">
        <el-form >
          <el-row :gutter="30">
            <el-col :span="12">
              <elui-china-area-dht v-model="add_path.startCity" :leave="2" placeholder="出发城市"></elui-china-area-dht>
            </el-col>
            <el-col :span="12">
              <elui-china-area-dht v-model="add_path.endCity" :leave="2" placeholder="到达城市"></elui-china-area-dht>
            </el-col>
          </el-row>

          <el-row :gutter="30" style="margin-top: 20px;margin-bottom: 20px">
            <el-col :span="12">
              <el-date-picker
                  v-model="add_path.startTime"
                  type="datetime"
                  placeholder="出发时间"
                  format="YYYY/MM/DD HH:mm"
                  value-format="x"
                  width="120"
              />
            </el-col>
            <el-col :span="12">
              <el-date-picker
                  v-model="add_path.endTime"
                  type="datetime"
                  placeholder="到达时间"
                  format="YYYY/MM/DD HH:mm"
                  value-format="x"
              />
            </el-col>
          </el-row>
          价格：<el-input-number v-model="add_path.cost" :min="0" :max="5000"/>

        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="addPath">
          确认添加
        </el-button>
      </span>
        </template>
      </el-dialog>


      <!-- 修改表单-->
      <el-dialog v-model="modifyVisible" title="修改路线" width="600">
        <el-form >
          <el-row :gutter="30">
            <el-col :span="12">
              <elui-china-area-dht v-model="add_path.startCity" :leave="2" placeholder="出发城市"></elui-china-area-dht>
            </el-col>
            <el-col :span="12">
              <elui-china-area-dht v-model="add_path.endCity" :leave="2" placeholder="到达城市"></elui-china-area-dht>
            </el-col>
          </el-row>

          <el-row :gutter="30" style="margin-top: 20px;margin-bottom: 20px">
            <el-col :span="12">
              <el-date-picker
                  v-model="add_path.startTime"
                  type="datetime"
                  placeholder="出发时间"
                  format="YYYY/MM/DD HH:mm"
                  value-format="x"
              />
            </el-col>
            <el-col :span="12">
              <el-date-picker
                  v-model="add_path.endTime "
                  type="datetime"
                  placeholder="到达时间"
                  format="YYYY/MM/DD HH:mm"
                  value-format="x"
              />
            </el-col>
          </el-row>
          价格：<el-input-number v-model="add_path.cost" :min="0" :max="5000"/>

        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="modifyVisible = false">取消</el-button>
        <el-button type="primary" @click="modifyPath">
          确认修改
        </el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts" name="data">
import {Edit, Delete} from '@element-plus/icons-vue';
import {reactive, ref} from "vue";
import { useDataStore } from '../../store/data';
import { ElMessage,ElMessageBox} from 'element-plus';
import axios from "axios";
import AdcodeToCity from "../../utils/querycity";
import {EluiChinaAreaDht} from "elui-china-area-dht";

const data = useDataStore();

interface TableItem {
  id: number,
  startCity: string,
  endCity: string,
  startTime: Date,
  endTime: Date,
  cost: number,
}

let tableData = ref<TableItem[]>([]);
let addVisible = ref(false);
let modifyVisible = ref(false);
let add_path = reactive({
  id:"",
  startCity:"",
  endCity:"",
  startTime:"",
  endTime:"",
  cost:0,
})

const checkParam = () => {

  if(add_path.endTime == "" || add_path.startTime == ""
      || add_path.cost == 0 || add_path.startCity == "" || add_path.endCity == "") {

    ElMessage({
      showClose:true,
      message:"参数不能为空!",
      type:"error",
    })

    return false;
  }

  if(add_path.startCity == add_path.endCity) {
    ElMessage({
      showClose:true,
      message:"出发到达地不合法!",
      type:"error",
    })

    return false;
  }

  if(parseInt(add_path.startTime) <= Date.now()
      || add_path.startTime >= add_path.endTime) {

    ElMessage({
      showClose:true,
      message:"时间不合法!",
      type:"error",
    })

    return false;
  }

  return true;
}

const addPath = () => {

  if(! checkParam()) return;
  axios({
    url:"http://localhost:3000/api/back/addpath/",
    method:"POST",
    headers:{
      Authorization:"Bearer " + sessionStorage.getItem("jwt_token"),
    },
    params:{
      startCity:add_path.startCity[add_path.startCity.length - 1],
      endCity:add_path.endCity[add_path.endCity.length - 1],
      startTime:add_path.startTime,
      endTime:add_path.endTime,
      cost:add_path.cost,
      type:3,
    }
  }).then((resp) => {
    if(resp.data.error_message === "success") {
      ElMessage({
        showClose:true,
        type:"success",
        message:"添加成功",
      })

      addVisible.value = false;
      freshTable();
    } else {
      ElMessage({
        showClose:true,
        type:"error",
        message:"添加失败",
      })
    }
  }).catch((reason) => {
    ElMessage({
      showClose:true,
      type:"error",
      message:reason.message,
    })
  })



}

const modifyPath = () => {

  add_path.startTime = new Date(add_path.startTime).valueOf() + "";
  add_path.endTime = new Date(add_path.endTime).valueOf() + ""

  if(add_path.startCity.length == 2) {
    add_path.startCity = add_path.startCity[add_path.startCity.length - 1];
  }

  if(add_path.endCity.length == 2) {
    add_path.endCity = add_path.endCity[add_path.endCity.length - 1];
  }


  if(! checkParam()) return;

  axios({
    url:"http://localhost:3000/api/back/modifypath/",
    method:"POST",
    headers:{
      Authorization:"Bearer " + sessionStorage.getItem("jwt_token"),
    },
    params:{
      id:add_path.id,
      startCity:add_path.startCity,
      endCity:add_path.endCity,
      startTime: add_path.startTime,
      endTime:add_path.endTime,
      cost:add_path.cost,
      type:3,
    }
  }).then((resp) => {
    if(resp.data.error_message === "success") {
      ElMessage({
        showClose:true,
        type:"success",
        message:"修改成功",
      })

      modifyVisible.value = false;
      freshTable();
    } else {
      ElMessage({
        showClose:true,
        type:"error",
        message:"修改时出现错误",
      })
    }

  }).catch((resp) => {
    ElMessage({
      showClose:true,
      type:"error",
      message:"出现未知错误",
    })
  })
}

const handleEdit = (index: number, row: any) => {
  modifyVisible.value = true;
  let sc = "", ec = "";
  sc = AdcodeToCity.get(row.startCity) + "";
  ec = AdcodeToCity.get(row.endCity) + "";
  add_path.id = row.id;
  add_path.startCity = sc;
  add_path.endCity = ec;
  add_path.startTime = row.startTime;
  add_path.endTime =  row.endTime;
  add_path.cost = row.cost;

}

const handleDelete = (index: number, row: any) => {

  ElMessageBox.confirm(
      '你确认要删除吗？',
      'warning',
      {
        confirmButtonText:'确认',
        cancelButtonText:'取消',
        type:'warning',
      }
  ).then(() => {

    axios({
      url:"http://localhost:3000/api/back/deletepath/",
      method:"POST",
      headers:{
        Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
      },
      params:{
        id:row.id,
      }
    }).then((resp) => {
      console.log(resp.data);
      if(resp.data.error_message == "success") {
        ElMessage({
          showClose:true,
          message:"删除成功",
          type:"success",
        })

        freshTable();
      } else {
        ElMessage({
          showClose:true,
          message:resp.data.error_message,
          type:"error",
        })
      }
    })


  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })


}

const freshTable = () => {

  // 请求路线
  axios({
    url:"http://localhost:3000/api/back/getpaths/",
    method:"GET",
    headers: {
      Authorization: "Bearer " + sessionStorage.getItem("jwt_token"),
    }
  }).then((resp) => {

    tableData.value = resp.data.plane;
    data.plane = resp.data.plane;

    for(let i of tableData.value) {
      i.startCity = AdcodeToCity.get(i.startCity);
      i.endCity = AdcodeToCity.get(i.endCity);
    }

    add_path.startCity="";
    add_path.endCity="";
    add_path.startTime="";
    add_path.endTime="";
    add_path.cost=0;
  })



}
freshTable();

</script>

<style scoped>

</style>
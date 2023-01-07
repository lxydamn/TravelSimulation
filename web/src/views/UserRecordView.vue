<template>
  <el-container>
    <el-main>
      <el-card class="box-card">
        <div id="container">
          <div>
            <el-row gutter="20">
              <el-col :span="6" :offset="1">
                <el-card class="user" shadow="hover">

                  <template #header>
                    <div class="clearfix" style="text-align:center">
                      <span>用户信息</span>
                    </div>
                  </template>

                  <div style="text-align: center;">
                    <el-avatar :size="100" :src=$store.state.user.photo />
                    <div class="user_namer" style="font-size:40px">
                      {{ $store.state.user.username }}
                    </div>
                  </div>


                </el-card>
              </el-col>

              <el-col :span="15" :offset="1">
                <el-card class="record" shadow="hover">
                  <template #header>
                    <div class="clearfix">
                      <span>历史记录</span>
                    </div>
                  </template>

                  <el-table :data="tableData" style="width: 100%" max-height="500">
                    <el-table-column type="expand">
                      <template #default="scope">
                        <p>
                          &emsp;&emsp;出发时间： {{ scope.row.content.recordData.startTime }}
                        </p>
                        <p>
                          &emsp;&emsp;到达时间： {{ scope.row.content.recordData.endTime }}
                        </p>
                        <p>
                          &emsp;&emsp;风险系数：{{ scope.row.content.risk }}&emsp;&emsp;费用开销：{{ scope.row.content.cost }}
                        </p>

                        <p v-for="item, index in (scope.row.content.recordData.paths)">
                          ====================================
                          <br />
                          &emsp;&emsp;路径{{ index }}:
                          <br />
                          &emsp;&emsp;{{ item.startCity }} -> {{ item.endCity }}
                          <br />
                          &emsp;&emsp;交通方式：{{ item.type }}&emsp;&emsp;费用：{{ item.cost }}
                          <br />
                          &emsp;&emsp;出发时间：{{ item.startTime }}
                          <br />
                          &emsp;&emsp;到达时间：{{ item.endTime }}
                        </p>
                      </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="日期" width="150" />
                    <el-table-column prop="startCity" label="出发地" width="110" />
                    <el-table-column prop="endCity" label="目的地" width="110" />
                    <el-table-column prop="strategy" label="出行策略" width="100" />
                    <el-table-column prop="transit" label="中转次数" width="80" />
                    <el-table-column prop="risk" label="风险系数" width="80" />
                    <el-table-column prop="cost" label="预计费用" width="80" />
                    <el-table-column fixed="right" label="操作" width="60">
                      <template #default="scope">
                        <el-button link type="primary" size="default" @click="deleteRecord(scope.$index, scope.row)">
                          删除
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>

                </el-card>
              </el-col>
            </el-row>

          </div>
        </div>

      </el-card>
    </el-main>

  </el-container>

</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue'
import axios from "axios"
import Operate from "@/views/HomeView.vue";
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
  setup() {
    const store = useStore();

    const tableData = ref([])

    const init = () => {
      axios({
        url: "http://localhost:3000/api/back/getrecord/",
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwt_token"),
        },
        params: {
          userId: (store.state.user.id),
        }
      }).then((res) => {
        const type = ["汽车", "火车", "飞机"]
        const strategy = ["耗时最短", "风险最小", "花费最少"]
        for (let i of res.data) {
          i.startCity = Operate.methods.cityName(i.startCity)
          i.endCity = Operate.methods.cityName(i.endCity)
          i.strategy = strategy[i.strategy - 1]

          let t1 = JSON.parse(i.content)
          let t2 = JSON.parse(t1.recordData)
          t2.startTime = Operate.methods.dateFormat((t2.startTime).toString())
          t2.endTime = Operate.methods.dateFormat((t2.endTime).toString())
          for (let j of t2.paths) {
            j.startCity = Operate.methods.cityName(j.startCity)
            j.endCity = Operate.methods.cityName(j.endCity)
            j.type = type[j.type - 1]
          }
          t1.recordData = t2
          i.content = t1
        }
        tableData.value = res.data
      })
    }
    init();

    const deleteRecord = (index, row) => {
      ElMessageBox.confirm(
        '你确认要删除吗？',
        'warning',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        tableData.value.splice(index, 1)
        axios({
          url: "http://localhost:3000/api/back/deleterecord/",
          method: "POST",
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt_token"),
          },
          params: {
            id: row.id,
          }
        }).then((resp) => {
          if (resp.data.error_message == "success") {
            ElMessage({
              showClose: true,
              message: "删除成功",
              type: "success",
            })
          } else {
            ElMessage({
              showClose: true,
              message: resp.data.error_message,
              type: "error",
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

    return {
      store,
      tableData,
      deleteRecord,
    }
  }
}

</script>

<style>
#container {
  height: 650px;
  background: beige;
}

.user {
  margin-top: 25px;
  padding: 10px
}

.record {
  margin-top: 25px;
  padding: 10px;
  height: 600px;
}
</style>
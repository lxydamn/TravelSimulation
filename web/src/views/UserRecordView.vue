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

                  <el-table :data="tableData" style="width: 100%" max-height="400">
                    <el-table-column fixed prop="date" label="日期" width="120" />
                    <el-table-column prop="name" label="出发地" width="120" />
                    <el-table-column prop="state" label="目的地" width="120" />
                    <el-table-column prop="city" label="出行策略" width="120" />
                    <el-table-column prop="address" label="中转次数" width="120" />
                    <el-table-column prop="zip" label="风险系数" width="120" />
                    <el-table-column prop="zip" label="预计费用" width="120" />
                    <el-table-column fixed="right" label="操作" width="120">
                      <template #default="scope">
                        <el-button link type="primary" size="default" @click.prevent="deleteRow(scope.$index)">
                          详情
                        </el-button>
                        <el-button link type="primary" size="default" @click.prevent="deleteRow(scope.$index)">
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

export default {
  setup() {
    const store = useStore();

    const now = new Date()

    const tableData = ref([
      {
        date: '2016-05-01',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: '1',
        zip: 'CA 90036',
      },
      {
        date: '2016-05-02',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: '1',
        zip: 'CA 90036',
      },
      {
        date: '2016-05-03',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: '1',
        zip: 'CA 90036',
      },
    ])

    const deleteRow = (index) => {
      tableData.value.splice(index, 1)
    }

    return {
      store,
      now,
      tableData,
      deleteRow,
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
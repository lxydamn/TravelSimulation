<template>
  <el-container>
    <el-main>
      <el-card class="box-card">
        <div id="container">
          <div>
            <el-row gutter="20">
              <el-col :span="8" :offset="1">
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

              <el-col :span="13" :offset="1">
                <el-card class="record" shadow="hover" >
                  <template #header>
                    <div class="clearfix">
                      <span>历史记录</span>
                    </div>
                  </template>

                  <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="Date" width="180" />
                    <el-table-column prop="name" label="Name" width="180" />
                    <el-table-column prop="address" label="Address" />
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

export default {
  setup() {
    const store = useStore();
    console.log(store.state.user.photo)

    const tableData = [
      {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-04',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-01',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
      },
    ]
    return {
      store,
      tableData
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
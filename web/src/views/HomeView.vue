<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <map-container>
          </map-container>
        </el-col>

        <el-col :span="8">
          <el-card class="box-card" style="margin-left: 15px">
            <el-row>
              <el-col :span="10">
                <div class="box">
                  <div>出发地</div>
                  <el-select v-model="valueStart" placeholder="选择出发地" style="margin-top: 15px">
                    <el-option v-for="item in optionsCity" :key="item.value" :label="item.label" :value="item.value" />
                  </el-select>
                </div>
              </el-col>
              <el-col :span="4">
                <div class="box">
                  <svg xmlns="http://www.w3.org/2000/svg" style="margin-top: 40px; margin-left: 20px;" width="25"
                    height="30" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                      d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z" />
                  </svg>
                </div>
              </el-col>
              <el-col :span="10">
                <div class="box">
                  <div>目的地</div>
                  <el-select v-model="valueEnd" placeholder="选择目的地" style="margin-top: 15px">
                    <el-option v-for="item in optionsCity" :key="item.value" :label="item.label" :value="item.value" />
                  </el-select>
                </div>
              </el-col>
            </el-row>

            <div class="box">
              <el-row style="margin-top: 10px;">
                <div style="margin-top: 5px;">交通工具：</div>
                <div>
                  <el-checkbox v-model="car" label="汽车" size="large" style="margin-left: 32px;" />
                  <el-checkbox v-model="train" label="火车" size="large" />
                  <el-checkbox v-model="plane" label="飞机" size="large" />
                </div>
              </el-row>
            </div>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>出发日期: </div>
              </el-col>
              <el-col :span="18">
                <el-date-picker v-model="valueTime" type="date" placeholder="选择出发日期" format="YYYY/MM/DD"
                  value-format="x" :size="size" style="width: 280px" />
              </el-col>
            </el-row>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>出行策略: </div>
              </el-col>
              <el-col :span="18">
                <el-select v-model="valueStrategy" placeholder="选择出行策略" style="width: 280px">
                  <el-option v-for="item in optionsStrategy" :key="item.value" :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-col>
            </el-row>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>中转城市: </div>
              </el-col>
              <el-col :span="18">
                <el-select-v2 v-model="valuezCity" :options="optionsCity" placeholder="选择中转的城市" style="width: 280px"
                  multiple />
              </el-col>
            </el-row>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>中转次数: </div>
              </el-col>
              <el-col :span="18">
                <el-input-number v-model="num" :min="0" :max="5" />
              </el-col>
            </el-row>

            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button @click="caul">规划路径</el-button>
            </el-row>


          </el-card>



          <el-card class="box-card" style="margin-top: 20px; margin-left: 15px;">
            <el-card class="box-card" :body-style="{ padding: '0px' }">
              <div class="card-header" style="text-align:center">
                <span>路径规划结果</span>
              </div>
              <el-scrollbar max-height="200px">
                <p v-for="item in count" :key="item" class="scrollbar-demo-item" style="text-align:center">
                  {{ item }}
                </p>
              </el-scrollbar>
            </el-card>
            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button @click="mock">模拟</el-button>
              <el-button>保存</el-button>
            </el-row>
          </el-card>
        </el-col>

      </el-row>
    </el-main>
  </el-container>

</template>
<script>
import MapContainer from "@/components/MapContainer";
import { ref } from 'vue';
import axios from "axios"

export default {
  components: {
    MapContainer,
  },
  setup() {
    let valueStart = ref('')
    let valueEnd = ref('')
    const optionsCity = ref([])

    const freshSelect = () => {
      axios({
        url: "http://localhost:3000/api/back/getcities/",
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwt_token"),
        },
      }).then((resp) => {
        let cities = resp.data;
        for (let city of cities) {
          optionsCity.value.push({ 'value': city.cityAdcode, 'label': city.cityName });
        }
      })
    }
    freshSelect();

    const car = ref(true)
    const train = ref(true)
    const plane = ref(true)

    const mock = () => {
      MapContainer.methods.drawpath();
    }

    let valueStrategy = ref('')
    const optionsStrategy = [
      {
        value: '1',
        label: '耗时最少',
      },
      {
        value: '2',
        label: '风险最小',
      },
      {
        value: '3',
        label: '花费最少',
      },
      {
        value: '4',
        label: '城市中转',
      },
    ]

    const size = ref('default')
    let valueTime = ref('')

    let valuezCity = ref([])

    const caul = () => {
      console.log(valueTime);
      console.log(num);
      MapContainer.methods.drawpath();
    }

    const count = ref(50)

    let num = ref('')

    return {
      valueStart,
      valueEnd,
      optionsCity,
      car,
      train,
      plane,
      valueStrategy,
      optionsStrategy,
      size,
      valueTime,
      caul,
      mock,
      count,
      valuezCity,
      num,
    }
  }
}
</script>

<style scoped>

</style>
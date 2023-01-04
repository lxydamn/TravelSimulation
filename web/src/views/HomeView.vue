<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <map-container>
          </map-container>
        </el-col>

        <el-col :span="8">
          <el-card class="box-card" style="margin-left: 15px; height: 382px;">
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
                <el-input-number v-model="transit" :min="-1" :max="5" />
              </el-col>
            </el-row>

            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button @click="caul(), showinfo()">规划路径</el-button>
            </el-row>


          </el-card>

          <el-card class="box-card" style="margin-top: 20px; margin-left: 15px;">
            <el-card class="box-card" :body-style="{ padding: '0px' }" style="height: 245px;">
              <div class="card-header" style="text-align:center">
                <span>路径规划结果</span>
              </div>
              <div v-show="isShow" style="text-align:left">
                <el-scrollbar max-height="200px">
                  <p>
                    &emsp;&emsp;出发时间： {{ dateFormat(datastartTime) }}
                  </p>
                  <p>
                    &emsp;&emsp;到达时间： {{ dateFormat(dataendTime) }}
                  </p>
                  <p>
                    &emsp;&emsp;风险系数：{{ datarisk }}&emsp;&emsp;费用开销：{{ datacost }}
                  </p>

                  <p v-for="item,index in datapaths">
                    ====================================
                    <br/>
                    &emsp;&emsp;路径{{ index }}:
                    <br/>
                    &emsp;&emsp;{{ item.startCity }} -> {{ item.endCity }}
                    <br/>
                    &emsp;&emsp;交通方式：{{ item.type }}&emsp;&emsp;费用：{{ item.cost }}
                    <br/>
                    &emsp;&emsp;出发时间：{{ dateFormat(item.startTime) }}
                    <br/>
                    &emsp;&emsp;到达时间：{{ dateFormat(item.endTime) }}
                  </p>

                </el-scrollbar>
              </div>
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
    let optionsCity = ref([])

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

    let car = ref(true)
    let train = ref(true)
    let plane = ref(true)

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


    let datastartTime = ref('')
    let dataendTime = ref('')
    let datacost = ref('')
    let datarisk = ref('')
    let datapaths = ref([])

    const caul = () => {
      axios({
        url: "http://localhost:3000/api/algorithm/dfs/",
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwt_token"),
        },
        params: {
          startTime: valueTime.value,
          startCity: valueStart.value,
          endCity: valueEnd.value,
          car: car.value,
          train: train.value,
          plane: plane.value,
          valueStrategy: valueStrategy.value,
          valuezCity: JSON.stringify(valuezCity),
          transit: transit.value
        }
      }).then((resp) => {
        if (resp.data.cost === undefined) {
          console.log(123)
        }
        else {
          console.log(resp.data)
          datastartTime.value = resp.data.startTime
          dataendTime.value = resp.data.endTime
          datacost.value = resp.data.cost
          datarisk.value = resp.data.risk
          datapaths.value = resp.data.paths
          console.log(datapaths)
        }
      })
    }

    var isShow = ref(false)
    const showinfo = () => {
      isShow.value = true
    }

    let transit = ref('')

    const mock = () => {
      MapContainer.methods.drawpath();
    }

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
      valuezCity,
      transit,
      isShow,
      showinfo,

      datastartTime,
      dataendTime,
      datacost,
      datarisk,
      datapaths,
    }
  },
  methods: {
    dateFormat: function (time) {
      var date = new Date(time);
      var year = date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
    cityName: async function(adcode)
    {
      var res = '';
      var key = "037a4bfd97ca4448be54d663aefaf845";
      var urlt = "https://geoapi.qweather.com/v2/city/lookup?location="+ adcode + "&key=" + key;
      await axios({
        url: urlt,
        method: "GET",
      }).then((resp) => {
        res = resp.data.location[0].name   
      })
      console.log(res)
      return res;
    },
  }
}
</script>

<style scoped>

</style>
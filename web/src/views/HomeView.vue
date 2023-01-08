<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <map-container>
          </map-container>
        </el-col>

        <el-col :span="8">
          <el-card class="box-card" style="margin-left: 15px; height: 350px;">
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

            <el-row style="margin-top: 15px;">
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

            <el-row style="margin-top: 15px;">
              <el-col :span="6">
                <div>中转次数: </div>
              </el-col>
              <el-col :span="18">
                <el-input-number v-model="transit" :min="-1" :max="5" />
              </el-col>
            </el-row>

            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button @click="caul()">规划路径</el-button>
            </el-row>


          </el-card>

          <el-card class="box-card" style="margin-top: 20px; margin-left: 15px;">
            <el-card class="box-card" :body-style="{ padding: '0px' }" style="height: 277px;">
              <div class="card-header" style="text-align:center">
                <span>路径规划结果</span>
              </div>
              <div v-if="isEmpty" style="text-align:center">
                <p>
                  暂无信息
                </p>
              </div>
              <div v-else style="text-align:left">

                <el-scrollbar max-height="230px">
                  <p>
                    &emsp;&emsp;出发时间： {{ dateFormat(datastartTime) }}
                  </p>
                  <p>
                    &emsp;&emsp;到达时间： {{ dateFormat(dataendTime) }}
                  </p>
                  <p>
                    &emsp;&emsp;风险系数：{{ datarisk }}&emsp;&emsp;费用开销：{{ datacost }}
                  </p>

                  <p v-for="item, index in datapaths">
                    ====================================
                    <br />
                    &emsp;&emsp;路径{{ index }}:
                    <br />
                    &emsp;&emsp;{{ cityName(item.startCity) }} -> {{ cityName(item.endCity) }}
                    <br />
                    &emsp;&emsp;交通方式：{{ typeToTransportation(item.type) }}&emsp;&emsp;费用：{{ item.cost }}
                    <br />
                    &emsp;&emsp;出发时间：{{ dateFormat(item.startTime) }}
                    <br />
                    &emsp;&emsp;到达时间：{{ dateFormat(item.endTime) }}
                  </p>

                </el-scrollbar>
              </div>
            </el-card>
            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button @click="mock">模拟</el-button>
              <el-button @click="save">保存</el-button>
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
import AdcodeToCity from "@/utils/queryCity";
import { useStore } from 'vuex';
import { ElMessage, ElMessageBox } from 'element-plus';

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
    ]

    const size = ref('default')
    let valueTime = ref('')


    let datastartTime = ref('')
    let dataendTime = ref('')
    let datacost = ref('')
    let datarisk = ref('')
    let datapaths = ref([])
    let dataStrategy = ref('')
    let datatransit = ref('')
    let data = ref()
    var isEmpty = ref(true)

    const caul = () => {
      if (valueStart.value == '') {
        ElMessage({
          showClose: true,
          message: "请选择出发地",
          type: "error",
        })
      }
      else if (valueEnd.value == '') {
        ElMessage({
          showClose: true,
          message: "请选择目的地",
          type: "error",
        })
      }
      else if (car.value == '' && train.value == '' && plane.value == '') {
        ElMessage({
          showClose: true,
          message: "至少选择一种交通出行方式",
          type: "error",
        })
      }
      else if (valueTime.value == '') {
        ElMessage({
          showClose: true,
          message: "请选择预计出发时间",
          type: "error",
        })
      }
      else if (valueStrategy.value == '') {
        ElMessage({
          showClose: true,
          message: "请选择出行策略",
          type: "error",
        })
      }
      else if (valueStart.value == valueEnd.value) {
        ElMessage({
          showClose: true,
          message: "出发地和起始地不能相同",
          type: "error",
        })
      }
      else {
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
            transit: transit.value
          }
        }).then((resp) => {
          if (resp.data.cost === undefined) {
            ElMessage({
              showClose: true,
              message: "暂无可出行路线",
              type: "info",
            })
            isEmpty.value = true
          }
          else {
            ElMessage({
              showClose: true,
              message: "规划成功",
              type: "success",
            })
            data = resp.data

            isEmpty.value = false
            datastartTime.value = resp.data.startTime
            dataendTime.value = resp.data.endTime
            datacost.value = resp.data.cost
            datarisk.value = resp.data.risk
            datapaths.value = resp.data.paths
            dataStrategy.value = resp.data.strategy
            datatransit.value = resp.data.transit
          }
        })
      }
    }

    let transit = ref('-1')

    const mock = () => {
      if (datapaths._rawValue.length == 0) {
        ElMessage({
          showClose: true,
          message: "暂无规划路线可模拟",
          type: "error",
        })
      }
      else {
        MapContainer.methods.drawpath(datapaths._rawValue);
        ElMessage({
          showClose: true,
          message: "模拟成功",
          type: "success",
        })
      }
    }

    const store = useStore();
    const save = () => {
      ElMessageBox.confirm(
        '是否保存？',
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        if (datapaths._rawValue.length == 0) {
          ElMessage({
            showClose: true,
            message: "暂无规划路线可保存",
            type: "error",
          })
        }
        else {
          axios({
            url: "http://localhost:3000/api/back/saverecord/",
            method: "POST",
            headers: {
              Authorization: "Bearer " + localStorage.getItem("jwt_token"),
            },
            params: {
              recordData: JSON.stringify(data),
              userId: (store.state.user.id),
              startCity: (datapaths.value[0].startCity),
              endCity: (datapaths.value[datapaths.value.length - 1].endCity),
              length: (datapaths.value.length - 1),
              strategy: dataStrategy.value,
              transit: datatransit.value,
              cost: datacost.value,
              risk: datarisk.value
            }
          }).then((resp) => {
            if (resp.data.error_message == "success") {
              ElMessage({
                showClose: true,
                message: "保存成功",
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
        }

      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消保存',
        })
      })
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
      save,
      isEmpty,
      transit,
      datastartTime,
      dataendTime,
      datacost,
      datarisk,
      datapaths,
      dataStrategy,
      datatransit,
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
    cityName: function (adcode) {
      return AdcodeToCity.get(adcode.toString());
    },
    typeToTransportation: function (type) {
      if (type == "1") return "汽车";
      else if (type == "2") return "火车";
      else return "飞机";
    },
  }
}
</script>

<style scoped>

</style>
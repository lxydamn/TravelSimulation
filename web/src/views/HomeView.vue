<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col">
        <div class="card">
          <map-container></map-container>
        </div>
      </div>

      <div class="col-4">
        <div class="card">
          <div class="card-body">
            <div class="row">
              <div class="col-5">
                <div class="box">
                  <label for="inputState" class="form-label">出发地</label>
                  <select id="inputState" class="form-select">
                    <option selected>Choose...</option>
                    <option>...</option>
                  </select>
                </div>
              </div>
              <div class="col-2">
                <div class="box">
                  <svg xmlns="http://www.w3.org/2000/svg" style="margin-top: 35px; margin-left: 10px;" width="30"
                    height="30" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                      d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z" />
                  </svg>
                </div>
              </div>
              <div class="col-5">
                <div class="box">
                  <label for="inputState" class="form-label">目的地</label>
                  <select id="inputState" class="form-select">
                    <option selected>Choose...</option>
                    <option>...</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="box">
              <div class="row" style="margin-top: 25px;">
                <div class="col">
                  <p>交通工具:</p>
                </div>
                <div class="col">
                  <input class="form-check-input" type="checkbox" value="" id="Check1">
                  <label class="form-check-label" for="Check1">
                    飞机
                  </label>
                </div>
                <div class="col">
                  <input class="form-check-input" type="checkbox" value="" id="flexCheckChecked">
                  <label class="form-check-label" for="flexCheckChecked">
                    火车
                  </label>
                </div>
                <div class="col">
                  <input class="form-check-input" type="checkbox" value="" id="flexCheckChecked">
                  <label class="form-check-label" for="flexCheckChecked">
                    汽车
                  </label>
                </div>
              </div>
            </div>

            <div class="row" style="margin-top: 5px;">
              <div class="col-4" style="margin-top: 5px;">
                <p>出发日期: </p>
              </div>
              <div class="col-8">
                <el-date-picker v-model="value" type="date" placeholder="请选择出发日期" ></el-date-picker>
              </div>
            </div>

            <div class="row" style="margin-top: 10px;">
              <div class="col-3" style="margin-top: 5px;">
                <label for="inputState" class="form-label">出行策略:</label>
              </div>
              <div class="col-9">
                <select id="inputState" class="form-select">
                  <option selected>Choose...</option>
                  <option>...</option>
                </select>
              </div>
            </div>

          </div>
        </div>

        <div class="card" style="margin-top: 60px;">
          <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
            <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
          </ul>
        </div>
      </div>

    </div>
  </div>

</template>
<script>
import MapContainer from "@/components/MapContainer";
import { ref } from "vue";


export default {
  components: {
    MapContainer,
  },
  setup() {
    let value = ref('');
    let count = ref(0)
    const load = () => {
      console.log("success");
      count.value += 2
    }
    return {
      value,
      count,
      load,
    }
  }
}
</script>

<style scoped>
.infinite-list {
  height: 300px;
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>
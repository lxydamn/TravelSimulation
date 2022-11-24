<template>
    <div class="container-fluid">
        <div class="row">
          <div class="col-9" style="margin-left: fill">
              <div class="card">
                <map-container></map-container>
              </div>
          </div>
          <div class="col-3">
            <div class="card">
              <div class="card-header">
                <span style="text-align: center">地图</span>
                <button type="button" class="btn btn-success float-end" style="scale: 90%" @click="add">新建</button>
              </div>
              <div class="card-body">
                <n-button-group>
                  <n-button ghost>
                    <template #icon>
                      <n-icon><log-in-icon /></n-icon>
                    </template>
                    再吃
                  </n-button>
                  <n-button ghost>
                    <template #icon>
                      <n-icon><log-in-icon /></n-icon>
                    </template>
                    一颗
                  </n-button>
                  <n-button round>
                    <template #icon>
                      <n-icon><log-in-icon /></n-icon>
                    </template>
                    苹果
                  </n-button>
                </n-button-group>
              </div>
            </div>
          </div>
        </div>
    </div>
    <div id="footer">CopyRight@李幸洋、卢裕中 Company@重庆交通大学信息学院</div>
</template>
<script>
import {NButton} from 'naive-ui';
import {NButtonGroup} from "naive-ui";
import MapContainer from "@/components/MapContainer";
export default {
  components:{
    MapContainer,
    NButton,
    NButtonGroup,
  },
  setup(){

    const add = () => {
      MapContainer.methods.drawLine();
    }
    return {
      add,
    }
  },
}
</script>

<style scope>
id.btn-success{
  align-content: center;
  margin-top: 20px  ;
}
</style>
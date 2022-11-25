<template>
    <div class="container-fluid">
        <div class="row">
          <div class="col-8">
              <div class="card">
                <map-container></map-container>
              </div>
          </div>
          <div class="col-4">
            <div class="card">
              <div class="card-header">
                <span style="text-align: center">地图</span>
                <button type="button" class="btn btn-success float-end" style="scale: 90%" @click="add">新建</button>
              </div>
            </div>
          </div>
        </div>
    </div>
</template>
<script>
import MapContainer from "@/components/MapContainer";
export default {
  components:{
    MapContainer,
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

<style>
</style>
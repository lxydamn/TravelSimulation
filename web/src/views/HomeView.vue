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
                  <label for="inputState" class="form-label">起始地</label>
                  <select id="inputState" class="form-select">
                    <option selected>Choose...</option>
                    <option>...</option>
                  </select>
                </div>
              </div>
              <div class="col-2">
                <div class="box">
                  <svg xmlns="http://www.w3.org/2000/svg" style="margin-top: 35px; margin-left: 10px;" width="30" height="30" fill="currentColor"
                    class="bi bi-arrow-right" viewBox="0 0 16 16">
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
          </div>
        </div>

        <div class="card" style="margin-top: 60px;">
          hh
        </div>

      </div>

    </div>
  </div>

</template>
<script>
import MapContainer from "@/components/MapContainer";

export default {
  components: {
    MapContainer,
  },
}
</script>

<style scoped>

</style>
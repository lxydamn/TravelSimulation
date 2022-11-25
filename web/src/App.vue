<template>
  <NavBar></NavBar>
  <div class="card" style="background-color: #EFEFEF">
    <router-view></router-view>
  </div>
  <div style="background-color: #212529">
    <FootBar></FootBar>
  </div>


</template>

<script>
import NavBar from './components/NavBar.vue'
import FootBar from '@/components/FootBar'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default {
   components: {
     NavBar,FootBar,
   },
}

</script>
<style>
body{
  background-size: 100%;
  background-color: #EFEFEF;
}

</style>

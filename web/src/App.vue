<template>
  <NavBar></NavBar>
  <div class="cardContainer">
    <router-view></router-view>
  </div>
  <FootBar></FootBar>

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

.cardContainer{
  height: 700px;
}
</style>

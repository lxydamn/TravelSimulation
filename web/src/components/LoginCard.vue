<template>
  <div class="container">
    <div class="card">
      <div class="card-header">
        <img src="@/assets/images/logo.png" alt="图片加载失败">
      </div>
      <div class="card-body">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>

</script>
<style scoped>
.container{
  width: 450px;
  margin-top: 60px;
  border-radius: 30px;
}
.card-header{
  border-radius: 30px 30px 0px 0px;
  text-align: center;
}
.card{
  border-radius: 30px;

}

</style>
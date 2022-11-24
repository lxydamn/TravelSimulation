<template>
    <div class="container-fluid bg-dark">
      <div class="row">
        <div class="col">
          <a class="myInfo">联系作者</a>
        </div>
        <div class="col">
          <router-link :to="{name:'introduce'}" style="text-decoration: none">
            <a class="myInfo"> 关于本站 </a>
          </router-link>
        </div>
        <div class="col">
          <a class="myInfo"> 后台管理</a>
        </div>
      </div>
    </div>
</template>
<script>

</script>

<style>

.myInfo{
  color: #FFFFFF;
  cursor: pointer;
  text-decoration: none;
}
.myInfo:hover{
  color:#B9BBBE;
  cursor: pointer;
  text-decoration: none;
}
</style>
<template>
  <el-container>
    <el-main>
      <el-card class="box-card">
        <div id="container">
          <div>
            <el-row gutter="20">
              <el-col :span="8" :offset="1">
                <el-card shadow="hover" body-style="{padding:'10px'}">
                  <template #header>
                    <div class="clearfix">
                      <span>基础信息</span>
                    </div>
                    <el-avatar :size="100" src="../assets/images/logo.png" />
                  </template>

                </el-card>
              </el-col>

              <el-col :span="14" :offset="1">
                <el-card shadow="hover" body-style="{padding:'10px'}">
                  <template #header>
                    <div class="clearfix">
                      <span>基础信息</span>
                    </div>
                  </template>

                </el-card>
              </el-col>
            </el-row>

          </div>
        </div>

      </el-card>
    </el-main>

  </el-container>

</template>
<script>

</script>

<style>
#container {
  height: 650px;
  background: beige;
}
</style>
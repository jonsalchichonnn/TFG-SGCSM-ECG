<template>
  <el-container style="height: 100%; border: 1px solid #eee;background-color: lavender">
    <el-aside
        width="auto"
        style="background-color: #3f5a9e; margin-left: -1px; margin-top: -1px"
    >
      <MyAside></MyAside>
    </el-aside>
    <el-container style="height: 100%">
      <el-header
          style="
          height: 100%;
          text-align: right;
          font-size: 12px;
          border-bottom: rgba(168, 168, 168, 0.3) 1px solid;
        "
      >
        <MyHeader></MyHeader>
      </el-header>

      <el-main style="height: 100%; background-color: lavender"> <!-- linen lavender #EFF2F3 -->
        <!--        <MyMain></MyMain>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-aside {
  color: #333;
}

.el-main {
  padding: 15px;
}

.el-header {
  /* background-color: #3f5a9e; */
  background-color: white;
  color: #333;
  line-height: 60px;
}
</style>

<script>
import MyAside from "./MyAside.vue";
import MyHeader from "./MyHeader.vue";
// import MyMain from "./MyMain.vue";

export default {
  name: "HomePage",
  components: {MyAside, MyHeader},
};
</script>

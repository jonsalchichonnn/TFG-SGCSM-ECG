<template>
  <!-- active-text-color= "#eff2f3"-->
  <!--  unique-opened-->
  <el-menu
      router
      default-active="/"
      background-color="#3f5a9e"
      text-color="#fff"
      active-text-color="#ffd04b"
      :collapse="isCollapse"
      :collapse-transition="false"
      style="height:100vh"
  >
    <el-menu-item index="/">
      <i class="el-icon-s-home" style="margin-right: 8px"></i>
      <span slot="title">Home</span>
    </el-menu-item>

    <el-menu-item :index="item.path" v-for="(item,i) in routes[1].children" :key="i" v-show="item.path !== '/welcome' && item.path !== '/profile'">
      <i :class="item.icon" style="margin-right: 8px"></i>
      <span slot="title">{{ item.name }}</span>
    </el-menu-item>
  </el-menu>
</template>

<style>

</style>

<script>
export default {
  name: "MyAside",
  computed: {
    isCollapse() {
      return this.$store.state.tab.isCollapse;
    },
    routes() {
      console.log("rutas menu = ", this.$store.state.routes.routes)
      return this.$store.state.routes.routes
    }

  },
};
</script>

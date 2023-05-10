<template>
  <div id="app">
    <!-- Router entry -->
    <router-view />
    <!-- <HomePage></HomePage> -->
  </div>
</template>

<script>
// import HomePage from "./components/HomePage.vue";

export default {
  name: "App",
  // components: {
  //   HomePage,
  // },
};
</script>

<style>
#app {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}
</style>

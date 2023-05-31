<template>
  <div style="display: flex">
    <!-- style="margin-bottom: 8px" -->
    <div style="margin-top: 8px">
      <i class="el-icon-s-fold" style="font-size: 20px" @click="handleMenu"></i>
    </div>
    <div style="flex: 1; text-align: center; font-size: 36px">
      SGCSM-ECG
    </div>
    <div>
      <span style="font-size: 14px">{{ curUser.name }}</span>
    </div>
    <el-dropdown>
      <i
          class="el-icon-caret-bottom"
          style="font-size: 20px; margin-right: 15px; margin-left: 5px"
      ></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toProfile">Profile</el-dropdown-item>
        <el-dropdown-item @click.native="logout">Log out</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<style scoped>
i {
  color: #333;
}

</style>

<script>

export default {
  name: "MyHeader",
  data() {
    return {
    }
  },
  methods: {
    toProfile() {
      this.$router.push("/profile");
    },
    logout() {
      console.log("logout!!!");

      this.$confirm('Current session will be closed', 'Do you want to log out?', {
        confirmButtonText: 'Log out',
        cancelButtonText: 'Cancel',
        type: 'warning',
        center: true // center text
      })
          .then(() => {
            // Clear user credentials
            sessionStorage.clear()
            // Clear menu data
            this.$store.commit('initRoutes', [])
            this.$router.replace("/login")
            this.$message.success("Logged out successfully!")
          })
          .catch(() => {
            this.$message.info("Log out cancelled!")
          })
    },
    handleMenu() {
      this.$store.commit("changeCollapse");
    },
  },
  computed:{
    curUser(){
      return this.$store.state.currentUser.user
    }
  }
};
</script>

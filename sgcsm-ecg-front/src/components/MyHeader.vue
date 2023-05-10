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

/* .el-icon-s-fold {
  font-size: 20px;
} */
/* .el-icon-caret-bottom {
  font-size: 14px;
} */
</style>

<script>
export default {
  name: "MyHeader",
  data() {
    return {
      curUser: JSON.parse(sessionStorage.getItem("currentUser"))
    }
  },
  methods: {
    toProfile() {
      alert("toProfile");
    },
    logout() {
      console.log("logout!!!");

      this.$confirm('Current session will be closed', 'Do you want to log out?', {
        confirmButtonText: 'Log out',  //确认按钮的文字显示
        cancelButtonText: 'Cancel',
        type: 'warning',
        center: true // center text
      })
          .then(() => {
            sessionStorage.clear()
            this.$router.replace("/login")
            this.$message.success("Logged out successfully!")
          })
          .catch(() => {
            this.$message.info("Log out cancelled!")
          })
    },
    handleMenu() {
      // alert("handleMenu");
      this.$store.commit("changeCollapse");
    },
  },
};
</script>

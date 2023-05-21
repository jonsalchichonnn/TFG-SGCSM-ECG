<template>
  <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0;margin: 0;">
    <h1 style="font-size: 50px;">{{ `Welcome ${user.name} !` }}</h1>
    <el-descriptions title="User Profile" :column="2" size="medium" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          Account
        </template>
        {{ user.account }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          Phone
        </template>
        {{ user.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          Sex
        </template>
        <el-tag
            :type="user.sex === '1' ? 'primary' : 'danger'"
            disable-transitions><i
            :class="user.sex === 1?'el-icon-male':'el-icon-female'"></i>{{ user.sex === 1 ? "M" : "F" }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          Role
        </template>
        <el-tag
            type="success"
            disable-transitions>{{ user.role === 0 ? "Super Admin" : (user.role === 1 ? "Admin" : "User") }}
        </el-tag>

      </el-descriptions-item>
    </el-descriptions>

    <DateUtils></DateUtils>
  </div>
</template>

<script>
import DateUtils from "../utils/DateUtils";

export default {
  name: "WelcomePage",
  components: {DateUtils},
  data() {

    return {
      // user: {}
    }
  },
  methods: {
    // init() {
    //   this.user = JSON.parse(sessionStorage.getItem('currentUser'))
    // }
  },
  computed:{
    user(){
      return this.$store.state.currentUser.user
    }
  }
  // created() {
  //   this.init()
  // }
}
</script>

<style scoped>
.el-descriptions {
  width: 90%;

  margin: 0 auto;
  text-align: center;
}
</style>
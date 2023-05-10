<template>
  <div class="login_container">
    <div class="login_form">
      <p class="login_title">SGCSM-ECG</p>
      <el-form
          v-loading="loading"
          element-loading-text="Loading..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          ref="loginForm"
          :model="loginForm"
          :rules="rules"
          status-icon
          label-width="100px"
      >
        <el-form-item label="Account" prop="account">
          <el-input
              v-model="loginForm.account"
              placeholder="Enter your account please"
          />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input
              type="password"
              v-model="loginForm.password"
              placeholder="Enter your password please"
              show-password
              @keyup.enter.native="login"
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              size="medium"
              style="width:100%"
              @click="login"
              :disabled="login_disabled"
          >Log in
          </el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.login_container {
  width: 100%;
  height: 100vh;
  /* background-color: rgba(242, 242, 242, 1); */
  background-image: url("../assets/login-bg.jpg");
  background-position: center bottom;
  background-repeat: no-repeat;
  background-attachment: scroll;
  background-size: cover;
  background-origin: border-box;
  border: none;
  border-radius: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login_form {
  width: 510px;
  margin: 0px auto;
  padding: 0 55px 15px 35px;
  background-color: #fff;
  border: none;
  border-radius: 5px;
  box-shadow: 0 0 25px;
}

.login_title {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
  text-decoration: none;
  color: rgb(0, 121, 254);
  font-size: 32px;
  margin-top: 50px;
  margin-bottom: 30px;
  text-align: center;
}
</style>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      loading:false,
      login_disabled: false,
      loginForm: {
        account: "",
        password: "",
      },
      rules: {
        account: [
          {
            required: true,
            message: "Enter your account please",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "Enter your password please",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    login() {
      console.log("LOGIN = " + this.loginForm.account + " + " + this.loginForm.password);

      this.login_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          //validate account credentials in the backend
          this.loading = true;
          this.$axios.post('/login', this.loginForm).then(res => res.data).then(res => {
            console.log(res)
            this.loading = false;
            if (res.code === 200) {
              // store current user data
              sessionStorage.setItem("currentUser", JSON.stringify(res.data))

              // console.log(res.data.menu)
              // this.$store.commit("setMenu", res.data.menu)
              //跳转到主页
              // this.$router.replace('/home');
              this.$router.replace('/');
              this.$message.success("Logged in successfully!")
            } else {
              this.login_disabled = false;
              // flash message
              this.$message.error(res.msg);
              return false;
            }
          });
        } else {
          this.login_disabled = false;
          console.log('Invalid form');
          this.$message.error('Please fill every field');
          return false;
        }
      });
    },
  },
};
</script>

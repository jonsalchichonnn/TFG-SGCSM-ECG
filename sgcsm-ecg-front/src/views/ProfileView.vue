<template>
  <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 32px;margin: 0;">
    <h1 style="font-size: 50px;">{{ `${user.name}'s Profile` }}</h1>
    <el-descriptions title="" :column="2" size="medium" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          Account
        </template>
        <el-tag>{{ user.account }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          Name
        </template>
        <el-tag type="warning">{{ user.name }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          Age
        </template>
        <el-tag type="info">{{ user.age }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          Phone
        </template>
        <el-tag>{{ user.phone }}</el-tag>
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
    <div style="display: flex;justify-content: space-around;margin-top: 10px;">
      <el-button type="primary" size="mini" @click="showUpdateUser">Update Information</el-button>
      <el-button type="danger" size="mini" @click="showUpdatePwd">Change Password</el-button>
    </div>
    <el-dialog
        title="Update Information"
        :visible.sync="infoDialogVisible"
        width="30%"
        center
    >
      <el-form ref="addForm" :rules="rules" :model="addForm" label-width="80px">
        <el-form-item label="Account" prop="account">
          <el-col :span="20">
            <el-input v-model="addForm.account" :disabled=true></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-col :span="20">
            <el-input v-model="addForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Age" prop="age">
          <el-col :span="20">
            <el-input v-model="addForm.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Sex">
          <el-radio-group v-model="addForm.sex">
            <el-radio label="1">Male</el-radio>
            <el-radio label="0">Female</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-col :span="20">
            <el-input v-model="addForm.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="infoDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateUser">Update</el-button>
      </span>
    </el-dialog>
    <!-- Change pwd dialog -->
    <el-dialog
        title="Change Password"
        :visible.sync="pwdDialogVisible"
        width="30%">
      <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="80px">
          <el-form-item label="Old" prop="oldPass">
            <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"
                      placeholder="Enter old password please"></el-input>
          </el-form-item>
          <el-form-item label="New" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"
                      placeholder="Enter new password please"></el-input>
          </el-form-item>
          <el-form-item label="Password check" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"
                      placeholder="Enter new password again please"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="changePassword('ruleForm')">Submit</el-button>
            <el-button @click="resetForm('ruleForm')">Reset</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ProfileView",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error("Age is too big"));
      } else {
        callback();
      }
    };
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter a password'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    }
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter the password again'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('The second password is different!'));
      } else {
        callback();
      }
    }

    return {
      admin2: null, // Edit object
      infoDialogVisible: false,
      pwdDialogVisible: false,

      ruleForm: { // change pwd form
        pass: '',
        checkPass: '',
        oldPass: '',
      },
      rules: {
        name: [
          {
            required: true,
            message: "Introduce your name please",
            trigger: "blur",
          },
        ],
        age: [
          {
            required: true,
            message: "Introduce your age please",
            trigger: "blur",
          },
          {
            min: 1,
            max: 3,
            message: "Length should be between 1 to 3 characters",
            trigger: "blur",
          },
          {
            pattern: /^([1-9][0-9]*){1,3}$/,
            message: "Age should be bigger than zero ",
            trigger: "blur",
          },
          {validator: checkAge, trigger: "blur"},
        ],
        phone: [
          {
            required: true,
            message: "Phone number may not be empty",
            trigger: "blur",
          },
          {
            pattern: /^\+?(6\d{2}|7[1-9]\d{1})\d{6}$/,
            message: "Introduce a correct spanish phone number please",
            trigger: "blur",
          },
        ],
        pass: [
          {
            required: true,
            message: "Introduce your password please",
            trigger: "blur",
          },
          {
            min: 3,
            max: 8,
            message: "Length should be between 3 to 8 characters",
            trigger: "blur",
          },
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {
            required: true,
            message: "Introduce your password please",
            trigger: "blur",
          },
          {validator: validatePass2, trigger: 'blur'}
        ],
        oldPass: [
          {
            required: true,
            message: "Introduce your password please",
            trigger: "blur",
          },
          {validator: validatePass, trigger: 'blur'}
        ]
      },
      addForm: {
        id: "",
        account: "",
        name: "",
        password: "",
        age: "",
        phone: "",
        sex: "",
        role: -1,
      },
    }
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('currentUser'))
    },
    changePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
              .put(`/users/${this.user.id}/pass`, this.ruleForm)
              .then((res) => res.data)
              .then((res) => {
                console.log(res);
                if (res.code === 200) {
                  // After new password is set => log out
                  sessionStorage.clear()
                  this.$store.commit('initRoutes', []) // reset routes
                  this.$router.replace('/login') // back to login
                } else {
                  this.$message.error(res.msg);
                }
              });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showUpdatePwd() {
      this.pwdDialogVisible = true
    },
    getCurrentUser() {
      this.loading = true;
      this.$axios
          .get(`/users/${this.addForm.id}`)
          .then((res) => res.data) // getData from axios response object
          .then((res) => {
            console.log(res);
            this.loading = false;
            if (res.code === 200) {
              this.$store.commit("initUser", res.data)
            } else {
              this.$message.error(res.msg);
            }
          }); // ADD EXCEPTION CONTROL (?)
    },
    updateUser() {
      // this.addForm.role = this.user.role;
      this.$axios
          .put(`/users/${this.addForm.id}`, this.addForm)
          .then((res) => res.data)
          .then((res) => {
            console.log(res);
            if (res.code === 200) {
              this.$message.success('Information updated successfully!');
              this.infoDialogVisible = false
              this.getCurrentUser();
            } else {
              this.$message.error(res.msg);
            }
          });
    },
    showUpdateUser() {
      this.infoDialogVisible = true
      this.$nextTick(() => {
        this.addForm.id = this.user.id
        this.addForm.account = this.user.account
        this.addForm.name = this.user.name
        this.addForm.age = this.user.age + ''
        this.addForm.sex = this.user.sex + ''
        this.addForm.phone = this.user.phone
        this.addForm.role = this.user.role
      })
    },
  },
  computed: {
    user() {
      return this.$store.state.currentUser.user

    }
  },
}
</script>

<style scoped>

</style>
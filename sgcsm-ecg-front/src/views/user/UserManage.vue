<template>
  <div>
    <div class="searchBar" style="margin-bottom: 16px">
      <el-input
          v-model="name"
          placeholder="Search by name"
          suffix-icon="el-icon-search"
          style="width: 200px"
          @keyup.enter.native="getUsers"
      ></el-input>
      <el-select
          v-model="sex"
          filterable
          placeholder="Search by sex"
          style="margin-left: 5px"
      >
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button type="primary" size="medium" icon="el-icon-search" style="margin-left: 5px" @click="search"
      >Search
      </el-button
      >
      <el-button type="success" size="medium" icon="el-icon-refresh" @click="resetParam">Reset</el-button>

      <el-button type="primary" size="medium" icon="el-icon-plus" style="margin-left: 5px" @click="popDialog"
      >Add User
      </el-button
      >
    </div>
    <el-table
        :data="tableData"
        :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
        border
        v-loading="loading"
        element-loading-text="Loading..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="account" label="Account" width="180">
      </el-table-column>
      <el-table-column prop="name" label="Name" width="180"></el-table-column>
      <el-table-column prop="age" label="Age" width="80"></el-table-column>
      <el-table-column prop="sex" label="Sex" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions
          >{{ scope.row.sex === 1 ? "M" : "F" }}
          </el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="role" label="Role" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="
              scope.row.role === 0
                ? 'danger'
                : scope.row.role === 1
                ? 'primary'
                : 'success'
            "
              disable-transitions
          >{{
              scope.row.role === 0
                  ? "Super Admin"
                  : scope.row.role === 1
                      ? "Admin"
                      : "user"
            }}
          </el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="Phone" width="150">
      </el-table-column>
      <el-table-column prop="operate" label="Action">
        <template slot-scope="scope">
          <el-button size="small" type="success" icon="el-icon-edit" @click="handleUpdate(scope.row)" round>Modify</el-button>
          <el-button size="small" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)" round>Delete</el-button>

          <!--          <el-popconfirm-->
          <!--              title="Are you sure?"-->
          <!--              @confirm="deleteUser(scope.row.id)"-->
          <!--              style="margin-left: 5px;"-->
          <!--          >-->
          <!--            <el-button slot="reference" size="small" type="danger">Delete</el-button>-->
          <!--          </el-popconfirm>-->
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 16px">
      <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20, 30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </div>
    <el-dialog
        :title="disableEdit ? 'Modify User' : 'Add User'"
        :visible.sync="centerDialogVisible"
        width="30%"
        @close="disableEdit = false"
        center
    >
      <el-form ref="addForm" :rules="rules" :model="addForm" label-width="80px">
        <el-form-item label="Account" prop="account">
          <el-col :span="20">
            <el-input v-model="addForm.account" :disabled="disableEdit"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-col :span="20">
            <el-input v-model="addForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-col :span="20">
            <el-input v-model="addForm.password" show-password></el-input>
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
        <el-button @click="showDialog(false)">Cancel</el-button>
        <el-button type="primary" @click="saveUpdate" v-if="disableEdit">Modify</el-button>
        <el-button type="primary" @click="saveUpdate" v-else>Add</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}
</style>

<script>
export default {
  name: "UserManage",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error("Age is too big"));
      } else {
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      // if id is provided = we are modifying => ok
      if (this.addForm.id) {
        return callback();
      }
      this.$axios
          .get("/users", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize, account: this.addForm.account
            }
          })
          .then((res) => {
            if (res.data.total === 0) {
              callback();
            } else {
              callback(new Error("Account already exists"));
            }
          });
    };
    return {
      loading: false,
      disableEdit: false,
      centerDialogVisible: false,
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: "",
      sex: "",
      sexs: [
        {
          value: "1",
          label: "Male",
        },
        {
          value: "0",
          label: "Female",
        },
      ],
      addForm: {
        id: "",
        account: "",
        name: "",
        password: "",
        age: "",
        phone: "",
        sex: "",
        role: 2,
      },
      rules: {
        account: [
          {
            required: true,
            message: "Introduce your account please",
            trigger: "blur",
          },
          {
            min: 3,
            max: 8,
            message: "Length should be between 3 to 8 characters",
            trigger: "blur",
          },
          {validator: checkDuplicate, trigger: "blur"},
        ],
        name: [
          {
            required: true,
            message: "Introduce your name please",
            trigger: "blur",
          },
        ],
        password: [
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
      },
    };
  },
  methods: {
    showDialog(show) {
      this.centerDialogVisible = show;
    },
    handleSizeChange(val) {
      console.log(`Each page ${val} entries`);
      this.pageNum = 1;
      this.pageSize = val;
      this.getUsers();
    },
    handleCurrentChange(val) {
      console.log(`Current page: ${val}`);
      this.pageNum = val;
      this.getUsers();
    },
    getUsers() {
      this.loading = true;
      this.$axios
          .get("/users", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              name: this.name,
              sex: this.sex,
              role: 2
            },
          })
          .then((res) => res.data) // getData from axios response object
          .then((res) => {
            console.log(res);
            this.loading = false;
            if (res.code === 200) {
              this.tableData = res.data; // getData from HttpResponse wrapper
              this.total = res.total;
            } else {
              this.$message.error(res.msg);
            }
          }); // ADD EXCEPTION CONTROL (码歌?)
    },
    resetParam() {
      this.name = "";
      this.sex = "";
    },
    resetForm() {
      this.$refs.addForm.resetFields();
      this.addForm.id = ""
    },
    popDialog() {
      this.showDialog(true);
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    saveUpdate() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          if (this.addForm.id) {
            this.updateUser(); // MODIFYING
          } else {
            this.saveUser();
          }
        } else {
          this.$message.error("Error on submit!!");
          return false;
        }
      });
    },
    saveUser() {
      this.$axios
          .post("/users", this.addForm)
          .then((res) => res.data)
          .then((res) => {
            console.log(res);
            if (res.code === 201) {
              this.$message.success('User created successfully!');
              this.showDialog(false);
              this.getUsers();
              this.resetForm();
            } else {
              this.$message.error(res.msg);
            }
          });
    },
    updateUser() {
      this.$axios
          .put(`/users/${this.addForm.id}`, this.addForm)
          .then((res) => res.data)
          .then((res) => {
            console.log(res);
            if (res.code === 200) {
              this.$message.success('User modified successfully!');
              this.showDialog(false);
              this.disableEdit = false
              this.getUsers();
              this.resetForm();
            } else {
              this.$message.error(res.msg);
            }
          });
    },
    handleUpdate(row) {
      this.showDialog(true)
      this.disableEdit = true
      // mejor hacer un getById al backend para obtener siempre la info actualizada
      this.$nextTick(() => {
        //get row value
        this.addForm.id = row.id
        this.addForm.account = row.account
        this.addForm.name = row.name
        this.addForm.password = '' // no dar opcion para modificarlo?
        this.addForm.age = row.age + ''
        this.addForm.sex = row.sex + ''
        this.addForm.phone = row.phone
        this.addForm.role = row.role
      })
    },
    handleDelete(id) {
      this.$confirm('This user will be permanently deleted', 'Do you want to delete?', {
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel',
        type: 'warning',
        center: true
      }).then(() => {
        this.deleteUser(id)
      }).catch(() => {
        this.$message.info('User delete cancelled');
      });
    },
    deleteUser(id) {
      console.log(`Deleting user w id = ${id}`)

      this.$axios.delete(`/users/${id}`).then(res => res.data).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message.success('User deleted successfully');
          this.getUsers()
        } else {
          this.$message.error('Error on delete...');
        }

      })
    },
    search() {
      this.pageNum = 1;
      this.getUsers();
    }
  },
  mounted() {
    this.getUsers();
  },
};
</script>

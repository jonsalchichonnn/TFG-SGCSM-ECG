<template>
  <div>
    <div class="searchBar" style="margin-bottom: 16px">
      <el-input
          v-model="name"
          placeholder="Search by name"
          suffix-icon="el-icon-search"
          style="width: 200px"
          @keyup.enter.native="getDepartments"
      ></el-input>
      <el-button type="primary" size="medium" icon="el-icon-search" style="margin-left: 5px" @click="search"
      >Search
      </el-button
      >
      <el-button type="success" size="medium" icon="el-icon-refresh" @click="resetParam">Reset</el-button>

      <el-button type="primary" size="medium" icon="el-icon-plus" style="margin-left: 5px" @click="popDialog"
      >Add Department
      </el-button
      >
    </div>
    <el-table
        :data="tableData"
        :header-cell-style="{ background: '#A9A9A9', color: '#fff' }"
        border
        v-loading="loading"
        element-loading-text="Loading..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="name" label="Name" width="180"></el-table-column>
      <el-table-column prop="comment" label="Comment"></el-table-column>
      <el-table-column prop="operate" label="Action">
        <template slot-scope="scope">
          <el-button size="small" type="success" icon="el-icon-edit" @click="handleUpdate(scope.row)" round>Modify
          </el-button>
          <el-button size="small" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)" round>Delete
          </el-button>
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
        :title="disableEdit ? 'Modify Department' : 'Add Department'"
        :visible.sync="centerDialogVisible"
        width="30%"
        @close="disableEdit = false"
        center
    >
      <el-form ref="addForm" :rules="rules" :model="addForm" label-width="80px">
        <el-form-item label="Name" prop="name">
          <el-col >
            <el-input v-model="addForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="Comment" prop="comment">
          <el-col >
            <el-input :autosize="{ minRows: 2}" type="textarea" v-model="addForm.comment"></el-input>
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
  name: "DepartManage",
  data() {
    let checkDuplicate = (rule, value, callback) => {
      // if id is provided = we are modifying => ok
      if (this.addForm.id) {
        return callback();
      }
      this.$axios
          .get("/departments", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize, newName: this.addForm.name
            }
          })
          .then((res) => {
            if (res.data.total === 0) {
              callback();
            } else {
              callback(new Error("Department already exists"));
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
      addForm: {
        id: '',
        name: '',
        comment: ''
      },
      rules: {
        name: [
          {
            required: true,
            message: "Introduce your name please",
            trigger: "blur",
          },
          {validator: checkDuplicate, trigger: "blur"},
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
      this.getDepartments();
    },
    handleCurrentChange(val) {
      console.log(`Current page: ${val}`);
      this.pageNum = val;
      this.getDepartments();
    },
    getDepartments() {
      this.loading = true;
      this.$axios
          .get("/departments", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              name: this.name
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
          }); // ADD EXCEPTION CONTROL (?)
    },
    resetParam() {
      this.name = "";
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
            this.updateDepartment(); // MODIFYING
          } else {
            this.saveDepartment();
          }
        } else {
          this.$message.error("Error on submit!!");
          return false;
        }
      });
    },
    saveDepartment() {
      this.$axios
          .post("/departments", this.addForm)
          .then((res) => res.data)
          .then((res) => {
            console.log(res);
            if (res.code === 201) {
              this.$message.success('Department created successfully!');
              this.showDialog(false);
              this.getDepartments();
              this.resetForm();
            } else {
              this.$message.error(res.msg)
            }
          });
    },
    updateDepartment() {
      this.$axios
          .put(`/departments/${this.addForm.id}`, this.addForm)
          .then((res) => res.data)
          .then((res) => {
            console.log(res);
            if (res.code === 200) {
              this.$message.success('Department modified successfully!');
              this.showDialog(false);
              this.disableEdit = false
              this.getDepartments();
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
        this.addForm.name = row.name
        this.addForm.comment = row.comment
      })
    },
    handleDelete(id) {
      this.$confirm('This department will be permanently deleted', 'Do you want to delete?', {
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel',
        type: 'warning',
        center: true
      }).then(() => {
        this.deleteDepartment(id)
      }).catch(() => {
        this.$message.info('Department delete cancelled');
      });
    },
    deleteDepartment(id) {
      console.log(`Deleting user w id = ${id}`)

      this.$axios.delete(`/departments/${id}`).then(res => res.data).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message.success('Department deleted successfully');
          this.getDepartments()
        } else {
          this.$message.error('Error on delete...');
        }

      })
    },
    search() {
      this.pageNum = 1;
      this.getDepartments();
    }
  },
  mounted() {
    this.getDepartments();
  },
};
</script>

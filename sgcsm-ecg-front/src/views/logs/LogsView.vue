<template>
  <div>
    <div style="display: flex;justify-content: space-around;margin-bottom: 16px">
      <div class="searchBar" style="margin-bottom: 16px">
        <el-input
            v-model="deviceId"
            placeholder="Search by device ID"
            suffix-icon="el-icon-search"
            style="width: 200px"
            @keyup.enter.native="getLogs"
        ></el-input>
        <el-button type="primary" size="medium" icon="el-icon-search" style="margin-left: 5px" @click="search"
        >Search
        </el-button
        >
        <el-button type="success" size="medium" icon="el-icon-refresh" @click="resetParam">Reset</el-button>
      </div>
      <div class="import-export">
        <el-button type="warning" @click="exportData"><i class="el-icon-download"></i>&nbsp; Export Data
        </el-button>
      </div>
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
      <el-table-column prop="deviceId" label="Device ID" width="200"></el-table-column>
      <el-table-column prop="ip" label="Operator IP"></el-table-column>
      <el-table-column prop="createTime" label="Record Creation Time"></el-table-column>
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
  </div>
</template>

<style></style>

<script>
export default {
  name: "LogsView",
  data() {
    return {
      loading: false,
      disableEdit: false,
      centerDialogVisible: false,
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      deviceId: "",
    };
  },
  methods: {
    exportData() {
      this.downloadRequest('/logs/export');
    },
    showDialog(show) {
      this.centerDialogVisible = show;
    },
    handleSizeChange(val) {
      console.log(`Each page ${val} entries`);
      this.pageNum = 1;
      this.pageSize = val;
      this.getLogs();
    },
    handleCurrentChange(val) {
      console.log(`Current page: ${val}`);
      this.pageNum = val;
      this.getLogs();
    },
    getLogs() {
      this.loading = true;
      this.$axios
          .get("/logs", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              deviceId: this.deviceId
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
      this.deviceId = "";
    },
    // resetForm() {
    //   this.$refs.addForm.resetFields();
    //   this.addForm.id = ""
    // },
    // popDialog() {
    //   this.showDialog(true);
    //   this.$nextTick(() => {
    //     this.resetForm();
    //   });
    // },
    // saveUpdate() {
    //   this.$refs.addForm.validate((valid) => {
    //     if (valid) {
    //       if (this.addForm.id) {
    //         this.updateDepartment(); // MODIFYING
    //       } else {
    //         this.saveDepartment();
    //       }
    //     } else {
    //       this.$message.error("Error on submit!!");
    //       return false;
    //     }
    //   });
    // },
    // saveDepartment() {
    //   this.$axios
    //       .post("/departments", this.addForm)
    //       .then((res) => res.data)
    //       .then((res) => {
    //         console.log(res);
    //         if (res.code === 201) {
    //           this.$message.success('Department created successfully!');
    //           this.showDialog(false);
    //           this.getLogs();
    //           this.resetForm();
    //         } else {
    //           this.$message.error(res.msg)
    //         }
    //       });
    // },
    // updateDepartment() {
    //   this.$axios
    //       .put(`/departments/${this.addForm.id}`, this.addForm)
    //       .then((res) => res.data)
    //       .then((res) => {
    //         console.log(res);
    //         if (res.code === 200) {
    //           this.$message.success('Department modified successfully!');
    //           this.showDialog(false);
    //           this.disableEdit = false
    //           this.getLogs();
    //           this.resetForm();
    //         } else {
    //           this.$message.error(res.msg);
    //         }
    //       });
    // },
    // handleUpdate(row) {
    //   this.showDialog(true)
    //   this.disableEdit = true
    //   // mejor hacer un getById al backend para obtener siempre la info actualizada
    //   this.$nextTick(() => {
    //     //get row value
    //     this.addForm.id = row.id
    //     this.addForm.name = row.name
    //     this.addForm.comment = row.comment
    //   })
    // },
    // handleDelete(id) {
    //   this.$confirm('This department will be permanently deleted', 'Do you want to delete?', {
    //     confirmButtonText: 'Delete',
    //     cancelButtonText: 'Cancel',
    //     type: 'warning',
    //     center: true
    //   }).then(() => {
    //     this.deleteDepartment(id)
    //   }).catch(() => {
    //     this.$message.info('Department delete cancelled');
    //   });
    // },
    // deleteDepartment(id) {
    //   console.log(`Deleting user w id = ${id}`)
    //
    //   this.$axios.delete(`/departments/${id}`).then(res => res.data).then(res => {
    //     console.log(res)
    //     if (res.code === 200) {
    //       this.$message.success('Department deleted successfully');
    //       this.getLogs()
    //     } else {
    //       this.$message.error('Error on delete...');
    //     }
    //
    //   })
    // },
    search() {
      this.pageNum = 1;
      this.getLogs();
    }
  },
  mounted() {
    this.getLogs();
  },
};
</script>

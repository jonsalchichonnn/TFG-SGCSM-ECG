<template>
  <div>
    <div class="search-bar" style="display: flex;justify-content: center;margin-bottom: 16px">
      <el-input
          v-model="deviceId"
          placeholder="Search by device ID"
          suffix-icon="el-icon-search"
          style="width: 200px"
          @keyup.enter.native="getDevices"
      ></el-input>
      <el-button type="primary" size="medium" icon="el-icon-search" style="margin-left: 5px" @click="search"
      >Search
      </el-button
      >
      <el-button type="success" size="medium" icon="el-icon-refresh" @click="resetParam">Reset</el-button>
    </div>

    <div class="device-container">
      <el-card class="device-card" v-for="(device,index) in devices" :key="index" shadow="always">
        <div slot="header" class="clearfix">
          <div class="device-info-tab">
            <div style="margin-bottom:5px;margin-top:5px;margin-right: 8px;">{{ device.id }}</div>
            <div><img src="../../assets/ic_launcher.png" :alt="device.id" :title="device.id" class="device-img"></div>
          </div>
        </div>
        <el-button type="primary" icon="el-icon-more" size="medium" style="margin-left: 5px" @click="popDialog"
        >Details
        </el-button
        >

        <el-dialog
            :title="device.id"
            :visible.sync="centerDialogVisible"
            width="30%"
            @close="disableEdit = false"
            center
        >
          <div class="device-info-container">
            <el-collapse v-model="activeName" accordion> <!-- @change="change" -->
              <!--            <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">-->
              <!--              <el-card class="box-card">-->
              <!--                <div slot="header" class="clearfix">-->
              <!--                  <span>可访问资源</span>-->
              <!--                  <el-button type="text" icon="el-icon-delete" style="float: right;padding: 3px 0;color: #f41f0a" @click="doDeleteRole(r)">-->
              <!--                  </el-button>-->
              <!--                </div>-->
              <!--              </el-card>-->
              <!--            </el-collapse-item>-->
              <el-collapse-item title="System" name="1">
                <div>Brand：{{ device.brand }}</div>
                <div>Model：{{ device.model }}</div>
                <div>OS：{{ device.os }}</div>
                <div>API Level：{{ device.api }}</div>
                <div>Manufacturer：{{ device.manufacturer }}</div>
                <div>Hardware：{{ device.hardware }}</div>
                <div>Bootloader：{{ device.bootloader }}</div>
              </el-collapse-item>

              <el-collapse-item title="Battery" name="2">
                <div>Load level：{{ device.batLoad }} %</div>
                <div>Temperature：{{ device.batTemp }} ºC</div>
                <div>Power Source：{{ device.batPowerSrc }}</div>
                <div>Status：{{ device.batStatus }}</div>
                <div>Health：{{ device.batHealth }}</div>
                <div>Technology：{{ device.batTech }}</div>
                <div>Voltage：{{ device.batVoltage }} mV</div>
                <div>Capacity：{{ device.batCapacity }} mAh</div>
              </el-collapse-item>

              <el-collapse-item title="Storage" name="3">
                <h4>Storage</h4>
                <el-progress :percentage="device.usedMemPct"></el-progress>
                <div>Total：{{ device.totalMemGb }} GB</div>
                <div>Used：{{ device.usedMem }}</div>
                <div>Free：{{ device.freeMem }}</div>
                <!--            <div>Load level：{{ device.usedMemPct }} %</div>-->
                <h4>RAM</h4>
                <el-progress :percentage="device.usedRamPct"></el-progress>
                <div>Total：{{ device.totalRamGb }}</div>
                <div>Used：{{ device.usedRamGb }}</div>
                <div>Free：{{ device.freeRamGb }}</div>
                <!--            <div>Load level：{{ device.usedRamPct }} %</div>-->
              </el-collapse-item>

              <el-collapse-item title="Graphics" name="4">
                <h4>Screen</h4>
                <div>Inches：{{ device.screenInches }}</div>
                <div>Resolution：{{ device.screenWidth }} x {{ device.screenHeight }} px</div>
                <div>Density：{{ device.density }}</div>
                <div>Refresh Rate：{{ device.refreshRate }}</div>
                <div>HDR Capabilities：{{ device.hdrCapabilities }}</div>
                <!--            <div>Total：{{ device.screenWidth }}</div>-->
                <!--            <div>Total：{{ device.screenHeight }}</div>-->
                <h4>GPU</h4>
                <div>Vendor：{{ device.renderer }}</div>
                <div>Renderer：{{ device.vendor }}</div>
                <div>OpenGL Version：{{ device.openglVersion }}</div>
              </el-collapse-item>

              <el-collapse-item title="Network" name="5">
                <!-- FALTA ASIGNAR STATUS EN ANDROID -->
                <div>Status：{{ device.status }}</div> <!-- ambos -->
                <div>SSID：{{ device.ssid }}</div>
                <div>BSSID：{{ device.bssid }}</div>
                <div>Signal：{{ device.wifiStrength }}</div>
                <div>Link Speed：{{ device.linkSpeed }}</div>
                <div>Frequency：{{ device.wifiFreqGhz }} GHz / {{ device.wifiFreq }} MHz</div>

                <div>Down/Upload Speed：{{ device.downSpeed }} / {{ device.upSpeed }} Mbps</div>

                <div>IP Address：{{ device.ip }}</div><!-- wifi -->
                <div>MAC Address：{{ device.realmac }}</div>
                <div>Gateway：{{ device.gateway }}</div>
                <div>Netmask：{{ device.netmask }}</div>
                <div>DNS1：{{ device.dns1 }}</div>
                <div>DNS2：{{ device.dns2 }}</div>

                <!-- cell network -->
                <div>Signal：{{ device.cellSignal }}</div>
                <div>Network Type：{{ device.cellNetworkType }}</div>
                <div>IP Address：{{ device.cellIp }}</div>

              </el-collapse-item>

              <el-collapse-item title="Location" name="6">
                <div>Latitude：{{ device.latitude }}</div>
                <div>Longitude：{{ device.longitude }}</div>
              </el-collapse-item>

              <el-collapse-item title="Sensors" name="7">
                <div v-for="(sensor,index) in device.sensors" :key="index">
                  <div>{{ sensor.name }}</div>
                  <div>{{ sensor.type }}</div>
                  <div>{{ sensor.vendor }}</div>
                  <div>{{ sensor.version }}</div>
                  <div>{{ sensor.power }}</div>
                  <br>
                </div>
              </el-collapse-item>

              <el-collapse-item title="Camera" name="8">
                <h4>Front</h4>
                <div>Pixels：{{ device.frontResolution }}</div>
                <div>Resolution：{{ device.frontWidth }} x {{ device.frontHeight }}</div>
                <div>Flash：{{ device.frontFlash }}</div>

                <h4>Rear</h4>
                <div>Pixels：{{ device.rearResolution }}</div>
                <div>Resolution：{{ device.rearWidth }} x {{ device.rearHeight }}</div>
                <div>Flash：{{ device.rearFlash }}</div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-dialog>

      </el-card>
    </div>
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

<style>
/*.el-header {*/
/*  background-color: #b3c0d1;*/
/*  color: #333;*/
/*  line-height: 60px;*/
/*}*/

.device-container {
  margin-top: 8px;
  /*width: 700px;*/
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap; /* automatically switch line */
}

.device-img {
  width: 72px;
  height: 72px;
  border-radius: 36px;
}

.device-info-tab {
  /*display: flex;*/
  justify-content: center;
}

.clearfix {
  display: flex;
  justify-content: space-between;
}
</style>

<script>
export default {
  name: "DeviceManage",
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
      // tableData: [],
      devices: [],
      activeName: -1, // el-collapse -> closed by default
      detailCollapse: -1,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      deviceId: "",
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
      this.getDevices();
    },
    handleCurrentChange(val) {
      console.log(`Current page: ${val}`);
      this.pageNum = val;
      this.getDevices();
    },
    getDevices() {
      this.loading = true;
      this.$axios
          .get("/devices", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              id: this.deviceId
            },
          })
          .then((res) => res.data) // getData from axios response object
          .then((res) => {
            console.log(res);
            this.loading = false;
            if (res.code === 200) {
              console.log(res.data)
              this.devices = res.data; // getData from HttpResponse wrapper
              this.total = res.total;
            } else {
              this.$message.error(res.msg);
            }
          }); // ADD EXCEPTION CONTROL (码歌?)
    },
    resetParam() {
      this.deviceId = "";
    },
    resetForm() {
      this.$refs.addForm.resetFields();
      this.addForm.id = ""
    },
    popDialog() {
      this.showDialog(true);
      // this.$nextTick(() => {
      //   this.resetForm();
      // });
    },
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
    //           this.getDevices();
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
    //           this.getDevices();
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
    //       this.getDevices()
    //     } else {
    //       this.$message.error('Error on delete...');
    //     }
    //
    //   })
    // },
    search() {
      this.pageNum = 1;
      this.getDevices();
    }
  },
  mounted() {
    this.getDevices();
  },
};
</script>

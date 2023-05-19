<template>
  <div>
    <div style="display: flex;justify-content: space-around;margin-bottom: 16px">
      <div class="search-bar">
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
      <div class="import-export">
        <el-button type="success" @click="exportData"><i class="el-icon-download"></i>&nbsp; Export Data
        </el-button>
      </div>
    </div>
    <div class="device-container"
         v-loading="loading"
         element-loading-text="Loading..."
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
      <el-card class="device-card" v-for="(device,index) in devices" :key="index" shadow="always">
        <div slot="header" class="clearfix">
          <div class="device-info-tab">
            <div style="margin-bottom:5px;margin-top:5px;margin-right: 8px;">{{ device.id }}</div>
            <!-- crear funcion para poner ruta dinámica en :src -->
            <div><img :src="img_src(device.brand)" :alt="device.id" :title="device.id" class="device-img"></div>
          </div>
        </div>
        <el-button type="primary" icon="el-icon-more" size="medium" style="margin-left: 5px" @click="popDialog(device)"
        >Details
        </el-button
        >
        <el-dialog
            :title="dialogDev.id"
            :visible.sync="centerDialogVisible"
            width="30%"
            @close="disableEdit = false"
            center
        >
          <div class="device-info-container">
            <el-collapse v-model="activeName" accordion> <!-- @change="change" -->
              <el-collapse-item title="System" name="1">
                <div>Brand：{{ dialogDev.brand }}</div>
                <div>Model：{{ dialogDev.model }}</div>
                <div>OS：{{ dialogDev.os }}</div>
                <div>API Level：{{ dialogDev.api }}</div>
                <div>Manufacturer：{{ dialogDev.manufacturer }}</div>
                <div>Hardware：{{ dialogDev.hardware }}</div>
                <div>Bootloader：{{ dialogDev.bootloader }}</div>
              </el-collapse-item>

              <el-collapse-item title="Battery" name="2">
                <div>Load level：{{ dialogDev.batLoad }} %</div>
                <div>Temperature：{{ dialogDev.batTemp }} ºC</div>
                <div>Power Source：{{ dialogDev.batPowerSrc }}</div>
                <div>Status：{{ dialogDev.batStatus }}</div>
                <div>Health：{{ dialogDev.batHealth }}</div>
                <div>Technology：{{ dialogDev.batTech }}</div>
                <div>Voltage：{{ dialogDev.batVoltage }} mV</div>
                <div>Capacity：{{ dialogDev.batCapacity }} mAh</div>
              </el-collapse-item>

              <el-collapse-item title="Storage" name="3">
                <div style="display: flex;justify-content: space-evenly;">
                  <div>
                    <h4 style="color: #2552E4;margin-bottom: 8px">Storage</h4>
                    <el-progress stroke-width=3 width=100 type="circle"
                                 :percentage="round(dialogDev.usedMemPct)"></el-progress>
                    <div style="margin-top: 8px">Total：{{ dialogDev.totalMemGb }} GB</div>
                    <div>Used：{{ dialogDev.usedMem }}</div>
                    <div>Free：{{ dialogDev.freeMem }}</div>
                  </div>
                  <br>
                  <div>
                    <h4 style="color: #2552E4;margin-bottom: 8px">RAM</h4>
                    <el-progress stroke-width=3 width=100 type="circle"
                                 :percentage="round(dialogDev.usedRamPct)"></el-progress>
                    <div style="margin-top: 8px">Total：{{ dialogDev.totalRamGb }}</div>
                    <div>Used：{{ dialogDev.usedRamGb }}</div>
                    <div>Free：{{ dialogDev.freeRamGb }}</div>
                  </div>
                </div>
              </el-collapse-item>

              <el-collapse-item title="Graphics" name="4">
                <h4 class="h4-custom">Screen</h4>
                <div><span style="font-weight: bold">Inches：</span>{{ dialogDev.screenInches }}</div>
                <div>Resolution：{{ dialogDev.screenWidth }} x {{ dialogDev.screenHeight }} px</div>
                <div>Density：{{ dialogDev.density }} dpi</div>
                <div>Refresh Rate：{{ dialogDev.refreshRate }} Hz</div>
                <div>HDR Capabilities：{{ dialogDev.hdrCapabilities }}</div>
                <br>
                <h4 class="h4-custom">GPU</h4>
                <div>Vendor：{{ dialogDev.renderer }}</div>
                <div>Renderer：{{ dialogDev.vendor }}</div>
                <div>OpenGL Version：{{ dialogDev.openglVersion }}</div>
              </el-collapse-item>

              <el-collapse-item title="Network" name="5">
                <div>Status：{{ dialogDev.status }}</div> <!-- ambos -->
                <div v-show="dialogDev.ssid">SSID：{{ dialogDev.ssid }}</div>
                <div v-show="dialogDev.bssid">BSSID：{{ dialogDev.bssid }}</div>
                <div v-show="dialogDev.wifiStrength">Signal：{{ dialogDev.wifiStrength }}</div>
                <div v-show="dialogDev.linkSpeed">Link Speed：{{ dialogDev.linkSpeed }}</div>
                <div v-show="dialogDev.wifiFreqGhz">Frequency：{{ dialogDev.wifiFreqGhz }} GHz / {{ dialogDev.wifiFreq }} MHz
                </div>

                <div v-show="dialogDev.status && !dialogDev.status.startsWith('Offline')">Down/Upload
                  Speed：{{ dialogDev.downSpeed }} / {{ dialogDev.upSpeed }} Mbps
                </div>

                <div v-show="dialogDev.ip">IP Address：{{ dialogDev.ip }}</div><!-- wifi -->
                <div v-show="dialogDev.realmac">MAC Address：{{ dialogDev.realmac }}</div>
                <div v-show="dialogDev.gateway">Gateway：{{ dialogDev.gateway }}</div>
                <div v-show="dialogDev.netmask">Netmask：{{ dialogDev.netmask }}</div>
                <div v-show="dialogDev.dns1">DNS1：{{ dialogDev.dns1 }}</div>
                <div v-show="dialogDev.dns2">DNS2：{{ dialogDev.dns2 }}</div>

                <!-- cell network -->
                <div v-show="dialogDev.cellSignal">Signal：{{ dialogDev.cellSignal }}</div>
                <div v-show="dialogDev.cellNetworkType">Network Type：{{ dialogDev.cellNetworkType }}</div>
                <div v-show="dialogDev.cellIp">IP Address：{{ dialogDev.cellIp }}</div>

              </el-collapse-item>

              <el-collapse-item title="Location" name="6">
                <div>Latitude：{{ dialogDev.latitude ? dialogDev.latitude : NOT_AVAILABLE }}</div>
                <div>Longitude：{{ dialogDev.longitude ? dialogDev.longitude : NOT_AVAILABLE }}</div>
              </el-collapse-item>

              <el-collapse-item title="Sensors" name="7">
                <div v-for="(sensor,index) in dialogDev.sensors" :key="index">

                  <h4 class="h4-custom">{{ sensor.name }}</h4>
                  <div>Type: {{ sensor.type }}</div>
                  <div>Vendor: {{ sensor.vendor }}</div>
                  <div>Version: {{ sensor.version }}</div>
                  <div>Power: {{ sensor.power }}</div>
                  <br>
                </div>
              </el-collapse-item>

              <el-collapse-item title="Camera" name="8">
                <h4 class="h4-custom">Front</h4>
                <div>Pixels：{{ dialogDev.frontResolution }}</div>
                <div>Resolution：{{ dialogDev.frontWidth }} x {{ dialogDev.frontHeight }}</div>
                <div>Flash：{{ dialogDev.frontFlash }}</div>

                <h4 class="h4-custom">Rear</h4>
                <div>Pixels：{{ dialogDev.rearResolution }}</div>
                <div>Resolution：{{ dialogDev.rearWidth }} x {{ dialogDev.rearHeight }}</div>
                <div>Flash：{{ dialogDev.rearFlash }}</div>
              </el-collapse-item>
            </el-collapse>
          </div>

        </el-dialog>

      </el-card>
    </div>
    <div style="margin-top: 32px">
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
.h4-custom {
  color: #2552E4;
  border-bottom: lightgrey solid
}

.device-container {
  margin-top: 32px;
  /*width: 700px;*/
  display: flex;
  justify-content: space-evenly;
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
import launcher from "../../assets/ic_launcher.png";
import samsung from "../../assets/samsung.jpg";
import huawei from "../../assets/huawei.png";
import realme from "../../assets/realme.png";

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
      dialogDev: "",
      launcherSrc: launcher,
      samsungSrc: samsung,
      huaweiSrc: huawei,
      realmeSrc: realme,
      NOT_AVAILABLE: "Not Available",
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
    round(num) {
      return Math.round(num * 100) / 100
    },
    img_src(brand) {
      let lowerBrand = brand.toLowerCase();
      let src = this.launcherSrc;
      if (lowerBrand === "samsung")
        src = this.samsungSrc;
      else if (lowerBrand === "huawei") {
        src = this.huaweiSrc;
      } else if (lowerBrand === "realme")
        src = this.realmeSrc;
      // return `'../../assets/${path}'`
      return src
    },
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
    popDialog(device) {
      this.showDialog(true);
      this.dialogDev = device
      // this.$nextTick(() => {
      //   this.resetForm();
      // });
    },
    exportData() {
      this.downloadRequest('/devices/export');
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

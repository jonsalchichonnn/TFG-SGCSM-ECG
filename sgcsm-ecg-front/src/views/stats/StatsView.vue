<template>
  <div>
    <div ref="barChart" id="barChart" style="height: 300px"></div>
    <div ref="vertBarChart" id="vertBarChart" style="height: 300px"></div>
    <div ref="pieChart" id="pieChart" style="height: 300px"></div>
    <div ref="halfDonutChart" id="halfDonutChart" style="height: 300px"></div>
    <div ref="lineChart" id="lineChart" style="height: 300px"></div>
    <el-backtop :bottom="60"></el-backtop>
<!--    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>-->
  </div>
</template>

<script>

import * as echarts from "echarts"

export default {
  name: "StatsView",
  data() {
    return {
      devices: [],
      // osMap: new Map(),
      osKeys: [],
      osValues: [],
      osPieData: [],
      // brandMap: new Map(),
      brandPieData: [],
      pixelKeys: [],
      pixelValues: [],
      sensorKeys: [],
      sensorValues: [],
      memKeys: [],
      memValues: []
    }
  },
  methods: {
    round(num) {
      return Math.round(num * 100) / 100
    },
    initOsData(devList) {
      // count os freq in map
      let osMap = new Map();
      devList.forEach(dev => {
        let key = "Android " + dev.os
        let freq = osMap.get(key)
        if (freq == null)
          osMap.set(key, 1)
        else
          osMap.set(key, freq + 1)
      })
      console.log("osMap", osMap)

      this.osPieData = Array.from(osMap, ([name, value]) => ({value, name}));
      console.log("pieData", this.osPieData)

      // only for bar charts
      this.osKeys = Array.from(osMap.keys());
      this.osValues = Array.from(osMap.values());
      console.log("this.osKeys", this.osKeys)
      console.log("this.osValues", this.osValues)
    },
    initBrandData(devList) {
      // count brand freq in map
      let brandMap = new Map();
      devList.forEach(dev => {
        let key = dev.brand.toUpperCase()
        let freq = brandMap.get(key)
        if (freq == null)
          brandMap.set(key, 1)
        else
          brandMap.set(key, freq + 1)
      })
      console.log("brandMap", brandMap)

      this.brandPieData = Array.from(brandMap, ([name, value]) => ({value, name}));
      let brandValues = Array.from(brandMap.values());
      // const sum = brandValues.reduce((partialSum, a) => partialSum + a, 0);
      const fillingRecord = {
        // make an record to fill the bottom 50%
        value: brandValues.reduce((partialSum, a) => partialSum + a, 0), // brandMap values sum
        itemStyle: {
          // stop the chart from rendering this piece
          color: 'none',
          decal: {
            symbol: 'none'
          }
        },
        label: {
          show: false
        }
      };
      this.brandPieData.push(fillingRecord);
      console.log("brandPieData", this.brandPieData)
    },
    initCameraData(devList) {
      // count brand freq in map
      // let pixelMap = new Map();
      devList.forEach(dev => {
        this.pixelKeys.push(dev.id);
        this.pixelValues.push(dev.frontResolution.slice(0, -3));
      });
      console.log("this.pixelKeys", this.pixelKeys)
      console.log("this.pixelValues", this.pixelValues)

    },
    initSensorData(devList) {
      // count brand freq in map
      // let pixelMap = new Map();
      devList.forEach(dev => {
        this.sensorKeys.push(dev.id);
        this.sensorValues.push(dev.sensors.length);
      });
      console.log("this.sensorKeys", this.sensorKeys)
      console.log("this.sensorValues", this.sensorValues)
    },
    initMemData(devList) {
      // count brand freq in map
      // let pixelMap = new Map();
      devList.forEach(dev => {
        this.memKeys.push(dev.id);
        this.memValues.push(this.round(dev.usedMemPct));
      });
      console.log("this.memKeys", this.memKeys)
      console.log("this.memValues", this.memValues)
    },
    getDevices() {
      // this.loading = true;
      this.$axios
          .get("/devices")
          .then((res) => res.data) // getData from axios response object
          .then((res) => {
            console.log(res);
            this.loading = false;
            if (res.code === 200) {
              console.log(res.data)
              this.devices = res.data; // getData from HttpResponse wrapper
              this.total = res.total;
              // count os stats
              console.log("this.devices", this.devices)
              // this.devices.forEach(dev => {
              //   let key = "Android " + dev.os
              //   let freq = this.osMap.get(key)
              //   if (freq == null)
              //     this.osMap.set(key, 1)
              //   else
              //     this.osMap.set(key, freq + 1)
              // })


              this.initCameraData(this.devices);
              this.initSensorData(this.devices);
              this.initMemData(this.devices);
              this.initOsData(this.devices);
              this.initBrandData(this.devices);


              var barChart = echarts.init(this.$refs.barChart)
              var barOption = {
                title: {
                  text: "Number of sensors",//graph title
                },
                xAxis: {
                  type: 'category',
                  data: this.sensorKeys
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    data: this.sensorValues,
                    type: 'bar',
                    markPoint: {
                      data: [
                        {
                          type: "max",
                          name: "Maximum"
                        },
                        {
                          type: "min",
                          name: "Minimum"
                        }
                      ]
                    },
                    markLine: {
                      data: [
                        {
                          type: "average",
                          name: "Average"
                        }
                      ]
                    },
                    showBackground: true,
                    backgroundStyle: {
                      color: 'rgba(180, 180, 180, 0.2)'
                    },
                    itemStyle: {
                      normal: {
                        color: function (params) {
                          let colorList = ["#5470c6", "#91cc75", "#fac858",
                            "#ee6666", "#73c0de", "#3ba272",
                            "#fc8452", "#9a60b4", "#ea7ccc"]
                          return colorList[params.dataIndex]
                        }
                      }
                    }
                  }
                ]
              };
              barChart.setOption(barOption);


              var vertBarChart = echarts.init(this.$refs.vertBarChart)
              var vertBarOption = {
                title: {
                  text: "Front Camera Resolution",//graph title
                },
                tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                    type: 'shadow'
                  },
                  formatter: '{b}:<br/>{c} Mpx'

                },
                legend: {},
                grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
                },
                yAxis: {
                  type: 'category',
                  data: this.pixelKeys
                },
                xAxis: {
                  type: 'value',
                  name: "Mpx",
                  nameLocation: "start"
                },
                series: [
                  {
                    data: this.pixelValues,
                    type: 'bar',
                    markPoint: {
                      symbolRotate: -90,
                      data: [
                        {type: "max", name: "Maximum"},
                        {type: "min", name: "Minimum"}
                      ]
                    },
                    markLine: {
                      data: [{type: "average", name: "Average"}]
                    },
                    itemStyle: {
                      normal: {
                        color: function (params) {
                          let colorList = ["#5470c6", "#91cc75", "#fac858",
                            "#ee6666", "#73c0de", "#3ba272",
                            "#fc8452", "#9a60b4", "#ea7ccc"]
                          return colorList[params.dataIndex]
                        }
                      }
                    },
                    showBackground: true,
                    backgroundStyle: {
                      color: 'rgba(180, 180, 180, 0.2)'
                    },
                  }
                ]
              };
              vertBarChart.setOption(vertBarOption);


              // OS pie chart
              var pieChart = echarts.init(this.$refs.pieChart)
              var pieOption = {
                // backgroundColor: '#2c343c',
                title: {
                  text: "Pie Chart",//graph title
                  left: "center"
                },
                tooltip: {
                  trigger: 'item'
                },
                legend: {
                  // orient: 'vertical',
                  top: '16%',
                  left: 'right',
                  padding: 8,
                },
                series: [
                  {
                    name: 'Devices OS',
                    type: 'pie',
                    data: this.osPieData,
                    label: {
                      position: 'inside',
                      show: true,
                      formatter(param) {
                        // correct the percentage
                        return param.name + ' (' + param.percent + '%)'; // may be percent * 2 (?)
                      }
                    },
                    emphasis: {
                      itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                      }
                    }
                  }
                ]
              };
              pieChart.setOption(pieOption);

              // Brand
              var halfDonutChart = echarts.init(this.$refs.halfDonutChart)
              var halfDonutOption = {
                title: {
                  text: "Device Brand",//graph title
                  left: "center"
                },
                tooltip: {
                  trigger: 'item'
                },
                legend: {
                  top: '16%',
                  left: 'center',
                  // doesn't perfectly work with our tricks, disable it
                  selectedMode: false
                },
                series: [
                  {
                    name: 'Access From',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    center: ['50%', '70%'],
                    // adjust the start angle
                    startAngle: 180,
                    label: {
                      show: true,
                      formatter(param) {
                        // correct the percentage
                        return param.name + ' (' + param.percent * 2 + '%)';
                      }
                    },
                    data: this.brandPieData
                  }
                ]
              };
              halfDonutChart.setOption(halfDonutOption)

              var lineChart = echarts.init(this.$refs.lineChart)
              var lineOption = {
                title: {
                  text: "Device Storage Usage %",//graph title
                  left: "center"
                },
                xAxis: {
                  type: 'category',
                  data: this.memKeys
                },
                yAxis: {
                  type: 'value',
                  name: "%"
                },
                series: [
                  {
                    data: this.memValues,
                    type: 'line',
                    smooth: true,
                    markPoint: {
                      data: [
                        {type: "max", name: "Maximum"},
                        {type: "min", name: "Minimum"}
                      ]
                    },
                    markLine: {data: [{type: "average", name: "Average"}]},
                  }
                ]
              };

              lineChart.setOption(lineOption)

            } else {
              this.$message.error(res.msg);
            }
          }); // ADD EXCEPTION CONTROL (码歌?)
    },
    initChartsInfo() {
      this.getDevices();

      // this.$nextTick(()=>{
      //
      // });
    }
  },
  // beforeMount() {
  //   this.getDevices();
  // },
  mounted() {
    this.initChartsInfo();
  }
}
</script>

<style scoped>

</style>
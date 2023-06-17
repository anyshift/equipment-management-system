<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>设备运行状态实时监测</title>
  <%@ include file="include/head.jsp" %>
  <style>
    .el-table .is-leaf .cell {
      color: #4682b4 !important;
    }
    .device-status {
      display: inline-flex;
      align-items: center;
    }

    .green-dot {
      height: 12px;
      width: 12px;
      border-radius: 100%;
      background: #7fba02;
    }
    .purple-dot {
      height: 12px;
      width: 12px;
      border-radius: 100%;
      background: #da70d6;
    }
    .red-dot {
      height: 12px;
      width: 12px;
      border-radius: 100%;
      background: #ff4500;
    }

    .el-dialog__body {
      padding-top: 5px !important;
      padding-bottom: 5px !important;
    }

    .el-dialog th {
      display: none;
    }

    .el-table__inner-wrapper::before {
      background-color: transparent !important;
    }

    .el-tabs__content {
      padding: 5px !important;
    }

    .el-select {
      width: 100%;
    }
  </style>
</head>
<body>
<div class="container-fluid" id="app">
  <ul class="nav nav-tabs">
    <li><a href="ShebeiServlet?action=list">设备列表</a></li>
    <c:if test="${loginUser.userType != '普通员工'}"><li class="active"><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
    <c:if test="${loginUser.userType == '管理员'}"><li><a href="ShebeiServlet?action=reportRecord">故障设备上报记录</a></li></c:if>
    <c:if test="${loginUser.userType == '管理员'}"><li><a href="shebei_dispatch_record.jsp">设备检查记录</a></li></c:if>
  </ul>
  <br/>
  <div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="设备名称" align="center">
        <template #default="scope">
          <a :href="'ShebeiServlet?action=get&id=' + scope.row.device.id"><span v-text="scope.row.device.shebeiName"></span></a>
        </template>
      </el-table-column>
      <el-table-column label="当前运行温度" align="center">
        <template #default="scope">
          <div v-if="scope.row.device.shebeiStatus === '正常'">
            <div style="display: flex; align-items: center;" :style="{'justify-content': (!scope.row.device.temperatureRecord) ? 'center' : 'space-between'}">
              <div>
                <span v-if="!scope.row.device.temperatureRecord">-</span>
                <span v-else v-text="scope.row.currentTemperature.toFixed(1) + '℃'"></span>
              </div>
              <div v-if="scope.row.device.temperatureRecord">
                <el-tooltip content="该设备的温度临界值" placement="top" :show-after="600">
                  <el-text type="info" v-text="scope.row.device.temperatureThreshold.toFixed(1) + '℃'"></el-text>
                </el-tooltip>
              </div>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="当前环境湿度" align="center">
        <template #default="scope">
          <div v-if="scope.row.device.shebeiStatus === '正常'">
            <div style="display: flex; align-items: center;" :style="{'justify-content': (!scope.row.device.humidityRecord) ? 'center' : 'space-between'}">
              <div>
                <span v-if="!scope.row.device.humidityRecord">-</span>
                <span v-else v-text="scope.row.currentHumidity.toFixed(1) + '%RH'"></span>
              </div>
              <div v-if="scope.row.device.humidityRecord">
                <el-tooltip content="该设备的湿度临界值" placement="top" :show-after="600">
                  <el-text type="info" v-text="scope.row.device.humidityThreshold.toFixed(1) + '%RH'"></el-text>
                </el-tooltip>
              </div>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="噪音监测" align="center">
        <template #default="scope">
          <div v-if="scope.row.device.shebeiStatus === '正常'">
            <div style="display: flex; align-items: center;" :style="{'justify-content': (!scope.row.device.noiseRecord) ? 'center' : 'space-between'}">
              <div>
                <span v-if="!scope.row.device.noiseRecord">-</span>
                <span v-else v-text="scope.row.currentNoise.toFixed(1) + 'dB'"></span>
              </div>
              <div v-if="scope.row.device.noiseRecord">
                <el-tooltip content="该设备的噪音临界值" placement="top" :show-after="600">
                  <el-text type="info" v-text="scope.row.device.noiseThreshold.toFixed(1) + 'dB'"></el-text>
                </el-tooltip>
              </div>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="电压监测" align="center">
        <template #default="scope">
          <div v-if="scope.row.device.shebeiStatus === '正常'">
            <div style="display: flex; align-items: center;" :style="{'justify-content': (!scope.row.device.voltageRecord) ? 'center' : 'space-between'}">
              <div>
                <span v-if="!scope.row.device.voltageRecord">-</span>
                <span v-else v-text="scope.row.currentVoltage.toFixed(1) + 'V'"></span>
              </div>
              <div v-if="scope.row.device.voltageRecord">
                <el-tooltip content="该设备的电压临界值" placement="top" :show-after="600">
                  <el-text type="info" v-text="scope.row.device.voltageThreshold.toFixed(1) + 'V'"></el-text>
                </el-tooltip>
              </div>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column prop="overview" label="实时状态" align="center">
        <template #default="scope">
          <div v-if="scope.row.device.shebeiStatus === '正常'">
            <div class="device-status">
              <div v-if="!scope.row.device.temperatureRecord && !scope.row.device.humidityRecord && !scope.row.device.noiseRecord && !scope.row.device.voltageRecord">
                <div class="green-dot"></div>
              </div>
              <div v-else>
                <div v-show="scope.row.device.shebeiStatus === '正常' && scope.row.status === '健康'" class="green-dot"></div>
                <div v-show="scope.row.device.shebeiStatus === '正常' && scope.row.status === '亚健康'" class="purple-dot"></div>
                <div v-show="scope.row.device.shebeiStatus === '故障'" class="red-dot"></div>
              </div>
            </div>
          </div>
          <div v-else>
            <div class="device-status">
              <div class="red-dot"></div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" >
        <template #default="scope">
          <el-button v-if="scope.row.device.shebeiStatus === '正常' && scope.row.device.voltageRecord" type="info" plain @click="handleOpenDialog(scope.row)">设备运行记录</el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <template #empty>
        <div style="min-height: 300px" v-loading="loadingStatus" element-loading-text="加载中"/>
        <el-empty v-if="!loadingStatus && tableData.length === 0" description="请添加设备后重试"/>
      </template>
    </el-table>
  </div>
  <div v-if="!loadingStatus && tableData.length != 0" class="device-status" style="margin-top: 8px; float: right;">
    注：<span class="green-dot" style="margin-right: 2px;"></span>健康状态&emsp;
    <span class="purple-dot" style="margin-right: 2px;"></span>亚健康状态&emsp;
    <span class="red-dot" style="margin-right: 2px;"></span>故障或损坏状态
  </div>

  <el-dialog
          v-model="dialogVisible"
          :title="dialogTitle"
          width="40%"
          @open="handleOpenEvent('today')"
          destroy-on-close
          align-center
          :close-on-click-modal="false"
          @before-close="handleCloseOuterDialogBefore()"
  >
    <el-tabs type="border-card" @tab-change="handleTabChange">
      <el-tab-pane label="今天">
        <el-table :data="dialogAlarms" style="width: 100%" height="250">
          <el-table-column prop="alarmTime" label="日期"></el-table-column>
          <el-table-column prop="alarmType" label="警报内容"></el-table-column>
          <template #append>
            <div v-if="dialogAlarms.length > 30" style="text-align: center; margin-top: 15px; color: #a9a9a9;">最多显示三十条记录</div>
          </template>
          <template #empty>
            <el-empty description="当前设备无该天警报记录"></el-empty>
          </template>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="昨天">
        <el-table :data="dialogAlarms" style="width: 100%" height="250">
          <el-table-column prop="alarmTime" label="日期"></el-table-column>
          <el-table-column prop="alarmType" label="警报内容"></el-table-column>
          <template #empty>
            <el-empty description="当前设备无该天警报记录"></el-empty>
          </template>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="前天">
        <el-table :data="dialogAlarms" style="width: 100%" height="250">
          <el-table-column prop="alarmTime" label="日期"></el-table-column>
          <el-table-column prop="alarmType" label="警报内容"></el-table-column>
          <template #empty>
            <el-empty description="当前设备无该天警报记录"></el-empty>
          </template>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="innerDialogVisible = true" :disabled="dialogDeviceObject.device.shebeiDispatch">
        <span v-if="!dialogDeviceObject.device.shebeiDispatch">派遣员工检查设备</span>
        <span v-else>已派遣员工检查设备</span>
      </el-button>
      <el-button @click="dialogVisible = false">关闭</el-button>
    </template>
    <el-dialog
            v-model="innerDialogVisible"
            width="30%"
            title="派遣员工检查设备"
            append-to-body
            align-center
            :close-on-click-modal="false"
            @open="handleOpenInnerDialogEvent()"
            @before-close="handleCloseInternalDialogBefore()"
    >
      <div style="display: flex; align-items: center;">
        <div style="font-size: 15px;width: 100px;">选择员工：</div>
        <el-select v-model="staffSelected" placeholder="请选择派遣的员工">
          <el-option
                  v-for="staff in staffs"
                  :key="staff.id"
                  :label="staff.realName + '（' + staff.userText + '）'"
                  :value="staff.id"
          />
        </el-select>
      </div>
      <div style="display: flex; align-items: center; margin-top: 8px;">
        <div style="font-size: 15px;width: 100px;">输入原因：</div>
        <el-input v-model="dispatchReason"></el-input>
      </div>
      <div style="margin: 15px 0 10px; display: flex; justify-content: end;">
        <el-button @click="handleDispatchStaff()" :loading="dispatchLoading">确定</el-button>
        <el-button @click="innerDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</div>
</body>
<script>
  const { createApp } = Vue

  const App = {
    data() {
      return {
        loadingStatus: false,
        tableData: [],
        dialogVisible: false,
        innerDialogVisible: false,
        dialogDeviceObject: {},
        dialogAlarms: [],
        dialogTitle: "",
        filter: "",
        staffs: [],
        staffSelected: '',
        dispatchReason: '',
        dispatchLoading: false,
      };
    },
    methods: {
      getDevicesStatus() {
        this.loadingStatus = true;
        setTimeout(() => {
          axios.get('/MonitorServlet')
                  .then(response => {
                    this.tableData = [];
                    let tempArray = response.data;
                    if (tempArray instanceof Array) {
                      for (let i = 0; i < tempArray.length; i++) {
                        if (tempArray[i].device.temperatureRecord && tempArray[i].currentTemperature > tempArray[i].device.temperatureThreshold) {
                          tempArray[i].status = "亚健康";
                        } else if (tempArray[i].device.humidityRecord && tempArray[i].currentHumidity > tempArray[i].device.humidityThreshold) {
                          tempArray[i].status = "亚健康";
                        } else if (tempArray[i].device.noiseRecord && tempArray[i].currentNoise > tempArray[i].device.noiseThreshold) {
                          tempArray[i].status = "亚健康";
                        } else tempArray[i].status = "健康";

                        this.tableData[i] = tempArray[i];
                      }
                      console.log(this.tableData)
                    }
                    this.loadingStatus = false;
                  }).catch(error => {
                    console.log(error);
                    this.loadingStatus = false;
                  });
        }, 500)
      },
      getDevicesStatusInterval() {
        setInterval(() => {
          axios.get('/MonitorServlet')
                  .then(response => {
                    let tempArray = response.data;
                    if (tempArray instanceof Array) {
                      for (let i = 0; i < tempArray.length; i++) {
                        if (tempArray[i].device.temperatureRecord && tempArray[i].currentTemperature > tempArray[i].device.temperatureThreshold) {
                          tempArray[i].status = "亚健康";
                        } else if (tempArray[i].device.humidityRecord && tempArray[i].currentHumidity > tempArray[i].device.humidityThreshold) {
                          tempArray[i].status = "亚健康";
                        } else if (tempArray[i].device.noiseRecord && tempArray[i].currentNoise > tempArray[i].device.noiseThreshold) {
                          tempArray[i].status = "亚健康";
                        } else tempArray[i].status = "健康";

                        this.tableData[i].currentTemperature = tempArray[i].currentTemperature;
                        this.tableData[i].currentHumidity = tempArray[i].currentHumidity;
                        this.tableData[i].currentNoise = tempArray[i].currentNoise;
                        this.tableData[i].currentVoltage = tempArray[i].currentVoltage;
                        this.tableData[i].status = tempArray[i].status;
                      }
                    }
                  }).catch(error => {
                    this.loadingStatus = false;
                  });
        }, 2000)
      },
      handleOpenDialog(obj) {
        this.dialogVisible = true;
        this.dialogDeviceObject = obj;
        this.dialogTitle =  '设备运行状况 - ' + this.dialogDeviceObject.device.shebeiName;
        this.filter = "today";
        console.log(this.dialogDeviceObject)
      },
      handleOpenEvent(filter) {
        axios.get('/MonitorServlet?action=deviceAlarm&deviceId=' + this.dialogDeviceObject.device.id + "&filter=" + filter)
                .then(response => {
                  this.dialogAlarms = response.data;
                }).catch(error => {
                  console.log(error);
                });
      },
      handleTabChange(tabIndex) {

        if (tabIndex == 0) {
          this.handleOpenEvent("today");
        } else if (tabIndex == 1) {
          this.handleOpenEvent("yesterday");
        } else this.handleOpenEvent("twoDaysAgo");
      },
      handleCloseOuterDialogBefore() {
        this.dialogDeviceObject = {};
        this.dialogTitle =  "";
        this.filter = "";
        this.dialogAlarms = [];
      },
      handleOpenInnerDialogEvent() {
        axios.get('/UserServlet?action=allStaff')
                .then(response => {
                  this.staffs = response.data;
                }).catch(error => {
                  console.log(error);
                });
      },
      handleCloseInternalDialogBefore() {
        this.staffs = [];
      },
      handleDispatchStaff() {
        this.dispatchLoading = true;
        setTimeout(() => {
          axios.post("/MonitorServlet?action=dispatch&uid=" + this.staffSelected + "&deviceId=" + this.dialogDeviceObject.device.id + "&reason=" + this.dispatchReason).then(response => {
            console.log(response)
            this.$message({
              message: '派遣员工成功',
              type: 'success'
            });
            this.dialogDeviceObject.device.shebeiDispatch = true;
            this.innerDialogVisible = false;
            this.dispatchLoading = false;
          }).catch(error => {
            console.log(error);
            this.dispatchLoading = false;
          })
        }, 500)
      }
    },
    mounted() {
      this.getDevicesStatus()
    },
    watch: {
      tableData: function (New, Old) {
        if (New.length > 0) {
          this.getDevicesStatusInterval()
        }
      }
    }
  };

  const app = Vue.createApp(App);
  app.use(ElementPlus);
  app.mount("#app");
</script>
</html>

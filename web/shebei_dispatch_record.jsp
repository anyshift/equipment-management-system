<%@ page import="com.demo.vo.User" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备警报派遣记录</title>
    <%@ include file="include/head.jsp" %>
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 30%;
        }

        @media screen and (max-width: 992px) {
            .modal-content {
                width: 50% !important;
            }
        }
    </style>
    <script>
        const voId = 0;
        function openModal(deviceId) {
            document.getElementById("myModal").style.display = "block";
            this.voId = deviceId;
        }

        // 关闭弹出窗口
        function closeModal() {
            document.getElementById("myModal").style.display = "none";
        }

        function updateFaultStatus() {
            var radios = document.getElementsByName('status');
            var isChecked = false;
            var checked = '';
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked) {
                    isChecked = true;
                    checked = radios[i].value;
                    break;
                }
            }
            if (isChecked) {
                window.location.href='ShebeiServlet?action=updateShebeiFaultStatus&id=' + this.voId + '&status=' + checked;
                closeModal();
            } else {
                alert("请选择一个设备状态选项");
            }
        }

        function getValue() {
            var radios = document.getElementsByName('status');

            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked) {
                    console.log('选中的值是：', radios[i].value);
                    break;
                }
            }
        }
    </script>
</head>
<body>
<div class="container-fluid" id="app">
    <ul class="nav nav-tabs">
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="ShebeiServlet?action=list">设备列表</a></li></c:if>
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="ShebeiServlet?action=reportRecord">故障设备上报记录</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li class="active"><a href="shebei_dispatch_record.jsp">设备检查记录</a></li></c:if>
        <c:if test="${loginUser.userType == '普通员工'}"><li class="active"><a href="shebei_dispatch_record.jsp">我的任务</a></li></c:if>
    </ul>
    <br/>
    <el-table :data="tableData" border style="width: 100%">
        <el-table-column label="设备名称" align="center">
            <template #default="scope">
                <a :href="'ShebeiServlet?action=get&id=' + scope.row.device.id"><span v-text="scope.row.device.shebeiName"></span></a>
            </template>
        </el-table-column>
        <el-table-column label="设备不稳定工作原因" align="center">
            <template #default="scope">
                <span v-text="scope.row.reason"></span>
            </template>
        </el-table-column>
        <el-table-column label="任务下发时间" align="center">
            <template #default="scope">
                <span v-text="scope.row.createTime"></span>
            </template>
        </el-table-column>
        <el-table-column :label="loginUserType === '管理员' ? '当前任务状态' : '操作'" align="center">
            <template #default="scope">
                <div v-if="loginUserType === '管理员'">
                    <span v-if="scope.row.device.shebeiDispatch">待处理</span>
                    <span v-else>已处理，处理结果为：{{ scope.row.finishedText }}</span>
                </div>
                <div v-else>
                    <div v-if="scope.row.finished">-</div>
                    <div v-else>
                        <el-button type="success" plain @click="markDeviceOK(scope.row)">完结任务</el-button>
                        <el-button type="danger" plain @click="markDeviceBad(scope.row)">故障上报</el-button>
                    </div>
                </div>
            </template>
        </el-table-column>
        <template #empty>
            <div style="min-height: 300px" v-loading="loadingStatus" element-loading-text="加载中"/>
            <el-empty v-if="!loadingStatus && tableData.length === 0" description="还没有任务，或所有任务已处理完成"/>
        </template>
        <template #append>
            <el-empty v-if="tableData.length > 0 && tableData.length < 10"
                      :description="loginUserType === '管理员' ? '没有更多记录了' : '没有更多任务了'"></el-empty>
        </template>
    </el-table>
</div>
</body>
<script>
    const { createApp } = Vue

    const App = {
        data() {
            return {
                tableData: [],
                loadingStatus: false,
                loginUserType: {},
            };
        },
        methods: {
            getDispatchRecords() {
                this.loadingStatus = true;
                let loginUserType = "<%= ((User)session.getAttribute("loginUser")).getUserType() %>";
                let uri = "";
                if (loginUserType === '管理员') {
                    uri = '/MonitorServlet?action=dispatchRecords&uid=' + <%= ((User)session.getAttribute("loginUser")).getId() %> + '&userType=admin';
                } else uri = uri = '/MonitorServlet?action=dispatchRecords&uid=' + <%= ((User)session.getAttribute("loginUser")).getId() %> + '&userType=staff';
                setTimeout(() => {
                    axios.get(uri)
                        .then(response => {
                            this.tableData = response.data;
                            this.loadingStatus = false;
                            console.log(response)
                        }).catch(error => {
                            console.log(error)
                            this.loadingStatus = false;
                        })
                }, 500)
            },
            markDeviceOK(deviceDispatch) {
                this.$confirm('确认该设备无需修理吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.put('/MonitorServlet?action=markDeviceOK&deviceId=' + deviceDispatch.deviceId)
                        .then(response => {
                            console.log(response);
                            deviceDispatch.finished = true;
                            deviceDispatch.finishedText = "无需修理";
                            this.$message({
                                type: 'success',
                                message: '已标记该设备为正常状态'
                            });
                        }).catch(error => {
                            console.log(error)
                        })
                }).catch(() => {});
            },
            markDeviceBad(deviceDispatch) {
                this.$prompt('请输入故障原因', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    axios.put('/MonitorServlet?action=markDeviceBad&deviceId=' + deviceDispatch.deviceId + '&reason=' + value)
                        .then(response => {
                            console.log(response)
                            deviceDispatch.finished = true;
                            deviceDispatch.finishedText = "已上报故障";
                            this.$message({
                                type: 'success',
                                message: '故障原因是: ' + value
                            });
                        }).catch(error => {
                            console.log(error)
                        })
                }).catch(() => {});
            }
        },
        mounted() {
            this.getDispatchRecords();
            this.loginUserType = "<%= ((User)session.getAttribute("loginUser")).getUserType() %>";
        }
    };

    const app = Vue.createApp(App);
    app.use(ElementPlus);
    app.mount("#app");
</script>
</html>
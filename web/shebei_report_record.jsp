<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备故障上报记录</title>
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
        <li><a href="ShebeiServlet?action=list">设备列表</a></li>
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
        <li class="active"><a href="#">设备故障上报记录</a></li>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="shebei_dispatch_record.jsp">设备检查记录</a></li></c:if>
    </ul>
    <br/>
    <c:if test="${empty list}"><p style="text-align: center;"><el-empty description="还没有上报记录，或所有记录已处理完毕"></el-empty></p></c:if>
    <c:if test="${!empty list}">
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th style="text-align: center">编号</th>
                <th style="text-align: center">名称</th>
                <th style="text-align: center">当前状态</th>
                <th style="text-align: center">故障原因</th>
                <th style="text-align: center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="vo">
                <tr>
                    <td style="text-align: center;vertical-align: middle;">${vo.id}</td>
                    <td style="text-align: center;vertical-align: middle;"><a href="ShebeiServlet?action=get&id=${vo.id}">${vo.name}</a></td>
                    <td style="text-align: center;vertical-align: middle;">${vo.status}</td>
                    <td style="text-align: center;vertical-align: middle;">${vo.reason}</td>
                    <td style="text-align: center;vertical-align: middle;">
                        <button onclick="openModal(${vo.id})" class="btn btn-pill btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改状态</button>

                        <div id="myModal" class="modal">
                            <div class="modal-content">
                                <div style="display: flex; justify-content: space-between;">
                                    <div style="text-align: left; font-size: 20px; font-weight: bold;">更改设备状态</div>
                                    <div onclick="closeModal()" style="float: right; cursor: pointer;">关闭</div>
                                </div>
                                <form style="text-align: left;">
                                    <br>
                                    <div style="margin-bottom: 15px;">
                                        <label>
                                            <input type="radio" name="status" value="正常"> 正常 &emsp;
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="故障"> 故障 &emsp;
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="损坏"> 损坏 &emsp;
                                        </label>
                                    </div>
                                    <button type="button" class="btn btn-pill btn-primary btn-sm" style="text-align: left;" onclick="updateFaultStatus()">提交</button>
                                    &nbsp;
                                    <button type="button" class="btn btn-pill btn-primary btn-sm" style="text-align: left;" onclick="closeModal()">取消</button>
                                </form>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
<script>
    const { createApp } = Vue

    const App = {
        data() {
            return {

            };
        },
    };

    const app = Vue.createApp(App);
    app.use(ElementPlus);
    app.mount("#app");
</script>
</html>

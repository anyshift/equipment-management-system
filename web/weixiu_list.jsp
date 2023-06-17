<%@ page import="com.demo.vo.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>维修管理</title>
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

        .styled-input {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            outline: none;
            margin-bottom: 15px;
            width: 100%;
            transition: border-color 0.2s ease-in-out;
        }

        .styled-input:focus {
            border-color: dodgerblue;
        }

        .custom-select {
            position: relative;
            display: inline-block;
            width: 200px;
            height: 40px;
            background-color: #f1f1f1;
            border-radius: 4px;
            overflow: hidden;
            margin-bottom: -5px;
        }

        .custom-select select {
            width: 100%;
            height: 100%;
            padding: 10px;
            border: none;
            outline: none;
            background-color: transparent;
            -webkit-appearance: none; /* 移除默认样式 */
            -moz-appearance: none;
            appearance: none;
        }

        .custom-select select:focus {
            outline: none;
        }

        .custom-select::after {
            content: '\25BC';
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            color: #888;
            pointer-events: none;
        }
    </style>
    <script>
        const voId = 0;
        function openAcceptTaskModal(deviceId) {
            document.getElementById("acceptTaskModal").style.display = "block";
            this.voId = deviceId;
            console.log(this.voId)
        }
        function openTransferTaskModal(deviceId) {
            document.getElementById("transferTaskModal").style.display = "block";
            this.voId = deviceId;
        }
        function openTaskFinishedModal(deviceId) {
            document.getElementById("taskFinishedModal").style.display = "block";
            this.voId = deviceId;
        }
        function openDeviceDestroyedModal(deviceId) {
            document.getElementById("deviceDestroyedModal").style.display = "block";
            this.voId = deviceId;
        }

        function closeAcceptTaskModal() {
            document.getElementById("acceptTaskModal").style.display = "none";
        }
        function closeTransferTaskModal() {
            document.getElementById("transferTaskModal").style.display = "none";
        }
        function closeTaskFinishedModal() {
            document.getElementById("taskFinishedModal").style.display = "none";
        }
        function closeDeviceDestroyedModal() {
            document.getElementById("deviceDestroyedModal").style.display = "none";
        }

        function submitAcceptTask() {
            window.location.href='WeixiuServlet?action=acceptTask&deviceId=' + this.voId;
            closeAcceptTaskModal();
        }
        function submitTaskFinished() {
            let price = document.getElementById("maintenance-price").value;
            if (price === "") {
                alert("请输入维修费用");
            } else {
                window.location.href='WeixiuServlet?action=taskFinished&deviceId=' + this.voId + '&price=' + price;
                closeTaskFinishedModal();
            }
        }
        function submitTransferTask() {
            let repairerBox = document.getElementById("repairerBox");
            let selectedRepairerId = repairerBox.value;
            window.location.href='WeixiuServlet?action=transferTask&uid=' + selectedRepairerId + '&deviceId=' + this.voId;
            closeTransferTaskModal();
        }
        function submitDeviceDestroyed() {
            window.location.href='WeixiuServlet?action=deviceDestroyed&deviceId=' + this.voId;
            closeDeviceDestroyedModal();
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active"><a href="WeixiuServlet?action=list">
            <c:choose>
                <c:when test="${loginUser.userType == '管理员'}">维修列表</c:when>
                <c:when test="${loginUser.userType == '维修工'}">我的维修任务</c:when>
            </c:choose></a></li>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="weixiu_add.jsp">添加</a></li></c:if>
    </ul>
    <br/>
    <c:if test="${empty list}"><p style="text-align: center;">还没有维修记录</p></c:if>
    <c:if test="${!empty list}">
        <form class="form-inline" id="searchForm" action="WeixiuServlet?action=list" method="post">
            <div class="form-group">
                <input type="text" class="form-control" name="keyword" id="keyword" placeholder="设备名称">
                <input type="hidden" id="searchColumn" name="searchColumn" value="weixiu_name"/>
            </div>
            <button class="btn btn-pill btn-danger btn-sm"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
        </form>
        <br/>
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th style="text-align: center">设备编号</th>
                <th style="text-align: center">故障设备名称</th>
                <th style="text-align: center">故障原因</th>
                <th style="text-align: center">维修费用</th>
                <c:if test="${loginUser.userType == '管理员'}"><th style="text-align: center">维修人</th></c:if>
                <c:if test="${loginUser.userType == '管理员'}"><th style="text-align: center">维修人电话</th></c:if>
                <th style="text-align: center">状态</th>
                <th style="text-align: center">最新状况</th>
                <th style="text-align: center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="vo">
                <tr>
                    <td style="text-align: center">${vo.weixiuNo}</td>
                    <td style="text-align: center"><a href="WeixiuServlet?action=get&id=${vo.id}">${vo.weixiuName}</a></td>
                    <td style="text-align: center">${vo.weixiuGuzhang}</td>
                    <td style="text-align: center">${vo.weixiuFeiyong}</td>
                    <c:if test="${loginUser.userType == '管理员'}"><td style="text-align: center">${vo.weixiuRen}</td></c:if>
                    <c:if test="${loginUser.userType == '管理员'}"><td style="text-align: center">${vo.weixiuPhone}</td></c:if>
                    <td style="text-align: center; <c:if test="${vo.weixiuStatus == '待维修' || vo.weixiuStatus == '正在维修' || vo.weixiuStatus == '维修完成'}">color: green;</c:if>
                            <c:if test="${vo.weixiuStatus == '损坏'}">color: orangered;</c:if>">
                            ${vo.weixiuStatus}
                    </td>
                    <td title="${vo.weixiuText}" style="text-align: center">
                        <c:choose>
                            <c:when test="${fn:length(vo.weixiuText) > 19}">
                                <c:out value="${fn:substring(vo.weixiuText, 0, 19)}..."/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${vo.weixiuText}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align: center">
                        <c:choose>
                            <c:when test="${loginUser.userType == '管理员'}">
                                <button onclick="window.location.href='WeixiuServlet?action=editPre&id=${vo.id}'"
                                        class="btn btn-pill btn-danger btn-xs"
                                        <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if>
                                >
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button onclick="if(window.confirm('将要删除：${vo.weixiuName}？'))window.location.href='WeixiuServlet?action=delete&id=${vo.id}'"
                                        class="btn btn-pill btn-warning btn-xs"
                                        <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if> >
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    删除
                                </button>
                            </c:when>
                            <c:when test="${loginUser.userType == '维修工' && vo.weixiuStatus == '待维修'}">
                                <button onclick="openAcceptTaskModal(${vo.weixiuNo})"
                                        class="btn btn-pill btn-success btn-xs">
                                    <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                    接受任务
                                </button>
                                <button onclick="openTransferTaskModal(${vo.weixiuNo})"
                                        class="btn btn-pill btn-info btn-xs">
                                    <span class="glyphicon glyphicon-share" aria-hidden="true"></span>
                                    转移任务
                                </button>
                                <div id="acceptTaskModal" class="modal">
                                    <div class="modal-content">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">温馨提示</div>
                                            <div onclick="closeAcceptTaskModal()" style="float: right; cursor: pointer;">关闭</div>
                                        </div>
                                        <p style="margin-bottom: 0">确定接受该维修任务？</p>
                                        <br>
                                        <button onclick="submitAcceptTask()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;">确定</button>
                                        <button onclick="closeAcceptTaskModal()" class="btn btn-pill btn-primary btn-sm">关闭</button>
                                    </div>
                                </div>
                                <div id="transferTaskModal" class="modal">
                                    <div class="modal-content">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">请选择转移对象</div>
                                            <div onclick="closeTransferTaskModal()" style="float: right; cursor: pointer;">关闭</div>
                                        </div>
                                        <div class="custom-select">
                                            <select id="repairerBox">
                                                <option value="">请选择转移对象</option>
                                                <%
                                                    List<User> workers = (List<User>) session.getAttribute("workers");
                                                    if (workers != null) {
                                                        for (User worker : workers) {

                                                %>
                                                <option value="<%= worker.getId() %>"><%= worker.getRealName() + " - " + worker.getUserText() %></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <br><br>
                                        <button onclick="submitTransferTask()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;">提交</button>
                                        <button onclick="closeTransferTaskModal()" class="btn btn-pill btn-primary btn-sm">关闭</button>
                                    </div>
                                </div>
                            </c:when>
                            <c:when  test="${loginUser.userType == '维修工' && vo.weixiuStatus == '正在维修'}">
                                <button onclick="openTaskFinishedModal(${vo.weixiuNo})"
                                        class="btn btn-pill btn-success btn-xs">
                                    <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                    标记完成
                                </button>
                                <button onclick="openDeviceDestroyedModal(${vo.weixiuNo})"
                                        class="btn btn-pill btn-danger btn-xs">
                                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                    标记损坏
                                </button>
                                <div id="taskFinishedModal" class="modal">
                                    <div class="modal-content">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">温馨提示</div>
                                            <div onclick="closeTaskFinishedModal()" style="float: right; cursor: pointer;">关闭</div>
                                        </div>
                                        <input type="number" id="maintenance-price" class="styled-input" placeholder="请输入维修费用">
                                        <br>
                                        <button onclick="submitTaskFinished()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;">确定</button>
                                        <button onclick="closeTaskFinishedModal()" class="btn btn-pill btn-primary btn-sm">关闭</button>
                                    </div>
                                </div>
                                <div id="deviceDestroyedModal" class="modal">
                                    <div class="modal-content">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">温馨提示</div>
                                            <div onclick="closeDeviceDestroyedModal()" style="float: right; cursor: pointer;">关闭</div>
                                        </div>
                                        <p>确定要将该设备标记为损坏？</p>
                                        <button onclick="submitDeviceDestroyed()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;">是</button>
                                        <button onclick="closeDeviceDestroyedModal()" class="btn btn-pill btn-primary btn-sm">否</button>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${loginUser.userType == '维修工' && vo.weixiuStatus == '维修完成'}">
                                -
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div style="float: right;padding-right: 10px;color: #515151;"><jsp:include page="split.jsp"/></div>
    </c:if>
</div>
</body>
</html>

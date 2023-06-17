<%@ page import="java.util.List" %>
<%@ page import="com.demo.vo.User" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备管理</title>
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
        function openFaultReasonModal(deviceId) {
            document.getElementById("faultReasonModal").style.display = "block";
            this.voId = deviceId;
        }
        function openDispatchRepairerModal(deviceId) {
            document.getElementById("dispatchRepairerModal").style.display = "block";
            this.voId = deviceId;
        }

        function closeFaultReasonModal() {
            document.getElementById("faultReasonModal").style.display = "none";
        }
        function closeDispatchRepairerModal() {
            document.getElementById("dispatchRepairerModal").style.display = "none";
        }

        function submitFaultReport() {
            let faultReason = document.getElementById("faultReason").value;
            window.location.href='ShebeiServlet?action=faultReport&id=' + this.voId + '&faultReason=' + faultReason;
            closeFaultReasonModal();
        }

        function submitDispatchRepairer() {
            let repairerBox = document.getElementById("repairerBox");
            let selectedRepairerId = repairerBox.value;
            window.location.href='ShebeiServlet?action=dispatchRepairer&uid=' + selectedRepairerId + '&deviceId=' + this.voId;
            closeDispatchRepairerModal();
        }
    </script>
</head>
<body>
<div class="container-fluid" id="app">
    <ul class="nav nav-tabs">
        <li class="active"><a href="ShebeiServlet?action=list">设备列表</a></li>
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="ShebeiServlet?action=reportRecord">故障设备上报记录</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="shebei_dispatch_record.jsp">设备检查记录</a></li></c:if>
    </ul>
    <br/>
    <div style="display: flex; justify-content: space-between;">
        <form class="form-inline" id="searchForm" action="ShebeiServlet?action=list" method="post">
            <div class="form-group" style="margin-right: 5px;">
                <input type="text" class="form-control" name="keyword" id="keyword" placeholder="名称或编号">
                <input type="hidden" id="searchColumn" name="searchColumn" value="shebei_name"/>
            </div>
            <button class="btn btn-pill btn-danger btn-sm"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</button>
        </form>
        <div class="form-group">
            <button class="btn btn-pill btn-success btn-sm" onclick="window.location.href='/shebei_add.jsp'">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加设备
            </button>
        </div>
    </div>
    <br/>
    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th style="text-align: center; vertical-align: middle; width: 150px;">名称</th>
            <th style="text-align: center; vertical-align: middle; width: 60px;">外观</th>
            <th style="text-align: center; vertical-align: middle;">编号</th>
            <th style="text-align: center; vertical-align: middle;">类型</th>
            <th style="text-align: center; vertical-align: middle;">生产厂家</th>
            <th style="text-align: center; vertical-align: middle;">采购日期</th>
            <th style="text-align: center; vertical-align: middle;">采购价格</th>
            <th style="text-align: center; vertical-align: middle;">使用部门</th>
            <th style="text-align: center; vertical-align: middle;">状态</th>
            <th style="text-align: center; vertical-align: middle;">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="vo">
            <tr>
                <td style="text-align: center; vertical-align: middle;"><a href="ShebeiServlet?action=get&id=${vo.id}">${vo.shebeiName}</a></td>
                <td style="text-align: center; vertical-align: middle;">
                    <%--<img style="max-width: 100px;" src="${vo.shebeiImage}" />--%>
                    <el-image style="width: 60px; height: 60px;" src="${vo.shebeiImage}" :zoom-rate="1.2" :preview-src-list="['${vo.shebeiImage}']" :initial-index="4" :hide-on-click-modal="true"></el-image>
                </td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiNo}</td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiType}</td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiCangjia}</td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiIndate}</td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiInprice}</td>
                <td style="text-align: center; vertical-align: middle;">${vo.shebeiDept}</td>
                <td style="text-align: center; vertical-align: middle;;<c:if test="${vo.shebeiStatus == '故障' || vo.shebeiStatus == '损坏'}">color: #d9534f;</c:if>
                <c:if test="${vo.shebeiStatus == '待维修'}">color: green;</c:if>">${vo.shebeiStatus}</td>

                <td style="text-align: center; vertical-align: middle;">
                    <c:choose>
                        <c:when test="${loginUser.userType == '管理员'}">
                            <c:choose>
                                <c:when test="${vo.shebeiStatus == '故障'}">
                                    <button onclick="openDispatchRepairerModal(${vo.id})"
                                            class="btn btn-pill btn-success btn-xs" style="margin-right: 5px;"
                                            <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if>
                                    >
                                        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
                                        派遣维修工
                                    </button>

                                    <div id="dispatchRepairerModal" class="modal">
                                        <div class="modal-content">
                                            <div style="display: flex; justify-content: space-between;">
                                                <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">选择维修工</div>
                                                <div onclick="closeDispatchRepairerModal()" style="float: right; cursor: pointer;">关闭</div>
                                            </div>
                                            <div class="custom-select">
                                                <select id="repairerBox">
                                                    <option value="">请选择维修工</option>
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
                                            <button onclick="submitDispatchRepairer()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;margin-right: 5px;">提交</button>
                                            <button onclick="closeDispatchRepairerModal()" class="btn btn-pill btn-primary btn-sm">关闭</button>
                                        </div>
                                    </div>
                                    <button onclick="window.location.href='ShebeiServlet?action=editPre&id=${vo.id}'"
                                            class="btn btn-pill btn-danger btn-xs" style="margin-right: 5px;"
                                            <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if>
                                    >
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        编辑
                                    </button>
                                </c:when>
                                <c:when test="${vo.shebeiStatus == '损坏'}">-</c:when>
                                <c:when test="${vo.shebeiStatus == '待维修'}">
                                    <button onclick="window.location.href='ShebeiServlet?action=editPre&id=${vo.id}'"
                                            class="btn btn-pill btn-danger btn-xs" style="margin-right: 5px; margin-left: 5px;"
                                            <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if>
                                    >
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        编辑
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="window.location.href='ShebeiServlet?action=editPre&id=${vo.id}'"
                                            class="btn btn-pill btn-danger btn-xs" style="margin-right: 5px; margin-left: 5px;"
                                            <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if>
                                    >
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        编辑
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${vo.shebeiStatus == '损坏'}"></c:when>
                                <c:otherwise>
                                    <button onclick="if(window.confirm('将要删除：${vo.shebeiName}？'))window.location.href='ShebeiServlet?action=delete&id=${vo.id}'"
                                            class="btn btn-pill btn-warning btn-xs"
                                            <c:if test="${loginUser.userType != '管理员'}">disabled="disabled" title="没有权限！！！"</c:if> >
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        删除
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <%--<button onclick="window.location.href='ShebeiServlet?action=faultReport&id=${vo.id}'"
                                    class="btn btn-pill btn-danger btn-xs">
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                故障上报
                            </button>--%>
                            <c:choose>
                                <c:when test="${vo.shebeiStatus == '正常'}">
                                    <button onclick="openFaultReasonModal(${vo.id})"
                                            class="btn btn-pill btn-danger btn-xs"
                                            <c:if test="${vo.shebeiReport}">disabled="disabled"</c:if>
                                    >
                                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                        <c:choose>
                                            <c:when test="${vo.shebeiReport}">已上报故障</c:when>
                                            <c:otherwise>故障上报</c:otherwise>
                                        </c:choose>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>

                            <div id="faultReasonModal" class="modal">
                                <div class="modal-content">
                                    <div style="display: flex; justify-content: space-between;">
                                        <div style="text-align: left; font-size: 20px; font-weight: bold;margin-bottom: 15px;">故障原因</div>
                                        <div onclick="closeFaultReasonModal()" style="float: right; cursor: pointer;">关闭</div>
                                    </div>
                                    <textarea id="faultReason" rows="4" cols="50" style="width: 100%;"></textarea>
                                    <br><br>
                                    <button onclick="submitFaultReport()" class="btn btn-pill btn-primary btn-sm" style="text-align: left;">提交</button>
                                    <button onclick="closeFaultReasonModal()" class="btn btn-pill btn-primary btn-sm">关闭</button>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="float: right;padding-right: 10px;color: #515151;margin-bottom: 50px;"><jsp:include page="split.jsp"/></div>
</div>
</body>
<script>
    const { createApp } = Vue

    const App = {
        data() {
            return {

            };
        },
        mounted() {
            let unhandledWorks = "<%= session.getAttribute("unhandledWorks") %>";
            let targetUid = "<%= session.getAttribute("targetUid") %>";
            let loginUid = "<%= ((User)session.getAttribute("loginUser")).getId() %>";
            if (unhandledWorks > 0 && targetUid === loginUid) {
                this.$notify.warning({
                    title: '消息提示',
                    dangerouslyUseHTMLString: true,
                    message: "有 " + unhandledWorks +" 台设备待检查，" +
                        "<a href='shebei_dispatch_record.jsp'>去浏览任务</a>",
                    duration: 0,
                });
            }
        }
    };

    const app = Vue.createApp(App);
    app.use(ElementPlus);
    app.mount("#app");
</script>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备详情</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<div class="container-fluid" id="app">
    <ul class="nav nav-tabs">
        <li><a href="ShebeiServlet?action=list">设备列表</a></li>
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="ShebeiServlet?action=reportRecord">故障设备上报记录</a></li></c:if>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="shebei_dispatch_record.jsp">设备检查记录</a></li></c:if>
        <c:if test="${loginUser.userType == '普通员工'}"><li><a href="shebei_dispatch_record.jsp">我的任务</a></li></c:if>
        <li class="active"><a href="#">设备详情</a></li>
    </ul>
    <br/>
    <form class="form-horizontal" style="width: 100%; margin-bottom: 0;" role="form" action="#" method="post">
        <input type="hidden" class="form-control" id="id" name="id" value="${vo.id}"/>
        <div class="form-group">
            <label class="col-sm-3 control-label">名称：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiName}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">编号：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiNo}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">类型：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiType}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">生产厂家：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiCangjia}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">采购日期：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiIndate}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">采购价格：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiInprice}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">使用部门：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiDept}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备当前状态：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.shebeiStatus}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备图片：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                <el-image style="width: 100px; height: 100px;" src="${vo.shebeiImage}" :zoom-rate="1.2" :preview-src-list="['${vo.shebeiImage}']" :initial-index="4" :hide-on-click-modal="true"></el-image>
            </div>
        </div>
        <div class="form-group" style="margin-bottom: 0;">
            <label class="col-sm-3 control-label"></label>
            <div class="col-sm-5" style="padding-top: 7px;">
                <input type="button" class="btn btn-pill btn-success btn-sm" value="返回" onclick="javascript:history.back(-1);">
            </div>
        </div>
    </form>
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

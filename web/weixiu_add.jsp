<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>维修添加</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="WeixiuServlet?action=list">维修列表</a></li>
        <li class="active"><a href="#">添加</a></li>
    </ul>
    <br/>
    <form class="form-horizontal" role="form" action="WeixiuServlet?action=add" method="post" onsubmit="return check()">
        <div class="form-group">
            <label class="col-sm-3 control-label">设备编号：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuNo" name="weixiuNo">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备名称：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuName" name="weixiuName">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">故障：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuGuzhang" name="weixiuGuzhang">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修费用：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuFeiyong" name="weixiuFeiyong">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修日期：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuDate" name="weixiuDate">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修人：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuRen" name="weixiuRen">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修人电话：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="weixiuPhone" name="weixiuPhone">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">状态：</label>
            <div class="col-sm-5">
                <input name="weixiuStatus" type="radio" value="已修好" checked="checked"/>&nbsp;&nbsp;&nbsp;已修好&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="weixiuStatus" type="radio" value="待维修"/>&nbsp;&nbsp;&nbsp;待维修&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">备注：</label>
            <div class="col-sm-5">
                <textarea rows="3" class="form-control" id="weixiuText" name="weixiuText" placeholder="请输入内容......"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"></label>
            <div class="col-sm-5">
                <input type="submit" class="btn btn-pill btn-primary btn-sm" value="保存">
                <input type="button" class="btn btn-pill btn-success btn-sm" value="返回" onclick="javascript:history.back(-1);">
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    //提交之前进行检查，如果return false，则不允许提交
    function check() {
        //根据ID获取值
        if (document.getElementById("weixiuNo").value.trim().length == 0) {
            alert("设备编号不能为空!");
            return false;
        }
        if (document.getElementById("weixiuName").value.trim().length == 0) {
            alert("设备名称不能为空!");
            return false;
        }
        if (document.getElementById("weixiuGuzhang").value.trim().length == 0) {
            alert("故障不能为空!");
            return false;
        }
        if (document.getElementById("weixiuFeiyong").value.trim().length == 0) {
            alert("维修费用不能为空!");
            return false;
        }
        if (document.getElementById("weixiuDate").value.trim().length == 0) {
            alert("维修日期不能为空!");
            return false;
        }
        if (document.getElementById("weixiuRen").value.trim().length == 0) {
            alert("维修人不能为空!");
            return false;
        }
        if (document.getElementById("weixiuPhone").value.trim().length == 0) {
            alert("维修人电话不能为空!");
            return false;
        }
        return true;
    }
</script>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>维修详情</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="WeixiuServlet?action=list">用户列表</a></li>
        <li class="active"><a href="#">详情</a></li>
    </ul>
    <br/>
    <form class="form-horizontal" role="form" action="#" method="post">
        <input type="hidden" class="form-control" id="id" name="id" value="${vo.id}"/>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备编号：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuNo}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备名称：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuName}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">故障：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuGuzhang}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修费用：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuFeiyong}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修日期：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuDate}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修人：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuRen}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">维修人电话：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuPhone}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">状态：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                ${vo.weixiuStatus}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">备注：</label>
            <div class="col-sm-5" style="padding-top: 7px;">
                <textarea rows="3" class="form-control" id="weixiuText" name="weixiuText" disabled="disabled">${vo.weixiuText}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"></label>
            <div class="col-sm-5" style="padding-top: 7px;">
                <input type="button" class="btn btn-pill btn-success btn-sm" value="返回" onclick="javascript:history.back(-1);">
            </div>
        </div>
    </form>
</div>
</body>
</html>

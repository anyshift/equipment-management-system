<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>公告编辑</title>
    <%@ include file="include/head.jsp" %>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="NoticeServlet?action=list">公告列表</a></li>
        <li class="active"><a href="#">编辑</a></li>
    </ul>
    <br/>
    <form class="form-horizontal" role="form" action="NoticeServlet?action=edit" method="post" onsubmit="return check()">
        <input type="hidden" class="form-control" id="id" name="id" value="${vo.id}"/>
        
            <div class="form-group">
                <label class="col-sm-3 control-label">标题：</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="noticeName" name="noticeName" value="${vo.noticeName}">
                </div>
            </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">内容：</label>
            <div class="col-sm-5">
                <textarea rows="3" class="form-control" id="noticeText" name="noticeText" placeholder="请输入内容......">${vo.noticeText}</textarea>
            </div>
        </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">类型：</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="noticeType" name="noticeType" value="${vo.noticeType}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">创建时间：</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="createDate" name="createDate" value="${vo.createDate}">
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
        if (document.getElementById("noticeName").value.trim().length == 0) {
            alert("标题不能为空!");
            return false;
        }
        if (document.getElementById("noticeType").value.trim().length == 0) {
            alert("类型不能为空!");
            return false;
        }
        if (document.getElementById("createDate").value.trim().length == 0) {
            alert("创建时间不能为空!");
            return false;
        }
        return true;
    }
</script>
</html>

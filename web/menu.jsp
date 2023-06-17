<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>高速公路机电设备维护管理系统</title>
    <link rel="stylesheet" href="js/myCss.css">
    <script src="js/jquery.min.js"></script>
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
    <div class="logo margin-big-left fadein-top">
        <h1 style="text-shadow: 1px 2px #FFFFFF;color: #000000"><%--<img src="img/icon.jpg" class="radius-circle rotate-hover" height="50" alt=""/>--%>高速公路机电设备维护管理系统</h1>
    </div>
    <div class="head-l" style="float: right;margin-right: 20px;"><a class="button button-little bg-red" href="AuthServlet?action=logout"><span class="icon-power-off"></span> 退出</a></div>
</div>
<div class="leftnav">
    <div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>
    <h2><span class="icon-user"></span> 个人中心</h2>
    <ul>
        <li>
            <a href="reset_password.jsp" target="right"><span class="icon-caret-right"></span>密码修改</a>
        </li>
    </ul>

    <h2><span class="icon-th-list"></span>
        <c:choose>
            <c:when test="${loginUser.userType == '管理员'}">管理中心</c:when>
            <c:otherwise>状态中心</c:otherwise>
        </c:choose>
    </h2>
    <ul>
        <c:if test="${loginUser.userType == '管理员'}"><li><a href="UserServlet" target="right"><span class="icon-caret-right"></span> 用户管理</a></li></c:if>
        <li>
            <a href="ShebeiServlet" target="right"><span class="icon-caret-right"></span>
                <c:choose>
                    <c:when test="${loginUser.userType == '管理员'}">设备管理</c:when>
                    <c:otherwise>设备列表</c:otherwise>
                </c:choose>
            </a>
        </li>
        <c:if test="${loginUser.userType != '普通员工'}">
            <li>
                <a href="WeixiuServlet" target="right">
                    <span class="icon-caret-right"></span>
                    <c:choose>
                        <c:when test="${loginUser.userType == '管理员'}">维修管理</c:when>
                        <c:when test="${loginUser.userType == '维修工'}">维修工作</c:when>
                    </c:choose>
                </a>
            </li>
        </c:if>
        <c:if test="${loginUser.userType == '普通员工'}">
            <li><a href="shebei_dispatch_record.jsp" target="right"><span class="icon-caret-right"></span> 我的任务</a></li>
        </c:if>

        <li>
            <a href="NoticeServlet" target="right"><span class="icon-caret-right"></span>
                <c:choose>
                    <c:when test="${loginUser.userType == '管理员'}">公告管理</c:when>
                    <c:otherwise>浏览公告</c:otherwise>
                </c:choose>
            </a>
        </li>

    </ul>
</div>
<script type="text/javascript">
    $(function () {
        $(".leftnav h2").click(function () {
            $(this).next().slideToggle(200);
            $(this).toggleClass("on");
        });
        $(".leftnav ul li a").click(function () {
            $("#a_leader_txt").text($(this).text());
            $(".leftnav ul li a").removeClass("on");
            $(this).addClass("on");
        });
        $('.leftnav ul').css('display','block');
    });
</script>
<ul class="bread">
    <li><a href="UserServlet?action=list" target="right" class="icon-home">首页</a></li>
    <li>
        <a id="a_leader_txt">
            <c:choose>
                <c:when test="${loginUser.userType == '管理员'}">
                    用户管理
                </c:when>
                <c:when test="${loginUser.userType == '普通员工'}">
                    设备列表
                </c:when>
                <c:otherwise>
                    维修工作
                </c:otherwise>
            </c:choose>
        </a>
    </li>
</ul>
<div class="admin">
    <c:choose>
        <c:when test="${loginUser.userType == '管理员'}">
            <iframe scrolling="auto" rameborder="0" src="UserServlet?action=list" name="right" width="100%" height="100%"></iframe>
        </c:when>
        <c:when test="${loginUser.userType == '普通员工'}">
            <iframe scrolling="auto" rameborder="0" src="ShebeiServlet?action=list" name="right" width="100%" height="100%"></iframe>
        </c:when>
        <c:otherwise>
            <iframe scrolling="auto" rameborder="0" src="WeixiuServlet?action=list" name="right" width="100%" height="100%"></iframe>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备添加</title>
    <%@ include file="include/head.jsp" %>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let fileInput = document.getElementById("imageFile");
            let resultDiv = document.getElementById("result");

            fileInput.addEventListener("change", function() {
                let file = fileInput.files[0];

                if (file) {
                    let formData = new FormData();
                    formData.append("imageFile", file);

                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "UploadServlet", true); // 替换为实际的上传处理Servlet URL

                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                                var response = JSON.parse(xhr.responseText);

                                if (response.status === "success") {
                                    let imagePath = response.imagePath;
                                    /*resultDiv.innerHTML = "<img src='" + imagePath + "' alt='Uploaded Image'>";*/
                                    resultDiv.innerHTML = "<span style='color: green;'>图片上传成功</span>";
                                    let imagePathDiv = document.getElementById("imagePath");
                                    imagePathDiv.value = imagePath;
                                } else {
                                    resultDiv.innerHTML = "<span style='color: orangered;'>图片上传失败</span>";
                                }
                            } else {
                                resultDiv.innerHTML = "<span style='color: orangered;'>服务器出现错误</span>";
                            }
                        }
                    };

                    xhr.send(formData);
                }
            });
        });
        function toggleThresholdInput(checkBoxId) {
            let checkbox = document.getElementById(checkBoxId);
            let thresholdInput = document.getElementById(checkBoxId + '_threshold');

            thresholdInput.disabled = !checkbox.checked;
        }
        function checkedInput(checkBoxId, bool) {
            document.getElementById(checkBoxId + '_threshold').checked = bool;
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li><a href="ShebeiServlet?action=list">设备列表</a></li>
        <li><a href="ShebeiServlet?action=reportRecord">故障设备上报记录</a></li>
        <c:if test="${loginUser.userType != '普通员工'}"><li><a href="${pageContext.request.contextPath}/shebei_monitor.jsp">设备运行状态监测</a></li></c:if>
        <li class="active"><a href="#">添加新设备</a></li>
    </ul>
    <br/>
    <form class="form-horizontal" role="form" action="ShebeiServlet?action=add" method="post" onsubmit="return check()">
        <div class="form-group">
            <label class="col-sm-3 control-label">名称：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiName" name="shebeiName">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">编号：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiNo" name="shebeiNo">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">类型：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiType" name="shebeiType">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">生产厂家：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiCangjia" name="shebeiCangjia">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">采购日期：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiIndate" name="shebeiIndate">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">采购价格：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiInprice" name="shebeiInprice">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">使用部门：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shebeiDept" name="shebeiDept">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">设备图片：</label>
            <div class="col-sm-5" style="display: flex; align-items: center;">
                <button class="btn" onclick="event.preventDefault(); document.getElementById('imageFile').click()">选择图片</button>
                <input id="imageFile" type="file" name="imageFile" value="" style="display: none;"/>
                <input id="imagePath" type="hidden" name="imagePath" value=""/>
                <div id="result" style="margin-left: 10px;"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">记录设备运行温度：</label>
            <div class="col-sm-5" style="margin-top: 7px;">
                <label style="padding-right: 8px;border-right: 1px dotted;">
                    <input type="checkbox" id="temperature_checkbox" onclick="toggleThresholdInput('temperature_checkbox')" name="temperature" value="true">
                    记录
                </label>&nbsp;
                <span>临界值：</span>
                <input type="number" id="temperature_checkbox_threshold" name="temperature_threshold" placeholder="温度临界值" disabled value="">
                <button type="button" disabled style="margin-left: -5px;">℃</button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">记录设备外界湿度：</label>
            <div class="col-sm-5" style="margin-top: 7px;">
                <label style="padding-right: 8px;border-right: 1px dotted;">
                    <input type="checkbox" id="humidity_checkbox" onclick="toggleThresholdInput('humidity_checkbox')" name="humidity" value="true">
                    记录
                </label>&nbsp;
                <span>临界值：</span>
                <input type="number" id="humidity_checkbox_threshold" name="humidity_threshold" placeholder="湿度临界值" disabled value="">
                <button type="button" disabled style="margin-left: -5px;">%RH</button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">记录设备外界噪音：</label>
            <div class="col-sm-5" style="margin-top: 7px;">
                <label style="padding-right: 8px;border-right: 1px dotted;">
                    <input type="checkbox" id="noise_checkbox" onclick="toggleThresholdInput('noise_checkbox')" name="noise" value="true">
                    记录
                </label>&nbsp;
                <span>临界值：</span>
                <input type="number" id="noise_checkbox_threshold" name="noise_threshold" placeholder="噪音临界值" disabled value="">
                <button type="button" disabled style="margin-left: -5px;">dB</button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">记录设备运行电压：</label>
            <div class="col-sm-5" style="margin-top: 7px;">
                <label style="padding-right: 8px;border-right: 1px dotted;">
                    <input type="checkbox" id="voltage_checkbox" onclick="toggleThresholdInput('voltage_checkbox')" name="voltage" value="true">
                    记录
                </label>&nbsp;
                <span>临界值：</span>
                <input type="number" id="voltage_checkbox_threshold" name="voltage_threshold" placeholder="电压临界值" disabled value="">
                <button type="button" disabled style="margin-left: -5px;">V</button>
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
        if (document.getElementById("shebeiName").value.trim().length == 0) {
            alert("名称不能为空!");
            return false;
        }
        if (document.getElementById("shebeiNo").value.trim().length == 0) {
            alert("编号不能为空!");
            return false;
        }
        if (document.getElementById("shebeiType").value.trim().length == 0) {
            alert("类型不能为空!");
            return false;
        }
        if (document.getElementById("shebeiCangjia").value.trim().length == 0) {
            alert("生产厂家不能为空!");
            return false;
        }
        if (document.getElementById("shebeiIndate").value.trim().length == 0) {
            alert("采购日期不能为空!");
            return false;
        }
        if (document.getElementById("shebeiInprice").value.trim().length == 0) {
            alert("采购价格不能为空!");
            return false;
        }
        if (document.getElementById("shebeiDept").value.trim().length == 0) {
            alert("使用部门不能为空!");
            return false;
        }
        return true;
    }
</script>
</html>

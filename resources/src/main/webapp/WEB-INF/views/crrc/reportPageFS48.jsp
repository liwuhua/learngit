<%--
  Created by IntelliJ IDEA.
  User: wangkai
  Date: 16/10/21
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <title>DPI</title>
    <link href="${contextPath}/styles/crrc/css/FS48.css" rel="stylesheet" type="text/css" />
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
        var plant = "${plant}";
        var pur_group = "${pur_group}";
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/highmaps.js"></script>

</head>
<body class="fixed-left">

    <!-- Begin page -->
    <div id="wrapper">
        <!-- ============================================================== -->
        <!-- Start right Content here -->
        <!-- ============================================================== -->
        <div class="content-page">
            <div class="content">
                <div class="container">
                    <!-- //遮罩层 -->
                    <div class='Mask'>
                        <img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b>
                    </div>
                    <div id='Mask'></div>
                    <div id='Mask2'></div>
                    <!-- <div class="row">
                        <div class="col-sm-12">
                            <form>
                                <p>
                                    <span class="setTime"> 
                                        <b>年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})" onfocus="this.blur()">
                                    </span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div> -->
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row ch-kp-fortyEight" style="position:relative;margin-top:0px;">
                        <div id="container" style="height:750px;position: relative;"></div>
                    </div>
                    <div class="zhezhao1"><img src='${contextPath}/styles/common/images/zhezhao.png' style="position:absolute;left:0;top:0;z-index:1;width: 100%;height: 100%;"></div>
                    <div class="zhezhao2"><img src='${contextPath}/styles/common/images/zhezhao.png' style="position:absolute;left:0;top:0;z-index:7;width: 100%;height: 100%;"></div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Right content here -->
        <!-- ============================================================== -->
        <!-- /Right-bar -->
    </div>
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
   <%--  <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script> --%>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script src="${contextPath}/styles/common/js/es5-shim.min.js"></script>
    <script src="${contextPath}/styles/common/js/proj4.js"></script>
    <script src="${contextPath}/styles/common/js/cn-with-citys.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS48-table.js"></script>

</body>
</html>

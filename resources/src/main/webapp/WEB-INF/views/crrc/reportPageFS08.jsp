<%--
  Created by IntelliJ IDEA.
  User: wangkai
  Date: 16/10/21
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<!DOCTYPE html> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <title>DPI</title>
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS08.css">
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
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
                    <div class="row">
                        <div class="col-sm-12">
                            <form>
                                <p>
                                   <p class="setTime">
                                        <b>单据日期(必填)</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
                                        <input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit">
                                    </p>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row ch-kp-eight">
                       	<p class="table-name">销售收入完成情况统计表</p>
                       	<p class="table-data"><span class="fr datatime1"></span></p>
                       	<div>
                       		<table class="col-sm-12" border="1">
                                <thead>
                                    <tr>
                                        <th rowspan="2">各单位</th>
                                        <th rowspan="2">年度指标</th>
                                        <th colspan="4">年度数据</th>
                                        <th colspan="4">当期数据</th>
                                    </tr>
                                    <tr>
                                        <th>当年累计完成额</th>
                                        <th>当年累计完成率</th>
                                        <th>上年同期累计完成额</th>
                                        <th>同比增减额</th>
                                        <th>当期完成额</th>
                                        <th>当期完成率</th>
                                        <th>上年同期完成额</th>
                                        <th>同比增减额</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                       	</div>                            
                       </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->
            <!-- /Right-bar -->
        </div>

    </div>
    <!-- END wrapper -->
    <!-- jQuery  -->
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    
    <script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
    <script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>

    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS08-table.js"></script>
    
</body>
</html>

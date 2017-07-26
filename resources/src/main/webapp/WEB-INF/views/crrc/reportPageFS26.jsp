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
    <link href="${contextPath}/styles/crrc/css/FS26.css" rel="stylesheet" type="text/css" />
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
        var comp_code = "${comp_code}";/* 公司代码  */
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
                        <!-- form表单 -->
                        <form>
                            <p>
                                <span><b class="type">公司代码：</b><input class="gongsi" type="text"><span class="btn1 btnAll"></span></span>
                                <span class="setTime">
                                    <b>年月－年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()">
                                </span>
                                <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                            </p>
                        </form>
                        <div class="retract">收起  ∧</div>
                    </div>
                </div>
                <!--未查询到数据显示框 -->
                <div class='notSearch'></div>
                <!-- //表格显示 -->
                <div class="row">
                    <p class="table-name">销售费用统计分析表</p>
                    <%-- <p class="table-data" style="overflow: hidden;"><input type="button" value="二级明细" id="btnChange"><span>编制单位：<span></span></span><b style="margin-left: 20px;">货币单位:元</b><b style="float: right;"><span  class="dataTime1"></span><c:if test="${showExp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></b></p> --%>
                    <p class="table-data" style="overflow: hidden;"><input type="button" value="二级明细" id="btnChange"><span class="bianzhi"></span><b style="margin-left: 20px;">货币单位:元</b><b style="float: right;"><span  class="dataTime1"></span><c:if test="${showExp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></b></p>
                    <div class="ch-kp-twentySix1">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12" width="100%">
                                    <tr>
                                        <th width="120px">项目</th>
                                        <th width="40px">行次</th>
                                        <th width="100px">内部订单</th>
                                        <th width="150px">描述</th>
                                        <th width="130px">上年发生额</th>
                                        <th width="130px">本年计划</th>
                                        <th width="130px">本期计划</th>
                                        <th width="130px">本期发生额</th>
                                        <th>执行率（%）</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content">
                            <table width='100%'>
								
                            </table>
                            <%--<div id='show1' style="width:100%;text-align:center;">数据加载完成</div>--%>
                        </div>
                    </div>


                    <div class="ch-kp-twentySix2">
                        <div class="t_r_t" id="t_r_t2">
                            <div class="t_r_title">
                                <table width='100%' class="col-sm-12">
                                    <tr>
                                        <th width="120px">项目</th>
                                        <th width="40px">行次</th>
                                        <th width="100px">内部订单</th>
                                        <th width="230px">描述</th>
                                        <th width="130px">上年发生额</th>
                                        <th width="100px">本年计划</th>
                                        <th width="100px">本期计划</th>
                                        <th width="100px">本期发生额</th>
                                        <th>执行率（%）</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content2">
                            <table width='100%'>
								
                            </table>
                            <%--<div id='show2' style="width:100%;text-align:center;">数据正在加载中...</div>--%>
                        </div>
                    </div>


                </div>


                <div class="importBox">
                    <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                    <div style="width:100%;">
                        <div class="chooseFile">
                            <span class="fl">选择文件</span>
                            <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS26/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
                            <span class="fileupload-file" style="padding-left: 20px;"></span>
                        </div>
                        <div id="progress" class="progress">
                            <div class="bar" style="width: 0%;"></div>
                        </div>
                        <div class="downloadTable">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- End Right content here -->
    <!-- ============================================================== -->
    <!-- /Right-bar -->
</div>
<script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
<script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
<script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
<script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS26-table.js"></script>
</body>
</html>

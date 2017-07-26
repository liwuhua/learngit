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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS16.css">
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
                                    <b>年月（必填）：</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()">
                                    <input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"><input type="button" value="下载模版" id="downMb" style="width: 60px; background: #c70019;">
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row">
                        <p class="table-name" style="padding: 0px 15px;">利润表(合并)</p>
                        <p class="table-data" id="table-data" style="padding: 0px 15px;">
                            <b class="fl">编制单位：<span class="bianzhi">中车永济电机有限公司</span><span style="margin-left:15px;">货币单位:  元</span></b>
                            <b class="fr dataTime1"><span></span><c:if test="${showExp eq 1}"><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></b>
                        </p>
                        <div class="ch-kp-sixteen" id="ch-kp-sixteen">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12" width="100%">
                                        <tr>
                                            
                                        </tr>
                                    </table>

                                </div>
                            </div>
                            <div class="t_r_content" id="t_r_content" onscroll="aa()">
                                <table>

                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="importBox">
                        <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                        <div style="width:100%;">
                            <div class="chooseFile">
                                <span class="fl">选择文件</span>
                                <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS16/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS16-table.js"></script>
    
</body>
</html>

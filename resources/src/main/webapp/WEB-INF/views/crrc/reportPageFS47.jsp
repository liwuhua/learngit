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

    <script>
        var ctx = "${contextPath}";
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
    <link href="${contextPath}/styles/crrc/css/FS47.css" rel="stylesheet" type="text/css" />
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
                                    <span><b class="type">车型(必填)</b><input class="train_type" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">车号</b><input class="train_num" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">造修厂家</b><input class="manufacturer" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">隶属路局</b><input class="bureau_subjection" type="text"><span class="btn4 btnAll"></span></span>
                                    <span><b class="type">配属段、所</b><input class="place_subjection" type="text"><span class="btn5 btnAll"></span></span>
                                    <span><b class="type">车修程类别</b><input class="repair_level" type="text"><span class="btn6 btnAll"></span></span>
                                    <span><b class="type">产品编号</b><input class="productCode" type="text"><span class="btn7 btnAll"></span></span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">国铁配属表</p>
                        <p class="table-data" style="overflow: hidden;">
                        	<b style="float: right;">
                        		<c:if test="${showExp eq 1}">
                        			<img alt="" src="${contextPath}/styles/common/images/right/export.png" class="export">
                        		</c:if>
                        		<c:if test="${showExp eq 1}">
                        			<img alt="" src="${contextPath}/styles/common/images/right/import.png" class="import">
                        		</c:if>
                        	</b>
                        </p>
                        <div class="fortySeven">
                        	<div class="t_r ch-kp-fortySeven" style='padding:0px;'>
	                            <div class="t_r_t" id="t_r_t">
	                                <div class="t_r_title">
	                                    <table>
	                                        
	                                    </table>
	                                </div>
	                            </div>
	                            <div class="t_r_content" id="t_r_content" onscroll="aa()">
	                                <table>
	
	                                </table>
	                                <div id='show' style="width:100%;text-align:center;">数据正在加载中...</div>
	                            </div>
	                            <div class="toTop"></div>
		                        <div class="t_r_b" id="t_r_b">
		                            <div class="t_r_title">
		                                <table class="col-sm-12" style="width:100%;">
		
		                                </table>
		                            </div>
		                        </div>
	                        </div>
                        </div>
                    </div>
                    <div class="importBox">
                        <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                        <div style="width:100%;">
                            <div class="chooseFile">
                                <span class="fl">选择文件</span>
                                <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS47/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS47-table.js"></script>
</body>
</html>

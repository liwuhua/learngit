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
<c:set var="ctx" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>

<!DOCTYPE html> 
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
	<meta name="author" content="Coderthemes">
    <title>DPI</title>
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS12.css">
    
    <script>
        var ctx = "${ctx}";
        var comp_code = "${comp_code}";/* 公司代码  */
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
</head>
<body class="fixed-left">

    <!-- Begin page -->
	<div id="wrapper">
			<!-- Start right Content here -->
			<!-- ============================================================== -->
            <div class="content-page">
                <div class="content">
                	<!-- //遮罩层 -->
					<div class='Mask'>
						<img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b>
					</div>
					<div id='Mask'></div>
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                <form>
                                    <p>
	                                	<span><b class="type">公司代码</b><input class="gongsi" type="text"><a class="btn1 btnAll" title="查询"></a></span>
                                    	<span><b>年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  onfocus="this.blur()"></span>
                                   		<span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row ch-kp-twelve">
                        	<p class="table-name">期间费用明细表</p>
	                        <p class="table-data">
	                        	<b class="fl">编制单位：<span class="bianzhi"></span><span style="margin-left: 20px;">货币单位 : 元</span></b>
	                        	<span class="fr"><b class="dataTime1"></b><c:if test="${showExp eq 1}"><img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img alt="" title="上传" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></span>
	                        </p>
	                        <div class="table-twelve">
	                        	<div class="table-3">
	                        		<div class="t_r">
		                        		<div class="t_r_t" id="t_r_t">
		                                	<div class="t_r_title">
		                                		<table>
				                                    <tr>
				                                        <th width='200px'>项目</th>
				                                        <th width='40px' class="xh">行次</th>
				                                        <th width='100px'>本年累计数</th>
				                                        <th width='100px'>上年同期数</th>
				                                        <th width='200px'>项目</th>
			                                        	<th width='40px' class="xh">行次</th>
			                                        	<th width='100px'>本年累计数</th>
			                                        	<th width='100px'>上年同期数</th>
			                                        	<th width='200px'>项目</th>
			                                        	<th width='40px' class="xh">行次</th>
			                                        	<th width='100px'>本年累计数</th>
			                                        	<th style="padding-right:17px;">上年同期数</th>
				                                    </tr>
		                                		</table>
		                                	</div>
		                                </div>
		                                <div class="t_r_content" id="t_r_content"  onscroll="aa()">
		                                	<table>
		                               
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
						    		<input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS12/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
						    		<span class="fileupload-file"></span>
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
    <!-- END wrapper -->
    <!-- jQuery  -->
   <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
	<!-- upload -->
    <script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
    <script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS12-table.js"></script>
   
</body>
</html>
                                
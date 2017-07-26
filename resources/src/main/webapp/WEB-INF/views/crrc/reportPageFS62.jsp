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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS62.css">
    
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
                                        <span><b class="type">工厂</b><input class="factory" type="text"><span class="btn1 btnAll" title="查询"></span></span>
	                                	<span><b class="typeNum">电机型号</b><input class="groes" type="text"><a class="btn2 btnAll" title="查询"></a></span>
                                        <span><b class="djbh">电机编号</b><input class="sernr" type="text"><a class="btn3 btnAll" title="查询"></a></span>
                                        <span><b class="wxjb">维修级别</b><input class="zlevel" type="text"><a class="btn4 btnAll" title="查询"></a></span>
                                        <span><b class="jxdd">检修地点</b><input class="checkAddress" type="text"><a class="btn5 btnAll" title="查询"></a></span>
                                        <span><b>检修入厂日期(必选)</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"></span>
                                   		<span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row">
                        	<p class="table-name">检修产品基础数据导出表</p>
	                        <p class="table-data" id="table-data">
	                        	<%--<span class="fr"><c:if test="${showExp eq 1}"><img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if></span>--%>
                                <span class="fr">
                                	<c:if test="${showExp eq 1}">
                                		<img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export">
                                	</c:if>
                                </span>
                            </p>
	                        <div class="ch-kp-sixtyTwo" id="ch-kp-sixtyTwo">
                                <div class="t_r_t" id="t_r_t">
                                    <div class="t_r_title">
                                        <table class="col-sm-12" width="100%">
                                            <tr>
                                                <th width='40px' class="xh">序号</th>
                                                <th width='80px' class="iwerk">工厂</th>
                                                <th width='150px' class="zdate">检修入厂日期</th>
                                                <th width='150px' class="arbpl">检修地点</th>
                                                <th width='150px' class="groes">产品型号</th>
                                                <th width='150px' class="sernr">产品编号</th>
                                                <th class="zlevel">维修级别</th>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="t_r_content" id="t_r_content">
                                    <table>

                                    </table>
                                    <div id='show' style="width:100%;text-align:center;">信息正在加载中。。。</div>
                                </div>
                                <div class="toTop"></div>
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS62-table.js"></script>
   
</body>
</html>
                                
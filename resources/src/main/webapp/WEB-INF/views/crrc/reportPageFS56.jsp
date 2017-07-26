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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS56.css">
    <script>
        var ctx = "${ctx}";
        var plant = "${plant}";/*  工厂 --> */
        var salesOrg = "${salesOrg}";/* - 销售组织 */
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
						<div id='Mask2'></div>
                        <div class="row">
                            <div class="col-sm-12">
                                <form>
                                    <p>
                                    	<span><b class="type">型号（必填）</b><input class="typeXh" type="text"><span class="btn1 btnAll"></span></span>
	                                    <span class="setTime">
	                                    	<b>日期</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
	                                    </span>

	                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"><input type="button" value="下载模版" id="downMb" style="width: 60px; background: #c70019;"><input type="button" value="上传模板" id="upMb" style="width: 60px; background: #c70019;"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row">
                        	<p class="table-name">检修电机数据统计表</p>
                        	<p class="table-data"><span class="dataTime1"></span><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"><img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></p>
                        	<div class="t_r  ch-kp-fiftySix">
                        		<div class="t_r_t" id="t_r_t">
                        			<div class="t_r_title">
		                        		<table class="col-sm-12" >
		                                    <%--<tr>--%>
		                                        <%--<th width='40px' class='xh'>序号</th>
		                                        <th width='80px' class='vkorg'>销售组织</th>
		                                        <th width='200px' class='iwerk'>工厂</th>
		                                        <th width='130px' class='parnr'>客户编码</th>
		                                        <th width='250px' class='txtmd'>客户名称</th>
		                                        <th width='150px' class='ddtext'>级别描述</th>
		                                        <th width='150px' class='matnr'>物料编码</th>
		                                        <th width='300px' class='maktx'>物料描述</th>
		                                        <th width='100px' class='qczcts'>初期在产台数</th>
                                                <th width='200px' class='qczccb'>初期在产成本</th>
                                                <th width='100px' class='qjjcts'>期间进厂台数</th>
                                                <th width='100px' class='qjccts'>期间出厂台数</th>
                                                <th width='100px' class='qjcccb'>期间出厂成本</th>
                                                <th width='100px' class='qjzcts'>期间在产台数</th>
                                                <th width='100px' class='qjzccb'>期间在产成本</th>
                                                <th width='100px' class='qjxsts'>期间销售台数</th>
                                                <th width='100px' class='qjxscb'>期间销售成本</th>
                                                <th width='100px' class='qjxsje'>期间销售金额</th>
                                                <th width='130px' class='yfhwkpts'>已发货未开票台数</th>
                                                <th class='yfhwkpcb'>已发货未开票成本</th>
		                                        </tr>--%>
		                                    <%--<span style="width:17px;display:inline-block;height:4px;"></span>--%>
			                            </table>
			                            
		                     		</div>
	                     		</div>
	                     		<div class="t_r_content" id="t_r_content" onscroll="aa()">
	                             	<table>
	                             		
	                             	</table>
	                             	<div id='show' style="width:100%;text-align:center;">数据正在加载中。。。</div>
	                             </div>
	                             <div class="toTop"></div>
                        	</div>
                        </div>

                        <div class="importBox1">
                            <p class="ppp1"><span class="fl1">模板导入</span><span class="fr1 closeX1" style="float: right;"></span></p>
                            <div style="width:100%;">
                                <div class="chooseFile1">
                                    <span class="fl1">选择文件</span>
                                    <input id="fileupload1" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS56/uploadTemplate" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
                                    <span class="fileupload-file1" style="padding-left: 20px;"></span>
                                </div>
                                <div id="progress1" class="progress1">
                                    <div class="bar1" style="width: 0%;"></div>
                                </div>
                                <div class="downloadTable1">

                                </div>
                            </div>
                        </div>

                        <div class="importBox">
                            <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                            <div style="width:100%;">
                                <div class="chooseFile">
                                    <span class="fl">选择文件</span>
                                    <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS56/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
    <!-- END wrapper -->

    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>


    <script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
    <script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
    <!-- App js -->
    <script src="${contextPath}/styles/common/js/common.js"></script>
    <%-- <script src="${contextPath}/styles/common/js/common2.js"></script> --%>
    <script src="${contextPath}/styles/crrc/js/FS56-table.js"></script>
    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script>
    
    </script>
        
</body>
</html>

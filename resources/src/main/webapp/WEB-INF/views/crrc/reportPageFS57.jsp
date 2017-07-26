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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS57.css">
    <script>
        var ctx = "${ctx}";
        var plant = "${plant}"; /* 工厂 */
        var salesOrg = "${salesOrg}";/* 销售组织 */ 
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
                                    	<span><b class="type">销售组织</b><input class="vkorgValue" type="text"><span class="btn1 btnAll"></span></span>
                                    	<span><b class="type">工厂</b><input class="gc" type="text"><span class="btn2 btnAll"></span></span>
                                    	<span><b class="type">客户编号</b><input class="kh" type="text"><span class="btn3 btnAll"></span></span>
	                                    <span class="setTime">
	                                    	<b>年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()">
	                                    </span>

	                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row">
                        	<p class="table-name">检修统计表</p>
                        	<p class="table-data">
                        		<span class="dataTime1"></span>
                        		<c:if test="${showExp eq 1}">
                        			<img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export">
                        		</c:if>
                        		<img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol">
                        	</p>
                        	<div class="t_r  ch-kp-fiftySeven">
                        		<div class="t_r_t" id="t_r_t2">
                        			<div class="t_r_title">
		                        		<table class="col-sm-12" >
		                                    <tr>
		                                        <th width='40px' class='xh'>序号</th>
		                                        <th width='60px' class='vkorg'>销售组织</th>
		                                        <th width='60px' class='iwerk'>工厂</th>
		                                        <th width='100px' class='parnr'>客户编码</th>
		                                        <th width='220px' class='txtmd'>客户名称</th>
		                                        <th width='100px' class='ddtext'>级别描述</th>
		                                        <th width='120px' class='matnr'>物料编码</th>
		                                        <th width='300px' class='maktx'>物料描述</th>
		                                        <th width='100px' class='qczcts'>初期在产台数</th>
                                                <th width='100px' class='qczccb'>初期在产成本</th>
                                                <th width='100px' class='qjjcts'>期间进厂台数</th>
                                                <th width='100px' class='qjccts'>期间出厂台数</th>
                                                <th width='100px' class='qjcccb'>期间出厂成本</th>
                                                <th width='100px' class='qjzcts'>期间在产台数</th>
                                                <th width='100px' class='qjzccb'>期间在产成本</th>
                                                <th width='100px' class='qjxsts'>期间销售台数</th>
                                                <th width='100px' class='qjxscb'>期间销售成本</th>
                                                <th width='100px' class='qjxsje'>期间销售金额</th>
                                                <th width='110px' class='yfhwkpts'>已发货未开票台数</th>
                                                <th class='yfhwkpcb'>已发货未开票成本</th>
		                                        </tr>
		                                    <span style="width:17px;display:inline-block;height:4px;"></span>
			                            </table>
			                            
		                     		</div>
	                     		</div>
	                     		<div class="t_r_content" id="t_r_content2" onscroll="bb()">
	                             	<table>
	                             		
	                             	</table>
	                             	<div id='show' style="width:100%;text-align:center;">数据正在加载中。。。</div>
	                             </div>
	                             <div class="toTop"></div>
                        	</div>
                        </div>
                      	<div class='hideColBox'>
                      		<p class='pClose'><span class='closeBtn'></span></p>
                      		<div class="t_r hideTable">
                        		<div class="t_r_t" id="t_r_t">
                        			<div class="t_r_title">
			                      		<table>
		                      				<tr style='background-color:#ededed;'>
			                      				<td style="width:50px;text-align: center;"><input type="checkbox" class="allCheck" checked='checked'></td>
			                      				<td>字段名称</td>
			                      			</tr>
			                      		</table>
                      				</div>
	                            </div>
	                            <div id="t_r_content" onscroll="aa()">
	                             	<table>
	                             		
	                             	</table>
	                             </div>
                        	</div>
                      		<p class="btns"><input type="button" value="取消" class="canBtns"><input type="button" value="确定" class="sureBtns"></p>
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
    <!-- App js -->
    <script src="${contextPath}/styles/common/js/common.js"></script>
    <%-- <script src="${contextPath}/styles/common/js/common2.js"></script> --%>
    <script src="${contextPath}/styles/crrc/js/FS57-table.js"></script>
    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script>
    
    </script>
        
</body>
</html>

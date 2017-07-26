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
    
    <script>
        var ctx = "${ctx}";
        var salesOff = "${salesOff}";/* <!--  销售部门 --> */
        var salesOrg = "${salesOrg}";/* <!-- 销售组织 --> */
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS02.css">
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
                                    	<span><b class="type">物料编码</b><input class="material" type="text"><span class="btn4 btnAll"></span></span>
                                    	<span><b class="type">销售部门</b><input class="vkburValue" type="text"><span class="btn2 btnAll"></span></span>
                                    	<span><b class="type">销售订单</b><input class="vbelnValue" type="text"><span class="btn3 btnAll"></span></span>
	                                    <span class="setTime">
	                                        <b>交付日期(必填)</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
	                                    </span>
	                                    <span class="productType"><b>工程化产品</b><input type="checkbox"><b>新产品</b><input type="checkbox"></span>
	                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row">
                        	<p class="table-name">销售订单完成统计表</p>
                        	<p class="table-data">
                        		<span class="dataTime1"></span>
                        		<c:if test="${showExp eq 1}">
                        			<img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export">
                        		</c:if>
                        		<img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol">
                        	</p>
                        	<div class="t_r ch-kp-cb1">
                        		<div class="t_r_t" id="t_r_t">
                        			<div class="t_r_title">
		                        		<table class="col-sm-12">
		                                    <tr>
		                                        <th width='40px' class='xh'>序号</th>
		                                        <th width='80px' class='vkbur'>销售部门</th>
		                                        <th width='125px' class='bname'>排产业务书编号</th>
		                                        <th width='130px' class='vbeln'>销售订单号</th>
                                                <th width='130px' class='aufnr'>生产订单</th>
		                                        <th width='100px' class='posnr'>行项目</th>
		                                        <th width='150px' class='matnr'>物料编码</th>
		                                        <th width='350px' class='arktx'>物料描述</th>
		                                        <th width='85px' class='erdat'>排产日期</th>
		                                        <th width='100px' class='edatu'>要求交付日期</th>
		                                        <th width='100px' class='wmeng'>计划数量</th>
		                                        <th width='140px' class='before_complete_amount_total'>前期累计完成数量</th>
		                                        <th width='120px' class='current_complete_amount'>当期完成数量</th>
		                                        <th width='120px' class='ontime_complete_amount'>准时完成数量</th>
		                                        <th width='125px' class='ontime_rate'>订单完成率</th>
		                                        <th class='bz'>备注</th>
		                                    </tr>
		                                    <span style="width:17px;display:inline-block;height:4px;"></span>
			                             </table>
			                             
		                             </div>
	                           	 </div>
	                             <div class="t_r_content" id="t_r_content" onscroll="aa()">
	                             	<table>
	                             		
	                             	</table>
	                             	<div id='show' style="width:100%;text-align:center;">信息正在加载中。。。</div>
	                             </div>
	                             <div class="toTop"></div>
                        	</div>
                        	<div class='hj'>
	                    		<p>合计<span style="padding-left: 20px;">订单完成率：<b></b></span><span style="padding-left: 20px;">订单正点率：<b></b></span></p>
	                    		<div class="lr"><<</div>
	                    	</div>
                        </div>
                		<div class='hideColBox'>
                      		<p class='pClose'><span class='closeBtn'></span></p>
                      		<div class="t_r hideTable">
                        		<div class="t_r_t">
                        			<div class="t_r_title">
			                      		<table>
		                      				<tr style='background-color:#ededed;'>
			                      				<td style="width:50px;text-align: center;"><input type="checkbox" class="allCheck" checked='checked'></td>
			                      				<td>字段名称</td>
			                      			</tr>
			                      		</table>
                      				</div>
	                            </div>
	                            <div class="t_r_content" onscroll="aa()">
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
    <!-- jQuery  -->
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    <!-- App js -->
    <script src="${contextPath}/styles/common/js/common.js"></script>
    <script src="${contextPath}/styles/crrc/js/FS0201-table.js"></script>
    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script>
    
    </script>
        
</body>
</html>

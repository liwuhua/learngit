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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS03.css">
    <script>
        var ctx = "${ctx}";
        var fs03_pur_group = "${pur_group}";/* 采购组 */
        var plant = "${plant}";/* 工厂 */
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
                    <div class="container">
                    	<!-- //遮罩层 -->
						<div class='Mask'>
							<img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b>
						</div>
						<div id='Mask'></div>
						<div id='Mask2'></div>
                        <div class="row">
                            <div class="col-sm-12">
                            	<!-- form表单 -->
                                <form>
                                    <p>
                                    	<span><b class="type">工厂</b><input class="factory" type="text"><span class="btn1 btnAll"></span></span>
                                    	<span><b class="type">物料编码</b><input class="material" type="text"><span class="btn2 btnAll"></span></span>
                                    	<!-- <span><b class="type">供应商</b><input class="supplier" type="text"><span class="btn3 btnAll"></span></span> -->
                                    	<span><b class="type">采购组</b><input class="purchase" type="text"><span class="btn4 btnAll"></span></span>
                                        <span class="setTime">
                                        	<b>入库日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM',minDate:'2017-04' })"  onfocus="this.blur()">
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
                        	<p class="table-name">采购成本变动金额统计表</p>
                            <p class='table-data' style="overflow: hidden;padding: 0 15px;margin-top:-30px;font-size:12px;font-weight:600;"><span style='float:left'>货币单位  : 元</span><b style="float: right;">期间：<span class="dataTime1"></span><c:if test="${showExp eq 1}"><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>
                        	<div class="t_r table-03">
                           		<div class="t_r_t" id="t_r_t">
                                	<div class="t_r_title">
                        				<table class="ch-kp-three col-sm-12" border="1" >
		                                    <tr>
		                                        <th class="xh" rowspan="2" width="40px">序号</th>
		                                        <th class='matnr' rowspan="2" width="120px">物料编码</th>
		                                        <th class='matnrV' rowspan="2" width="180px">物料描述</th>
		                                        <th class='ekgrp' rowspan="2" width="50px">采购组</th>
		                                        <th class='sum-menge' rowspan="2" width="80px" style='text-align:right;'>当期数量</th>
		                                        <th class='meins' rowspan="2" width="60px">计量单位</th>
		                                        <th class='price-base' rowspan="2" width="60px">价格单位</th>
		                                        <th colspan="6" >含税价</th>
		                                        <th colspan="6">不含税价</th>
		                                    </tr>
		                                    <tr>
		                                        <th width="120px">追溯最近年平均价</th>
		                                        <th width="80px">上年平均价</th>
		                                        <th width="80px">当年平均价</th>
		                                        <th width="80px">当期平均价</th>
		                                        <th width="80px">价差</th>
		                                        <th width="80px">变动金额</th>
		                                        <th width="120px">追溯最近年平均价</th>
		                                        <th width="80px">上年平均价</th>
		                                        <th width="80px">当年平均价</th>
		                                        <th width="80px">当期平均价</th>
		                                        <th width="100px">价差</th>
		                                        <th>变动金额</th>
		                                    </tr>
		                                    <span style="width:17px;display:inline-block;height:0px;"></span>
		                                </table>
		                            </div>
		                        </div>
		                        <div  class="t_r_content" id="t_r_content" onscroll="aa()">
		                        	<table>
		                        		
		                        	</table>
		                        	<div id='show' style="width:100%;text-align:center;">数据正在加载中...</div>
		                        </div>
		                        <div class="toTop"></div>
                        	</div>
                            
                        </div>
						<!-- 隐藏列  -->
	                    <div class='hideColBox'>
	                        <p class='pClose'><span class='closeBtn'></span></p>
	                        <div class="t_r hideTable">
	                            <div class="t_r_t">
	                                <div class="t_r_title">
	                                    <table>
	                                        <tr style='background-color:#ededed;'>
	                                            <td style="width:50px; text-align: center;"><input type="checkbox" class="allCheck" checked='checked'></td>
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
            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->
            <!-- /Right-bar -->
    </div>
    <!-- END wrapper -->
    <!-- jQuery  -->
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS03-table.js"></script>
   
</body>
</html>

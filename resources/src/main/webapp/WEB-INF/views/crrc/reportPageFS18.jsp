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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS18.css">
    <script>
        var ctx = "${ctx}";
        var plant = "${plant}";
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
                            	<form>
                                	<p>
                                		<span><b class="type">销售订单</b><input class="vbelnValue" type="text"><span class="btn1 btnAll"></span></span>
		                                <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn2 btnAll"></span></span>
		                                <span><b class="type">销售订单工厂</b><input class="werksValue" type="text"><span class="btn3 btnAll"></span></span>
		                                <span><b class="type">生产工厂</b><input class="dwerkValue" type="text"><span class="btn4 btnAll"></span></span>
	                                   	<span><b class="type">生产订单类型</b><input class="dauatValue" type="text"><span class="btn5 btnAll"></span></span>
	                                   	<span class="setTime">
	                                       	<b>销售订单交货日期</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
	                                   	</span>
	                                   	<span class="setTime">
	                                       	<b>计划完成日期</b><input type="text" id="startTimeTwo" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTimeTwo" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
	                                   	</span>
	                                   	<span class="setTime">
	                                       	<b>生产订单收货入库日期</b><input type="text" id="startTimeThree" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTimeThree" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
	                                   	</span>
	                                   	<span class="yujing"><b>预警状态</b>
		                                   	<input data-show="1" type="checkbox" checked="checked"><s class="red-1"></s>
		                                   	<input data-show="2" type="checkbox"><s class="yellow-2"></s>
		                                   	<input data-show="0" type="checkbox"><s class="blue-0"></s>
	                                   	</span>
	                                   	<span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                     </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <!-- 18表 -->
                        <div class="row">
                        	<div class="t_r ch-kp-eighteen">
                        		<div class="t_r_t" id="t_r_t">
                        			<div class="t_r_title">
		                        		<table class="col-sm-12"  cellpadding="0" cellspacing="0" style="height: 25px;">
		                                    <tr>
		                                        <th width="90px">销售订单</th>
		                                        <th width="60px">行项</th>
		                                        <th width="50px">工厂</th>
		                                        <th width="100px">生产订单</th>
		                                        <th width="120px">物料编码</th>
		                                        <th width="350px">物料描述</th>
		                                        <th width="60px">生产工厂</th>
		                                        <th width="90px">订单数量</th>
		                                        <th width="120px">销售订单创建日期</th>
		                                        <th width="120px">销售订单交货日期</th>
		                                        <th width="120px">生产订单创建日期</th>
		                                        <th width="120px">生产订单下达日期</th>
		                                        <th width="90px">计划完成日期</th>
		                                        <th width="80px">入库数量</th>
		                                        <th>生产订单收货入库日期</th>
		                                    </tr>
		                                    <span style="width:17px;display:inline-block;height:4px;"></span>
                            		  </table>
                            	    </div>
                        		</div>
	                        	<div class="t_r_content" id="t_r_content" onscroll="aa()" style="height: 100px;">
	                             	<table  cellpadding="0" cellspacing="0">
	                             		
	                             	</table>
	                             	<div id='show' style="width:100%;text-align:center;">数据正在加载中...</div>
	                             </div>
                       		</div>
                    	</div>
                		<!-- 20表 -->
                        <div class="twentyBox">
                            <div class="twentyTable">
                                <div class="t_left">
                                    <div style="width:100%;" class="t_l_t">
                                        <table>

                                        </table>
                                    </div>
                                    <div class="cl_freeze" id="cl_freeze2">
                                        <table>

                                        </table>
                                    </div>
                                </div>
                                <div class="t_r ch-kp-twenty">
                                    <div class="t_r_t" id="t_r_t2">
                                        <div class="t_r_title">
                                            <table class="col-sm-12">

                                            </table>
                                        </div>
                                    </div>
                                    <div class="t_r_content" id="t_r_content2" onscroll="ff()">
                                        <table>

                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                    	<!-- 23表	 -->
                    	<div class="twentyThreeTable" style='display:none;'>
                    		<div class="t_left"> 
						       <div style="width:100%;" class="t_l_t"> 
							       <table style="width:100%;"> 
							           
							       </table> 
						       </div> 
						       <div class="cl_freeze" id="cl_freeze3"> 
						       	    <table> 
						                
						       		</table> 
						   		</div> 
						    </div> 
                        	<div class="t_r ch-kp-twentyThree">
                        		<div class="t_r_t" id="t_r_t3">
                        			<div class="t_r_title">
		                        		<table class="col-sm-12">
		                                    
                            		    </table>
                            	    </div>
                        		</div>
	                        	<div class="t_r_content" id="t_r_content3" onscroll="gg()">
	                             	<table>
	                             		
	                             	</table>
	                             </div>
                       		</div>
                    	</div>	
                    	
                    	<!-- 25表 27表 -->
                    	<div class="table-detail">
                    		<div class="Mask1">
                    			<img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b>
                    		</div>
                    		<div class="Mask2">
                    			<img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b>
                    		</div>
	                        <div class="detail-del"><%-- <img alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol2"> --%><img alt="" src="${contextPath}/styles/common/images/del.png" class="del"></div>
	                        
	                        <div class="tableBox25">
	                        	<p class='table-name'>生产订单缺料表</p>
		                        <div class="t_r ch-kp-twentyFive">
		                            <div class="t_r_t" id="t_r_t4">
		                                <div class="t_r_title" id="t_r_title4">
		                                	<table class="col-sm-12">
		                                       <tr>
		                                            <th width='90px'>生产订单</th>
		                                            <th width='60px'>工作中心</th>
		                                            <th width='120px'>物料编码</th>
		                                            <th width='350px'>物料描述</th>
		                                            <th width='90px'>计划发料时间</th>
		                                            <th width='90px'>总需求量</th>
		                                            <th width='90px'>累计发料量</th>
		                                            <th width='90px'>预留量</th>
		                                            <th width='90px'>最后发料时间</th>
		                                            <th width='100px'>模拟库存量</th>
		                                            <th width='50px'>工厂</th>
		                                            <th>MRP控制者</th>
		                                        </tr>
		                                        <span style="width:17px;display:inline-block;height:4px;"></span>
		                                    </table>
		                                </div>
		                            </div>
		                            <div class="t_r_content" id="t_r_content4" onscroll="dd()">
		                                <table>
		
		                                </table>
		                                <!-- <div id='show4' style="width:100%;text-align:center;">数据正在加载完成</div> -->
		                            </div>
		                        </div>
	                        </div>
	                        
	                        <div class="tableBox27">
	                        	<p class='table-name'>缺料追溯表</p>
		                        <div class="t_r ch-kp-twentySeven">
		                            <div class="t_r_t" id="t_r_t5">
		                                <div class="t_r_title" id="t_r_title5">
		                                	<table class="col-sm-12">
		                                       <tr>
		                                            <th width='50px'>工厂</th>
		                                            <th width='120px'>物料编码</th>
		                                            <th width='350px'>物料描述</th>
		                                            <th width='200px'>单据号／行号</th>
		                                            <th width='90px'>计划交货日期</th>
		                                            <th width='90px'>实际交货日期</th>
		                                            <th width='90px'>订单数量</th>
		                                            <th width='90px'>收货数量</th>
		                                            <th>MRP控制者</th>
		                                        </tr>
		                                        <span style="width:17px;display:inline-block;height:4px;"></span>
		                                    </table>
		                                </div>
		                            </div>
		                            <div class="t_r_content" id="t_r_content5" onscroll="ee()">
		                                <table>
		
		                                </table>
		                                <!-- <div id='show4' style="width:100%;text-align:center;">数据正在加载完成</div> -->
		                            </div>
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
   
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS18-table.js"></script>
</body>
</html>
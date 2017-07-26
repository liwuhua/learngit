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
        var plant = "${plant}";
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS39.css">
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
                                		<span><b class="type">产成品</b><input class="ccp" type="text"><span class="btn1 btnAll"></span></span>
		                                <span><b class="type">工序名称</b><input class="gxmc" type="text"><span class="btn2 btnAll"></span></span>
		                                <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn3 btnAll"></span></span>
		                                <span><b class="type">销售订单</b><input class="xsdd" type="text"><span class="btn4 btnAll"></span></span>
	                                   	<span><b class="type">销售订单行号</b><input class="xsddhh" type="text"><span class="btn5 btnAll"></span></span>
	                                   	<span><b class="type">工厂</b><input class="factory" type="text"><span class="btn6 btnAll"></span></span>
	                                   	<span><b class="type">工作中心</b><input class="gzzx" type="text"><span class="btn7 btnAll"></span></span>
	                                   	<span><b class="type">下工序</b><input class="xqdw" type="text"><span class="btn8 btnAll"></span></span>
	                                   	<span><b class="type">生产订单类型</b><input class="scddlx" type="text"><span class="btn9 btnAll"></span></span>
	                                   	<span><b class="type">日能力</b><input class="rnl" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"></span>
	                                   	<span class="setTime">
	                                       	<b>计划产出日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   onfocus="startTimeFocus();this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="endTimeFocus();this.blur()">
	                                   	</span>
	                                   	<span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                     </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                		<!-- 表格显示 -->
		                <div class="row">
		                    <p class="table-name">作业计划、工序能力平衡表</p>
		                    <p class="table-data" style="overflow: hidden;"><b style="float: right;"><span  class="dataTime1"></span><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>
		                    <div class="t_r thirtyNineTable">
	                            <div class="t_r_t" id="t_r_t">
	                                <div class="t_r_title">
	                                    <table>
	                                        
	                                    </table>
	                                </div>
	                            </div>
	                            <div class="t_r_content" id="t_r_content" onscroll="II()">
	                                <table>
	
	                                </table>
	                                <div id='show' style="width:100%;text-align:center;color:#484848;">数据正在加载中...</div>
	                            </div>
	                            <div class="t_r_b" id="t_r_b">
	                                <div class="t_r_foot">
	                                    <table>
	                                        
	                                    </table>
	                                </div>
	                            </div>
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
   
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS39-table.js"></script>
    <script type="text/javascript">
    function startTimeFocus() {
        return WdatePicker({
            skin : 'whyGreen',
            minDate:'#F{$dp.$D(\'endTime\',{d:-90});}',
            maxDate : '#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',
            doubleCalendar:true,
            dateFmt:'yyyy-MM-dd'
        });
    }
     
    function endTimeFocus() {
        return WdatePicker({
            skin : 'whyGreen',
            minDate:'#F{$dp.$D(\'startTime\')}',
            maxDate : '#F{$dp.$D(\'startTime\',{d:90})||\'%y-%M-%d\'}',
            doubleCalendar:true, 
            dateFmt:'yyyy-MM-dd'
        });
    }

    </script>
</body>
</html>
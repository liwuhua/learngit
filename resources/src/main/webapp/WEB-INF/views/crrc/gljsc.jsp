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
<!-- <!DOCTYPE html> -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8" />
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <title>DPI</title>
    <script>
    	var ctx = "${ctx}";
    	
    </script>
    <link href="${contextPath}/styles/crrc/css/gljsc.css" rel="stylesheet" type="text/css" />
    <%-- <link href="${contextPath}/styles/crrc/css/gljsc-1024.css" rel="stylesheet" type="text/css" />  --%>
    <script type="text/javascript">
    //以下进行测试
    var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (client_w>1000&&client_w<1200) {
    	document.write('<link href="' + ctx + '/styles/crrc/css/gljsc-1024.css" rel="stylesheet" type="text/css" />');
        
    }else if (client_w>1200&&client_w<1500) {
    	document.write('<link href="' + ctx + '/styles/crrc/css/gljsc-1366.css" rel="stylesheet" type="text/css" />');
        
    }else if (client_w>1900) {
    	document.write('<link href="' + ctx + '/styles/crrc/css/gljsc-1920.css" rel="stylesheet" type="text/css" />');
        
    }
    </script>
</head>
<body id="body">
	<div id='Mask'>
		<img style='width:100%;height:100%;' alt="" src="${contextPath}/styles/common/images/zhezhao.png">
	</div>
	<div id='Mask2'>
		<img style='width:100%;height:100%;' alt="" src="${contextPath}/styles/common/images/zhezhao.png">
	</div>
    <div id='Mask3'>
    	<img style='width:100%;height:100%;' alt="" src="${contextPath}/styles/common/images/zhezhao.png">
    </div>
    <div id='Mask-s'>
    	<img style='width:100%;height:100%;' alt="" src="${contextPath}/styles/common/images/zhezhao.png">
    </div>
	<!-- 管理驾驶舱 -->
    <div id="wrappers" style="background-color:#f5f5f5;overflow: hidden;">
    	<div class="logo-jsc">
    		<img alt="中国中车股份有限公司" src="${contextPath}/styles/common/images/gljsc-logo.png">
    		<a href="${contextPath}/logout">注销</a>
    		<a href="${contextPath}/crrc/index">进入报表主题</a>
    	</div>
    	<div id="index-head">
    		<div class="fl">管理驾驶舱</div>
    		<div id="date-time" class="fr" ><input type="text" class="data-time" onfocus="this.blur()"></div>
    	</div>
    	<div id="jsc-content">
    		<div class="row" style="width:100%;">
    			<div class="divs fl" style="width:54%;">
	    			<h3><span class='fl'>经营管理与财务目标</span><%-- <img class='fl' style='display:none;margin-top:5px;margin-left:10px;height:20px;' alt="" src="${contextPath}/styles/common/images/191.png"> --%><span id="deadline1" class="fr"></span></h3>
	    			<div class="cont clear">
	    				<div id="jsc_1" class="fl">
                            <div id="jsc-1"></div>
                            <div class="tuli">
                                <p><s></s><b>营业收入</b><span></span></p>
                                <p class="last-child"><s></s><b>净利润</b><span></span></p>
                            </div>
                            <div class='seris'>
                            	<img style='width:100%;' alt="" src="${contextPath}/styles/common/images/seris.png">
                            </div>               
                        </div>
	    				<div id="jsc-2" class="fl"></div>
	    			</div>
	    		</div>
	    		<div class="fl" style="width:45%;margin-left:1%;">
	    			<h3>市场管理目标</h3>
	    			<div class="cont clear">
	    				<div id="scgl-srzb" class="fl">
                            <div id="scgl-sr" class="zbwcBox" style='position:relative;'>
                                <p class="s_name">销售收入指标及完成情况</p>
                                <p  class="jh"><s></s>销售目标<span></span></p>
                                <p  class="zd"><s></s>实际销售收入<span></span></p>
                                <div class="red-flag"> 
                                	<img alt="" src="${contextPath}/styles/common/images/flag.png"> 
                                </div>
                                <div id="xssr-zbwc">
                                	<p id="xssr"></p>
                                </div>
                                <div class='srwcl'></div>
                            </div>
                            <div id="ndht">
                                <p class="zb">年度合同签约指标：<b style='text-align:right;display:inline-block;'><span></span>万元</b></p>
                                <p class="wc">年度合同累计签订：<b style='text-align:right;display:inline-block;'><span></span>万元</b></p>
                            </div>

		    			</div>
                        <div id="shgl-xssrjwcqk" class="fl">
                            <div class="bt">
                            	<div class='fr'>
                            		<img alt="" src="${contextPath}/styles/common/images/xiazuan.png">
                            	</div>
                            	<p>
                            		<b style="background:#908F8F"></b>
                            		<span style="margin-right: 5px;">销售目标(万元)</span>
                            		<b style="background:#C70019"></b>
                            		<span>累计完成(万元)</span>
                            	</p>
                            	
                            </div>
                            <div id="pie0"></div>
                            <div id="pie-0" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie1"></div>
                            <div id="pie-1" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie2"></div>
                            <div id="pie-2" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie3"></div>
                            <div id="pie-3" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie4"></div>
                            <div id="pie-4" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie5"></div>
                            <div id="pie-5" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                            <div id="pie6"></div>
                            <div id="pie-6" class="quan">
                            	<img alt="" src="${contextPath}/styles/common/images/circle-kong1.png">
                            </div>
                        </div>
		    		</div>
                    <!-- <div class="scBox" style="display: none;">
                        <div class="tabBox">
                            <ul></ul>
                        </div>
                        <p class="can">X</p>
                        <div class="tableBox">

                        </div>
                        <div class="t_r_b hjBox" id="t_r_b">
                            <div class="t_r_title">
                                <table style="width:100%;border-left: 1px solid #DDDDDD;">

                                </table>
                            </div>
                        </div>
                    </div> -->
	    		</div>
   			</div>
	   		<div class="row" style="width:100%;">
	   			<div class="divs fl" style="width:54%;">
	   				<h3>生产管理目标</h3>
	   				<div class="cont clear">
	   					<div id='scgl-yx' class="fl">
	   						<%--<div style="height:60px;font-size:14px;padding-left:10px;line-height:30px;">--%>
                            <div class='scgl-yx'>
	   							<p style="margin:0;" class="yxddzdl">营销订单正点率<span style="margin-left:5px;"></span></p>
	   							<p style="margin:0;" class="cpzdj">产品正点交付率<span style="margin-left:5px;"></span></p>
	   						</div>
	   						<div class="cpqk-title" >
	   							<span class='cpwc'>产品完成 </span>
	   							<span class='cpwwc'>累计未完成 </span>
	   						</div>
	   						<div class='scgl-cpzs'>
	   							<%--<div id="scmb-1-1" class="fl" style="width:160px;height:110px;"></div>
	   							<div id="scmb-1-2" class="fl" style="width:160px;height:110px;"></div>--%>
                                <!-- <div class="fl kuang1"></div> -->
                                <div id="scmb-1-1" class="fl"></div>
                                <div id="scmb-1-2" class="fl"></div>
                                <!-- <div class="fl kuang2"></div> -->
	   						</div>
	   						<div class="danwei">(单位: 台件)</div>
	   						<!-- 产品正交付率一级下钻 -->
	   						
	   					</div>
	    				<div class="fl" id="jsc-sc-dd"></div>
	   				</div>
	   			</div>
	   			<div class="divs fl" style="width:45%;margin-left:1%;">
	   				<h3>采购仓储管理目标</h3>
	   				<div class="cont clear" id="cggl-zjzy" style="position:relative;">
	   					<div class="col-xs-4 fl" id="gljsc-cg-dh" style='position:relative;'>
	   						<p class="name">采购到货正点率<span></span></p>
	   						<p class="jh"><s></s>采购计划<span></span></p>
	   						<p class="zd"><s></s>采购正点<span></span></p>
	   						<div class="dh-chart" id="dh-chart">
	   							<p id="cg"></p>
	   						</div>
	   						<div class='zdl'></div>
	   						<div class='xiazuan xiazuan1'><img alt="" src="${contextPath}/styles/common/images/xiazuan.png"></div>
	   						<div class='xiazuan xiazuan2'><img alt="" src="${contextPath}/styles/common/images/xiazuan.png"></div>
	   					</div>
	    				<div class="fl cggl-zjzy" id="cggl-zjzy-ycl"></div>
	    				<div class="fl cggl-zjzy" id="cggl-zjzy-ccp"></div>
	    				<div id="cggl-zjzy-tb1">
	    					<span>原材料</span>
	    					<span>产成品</span>
	    				</div>
	   				</div>
	   			</div>
	   		</div>
	   		<div  class="row" id="zlhshglmb" style="width:100%;position: relative;">
	   			<h3>质量和售后管理目标<span id="deadline5" class="fr"></span></h3>
	   			<div class="cont clear">
	   				<!-- 质量损失趋势 -->
                    <div id="ssqs" style="width:42%;">
                        <div id="zlhshglmb-ssqs" class="fl"></div>
                    </div>

	   				<!-- 质量问题分布 -->
                    <div class="zlwtfb" style="width:22%;float:left;">
                        <div id="zlhshglmb-cwss" class="fl zlhshglmb-ss"></div>
                        <div id="zlhshglmb-cnss" class="fl zlhshglmb-ss" style="display:none;"></div>
                        <div id="zlhshglmb-tb1"><span>厂外损失</span><span>厂内损失</span></div>
                    </div>

	    			<!-- 产品故障统计 -->
	                <div class="cpgztj" class="fl" style="width:36%;position:relative;">
	                	<div id="zl-cpgz-tb2"></div>
	                	<div class='cpgztj-title'>产品故障统计</div>
	                	<div id="zl-cpgz" class="zlhshglmb-gz">
		    				<div id="tldjs1" class="fl tldjs"></div>
		    				<div id="tldjs2" class="fl tldjs"></div>
		    			</div>
		    			<div id='zl-cpgz-text'></div>
	                </div>
	   			</div>
	   		</div>
	    </div>
    </div>
    <div id="date"></div>
    <script type="text/javascript" src="${contextPath}/styles/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/highcharts.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/highcharts-more.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-cggl-1.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-cggl-2.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-jygl-1.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-jygl-2.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-scgl-1.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-scgl-2.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-scmb-1.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-scmb-2.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-zlsh-1.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-zlsh-2.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/gljsc-zlsh-3.js"></script>
</body>
</html>
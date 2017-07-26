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
    <link href="${contextPath}/styles/crrc/css/FS01.css" rel="stylesheet" type="text/css" />
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
        var plant = "${plant}";
        var pur_group = "${pur_group}";
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
                                    <span><b class="type">工厂</b><input class="factory" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">供应商</b><input class="supplier" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">采购组</b><input class="purchase" type="text"><span class="btn4 btnAll"></span></span>
                                    <span class="setTime"> 
                                        <b>入库日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
                                    </span>
                                    <span class="trading"><b class="type">是否显示公司间交易业务</b><input type="radio" name="yesno" checked>是<input type="radio" name="yesno">否</span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">采购入库金额统计表</p>
                        <p class="table-data">
                        	<b>货币单位：元</b>
                        	<b style="float: right;">
                        		<span  class="dataTime1"></span>
                        		<c:if test="${showExp eq 1}">
                        			<img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export">
                        		</c:if>
                        		<img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol">
                        	</b>
                        </p>
                        <div class="t_r ch-kp-one" style="width: auto;">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                            <th width='40px' class='xh'>序号</th>
                                            <th width='40px' class='werks'>工厂</th>
                                            <th width='110px' class='matnr'>物料编码</th>
                                            <th width='380px' class='txtmd'>物料描述</th>
                                            <th width='90px' class='sum_menge'>入库数量</th>
                                            <th width='70px' class='meins' >计量单位</th>
                                            <th width='135px' class='sum_bualt'>入库金额（不含税）</th>
                                            <th width='70px' class='price_base'>价格单位</th>
                                            <th width='90px' class='lifnr'>供应商编码</th>
                                            <th width='270px' class='vendor'>供应商描述</th>
                                            <th class='ekgrp'>采购组</th>
                                        </tr>
                                        <span style="width:17px;display:inline-block;height:0px;"></span>
                                    </table>
                                </div>
                            </div>
                            <div class="t_r_content" id="t_r_content" onscroll="aa()">
                                <table>

                                </table>
                                <div id='show' style="width:100%;text-align:center;">数据正在加载中...</div>
                            </div>
                            <div class="toTop"></div>
                        </div>
                    	<div class='hj'>
	                    	<p>合计<span style="padding-left: 20px;">入库数量：<b></b></span><span style="padding-left: 20px;">入库金额：<b></b></span></p>
	                    	<div class="lr"><<</div>
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
    <script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS01-table.js"></script>
</body>
</html>

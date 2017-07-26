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
    <link href="${contextPath}/styles/crrc/css/FS3001.css" rel="stylesheet" type="text/css" />
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
                                    <span><b class="type">公司代码：</b><input class="gongsi" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">供应商</b><input class="supplier" type="text"><span class="btn2 btnAll"></span></span>
                                    <span class="setTime"> 
                                        <b>日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
                                    </span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">应付账款统计分析表</p>
                        <p class="table-data"><span class="bianzhi"></span><b style="margin-left: 20px;">货币单位:元</b><b style="float: right;"><span  class="dataTime1"></span><c:if test="${showExp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></b></p>

                        <div class="t_r ch-kp-thirty-one" style="width: auto;">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                            <th width='40px' colspan='1' rowspan='2' class='xh'>序号</th>
                                            <th width='40px' colspan='1' rowspan='2' class='werks'>公司代码</th>
                                            <th width='110px' colspan='1' rowspan='2' class='matnr'>公司名称</th>
                                            <th width='380px' colspan='1' rowspan='2' class='txtmd'>供应商编号</th>
                                            <th width='90px' colspan='1' rowspan='2' class='sum_menge'>供应商名称</th>
                                            <th width='70px' colspan='1' rowspan='2' class='meins' >特别总账标识</th>
                                            <th width='135px' colspan='1' rowspan='2' class='sum_bualt'>统驭科目</th>
                                            <th width='135px' colspan='1' rowspan='2' class='sum_bualt'>初期余额</th>
                                            <th width='135px' colspan='1' rowspan='2' class='sum_bualt'>采购额（包含暂估）</th>
                                            <th colspan='3' rowspan='1' class='sum_bualt'>当期采购额</th>
                                            <th colspan='6' rowspan='1' class='sum_bualt'>当期付款情况</th>
                                            <th width='135px' colspan='1' rowspan='2' class='sum_bualt'>期末余额</th>
                                            <th colspan='7' rowspan='1' class='sum_bualt'>应付账款账龄</th>
                                            <th colspan='8' rowspan='1' class='sum_bualt'>其中：质保金账龄</th>
                                        </tr>
                                        <tr>
                                            <th width='40px' colspan='1' rowspan='1' class='xh'>采购额</th>
                                            <th width='40px' colspan='1' rowspan='1' class='werks'>进项税金</th>
                                            <th width='110px' colspan='1' rowspan='1' class='matnr'>应付账款</th>
                                            <th width='380px' colspan='1' rowspan='1' class='txtmd'>小计</th>
                                            <th width='90px' colspan='1' rowspan='1' class='sum_menge'>现金</th>
                                            <th width='70px' colspan='1' rowspan='1' class='meins' >银行承兑</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>商业承兑</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>云信</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>抵账</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>6个月以内</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>6个月-1年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>1-2年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>2-3年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>3-4年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>4-5年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>5年以上</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>6个月以内</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>6个月-1年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>1-2年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>2-3年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>3-4年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>4-5年</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>5年以上</th>
                                            <th width='135px' colspan='1' rowspan='1' class='sum_bualt'>质保金小计</th>
                                        </tr>
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS3001-table.js"></script>
</body>
</html>

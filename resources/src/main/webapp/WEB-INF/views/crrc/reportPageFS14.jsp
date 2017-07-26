<%--
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
<!DOCTYPE html> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <title>DPI</title>
    <link href="${contextPath}/styles/crrc/css/FS14.css" rel="stylesheet" type="text/css" />
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
        var comp_code = "${comp_code}";/* 公司代码  */
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
                <div class="row">
                    <div class="col-sm-12">
                        <!--start form表单 查询条件-->
                        <form>
                            <p>
                            	<span><b class="type">工厂</b><input class="factory" type="text"><span class="btn1 btnAll"></span></span>
                                <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn2 btnAll"></span></span>
                                <span><b class="type">物料组</b><input class="wlz" type="text"><span class="btn3 btnAll"></span></span>
                                <span><b class="type">评估类</b><input class="pgl" type="text"><span class="btn4 btnAll"></span></span>
                                <span><b class="type">库存地点</b><input class="kcdd" type="text"><span class="btn5 btnAll"></span></span>
                                <span><b class="type">管库员（库存仓位）</b><input class="gky" type="text"><span class="btn6 btnAll"></span></span>
                                <span><b class="type">库存状态</b><input class="kczt" type="text"><span class="btn7 btnAll"></span></span>
                                <span><b class="type">特殊库存标识</b><input class="tskcbz" type="text"><span class="btn8 btnAll"></span></span>
                                <span><b class="type">截止查询时间N天无动态</b><input class="jzcxsj" type="text" onkeyup="javascript:CheckInputIntFloat(this);"></span>
                                <span><b class="type">查询时间(必填)</b><input class="checkDate" type="text" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'})" onfocus="this.blur()" style="width:90px;"></span>
                                <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                            </p>
                        </form>
                        <!--end 查询条件  -->
                        <div class="retract">收起  ∧</div>
                    </div>
                </div>
                <!--未查询到数据显示框 -->
                <div class='notSearch'></div>
                <!-- //表格显示 -->
                <div class="row">
                    <p class="table-name">存货构成及货龄表</p>
                    <p class="table-data" style="overflow: hidden;"><input type="button" value="二级明细" id="btnChange"><span class="bianzhi"></span><b style="margin-left: 20px;">货币单位:元</b><b style="float: right;"><span  class="dataTime1"></span></b></p>
                    <!--start 一级明细  -->
                    <div class="ch-kp-fourteen1">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12" width="100%">
                                    <tr>
                                        <th rowspan='2' width='40px' class='xh'>序号</th>
                                        <th rowspan='2' width='50px' class='WERKS'>工厂</th>
                                        <th rowspan='2' width='60px' class='VAL_CLASS'>评估类</th>
                                        <th rowspan='2' width='120px' class='VALTXT'>评估类描述</th>
                                        <th rowspan='2' width='110px' class='MATNR'>物料编码</th>
                                        <th rowspan='2' width='350px' class='ARKTX'>物料描述</th>
                                        <th rowspan='2' width='50px' class='MATL_GROUP'>物料组</th>
                                        <th rowspan='2' width='130px' class='TXTSH' >物料组描述</th>
                                        <th rowspan='2' width='60px' class='LGORT'>库存地点</th>
                                        <th rowspan='2' width='130px' class='LOCTXT'>库存地描述</th> 
                                        <th rowspan='2' width='60px' class='LGPBE'>库存仓位</th>
                                        <th rowspan='2' width='60px' class='MEINS'>计量单位</th>
                                        <th rowspan='2' width='60px' class='PRICE_BASE'>价格单位</th>
                                        <th rowspan='2' width='100px' class='MATPRICE'>单价</th>
                                        <th rowspan='2' class='SOBKZ' width='80px'>最近发料日期</th>
                                        <th rowspan='2' class='SOBKZ' width='80px'>最近发料数量</th>
                                        <th rowspan='2' class='SOBKZ' width='120px'>最近发料金额</th>
                                        <th rowspan='2' class='SOBKZ' width='80px'>期末库存数量</th>
                                        <th rowspan='2' class='SOBKZ' width='120px'>期末库存金额</th>
                                        <th colspan='12'>其中</th>
                                    </tr>
                                    <tr>
                                    	<th width='100px'>1-3个月(数量)</th>
                                    	<th width='100px'>1-3个月(金额)</th>
                                    	<th width='100px'>4-6个月(数量)</th>
                                    	<th width='100px'>4-6个月(金额)</th>
                                    	<th width='110px'>半年到一年(数量)</th>
                                    	<th width='110px'>半年到一年(金额)</th>
                                    	<th width='110px'>一年到两年(数量)</th>
                                    	<th width='110px'>一年到两年(金额)</th>
                                    	<th width='110px'>两年到三年(数量)</th>
                                    	<th width='110px'>两年到三年(金额)</th>
                                    	<th width='110px'>三年以上(数量)</th>
                                    	<th>三年以上(金额)</th>
                                    </tr>
                                    <span style="width:17px;display:inline-block;height:0px;"></span>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content" onscroll="II()">
                            <table>
								
                            </table>
                            <div id='show1' style="width:100%;text-align:center;">数据加载完成</div>
                        </div>
                        <div class="t_r_b" id="t_r_b">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width:100%;">

                                </table>
                            </div>
                        </div>
                    </div>
					<!--end 一级明细  -->
					<!--start 二级明细  -->
                    <div class="ch-kp-fourteen2">
                        <div class="t_r_t" id="t_r_t2">
                            <div class="t_r_title">
                                <table class="col-sm-12">
                                    <tr>
                                        <th rowspan='2' width='40px' class='xh'>序号</th>
                                        <th rowspan='2' width='50px' class='WERKS'>工厂</th>
                                        <th rowspan='2' width='60px' class='VAL_CLASS'>评估类</th>
                                        <th rowspan='2' width='120px' class='VALTXT'>评估类描述</th>
                                        <th rowspan='2' width='110px' class='MATNR'>物料编码</th>
                                        <th rowspan='2' width='350px' class='ARKTX'>物料描述</th>
                                        <th rowspan='2' width='50px' class='MATL_GROUP'>物料组</th>
                                        <th rowspan='2' width='130px' class='TXTSH' >物料组描述</th>
                                        <th rowspan='2' width='60px' class='LGORT'>库存地点</th>
                                        <th rowspan='2' width='130px' class='LOCTXT'>库存地描述</th> 
                                        <th rowspan='2' width='60px' class='LGPBE'>库存仓位</th>
                                        <th rowspan='2' width='60px' class='MEINS'>计量单位</th>
                                        <th rowspan='2' width='60px' class='PRICE_BASE'>价格单位</th>
                                        <th rowspan='2' width='100px' class='MATPRICE' style='text-align:right;'>单价</th>
                                        <th rowspan='2' width='90px'>物料库存类型</th>
                                        <th rowspan='2' width='90px'>库存状态</th>
                                        <th rowspan='2' width='80px'>最近发料日期</th>
                                        <th rowspan='2' width='80px' style='text-align:right;'>最近发料数量</th>
                                        <th rowspan='2' width='120px' style='text-align:right;'>最近发料金额</th>
                                        <th rowspan='2' width='80px' style='text-align:right;'>期末库存数量</th>
                                        <th rowspan='2' width='120px' style='text-align:right;'>期末库存金额</th>
                                        <th colspan='12' style='text-align:center;'>其中</th>
                                    </tr>
                                    <tr>
                                    	<th width='100px' style='text-align:right;'>1-3个月(数量)</th>
                                    	<th width='100px' style='text-align:right;'>1-3个月(金额)</th>
                                    	<th width='100px' style='text-align:right;'>4-6个月(数量)</th>
                                    	<th width='100px' style='text-align:right;'>4-6个月(金额)</th>
                                    	<th width='110px' style='text-align:right;'>半年到一年(数量)</th>
                                    	<th width='110px' style='text-align:right;'>半年到一年(金额)</th>
                                    	<th width='110px' style='text-align:right;'>一年到两年(数量)</th>
                                    	<th width='110px' style='text-align:right;'>一年到两年(金额)</th>
                                    	<th width='110px' style='text-align:right;'>两年到三年(数量)</th>
                                    	<th width='110px' style='text-align:right;'>两年到三年(金额)</th>
                                    	<th width='110px' style='text-align:right;'>三年以上(数量)</th>
                                    	<th style='text-align:right;'>三年以上(金额)</th>
                                    </tr>
                                    <span style="width:17px;display:inline-block;height:0px;"></span>
                                </table>
                            </div>
                        </div>
                       <div class="t_r_content" id="t_r_content2" onscroll="hh()">
                            <table>
								
                            </table>
                            <div id='show2' style="width:100%;text-align:center;">数据正在加载中...</div>
                        </div>
                        <div class="t_r_b" id="t_r_b2">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width:100%;">

                                </table>
                            </div>
                        </div>
                    </div>
                    <!--end 二级明细  -->
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
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS14-table.js"></script>
</body>
</html>

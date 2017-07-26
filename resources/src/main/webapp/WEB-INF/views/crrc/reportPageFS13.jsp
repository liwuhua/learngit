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
    <link href="${contextPath}/styles/crrc/css/FS13.css" rel="stylesheet" type="text/css" />
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
                    <div class="row">
                        <div class="col-sm-12">
                            <form>
                                <p>
                                    <span><b class="type">工厂</b><input class="factory" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">库存地点</b><input class="kcdd" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">管库员（库存仓位）</b><input class="gky" type="text"><span class="btn4 btnAll"></span></span>
                                    <span><b class="type">供应商</b><input class="supplier" type="text"><span class="btn5 btnAll"></span></span>
                                    <span><b class="type">供应商类别</b><input class="supplier-lb" type="text"><span class="btn6 btnAll"></span></span>
                                    <span class="setTime"> 
                                        <b>库存天数</b><input type="text" class="startDay" onkeyup="javascript:CheckInputIntFloat(this);"> - <input type="text" class="endDay" onkeyup="javascript:CheckInputIntFloat(this);">
                                    </span>
                                    <span><b class="type">查询日期（必填）</b><input class="checkDate" type="text" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-%d'})" onfocus="this.blur()" style="width:90px;"></span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">物料库龄明细表</p>
                        <p class="table-data"><b>货币单位：元</b><b style="float: right;"><span  class="dataTime1"></span><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>
                        <div class="t_r ch-kp-thirteen">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                            <th width='40px' class='xh'>序号</th>
                                            <th width='60px' class='WERKS'>工厂</th>
                                            <th width='80px' class='LGORT'>库存地点</th>
                                            <th width='100px' class='LOCTXT'>库存地描述</th> 
                                            <th width='70px' class='MATL_GROUP'>物料组</th>
                                            <th width='120px' class='TXTSH' >物料组描述</th>
                                            <th width='80px' class='VAL_CLASS'>评估类</th>
                                            <th width='150px' class='VALTXT'>评估类描述</th>
                                            <th width='80px' class='LGPBE'>库存仓位</th>
                                            <th width='120px' class='MATNR'>物料编码</th>
                                            <th width='300px' class='ARKTX'>物料描述</th>
                                            <th width='120px' class='KDAUF'>销售订单号</th>
                                            <th width='120px' class='KDPOS'>销售订单行项目</th>
                                            <th width='120px' class='LIFNR'>分包商编码</th>
                                            <th width='200px' class='VENTXT'>分包商名称</th>
                                            <th width='120px' class='CHARG'>批次</th>
                                            <th width='120px' class='SERNR'>序列号</th>
                                            <th width='120px' class='STDAT'>入库时间</th>
                                            <th width='120px' class='STCOU'>库存天数</th>
                                            <th width='120px' class='MEINS'>计量单位</th>
                                            <th width='120px' class='BWMNG'>库存数量</th>
                                            <th width='120px' class='PRICE_BASE'>价格单位</th>
                                            <th width='120px' class='MATPRICE'>单价</th>
                                            <th width='120px' class='kcje'>库存金额</th>
                                            <th width='120px' class='LIFNR'>供应商代码</th>
                                            <th width='200px' class='VENTXT'>供应商名称</th>
                                            <th class='XASUPPTY'>供应商物料类别</th>
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
	                    	<p>合计<span style="padding-left: 20px;">库存数量：<b></b></span><span style="padding-left: 20px;">库存金额：<b></b></span></p>
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS13-table.js"></script>
</body>
</html>

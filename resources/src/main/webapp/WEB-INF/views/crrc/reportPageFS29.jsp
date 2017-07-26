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
    <link href="${contextPath}/styles/crrc/css/FS29.css" rel="stylesheet" type="text/css" />
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
                        <!-- form表单 -->
                        <form>
                            <p>
                                <span><b class="type">工厂</b><input class="werksValue" type="text"><span class="btn1 btnAll"></span></span>
                                <span><b class="type">销售订单</b><input class="vbelnValue" type="text"><span class="btn2 btnAll"></span></span>
                                <span><b class="type">销售订单行号</b><input class="dwerkValue" type="text"><span class="btn3 btnAll"></span></span>
                                <span><b class="type">生产订单</b><input class="vkorgValue" type="text"><span class="btn5 btnAll"></span></span>
                                <span><b class="type">生产订单物料编码</b><input class="matnrValue" type="text"><span class="btn4 btnAll"></span></span>
                                <span><b class="type">生产订单类型</b><input class="dauatValue" type="text"><span class="btn6 btnAll"></span></span>
                                <span><b class="type">工作中心</b><input class="vendorValue" type="text"><span class="btn7 btnAll"></span></span>
                                <span><b class="type">产品型号</b><input class="cpxh" type="text"><span class="btn13 btnAll"></span></span>
                                <span><b class="type">组件采购类型：</b><input class="comppurtype" type="text"><span class="btn8 btnAll"></span></span>
                                <span><b class="type">组件物料编码</b><input class="zjwlbm" type="text"><span class="btn12 btnAll"></span></span>
                                <span><b class="type">采购组</b><input class="purGroupValue" type="text"><span class="btn9 btnAll"></span><input type="checkbox" class="purGroupValuePt">排他</span>
                                <span><b class="type">供应商编码：</b><input class="oneValue" type="text"><span class="btn10 btnAll"></span><input type="checkbox" class="oneValuePt">排他</span>
                                <span><b class="type">配送方式</b><input class="twoValue" type="text"><span class="btn11 btnAll"></span><input type="checkbox" class="twoValuePt">排他</span>
                                <!-- <span><b class="type">模拟库存量</b><input type="text" class="mnkclMin" style="width: 70px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">-<input type="text" class="mnkclMax" style="width: 70px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></span> -->
                                <span>
                                	<b class="type" style='margin-right:5px;margin-left:15px;'>模拟库存量</b>
                                	<b style='margin-right:0px;margin-left:5px;'>大于等于零</b>
                                	<input class="mnkclMax" checked='checked' type="checkbox">
                                	<b style='margin-right:0px;margin-left:5px;'>小于零</b>
                                	<input style='margin-right:15px;' class="mnkclMin" checked='checked' type="checkbox">
                                </span>
                                <span class="setTime">
                                    <b>基本开始日期</b><input type="text" id="startTime1" class="startTime1" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime1" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()">
                                </span>
                                <span class="setTime">
                                    <b>报告日期（必填）</b><input type="text" id="startTime2" class="startTime2" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()">
                                    <!--  - <input type="text" id="endTime2" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()"> -->
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
                <div class="row" style="position:relative;">
                    <p class="table-name">生产订单缺料统计分析表</p>
                    <%--<p class="table-data"><b style="float: right;"><span  class="dataTime1"></span><c:if test="${showExp eq 1}"><img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>--%>
                    <p class="table-data">
                    	<b style="float: right;">
                    		<span  class="dataTime2"></span>
                    		<c:if test="${showExp eq 1}">
                    			<img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export">
                    		</c:if>
                    		<c:if test="${showimp eq 1}">
                    			<img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import">
                    		</c:if>
                    		<img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol">
                    	</b>
                    </p>
                    <div class="t_r ch-kp-twentynine">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12">
                                    <tr>
                                        <th width='40px' class="xh">序号</th>
                                        <th width='80px' class='DELIDATE'>报告日期</th>
                                        <th width='50px' class='WERKS'>工厂</th>
                                        <th width='130px' class='ZHWCX'>工作中心</th>
                                        <th width='100px' class='VBLINE'>销售订单/行号</th>
                                        <th width='90px' class='RT_SIZE2'>产品型号</th>
                                        <th width='100px' class='AUFNR'>生产订单</th>
                                        <th width='120px' class='MATNR'>生产订单物料编码 </th>
                                        <th width='190px' class='MATNRTXTMD'>生产订单物料编码描述 </th>
                                        <th width='100px' class='GSTRP'>基本开始日期</th>
                                        <th width='120px' class='COPOMATNUM'>组件物料编码</th>
                                        <th width='300px' class='ARKTX'>组件物料描述</th>
                                        <th width='70px' class='ZHWCX'>需求量</th>
                                        <th width='80px' class='ANALINVEN'>模拟库存量</th>
                                        <th width='60px' class='MEINS'>计量单位</th>
                                        <th width='120px' class='DUCUMNUM'>单据号/行号</th>
                                        <th width='100px' class='ORDERNUM'>订单数量</th>
                                        <th width='90px' class='PLANDELIDATE'>计划交货日期</th>
                                        <th width='90px' class='ACTUDELIDATE'>实际交货日期</th>
                                        <th width='80px' class='DELIVNUM'>收货数量</th>
                                        <th width='180px' class='EKGRP'>采购组</th>
                                        <th class='DISPO'>MRP控制者</th>
                                        <!-- <th width='100px' class='CAUSEANALY'>原因分析</th>
                                        <th width='120px' class='ECPECARRIVDATE'>预计到货日期</th>
                                        <th class='ISREQUIPLAN'>是否满足生产计划要求</th> -->
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

                <div class="importBox">
                    <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                    <div style="width:100%;">
                        <div class="chooseFile">
                            <span class="fl">选择文件</span>
                            <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS29/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
                            <span class="fileupload-file" style="padding-left: 20px;"></span>
                        </div>
                        <div id="progress" class="progress">
                            <div class="bar" style="width: 0%;"></div>
                        </div>
                        <div class="downloadTable">

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
<script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
<script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
<script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
<script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS29-table.js"></script>
</body>
</html>

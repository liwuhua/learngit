<%--

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
    <link href="${contextPath}/styles/crrc/css/FS07.css" rel="stylesheet" type="text/css" />
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
                        <!-- form表单 -->
                        <form>
                            <p>
                                <span><b class="type">公司代码：</b><input class="gongsi" type="text"><span class="btn1 btnAll"></span></span>
                                <span><b class="type">客户编码：</b><input class="kehu" type="text"><span class="btn2 btnAll"></span></span>
                                <span class="setTime">
                                    <b>年月－年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()">
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
                    <p class="table-name">应收账款统计分析表</p>
                    <p class="table-data" style="overflow: hidden;">
                    	<span>编制单位：<span class='bianzhi'></span></span>
                    	<b style="float: right;">
                    		<span  class="dataTime1"></span>
                    		<c:if test="${showExp eq 1}">
                    			<img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export">
                    		</c:if>
                    		<c:if test="${showimp eq 1}">
                    			<img alt="" title="上传" src="${contextPath}/styles/common/images/right/import.png" class="import">
                    		</c:if>
                    	</b>
                    </p>
                    <div class="t_r ch-kp-seven">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12">
                                    <tr>
                                        <th rowspan="2" width="50px" class="xh">序号</th>
                                        <th rowspan="2" width="60px">公司代码</th>
                                        <th rowspan="2" width="160px">公司名称</th>
                                        <th rowspan="2" width="85px">客户编码</th>
                                        <th rowspan="2" width="250px">客户名称</th>
                                        <th rowspan="2" width="100px">统驭科目</th>
                                        <th rowspan="2" width="150px">当期销售收入（不含税）</th>
                                        <th rowspan="2" width="110px">期初余额</th>
                                        <th colspan="5">当期销售收入</th>
                                        <th colspan="6">当期回款情况</th>
                                        <th rowspan="2" width="120px">期末余额</th>
                                        <th colspan="7">应收账款账龄</th>
                                        <th colspan="8">其中：质保金账龄</th>
                                    </tr>
                                    <tr>
                                        <th width="120px">小计</th>
                                        <th width="120px">主营业务收入</th>
                                        <th width="120px">其他业务收入</th>
                                        <th width="120px">销项税金</th>
                                        <th width="120px">其他</th>
                                        <th width="120px">小计</th>
                                        <th width="120px">现金</th>
                                        <th width="120px">银行承兑</th>
                                        <th width="120px">商业承兑</th>
                                        <th width="120px">云信</th>
                                        <th width="120px">抵账</th>
                                        <th width="120px">6个月以内</th>
                                        <th width="120px">6个月—1年</th>
                                        <th width="120px">1—2年</th>
                                        <th width="120px">2—3年</th>
                                        <th width="120px">3—4年</th>
                                        <th width="120px">4—5年</th>
                                        <th width="120px">5年以上</th>
                                        <th width="120px">6个月以内</th>
                                        <th width="120px">6个月—1年</th>
                                        <th width="120px">1—2年</th>
                                        <th width="120px">2—3年</th>
                                        <th width="120px">3—4年</th>
                                        <th width="120px">4—5年</th>
                                        <th width="120px">5年以上</th>
                                        <th>小计</th>
                                    </tr>
                                    <span style="width:17px;display:inline-block;height:0px;"></span>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content" onscroll="aa()">
                            <table>
								
                            </table>
                            <div id='show1' style="width:100%;text-align:center;">数据正在加载中</div>
                        </div>
                    </div>
                </div>

             <!-- //文件导入 -->
                <div class="importBox">
                    <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                    <div style="width:100%;">
                        <div class="chooseFile">
                            <span class="fl">选择文件</span>
                            <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS07/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS07-table.js"></script>
</body>
</html>

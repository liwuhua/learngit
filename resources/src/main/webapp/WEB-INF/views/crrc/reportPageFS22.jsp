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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS22.css">
    <script>
        var ctx = "${ctx}";
        var comp_code = "${comp_code}";/* 公司代码  */
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
                <div id='Mask2'><img alt="" src="${contextPath}/styles/common/images/loading.gif"><b>数据正在加载中</b></div>

                <div class="row">
                    <div class="col-sm-12">
                        <!-- form表单 -->
                        <form>
                            <p>
                                <span><b class="type">公司代码：</b><input class="gongsi" type="text"><span class="btn1 btnAll"></span></span>
                                <span><b class="type">内部订单</b><input class="internalOrder" type="text"><span class="btn2 btnAll"></span></span>
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
                    <p class="table-name">研发费用明细表</p>
                    <p class="table-data" style="overflow: hidden;"><input type="button" value="二级明细" id="btnChange"><span class="bianzhi"></span><b style="margin-left: 20px;">货币单位:元</b><b style="float: right;"><span  class="dataTime1"></span><c:if test="${showExp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><c:if test="${showimp eq 1}"><img alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></b></p>
                    <div class="t_r ch-kp-twentyTwo1">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width: 100%;">
                                    <tr>
                                        <th colspan="7">研发项目</th>
                                        <th rowspan="2" width="120px">材料费</th>
                                        <th rowspan="2" width="120px">燃料费</th>
                                        <th rowspan="2" width="120px">动力费用</th>
                                        <th rowspan="2" width="120px">人工工资</th>
                                        <th rowspan="2" width="120px">折旧费</th>
                                        <th rowspan="2" width="90px">无形资产摊销</th>
                                        <th rowspan="2" width="120px">工卡模具费</th>
                                        <th rowspan="2" width="120px">试验检验费</th>
                                        <th rowspan="2" width="240px">研发成果论证、鉴定、评审、验收费用</th>
                                        <th rowspan="2" width="120px">专利费</th>
                                        <th rowspan="2" width="120px">技术开发费</th>
                                        <th rowspan="2" width="120px">技术转让费</th>
                                        <th rowspan="2" width="100px">技术资料费用</th>
                                        <th rowspan="2" width="120px">差旅费</th>
                                        <th rowspan="2" width="120px">办公费</th>
                                        <th rowspan="2" width="120px">修理费</th>
                                        <th rowspan="2">其他</th>
                                    </tr>
                                    <tr>
                                        <th width="100px">项目号</th>
                                        <th width="280px">项目描述</th>
                                        <th width="130px">项目总预算</th>
                                        <th width="130px">以前年度累计发生额</th>
                                        <th width="110px">当年项目预算</th>
                                        <th width="100px">当年累计发生额</th>
                                        <th width="130px">当期发生额</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content" onscroll="II()">
                            <table>

                            </table>
                            <div id='show1' style="width:100%;text-align:center;">数据正在加载中...</div>
                        </div>
                        <div class="toTop"></div>
                        <div class="t_r_b" id="t_r_b">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width:100%;">

                                </table>
                            </div>
                        </div>
                    </div>


                    <div class="t_r ch-kp-twentyTwo2">
                        <div class="t_r_t" id="t_r_t2">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width:100%;">
                                    <tr>
                                        <th colspan="7">研发项目</th>
                                        <th colspan="10">材料费</th>
                                        <th width="120px">燃料费</th>
                                        <th colspan="6">动力费用</th>
                                        <th width="110px">人工工资</th>
                                        <th colspan="3">折旧费</th>
                                        <th colspan="2">无形资产摊销</th>
                                        <th colspan="3">工卡模具费</th>
                                        <th width="100px">试验检验费</th>
                                        <th width="140px">研发成果论证、鉴定、<br>评审、验收费用</th>
                                        <th width="100px">专利费</th>
                                        <th width="110px">技术开发费</th>
                                        <th width="110px">技术转让费</th>
                                        <th colspan="2">技术资料费用</th>
                                        <th colspan="2">差旅费</th>
                                        <th colspan="10">办公费</th>
                                        <th colspan="9">修理费</th>
                                        <th colspan="7">其他</th>
                                    </tr>
                                    <tr>
                                        <th width="100px">项目号</th>
                                        <th width="280px">项目描述</th>
                                        <th width="130px">项目总预算</th>
                                        <th width="130px">以前年度累计发生额</th>
                                        <th width="130px">当年项目预算</th>
                                        <th width="100px">当年累计发生额</th>
                                        <th width="100px">当期发生额</th>
                                        <th width="100px">成本费用-<br>原材料消耗</th>
                                        <th width="100px">成本费用-<br>外购半成品消耗</th>
                                        <th width="120px">成本费用-<br>自制半成品消耗</th>
                                        <th width="100px">成本费用-<br>产成品消耗</th>
                                        <th width="100px">成本费用-<br>辅助生产成本</th>
                                        <th width="100px">成本费用-<br>包装物消耗</th>
                                        <th width="100px">成本费用-<br>低值易耗品消耗</th>
                                        <th width="100px">成本费用-<br>自制半成品转出</th>
                                        <th width="100px">成本费用-<br>加工费</th>
                                        <th width="100px">成本费用-<br>产成品转出</th>
                                        <th width="100px">成本费用-<br>燃料消耗</th>
                                        <th width="90px">研发动能分摊</th>
                                        <th width="90px">作业-直接人工</th>
                                        <th width="90px">作业-间接人工</th>
                                        <th width="110px">作业-<br>固定制造费用</th>
                                        <th width="110px">作业-<br>其它变动费用</th>
                                        <th width="100px">作业-动能</th>
                                        <th width="110px">成本费用-研发费-<br>研发中心费用</th>
                                        <th width="120px">成本费用-<br>房屋建筑物-折旧费</th>
                                        <th width="100px">成本费用-<br>仪器-折旧费</th>
                                        <th width="100px">成本费用-<br>设备-折旧费</th>
                                        <th width="120px">成本费用-软件-<br>无形资产摊销</th>
                                        <th width="140px">成本费用-非专利技术-<br>无形资产摊销</th>
                                        <th width="100px">模具领用</th>
                                        <th width="100px">成本费用-<br>工具费</th>
                                        <th width="100px">成本费用-<br>专用工卡模具费</th>
                                        <th width="100px">成本费用-<br>试验检验费</th>
                                        <th width="140px">成本费用-研发费-<br>技术评审费</th>
                                        <th width="100px">成本费用-<br>研发费-专利费</th>
                                        <th width="120px">成本费用-<br>研发费-技术开发费</th>
                                        <th width="120px">成本费用-<br>研发费-技术转让费</th>
                                        <th width="120px">成本费用-研发费-<br>技术资料费用</th>
                                        <th width="120px">成本费用-研发费-<br>标准资料费用</th>
                                        <th width="100px">成本费用-<br>研发费-差旅费</th>
                                        <th width="100px">成本费用-差旅费</th>
                                        <th width="110px">成本费用-<br>办公费-办公用品</th>
                                        <th width="100px">成本费用-<br>办公费-电话费</th>
                                        <th width="110px">成本费用-<br>办公费-会议用品</th>
                                        <th width="100px">成本费用-<br>办公费-年鉴费</th>
                                        <th width="120px">成本费用-<br>办公费-软件服务费</th>
                                        <th width="120px">成本费用-<br>办公费-图书资料费</th>
                                        <th width="110px">成本费用-<br>办公费-宣传经费</th>
                                        <th width="100px">成本费用-<br>办公费-印刷费</th>
                                        <th width="110px">成本费用-<br>办公费-邮资电报</th>
                                        <th width="100px">成本费用-<br>办公费-展览费</th>
                                        <th width="120px">成本费用-<br>修理费-车间维修费</th>
                                        <th width="110px">成本费用-<br>修理费-大修费用</th>
                                        <th width="130px">成本费用-修理费-<br>大修费用-设备</th>
                                        <th width="110px">成本费用-修理费-<br>动力设备</th>
                                        <th width="110px">成本费用-修理费-<br>房屋维修</th>
                                        <th width="110px">成本费用-修理费-<br>机械设备</th>
                                        <th width="100px">本费用-修理费-<br>微机修理</th>
                                        <th width="100px">成本费用-<br>修理用备件消耗</th>
                                        <th width="100px">成本费用-<br>修理费-其他</th>
                                        <th width="120px">成本费用-研发费-<br>其他研发费用</th>
                                        <th width="100px">成本费用-<br>包装费</th>
                                        <th width="100px">成本费用-<br>安全措施费</th>
                                        <th width="120px">成本费用-职工薪酬-<br>福利费-经费</th>
                                        <th width="130px">成本费用-职工薪酬-<br>劳动保护费用</th>
                                        <th>成本费用-其他</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content2" onscroll="hh()">
                            <table>

                            </table>
                            <div id='show2' style="width:100%;text-align:center;">数据正在加载中...</div>
                        </div>
                        <div class="toTop1"></div>
                        <div class="t_r_b" id="t_r_b2">
                            <div class="t_r_title">
                                <table class="col-sm-12" style="width:100%;">

                                </table>
                            </div>
                        </div>
                    </div>


                </div>


                <div class="importBox">
                    <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                    <div style="width:100%;">
                        <div class="chooseFile">
                            <span class="fl">选择文件</span>
                            <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS22/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
<!-- END wrapper -->
<!-- jQuery  -->
<script src="${contextPath}/styles/common/js/bootstrap.min.js"></script>
<!-- upload -->
<script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
<script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
<!--DatePicker-->
<script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS22-table.js"></script>

</body>
</html>

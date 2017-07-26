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
    <link href="${contextPath}/styles/crrc/css/FS06.css" rel="stylesheet" type="text/css" />
    <script>
        var ctx = "${contextPath}";
        var url = "${url}";
        var salesOff = "${salesOff}";<!-- 销售部门 -->
        var salesOrg = "${salesOrg}";<!-- 销售组织 -->
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
                                <span><b class="type">物料编码</b><input class="material" type="text"><span class="btn1 btnAll"></span></span>
                                <span><b class="type">客户编码</b><input class="kunnrValue" type="text"><span class="btn2 btnAll"></span></span>
                                <span><b class="type">销售组织</b><input class="vkorgValue" type="text"><span class="btn3 btnAll"></span></span>
                                <span><b class="type">销售部门</b><input class="vkburValue" type="text"><span class="btn4 btnAll"></span></span>
                                <span><b class="type">销售合同类型</b><input class="auartValue" type="text"><span class="btn5 btnAll"></span></span>
                                <span><b class="type">销售合同号</b><input class="vbeln" type="text"><span class="btn6 btnAll"></span></span>
                                <span class="setTime">
                                	<b>合同单据日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  onfocus="this.blur()">
                            	</span>
                                <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                            </p>
                        </form>
                        <div class="retract">收起  ∧</div>
                    </div>
                </div>
                <!-- 未查询到数据显示框 -->
                <div class='notSearch'></div>
                <div class="row ch-kp-six">
                    <p class="table-name"><span style="font-size:12px;margin-top:4px;float:left;margin-left:15px;">货币单位:  元</span>合同执行情况统计表<span style="float: right;margin-right: 20px;"><c:if test="${showExp eq 1}"><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></span></p>

                    <div class="t_r ch-kp-six">
                        <div class="t_r_t" id="t_r_t">
                            <div class="t_r_title">
                                <table class="col-sm-12" border="1">
                                    <tr>
                                        <th width='50px' class="xh">序号</th>
                                        <th width='70px' class='vkbur'>业务部门</th>
                                        <th width='90px' class='kunnr'>客户编码</th>
                                        <th width='220px' class='txtmd'>客户描述</th>
                                        <th width='150px' class='matnr'>物料编码</th>
                                        <th width='350px' class='arktx'>物料描述</th>
                                        <th width='80px' class='vbeln'>销售合同</th>
                                        <th width='80px' class='posnr'>合同行项目</th>
                                        <th width='90px' class='erdat'>合同创建日期</th>
                                        <th width='60px' class='zmeng' style="text-align: right;">合同数量</th>
                                        <th width='100px' class='unit' style="text-align: right;">合同单价</th>
                                        <th width='120px' class='sum_unit' style="text-align: right;">合同金额</th>
                                        <th width='120px' class='ddcount' style="text-align: right;">订单数量（合计）</th>
                                        <th width='70px' class='fhcount' style="text-align: right;">发货数量</th>
                                        <th width='70px' class='kpcount' style="text-align: right;">开票数量</th>
                                        <th width='70px' class='ddstock'>订单库存</th>
                                        <th class='uddstock'>非订单库存</th>
                                    </tr>
                                    <span style="width:17px;display:inline-block;height:4px;"></span>
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
                <div class="table-detail">
                    <div class="detail-del"><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol2"><img alt="" src="${contextPath}/styles/common/images/del.png" class="del"></div>
                    <div class="t_r" id="t_r">
                        <div class="t_r_t" id="t_r_t2">
                            <div class="t_r_title" id="t_r_title2">
                                <table class="col-sm-12">
                                    <tr>
                                        <th width='50px' class="xh2">序号</th>
                                        <th width='70px' class='lips_erdat'>发货时间</th>
                                        <th width='90px' class='kunnr'>送达方</th>
                                        <th width='250px' class='kunnr'>送达方名称</th>
                                        <th width='90px' class='lips_vbeln'>发货单据号</th>
                                        <th width='90px' class='lgmng' style="text-align: right;">本期发货数量</th>
                                        <th width='250px' class='sernr'>产品序列号</th>
                                        <th width='80px' class='kdauf'>销售订单</th>
                                        <th width='70px' class='kdpos'>订单行项目</th>
                                        <th width='80px' class='vbak_erdat'>订单创建日期</th>
                                        <th width='60px' class='kwmeng' style="text-align: right;">订单数量</th>
                                        <th width='70px' class='vbrp_erdat'>开票时间</th>
                                        <th width='70px' class='fklmg' style="text-align: right;">开票数量</th>
                                        <th class='vbrp_vbeln'>发票号</th>
                                    </tr>
                                    <span style="width:17px;display:inline-block;height:4px;"></span>
                                </table>

                            </div>
                        </div>
                        <div class="t_r_content" id="t_r_content2" onscroll="bb()">
                            <table>

                            </table>
                            <div id='show2' style="width:100%;text-align:center;">数据正在加载中...</div>
                        </div>
                        <div class="toTop1"></div>
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
                                        <td style="width:50px;text-align: center;"><input type="checkbox" class="allCheck" checked='checked'></td>
                                        <td>字段名称</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content">
                            <table>

                            </table>
                        </div>
                    </div>
                    <p class="btns"><input type="button" value="取消" class="canBtns"><input type="button" value="确定" class="sureBtns"></p>
                </div>

                <div class='hideColBox2'>
                    <p class='pClose2'><span class='closeBtn2'></span></p>
                    <div class="t_r hideTable2">
                        <div class="t_r_t">
                            <div class="t_r_title">
                                <table>
                                    <tr style='background-color:#ededed;'>
                                        <td style="width:50px;text-align: center;"><input type="checkbox" class="allCheck2" checked='checked'></td>
                                        <td>字段名称</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="t_r_content">
                            <table>

                            </table>
                        </div>
                    </div>
                    <p class="btns2"><input type="button" value="取消" class="canBtns2"><input type="button" value="确定" class="sureBtns2"></p>
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
<script type="text/javascript" src="${contextPath}/styles/crrc/js/FS06-table.js"></script>
</body>
</html>
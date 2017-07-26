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
    <link rel="stylesheet" href="${contextPath}/styles/crrc/css/FS04.css">    
    <script>
        <%--console.log("${pageContext.request.scheme}");--%>
        <%--console.log("${pageContext.request.serverName}");--%>
        <%--console.log("${pageContext.request.serverPort}");--%>
        /* console.log("${pageContext.request.contextPath}"); */
        var ctx = "${ctx}";
        var fs04_pur_group = "${pur_group}";/* 采购组 */
        var plant = "${plant}";/* 工厂 */
        /* console.log("----"+ctx); */
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
                                    <span><b class="type">采购订单</b><input class="ebelnValue" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">采购组</b><input class="purGroupValue" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">工厂</b><input class="plantValue" type="text"><span class="btn4 btnAll"></span></span>
                                    <span><b class="type">供应商</b><input class="vendorValue" type="text"><span class="btn5 btnAll"></span></span>
                                    <span><b class="type">时间差异（n）天</b><input class="dataDay" type="text" placeholder="多值用逗号隔开"></span>
                                    <span class="setTime">
                                        <b>实际收货日期</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"  onfocus="this.blur()">
                                    </span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row">
                        <p class="table-name">物料分批次到货统计表<span><c:if test="${showExp eq 1}"><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"></c:if><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></span></p>
                            <div class="t_r ch-kp-four">
                                <div class="t_r_t" id="t_r_t">
                                    <div class="t_r_title">
                                        <table class="col-sm-12" >
                                            <tr>
                                                <th width='40px' class='xh'>序号</th>
                                                <th class='matnr' width='120px'>物料编码</th>
                                                <th class='matnrtxtmd' width='320px'>物料描述</th>
                                                <th class='ebeln' width='100px'>采购订单</th>
                                                <th class='ebelp' width='90px'>采购订单行号</th>
                                                <th class='banfn' width='100px'>采购申请</th>
                                                <th class='lfdat' width='120px'>采购申请交货期日</th>
                                                <th class='lfdat' width='120px'>采购订单交货期日</th>
                                                <th class='menge' width='90px'>实际收货数量</th>
                                                <th class='budat' width='90px'>实际收货日期</th>
                                                <th class='timediff' width='130px'>申请交货时间差异</th>
                                                <th class='timediff1' width='130px'>订单交货时间差异</th>
                                                <th class='ekgrp' width='50px'>采购组</th>
                                                <th class='werks' width='60px'>工厂</th>
                                                <th class='lifnr' width='120px'>供应商编号</th>
                                                <th class='lifnrtxtmd'>供应商描述</th>
                                            </tr>
                                            <span style="width:17px;display:inline-block;height:4px;"></span>
                                        </table>

                                    </div>
                                </div>
                                <div class="t_r_content" id="t_r_content" onscroll="aa()">
                                    <table>

                                    </table>
                                    <div id='show' style="width:100%;text-align:center;">信息正在加载中。。。</div>
                                </div>
                                <div class="toTop"></div>
                            </div>
                        </div>

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

    <!--DatePicker-->
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS04-table.js"></script>   
</body>
</html>

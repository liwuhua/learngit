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
    <%--<link href="${contextPath}/styles/crrc/css/FS44.css" rel="stylesheet" type="text/css" />--%>
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
                                    <span><b class="type">工厂</b><input class="werksValue" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">工作中心</b><input class="vendorValue" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">组件采购类型</b><input class="dauatValue" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">采购组</b><input class="purGroupValue" type="text"><span class="btn4 btnAll"></span><input type="checkbox" class="purGroupValuePt">排他</span></span>
                                    <span><b class="type">供应商编码</b><input class="oneValue" type="text"><span class="btn5 btnAll"></span><input type="checkbox" class="oneValuePt">排他</span></span>
                                    <span><b class="type">配送方式</b><input class="twoValue" type="text"><span class="btn6 btnAll"></span><input type="checkbox" class="twoValuePt">排他</span></span>
                                    <span class="setTime">
                                        <b>报告日期（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
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
                        <p class="table-name">物料拉动前后对比表</p>
                        <p class="table-data"><b style="float: right;"><span  class="dataTime1"></span></b></p>
                        <div class="t_r ch-kp-fortyFour">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                        	<th width='40px' class='xh'>序号</th>
                                            <th width='40px' class='WERKS'>工厂</th>
                                            <th width='110px' class='KTEXT'>工作中心</th>
                                            <th width='100px' class='DELIDATE'>报告日期</th>
                                            <th width='130px' class='BEFORE_ORDERNUM'>拉动前缺件订单数</th>
                                            <th class='AFTER_ORDERNUM'>拉动后缺件订单数</th>
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS44-table.js"></script>
</body>
</html>

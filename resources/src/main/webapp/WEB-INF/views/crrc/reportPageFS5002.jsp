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
    <link href="${contextPath}/styles/crrc/css/FS5002.css" rel="stylesheet" type="text/css" />
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
                                    <span><b class="type">信息编号</b><input class="xxbh" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">配属局段</b><input class="psjd" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">产品型号</b><input class="cpxh" type="text"><span class="btn4 btnAll"></span></span>
                                    <span><b class="type">故障部位</b><input class="gzbw" type="text"><span class="btn5 btnAll"></span></span>
                                    <span><b class="type">事故类别</b><input class="sglb" type="text"><span class="btn6 btnAll"></span></span>
                                    <span><b class="type">严重度</b><input class="yzd" type="text"><span class="btn7 btnAll"></span></span>
                                    <span><b class="type">产品寿命阶段</b><input class="cpsmjd" type="text"><span class="btn8 btnAll"></span></span>
                                    <!-- <span><b class="type">境内/境外</b><input class="jnjw" type="text"><span class="btn9 btnAll"></span></span>
                                    <span><b class="type">新造/检修</b><input class="xzjx" type="text"><span class="btn10 btnAll"></span></span> -->
                                    <span><b class="type">供应商</b><input class="gys" type="text"><span class="btn11 btnAll"></span></span>
                                    <span><b class="type">产品编号</b><input class="cpbh" type="text"><span class="btn12 btnAll"></span></span>
                                    <span><b class="type">机车编号</b><input class="jcbh" type="text"><span class="btn13 btnAll"></span></span>
                                    <span><b class="type">质量损失类别编号</b><input class="lbbh" type="text"><span class="btn14 btnAll"></span></span>
                                    <span><b class="type">报告编号</b><input class="bgbh" type="text"><span class="btn15 btnAll"></span></span>
                                    <span style='margin-right:15px;'><b class="type">检修地点</b><input class="jxdd" type="text"><!-- <span class="btn3 btnAll"></span> --></span>
                                    <span style='margin-right:15px;'>
                                        <b class="type">发现时间</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
                                    </span>
                                    <span>
                                        <b>年月</b><input type="text" id="startYearMonth" class="startYearMonth" onclick="WdatePicker({dateFmt: 'yyyy-MM'})"  onfocus="this.blur()">- <input type="text" id="endYearMonth" class="endYearMonth" onclick="WdatePicker({dateFmt: 'yyyy-MM'})" onfocus="this.blur()">
                                    </span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"><c:if test="${showimp eq 1}"><input type="button" value="下载模版" id="downMb" style="width: 60px; background: #c70019;"></c:if><c:if test="${showimp eq 1}"><img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></c:if></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">厂外铁路产品质量信息表-服务中心</p>
                        <p class="table-data"><span>货币单位:   元</span><b style="float: right;"><img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export"><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>
                        <div class="t_r ch-kp-fifty-two">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                            <th width='40px' class='xh'>序号</th>
                                            <th width='60px' class='yf'>月份</th>
                                            <th width='100px' class='xxbh'>信息编号</th>
                                            <th width='120px' class='lbbh'>质量损失类别编号</th>
                                            <th width='110px' class='lbdm'>质量损失类别</th>
                                            <th width='110px' class='yydm'>质量损失原因</th>
                                            <th width='140px' class='psjd'>配属局段</th>
                                            <th width='100px' class='cpsmjd'>产品寿命阶段</th>
                                            <th width='80px' class='jxdd'>检修地点</th>
                                            <th width='80px' class='fxsj' >发现时间</th>
                                            <th width='60px' class='cpxh'>产品型号</th>
                                            <th width='140px' class='cpbh'>产品编号</th>
                                            <th width='40px' class='amount'>数量</th>
                                            <th width='90px' class='xzccrq'>新造出厂日期</th>
                                            <th width='110px' class='zjjxccrq'>最近检修出厂日期</th>
                                            <th width='120px' class='jcbh'>机车编号</th>
                                            <th width='80px' class='zlc'>总运行里程</th>
                                            <th width='80px' class='jxhlc'>检修后里程</th>
                                            <th width='160px' class='gzbw'>故障部位</th>
                                            <th width='400px' class='gzms'>故障描述</th>
                                            <th width='350px' class='cljg'>处理结果</th>
                                            <th width='60px' class='clff'>处理方法</th>
                                            <th width='140px' class='gzwcrq'>现场故障处理完成日期</th>
                                            <th width='80px' class='sglb'>事故类别</th>
                                            <th width='80px' class='yzd'>严重度</th>
                                            <th width='80px' class='zrcp'>责任初判</th>
                                            <th width='120px' class='yyfx'>原因分析</th>
                                            <th width='280px' class='zgcs'>整改措施</th>
                                            <th width='110px' class='bgbh'>报告编号</th>
                                            <th width='90px' class='ss'>损失费用合计</th>
                                            <th width='80px' class='clfy'>材料费用</th>
                                            <th width='80px' class='ysfy'>运输费用</th>
                                            <th width='90px' class='rgfy'>人工差旅费用</th>
                                            <th width='80px' class='qtfy'>其他费用</th>
                                            <th width='80px' class='pc'>赔偿</th>
                                            <th width='70px' class='jnw'>境内/境外</th>
                                            <th width='70px' class='xzjx'>新造/检修</th>
                                            <th width='80px' class='lifnr'>供应商</th>
                                            <th class='bz'>备注</th>
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
                    <!-- 导入 -->
                    <div class="importBox">
                        <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
                        <div style="width:100%;">
                            <div class="chooseFile">
                                <span class="fl">选择文件</span>
                                <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS50/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
                                <span class="fileupload-file" style="padding-left: 20px;"></span>
                            </div>
                            <div id="progress" class="progress">
                                <div class="bar" style="width: 0%;"></div>
                            </div>
                            <div class="downloadTable">

                            </div>
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS5002-table.js"></script>
</body>
</html>

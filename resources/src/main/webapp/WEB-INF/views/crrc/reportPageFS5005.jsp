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
    <link href="${contextPath}/styles/crrc/css/FS5005.css" rel="stylesheet" type="text/css" />
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
                                    <span><b class="type">质量损失类别编号</b><input class="lbbh" type="text"><span class="btn1 btnAll"></span></span>
                                    <span><b class="type">质量损失类别</b><input class="lbdm" type="text"><span class="btn2 btnAll"></span></span>
                                    <span><b class="type">质量损失原因</b><input class="yydm" type="text"><span class="btn3 btnAll"></span></span>
                                    <span><b class="type">项目类型</b><input class="xmlx" type="text"><span class="btn4 btnAll"></span></span>
                                    <span><b class="type">质量问题发生单位</b><input class="fsdw" type="text"><span class="btn5 btnAll"></span></span>
                                    <%--<span><b class="type">产品编码</b><input class="productCode" type="text"><span class="btn5 btnAll"></span></span>--%>
                                    <span><b class="type">责任单位</b><input class="zrdw" type="text"><span class="btn6 btnAll"></span></span>
                                    <!-- <span><b class="type">境内/境外</b><input class="jnw" type="text"><span class="btn7 btnAll"></span></span>
                                    <span><b class="type">新造/检修</b><input class="xzjx" type="text"><span class="btn8 btnAll"></span></span> -->
                                    <span>
                                        <b class="type">发现时间</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" onfocus="this.blur()">
                                    </span>
                                    <span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"><input type="button" value="下载模版" id="downMb" style="width: 60px; background: #c70019;"><img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></span>
                                </p>
                            </form>
                            <div class="retract">收起  ∧</div>
                        </div>
                    </div>
                    <!-- 未查询到数据显示框 -->
                    <div class='notSearch'></div>
                    <div class="row" style="position:relative;">
                        <p class="table-name">厂内产品质量信息表</p>
                        <p class="table-data"><span>货币单位:   元</span><b style="float: right;"><img alt="" title="下载" src="${contextPath}/styles/common/images/right/export.png" class="export"><img title="隐藏列" alt="" src="${contextPath}/styles/common/images/right/hide.png" class="hideCol"></b></p>
                        <div class="t_r ch-kp-fifty-two">
                            <div class="t_r_t" id="t_r_t">
                                <div class="t_r_title">
                                    <table class="col-sm-12">
                                        <tr>
                                            <th width='40px' class='xh'>序号</th>
                                            <th width='70px' class='date'>时间</th>
                                            <th width='60px' class='yymm'>月份</th>
                                            <th width='160px' class='categorynum'>质量损失类别编号</th>
                                            <th width='120px' class='productmodel'>产品型号</th>
                                            <th width='110px' class='categorycode'>质量损失类别</th>
                                            <th width='110px' class='reasoncode'>质量损失原因</th>
                                            <th width='110px' class='occurrunit'>质量问题发生单位</th>
                                            <th width='100px' class='discoprocess'>发现工序</th>
                                            <th width='150px' class='productname' >产品名称</th>
                                            <th width='150px' class='faultnum'>不合格数量（故障数量）</th>
                                            <th width='40px' class='unit'>单位</th>
                                            <th width='420px' class='qualitydesc'>质量问题描述</th>
                                            <th width='140px' class='ducumnum'>不合格品单据编号</th>
                                            <th width='80px' class='noqualistatus'>不合格状态</th>
                                            <th width='100px' class='timeloss'>工时损失</th>
                                            <th width='100px' class='materialloss'>材料损失</th>
                                            <th width='100px' class='otherloss'>其他损失</th>
                                            <th width='140px' class='prelossmonty'>预计损失金额(元)</th>
                                            <th width='140px' class='actlossmonty'>实际损失金额(元)</th>
                                            <th width='100px' class='payaccou'>赔偿金额(元)</th>
                                            <th width='140px' class='dutyunit'>责任单位</th>
                                            <th class='projetype'>项目类型</th>
                                            <!-- <th class='faultnum'>操作标识</th>  -->
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
                                <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportKPI2227/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
    <!-- upload -->
    <script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
    <script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
    <script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/common/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS5005-table.js"></script>
</body>
</html>

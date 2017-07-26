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
    <link href="${contextPath}/styles/crrc/css/FS09.css" rel="stylesheet" type="text/css" />
    <style>
    	.ch-kp-five th{
    		font-size:16px;
    		font-weight:400;
    	}
    </style>
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
                        <div class="row">
                            <div class="col-sm-12">
                                <form>
                                    <p>
                                    	<span><b class="type">公司代码</b><input class="gongsi" type="text"><span class="btn1 btnAll"></span></span>
                                    	<span><b>年月（必填）</b><input type="text" id="startTime" class="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  onfocus="this.blur()"> - <input type="text" id="endTime" class="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  onfocus="this.blur()"></span>
                                    	<span><input id="resetBtn" type="reset" value="重置"><input type="button" value="提交" id="submit"></span>
                                    </p>
                                </form>
                                <div class="retract">收起  ∧</div>
                            </div>
                        </div>
                        <!-- 未查询到数据显示框 -->
                        <div class='notSearch'></div>
                        <div class="row">
                       		<p class="table-name">制造费用明细表</p>
                            <p class="table-data" style='font-size:12px;'><b style="font-weight:500;">编制单位：<strong style="font-weight:500;" class="bianzhi"></strong></b><span style="margin-left:20px;">货币单位：元</span><span style="float:right;font-size:12px;font-weight:500;"><span class="datatime1"></span><img title="下载" alt="" src="${contextPath}/styles/common/images/right/export.png" class="export"><img title="上传" alt="" src="${contextPath}/styles/common/images/right/import.png" class="import"></span></p>
                       		<div class="ch-kp-nine" id="ch-kp-nine">
                                <div class="t_r_t" id="t_r_t">
                                    <div class="t_r_title">
                                        <table class="col-sm-12" width="100%">
                                            <tr>
                                                <th width="300px">项目</th>
                                                <th class='xh' width="40px">行次</th>
                                                <th width="130px">本年数</th>
                                                <th style="padding-right:17px">上年数</th>
                                            </tr>
                                        </table>

                                    </div>
                                </div>
                       			<%--<table id="table1" width="100%" cellspacing="0" cellpadding="0">
	                                 <tr>
	                                      <th>项目</th>
	                                      <th class='xh'>行次</th>
	                                      <th style="text-align:right;">本年数</th>
	                                      <th style="text-align:right;">上年数</th>
	                                  </tr>
	                              </thead>
	                     		</table>--%>
                                <div class="t_r_content" id="t_r_content" >
                                    <table>
                                        <tr>
                                            <td width="300px">制造费用合计</td>
                                            <td class='xh' width="40px">1</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">生产单位管理人员职工薪酬</td>
                                            <td class='xh' width="40px">2</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:2em;" width="300px">其中：工资</td>
                                            <td class='xh' width="40px">3</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">福利费用</td>
                                            <td class='xh' width="40px">4</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">基本养老保险</td>
                                            <td class='xh' width="40px">5</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">基本医疗保险</td>
                                            <td class='xh' width="40px">6</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">失业保险费</td>
                                            <td class='xh' width="40px">7</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">工伤保险费</td>
                                            <td class='xh' width="40px">8</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">生育保险费</td>
                                            <td class='xh' width="40px">9</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">补充养老保险（年金）</td>
                                            <td class='xh' width="40px">10</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">补充医疗保险</td>
                                            <td class='xh' width="40px">11</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">住房公积金</td>
                                            <td class='xh' width="40px">12</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">住房补贴</td>
                                            <td class='xh' width="40px">13</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">工会经费</td>
                                            <td class='xh' width="40px">14</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">职工教育经费 </td>
                                            <td class='xh' width="40px">15</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">非货币性福利</td>
                                            <td class='xh' width="40px">16</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:4em;" width="300px">其他</td>
                                            <td class='xh' width="40px">17</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">固定资产折旧</td>
                                            <td class='xh' width="40px">18</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">长期待摊费用摊销</td>
                                            <td class='xh' width="40px">19</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">租赁费</td>
                                            <td class='xh' width="40px">20</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">物料消耗</td>
                                            <td class='xh' width="40px">21</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">低值易耗品摊销</td>
                                            <td class='xh' width="40px">22</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">取暖费</td>
                                            <td class='xh' width="40px">23</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">水电费</td>
                                            <td class='xh' width="40px">24</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">办公费</td>
                                            <td class='xh' width="40px">25</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">差旅费</td>
                                            <td class='xh' width="40px">26</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">运输费</td>
                                            <td class='xh' width="40px">27</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">保险费</td>
                                            <td class='xh' width="40px">28</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">计量试验检验费</td>
                                            <td class='xh' width="40px">29</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">劳动保护费</td>
                                            <td class='xh' width="40px">30</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">生产用工费</td>
                                            <td class='xh' width="40px">31</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">专用工卡模具费</td>
                                            <td class='xh' width="40px">32</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">技术组织措施费</td>
                                            <td class='xh' width="40px">33</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">搬迁费</td>
                                            <td class='xh' width="40px">34</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                        <tr>
                                            <td style="text-indent:1em;" width="300px">其他</td>
                                            <td class='xh' width="40px">35</td>
                                            <td style="text-align: right;" width="130px"></td>
                                            <td style="text-align: right;"></td>
                                        </tr>
                                    </table>
                                </div>
                       		</div>
                    		
                       		
                        </div>
                    	<div class="importBox">
	                        <p class="ppp"><span class="fl">文件导入</span><span class="fr closeX"></span></p>
	                        <div style="width:100%;">
	                            <div class="chooseFile">
	                                <span class="fl">选择文件</span>
	                                <input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/crrc/reportFS09/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}' accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" />
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
    <script type="text/javascript" src="${contextPath}/styles/crrc/js/FS09-table.js"></script>
</body>
</html>
                                
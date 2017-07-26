<%--
  Created by IntelliJ IDEA.
  User: wangkai
  Date: 16/10/21
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<c:set var="ctx" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8" />
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">
    <title>DPI</title>
    <script>
        var ctx = "${ctx}";
        var url = "${url}";
    </script>
    <script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
</head>
<body class="fixed-left" style='overflow:auto;min-width:1330px;min-height:600px;'>
    <!-- Begin page -->
    <div id="wrapper" style="height: 100%;">
        <!-- Top Bar Start -->
        <div class="topbar">
            <!-- LOGO -->
            <div class="topbar-left">
                <div class="text-center">
                    <a href="${contextPath}/crrc/index" class="logo">
                        <i style="display:inline-block;width:32px;height:32px;margin-top:9px;margin-right:5px;background:url(${contextPath}/styles/common/images/top/logo-small.png) no-repeat;background-size:100%;-ms-background-size: cover;-ms-behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);"></i>
                        <span class="slimScrollBar" style="display:inline-block;width:55px;height:32px;margin-bottom:-3px;background:url(${contextPath}/styles/common/images/top/logo.png) no-repeat;background-size:100%;-ms-background-size: cover;-ms-behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);"></span>
                    </a>
                </div>
            </div>
            <!-- Button mobile view to collapse sidebar menu -->
            <div class="navbar navbar-default" role="navigation" style="height:50px;">
                <div class="container">
                    <div class="">
                        <div class="pull-left">
                            <button class="button-menu-mobile open-left waves-effect waves-light">
                                <img style="width: 18px;" alt="" src="${contextPath}/styles/common/images/top/shrink.png">
                            </button>
                            <c:if test="${isShowKPI eq 1}">
                            <a href="${contextPath}/crrc/gljsc" style="color:#262626;">管理驾驶舱</a>
                            </c:if>
                            <span class="clearfix"></span>
                        </div>

                        <!--  <form role="search" class="navbar-left app-search pull-left hidden-xs">
                             <input type="text" placeholder="搜索..." class="form-control">
                             <a href=""><i class="fa fa-search"></i></a>
                         </form> -->

                        <ul class="nav navbar-nav navbar-right pull-right">
                            <input type="hidden" name="hidd"  value="${is_admin}" id="hid"/>
                            <input type="hidden" name="isUserWithCodeManage"  value="${isUserWithCodeManage}" id="isUserWithCodeManage"/>
                            <li id="li_manager" style="display:none;">
                                <a href="${contextPath}/management/index">后台管理</a>
                            </li>


                            <li class="dropdown user-box">
                                <a href="${contextPath}/logout">注销</a>
                            </li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <!-- Top Bar End -->
        <!-- ========== Left Sidebar Start ========== -->
        <div class="left side-menu">
            <div class="sidebar-inner slimscrollleft">
                <!--- Divider -->
                <div id="sidebar-menu">
                    <ul>
                        <c:forEach var="level1" items="${menu_module}">
                            <li class="has_sub">
                                <a href="javascript:void(0);" class="waves-effect">
                                    <i class="icon1" style="background-image: url(${contextPath}${level1.url });-ms-background-size: cover;-ms-behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);"></i> 
                                    <span> ${level1.name } </span> 
                                </a>
                                <ul class="list-unstyled">
                                    <c:forEach var="level2" items="${level1.children }">
                                        <li>
                                            <a target="box" href="${contextPath}${level2.url }" title="${level2.name }" style="font-size:11px;margin-left:3px;">${level2.name }</a>

                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>						
                        </c:forEach>

                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- Left Sidebar End -->
        <!-- ============================================================== -->
        <!-- Start right Content here -->
        <!-- ============================================================== -->
        <iframe id="frameBox" src="${contextPath}${menu_module[0].children[0].url}" frameborder="0" name="box" style="width:100%;height:100%;display:block;"></iframe>

        <!-- ============================================================== -->
        <!-- End Right content here -->
        <!-- ============================================================== -->
        <!-- /Right-bar -->
    </div>
    <!-- END wrapper -->
    <!-- jQuery  -->
    <script src="${contextPath}/styles/common/js/fastclick.js"></script>
    <script src="${contextPath}/styles/common/js/jquery.app.js"></script>
    
    <script type="text/javascript">
    //判断是管理员还是用户
	if($("#hid").attr("value")=="true" || $("#isUserWithCodeManage").attr("value")=="true"){
		$("#li_manager").css("display","inline-block");
	}else{
		$("#li_manager").css("display","none");
	}
    /* 左侧菜单栏  */
    $('.list-unstyled').off("click").on('click', 'a', function () {
        $('.list-unstyled  a').css("color", "#90aab6")
        $(this).css("color", "#fff");

    })
    $(document).ready(function () {
        $("#sidebar-menu>ul>li:nth-child(1) a:eq(0)").addClass('subdrop');
        $("#sidebar-menu>ul>li:nth-child(1)>ul").show();
    })
    </script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
		<meta name="author" content="Coderthemes">

		
		<title>徳风大数据分析平台 </title>

        <!--Morris Chart CSS -->
		<link rel="stylesheet" href="${contextPath}/styles/chart/css/morris.css">

		<link href="${contextPath}/styles/chart/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/styles/chart/css/menu.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/styles/chart/css/core.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/styles/chart/css/components.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/styles/chart/css/icons.css" rel="stylesheet" type="text/css" />
        <%-- <link href="${contextPath}/styles/chart/css/pages.css" rel="stylesheet" type="text/css" />--%> 
        <link href="${contextPath}/styles/chart/css/responsive.css" rel="stylesheet" type="text/css" /> 

        <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
		
        <script src="${contextPath}/styles/chart/js/modernizr.min.js"></script>
        
	</head>

	<body class="fixed-left">

		<!-- Begin page -->
		<div id="wrapper">

            <!-- Top Bar Start -->
            <div class="topbar">

                <!-- LOGO -->
                <div class="topbar-left">
                    <div class="text-center">
                        <a href="${contextPath}/management/index/indexPortal" class="logo">
                            <i class="zmdi zmdi-toys icon-c-logo"></i><span>德风大数据分析平台</span>
                            <!--<span><img src="${contextPath}/styles/chart/images/logo.png" alt="logo" style="height: 20px;"></span>-->
                        </a>
                    </div>
                </div>

                <!-- Button mobile view to collapse sidebar menu -->
                <div class="navbar navbar-default" role="navigation">
                    <div class="container">
                        <div class="">
                            <div class="pull-left">
                                <button class="button-menu-mobile open-left waves-effect waves-light">
                                    <i class="zmdi zmdi-menu"></i>
                                </button>
                                <span class="clearfix"></span>
                            </div>

                           <!--  <form role="search" class="navbar-left app-search pull-left hidden-xs">
                                <input type="text" placeholder="搜索..." class="form-control">
                                <a href=""><i class="fa fa-search"></i></a>
                            </form> -->
                            
							<ul class="nav navbar-nav navbar-right pull-right">
								<input type="hidden" name="hidd"  value="${is_admin}" id="hid"/>
                            	<li id="li_manager" style="display:none" class="li_manager">
                            		<a href="${contextPath}/management/index"><span class="glyphicon glyphicon-cog"></span>后台系统</a>
                            	</li>
                                <li>
                                    <!-- Notification -->
                                    <div class="notification-box">
                                        <ul class="list-inline m-b-0">
                                            <li>
                                                <a href="javascript:void(0);" class="right-bar-toggle">
                                                    <i class="zmdi zmdi-notifications-none"></i>
                                                </a>
                                                <div class="noti-dot">
                                                    <span class="dot"></span>
                                                    <span class="pulse"></span>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <!-- End Notification bar -->
                                </li>

                                <li class="dropdown user-box">
                                    <a href="" class="dropdown-toggle waves-effect waves-light profile " data-toggle="dropdown" aria-expanded="true">
                                        <img src="${contextPath}/styles/chart/images/users/avatar-1.jpg" alt="user-img" class="img-circle user-img">
                                        <div class="user-status away"><i class="zmdi zmdi-dot-circle"></i></div>
                                    </a>

                                    <ul class="dropdown-menu">
                                        <li><a href="javascript:void(0)"><i class="ti-user m-r-5"></i> 个人档案</a></li>
                                        <li><a href="javascript:void(0)"><i class="ti-settings m-r-5"></i> 设置</a></li>
                                        <li><a href="javascript:void(0)"><i class="ti-lock m-r-5"></i> 锁屏</a></li>
                                        <li><a href="${contextPath}/logout"><i class="ti-power-off m-r-5"></i> 注销</a></li>
                                    </ul>
                                </li>
                            </ul>
                            <%-- <ul class="nav navbar-nav navbar-right pull-right">
                            	<input type="hidden" name="hidd"  value="${is_admin}" id="hid"/>
                            	<li id="li_manager" style="display:none" class="li_manager">
                            		<a href="${contextPath}/management/index"><span class="glyphicon glyphicon-cog"></span>后台系统</a>
                            	</li>
                                <li>
                                    <!-- Notification -->
                                    <div class="notification-box">
                                        <ul class="list-inline m-b-0">
                                            <li>
                                                <a href="javascript:void(0);" class="right-bar-toggle">
                                                    <i class="zmdi zmdi-notifications-none"></i>
                                                </a>
                                                <div class="noti-dot">
                                                    <span class="dot"></span>
                                                    <span class="pulse"></span>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <!-- End Notification bar -->
                                </li>

                                <li class="dropdown user-box">
                                    <a href="" class="dropdown-toggle waves-effect waves-light profile " data-toggle="dropdown" aria-expanded="true">
                                        <img src="${contextPath}/styles/chart/images/users/avatar-1.jpg" alt="user-img" class="img-circle user-img">
                                        <div class="user-status away"><i class="zmdi zmdi-dot-circle"></i></div>
                                    </a>

                                    <ul class="dropdown-menu">
                                        <li><a href="javascript:void(0)"><i class="ti-user m-r-5"></i> 个人档案</a></li>
                                        <li><a href="javascript:void(0)"><i class="ti-settings m-r-5"></i> 设置</a></li>
                                        <li><a href="javascript:void(0)"><i class="ti-lock m-r-5"></i> 锁屏</a></li>
                                        <li><a href="${contextPath}/logout"><i class="ti-power-off m-r-5"></i> 注销</a></li>
                                    </ul>
                                </li>
                            </ul> --%>

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
                            
                            <li class="has_sub">
                                <a href="${contextPath}/management/index/indexPortal" class="waves-effect active"><i class="zmdi zmdi-view-dashboard"></i> <span> 娱乐行业栏目组 </span> </a>
                            </li>

                            <li class="has_sub">
                                <a href="javascript:void(0);" class="waves-effect"><i class="zmdi zmdi-invert-colors"></i><span class="label label-default pull-right">1</span> <span> 汽车行业栏目组 </span> </a>
                                <ul class="list-unstyled">
                                    <!-- <li><a href="car.jsp">型号排行</a></li> -->
                                    <li><a href="${contextPath}/management/index/car">型号排行</a></li>
                                </ul>
                            </li>

                            <li class="has_sub">
                                <a href="javascript:void(0);" class="waves-effect active"><i class="zmdi zmdi-collection-text"></i><span class="label label-default pull-right">2</span><span> 旅游行业栏目组 </span> </a>
                                <ul class="list-unstyled">
                                    <li class="active"><a href="${contextPath}/management/index/tourTopic">专题</a></li>
                                    <li><a href="${contextPath}/management/index/tourPicture">旅游拼图</a></li>
                                </ul>
                            </li>
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
            <div class="content-page">
                <div class="content">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-12">
                                
                                <h2 class="page-title">旅游行业栏目组--专题</h2>
                            </div>
                        </div>

                        <!-- <div class="row">
                        	<div class="col-lg-6">
                        		<div class="card-box">
                        			<div id="psLine" style="height:410px;" class="flot-chart"></div> 
                        		</div>
                                
                            </div>
                            <div class="col-lg-6">
                        		<div class="card-box">
                        			<div class="table-responsive">
	                                    <table class="table m-0" style="height:410px">
	                                        <thead>
	                                            <tr>
	                                                <th>编号</th>
	                                                <th>车型</th>
	                                                <th>销量</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody id="tbody">
	                                            
	                                            
	                                        </tbody>
	                                    </table>
                                	</div>
                        		</div>
                        	</div>
                                
                        </div> -->
                        
                        
                        
                        
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
        <script src="${contextPath}/styles/chart/js/jquery.min.js"></script>
        <script src="${contextPath}/styles/chart/js/bootstrap.min.js"></script>
        <!-- <script src="${contextPath}/styles/chart/js/detect.js"></script> -->
        <script src="${contextPath}/styles/chart/js/fastclick.js"></script>
        <!-- <script src="${contextPath}/styles/chart/js/jquery.slimscroll.js"></script> -->
        <!-- <script src="${contextPath}/styles/chart/js/jquery.blockUI.js"></script> -->
        <!-- <script src="${contextPath}/styles/chart/js/waves.js"></script> -->
        <script src="${contextPath}/styles/chart/js/wow.min.js"></script>
        <!-- <script src="${contextPath}/styles/chart/js/jquery.nicescroll.js"></script> -->
        <!-- <script src="${contextPath}/styles/chart/js/jquery.scrollTo.min.js"></script> -->

        
        <!--Chartist Chart-->
        <!-- <script src="assets/plugins/chartist/dist/chartist.min.js"></script>
        <script src="assets/plugins/chartist/dist/chartist-plugin-tooltip.min.js"></script> -->
        <script src="${contextPath}/styles/chart/js/echarts.js"></script>
        <!-- <script src="${contextPath}/styles/chart/js/style.js"></script> -->
       <%--  <script src="${contextPath}/styles/chart/js/jquery.chartist.init.js"></script> --%>


        <!-- App js -->
        <script src="${contextPath}/styles/chart/js/jquery.core.js"></script>
        <script src="${contextPath}/styles/chart/js/jquery.app.js"></script>
		
        	
	        
       
		
	</body>
</html>
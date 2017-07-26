<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<c:set var="ctx" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<html>
<head>
<script type="text/javascript">
if(top!=self){  
   if(top.location != self.location)  
        top.location=self.location;   
}
</script>
<script src="${contextPath}/styles/jquery/jquery-1.9.1.min.js"></script>
<script>
var global_path = '${contextPath}';
var ctx = "${ctx}";
</script>
<script src="${contextPath}/styles/common/js/browser.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>Mobile Web Dissector</title>
<link href="${contextPath}/styles/login/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/styles/login/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/styles/login/themes/css/login.css" rel="stylesheet" type="text/css" />
<!-- form验证 -->
<link rel="stylesheet" href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" type="text/css"/>

</head>

<body>
	<div id="login">
		<div class="login_header">
			<div class="login_logo"><img src="${contextPath}/styles/common/images/users/login_logo.png"/></div>
			<h1>运营数据分析平台</h1>
		</div>
		<div class="login_main" style="background:url('${contextPath}/styles/common/images/users/login_bg.png') no-repeat;background-size:100% 100%;-ms-background-size: cover;-ms-behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);behavior: url(${contextPath}/styles/common/css/backgroundsize.min.htc);">
			<%-- <div class="login_left"><img src="${contextPath}/styles/common/images/users/pic_2.png"/></div> --%>
			<div class="login_right">
				<form method="post" action="${contextPath}/login" id="formID">
					<div style="width:100%;height:24px;">
						<c:if test="${msg!=null }">
							<p style="color: red;">${msg }</p>
						</c:if>
					</div>
					<h2 style="background:url('${contextPath}/styles/common/images/users/login_line.png') no-repeat left bottom">用户登录</h2>
					<p class="login-p1 login-p">
						<input type="text" name="username" class="validate[required] login_input loginInput"
							id="username"  value=""   placeholder="用户名"/>
					</p>
					<p class="login-p2 login-p">
						 <input type="password"
							name="password" class="validate[required] login_input loginInput" id="password" value=""  placeholder="密码" />
					</p>
					<p class="login-p3 login-p">
						<input type="text" id="captcha_key" name="captcha_key"
							class="login_input validate[required,maxSize[6]]" size="6"  placeholder="验证码"/> <span style="margin-left:-66px;"><img
							src="${contextPath}/Captcha.jpg" alt="点击刷新验证码" width="66"
							height="34" id="captcha" /></span>
					</p>
					<p class="login-p4" style="visibility: hidden;">
						 <input type="checkbox" id="rememberMe"
							name="rememberMe" style="border:none\9;"/><label>记住密码</label>
					</p>
					<div class="login_bar loginInput" style="disply: block; float: left;">
						<input class="sub" type="submit" value="登陆" />
					</div>
				</form>
			</div> 
		</div>
		<div class="login_footer">
			<p>中国中车股份有限公司 版权所有 2016 京ICP备15050284号 京公安网安备11010802020546号</p>
		</div>
	</div>
	
	<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>
	<script>
	
    jQuery(document).ready(function(){
        jQuery("#formID").validationEngine();
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "${contextPath}/Captcha.jpg?time=" + new Date());
    		return false;
    	});
    	$(".login_right .login-p input").off("focus").on("focus",function(){
    		$(".login_right input").css("box-shadow","0px 0px 0px 0px rgba(0,0,0,0.2)")
    		$(this).css("box-shadow","0px 0px 3px 3px rgba(155,207,244,0.8)")
    	});
    });
	</script>
	<script>
	$(function(){
		var heights = $(window).height();
		$(".login_main").height((heights-114)+"px");
	});
	</script>
</body>
	
</html>
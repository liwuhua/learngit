<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="dwz" uri="http://www.ketayao.com/dwz"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.yjdj.view.core.constant.CommonConstant"%>
<%@ page import="java.util.List" %><%----%>
<%
    String servletPath = request.getServletPath();
    String sn = servletPath.substring(servletPath.length()-8, servletPath.length()-4);
    if("0201".equals(sn) || "0202".equals(sn)){
        sn = "FS"+sn;
    }
    Boolean showExp = (Boolean)request.getSession().getAttribute(sn + "-" + CommonConstant.FUNCTION_VIEW_EXPORT);
    Boolean showimp = (Boolean)request.getSession().getAttribute(sn + "-" + CommonConstant.FUNCTION_IMPORT);
    if(showExp!=null && showExp){
        request.setAttribute("showExp", 1);
    }
    if(showimp!=null && showimp){
        request.setAttribute("showimp", 1);
    }
    
    Boolean kpiView = (Boolean)request.getSession().getAttribute(CommonConstant.KPI+"-"+CommonConstant.FUNCTION_VIEW);
    if(kpiView!=null && kpiView){
        request.setAttribute("isShowKPI", 1);
    }
    

    List<String> plant = (List<String>)request.getSession().getAttribute(sn + "-" + CommonConstant.PLANT);
    request.setAttribute("plant", plant);
    List<String> pur_group = (List<String>)request.getSession().getAttribute(sn + "-" + CommonConstant.PUR_GROUP);
    request.setAttribute("pur_group", pur_group);
    List<String> comp_code = (List<String>)request.getSession().getAttribute(sn + "-" + CommonConstant.COMP_CODE);
    request.setAttribute("comp_code", comp_code);
    List<String> salesOrg = (List<String>)request.getSession().getAttribute(sn + "-" + CommonConstant.SALESORG);
    request.setAttribute("salesOrg", salesOrg);
    List<String> salesOff = (List<String>)request.getSession().getAttribute(sn + "-" + CommonConstant.SALES_OFF);
    request.setAttribute("salesOff", salesOff);
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="ctx" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="url" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>

<c:set var="plant" value="${plant}" />
<c:set var="pur_group" value="${pur_group}" />
<c:set var="comp_code" value="${comp_code}" />
<c:set var="salesOrg" value="${salesOrg}" />
<c:set var="salesOff" value="${salesOff}" />



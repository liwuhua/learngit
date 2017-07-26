<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script>
	$(function () {
		searchCodeTypesChange(); 
	});
	
	
	function searchCodeTypesChange(){
		var codeTypeId = $('#searchCodeTypes').val();
		var parentId = $('#searchCodeTypes option:selected').attr("data-parentid");
		if(parentId=="" ){
			parentId="00000";
		}
		$("#codeTypeId").val(codeTypeId);
		$('#updateCodeType').attr("href","${contextPath}/management/code/codeTypeUpdate/" + codeTypeId);
		$('#addCodeInfo').attr("href","${contextPath}/management/code/createCode/" + codeTypeId + "/" + parentId);
		$('#codeInfoEdit').attr("href","${contextPath}/management/code/updateCode/" + codeTypeId + "/{slt_uid}"+"/" + parentId);
		$('#deleteCodeTypeForm').attr("action","${contextPath}/management/code/deleteCodeType/" + codeTypeId);
		$('#searchCodeForm').attr("action","${contextPath}/management/code/list?codeTypeId=" + codeTypeId);
		if($('#isAutoLoad').val() != "yes"){
			navTabSearch($('#searchCodeForm'));	
		}
	}
	$("#searchCodeTypes").on("change",function(){
		navTabSearch($("#codeTypeId").val());
	});
	function deleteCodeType(){
		alertMsg.confirm("该动作会删除编码类型及其所属的所有词典数据,确定删除吗？", {
		    okCall: function(){
		    	$("#deleteCodeTypeForm").attr("method", "post");
		    	validateCallback($("#deleteCodeTypeForm"), dialogReloadNavTab);
		    }
		});	
	}
	function ids_i(){
		var len=$(".ids_ids").length;
		var arr_l=[];
		for(var i=0;i<len;i++){
			if($(".ids_ids").eq(i)[0].checked==true){
				arr_l.push($(".ids_ids").eq(i)[0]);
			}
		}
		var len1=arr_l.length;
		if(len1<len){
			$(".checkboxCtrl").prop("checked",false);
		}else{
			$(".checkboxCtrl").prop("checked",true);
		}
	}
</script>

<!-- 删除编码后刷新页面 -->
<dwz:paginationForm action="/resources/management/code/list" page="${page}">
	<input type="hidden" name="codeTypeId" id="codeTypeId"  />
	<input type="hidden" name="isAutoLoad" id="isAutoLoad" value="${isAutoLoad}"  />
</dwz:paginationForm>

<!--编码类型添加 -->
<form method="post" id="searchCodeForm" action="#"  onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width: 500px;">
					<label style="width: 100px;">编码类型：</label>
					<select id="searchCodeTypes" name="codeTypes" onchange="searchCodeTypesChange();">
						<c:forEach var="codeType" items="${codeTypes}">
							<option data-parentid="${codeType.parentId}"  target="slt_codeTypeId" value="${codeType.id}" <c:if test="${codeType.id == codeTypeId}"> selected </c:if>>${codeType.name}</option>
						</c:forEach>
					</select>
					<a  target="dialog"
					rel="lookup2organization_edit" mask="true" width="766" height="400"
					href="${contextPath}/management/code/codeTypeCreate"><span style="background-image: url(${contextPath}/styles/dwz/themes/css/images/toolbar_icons16/user_add.png);">添 加</span></a>
					<a  target="dialog"
					rel="lookup2organization_edit" id="updateCodeType" mask="true" width="766" height="400"
					href="#"><span style="background-image: url(${contextPath}/styles/dwz/themes/css/images/toolbar_icons16/user_edit.png">编 辑</span></a>
					<a id="deleteCodeType" onclick="deleteCodeType();"><span style="background-image: url(${contextPath}/styles/dwz/themes/css/images/toolbar_icons16/user_delete.png">删除</span></a>
				</li>
			</ul>
			<!-- <div class="subBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="submit">搜索</button>
							</div>
						</div>
					</li>
				</ul>
			</div> -->
		</div>
	</div>
</form>

<!-- 添加code 中间部分 -->
<div class="pageContent">
<form id="deleteCodeTypeForm" class="required-validate pageForm" method="post"></form>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a iconClass="user_add" target="dialog"
				rel="lookup2organization_add" id="addCodeInfo" mask="true" width="780" height="500"
				href="#"><span>添加</span></a>
			</li>
			<li><a iconClass="user_edit" id="codeInfoEdit" target="dialog"
				rel="lookup2organization_edit" mask="true" width="766" height="500"
				href="${contextPath }/management/code/updateCode/#/{slt_uid}"><span>编辑</span></a></li>
			<li><a id="a_deleteCode" iconClass="user_delete" target="selectedTodo" rel="ids"
				href="${contextPath }/management/code/deleteCode/${codeTypeId}" title="确认要删除????"><span>删除</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="162" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<!-- <th width="100">一级编码</th>
				<th width="100">二级编码</th>
				<th width="100">三级编码</th>
				<th width="100">四级编码</th> -->
				<th width="100">编码</th>
				<th width="100">名称</th>
				<!-- <th width="100">层次</th> -->
				<th width="100">保留1</th>
				<th width="100">保留2</th>
				<th width="130">保留3</th>
				<th width="130">最后修改者</th>
				<th width="160">最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${codeInfoEntities}">
				<tr target="slt_uid" rel="${item.id}">
                	<td><input name="ids" class="ids_ids" onclick="ids_i();" value="${item.code4}" type="checkbox"></td>
					<%-- <td>${item.code1}</td>
					<td>${item.code2}</td>
					<td>${item.code3}</td>
					<td>${item.code4}</td> --%>
					<td>${item.code4}</td>
					<td>${item.name}</td>
					<%-- <td>${item.level}</td> --%>
					<td>${item.reserve1}</td>
					<td>${item.reserve2}</td>
					<td>${item.reserve3}</td>
					<td>${item.lastUpdateUser}</td>
					<td><fmt:formatDate value="${item.lastUpdateTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}" />
</div>
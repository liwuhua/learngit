<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<form method="post"
		action="${contextPath }/management/code/createCodeType"
		class="required-validate pageForm"
		onsubmit="return validateCallback(this, dialogReloadNavTab);">
		<div class="pageFormContent" layoutH="58">
			<p class="nowrap">
				<label>编码类型ID：</label> <input type="text" name="id"
					style="width: 545px;" value="${codeType.id }" readonly="readonly" />
			</p>
			<p class="nowrap">
				<label>中文名 ：</label> <input type="text" name="name"
					style="width: 545px;" value="${codeType.name }"
					class="validate[required,maxSize[32]] required" size="20"
					maxlength="32" />
			</p>
			<p class="nowrap">
				<label>英文名：</label> <input type="text" name="ename"
					style="width: 545px;" value="${codeType.ename }"
					class="validate[required,minSize[6],maxSize[20]] required"
					size="20" maxlength="32" />
			</p>
			<p class="nowrap">
				<label>父级：</label>
				<select name="parentId" style="width: 545px;" value="${codeType.parentId}">
                      <c:forEach var="codeType" items="${codeTypes}">
							<option  target="slt_codeTypeId" value="${codeType.id}">${codeType.name}</option>
					  </c:forEach>
                </select>
			</p>
			<p class="nowrap">
				<label>编码长度：</label> <input type="text" name="codeLength"
					style="width: 545px;" value="${codeType.codeLength}"
					class="validate[required,custom[integer],min[1],maxSize[32]]" size="20" maxlength="32" />
			</p>        
			<p class="nowrap">
				<label>备注：</label>
				<textarea name="description" style="width: 545px; height: 220px;">${codeType.description}</textarea>
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">确定</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div></li>
			</ul>
		</div>

	</form>
</div>
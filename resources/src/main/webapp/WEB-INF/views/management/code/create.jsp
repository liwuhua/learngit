<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<form method="post"
		action="${contextPath }/management/code/createCode"
		class="required-validate pageForm"
		onsubmit="return validateCallback(this, dialogReloadNavTab);">
		<div class="pageFormContent" layoutH="58">
			<p class="nowrap">
				<label>ID：</label> <input type="text" name="id"
					style="width: 545px;" value="${codeInfo.id }" readonly="readonly" />
			</p>
			<p class="nowrap">
				<label>编码类型ID ：</label> <input type="text" name="codeTypeId"
					style="width: 545px;" value="${codeTypeId}" readonly="readonly" />
			</p>
			<%-- <p class="nowrap">
				<label>一级编码：</label> <input type="text" name="code1"
					style="width: 545px;" value="${codeInfo.code1 }"
					class="validate[required,minSize[2],maxSize[2]] required"
					 maxlength="2" />
			</p>
			<p class="nowrap">
				<label>二级编码：</label> <input type="text" name="code2"
					style="width: 545px;" value="${codeInfo.code2}"
					class="validate[required,minSize[2],maxSize[2]]" size="20" maxlength="32" />
			</p>
			<p class="nowrap">
				<label>三级编码：</label> <input type="text" name="code3"
					style="width: 545px;" value="${codeInfo.code3}"
					class="validate[required,minSize[2],maxSize[2]]" size="20" maxlength="32" />
			</p>
			<p class="nowrap">
				<label>四级编码：</label> <input type="text" name="code4"
					style="width: 545px;" value="${codeInfo.code4}"
					class="validate[required,minSize[2],maxSize[2]]" size="20" maxlength="32" />
			</p> --%>
			<p class="nowrap">
				<label>中文名称：</label> <input type="text" name="name"
					style="width: 545px;" value="${codeInfo.name}"
					class="validate[required,maxSize[32]]  required" size="20" maxlength="32" />
			</p>
			<p class="nowrap">
				<label>英文名称：</label> <input type="text" name="ename"
					style="width: 545px;" value="${codeInfo.ename}"
					class="validate[required,maxSize[32]]  required" size="20" maxlength="32" />
			</p>
			<%-- <p class="nowrap">
				<label>等级：</label>
				<select name="level" style="width: 545px;" value="${codeInfo.level}">
						<option value="1">一级编码</option>
                        <option value="2">二级编码</option>
                        <option value="3">三级编码</option>
                        <option value="4">四级编码</option>
                    </select>
			</p> --%>
			<p class="nowrap">
				<label>描述：</label> <input type="text" name="descript"
					style="width: 545px;" value="${codeInfo.descript}" size="20"
					maxlength="32" />
			</p>
			<p class="nowrap">
				<label>父级：</label>
				<select name="extend" style="width: 545px;" value="${codeInfo.extend}">
                      <c:forEach var="codeinfos" items="${codeinfos}">
							<option  target="slt_codeTypeId" value="${codeinfos.id}">${codeinfos.name}</option>
					  </c:forEach>
                </select>
			</p>
			<p class="nowrap">
				<label>保留字段1：</label> <input type="text" name="reserve1"
					style="width: 545px;" value="${codeInfo.reserve1}" />
			</p>
			<p class="nowrap">
				<label>保留字段2：</label> <input type="text" name="reserve2"
					style="width: 545px;" value="${codeInfo.reserve2}" />
			</p>
			<p class="nowrap">
				<label>保留字段3：</label> <input type="text" name="reserve3"
					style="width: 545px;" value="${codeInfo.reserve3}" />
			</p>
			<p class="nowrap">
				<label>保留字段4：</label> <input type="text" name="reserve4"
					style="width: 545px;" value="${codeInfo.reserve4}" />
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
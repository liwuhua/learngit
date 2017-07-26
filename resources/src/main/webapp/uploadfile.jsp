<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<html lang="en">
<head>
<!--<meta charset="utf-8">-->
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>附件上传</title>
<link href="${contextPath}/styles/upload/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h1>Spring MVC - jQuery File Upload</h1>
    <div style="width:500px;padding:20px">
	<input id="fileupload" type="file" name="files[]" multiple data-url="${contextPath}/file/uploadFile" data-sequential-uploads="true" data-form-data='{"script": "true"}'>
	<div id="progress" class="progress">
            <div class="bar" style="width: 0%;"></div>
	</div>
	<table id="uploaded-files" class="table">
		<tr>
			<th>File Name</th>
			<th>File Size</th>
			<th>File Type</th>
			<th>Download</th>
		</tr>
	</table>
    </div>
</body>
<script src="${contextPath}/styles/upload/js/jquery.1.9.1.min.js"></script>
<script src="${contextPath}/styles/upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.iframe-transport.js"></script>
<script src="${contextPath}/styles/upload/js/jquery.fileupload.js"></script>
<!-- bootstrap just to have good looking page -->
<script src="${contextPath}/styles/upload/bootstrap/js/bootstrap.min.js"></script>
<script src="${contextPath}/styles/upload/js/myuploadfunction.js"></script>
</html>

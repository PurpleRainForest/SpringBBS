<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.bpopup.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.msgbox.min.js"></script>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Member Detail View(File download)</title>
<script type="text/javascript">



	var register = function() {
	}
</script>

<style>
<!--
	#tableCss {width:700px; font-size: 12px;line-height: 20px;}
	#tableCss table {width: 100%; border-collapse: collapse; font-size: 12px; line-height: 20px;}
	#tableCss th {padding: 2px; border: #D3D3D3 solid 1px; text-align: center; background: #F4F4FF;}
	#tableCss td {padding: 2px; border: #D3D3D3 solid 1px; text-align: left;}
	#tableCss tfoot td {padding: 2px; border: #D3D3D3 solid 1px; text-align: center;}
-->
</style>


</head>
<body>
	
	<form id="submitFrm" method="post" action="registerProc.do" encType="multipart/form-data">
		<div id="tableCss">
			<table border="1" id="mtb">
				<tr>
					<th>Name</th> <td>${member.name}</td>
				</tr>	
				<tr>
					<th>Email</th> <td>${member.email}</td>
				</tr>	
				<tr>
					<th>Phone</th> <td>${member.phone}</td>
				</tr>	
				<c:forEach items="${fList}" var="filelist">
					<tr>
						<th>Attach File</th> <td><img width="250" src="upload/${filelist}"><br>
						<a href="download.do?p=upload&f=${filelist}">&gt;down&lt;</a>
						</td>
					</tr>	
				</c:forEach>				

			</table>
		</div>
	<input type="button" class="btn btn-warning btn-sm" onclick="javascript:location.href='${pageContext.request.contextPath}/main.do'" value="Back to ajax mode">
	</form>
	<script src="js/bootstrap.js"></script>
</body>
</html>
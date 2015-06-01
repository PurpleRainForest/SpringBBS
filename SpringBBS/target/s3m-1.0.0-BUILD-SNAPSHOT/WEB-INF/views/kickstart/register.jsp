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
	<title>Member Register Form(File upload)</title>
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
					<th>Name</th> <td><input type="text" name="name" id="name" class="form-control" ></td>
				</tr>	
				<tr>
					<th>Email</th> <td><input type="text" name="email" id="email" class="form-control" ></td>
				</tr>	
				<tr>
					<th>Phone</th> <td><input type="text" name="phone" id="phone" class="form-control" ></td>
				</tr>	
				<tr>
					<th>Attach File</th> <td><input type="file" name="aFile"></td>
				</tr>					
				<tr>
					<th>Attach File</th> <td><input type="file" name="aFile"></td>
				</tr>
			</table>
		</div>
	<input type="submit" class="btn btn-success btn-sm" value="Register New Member">
	<input type="button" class="btn btn-warning btn-sm" onclick="javascript:location.href='${pageContext.request.contextPath}/main.do'" value="Back to ajax mode">
	</form>
	<script src="js/bootstrap.js"></script>
</body>
</html>
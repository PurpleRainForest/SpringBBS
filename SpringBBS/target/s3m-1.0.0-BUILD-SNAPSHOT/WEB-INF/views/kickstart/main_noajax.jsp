<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MyBatis Main (no ajax)</title>
	
	<style>
	<!--
		#tableCss {width:620px; font-size: 12px;line-height: 20px;}
		#tableCss table {width: 100%; border-collapse: collapse; font-size: 12px; line-height: 20px;}
		#tableCss th {padding: 2px; border: #D3D3D3 solid 1px; text-align: center; background: #F4F4FF;}
		#tableCss td {padding: 2px; border: #D3D3D3 solid 1px; text-align: left;}
		#tableCss tfoot td {padding: 2px; border: #D3D3D3 solid 1px; text-align: center;}
	-->
	</style>	
</head>
<body>
<form  method="post" action="">
	<div id="tableCss">
		<table border="1">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Phone</th>
				
			</tr>
			<!-- <tr>
				<td><input type="text" name="name" id="name"></td>
				<td><input type="text" name="email" id="email"></td>
				<td><input type="text" name="phone" id="phone"></td>
				<td colspan="2" align="center">
				<input type="submit" value="Insert">
				</td>
	
			</tr> -->
			<c:forEach items="${result}" var="member">
				<tr>
					<td>${member.name}</td>
					<td>${member.email}</td>
					<td>${member.phone}</td>
					
				</tr>
			</c:forEach>
		</table>
		<input type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/main.do'" value="Back to ajax mode">
	</div>
</form>
</body>
</html>
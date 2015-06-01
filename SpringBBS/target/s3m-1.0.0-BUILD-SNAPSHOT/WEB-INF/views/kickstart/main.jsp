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
	<title>MyBatis Main (ajax type)</title>

	<script type="text/javascript">
	
		var getMemberCount = function() {
			$.ajax({
				type : "POST",
				url : "./countMembers.do",
				data : {
					currCnt : $("#totMemCount").val()
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {
					//alert('ok');
					$("#totMemCount").val(data);
				}
			});
	
		}
	
		var insert = function() {
			if ($("#name").val() == '') {
				alert("input the name");
				$("#name").focus();
				return;
			};
			$.ajax({
				type : "POST",
				url : "./insert.do",
				data : {
					name : $("#name").val(),
					email : $("#email").val(),
					phone : $("#phone").val()
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {
					//alert(data);
					$("#mtb>tbody").empty();
	
					var nHtml = "<tr>";
					nHtml = nHtml + "<th>Name</th>";
					nHtml = nHtml + "<th>Email</th>";
					nHtml = nHtml + "<th>Phone</th>";
					nHtml = nHtml + "<th></th>";
					nHtml = nHtml + "</tr>";
					nHtml = nHtml + "<tr>";
					nHtml = nHtml + "<td><input type='text' name='name' id='name'></td>";
					nHtml = nHtml + "<td><input type='text' name='email' id='email'></td>";
					nHtml = nHtml + "<td><input type='text' name='phone' id='phone'></td>";
					nHtml = nHtml + "<td colspan='2' align='center'><input type='submit' value='Insert'></td>";
					nHtml = nHtml + "</tr>";
	
					var dHtml = "<tr>";
					$(data).each(function(key, val) {
						//alert("name:"+val.name+"   email:"+val.email+"   phone:"+val.phone);
						dHtml = dHtml + "<td>" + val.name + "</td>";
						dHtml = dHtml + "<td>" + val.email + "</td>";
						dHtml = dHtml + "<td>" + val.phone + "</td>";
						dHtml = dHtml + "<td><input type='button' value='Update' onclick=''> ";
						dHtml = dHtml + "<input type='button' value='Delete' onClick='javascript:delMember(\"" + val.regno + "\");'</td>";
						dHtml = dHtml + "</tr>";
					});
					nHtml = nHtml + dHtml;
					//$("#mtb>tbody").html(nHtml);
					$("#mtb>tbody").append(nHtml);
					$("#submitFrm").reset();
	
				}
			});
		}
		
		var delMember = function(delname) {
			
			$.ajax({
				type : "POST",
				url : "./delete.do",
				data : {
					regno : delname
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {
					//alert(data.msg);
					if(data.ok == 'Y') {
						location.reload();
					}
				}
			}); 
	
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
	
	<form id="submitFrm" method="post" action="insert_noajax.do">
		<br /><br />
		<div id="tableCss">
			<table border="1" id="mtb">
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th></th>
				</tr>
				<tr>
					<td><input type="text" name="name" id="name" class="form-control"></td>
					<td><input type="text" name="email" id="email" class="form-control"></td>
					<td><input type="text" name="phone" id="phone" class="form-control"></td>
					<td colspan="2" align="center"><input type="submit" value="Insert" class="btn btn-primary btn-sm">
					</td>
				</tr>
				<c:forEach items="${result}" var="member">
					<tr>
						<td><a href='detail.do?regno=${member.regno}'>${member.name}</a></td>
						<td>${member.email}</td>
						<td>${member.phone}</td>
						<td><input type="button" value="Update" onclick="" class="btn btn-warning btn-sm"> 
							<input type="button" value="Delete" onclick="delMember('${member.regno}');" class="btn btn-warning btn-sm"></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form>
	<button onclick="getMemberCount();" class="btn btn-info btn-sm">Total Member (ajax call)</button>:
	<input type="text" id="totMemCount" value="999">
	<button onclick="insert();"  class="btn btn-primary btn-sm">JSON Type Insert (ajax call)</button>
	<input type="button" class="btn btn-success btn-sm" onclick="javascript:location.href='${pageContext.request.contextPath}/registerForm.do'" value="Insert (File Upload)">

	<script src="js/bootstrap.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import ="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Result Page</title>
</head>
<body>

<table border="1">
	 <tr>
		 <td width="50px" align="center">id</td>
		 <td align="center">email</td>
		 <td align="center">phone</td>
	 </tr>
	 
	 <c:forEach items="${result}" var="member">
		 <tr>
			 <td>${member.name}</td>
			 <td>${member.email}</td>
			 <td>${member.phone}</td>	
		 </tr>
	 </c:forEach>

</table>

</body>
</html>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Session & Cookie</title>
</head>
<body>

<% 
	request.getSession().setAttribute("userNm", "John");
%>
	
	<c:if test="${empty sessionScope.userNm}">
	no userid
	</c:if>
	
	<c:if test="${not empty sessionScope.userNm}">
		${sessionScope.userNm}
	</c:if>
	
<%
	request.getSession().setMaxInactiveInterval(20 * 60);
	// change specific users timeout period

	// request.getSession().invalidate();
%>

<%
	Cookie ck;
	ck = new Cookie("fNm", "john");
	// ck.setMaxAge(60*60*24*30);
	response.addCookie(ck);
	ck = new Cookie("sNm", URLEncoder.encode("홍길동", "UTF-8"));
	ck.setMaxAge(60*60*24*30);
	// ck.setMaxAge(-1);
	// ck.setMaxAge(0);
	
	ck.setPath("/");
	response.addCookie(ck);
%>

<%
	Cookie [] cr = request.getCookies();
	if (cr != null) {
		//for (int i=0; i<cr.length;i++){	}
		
		for (Cookie c: cr){
			out.write("key :"+c.getName() +", value:"+URLDecoder.decode(c.getValue(), "UTF-8") +"<br />");
		}
	}	else {
		out.write("no cookie");
	}

%>





















</body>
</html>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JSTL core</title>
</head>
<body>
	<c:set var="cDate" value="<%= new Date() %>" />
	${cDate} <br />
	<hr />
	<fmt:formatDate value="${cDate}" /> <br />
	<fmt:formatDate value="${cDate}" type="date" /> <br />
	<fmt:formatDate value="${cDate}" type="time" /> <br />
	<fmt:formatDate value="${cDate}" type="both" /> <br />
	<hr />
	<fmt:formatDate value="${cDate}" type="both" timeStyle="short"/> <br />
	<fmt:formatDate value="${cDate}" type="both" timeStyle="medium" /> <br />
	<fmt:formatDate value="${cDate}" type="both" timeStyle="long" /> <br />
	<fmt:formatDate value="${cDate}" type="both" timeStyle="full"/> <br />
	<hr />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="short"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="medium"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="long"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="full"/> <br />
	<hr />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="short" pattern="YY-MM-dd"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="medium" pattern="YY-MM-dd a hh:mm:ss"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="long" pattern="YYYY-MM-dd [E]"/> <br />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="full" pattern="YYYY-MM-dd [E] a hh:mm:ss"/> <br />
	<hr />
	<fmt:formatNumber value="100000"/>	<br />
	<fmt:formatNumber value="100000" type="number"/> - <fmt:formatNumber value="100000" type="currency"/> - <fmt:formatNumber value="100000" type="percent"/>	<br />
	<fmt:formatNumber value="100000" type="currency" pattern="# Won"/>	<br />
	<fmt:formatNumber value="1000000000.237" type="currency" pattern="###,###.## Won"/>	<br />
	<fmt:formatNumber value="1000000000.237" type="currency" minFractionDigits="5" maxIntegerDigits="4" />	<br />
	<hr />
	<fmt:setLocale value="ko_Kr" />
	<fmt:formatDate value="${cDate}" type="both" /><br />
	<fmt:formatNumber value="1000000000.237" type="currency"/><br />
	<fmt:setLocale value="en_US" />
	<fmt:formatDate value="${cDate}" type="both" /><br />
	<fmt:formatNumber value="1000000000.237" type="currency"/>
	<hr />	
	<c:set var="lcs" value="<%=Locale.getAvailableLocales() %>" />
	<c:forEach var="lcs" items="${lcs}">
		${lcs} , 
	</c:forEach>
	<hr />
	<fmt:formatDate value="${cDate}" type="both" dateStyle="long" timeZone="Pacific/Auckland" />
	<hr />	
	<c:set var="tZone" value="<%= TimeZone.getAvailableIDs() %>" />	
	<c:forEach var="tZ" items="${tZone}">
		${tZ} , 
	</c:forEach>
	<hr />	
</body>
</html>



































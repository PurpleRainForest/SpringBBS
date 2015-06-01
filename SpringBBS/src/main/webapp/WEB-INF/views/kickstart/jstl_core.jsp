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
<title>JSTL core</title>
</head>
<body>
	<c:set var="n" value="0" scope="request" />
	<c:forEach var="i" begin="1" end="10" varStatus="st">		
		
		<c:out value="<${i}>" />
		<%--
		&lt;${i}&gt;
		 --%>
		<c:set var="n" value="${n+i}" scope="request" />
		<c:if test="${not st.last}">,</c:if>
	</c:forEach>
	sum: ${n}
	<hr />
	
		
	<c:forEach var="i" begin="1" end="10" varStatus="st">
		<c:choose>
			<c:when test="${i%2==0}">
				<span style='color:red;'><c:out value="<${i}>" /></span>
			</c:when>
			<c:when test="${i%2!=0}">
				<span style='color:blue;'><c:out value="<${i}>" /></span>
			</c:when>
		</c:choose>
	</c:forEach>
	<hr />
	
	
	<c:forEach var="i" begin="1" end="10" varStatus="st">
		<c:choose>
			<c:when test="${i<=3}">
				<span style='color:red;'><c:out value="<${i}>" /></span>
			</c:when>
			<c:when test="${i<=6}">
				<span style='color:blue;'><c:out value="<${i}>" /></span>
			</c:when>
			<c:otherwise>
				<span style='color:black;'><c:out value="<${i}>" /></span>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<hr />	
	
	<%-- c:url tag adds Context name automatically --%>
	<%-- c:url tag includes JSessionID at first request  --%>
	<c:url var="mypage" value="http://www.google.com">
		<c:param name="x">30</c:param>
		<c:param name="y">20</c:param>
	</c:url>
	<c:forEach var="i" begin="1" end="10" varStatus="st">
		<c:choose>
			<c:when test="${i<=3}">
				<!-- 
				<span style='color:red;'><a href="<c:url value="/aBdList.htm" />"><c:out value="<${i}>" /></a></span>
				 -->
				<span style='color:red;'><a href="${pageContext.request.contextPath}/aBdList.htm"><c:out value="<${i}>" /></a></span>
			</c:when>
			<c:when test="${i<=6}">
				<span style='color:blue;'><a href="${mypage}"><c:out value="<${i}>" /></a></span>
			</c:when>
			<c:otherwise>
				<span style='color:black;'><c:out value="<${i}>" /></span>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<hr />		
	<c:url var="mypage2" value="/aBdList.htm">
		<c:param name="pg">1</c:param>
	</c:url>
	
	<%-- <c:redirect url="jstl_forward.jsp" /> --%>
	
	<%-- <<jsp:forward page="jstl_forward.jsp" />  --%>
	
	<c:import url="jstl_import.jsp">
		<c:param name="x" value="20"></c:param>
	</c:import>
	<hr />
	<jsp:include page="jstl_import.jsp">
		<jsp:param name="x" value="20" />
	</jsp:include>
	
	<hr /><hr />
	<c:forTokens var="TNm" items="apple/2,banana/3,mango/4,lemon/2" delims=",/"
	 varStatus="st" begin="1" end="7" step="2">${TNm}<br />
		index:${st.index}<br />
		current:${st.current}<br />
		count:${st.count}<br />
		first:${st.first}<br />
		last:${st.last}<br />
		begin:${st.begin}<br />
		end:${st.end}<br />
		step:${st.step}<br />
		<hr />
	</c:forTokens>



</body>
</html>
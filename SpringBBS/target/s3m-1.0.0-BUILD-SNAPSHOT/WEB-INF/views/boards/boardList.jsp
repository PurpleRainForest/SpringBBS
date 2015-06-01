<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.bpopup.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.msgbox.min.js"></script>
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	


	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ClientSide List</title>
</head>
<body>
		<br /><br />

		<div class="container">
		
			<div align="right">
				<form id="searchForm" action="boardList.do" method="get" class="form-inline">
					<input type="hidden" name="pg" value="1"  class="form-control" />
					<input type="hidden" name="tbid" value="${param.tbid}"  class="form-control" />
					<div class="form-group">
						<select name="field" class="form-control" >
							<option value="title" ${param.field=="title"?"selected":""}>Title</option>
							<option value="cont" ${param.field=="cont"?"selected":""}>Contents</option>
						</select> 
					</div>
					<div class="form-group">
						<input type="text" name="query" value="${query}" class="form-control" /> 
					</div>
					<div class="form-group">
						<input type="submit" value="Search" class="btn btn-default btn-sm" />
					</div>
				</form>
				
			</div>
			<br />
		
			<table class="table table-bordered table-striped">
				<tr>
					<th>No</th>
					<th>Title</th>
					<th>Writer</th>
					<th>View</th>
					<th>Reg</th>
				</tr>
				<c:forEach items="${result}" var="article">
					<tr>
						<td>${article.bbsno}</td>
						<td><a href='boardDetail.do?tbid=${param.tbid}&bbsno=${article.bbsno}&pg=${page}&field=${param.field==null?"title":param.field}&query=${param.query}' style="text-decoration:none;font-weight:bold;">
							<c:forEach var="idx" begin="1" end="${article.ref_level}" step="1">
							&nbsp;&nbsp;&nbsp;
							</c:forEach>
							<c:if test="${article.ref_level > 0}">
								<span class="glyphicon glyphicon-chevron-right"></span>
							</c:if>
							${article.title}
							</a>
						</td>
						
						
						<%-- <td><a href='boardDetail.do?tbid=${param.tbid}&bbsno=${article.bbsno}' style="text-decoration:none;color:#430000;font-weight:bold;">${article.title}</a></td> --%>
						<td>${article.writer}</td>
						<td>${article.views}
						<c:choose>
							<c:when test="${article.likes > '0'}">
								<sup><span class="glyphicon glyphicon-heart"></span><font color="red"> +${article.likes}</font></sup>
							</c:when>
						</c:choose>
						
						 </td>
						<td><fmt:formatDate value="${article.cdate}" type="both" dateStyle="long" pattern="YYYY-MM-dd [E]"/> <br /></td>
					</tr>
				</c:forEach>
			</table>
			
			<div align="left">
					<a href='boardInsert.do?tbid=${param.tbid}' class="btn btn-primary btn-sm">New Article</a>
			</div>
			
			<div align="center">
				<ul class="pagination pagination-sm">
					<c:if test="${sPage==1}">
						<li class="disabled"><span>&laquo;</span></li>
					</c:if> 
					<c:if test="${sPage!=1}">
						<li><a href='boardList.do?tbid=${param.tbid}&pg=${sPage-1}&field=${param.field==null?"title":param.field}&query=${param.query}&pgNavLen=${pgNavLen}&contentPerPg=${contentPerPg}'><span>&laquo;</span></a></li>
					</c:if>
					
					<c:forEach var="i" begin="0" end="${pgNavLen-1}">
						<c:if test="${sPage+i > ePage}">
							<li class="disabled"><span>${i+sPage}</span></li>    
						</c:if>
						<c:if test="${sPage+i <= ePage}">
							<c:if test="${sPage+i != page }">
								<li><a href='boardList.do?tbid=${param.tbid}&pg=${i+sPage}&field=${param.field==null?"title":param.field}&query=${param.query}&pgNavLen=${pgNavLen}&contentPerPg=${contentPerPg}'><span>${i+sPage}</span></a></li> 						
							</c:if>
							<c:if test="${sPage+i == page }">
								<li class="active"><span>${i+sPage}</span></li>
							</c:if>
						</c:if>
					</c:forEach> 
					
					<c:if test="${sPage+pgNavLen >= ePage}">
						<li class="disabled"><span>&raquo;</span></li>
					</c:if> 
					<c:if test="${sPage+pgNavLen < ePage}">
						<li><a href='boardList.do?tbid=${param.tbid}&pg=${sPage+pgNavLen}&field=${param.field==null?"title":param.field}&query=${param.query}&pgNavLen=${pgNavLen}&contentPerPg=${contentPerPg}'><span>&raquo;</span></a></li>
						<%-- 
						<li><a href='boardList.do?tbid=${param.tbid}&pg=${ePage}&field=${param.field==null?"title":param.field}&query=${param.query}&pgNavLen=${pgNavLen}&contentPerPg=${contentPerPg}'>Last <span class="badge">${ePage}</span></a></li>
						 --%>
					</c:if>
				</ul>

			</div>
		</div>

</body>
</html>

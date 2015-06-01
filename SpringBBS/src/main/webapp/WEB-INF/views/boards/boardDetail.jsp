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

	<title>ClientSide Detail</title>
	<script>
		var gotoBoardList = function() {
			location.href='boardList.do?tbid=${param.tbid}&pg=${page}&field=${field}&query=${query}';
		};
		var gotoReply = function() {
			location.href='boardInsert.do?tbid=${param.tbid}&ref_parent=${result.bbsno}&ref_no=${result.ref_no}&pg=${page}&field=${field}&query=${query}&ref_level=${result.ref_level+1}&ref_step=${result.ref_step+1}';
		};		
		
		var addBoardLikes = function(targetBbsno, currentLikes, tbid){
			//alert("targetBbsno:"+targetBbsno+"   currentLkes:"+currentLikes +"  tbid:"+tbid);
			$.ajax({
				type : "POST",
				url : "addBoardLikes.do",
				data : {
					bbsno     : targetBbsno,
					tbid	  : tbid
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {

					 if(data.ok=='Y'){
						$("#likesText").empty();
						var addedLikes = eval(currentLikes) + 1;
						var nHtml = "<a href='javascript:addBoardLikes(";
						nHtml = nHtml + targetBbsno + ", " + addedLikes ;
						nHtml = nHtml + ", \"" + tbid + "\");' class='btn btn-info btn-sm'>"; 
						nHtml = nHtml + addedLikes + " Likes (+)</a>";
						$("#likesText").append(nHtml);
					}
					if(data.ok=='N'){
						alert("Error while adding Likes Count");
						location.reload();
					} 
				}
			});
		};
		
		
		var gotoDelete = function(tbid, bbsno, ref_no){
			//alert("tbid:"+tbid+"   bbsno:"+bbsno +"  ref_no:"+ref_no);
			$.ajax({
				type : "POST",
				url : "boardDelete.do",
				data : {
					tbid	  : tbid,
					bbsno     : bbsno,
					ref_no	  : ref_no
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {

					 if(data.ok=='Y'){
						 location.href='boardList.do?tbid=${param.tbid}&pg=${page}&field=${field}&query=${query}';
					}
					if(data.ok=='C'){
						alert("Error: Delete child article first.");
						 location.href='boardDetail.do?tbid=${param.tbid}&bbsno=${param.bbsno}&pg=${page}&field=${field}&query=${query}';
					} 
					if(data.ok=='N'){
						alert("Error during delete process");
					} 

				}
			});
		};		
		
		
	</script>

</head>
<body>


		<br /><br />

		<div class="container">
			<div id="likesText" align="left">
				<a href="javascript:addBoardLikes(${result.bbsno}, ${result.likes}, '${param.tbid}');" class="btn btn-info btn-sm">${result.likes} Likes (+)</a> 
			</div>
			<table class="table table-bordered">
				<tr>
					<td class="info">Title</td><td colspan="5">${result.title}</td>
				</tr>
				<tr>
					<td class="info">Writer</td><td>${result.writer}</td>
					<td class="info">Reg. Date</td><td><fmt:formatDate value="${result.cdate}" type="both" dateStyle="long" pattern="YYYY-MM-dd [E] a hh:mm:ss"/></td>
					<td class="info">View</td><td>${result.views}</td>
				</tr>
				<tr>
					<td colspan="6" class="active">${result.cont}</td> 
				</tr>	
				<c:if test="${attachFileLinks.size() > 0}">
				<tr>
					<td colspan="6">
						<c:forEach var="attachFileLink" items="${attachFileLinks}" varStatus="st">
							${attachFileLink} 
						</c:forEach>

					</td>
				</tr>	
				
				</c:if>
			</table>
				<div style="float:left; width:200px;display:inline-block;">
					<a href="javascript:gotoReply();" class="btn btn-primary btn-sm">Reply</a> 
				</div>	
				<div style="float:right; display:inline-block;">
					<!-- <a href="javascript:history.back();" class="btn btn-success btn-sm">Back to List</a> -->
					<a href="javascript:gotoBoardList();" class="btn btn-success btn-sm">Back to List</a>
					<a href="boardEdit.do?tbid=${param.tbid}&bbsno=${result.bbsno}" class="btn btn-warning btn-sm">Edit</a>
					<a href="javascript:gotoDelete('${param.tbid}', '${result.bbsno}', '${result.ref_no}');" class="btn btn-danger btn-sm">Delete</a>
					<%-- <a href="boardDelete.do?tbid=${param.tbid}&bbsno=${result.bbsno}&ref_no=${result.ref_no}" class="btn btn-danger btn-sm">Delete</a> --%>
				</div>	
		</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.bpopup.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.msgbox.min.js"></script>
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

	<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>



	<title>ClientSide Detail</title>
	
	<script type="text/javascript">
		var insert = function() {
			
			if ($("#title").val() == '') {
				alert("input the Title");
				$("#title").focus();
				return;
			};
			
			if ( $('#isFileUploadRequired').val() == 'true' ){
				alert("upload File before Saving");
				$("#fileUploadSubmit").focus();
				return;				
			}
			

			
			ckeditor.updateElement();
			
			$.ajax({
				type : "POST",
				url : "boardEditProc.do",
				data : {
					bbsno     :  $("#bbsno").val(),
					ref_step  :  $("#ref_step").val(),
					title     :  $("#title").val(),
					cont      :  $("#cont").val(),
					likes     :  $("#likes").val(),
					aFile     :  $("#aFile").val(),
					deleted   :  $("#deleted").val(),
					tbid	  :  $("#tbid").val()
				},
				dataType : "json",
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				success : function(data) {
					if(data.ok=='Y'){
						location.href="boardDetail.do?tbid="+$("#tbid").val()+"&bbsno="+$("#bbsno").val();
					}
					if(data.ok=='N'){
						alert(msg);
						location.reload();
					}
	
				}
			}); 
			
		};
		
		function replaceAllSubString(srcString, patternString, targetString) {
			out = patternString; 
			add = targetString;
			temp = "" + srcString;
			while (temp.indexOf(out)>-1) {
				pos= temp.indexOf(out);
				temp = "" + (temp.substring(0, pos) + add + 
				temp.substring((pos + out.length), temp.length));
				}
			return temp;
		}

		var removeAFileButton = function(fileForm, removeAFileBtn, removeFileString){
			var finalFileNames = replaceAllSubString( $('#aFile').val(), removeFileString, '' );
			$('#aFile').val(finalFileNames);
			$("button[id='"+removeAFileBtn+"']").hide();
			//alert("Hidden Files:" + $('#aFile').val() );
		};
		

		var onFileSelect = function() {
			$('#isFileUploadRequired').val('true');
			$('#fileUploadStatus').html('<font color="#D941C5">Upload files before Save</font>');
		};	
		
		var addUploadFile = function() {
			 $('#fileUploadTable').append("<tr><td><input type='file' name='fileUpload'/></td></tr>");
		};		
		
		$('#fileUploadFrm').ajaxForm({
			dataType : 'json', 
			beforeSerialize: function(){                       
			},
			beforeSubmit : function() {
				// loading messages
			},		
			uploadProgress: function(event, position, total, percentComplete) {
		        $('#fileUploadStatus').text('Uploading : '+percentComplete + '%');
		    },			
			success : function(data) {
				if(data.upload=='Y'){
					$('#aFile').val( $('#aFile').val() + data.fileUpload );
					// $('#fileUploadStatus').text(data.fileUpload);
					$('#fileUploadStatus').html('<font color="#22741C">Upload completed</font>');

					$('#isFileUploadRequired').val('false');
					// alert($('#aFile').val());
				}
				if(data.upload=='N'){
					$('#fileUploadStatus').html('<font color="#CC3D3D">File upload failed, check the attach file</font>');
				} 
			},
			complete: function(xhr) {
				
			}
		});
	</script>

</head>
<body>

		<br /><br />
		<div class="container">
			<form id="submitFrm" method="post" action="">
				<table class="table table-bordered">
					<tr>
						<td class="info">Title</td><td colspan="5"><input type="text" id="title" name="title" class="form-control" value="${result.title}" /></td>
					</tr>
					<tr>
						<td class="info">Writer</td><td>${result.writer}</td>
						<td class="info">Reg. Date</td><td><fmt:formatDate value="${result.cdate}" type="both" dateStyle="long" pattern="YYYY-MM-dd [E] a hh:mm:ss"/></td>
						<td class="info">View</td><td>${result.views}</td>
					</tr>
					<tr>
						<td colspan="6" class="active">
							<textarea id="cont" name="cont" class="form-control" rows="10">${result.cont}</textarea>
							<script type="text/javascript">
						       var ckeditor_config = {
						    		   height : '450px',
						               toolbarCanCollapse : true , 
						               removePlugins : "elementspath", 
						               filebrowserUploadUrl: 'boardCKEditFileUpload.do' // file upload processing

						       };
								var ckeditor = CKEDITOR.replace('cont', ckeditor_config);
							</script>
						</td>
					</tr>	
				</table>
				<input type="hidden" id="bbsno" name="bbsno" value="${result.bbsno}" />
				<input type="hidden" id="ref_step" name="ref_step" value="${result.ref_step}" />
				<input type="hidden" id="likes" name="likes" value="${result.likes}" />
				<input type="hidden" id="aFile" name="aFile" value="${result.aFile}" /> 
				<input type="hidden" id="deleted" name="deleted" value="${result.deleted}" />
				<input type="hidden" id="tbid" name="tbid" value="${param.tbid}" />
				
				<input type="hidden" id="isFileUploadRequired" name="isFileUploadRequired" value="false" />
				
			</form>
 			<form id="fileUploadFrm" method="post" action="boardFileUpload.do" enctype="multipart/form-data">
	            <table id="fileUploadTable"  class="table table-bordered">

					<c:if test="${removeFileLinks.size() > 0}">
					<tr>
						<td colspan="6">
							<c:forEach var="removeFileLink" items="${removeFileLinks}" varStatus="st">
								${removeFileLink} 
							</c:forEach>
	
						</td>
					</tr>	
					
					</c:if>

	            	<tr>
	            		<td>
				            <a href="javascript:addUploadFile();" class="btn btn-default btn-sm">Add File</a>
							<input type="submit" id="fileUploadSubmit" value="Upload" class="btn btn-success btn-sm" /> 
							&nbsp;&nbsp; <label id="fileUploadStatus"></label>
	            		</td>
	            	</tr>
	                <tr>
	                    <td>
	                    	<input type='file' name='fileUpload' onchange="onFileSelect();"/>
	                    </td>
	                </tr>
	            </table>
	        </form>
	        <div align="right">
					<a href="javascript:history.back();" class="btn btn-default btn-sm">Cancel</a>
					<a href="javascript:insert();" class="btn btn-success btn-sm">Save</a>
			</div>
		</div>
		

</body>
</html>
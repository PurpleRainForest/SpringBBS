<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<script type='text/javascript'>
window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${file_path}', 'File upload completed');
</script>
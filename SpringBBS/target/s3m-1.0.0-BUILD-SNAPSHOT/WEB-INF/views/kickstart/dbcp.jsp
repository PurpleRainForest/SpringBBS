<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>dbcp status</title>
</head>
<body>
	<p>DBCP Connection Status</p>
	Active Status : ${active} Idle Status : ${idle}
</body>
</html>

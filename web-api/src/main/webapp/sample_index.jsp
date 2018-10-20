<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.*,java.util.*" %>
<html>
<head>
    <title>index.jsp</title>
</head>

<% SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy"); %>

<body>
<h1>Welcome to index.jsp - Today is <%= sdf.format(new Date()) %></h1>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/_common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重庆铁路巡检信息化工作平台</title>
</head>

<frameset rows="127,*,11" frameborder="no" border="0" framespacing="0">
  <frame src="<%=request.getContextPath()%>/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" />
  <frame src="<%=request.getContextPath()%>/center.jsp" name="mainFrame" id="mainFrame" />
  <frame src="<%=request.getContextPath()%>/down.jsp" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>

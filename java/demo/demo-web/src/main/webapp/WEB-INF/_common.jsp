<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.demo.db.*,com.demo.util.*" %>
<%
AdminVO adminVO = (AdminVO)session.getAttribute("adminVO");
if(adminVO == null){
	response.sendRedirect(request.getContextPath()+"/logout.jsp");
	return;
}
%>
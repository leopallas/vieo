<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.demo.db.*" %>
<%
String uid = request.getParameter("uid");
String pas = request.getParameter("pas");
AdminVO adminVO = new DBConnection().login(uid, pas);
if(adminVO == null){
	response.sendRedirect(request.getContextPath() + "/login.jsp?uid="+uid+"&err=1");
}else{
	session.setAttribute("adminVO",adminVO);
	response.sendRedirect(request.getContextPath() + "/main.jsp");
}
%>
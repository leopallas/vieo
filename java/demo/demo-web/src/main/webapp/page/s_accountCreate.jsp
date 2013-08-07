<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%@ page import="com.demo.db.*,com.demo.util.*" %>
<%
String id = request.getParameter("uid");
String pas1 = request.getParameter("pas1");
String uname = request.getParameter("uname");
new DBConnection().addAccount(id, uname, pas1);
%>
<div style="font-size:14px;margin:40px;">操作成功，页面正在跳转....</div>
<script>
window.setTimeout(function(){
	location.href = "<%=request.getContextPath()%>/page/s_account.jsp";
},2000);
</script>


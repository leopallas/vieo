<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%@ page import="com.demo.db.*,com.demo.util.*" %>
<%
String acct = request.getParameter("acct");
int account = Integer.parseInt(acct);
new DBConnection().addBarcode(account);
%>
<div style="font-size:14px;margin:40px;">操作成功，页面正在跳转....</div>
<script>
window.setTimeout(function(){
	location.href = "<%=request.getContextPath()%>/page/s_register.jsp";
},2000);
</script>


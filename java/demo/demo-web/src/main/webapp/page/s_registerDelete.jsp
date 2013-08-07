<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%@ page import="com.demo.db.*,com.demo.util.*" %>
<%
String id = request.getParameter("id");
boolean result = new DBConnection().deleteBarcode(id); 
if(result){
%>
<div style="font-size:14px;margin:40px;">操作成功，页面正在跳转....</div>
<%}else{ %>
<div style="font-size:14px;margin:40px;color:red;">当前条码已与设备绑定，不可以删除</div>
<%} %>
<script>
window.setTimeout(function(){
	location.href = "<%=request.getContextPath()%>/page/s_register.jsp";
},2000);
</script>


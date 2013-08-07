<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重庆铁路巡检信息化工作平台</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	overflow:hidden;
}
.STYLE3 {font-size: 12px; color: #adc9d9; }
-->
</style></head>

<body>
<form action="<%=request.getContextPath()%>/loginSubmit.jsp" method="post">
<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#1075b1">&nbsp;</td>
  </tr>
  <tr>
    <td height="608" background="<%=request.getContextPath()%>/images/login_03.gif"><table width="847" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="318" background="<%=request.getContextPath()%>/images/new.png">&nbsp;</td>
      </tr>
      <tr>
        <td height="84"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="381" height="84" background="<%=request.getContextPath()%>/images/login_06.gif">&nbsp;</td>
            <td width="162" valign="middle" background="<%=request.getContextPath()%>/images/login_07.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="44" height="24" valign="bottom"><div align="right"><span class="STYLE3">用户</span></div></td>
                <td width="10" valign="bottom">&nbsp;</td>
                <td height="24" colspan="2" valign="bottom">
                  <div align="left">
                  <%if(request.getParameter("uid") == null){ %>
                    <input type="text" name="uid" id="textfield" style="width:100px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; ">
                  <%}else{ %>
                  	<input type="text" value="<%=request.getParameter("uid") %>" name="uid" id="textfield" style="width:100px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; ">
                  <%} %>
                  </div></td>
              </tr>
              <tr>
                <td height="24" valign="bottom"><div align="right"><span class="STYLE3">密码</span></div></td>
                <td width="10" valign="bottom">&nbsp;</td>
                <td height="24" colspan="2" valign="bottom">
                <input type="password" name="pas" id="textfield2" style="width:100px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; "></td>
              </tr>
              <tr>
                <td height="24" valign="bottom"><div align="right"><span class="STYLE3">验证码</span></div></td>
                <td width="10" valign="bottom">&nbsp;</td>
                <td width="52" height="24" valign="bottom">
                <input type="text" name="textfield3" id="textfield3" style="width:50px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; "></td>
                <td width="62" valign="bottom"><div align="left" style="font-size:14px;font-weight:bold;color:#fff;font-family:verdana, sans-serif;" id="auth-cd"></div></td>
              </tr>
              <tr></tr>
            </table></td>
            <td width="26"><img src="<%=request.getContextPath()%>/images/login_08.gif" width="26" height="84"></td>
            <td width="67" background="<%=request.getContextPath()%>/images/login_09.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25"><div align="center"><img src="<%=request.getContextPath()%>/images/dl.gif" width="57" style="cursor:pointer;" onclick="MM_doaction();" height="20"></div></td>
              </tr>
              <tr>
                <td height="25"><div align="center"><img src="<%=request.getContextPath()%>/images/cz.gif" width="57" onclick="MM_reset();" height="20"></div></td>
              </tr>
            </table></td>
            <td width="211" background="<%=request.getContextPath()%>/images/login_10.gif">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="206" background="<%=request.getContextPath()%>/images/login_11.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#152753">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>
<script>
function MM_doaction(){
	var org = document.getElementById("auth-cd").innerHTML;
	var ipt = document.forms[0].textfield3.value;
	if(org != ipt){
		document.forms[0].textfield3.select();
		alert("验证码输入不正确，请重试");
		return;
	}
	document.forms[0].submit();
}

function MM_reset(){
	document.forms[0].reset();
	document.forms[0].uid.focus();
}

function MM_authcode(){
	var cd = parseInt(Math.random() * 10000) + "0000";
	cd = cd.substring(0,4);
	document.getElementById("auth-cd").innerHTML = cd;
}

MM_authcode();
</script>
<%
if("1".equals(request.getParameter("err"))){
	out.println("<script>alert('用户名或密码错误');</script>");
} 

%>



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.demo.db.*,com.demo.util.*" %>
<%
AdminVO adminVO = (AdminVO)session.getAttribute("adminVO");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	font-size: 12px;
	color: #000000;
}
.STYLE5 {font-size: 12}
.STYLE7 {font-size: 12px; color: #FFFFFF; }
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="57" background="<%=request.getContextPath()%>/images/main_03.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="378" height="57">
        	<div style="color:#fff;font-weight:bold;font-size:16px;font-family:微软雅黑;" >&nbsp;&nbsp;&nbsp;重庆西车段智能巡检信息平台</div>
        </td>
        <td>&nbsp;</td>
        <td width="281" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="33" height="27"><img src="<%=request.getContextPath()%>/images/main_05.gif" width="33" height="27" /></td>
            <td width="248" background="<%=request.getContextPath()%>/images/main_06.gif"><table width="225" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="17"><div align="right"><img style="cursor:pointer;" onclick="MM_modify();" src="<%=request.getContextPath()%>/images/pass.gif" width="69" height="17" /></div></td>
                <td><div align="right"><img style="cursor:pointer;" onclick="MM_user();" src="<%=request.getContextPath()%>/images/user.gif" width="69" height="17" /></div></td>
                <td><div align="right"><img style="cursor:pointer;" onclick="MM_quit();" src="<%=request.getContextPath()%>/images/quit.gif" width="69" height="17" /></div></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="40" background="<%=request.getContextPath()%>/images/main_10.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40">&nbsp;</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="21"><img src="<%=request.getContextPath()%>/images/main_13.gif" width="19" height="14" /></td>
            <td width="35" class="STYLE7"><div style="cursor:pointer;" align="center" onclick="MM_goto(1);">首页</div></td>
            <td width="21" class="STYLE7"><img src="<%=request.getContextPath()%>/images/main_15.gif" width="19" height="14" /></td>
            <td width="35" class="STYLE7"><div style="cursor:pointer;" align="center" onclick="MM_goto(2);">后退</div></td>
            <td width="21" class="STYLE7"><img src="<%=request.getContextPath()%>/images/main_17.gif" width="19" height="14" /></td>
            <td width="35" class="STYLE7"><div style="cursor:pointer;" align="center" onclick="MM_goto(3);">前进</div></td>
            <td width="21" class="STYLE7"><img src="<%=request.getContextPath()%>/images/main_19.gif" width="19" height="14" /></td>
            <td width="35" class="STYLE7"><div style="cursor:pointer;" align="center" onclick="MM_goto(4);">刷新</div></td>
            <td width="21" class="STYLE7"><img src="<%=request.getContextPath()%>/images/main_21.gif" width="19" height="14" /></td>
            <td width="35" class="STYLE7"><div style="cursor:pointer;" align="center" onclick="MM_goto(5);">帮助</div></td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
        <td width="248" background="<%=request.getContextPath()%>/images/main_11.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="16%"><span class="STYLE5"></span></td>
            <td width="75%"><div align="center"><span class="STYLE7">今天是：<%=Tools.getCurrentDT()%>&nbsp;<%=Tools.getCurrentWeek()%></span></div></td>
            <td width="9%">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="30" background="<%=request.getContextPath()%>/images/main_31.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" height="30"><img src="<%=request.getContextPath()%>/images/main_28.gif" width="8" height="30" /></td>
        <td width="147" background="<%=request.getContextPath()%>/images/main_29.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="24%">&nbsp;</td>
            <td width="43%" height="20" valign="bottom" class="STYLE1">管理菜单</td>
            <td width="33%">&nbsp;</td>
          </tr>
        </table></td>
        <td width="39"><img src="<%=request.getContextPath()%>/images/main_30.gif" width="39" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="20" valign="bottom"><span class="STYLE1">当前登录用户：<%=adminVO.getName() %> &nbsp;用户角色：<%=adminVO.getAdmType() == 0? "系统管理员":"高级用户"%></span></td>
            <td valign="bottom" class="STYLE1"><div align="right">
                <!--img src="<%=request.getContextPath()%>/images/sj.gif" width="6" height="7" />
            IP：<%=request.getRemoteAddr() %>       &nbsp; &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/sj.gif" width="6" height="7" /!--> &nbsp;
            	&nbsp; </div></td>
          </tr>
        </table></td>
        <td width="17"><img src="<%=request.getContextPath()%>/images/main_32.gif" width="17" height="30" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<script>
var SYS_NAVI_LIST = [];

function MM_goto(num){
	switch(num){
		case 1: 
			top.mainFrame.MPage.location.href = "<%=request.getContextPath()%>/page/t_monitor.jsp";
			break;
		case 2: 
			for(var i=1;i<SYS_NAVI_LIST.length;i++){
				if(SYS_NAVI_LIST[i] == top.mainFrame.MPage.location.href){
					top.mainFrame.MPage.location.href = SYS_NAVI_LIST[i-1];
					break;
				}
			}
			break;
		case 3: 
			for(var i=0;i<SYS_NAVI_LIST.length-1;i++){
				if(SYS_NAVI_LIST[i] == top.mainFrame.MPage.location.href){
					top.mainFrame.MPage.location.href = SYS_NAVI_LIST[i+1];
					break;
				}
			}
			break;
		case 4: 
			top.mainFrame.MPage.location.reload();
			break;
		case 5: 
			alert("重庆市铁路信息管理平台\r\n系统版本：2013 v1.0.1");
			break;
	}
}

function MM_quit(){
	if(confirm("您确定要退出吗？")){
		top.location.href = "<%=request.getContextPath()%>/logout.jsp";
	}
}

function MM_user(){
	top.mainFrame.MPage.location.href = "<%=request.getContextPath()%>/page/s_account.jsp";
}
function MM_modify(){
	top.mainFrame.MPage.location.href = "<%=request.getContextPath()%>/page/s_account.jsp?from=md";
}
</script>


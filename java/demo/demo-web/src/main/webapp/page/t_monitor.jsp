<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 3px;
	margin-top: 0px;
	margin-right: 3px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #e1e2e3;
	font-size: 12px;
}

.STYLE6 {color: #000000; font-size: 12; }
.STYLE10 {color: #000000; font-size: 12px; }
.STYLE19 {
	color: #344b50;
	font-size: 12px;
}
.STYLE21 {
	font-size: 12px;
	color: #3b6375;
}
.STYLE22 {
	font-size: 12px;
	color: #295568;
}
-->
</style>



</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="6%" height="19" valign="bottom">
                <div align="center">
                <img src="<%=request.getContextPath()%>/images/tb.gif" width="14" height="14" />
                </div></td>
                <td width="94%" valign="bottom"><span class="STYLE1">终端监控</span></td>
              </tr>
            </table></td>
            <td><div align="right"><span class="STYLE1">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              	&nbsp;&nbsp;</span>
              <span class="STYLE1"> &nbsp;</span>
             </div>
             </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
      <tr>
        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
        <div style="margin:20px;">
        	<%
			List<TerminalVO> terminalVOList = new DBConnection().listTerminal(100,1);
        	long onlineMins = 0;
        	int tStatus = 0;
			for(int i=0;i<terminalVOList.size();i++){
				TerminalVO element = terminalVOList.get(i);
				if(element.getStatus() == 1){ // 登陆
					onlineMins = Tools.getOffsetMins(element.getLoginDt());
					if(Tools.getOffsetMins(element.getUpdDt()) > 1){
						tStatus = 2;//无法连接;
					}else{
						tStatus = 1;//在线;
					}
				}else{ //登出
					tStatus = 3;//离线
				}
			%>
        	<div style="margin:10px;width:90px;height:100px;border:0px dashed #EEEEE0;float:left">
        		<div style="margin:5px;font-size:12px;">
        			<div><img src="<%=request.getContextPath()%>/icon/10<%=tStatus %>.png" style="width:60px;height:60px;"/></div>
        			<div>状态：<%
        					switch(tStatus){
        						case 1:out.println("在线");break;
        						case 2:out.println("<font color='red'>断线</font>");break;
        						case 3:out.println("<font color='#ccc'>离线</font>");break;
        					} 
        					%>
        			</div>
        			<%if(tStatus != 3){ %>
        			<div>员工：<%=element.getUsrId() %></div>
        			<div>时长：<span style="font-size: 10px"><%=onlineMins%>分钟</span></div>
        			<%} %>
        		</div>
        	</div>
        	<%} %>
        	<div style="clear:both;"></div>
        </div>
        <div style="margin-bottom:50px;"></div>
        </td>
      </tr>
      
    </table></td>
  </tr>

</table>


</body>
</html>
<script>
window.setInterval(function(){
	location.href = "<%=request.getContextPath()%>/page/t_monitor.jsp?rd=" + Math.random();
},10*1000);

top.topFrame.SYS_NAVI_LIST.push(self.location.href);
</script>
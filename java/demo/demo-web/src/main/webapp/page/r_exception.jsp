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
                <td width="94%" valign="bottom"><span class="STYLE1">异常监控</span></td>
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
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
      <tr>
        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
        <div style="margin:20px;">
          <%
              int pageNumber = 1;
              int pageSize = 100;
		  List<PictureVO> adminVOList = new DBConnection().listPicture(null,null,null,3,100,1);
		  for(int i=0;i<adminVOList.size();i++){
			  PictureVO element = adminVOList.get(i);
		  %>
          <div style="margin:10px;width:310px;height:200px;background-color:#fff;border:1px dashed #AAEEE0;float:left;" onmouseover="this.style.backgroundColor='#AAEEE0'" onmouseout="this.style.backgroundColor='#FFF'">
        		<div style="margin:5px;font-size:10px;">
        			<div style="float:left;width:180px;"><img src="<%=Tools.getPictureURL(request.getContextPath(),element.getUsrId(), element.getPicId(), element.getPicName()) %>" style="width:180px;height:180px;"/></div>
        			<div style="float:left;width:110px;margin-left:6px;">
                        <div style="height:15px;margin-bottom:3px;">工号：<%=element.getUsrId() %></div>
                        <div style="height:15px;margin-bottom:3px;">时间：<%=Tools.getDT2(element.getCreateDt()) %></div>
                        <div style="height:15px;margin-bottom:3px;">经度：<%=element.getLat() %></div>
                        <div style="height:15px;margin-bottom:3px;">纬度：<%=element.getLon() %></div>
                        <div style="margin-bottom:3px;border-top:1px dashed #AAEEE0;padding-top:5px;">描述：<%=element.getPicDesc() %></div>
        				<%if(element.getSodDesc() != null && element.getSodDesc().trim().length() > 0){ %>
        				<div style="border-top:1px dashed #AAEEE0;padding-top:5px;">
        					<img onclick="MM_play('<%=Tools.getSoundResource(request.getContextPath(),element.getUsrId(), element.getPicId(), element.getSodDesc())  %>');" alt="播放" src="<%=request.getContextPath() %>/icon/sound.png" style="width:40px;height:40px;cursor:pointer;"/>
        				</div>
        				<%} %>
        			</div>
        			<div style="clear:both;"></div>
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
function MM_play(src){
	window.open("<%=request.getContextPath()%>/page/r_sound_play.jsp?src="+src,'newwindow','height=100,width=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}



top.topFrame.SYS_NAVI_LIST.push(self.location.href);
</script>
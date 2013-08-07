<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>

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
<form action="<%=request.getContextPath()%>/page/r_normal.jsp">
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
                <td width="94%" valign="bottom"><span class="STYLE1">实时数据</span></td>
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

		  List<PictureVO> adminVOList = new DBConnection().listPicture(null,null,null,1,20,1);
          for(int i=0;i<adminVOList.size();i++){
			  PictureVO element = adminVOList.get(i);
		  %>
          <div style="margin:10px;width:280px;height:180px;background-color:#fff;border:1px dashed #AAEEE0;float:left;" onmouseover="this.style.backgroundColor='#AAEEE0'" onmouseout="this.style.backgroundColor='#FFF'">
              <div style="margin:5px;font-size:10px;">
                  <div style="float:left;width:140px;">
                      <img onclick="MM_openPhoto('<%= Tools.getPictureURL(request.getContextPath(),element.getUsrId(), element.getPicId(), element.getPicName()) %>');" src="<%= Tools.getPictureURL(request.getContextPath(),element.getUsrId(), element.getPicId(), element.getPicName()) %>" style="cursor:pointer;width:140px;height:140px;"/>
                  </div>
                  <div style="float:left;width:120px;margin-left:6px;">
                      <div style="height:15px;margin-bottom:3px;">工号：<%=element.getUsrId() %></div>
                      <div style="height:15px;margin-bottom:3px;">时间：<%=Tools.getDT2(element.getCreateDt()) %></div>
                      <div style="height:15px;margin-bottom:3px;">经度：<%=element.getLat() %></div>
                      <div style="height:15px;margin-bottom:3px;">纬度：<%=element.getLon() %></div>
                      <div style="height:15px;margin-bottom:3px;">类型：<%=element.getGzfl()==0?"普通":"<font color=red>故障</font>" %></div>
                      <%if(element.getSodDesc() != null && element.getSodDesc().trim().length() > 0){ %>
                      <div style="border-top:1px dashed #AAEEE0;padding-top:5px;">
                          <img onclick="MM_play('<%=Tools.getSoundResource(request.getContextPath(), element.getUsrId(), element.getPicId(), element.getSodDesc()) %>');" alt="播放" src="<%=request.getContextPath() %>/icon/sound.png" style="width:40px;height:40px;cursor:pointer;"/>
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

</form>

</body>
</html>
<script>
function MM_play(src){
	window.open("<%=request.getContextPath()%>/page/r_sound_play.jsp?src="+src,'newwindow','height=100,width=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}



top.topFrame.SYS_NAVI_LIST.push(self.location.href);

setInterval(function(){
    location.href = "<%=request.getContextPath()%>/page/r_normal_intime.jsp?rd="+Math.random();
},10*1000);

function MM_openPhoto(url){

    // 传递至子窗口的参数
    var paramObj = { };

    // 模态窗口高度和宽度
    var whparamObj = { width: 700, height: 680 };

    // 相对于浏览器的居中位置
    var bleft = (screen.width - whparamObj.width) / 2;
    var btop = (screen.height - whparamObj.height) / 2;



    // 最终模态窗口的位置
    var left = bleft ;
    var top = btop ;

    // 参数
    var p = "help:no;status:no;center:yes;";
    p += 'dialogWidth:'+(whparamObj.width)+'px;';
    p += 'dialogHeight:'+(whparamObj.height)+'px;';
    p += 'dialogLeft:' + left + 'px;';
    p += 'dialogTop:' + top + 'px;';

    window.showModalDialog("<%=request.getContextPath()%>/page/r_photo_view.jsp?url="+url ,paramObj,p);
    //document.getElementById("xxx").src = url;
    // $.blockUI({
    //     message: $("#panel2"),
    //     showOverlay: false,
    //     css: { top: '10px',width: '600px', height:"300px", borderWidth:"0px"},
    //     timeout: 2000
    // });
}
</script>
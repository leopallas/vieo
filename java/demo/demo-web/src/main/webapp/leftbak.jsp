<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理平台</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE2 {color: #43860c; font-size: 12px; }

a:link {font-size:12px; text-decoration:none; color:#43860c;}
a:visited {font-size:12px; text-decoration:none; color:#43860c;}
a:hover{font-size:12px; text-decoration:none; color:#FF0000;}
-->
</style>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body onload="MM_preloadImages('<%=request.getContextPath()%>/images/main_26_1.gif','<%=request.getContextPath()%>/images/main_29_1.gif','<%=request.getContextPath()%>/images/main_31_1.gif')">
<table width="177" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
      <tr>
        <td height="26" background="<%=request.getContextPath()%>/images/main_21.gif">&nbsp;</td>
      </tr>
      <tr>
        <td height="80" style="background-image:url(<%=request.getContextPath()%>/images/main_23.gif); background-repeat:repeat-x;"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="45"><div align="center"><a href="#" onclick="Toggle_Menu('0');"><img src="<%=request.getContextPath()%>/images/main_26.gif" name="Image1" width="40" height="40" border="0" id="Image1" onmouseover="MM_swapImage('Image1','','<%=request.getContextPath()%>/images/main_26_1.gif',1)" onmouseout="MM_swapImgRestore()" /></a></div></td>
            <td><div align="center"><a href="#" onclick="Toggle_Menu('1');"><img src="<%=request.getContextPath()%>/images/main_28.gif" name="Image2" width="40" height="40" border="0" id="Image2" onmouseover="MM_swapImage('Image2','','<%=request.getContextPath()%>/images/main_29_1.gif',1)" onmouseout="MM_swapImgRestore()" /></a></div></td>
            <td><div align="center"><a href="#" onclick="Toggle_Menu('2');"><img src="<%=request.getContextPath()%>/images/main_31.gif" name="Image3" width="40" height="40" border="0" id="Image3" onmouseover="MM_swapImage('Image3','','<%=request.getContextPath()%>/images/main_31_1.gif',1)" onmouseout="MM_swapImgRestore()" /></a></div></td>
          </tr>
          <tr>
            <td height="25"><div align="center" class="STYLE2"><a href="#" onclick="Toggle_Menu('0');">系统管理</a></div></td>
            <td><div align="center" class="STYLE2"><a href="#" onclick="Toggle_Menu('1');">终端管理</a></div></td>
            <td><div align="center" class="STYLE2"><a href="#" onclick="Toggle_Menu('2');">数据分析</a></div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td  style="line-height:4px; background:url(<%=request.getContextPath()%>/images/main_38.gif)">&nbsp;</td>
      </tr>
      <tr>
        <td>
        	<div style="margin:10px;margin-left:20px;text-align:left;font-size:14px;" id="menu"></div>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<script>
var MENU = [
            [
             {"name":"系统账号","pic":"518.png","url":"s_account.jsp"},
             {"name":"员工账号","pic":"517.png","url":"s_staff.jsp"},
             {"name":"终端注册","pic":"513.png","url":"s_register.jsp"}
            ],
            [
             {"name":"状态监控","pic":"512.png","url":"t_monitor.jsp"},
             {"name":"路线轨迹","pic":"514.png","url":"t_track.jsp"}
            ],
            [
             {"name":"巡检数据","pic":"515.png","url":"r_normal.jsp"},
             {"name":"告警查看","pic":"516.png","url":"r_warn.jsp"},
             {"name":"异常监控","pic":"521.png","url":"r_exception.jsp"},
             {"name":"故障信息","pic":"520.png","url":"r_fault.jsp"}
            ]
           ];
function Toggle_Menu(index){
	var ml = MENU[index];
	var mlhtml = "";
	for(var i=0;i<ml.length;i++){
		var el = ml[i];
		mlhtml += "<div style='margin-bottom:5px;height:25px;line-height:25px;'>";
		mlhtml += "<div style='float:left;'><img src='<%=request.getContextPath()%>/icon/"+el.pic+"' style='width:25px;height:25px;'></div>";
		mlhtml += "<div style='float:left;'><a target='I1' href='<%=request.getContextPath()%>/page/"+el.url+"'>"+el.name+"</a></div><div style='clear:both;'></div></div>";
	}
	document.getElementById("menu").innerHTML = mlhtml;
}

Toggle_Menu(0);
</script>
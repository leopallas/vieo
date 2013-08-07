<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
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
                <td width="94%" valign="bottom"><span class="STYLE1">路线轨迹</span></td>
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
        	<!--div id="mapContainer"></div-->
            <div>地图初始化失败(可能原因：地图服务缺失)...</div>
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
<script type="text/javascript" >
        var map = new BMap.Map("mapContainer");
        map.enableScrollWheelZoom();
        var point = new BMap.Point(116.404, 39.915);
        map.centerAndZoom(point, 13);

        var marker = new BMap.Marker(point);
        map.addOverlay(marker);

        var rm = new BMapLib.TextIconOverlay(point, 30);

        map.addOverlay(rm);
        map.addOverlay(new BMapLib.TextIconOverlay(new BMap.Point(116.404, 39.885), 15));
        map.addOverlay(new BMapLib.TextIconOverlay(new BMap.Point(116.354, 39.915), 24));
        map.addOverlay(new BMapLib.TextIconOverlay(new BMap.Point(116.454, 39.915), 48));
        map.addOverlay(new BMapLib.TextIconOverlay(new BMap.Point(116.404, 39.945), 99));

        rm.addEventListener("click", function (e) {
            // console.log(e);
        });
        rm.addEventListener("mouseover", function (e) {
            // console.log(e);
        });
        rm.addEventListener("mouseout", function (e) {
            // console.log(e);
        });

        var btnOpen = document.getElementById('btnText');
        btnOpen.onclick = function (event) {
            rm.setText(35);
        };

        var newPt = new BMap.Point(116.404, 39.895);
        var btnClose = document.getElementById('btnPoint');
        btnClose.onclick = function () {
            rm.setPosition(newPt);
            marker.setPosition(newPt);
        };
    </script>
    <script>
    top.topFrame.SYS_NAVI_LIST.push(self.location.href);
    </script>
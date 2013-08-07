<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
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
.STYLE3 {
	font-size: 12px;
	color: #435255;
}
.STYLE4 {font-size: 12px}
.STYLE5 {font-size: 12px; font-weight: bold; }
-->
</style></head>

<body>
<table width="147" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<%
    AdminVO adminVOXX = (AdminVO)session.getAttribute("adminVO");
    if(adminVOXX.getAdmType() == 0){
%>
<script>
    setTimeout(function(){
        top.mainFrame.MPage.location.href = "<%=request.getContextPath()%>/page/s_account.jsp";
    },1000);
</script>
  <tr>
    <td height="23" background="images/main_34.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE5">系统管理</div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><div align="center">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_40.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/s_account.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">系统账号</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_46.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/s_staff.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">员工账号</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_48.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/s_register.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">终端注册</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        

      </table>
    </div></td>
  </tr>
  <%}else{%>
  <tr>
    <td height="23" background="images/main_34_1.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE4">终端管理</div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><div align="center">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_40.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/t_monitor.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">状态监控</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_46.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/t_track.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">路线轨迹</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        
        

      </table>
    </div></td>
  </tr>
  
  <tr>
    <td height="23" background="images/main_34_1.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE4">巡检数据</div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><div align="center">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_40.gif" width="28" height="28"></td>
                  <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/r_normal.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">巡逻检查</td>
                      </tr>
                  </table></td>
              </tr>
          </table></td>
      </tr>
          <tr>
              <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_40.gif" width="28" height="28"></td>
                      <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                              <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/r_normal_intime.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">实时数据</td>
                          </tr>
                      </table></td>
                  </tr>
              </table></td>
          </tr>

        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_46.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/r_fault_input.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">故障查询</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>

          <tr>
              <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_46.gif" width="28" height="28"></td>
                      <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                              <td height="23" onclick="MM_doaction('<%=request.getContextPath()%>/page/r_report_input.jsp');" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">统计报表</td>
                          </tr>
                      </table></td>
                  </tr>
              </table></td>
          </tr>

      </table>
    </div></td>
  </tr>
  <%}%>
  
  <tr>
    <td height="23" background="images/main_34_1.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE4">系统帮助</div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><div align="center">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="<%=request.getContextPath()%>/images/main_40.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" class="STYLE4" style="cursor:pointer" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'" onclick='alert("重庆市铁路信息管理平台\r\n系统版本：2013 v1.0.1");'>帮助</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        
        <tr>
          <td height="5">&nbsp;</td>
        </tr>
      </table>
    </div></td>
  </tr>
  
  <tr>
    <td height="19" background="<%=request.getContextPath()%>/images/main_69.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="24%">&nbsp;</td>
        <td width="76%" valign="bottom"><span class="STYLE3">版本：2013 v1.0.1</span></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<script>
function MM_doaction(url){
	top.mainFrame.MPage.location.href = url;
}
</script>

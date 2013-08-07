<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
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
<form action="<%=request.getContextPath()%>/page/r_fault.jsp" method="post">
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
                <td width="94%" valign="bottom"><span class="STYLE1">故障查询</span></td>
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
        <div style="margin:20px;text-align: center;">
          <div style="width:340px;margin-left:100px;margin-top:30px;height:190px;">
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">车辆号：</div>
                  <div style="float:left;margin-left:5px;"><input type="text" size="18" name="clh"></div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">修程：</div>
                  <div style="float:left;margin-left:5px;">
                      <select name="xc">
                          <%
                              Map<Integer, String> xcmap1 = Column.XC_TYPE.getCodesAndLabels();
                              Set<Integer> xckeys1 = xcmap1.keySet();
                              for(Integer xcit1: xckeys1){
                          %>
                          <option value="<%=xcit1%>"><%=Column.XC_TYPE.getLabelByCode(xcit1)%></option>
                          <%
                              }
                          %>
                          <option value="">--全选--</option>
                      </select>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">台位：</div>
                  <div style="float:left;margin-left:5px;">
                      <select name="tw">
                          <%
                              for(int i=1;i<=16;i++){
                          %>
                          <option value="<%=i%>"><%=i%></option>
                          <%
                              }
                          %>
                          <option value="">--全选--</option>
                      </select>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">部位：</div>
                  <div style="float:left;margin-left:5px;">
                      <select name="bw">
                          <%
                              Map<Integer, String> xcmap3 = Column.BW_TYPE.getCodesAndLabels();
                              Set<Integer> xckey3 = xcmap3.keySet();
                              for(Integer xcit3: xckey3){
                          %>
                          <option value="<%=xcit3%>"><%=Column.BW_TYPE.getLabelByCode(xcit3)%></option>
                          <%
                              }
                          %>
                          <option value="">--全选--</option>
                      </select>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">故障分级：</div>
                  <div style="float:left;margin-left:5px;">
                      <select name="gzfj">
                          <%
                              Map<Integer, String> xcmap4 = Column.GZFJ_TYPE.getCodesAndLabels();
                              Set<Integer> xckeys4 = xcmap4.keySet();
                              for(Integer xcit4: xckeys4){
                          %>
                          <option value="<%=xcit4%>"><%=Column.GZFJ_TYPE.getLabelByCode(xcit4)%></option>
                          <%
                              }
                          %>
                          <option value="">--全选--</option>
                      </select>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">故障分类：</div>
                  <div style="float:left;margin-left:5px;">
                      <select name="gzfl">
                          <%
                              Map<Integer, String> xcmap5 = Column.GZFL_TYPE.getCodesAndLabels();
                              Set<Integer> xckeys5 = xcmap5.keySet();
                              for(Integer xcit5: xckeys5){
                          %>
                          <option value="<%=xcit5%>"><%=Column.GZFL_TYPE.getLabelByCode(xcit5)%></option>
                          <%
                              }
                          %>
                          <option value="">--全选--</option>
                      </select>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="height:25px;line-height:25px;clear:both;">
                  <div style="float:left;width:70px;text-align: left;">时间范围：</div>
                  <div style="float:left;margin-left:5px;">
                    <input class="Wdate" type="text" id="d15" value="" name="from" style="height:20px;width:100px;cursor: pointer;" onFocus="WdatePicker({isShowClear:false,readOnly:true,startDate:''})"/>
                    -
                    <input class="Wdate" type="text" id="d16" value="" name="to" style="height:20px;width:100px;cursor: pointer;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
                  </div>
                  <div style="clear:both;"></div>
              </div>
              <div style="margin-top:2px;">
                  <div style="float:left;width:70px;text-align: left;">&nbsp;</div>
                  <div style="float:left;margin-left:5px;">
                      <input type="submit" value=" 查 询 " />
                  </div>
              </div>

          </div>
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
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<%
    String clhP = request.getParameter("clh");
    String xcP = request.getParameter("xc");
    String twP = request.getParameter("tw");
    String bwP = request.getParameter("bw");
    String gzfjP = request.getParameter("gzfj");
    String gzflP = request.getParameter("gzfl");
    String fromP = request.getParameter("from");
    String toP = request.getParameter("to");

    int xc = 0;
    int tw = 0;
    int bw = 0;
    int gzfj = 0;
    int gzfl = 0;
    if(xcP == null || xcP.trim().equals("")){
        xc = -1;
    }else{
        xc = Integer.parseInt(xcP);
    }
    if(twP == null ||twP.trim().equals("")){
        tw = -1;
    }else{
        tw = Integer.parseInt(twP);
    }
    if(bwP == null || bwP.trim().equals("")){
        bw = -1;
    }else{
        bw = Integer.parseInt(bwP);
    }
    if(gzfjP == null || gzfjP.trim().equals("")){
        gzfj = -1;
    }else{
        gzfj = Integer.parseInt(gzfjP);
    }
    if(gzflP == null || gzflP.trim().equals("")){
        gzfl = -1;
    }else{
        gzfl = Integer.parseInt(gzflP);
    }

    int pageSize = 20;
    String pn = request.getParameter("pn");
    int pageNumber = 1;
    if(pn == null || pn.trim().equals("")){
        pageNumber = 1;
    }else{
        pageNumber = Integer.parseInt(pn);
    }
%>
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
                <td width="94%" valign="bottom"><span class="STYLE1">故障查询结果</span></td>
              </tr>
            </table></td>
            <td><div align="right"><span class="STYLE1">

                <a style="cursor: pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/page/r_fault_input.jsp'">返回查询</a>
                &nbsp;


            </span>
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
            <td width="3%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">序号</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">日期</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">质检员</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">车辆号</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">修程</span></div></td>
            <td width="6%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">台位</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">部位</span></div></td>
            <td width="9%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">故障等级</span></div></td>
            <td width="9%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">故障分类</span></div></td>
            <td width="12%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">故障内容</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">查看图片</span></div></td>
        </tr>
        <%
            List<PictureVO> adminVOList = new DBConnection().listPicture(clhP, fromP, toP, xc, tw, bw, gzfj, gzfl,pageSize,pageNumber);
            int recordCount = new DBConnection().listPictureCnt(clhP, fromP, toP, xc, tw, bw, gzfj, gzfl);
            for(int i=0;i<adminVOList.size();i++){
                PictureVO element = adminVOList.get(i);
        %>
        <tr align="right">
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=(i+1)%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.getDT2(element.getCreateDt()) %>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getUsrId()%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(element.getClh())%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(Column.XC_TYPE.getLabelByCode(Tools.intConvert(element.getXc())))%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(element.getTw())%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(Column.BW_TYPE.getLabelByCode(Tools.intConvert(element.getBw())))%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(Column.GZFJ_TYPE.getLabelByCode(Tools.intConvert(element.getGzfj())))%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(Column.GZFL_TYPE.getLabelByCode(Tools.intConvert(element.getGzfl())))%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(element.getGznr())%>&nbsp;</td>
            <td height="23" style="text-align: center;cursor: pointer" bgcolor="#FFFFFF" class="STYLE19"><a onclick="MM_openPhoto('<%= Tools.getPictureURL(request.getContextPath(),element.getUsrId(), element.getPicId(), element.getPicName()) %>');">点击查看</a></td>
        </tr>
        <%}%>
      
    </table></td>
  </tr>


    <tr>
        <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong> <%=recordCount %></strong> 条记录，当前第<strong> <%=pageNumber%></strong> 页，共 <strong><%
                    int pageCount = recordCount/pageSize +  (recordCount % pageSize==0?0:1);
                    out.println(pageCount);
                    int prev = (pageNumber-1)<1?1:(pageNumber-1);
                    int next = (pageNumber+1)>pageCount?pageCount:(pageNumber+1);
                    if(next <= 0){
                        next = 1;
                    }
                    if(prev <= 0){
                        prev = 1;
                    }
                %></strong> 页</span></div></td>
                <td width="67%"><table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="22" class="STYLE22"><div align="center"></div></td>
                        <td width="5"></td>
                        <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_54.gif" width="40" height="15" onclick="MM_goto(1);"/></div></td>
                        <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_56.gif" width="45" height="15" onclick="MM_goto(<%=prev%>);"/></div></td>
                        <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_58.gif" width="45" height="15" onclick="MM_goto(<%=next%>);"/></div></td>
                        <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_60.gif" width="40" height="15" onclick="MM_goto(<%=pageCount==0?1:pageCount%>);"/></div></td>
                        <td width="37" class="STYLE22"><div align="center"></div></td>
                        <td width="22"><div align="center">

                        </div></td>

                    </tr>
                </table></td>
            </tr>
        </table>
        </td></tr>

</table>


</body>
</html>
<script>
function MM_play(src){
	window.open("<%=request.getContextPath()%>/page/r_sound_play.jsp?src="+src,'newwindow','height=100,width=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}



top.topFrame.SYS_NAVI_LIST.push(self.location.href);

function MM_goto(n){
    location.href = "<%=request.getContextPath()%>/page/r_fault.jsp?pn="+n+"&clh=<%=clhP%>&xc=<%=xcP%>&tw=<%=twP%>&bw=<%=bwP%>&gzfj=<%=gzfjP%>&gzfl=<%=gzflP%>&from=<%=fromP%>&to=<%=toP%>";
}

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


    function MM_exportPDF(){
        window.open("<%=request.getContextPath()%>/page/r_fault_pdf.jsp?clh=<%=clhP%>&xc=<%=xcP%>&tw=<%=twP%>&bw=<%=bwP%>&gzfj=<%=gzfjP%>&gzfl=<%=gzflP%>&from=<%=fromP%>&to=<%=toP%>");
    }

    function MM_exportEXL(){
        window.open("<%=request.getContextPath()%>/page/r_fault_exl.jsp?clh=<%=clhP%>&xc=<%=xcP%>&tw=<%=twP%>&bw=<%=bwP%>&gzfj=<%=gzfjP%>&gzfl=<%=gzflP%>&from=<%=fromP%>&to=<%=toP%>");
    }
</script>
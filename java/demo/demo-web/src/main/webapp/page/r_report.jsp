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
    String typeP = request.getParameter("type");

    int xc = 0;
    int tw = 0;
    int bw = 0;
    int gzfj = 0;
    int gzfl = 0;
    int type = 0;
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
    if(typeP == null || typeP.trim().equals("")){
        type = -1;
    }else{
        type = Integer.parseInt(typeP);
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
                <td width="94%" valign="bottom"><span class="STYLE1">查询统计结果</span></td>
              </tr>
            </table></td>
            <td><div align="right"><span class="STYLE1">

                <a style="cursor: pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/page/r_report_input.jsp'">返回查询</a>
                &nbsp;
                <input type="button" value="导出为PDF" onclick="MM_exportPDF();"/><input type="button" value="导出为EXCEL" onclick="MM_exportEXL();"/>

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
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">质检员</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">车辆号</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">台位</span></div></td>
            <td width="8%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">部位</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">A类故障</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">B类故障</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">C类故障</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">整车落成</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">中间抽查</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">入段配件</span></div></td>
            <td width="5%" height="25" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">配件落成</span></div></td>
        </tr>
        <%
            HashMap<String,TPVO> listMap = new HashMap<String,TPVO>();
            List<PictureVO> adminVOListGZFJ = new DBConnection().countPictureGZFJ(clhP, fromP, type, xc, tw, bw, gzfj);
            System.out.println(adminVOListGZFJ.size());;
            for(int i=0;i<adminVOListGZFJ.size();i++){
                PictureVO element = adminVOListGZFJ.get(i);
                String key = element.getUsrId()+"-"+element.getClh()+"-"+element.getTw()+"-"+element.getBw();
                if(!listMap.containsKey(key)){
                    TPVO ipv = new TPVO();
                    ipv.setUid(element.getUsrId());
                    ipv.setClh(element.getClh());
                    ipv.setTw(element.getTw());
                    ipv.setBw(element.getBw());
                    listMap.put(key, ipv);
                }
                TPVO iii = listMap.get(key);
                switch (element.getGzfj()){
                    case 1:
                        iii.setGzfj1(element.getGzfl());
                        break;
                    case 2:
                        iii.setGzfj2(element.getGzfl());
                        break;
                    case 3:
                        iii.setGzfj3(element.getGzfl());
                        break;
                }
            }

            System.out.println(listMap.size());
            List<PictureVO> adminVOListGZFL = new DBConnection().countPictureGZFL(clhP, fromP, type, xc, tw, bw, gzfl);
            System.out.println(adminVOListGZFL.size());;
            for(int i=0;i<adminVOListGZFL.size();i++){
                PictureVO element = adminVOListGZFL.get(i);
                String key = element.getUsrId()+"-"+element.getClh()+"-"+element.getTw()+"-"+element.getBw();
                if(!listMap.containsKey(key)){
                    TPVO ipv = new TPVO();
                    ipv.setUid(element.getUsrId());
                    ipv.setClh(element.getClh());
                    ipv.setTw(element.getTw());
                    ipv.setBw(element.getBw());
                    listMap.put(key, ipv);
                }
                TPVO iii = listMap.get(key);
                switch (element.getGzfl()){
                    case 1:
                        iii.setGzfl1(element.getGzfj());
                        break;
                    case 2:
                        iii.setGzfl2(element.getGzfj());
                        break;
                    case 3:
                        iii.setGzfl3(element.getGzfj());
                        break;
                    case 4:
                        iii.setGzfl4(element.getGzfj());
                        break;
                }
            }

            Iterator<TPVO> list = listMap.values().iterator();
            int i = 1;
            while(list.hasNext()){
                TPVO element = list.next();
        %>
        <tr align="right">
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=(i++)%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getUid()%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(element.getClh())%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(element.getTw())%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=Tools.display(Column.BW_TYPE.getLabelByCode(Tools.intConvert(element.getBw())))%>&nbsp;</td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfj1()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfj2()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfj3()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfl1()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfl2()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfl3()%></td>
            <td height="23" bgcolor="#FFFFFF" class="STYLE19"><%=element.getGzfl4()%></td>
        </tr>
        <%}%>
      
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


    function MM_exportPDF(){
        window.open("<%=request.getContextPath()%>/page/r_report_pdf.jsp?clh=<%=clhP%>&xc=<%=xcP%>&tw=<%=twP%>&bw=<%=bwP%>&gzfj=<%=gzfjP%>&gzfl=<%=gzflP%>&from=<%=fromP%>&type=<%=typeP%>");
    }

    function MM_exportEXL(){
        window.open("<%=request.getContextPath()%>/page/r_report_exl.jsp?clh=<%=clhP%>&xc=<%=xcP%>&tw=<%=twP%>&bw=<%=bwP%>&gzfj=<%=gzfjP%>&gzfl=<%=gzflP%>&from=<%=fromP%>&type=<%=typeP%>");
    }
</script>
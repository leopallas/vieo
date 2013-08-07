<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8"
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
    }
    if(twP == null ||twP.trim().equals("")){
        tw = -1;
    }
    if(bwP == null || bwP.trim().equals("")){
        bw = -1;
    }
    if(gzfjP == null || gzfjP.trim().equals("")){
        gzfj = -1;
    }
    if(gzflP == null || gzflP.trim().equals("")){
        gzfl = -1;
    }
%>
<%
    List<PictureVO> adminVOList = new DBConnection().listPicture(clhP, fromP, toP, xc, tw, bw, gzfj, gzfl,5000,1);
    StringBuffer CT = new StringBuffer();
    CT.append("<table width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#a8c7ce\">\n" +
            "    <tr>\n" +
            "        <td width=\"3%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">序号</span></div></td>\n" +
            "        <td width=\"8%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">日期</span></div></td>\n" +
            "        <td width=\"5%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">质检员</span></div></td>\n" +
            "        <td width=\"8%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">车辆号</span></div></td>\n" +
            "        <td width=\"8%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">修程</span></div></td>\n" +
            "        <td width=\"6%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">台位</span></div></td>\n" +
            "        <td width=\"8%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">部位</span></div></td>\n" +
            "        <td width=\"9%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">故障等级</span></div></td>\n" +
            "        <td width=\"9%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">故障分类</span></div></td>\n" +
            "        <td width=\"12%\" height=\"25\" bgcolor=\"d3eaef\" class=\"STYLE6\"><div align=\"center\"><span class=\"STYLE10\">故障内容</span></div></td>\n" +
            "    </tr>");
%>

<%
    String fileName = String.valueOf(System.currentTimeMillis());

    for(int i=0;i<adminVOList.size();i++){
        PictureVO element = adminVOList.get(i);
        CT.append("<tr align=\"right\">");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+(i + 1)+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.getDT2(element.getCreateDt())+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getUsrId()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(element.getClh())+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(Column.XC_TYPE.getLabelByCode(Tools.intConvert(element.getXc())))+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(element.getTw())+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(Column.BW_TYPE.getLabelByCode(Tools.intConvert(element.getBw())))+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(Column.GZFJ_TYPE.getLabelByCode(Tools.intConvert(element.getGzfj())))+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(Column.GZFL_TYPE.getLabelByCode(Tools.intConvert(element.getGzfl())))+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(element.getGznr())+"&nbsp;</td>");
        CT.append("</tr>");
    }

    CT.append("</table>");
%>

<%
    String recommendedName = new String(fileName.getBytes(),"iso_8859_1");
    response.setHeader("Content-Disposition", "attachment; filename=" + recommendedName + "\"");
    response.resetBuffer();
    ServletOutputStream sos = response.getOutputStream();
    sos.write(CT.toString().getBytes());
    sos.flush();
    sos.close();
%>

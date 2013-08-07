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

%>
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


    StringBuffer CT = new StringBuffer();
    CT.append("<table width='100%' border='1' cellpadding='0' cellspacing='1' bgcolor='#a8c7ce'>");
    CT.append("<tr><td width='3%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>序号</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>质检员</span></div></td>");
    CT.append("<td width='8%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>车辆号</span></div></td>");
    CT.append("<td width='8%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>台位</span></div></td>");
    CT.append("<td width='8%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>部位</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>A类故障</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>B类故障</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>C类故障</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>整车落成</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>中间抽查</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>入段配件</span></div></td>");
    CT.append("<td width='5%' height='25' bgcolor='d3eaef' class='STYLE6'><div align='center'><span class='STYLE10'>配件落成</span></div></td>");
    CT.append("</tr>");
%>

<%
    String fileName = String.valueOf(System.currentTimeMillis());
    int i = 1;
    while(list.hasNext()){
        TPVO element = list.next();
        CT.append("<tr align=\"right\">");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+(i++)+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getUid()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(element.getClh())+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(element.getTw())+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+Tools.display(Column.BW_TYPE.getLabelByCode(Tools.intConvert(element.getBw())))+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfj1()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfj2()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfj3()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfl1()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfl2()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfl3()+"&nbsp;</td>");
        CT.append("<td height=\"23\" bgcolor=\"#FFFFFF\" class=\"STYLE19\">"+element.getGzfl4()+"&nbsp;</td>");
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

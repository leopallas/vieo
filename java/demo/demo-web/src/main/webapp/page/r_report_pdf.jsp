<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<%@ page import =" com.itextpdf.text.*,com.itextpdf.text.pdf.*, java.io.*,com.itextpdf.text.pdf.fonts.*"%>
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
    java.util.List<PictureVO> adminVOListGZFJ = new DBConnection().countPictureGZFJ(clhP, fromP, type, xc, tw, bw, gzfj);
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
    java.util.List<PictureVO> adminVOListGZFL = new DBConnection().countPictureGZFL(clhP, fromP, type, xc, tw, bw, gzfl);
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

%>

<%
    String fileName = String.valueOf(System.currentTimeMillis());

%>

<%
try{
    Document document = new Document(PageSize.A4, 36,36,36,36);
    ByteArrayOutputStream ba = new ByteArrayOutputStream();

    PdfWriter writer = PdfWriter.getInstance(document, ba);
    document.addTitle("铁路系统报表数据");
    document.addSubject("铁路系统报表数据");
    document.addAuthor("SYSTEM");
    document.addCreator("SYSTEM");
    document.addKeywords("pdf SYSTEM");

    document.open();


com.itextpdf.text.pdf.BaseFont bfChinese = com.itextpdf.text.pdf.BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font fontChinese = new com.itextpdf.text.Font(bfChinese, 10f, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font fontChineseTitle = new com.itextpdf.text.Font(bfChinese, 10f, com.itextpdf.text.Font.NORMAL);
    String[] headers = {"序号","质检员","车辆号","台位","部位","A类故障","B类故障","C类故障","整车落成","中间抽查","入段配件","配件落成"};
    float[] widths = { 0.07f, 0.10f, 0.11f, 0.06f, 0.10f, 0.10f, 0.10f,0.10f,0.10f,0.10f,0.10f,0.10f };
    PdfPTable table = new PdfPTable(headers.length);
    for (int i = 0; i < headers.length; i++) {
        PdfPCell cell = new PdfPCell(new Paragraph(headers[i],fontChineseTitle));
        table.addCell(cell);
     }

    int index = 1;
    while(list.hasNext()){
       TPVO element = list.next();
       PdfPCell cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(index++)),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getUid())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getClh())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getTw())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.display(Column.BW_TYPE.getLabelByCode(Tools.intConvert(element.getBw()))),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfj1())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfj2())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfj3())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfl1())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfl2())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfl3())),fontChinese));
       table.addCell(cell);

       cell = new PdfPCell(new Paragraph(Tools.getChinese(String.valueOf(element.getGzfl4())),fontChinese));
       table.addCell(cell);
    }


    table.setWidths(widths);
    table.setWidthPercentage(100f);
    document.add(table);

    document.close();
    response.setContentType("application/pdf");
    response.setContentLength(ba.size());
    ServletOutputStream outx = response.getOutputStream();
    ba.writeTo(outx);
    outx.flush();
}catch (Exception ex){
    ex.printStackTrace();
    System.out.print("xxxxx");
}
%>


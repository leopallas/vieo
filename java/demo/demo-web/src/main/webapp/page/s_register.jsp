<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>

    <script src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.barcode.0.3.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.PrintArea.js" type="text/javascript"></script>

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
                <td width="94%" valign="bottom"><span class="STYLE1">终端注册情况列表</span></td>
              </tr>
            </table></td>
            <td><div align="right"><span class="STYLE1">
              <input type="checkbox" name="checkbox11" id="checkbox11" onclick="MM_selectedAll(this.checked);" />
              	全选      &nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/add.gif" width="10" height="10" /> 
              	<a onclick="MM_create();" style="cursor:pointer;">添加</a>   &nbsp; <img src="<%=request.getContextPath()%>/images/del.gif" width="10" height="10" /> 
              	<a onclick="MM_delete();" style="cursor:pointer;">删除</a> </span>
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
        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">选择</span></div></td>
        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">序号</span></div></td>
        <td width="14%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">条码号</span></div></td>
        <td width="16%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">绑定设备号</span></div></td>
        <td width="27%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">设备状态</span></div></td>
        <td width="14%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">基本操作</span></div></td>
      </tr>
      <%
		  List<BarcdVO> barcdVOList = new DBConnection().listBarcode();
		  for(int i=0;i<barcdVOList.size();i++){
			  BarcdVO element = barcdVOList.get(i);
		  %>
      <tr>
        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center"><input type="checkbox" name="checkbox" id="checkbox" /></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=(i+1) %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=element.getBarcode() %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=element.getDeviceNo() == null?"--":element.getDeviceNo() %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=element.getStatus() %></div></td>
        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21"><a style="cursor:pointer;" onclick="MM_edit('<%=element.getBarcode()%>');">编辑</a>
            | <a style="cursor:pointer;" onclick="MM_deleteOne('<%=element.getBarcode()%>');">删除</a>
            | <a style="cursor:pointer;" onclick="MM_print('<%=element.getBarcode()%>');">打印条码</a>

        </div></td>
      </tr>
      <%} %>
      
    </table></td>
  </tr>
  <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong> <%=barcdVOList.size() %></strong> 条记录，当前第<strong> 1</strong> 页，共 <strong>1</strong> 页</span></div></td>
        <td width="67%"><table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_54.gif" width="40" height="15" /></div></td>
            <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_56.gif" width="45" height="15" /></div></td>
            <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_58.gif" width="45" height="15" /></div></td>
            <td width="49"><div align="center"><img src="<%=request.getContextPath()%>/images/main_60.gif" width="40" height="15" /></div></td>
            <td width="37" class="STYLE22"><div align="center">转到</div></td>
            <td width="22"><div align="center">
              <input type="text" name="textfield" id="textfield"  style="width:20px; height:12px; font-size:12px; border:solid 1px #7aaebd;"/>
            </div></td>
            <td width="22" class="STYLE22"><div align="center">页</div></td>
            <td width="35"><img src="<%=request.getContextPath()%>/images/main_62.gif" width="26" height="15" /></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>


<div style="display:none;" >
	<div style='margin:30px;' id="panel">
	<form method="post" name="xx" action="<%=request.getContextPath()%>/page/s_registerCreate.jsp">
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>条码数量：</div><div style='float:left'><input type='text' size='20' name="acct"/></div><div style='clear:both'></div></div>
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>&nbsp;</div><div style='float:left'><input type='button' value="新增" onclick="MM_createSubmit();"/><input type='button' value="取消" onclick="MM_cancel();"/></div><div style='clear:both'></div></div>
	</form>
	</div>
</div>

</body>
</html>
<script>
function MM_selectedAll(status){
	var ck = document.getElementsByName("checkbox");
	for(var i=0;i<ck.length;i++){
		ck[i].checked = status;
	}
}

function MM_create(){
	$.blockUI({ 
        message: $("#panel"), 
        showOverlay: false,
        css: { fontSize: '14px',top: '100px'}
    }); 
}
function IsNum(s)
{
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}
function MM_createSubmit(){
	var acct = document.xx.acct.value;
	if(acct == "" || !IsNum(acct)){
		alert("请输入需要产生的条码数量");
		document.xx.acct.focus();
		return;
	}
	if(parseInt(acct) > 5){
		alert("条码数量不能大于5");
		document.xx.acct.focus();
		return;
	}
	document.xx.submit();		
}
function MM_cancel(){
	$.unblockUI();
	$(".blockUI").fadeOut("slow");
}
function MM_delete(){
	alert("对不起，您的权限暂不支持本功能！");
}
function MM_deleteOne(id){
	if(confirm("删除后将不可以恢复，您确定要删除吗？")){
		location.href = "<%=request.getContextPath()%>/page/s_registerDelete.jsp?id=" + id;
	}
}
function MM_edit(id){
	alert("对不起，您的权限暂不支持本功能！");
}

function MM_print(bc){
    if(confirm("你确定要打印吗？")){
        BCDPrinter.execute(bc,bc);
    }
}

top.topFrame.SYS_NAVI_LIST.push(self.location.href);
</script>
<script>
    var BCDPrinter = {
        divExist:false,
        init:function(){
            var PA = '<div id="printArea" style="display:none">';
            PA += '<div style="width:300px;height:200px;border:0px solid red;padding:0px;margin:20px 10px 0px 10px;">';
            PA += '<div id="barcode_code" style="width:200px;height:60px;border:0px solid red;"></div>';
            PA += '<div style="margin-top:5px;margin-bottom:5px;font-size:14px;height:20px;;" id="barcode_label"></div>';
            PA += '</div>';
            PA += '</div>';
            $(PA).appendTo('body');
            BCDPrinter.divExist = true;
        },
        execute:function(_code, _label){
            if(!BCDPrinter.divExist){
                BCDPrinter.init();
            }
            $('#barcode_label').html(_label);
            $('#barcode_code').html(_code);
            $('#barcode_code').barcode({code:'code39'});
            $("#printArea").printArea();
        }
    }
</script>
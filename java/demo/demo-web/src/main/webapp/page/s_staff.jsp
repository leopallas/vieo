<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.demo.db.*,com.demo.util.*" %>
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
                <td width="94%" valign="bottom"><span class="STYLE1">员工账号列表</span></td>
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
        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">序号</span></div></td>
        <td width="14%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">员工号</span></div></td>
        <td width="16%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">员工姓名</span></div></td>
        <td width="25%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">员工权限</span></div></td>
        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">登录状态</span></div></td>
        <td width="14%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">基本操作</span></div></td>
      </tr>
        <script>
            var AccountList = [];
        </script>
      <%
		  List<StaffVO> adminVOList = new DBConnection().listStaff();
		  for(int i=0;i<adminVOList.size();i++){
			  StaffVO element = adminVOList.get(i);
              boolean isLogin = !(element.getSecretToken()==null);
	  %>
      <tr>
        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center"><input type="checkbox" name="checkbox" id="checkbox" /></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=(i+1) %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=element.getId() %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=element.getName() %></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">[<%=element.getUserType()==0?"普通巡检员":"高级巡检员"%>]</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><%=(isLogin?"已登录":"未登录") %></div></td>
        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21"><a style="cursor:pointer;" onclick="MM_edit('<%=element.getId()%>');">编辑</a> | <a style="cursor:pointer;" onclick="MM_deleteOne('<%=element.getId()%>');">删除</a>
            <%if(isLogin){%>| <a style="cursor:pointer;" onclick="MM_LogoutTerminal('<%=element.getId()%>');">强制登出</a><%}%></div></td>
      </tr>
        <script>
            AccountList[AccountList.length] = {"id":"<%=element.getId() %>","name":"<%=element.getName() %>","type":"<%=element.getUserType() %>"};
        </script>
      <%} %>
      
    </table></td>
  </tr>
  <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong> <%=adminVOList.size() %></strong> 条记录，当前第<strong> 1</strong> 页，共 <strong>1</strong> 页</span></div></td>
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
	<form method="post" name="xx" action="<%=request.getContextPath()%>/page/s_staffCreate.jsp">
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工账号：</div><div style='float:left'><input type='text' size='20' name="uid"/></div><div style='clear:both'></div></div>
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工姓名：</div><div style='float:left'><input type='text' size='20' name="uname"/></div><div style='clear:both'></div></div>
        <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工权限：</div><div style='float:left'><select name="tpe"><option value="0">普通巡检员</option><option value="1">高级巡检员</option></select></div><div style='clear:both'></div></div>
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>登录密码：</div><div style='float:left'><input type='password' size='20' name="pas1"/></div><div style='clear:both'></div></div>
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>确认密码：</div><div style='float:left'><input type='password' size='20' name="pas2"/></div><div style='clear:both'></div></div>
		<div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>&nbsp;</div><div style='float:left'><input type='button' value="添加" onclick="MM_createSubmit();"/><input type='button' value="取消" onclick="MM_cancel();"/></div><div style='clear:both'></div></div>
	</form>
	</div>
</div>


<div style="display:none;" >
    <div style='margin:30px;' id="panel2">
        <form method="post" name="xx2" action="<%=request.getContextPath()%>/page/s_staffUpdate.jsp">
            <input type='hidden' size='20' name="m_uid"/>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工账号：</div><div style='float:left' id="m_uid_id"></div><div style='clear:both'></div></div>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工姓名：</div><div style='float:left'><input type='text' size='20' name="m_uname"/></div><div style='clear:both'></div></div>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>员工权限：</div><div style='float:left'><select name="tpe"><option value="0">普通巡检员</option><option value="1">高级巡检员</option></select></div><div style='clear:both'></div></div>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>登录密码：</div><div style='float:left'><input type='password' size='20' name="m_pas1"/></div><div style='clear:both'></div></div>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>确认密码：</div><div style='float:left'><input type='password' size='20' name="m_pas2"/></div><div style='clear:both'></div></div>
            <div style='height:30px;line-heigth:30px;'><div style='float:left;width:90px;margin-left:50px;'>&nbsp;</div><div style='float:left'><input type='button' value="修改" onclick="MM_createSubmit2();"/><input type='button' value="取消" onclick="MM_cancel();"/></div><div style='clear:both'></div></div>
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
function MM_createSubmit(){
	var acc = document.xx.uid.value;
	var uname = document.xx.uname.value;
    var tpe = document.xx.tpe.value;
	var pas1 = document.xx.pas1.value;
	var pas2 = document.xx.pas2.value;
	if(acc == "" || acc.length < 2){
		alert("请输入合法的员工账号");
		document.xx.uid.focus();
		return;
	}
	if(uname == "" || uname.length < 2){
		alert("请输入合法的员工姓名");
		document.xx.uname.focus();
		return;
	}
    for(var i=0;i<AccountList.length;i++){
        if(AccountList[i].id == acc){
            alert("已存在该员工账号，请更换后重试");
            document.xx.uid.focus();
            return;
        }
    }
	if(pas1 == "" || pas1.length < 2){
		alert("请输入合法的密码");
		document.xx.pas1.focus();
		return;
	}
	if(pas1 != pas2){
		alert("确认密码与密码输入不一致");
		document.xx.pas2.focus();
		return;
	}
	document.xx.submit();		
}

function MM_createSubmit2(){
    var acc = document.xx2.m_uid.value;
    var pas1 = document.xx2.m_pas1.value;
    var pas2 = document.xx2.m_pas2.value;
    if(acc == "" || acc.length < 2){
        alert("请输入合法的员工账号");
        document.xx2.m_uid.focus();
        return;
    }
    if(pas1 == "" || pas1.length < 2){
        alert("请输入合法的密码");
        document.xx2.m_pas1.focus();
        return;
    }
    if(pas1 != pas2){
        alert("确认密码与密码输入不一致");
        document.xx2.m_pas2.focus();
        return;
    }
    document.xx2.submit();
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
		location.href = "<%=request.getContextPath()%>/page/s_staffDelete.jsp?id=" + id;
	}
}
function MM_edit(_id){
    //alert("对不起，您的权限暂不支持本功能！");
    for(var i=0;i<AccountList.length;i++){
        if(AccountList[i].id == _id){
            document.xx2.m_uid.value = AccountList[i].id;
            document.xx2.tpe.value = AccountList[i].type;
            document.xx2.m_uname.value = AccountList[i].name;
            document.getElementById("m_uid_id").innerHTML = _id;
        }
    }
    $.blockUI({
        message: $("#panel2"),
        showOverlay: false,
        css: { fontSize: '14px',top: '100px'}
    });
}

function MM_LogoutTerminal(id){
    if(confirm("此操作可能导致终端数据丢失，您确定要强制登出终端设备吗？")){
        location.href = "<%=request.getContextPath()%>/page/s_staffKickoff.jsp?id=" + id;
    }
}

top.topFrame.SYS_NAVI_LIST.push(self.location.href);
</script>
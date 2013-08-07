<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>声音播放</title>
</head>
<body>

<object width="190" height="90" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
<param name="src" value="<%= request.getParameter("src") %>" />
<param name="controller" value="true" />
<param name="type" value="video/quicktime" />
<param name="target" value="myself" />
<param name="bgcolor" value="black" />
<param name="pluginspage" value="http://www.apple.com/quicktime/download/index.html" />
<embed src="mymovie.3gp" width="320" height="250" controller="true" align="middle" bgcolor="black" target="myself" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/index.html"></embed>
</object>

</body>
</html>
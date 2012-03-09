<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/login.js"></script>
<title>系统登录</title>
<script>
	//
</script>
</head>
<body class="easyui-layout">

	<div region="center">
		<form id="loginForm" action="doLogin.do" method="post">
			<table width="100%" style="font-size: 12px">
				<tr>
					<td width="100" align="right">登录账户:</td>
					<td><input name="account" type="text" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">登录密码:</td>
					<td><input name="password" type="password" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">
					<a href="javascript:login();" class="easyui-linkbutton"
							style="float: right;" >登录</a>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</form>
		${message }
		
		
	</div>
</body>
</html>
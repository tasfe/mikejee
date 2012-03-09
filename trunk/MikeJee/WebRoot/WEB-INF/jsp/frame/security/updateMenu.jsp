<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/updateMenu.js"></script>
<title>修改菜单</title>
<script>
	//
</script>
</head>
<body class="easyui-layout">

	<div region="center">
		<form id="menuForm" action="updateMenu.do" method="post">
			<input name="menuId" type="hidden" value="${menu.menuId }" />
			<table width="100%" style="font-size: 12px">
				<tr>
					<td width="100" align="right">菜单名称:</td>
					<td><input name="menuName" type="text" style="width:200px;"
						class="easyui-validatebox" required="true" value="${menu.menuName }"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">上级菜单:</td>
					<td>
						<input id="parentName" type="text" name="parent.menuName" style="width:205px;" value="${menu.parent.menuName }" />
						<input id="parentId" type="hidden" name="parent.menuId" value="${menu.parent.menuId }" />
					</td>
				</tr>
				<tr>
					<td width="100" align="right">请求地址:</td>
					<td>
						<input type="text" name="request" style="width:200px;" value="${menu.request }" >
					</td>
				</tr>
				<tr>
					<td width="100" align="right">排序号:</td>
					<td>
						<input type="text" name="sortNo" style="width:200px;" value="${menu.sortNo }" >
					</td>
				</tr>
				<tr>
					<td width="100" align="right">备注:</td>
					<td>
						<input type="text" name="remark" style="width:200px;" value="${menu.remark }">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div region="south" border="false" style="padding-top: 8px;">
		<a href="javascript:closeChildWindow();" class="easyui-linkbutton"
			style="float: right;margin-left: 10px;margin-right: 5px;" 
			iconCls="icon-cancel">关闭</a>
		<a href="javascript:submitForm();" class="easyui-linkbutton"
			style="float: right;" iconCls="icon-save">保存</a>
	</div>	
	
</body>
</html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/updateRole.js"></script>
<title>修改角色</title>
<script>
	//
</script>
</head>
<body class="easyui-layout">

	<div region="center">
		<form id="roleForm" action="updateRole.do" method="post">
			<input type="hidden" name="roleId" value="${role.roleId }" />
			<table width="100%" style="font-size: 12px">
				<tr>
					<td width="100" align="right">角色名称:</td>
					<td><input name="roleName" type="text" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间" 
						value="${role.roleName }" >
					</td>
				</tr>
				<tr>
					<td width="100" align="right">所属部门:</td>
					<td>
						<input id="deptName" type="text" name="dept.deptName" style="width:205px;" value="${role.dept.deptName }" />
						<input id="deptId" type="hidden" name="dept.deptId" value="${role.dept.deptId }" />
					</td>
				</tr>
				<tr>
					<td width="100" align="right">角色类型:</td>
					<td>
						<select id="roleType" name="roleType" style="width:205px;">
							<option value="1" ${role.roleType=="1"?"selected":"" }>业务角色</option>
							<option value="2" ${role.roleType=="2"?"selected":"" }>管理角色</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="100" align="right">备注:</td>
					<td>
						<input type="text" name="remark" style="width:200px;" value="${role.remark }">
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
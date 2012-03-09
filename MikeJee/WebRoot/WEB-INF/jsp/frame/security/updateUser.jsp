<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/updateUser.js"></script>
<title>修改人员</title>
<script>
	//
</script>
</head>
<body class="easyui-layout">

	<div region="center">
		<form id="userForm" action="updateUser.do" method="post">
			<input type="hidden" name="userId" style="width:200px;" value="${user.userId }">
			<table width="100%" style="font-size: 12px">
				<tr>
					<td width="100" align="right">人员名称:</td>
					<td><input name="userName" type="text" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间" 
						value="${user.userName }">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">登录账户:</td>
					<td><input name="account" type="text" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间" 
						value="${user.account }">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">登录密码:</td>
					<td><input name="password" type="password" style="width:200px;"
						class="easyui-validatebox" required="true"
						validType="length[1,20]" invalidMessage="输入长度必须在1~20之间" 
						value="${user.password }">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">所属部门:</td>
					<td>
						<input id="deptName" type="text" name="dept.deptName" style="width:205px;" value="${user.dept.deptName }" />
						<input id="deptId" type="hidden" name="dept.deptId" value="${user.dept.deptId }" />
					</td>
				</tr>
				<tr>
					<td width="100" align="right">人员类型:</td>
					<td>
						<select id="userType" name="userType" style="width:205px;">
							<option value="1" ${user.userType=="1"?"selected":"" }>经办员</option>
							<option value="2" ${user.userType=="2"?"selected":"" }>管理员</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="100" align="right">性别:</td>
					<td>
						<select id="sex" name="sex" style="width:205px;">
							<option value="0" ${user.sex=="0"?"selected":"" }>未知</option>
							<option value="1" ${user.sex=="1"?"selected":"" }>男</option>
							<option value="2" ${user.sex=="2"?"selected":"" }>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="100" align="right">电话:</td>
					<td>
						<input type="text" name="phone" style="width:200px;" 
						value="${user.phone }">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">电子邮箱:</td>
					<td>
						<input type="text" name="email" style="width:200px;" 
						value="${user.email }">
					</td>
				</tr>
				<tr>
					<td width="100" align="right">备注:</td>
					<td>
						<input type="text" name="remark" style="width:200px;" 
						value="${user.remark }">
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
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/manageUser.js"></script>
<title>用户管理</title>
<script>
</script>
</head>
<body class="easyui-layout">

	<!-- west -->
    <div region="west" split="true" title="组织机构" style="width:250px;">   
    	<ul id="deptTree"></ul> 
    </div> 
     
    <!-- center -->
    <div id="content" region="center" title="" style="padding:0px;"> 
    	<!-- 查询div -->
    	<div id="p" class="easyui-panel" title="查询条件" icon="icon-search"
			collapsible="true" fit="false" minimizable="false" maximizable="false"
			closable="false" width="100%"
			style="height: auto; padding: 0px;margin-bottom:5px; background: #fafafa;">
			<table width="80%" style="float: left">
				 <tr>
				 	<td>用户名称：</td>
				 	<td><input id="userName" type="text" /></td>
				 </tr>
			</table>
			<div style="float: right : width : 50px; margin: 10px">
				<a href="javascript:void(0);" onclick="submitQueryForm();"
					class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
		 </div>
		
		<!-- 列表table -->
		<table id="datagridTb" width="100%" title="用户列表信息"
			singleSelect="true" idField="userId">
			<thead>
				<tr>
					<th field="userId" style="display: none">用户编号</th>
					<th field="userName" width="60px">用户名称</th>
					<th field="account" width="100px">登录账户</th>
					<th field="userType" width="100px">用户类型</th>
					<th field="sex" width="20px">性别</th>
					<th field="phone" width="100px">电话</th>
					<th field="email" width="100px">电子邮箱</th>
					<th field="remark" width="100px">备注</th>
				</tr>
			</thead>
		</table>
	
		<!-- winFrame -->
		<div id="dd" icon="icon-save"
			style="padding: 5px; width: 400px; height: 300px; display: none">
			<iframe id="winFrame" src="" width="100%" height="100%"
				scrolling="yes" frameborder="0"></iframe>
		</div>
    </div>
    
</body>
</html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/manageRole.js"></script>
<title>角色管理</title>
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
				 	<td>角色名称：</td>
				 	<td><input id="roleName" type="text" /></td>
				 </tr>
			</table>
			<div style="float: right : width : 50px; margin: 10px">
				<a href="javascript:void(0);" onclick="submitQueryForm();"
					class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
		 </div>
		
		<!-- 列表table -->
		<table id="datagridTb" width="100%" title="角色列表信息"
			singleSelect="true" idField="roleId">
			<thead>
				<tr>
					<th field="roleId" style="display: none">角色编号</th>
					<th field="roleName" width="60px">角色名称</th>
					<th field="roleType" width="100px">角色类型</th>
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
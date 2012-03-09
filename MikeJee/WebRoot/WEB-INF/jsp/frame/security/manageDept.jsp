<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/manageDept.js"></script>
<title>部门管理</title>
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
				 	<td>部门名称：</td>
				 	<td><input id="deptName" type="text" /></td>
				 	<td>业务对照码：</td>
				 	<td><input id="customId" type="text" /></td>
				 </tr>
			</table>
			<div style="float: right : width : 50px; margin: 10px">
				<a href="javascript:void(0);" onclick="submitQueryForm();"
					class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
		 </div>
		
		<!-- 列表table -->
		<table id="datagridTb" width="100%" title="部门列表信息"
			singleSelect="true" idField="deptId">
			<thead>
				<tr>
					<th field="deptId" style="display: none">部门编号</th>
					<th field="deptName" width="80px">部门名称</th>
					<th field="customId" width="50px">自定义编码</th>
					<th field="sortNo" width="15px">排序号</th>
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
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/security/roleAuthorize.js"></script>
<title>角色授权</title>
<script>
	//
</script>
</head>
<body class="easyui-layout">
	 <input type="hidden" id="roleId" name="roleId" value="${roleId }" />
	 
	 <div id="content" region="center" title="" style="padding:0px;">
		<div class="easyui-tabs" style="width:350px;height:330px;padding:0px;" border="false">  
		    <div title="选择菜单" style="padding:10px;">  
		    	<div id="grantMenuTree"></div>  
		    </div>  
		    <div title="选择人员" closable="false" style="padding:10px;">  
		        ... 
		    </div>  
		    <div title="Third Tab" iconCls="icon-reload" closable="false" style="padding:10px;">  
		        ...  
		    </div>  
		</div>  
	</div>
	<div region="south" border="false" style="padding-top: 8px;">
		<a href="javascript:closeChildWindow();" class="easyui-linkbutton"
			style="float: right;margin-left: 10px;margin-right: 5px;" 
			iconCls="icon-cancel">关闭</a>
		<a href="javascript:save();" class="easyui-linkbutton"
			style="float: right;" iconCls="icon-save">保存</a>
	</div>
	
</body>
</html>
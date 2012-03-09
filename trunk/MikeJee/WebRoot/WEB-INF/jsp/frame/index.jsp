<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/frame/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jsp/frame/include/include.jsp"%>
<script type="text/javascript" src="<%= path %>/js/frame/index.js"></script>
<title>...</title>
<script>
</script>
</head>
<body class="easyui-layout">  
 
    <div region="north" title="-" split="true" style="height:100px;">
    	<div>
    		MikeJee
    	</div>
    	<div>
    		<a href="<%= path %>/login/loginInit.do">退出登录</a>
    	</div>
    </div>
     
      
    <!-- south
    <div region="south" title="South Title" split="true" style="height:30px;"></div>  
    -->
    
    <!-- west -->
    <div region="west" split="true" title="<font color=green>系统导航</font>" 
    	style="width: 220px; padding1: 1px; overflow: hidden;">
    	<div id="menu" class="easyui-accordion" fit="true" border="false">
			<!-- <div id='newdiv' selected="true" title='new '>
				<a id='aaaa' href="#">ttt</a>
			</div>
			加载菜单的面板 -->
			
			<!--  
			<div id="sysCard" title="系统管理" style="overflow:auto;padding:0px;">  
		          <div id="sysMenuTree"></div>
		    </div> 
		    
		    <div id="klCard" title="知识管理" style="overflow:auto;padding:10px;">  
		          
		    </div>
		    
		    <div id="cpCard" title="企业管理" style="overflow:auto;padding:10px;">  
		          
		    </div>
			-->
			
		</div>
    </div>  
    
    <!-- center -->
    <div region="center" id="Main Title" style="overflow: hidden;">
    	<div id="conterTab" class="easyui-tabs" fit="true" border="false">
				
		</div>
    </div>  
    
</body> 
</html>
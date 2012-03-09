$(function() {

	//系统菜单
	//$('#sysMenuTree').tree({  
	//    url:'getChildrenByPid.do?menuType=sys',
	//    onClick:function(node) {
	    	//alert(node.attributes);
	    	//alert(node.attributes.request);
	    	
	    	//打开tab
	//    	addTab(node);
	//    },
	//    onLoadSuccess:function (node) {
	//		var node = $(this).tree('getRoot');
	//		if (null != node && node.state == 'closed') {
	//			$(this).tree('toggle',node.target);
	//		}
	//	}
	//});
	//var userId = 1;
	
	//create accordion
	//ajax request
	$.getJSON("getGranted2LevelMenus.do", 
	 //{userId: userId},  
	 function(data){
	 	if (data != null) {
	 		for (i=0; i<data.length; i++) {
	 			var m = data[i];
	 			//创建accordion
	 			var accor = $("#menu").accordion("add", {
				 				id:m.menuId,
				 				title:m.menuName
				 			 });
				//创建tree	 
				var t = $("<div class='easyui-tree'/>").tree({
							url :'getGrantedMenu.do?pMenuId='+m.menuId,
							onClick:addTab
						}); 
						
				//添加tree到	accordion.panel	
				accor.accordion('getPanel', m.menuName).append(t);					 
	 		}
	 	}
	});
	
	//main page
	initFirstPage();
	
});	

//add Tab
function addTab(node) {
	var isLeaf = $("#sysMenuTree").tree('isLeaf', node.target);
	if (isLeaf) {
		if (!$('#conterTab').tabs('exists', node.text)) {
			var tab = $("#conterTab").tabs('add', {
				title : node.text,
				style : 'overflow: hidden; padding: 5px;',
				iconCls : node.iconCls,
				loadingMessage : '页面正在加载...',
				content : "<iframe name='iframeTab' id='"
						+ node.id
						+ "' src='../"
						+ node.attributes.url
						+ "' style='height: 100%; width: 100%; overflow: hidden; border: medium none; margin: 0px; padding: 0px;'></iframe>",
				closable : true

			});
		} else {
			// 定位到相应的tab页面
			$("#conterTab").tabs('select', node.text);
		}
	} else {
		$("#sysMenuTree").tree('toggle', node.target);
	}
}

//init main page
function initFirstPage() {
	$("#conterTab").tabs('add', {
		id:'main_pagetab',
		title : 'welcome',
		style : 'overflow: hidden; padding: 5px;',
		//iconCls : node.iconCls,
		loadingMessage : '页面正在加载...',
		content : "<iframe name='iframeTab' id='main_page' src='main.do' style='height: 100%; width: 100%; overflow: hidden; border: medium none; margin: 0px; padding: 0px;'></iframe>",
		closable : false
		
	});
}
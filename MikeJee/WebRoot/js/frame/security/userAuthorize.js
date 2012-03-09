$(function() {
	var userId = $("#userId").val();
	
	$('#tt').tabs({  
	    border:false,  
	    onSelect:function(title){  
	        if (title == "选择角色") {
	        	loadGrantRoleTree();
	        } else if (title == "选择菜单") {
	        	loadGrantMenuTree();
	        }
	    }  
	}); 

});

//隐藏复选框
function setCheckedBoxHidden(rootNode) {
	$(rootNode.target).children("span").removeClass("tree-checkbox");
	var nodes = $("#grantRoleTree").tree('getChildren',rootNode.target);
				
	for (i=0; i<nodes.length; i++) {
		var n = nodes[i];
		if (n.attributes.nodeType == "dept") {
			$(n.target).children("span").removeClass("tree-checkbox");
		}
	}
}

//加载授权角色树
function loadGrantRoleTree() {
	var userId = $("#userId").val();
	
	//授权角色
	$("#grantRoleTree").tree({
		 url:'getRolesForGrant.do?userId='+userId,
		 checkbox:true,//显示复选框
		 cascadeCheck:false,//不级联
		 onlyLeafCheck:false,//...
		 onCheck:function(node, checked) {
		 	//var rootNode = $("#grantMenuTree").tree("getRoot");
		 	//cascadeCheck(rootNode, node);
		},
		onLoadSuccess : function(node, data) {
			var rootNode = $("#grantRoleTree").tree('getRoot');
			if (rootNode != null) {
				setCheckedBoxHidden(rootNode);
			}
		}
	});
}

//加载授权菜单树
function loadGrantMenuTree() {
	var userId = $("#userId").val();
	
	//授权角色
	$("#grantMenuTree").tree({
		 url:'getMenusForGrant.do?userId='+userId,
		 checkbox:true,//显示复选框
		 cascadeCheck:false,//不级联
		 onlyLeafCheck:false,//...
		 onCheck:function(node, checked) {
		 	//var rootNode = $("#grantMenuTree").tree("getRoot");
		 	//cascadeCheck(rootNode, node);
		}
		//onLoadSuccess : function(node, data) {
		//	var rootNode = $("#grantRoleTree").tree('getRoot');
		//	if (rootNode != null) {
		//		setCheckedBoxHidden(rootNode);
		//	}
		//}
	});
}

//保存角色人员关联
function saveSelectedRoles() {
	var userId = $("#userId").val();
	
	var checkedNodes = $("#grantRoleTree").tree("getChecked");
	//alert(checkedNodes.length);
	
	var roleIds = "";
	for (i=0; i<checkedNodes.length; i++) {
		var node = checkedNodes[i];
		
		if (i != (checkedNodes.length-1)) {
			roleIds += node.id+",";
		} else {
			roleIds += node.id;
		}
	}
	
	//ajax request
	$.getJSON("saveSelectedRolesForUser.do", 
	 {userId: userId, roleIds:roleIds},  
	 function(data){
		if (data == 'success') {
			$.messager.alert('提示','授权成功','info');
		} else {
			$.messager.alert('提示','授权失败','info');
		}
	});
}

//保存菜单人员关联
function saveSelectedMenus() {
	var userId = $("#userId").val();

	var checkedNodes = $("#grantMenuTree").tree("getChecked");
	
	var menuIds = "";
	for (i=0; i<checkedNodes.length; i++) {
		var node = checkedNodes[i];
		
		if (i != (checkedNodes.length-1)) {
			menuIds += node.id+",";
		} else {
			menuIds += node.id;
		}
	}
	
	//ajax request
	$.getJSON("saveSelectedMenuForUser.do", 
	 {userId: userId, menuIds:menuIds},  
	 function(data){
		if (data == 'success') {
			$.messager.alert('提示','授权成功','info');
		} else {
			$.messager.alert('提示','授权失败','info');
		}
	});
}

//保存
function save() {
	var selectedTab = $('#tt').tabs("getSelected"); 
	var title = selectedTab.panel("options").title;
	
	if (title == "选择角色") {
		saveSelectedRoles();
	} else if (title == "选择菜单") {
		saveSelectedMenus();
	}
	
}



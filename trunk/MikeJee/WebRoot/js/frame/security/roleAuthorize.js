$(function() {
	var roleId = $("#roleId").val();

	//级联问题???
	//授权菜单树
	$("#grantMenuTree").tree({
		 url:'getMenusForGrant.do?roleId='+roleId,
		 checkbox:true,//显示复选框
		 cascadeCheck:false,//不级联
		 onlyLeafCheck:false,//...
		 onCheck:function(node, checked) {
		 	//var rootNode = $("#grantMenuTree").tree("getRoot");
		 	//cascadeCheck(rootNode, node);
		}
	});

});

//保存
function save() {
	var checkedNodes = $("#grantMenuTree").tree("getChecked");
	//alert(checkedNodes.length);
	
	var menuIds = "";
	for (i=0; i<checkedNodes.length; i++) {
		var node = checkedNodes[i];
		
		if (i != (checkedNodes.length-1)) {
			menuIds += node.id+",";
		} else {
			menuIds += node.id;
		}
	}
	
	var roleId = $("#roleId").val();
	
	//ajax request
	$.getJSON("saveSelectedMenuForRole.do", 
	 {roleId: roleId, menuIds:menuIds},  
	 function(data){
		if (data == 'success') {
			$.messager.alert('提示','授权成功','info');
		} else {
			$.messager.alert('提示','授权失败','info');
		}
	});
}




$(function() {

	//菜单树
	$('#menuTree').tree({  
	    url:'getChildrenByPid.do',
	    onClick:function(node) {
	    	//单击节点，展开子节点
	    	$(this).tree("expand",node.target);
	    	//点击的节点id
	    	var pid = node.id;
	    	//reload datagrid
	    	reloadDatagrid(pid);
	    },
	    onLoadSuccess:function (node) {
			var node = $(this).tree('getRoot');
			if (null != node && node.state == 'closed') {
				$(this).tree('toggle',node.target);
				$(this).tree("select",node.target);
			}
		}
	});
	
	//分页列表
	$('#datagridTb').datagrid({
		//是否需要分页栏
		pagination : true,
		//是否显示行数列
		rownumbers : true,
		//是否只允许选中一行
		singleSelect : true,
		//是否适应列，就是填充整个列
		fitColumns : true,
		//数据路径
		url : "queryMenus.do",
		//列表是否合适的高度
		fit : false,
		//整个数据表格的高度
		height : $(document).height() - 100,
		//工具栏
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				var pid = $("#menuTree").tree("getSelected").id;
				$("#winFrame").attr("src", "toAddMenu.do?pid="+pid);
				$("#dd").show();
				$('#dd').dialog({
					title : "新建菜单",
					height : 400,
					width : 500,
					modal : true,// 屏蔽页面
					collapsible:true,// 是否可折叠
					autoOpen : false,
					onClose : function() {
						//窗口关闭时，重新加载datagrid
						$('#datagridTb').datagrid("reload");
						
						//reload node
						//选中的节点
						var selectedNode = $("#menuTree").tree("getSelected");
						var isLeaf = $("#menuTree").tree("isLeaf",selectedNode.target);
						if (isLeaf) {
							reloadParentTreeNode();
						} else {
							reloadSelectedTreeNode();
						}
					}
				});
			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				// 得到选中的数据
				var rows = $("#datagridTb").datagrid("getSelections");
				if (rows.length != 1) {
					$.messager.alert("系统提示", "请选择要修改的行！", 'info');
					return;
				}
				var menuId = rows[0].menuId;
				$("#winFrame").attr("src", "toUpdateMenu.do?menuId="+menuId);
				$("#dd").show();
				$('#dd').dialog({
					title : "修改菜单",
					height : 400,
					width : 500,
					modal : true,// 屏蔽页面
					collapsible:true,// 是否可折叠
					autoOpen : false,
					onClose : function() {
						$('#datagridTb').datagrid("reload");
						
						//选中的节点
						var selectedNode = $("#menuTree").tree("getSelected");
						if (selectedNode != null && selectedNode.id == menuId) {
							reloadParentTreeNode();
						} else {
							reloadSelectedTreeNode();
						}
					}
				});
			}
		},'-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				var row = $('#datagridTb').datagrid('getSelected');
				if (row) {
					var index = $('#datagridTb').datagrid('getRowIndex', row);
					var menuId = row.menuId;
					$.messager.confirm('提示', '数据删除不可恢复，您确认删除吗?', function(r){
						if (r){
							$.getJSON("delMenu.do", { menuId: row.menuId },  function(data){
								if (data == 'success') {
									$.messager.alert('提示','删除成功','info');
									$('#datagridTb').datagrid('deleteRow', index);
									$('#datagridTb').datagrid("reload");
									
									//选中的节点
									var selectedNode = $("#menuTree").tree("getSelected");
									if (selectedNode != null && selectedNode.id == menuId) {
										reloadParentTreeNode();
									} else {
										reloadSelectedTreeNode();
									}
									
								} else {
									$.messager.alert('提示','删除失败','info');
								}
							});
						}
					});
				}else{
					$.messager.alert('提示','请选择一条需要删除的纪录','info');
				}
			}
		} ]
	});
	
	//隐藏字段
	$('#datagridTb').datagrid('hideColumn', "menuId")
	
});

//重新加载分页列表
function reloadDatagrid(parentId) {
	var queryParams = $('#datagridTb').datagrid('options').queryParams;   
	// 更改queryParams对象。
	queryParams.menuName = $("#menuName").val();
	if (parentId != null) {
		queryParams.parentId = parentId;
	}
	
	//当点击查询的时候把页面设置成第一页
   	$('#datagridTb').datagrid('getPager').pagination({
   		pageNumber:1
   	});
	
	$('#datagridTb').datagrid('reload'); 
}

//reload选中的树节点
function reloadSelectedTreeNode() {
	//刷新树
	var selectedNode = $("#menuTree").tree("getSelected");
	$("#menuTree").tree("reload",selectedNode.target);
}

//reload选中节点的父节点
function reloadParentTreeNode() {
	var selectedNode = $("#menuTree").tree("getSelected");
	var parentNode = $("#menuTree").tree("getParent",selectedNode.target);
	$("#menuTree").tree("reload",parentNode.target);
	$("#menuTree").tree("select",parentNode.target);
}

//提交查询
function submitQueryForm() {
	reloadDatagrid(null); 
}


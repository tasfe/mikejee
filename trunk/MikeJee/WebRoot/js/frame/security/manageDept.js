$(function() {

	//组织机构树
	$('#deptTree').tree({  
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
		url : "queryDepts.do",
		//列表是否合适的高度
		fit : false,
		//整个数据表格的高度
		height : $(document).height() - 100,
		//工具栏
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				//获取选中的组织机构树id
				var pid = $("#deptTree").tree("getSelected").id;
				$("#winFrame").attr("src", "toAddDept.do?pid="+pid);
				$("#dd").show();
				$('#dd').dialog({
					title : "新建部门",
					height : 300,
					width : 500,
					modal : true,// 屏蔽页面
					autoOpen : false,
					collapsible:true,// 是否可折叠
					onClose : function() {
						//窗口关闭时，重新加载datagrid
						$('#datagridTb').datagrid("reload");
						
						//reload node
						//选中的节点
						var selectedNode = $("#deptTree").tree("getSelected");
						var isLeaf = $("#deptTree").tree("isLeaf",selectedNode.target);
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
				var deptId = rows[0].deptId;
				
				if (!isAllowUpdate(deptId)) {
					$.messager.alert('提示','根部门不允许修改!','info');
					return;
				}
				
				$("#winFrame").attr("src", "toUpdateDept.do?deptId="+deptId);
				$("#dd").show();
				$('#dd').dialog({
					title : "修改部门",
					height : 300,
					width : 500,
					modal : true,// 屏蔽页面
					autoOpen : false,
					collapsible:true,// 是否可折叠
					onClose : function() {
						$('#datagridTb').datagrid("reload");
						
						//选中的节点
						var selectedNode = $("#deptTree").tree("getSelected");
						if (selectedNode != null && selectedNode.id == deptId) {
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
					var deptId = row.deptId;
					
					if (!isAllowUpdate(deptId)) {
						$.messager.alert('提示','根部门不允许删除!','info');
						return;
					}
					
					$.messager.confirm('提示', '数据删除不可恢复，您确认删除吗?', function(r){
						if (r){
							$.getJSON("delDept.do", { deptId: row.deptId },  function(data){
								if (data == 'success') {
									$.messager.alert('提示','删除成功','info');
									$('#datagridTb').datagrid('deleteRow', index);
									$('#datagridTb').datagrid("reload");
									
									//选中的节点
									var selectedNode = $("#deptTree").tree("getSelected");
									if (selectedNode != null && selectedNode.id == deptId) {
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
	$('#datagridTb').datagrid('hideColumn', "deptId")
	
});

//重新加载分页列表
function reloadDatagrid(parentId) {
	var queryParams = $('#datagridTb').datagrid('options').queryParams;   
	// 更改queryParams对象。
	queryParams.deptName = $("#deptName").val();
	queryParams.customId = $("#customId").val();
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
	var selectedNode = $("#deptTree").tree("getSelected");
	$("#deptTree").tree("reload",selectedNode.target);
}

//reload选中节点的父节点
function reloadParentTreeNode() {
	var selectedNode = $("#deptTree").tree("getSelected");
	var parentNode = $("#deptTree").tree("getParent",selectedNode.target);
	$("#deptTree").tree("reload",parentNode.target);
	$("#deptTree").tree("select",parentNode.target);
}

//提交查询
function submitQueryForm() {
	reloadDatagrid(null); 
}

//判断是否允许修改
function isAllowUpdate(deptId) {
	//根节点
	if (deptId == 1) {
		return false;
	}
	
	return true;
}


$(function() {

	//组织机构树
	$('#deptTree').tree({  
	    url:'../dept/getChildrenByPid.do',
	    //url:'getDeptTree.do',
	    onClick:function(node) {
	    	//单击节点，展开子节点
	    	$(this).tree("expand",node.target);
	    	//点击的节点id
	    	var deptId = node.id;
	    	//reload datagrid
	    	reloadDatagrid(deptId);
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
		url : "queryUsers.do",
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
				var deptId = $("#deptTree").tree("getSelected").id;
				$("#winFrame").attr("src", "toAddUser.do?deptId="+deptId);
				$("#dd").show();
				$('#dd').dialog({
					title : "新建人员",
					height : 400,
					width : 500,
					modal : true,// 屏蔽页面
					collapsible:true,// 是否可折叠
					autoOpen : false,
					onClose : function() {
						//窗口关闭时，重新加载datagrid
						$('#datagridTb').datagrid("reload");
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
				var userId = rows[0].userId;
				$("#winFrame").attr("src", "toUpdateUser.do?userId="+userId);
				$("#dd").show();
				$('#dd').dialog({
					title : "修改人员",
					height : 400,
					width : 500,
					modal : true,// 屏蔽页面
					collapsible:true,// 是否可折叠
					autoOpen : false,
					onClose : function() {
						$('#datagridTb').datagrid("reload");
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
					var userId = row.userId;
					$.messager.confirm('提示', '数据删除不可恢复，您确认删除吗?', function(r){
						if (r){
							$.getJSON("delUser.do", { userId: row.userId },  function(data){
								if (data == 'success') {
									$.messager.alert('提示','删除成功','info');
									$('#datagridTb').datagrid('deleteRow', index);
									$('#datagridTb').datagrid("reload");	
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
		},'-', {
			text : '授权',
			iconCls : 'icon-edit',
			handler : function() {
				// 得到选中的数据
				var rows = $("#datagridTb").datagrid("getSelections");
				if (rows.length != 1) {
					$.messager.alert("系统提示", "请选择要授权的人员！", 'info');
					return;
				}
				var userId = rows[0].userId;
				$("#winFrame").attr("src", "toUserAuthorize.do?userId="+userId);
				$("#dd").css("padding","0");
				$("#dd").show();
				$('#dd').dialog({
					title : "人员授权",
					height : 450,
					width : 400,
					modal : true,// 屏蔽页面
					collapsible:true,// 是否可折叠
					autoOpen : false,
					onClose : function() {
						//$('#datagridTb').datagrid("reload");
					}
				});
			}
		}]
	});
	
	//隐藏字段
	$('#datagridTb').datagrid('hideColumn', "userId")
	
});

//重新加载分页列表
function reloadDatagrid(deptId) {
	var queryParams = $('#datagridTb').datagrid('options').queryParams;   
	// 更改queryParams对象。
	queryParams.userName = $("#userName").val();
	if (deptId != null) {
		queryParams.deptId = deptId;
	}
	
	//当点击查询的时候把页面设置成第一页
   	$('#datagridTb').datagrid('getPager').pagination({
   		pageNumber:1
   	});
	
	$('#datagridTb').datagrid('reload'); 
}

//提交查询
function submitQueryForm() {
	reloadDatagrid(null); 
}


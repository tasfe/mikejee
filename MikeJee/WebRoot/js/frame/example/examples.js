$(function() {
	
	//分页列表
	var lastIndex;
	$('#datagridTb').datagrid({
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		fitColumns : true,
		url:"queryExamples.do",
		fit : false,
		height:$(document).height()-130,
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				$("#winFrame").attr("src", "toAddDept.do");
				$("#dd").show();
				$('#dd').dialog({
					title : "新建部门",
					height : 500,
					width : 800,
					modal : true,// 屏蔽页面
					autoOpen : false,
					onClose:function(){
						$('#datagridTb').datagrid("reload");
					}
				});
			}
		}  ],
		onBeforeLoad : function() {
			$(this).datagrid('rejectChanges');
		},
		onClickRow : function(rowIndex) {
			lastIndex = rowIndex;
		}
	});
	$('#datagridTb').datagrid('hideColumn', "deptId");
});

function submitQueryForm() {
	var queryParams = $('#datagridTb').datagrid('options').queryParams;   
	// 更改queryParams对象。
	/*
	queryParams.userName = $("#userName").val(); 
	queryParams.realName = $("#realName").val();
	if( $("#sex").val()>-1){
		queryParams.sex = $("#sex").val();
	}else{
		queryParams.sex ="";
	}
	if( $("#enable").val()>-1){
		queryParams.enabled= $("#enable").val();
	}else{
		queryParams.enabled="";
	}
	queryParams.startDate = $("#startDate").datebox('getValue');
	queryParams.endDate = $("#endDate").datebox('getValue');
	*/
	
	$('#datagridTb').datagrid('reload');  
}


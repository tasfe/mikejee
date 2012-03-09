$(function() {

	//构造ComboTree(下拉树)
	$('#deptName').combotree({ 
    	onClick:function(node) {
	    	$("#deptName").combotree('setValue',node.text);
	    	$("#deptId").val(node.id);
	    },
	    onShowPanel:function(){
	    	$(this).combotree('reload','getChildrenDeptByPid.do');
	    },
	    onLoadSuccess:function (node) {
			var node = $(this).tree('getRoot');
			if (null != node && node.state == 'closed') {
				$(this).tree('toggle',node.target);
			}
		}
	});

});

//提交表单
function submitForm() {
	if ($("#roleForm").form('validate')) {
		$("#roleForm")[0].submit();
	}
}
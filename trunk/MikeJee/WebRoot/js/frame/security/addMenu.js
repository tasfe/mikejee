$(function() {

	//构造ComboTree(下拉树)
	$('#parentName').combotree({ 
    	onClick:function(node) {
	    	$("#parentName").combotree('setValue',node.text);
	    	$("#parentId").val(node.id);
	    },
	    onShowPanel:function(){
	    	$(this).combotree('reload','getChildrenByPid.do');
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
	if ($("#menuForm").form('validate')) {
		$("#menuForm")[0].submit();
	}
}
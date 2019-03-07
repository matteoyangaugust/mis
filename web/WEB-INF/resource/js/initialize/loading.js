$(function(){
    ajax(ctxPath + '/initialize/setupMenuSn.do', {}, function(result){
        if(result.success){
            var $redirectForm = $("#redirectForm");
            var targetMenuBean = result.data['menuRecord'];
            $redirectForm.find('#mainMenuSn').val(targetMenuBean['mainMenu']['sn']);
            $redirectForm.find('#subMenuSn').val(targetMenuBean['subMenu']['sn']);
            $redirectForm.attr('action', ctxPath + targetMenuBean['subMenu']['url'].replace('/', ''));
            $redirectForm.submit();
        }else{
            swal({title:result['msg'], allowOutsideClick:false});
        }
    });
});

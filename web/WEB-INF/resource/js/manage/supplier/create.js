$(function(){
    $("#submit").bind('click', function(){
        createSupplier();
    });
});

function createSupplier(){
    if(isValid()){
        ajax(ctxPath + 'manage/supplier/creating.do', {name:$("#name").val()}, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            });
        });
    }
    function isValid(){
        var errorStr = "";
        var isValid = true;
        $(".notEmpty").each(function(){
            var $this = $(this);
            if(isNothingStrValue($this.val())){
                errorStr += "*" + $this.attr("aria-describedby") + " not be empty\n";
                isValid = false;
            }
        });
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
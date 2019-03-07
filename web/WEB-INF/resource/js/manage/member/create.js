$(function(){
    $('#submit').bind('click', function(){
        creatingUser();
    });
    $(".reload").bind('click', function(){
        reloadView();
    });
});

function creatingUser(){
    if(isValid()){
        ajax(ctxPath + 'manage/member/creating.do', $('#memberForm').serializeArray(), function(result){
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
        if($("#password").val() != $('#passwordConfirm').val()){
            errorStr += "*" + "'Password' isn't same with 'Password Confirm'\n";
            isValid = false;
        }
        if($("#role").val() == 'none'){
            errorStr += "*" + "Role isn't selected\n";
            isValid = false;
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
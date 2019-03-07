$(function(){
    $(".save").bind('click', function(){
        modifyUser($(this).closest('.userForm'));
    });
    $('.delete').bind('click', function(){
        deleteUser($(this).closest('.userForm'));
    });
    $(".changePassword").bind('click', function(){
        changePassword($(this).closest('.userForm'));
    });
});

/**
 *
 * @param $userForm
 */
function changePassword($userForm){
    var $changePasswordTemplate = $(".changePasswordTemplate").clone();
    $changePasswordTemplate.show();
    sweetAlertAttr.confirmCallback = confirmCallback;
    sHTMLAlert("New Password", $changePasswordTemplate, sweetAlertAttr);

    function confirmCallback(){
        var $swalContent = $('.swal2-content');
        var password = $swalContent.find('#password').val();
        var passwordConfirm = $swalContent.find('#passwordConfirm').val();
        if(isValid($swalContent)){
            ajax(ctxPath + 'manage/member/changePassword.do', {sn:$userForm.find('.sn').val(), password:password}, function(result){
                sAlert(result['msg'], result['success']);
            });
        }
    }

    function isValid($swalContent){
        var errorStr = "";
        var isValid = true;
        $swalContent.find(".notEmpty").each(function(){
            var $this = $(this);
            if(isNothingStrValue($this.val())){
                errorStr += "*" + $this.attr("aria-describedby") + " not be empty\n";
                isValid = false;
            }
        });
        if($swalContent.find("#password").val() != $swalContent.find('#passwordConfirm').val()){
            errorStr += "*'New Password' is not the same with 'New Password Confirm'\n";
            isValid = false;
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}

/**
 *
 * @param sn
 */
function deleteUser($userForm){
    sConfirm(getSwalTextSpan("Are you sure to undo?", "dangerText"), function(){
        ajax(ctxPath + 'manage/member/delete.do', {sn:$userForm.find('.sn').val()}, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    $userForm.remove();
                }
            });
        });
    });
}

/**
 *
 * @param $saveBtn
 */
function modifyUser($userForm){
    if(isValid($userForm)){
        ajax(ctxPath + 'manage/member/modifyUser.do', $userForm.serializeArray(), function(result){
            sAlert(result['msg'], result['success']);
        });
    }
    function isValid($userForm){
        var errorStr = "";
        var isValid = true;
        $userForm.find(".notEmpty").each(function(){
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
$(function(){
    hasResult();
    $(".reportChoiceLabel").bind('click', function(){
        reportChoiceChanged($(this));
    });

    $('#submit').bind('click', function(){
        toPrint();
    });

    $('.reportTypeLabel').bind('click', function(){
        var $thisInput = $(this).find('.reportType');
        if($thisInput.val() == 'pdf'){
            $("#printForm").attr('action', ctxPath + 'manage/print/printPdf.do');
        }else{
            $("#printForm").attr('action', ctxPath + 'manage/print/printExcel.do');
        }
    });
});

function hasResult(){
    if(!result['success']){
        sAlert(result['msg'], false);
    }
}

function reportChoiceChanged($label){
    if($label.find('.reportChoice').val() == 'generation'){
        $('.duration').show();
    }else{
        $('.duration').hide();
        $(".duration").find('.date').val('');
    }
}

function toPrint(){
    if(isValid()){
        $('#printForm').submit();
    }

    function isValid(){
        var errorStr = "";
        var isValid = true;
        if($('.reportChoice:checked').val() == 'generation'){
            $('.date').each(function(){
                if($(this).val() == ''){
                    isValid = false;
                    errorStr = "*Please select duration";
                }
            });
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
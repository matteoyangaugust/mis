$(function(){
    initialDoubleNumber($('.quantity'));
    $(".save").bind('click', function(){
        toSave($(this));
    });
    
    $(".delete").bind('click', function(){
        toDelete($(this));
    });

    $("#pageSearchBtn").bind('click', function(){
        pageSearch()
    });

    $("#pageSearch").bind('change', function(){
       pageSearch();
    });
    $("#pageSearch").bind('keyup', function(){
        pageSearch();
    });
});


function pageSearch(){
    var str = $("#pageSearch").val();
    if(isNothingStrValue(str)){
        $(".materialGroup").show();
    }else{
        $('.materialGroup').each(function(){
            var $materialGroup = $(this);
            if($materialGroup.find('.name').val().toLowerCase().indexOf(str.toLowerCase()) >= 0){
                $materialGroup.show();
            }else{
                $materialGroup.hide();
            }
        });
    }
}

/**
 *
 * @param $deleteBtn
 */
function toDelete($deleteBtn){
    var $materialGroup = $deleteBtn.closest('.materialGroup');
    sConfirm("確定刪除原料?", function(){
        ajax(ctxPath + 'material/manage/delete.do', {
            sn : $materialGroup.find('.sn').val()
        }, function(result){
            if(result['success']){
                $materialGroup.remove();
            }
            sAlert(result['msg'], result['success']);
        });
    });
}

/**
 * 儲存修改
 * @param $saveBtn
 */
function toSave($saveBtn){
    var $materialGroup = $saveBtn.closest('.materialGroup');
    if(isValid()){
        ajax(ctxPath + 'material/manage/modify.do', $materialGroup.serializeArray(), function(result){
            sAlert(result['msg'], result['success']);
        });
    }
    function isValid(){
        var errorStr = "";
        var isValid = true;
        $materialGroup.find(".notEmpty").each(function(){
            var $this = $(this);
            if(isNothingStrValue($this.val())){
                errorStr += "*" + $this.attr("aria-describedby") + " isn't allowed empty\n";
                isValid = false;
            }
        });
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
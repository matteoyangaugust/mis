$(function(){

    $("#submit").bind('click', function(){
        create();
    });
});

function create(){
    if(isValid()){
        ajax(ctxPath + '/material/create/creating.do', $("#materialForm").serializeArray(), function(result){
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
                errorStr += "*" + $this.attr("aria-describedby") + " isn't allowed empty\n";
                isValid = false;
            }
        });
        if($("#unit").val() == 'none'){
            errorStr += "*" + 'Please select unit\n';
            isValid = false;
        }
        if($("#supplier").val() == 'none'){
            errorStr += "*" + 'Please select supplier\n';
            isValid = false;
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
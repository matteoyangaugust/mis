$(function(){
    $("#toProduce").bind('click', function(){
        toProduce();
    });

    $(".toDelete").bind('click', function(){
        toDelete($(this));
    });
});

function toDelete($deleteBtn) {
    sConfirm(getSwalTextSpan("Are you sure to undo?", "dangerText"), function(){
        var historyToDelete;
        $.each(histories, function(index, history){
            if(history.sn == $deleteBtn.val()){
                historyToDelete = history;
            }
        });
        ajax(ctxPath + 'product/produce/delete.do', historyToDelete, function(result){
            sAlert(result['msg'], result['success'], function(){
                reloadView();
            });
        });
    });
}

function toProduce(){
    var $form = $(".toProduceTemplate").clone(true);
    $form.show();
    sweetAlertAttr.confirmCallback = confirmCallback;
    sweetAlertAttr.width = '500px';
    sHTMLAlert(getSwalTextSpan("Build Product", "swalLabelText"), $form, sweetAlertAttr);

    function confirmCallback(){
        var $swalContent = $('.swal2-content');
        $.each(products, function(index, product){
            if(product.sn == $swalContent.find(".product_sn").val()){
                $swalContent.find(".remain").val(product['remain']);
            }
        });
        if(isValid()){
            ajax(ctxPath + 'product/produce/saving.do', $swalContent.find('.toProduceTemplate').serializeArray(), function(result){
                sAlert(result['msg'], result['success'], function(){
                    if(result['success']){
                        reloadView();
                    }
                });
            });
        }
        function isValid(){
            var isValid = true;
            var errorStr = '';
            $swalContent.find(".notEmpty").each(function(){
                var $this = $(this);
                if(isNothingStrValue($this.val())){
                    errorStr += "*" + $this.attr("aria-describedby") + " not be empty\n";
                    isValid = false;
                }
            });
            $swalContent.find("select").each(function(){
                var $this = $(this);
                if($this.val() == 'none'){
                    errorStr += "*" + $this.attr("aria-describedby") + " isn't selected\n";
                    isValid = false;
                }
            });


            if(!isValid){
                sAlert(errorStr, false);
            }
            return isValid;
        }
    }
}
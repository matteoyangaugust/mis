var $swal;
var currentMaterial;
$(function(){
    initialDoubleNumberText($(".doubleNumber"));
    $("#toProduce").bind('click', function(){
        toProduce();
    });
    $(".toDelete").bind('click', function(){
        toDelete($(this));
    });
    $(".checkDetail").bind('click', function(){
        checkDetail($(this));
    });
});

function checkDetail($checkBtn){
    var $detailForm = $('.detailTemplate').clone(true);
    $.each(histories, function(index, history){
        if(history.sn == $checkBtn.val()){
            $detailForm.find(".updateTimeWithUnit").text(history['updateTimeWithUnit']);
            $detailForm.find(".buyingTimeWithUnit").text(history['buyingTimeWithUnit']);
            $detailForm.find(".identifier").text(history['materialIdentifier']);
            $detailForm.find(".name").text(history['materialName']);
            $detailForm.find(".supplierName").text(history['supplierName']);
            $detailForm.find(".fee").text(history['fee'] + " NTD");
            $detailForm.find(".quantity").text(history['quantity'] + "\n" + getUnitForMaterial(history['unit']));
            $detailForm.find(".remain").text(history['remain'] + "\n" + getUnitForMaterial(history['unit']));
        }
    });
    sHTMLAlertWithNoCancel(getSwalTextSpan("Detail"), $detailForm);
}

function toDelete($deleteBtn){
    sConfirm(getSwalTextSpan("Are you sure to delete this record?", "dangerText"), function(){
        ajax(ctxPath + 'material/produce/delete.do', {sn : $deleteBtn.val()}, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            });
        });
    });
}

function initialEvent(){
    swalNumInputEvent();
    $swal = $('.mySwal');
    $swal.find('#quantity').bind('keyup', function(){
        quantityChanged();
    });
    $swal.find('#quantity').bind('change', function(){
        quantityChanged();
    });
    $swal.find('#quantity').bind('mousedown', function(){
        quantityChanged();
    });
}

function materialChanged(){
    $swal.find('#unit').text('');
    var materialSn = $swal.find("#material_sn").val();
    if(materialSn == 'none'){
        $swal.find('.showTogether').find('.notEmpty').val(0);
        $swal.find('.supplier_sn').val('none');
        $swal.find('.buying_time').val('');
        $swal.find('.showTogether').hide();
    }else{
        $.each(materials, function(index, material){
            if(material.sn == $swal.find("#material_sn").val()){
                $swal.find('#unit').text(getUnitForMaterial(material['unit']));
                $swal.find('#supplier_sn').val(material['supplier']);
                currentMaterial = material;
                $swal.find('.showTogether').show();
                return false;
            }
        });
    }
}

function quantityChanged(){
    var price = currentMaterial['price'];
    var quantity = parseFloat($swal.find('#quantity').val());
    $swal.find("#fee").val(FloatNumber.mul(price, quantity));
}

function toProduce(){
    var $form = $(".addTemplate").clone(true);
    $form.find('.showTogether').hide();
    $swal = $('.mySwal');
    sweetAlertAttr.confirmCallback = confirmCallback;
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = onOpen;
    sHTMLAlert(getSwalTextSpan("原料進貨"), $form, sweetAlertAttr);
    initialEvent();

    function onOpen(){
        $swal.find('#material_sn').bind('change', function(){
            materialChanged();
        });
        initialEvent();
    }



    function confirmCallback(){
        if(isValid()){
            console.log($swal.find('.addTemplate').serializeArray())
            ajax(ctxPath + 'material/produce/saving.do', $swal.find('.addTemplate').serializeArray(), function(result){
                sAlert(result['msg'], result['success'], function(){
                    if(result['success']){
                        reloadView();
                    }
                });
            });
        }
    }

    function isValid(){
        var errorStr = "";
        var isValid = true;
        $swal.find(".notEmpty").each(function(){
            var $this = $(this);
            if(isNothingStrValue($this.val())){
                errorStr += "*" + $this.attr("aria-describedby") + " isn't allowed empty\n";
                isValid = false;
            }
        });
        $swal.find('.selector').each(function(){
            var $this = $(this);
            if($this.val() == 'none'){
                errorStr += "*" + $this.attr("aria-describedby") + "isn't selected\n";
                isValid = false;
            }
        });

        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
var currentComponent = new Object();
var overrideStatus = false;
var isPurchase = false;
$(function(){
    $("#toProduce").bind('click', function(){
        isPurchase = false;
        toProduce();
    });
    $(".checkDetail").bind('click', function(){
        checkDetail($(this));
    });
    $('.toDelete').bind('click', function(){
        toDelete($(this));
    });
});

function bindTimeEvent(){
    $swal.find('.timePurposeBlock').bind('click', function(){
        countTimeTook();
    });
    $swal.find('.isOverrideTimeLabel').bind('click', function(){
        isOverride($(this));
    });
    $swal.find('#amount').bind('keyup', function(){
        amountChanged();
        countTimeTook();
    });
    $swal.find('#amount').bind('change', function(){
        amountChanged()
        countTimeTook();
    });
    $swal.find('#amount').bind('mousedown', function(){
        amountChanged()
        countTimeTook();
    });
    $swal.find('#component_sn').bind('change', function(){
        changeComponent($(this));
        is_need_pay($(this));
    });
    $swal.find("#process_amount").bind('keyup', function(){
        processAmountChanged();
        countTimeTook();
    });
    $swal.find("#process_amount").bind('mousedown', function(){
        processAmountChanged();
        countTimeTook();
    });
    $swal.find("#process_amount").bind('change', function(){
        processAmountChanged();
        countTimeTook();
    });
}

function amountChanged(){
    var amount = parseInt($swal.find('#amount').val());
    if(isPurchase){
        var purchase_fee = parseInt(currentComponent['purchase_fee']);
        $swal.find('#purchase_fee').val(purchase_fee * amount);
    }else{
        var isNeedToCount = true;
        //如果原本工序設定是購買，則不須自動計算
        $.each(processCategories, function(index, processCategory){
            if(processCategory.sn == currentComponent['process_category_sn']){
                if(processCategory['relation'].indexOf('ROLE_PURCHASE') != -1){
                    isNeedToCount = false;
                }
            }
        });
        if(isNeedToCount){
            var process_yield = parseInt(currentComponent['process_yield']);
            var process_amount = Math.ceil(amount / process_yield);
            $swal.find("#process_amount").val(process_amount);
        }
    }
}

function processAmountChanged(){
    var process_yield = parseInt(currentComponent['process_yield']);
    var process_amount = parseInt($swal.find('#process_amount').val());
    $swal.find("#amount").val(process_amount * process_yield);
}

function countTimeTook(){
    var process_duration = parseInt(currentComponent['process_duration']);
    var process_amount = parseInt($swal.find('#process_amount').val());
    var totalTimeBean = timeCovert(process_duration * process_amount);
    $.each(components, function(index, component){
        if(component.sn == $swal.find("#component_sn").val()){
            component.totalTimeBean = totalTimeBean;
            if(timePurpose == 'total'){
                $swal.find('#time_took_of_day').val(component['totalTimeBean']['day']);
                $swal.find('#time_took_of_hour').val(component['totalTimeBean']['hour']);
                $swal.find('#time_took_of_minute').val(component['totalTimeBean']['minute']);
                $swal.find('#time_took_of_second').val(component['totalTimeBean']['second']);
            }
            $swal.find("#total_duration").val(process_duration * process_amount);
        }
    });
}

function isOverride($overrideBtn){
    var $mySwal = $('.mySwal');
    if($overrideBtn.find('.isOverrideTime').val() == 'false'){
        overrideStatus = false;
        $mySwal.find('.timeTook').find('.timeNumber').attr('disabled', 'disabled');
        countTimeTook();
    }else{
        overrideStatus = true;
        $mySwal.find('.timeTook').find('.timeNumber').removeAttr('disabled');
        $mySwal.find('.timeTook').find('.timePurposeBlock').first().trigger('click');
        countDuration();
    }
    return overrideStatus;
}

function toDelete($deleteBtn){
    var history;
    $.each(historyDataList, function(index, historyData){
        if(historyData['sn'] == $deleteBtn.val()){
            history = historyData;
        }
    });
    sConfirm(getSwalTextSpan("Are you sure to undo?", "dangerText"), function(){
        ajax(ctxPath + 'component/produce/delete.do', history, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            })
        });
    });
}

function is_need_pay($selector){
    var $toProduceForm = $swal.find('.toProduceTemplate');
    $toProduceForm.find('.valueInput').val(0);
    $toProduceForm.find('.timeNumber').val(0);
    $toProduceForm.find('#total_duration').val(0);
    var process_category_sn;
    if(typeof $selector != 'undefined'){
        $.each(components, function(index, component){
            if($selector.val() == component.sn){
                currentComponent = JSON.parse(JSON.stringify(component));
                process_category_sn = currentComponent['process_category_sn'];
                return false;
            }
        });
    }else{
        process_category_sn = $swal.find('#process_category_sn').val();
    }
    $.each(processCategories, function(index, processCategory){
        if(process_category_sn == processCategory['sn']){
            if(processCategory['relation'].indexOf('ROLE_PURCHASE') != -1){
                isPurchase = true;
                $toProduceForm.find(".nonePurchaseDiv").hide();
                $toProduceForm.find(".purchaseDiv").show();
            }else{
                isPurchase = false;
                $swal.find("#total").closest('.timePurposeBlock').trigger('click');
                $toProduceForm.find(".nonePurchaseDiv").show();
                $toProduceForm.find(".purchaseDiv").hide();
            }
            return false;
        }
    });
}

function changeComponent($selector){
    var $toProduceForm = $selector.closest('.toProduceTemplate');
    var componentSn = $selector.val();
    var process;
    $.each(components, function(index, component){
        if(componentSn == component.sn){
            currentComponent = JSON.parse(JSON.stringify(component));
            // var $processSelector = $toProduceForm.find('.process_category_sn');
            // $processSelector.find('option[value="' + component['process_category_sn'] + '"]')
            //     .prop('selected', true);
            $.each(processCategories, function(index, processCategory){
                 if(currentComponent['process_category_sn'] == processCategory.sn){
                     process = processCategory;
                 }
            });
            return false;
        }
    });
    $toProduceForm.find("#process_category_sn").val(process.sn);
    $toProduceForm.find('#process_category_name').text(process.name);
    $toProduceForm.find('#amount').val(0);
    $toProduceForm.find("#amount").trigger('change');
    $toProduceForm.find("#purchase_fee").val(0);
    $toProduceForm.find('.isOverrideTime[value="false"]').closest('.isOverrideTimeLabel').trigger('click');
    $toProduceForm.find('.timePurposeBlock').find('.component_sn').val(componentSn);
    if(componentSn != 'none'){
        $toProduceForm.find(".showTogether").show();
    }else{
        $toProduceForm.find(".showTogether").hide();
    }
    countDuration('swal');
}

function toProduce(){
    buildForm();
    function buildForm(){
        var $form = $(".toProduceTemplate").clone(true);
        $form.show();
        $form.find('.timeTook').find('.timeNumber').attr('disabled', 'disabled');
        timePurpose = 'total';
        sweetAlertAttr.confirmCallback = confirmCallback;
        sweetAlertAttr.cancelCallback = cancelCallback;
        sweetAlertAttr.openCallback = openCallback;
        sweetAlertAttr.width = '600px';
        sHTMLAlert("<span class='swalLabelText'>Build Component</span>", $form, sweetAlertAttr);

    }

    function openCallback(){
        bindTimeEvent();
        setTimeTookEvent();
    }

    function cancelCallback(){
        dataRecover();
    }
    function confirmCallback(){
        var $swalContent = $('.swal2-content');
        if(isValid()){
            ajax(ctxPath + 'component/produce/saving.do', $swalContent.find('.toProduceTemplate').serializeArray(), function(result){
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
                    errorStr += "*" + $this.attr("aria-describedby") + " not ne empty\n";
                    isValid = false;
                }
            })
            $swalContent.find("select").each(function(){
                var $this = $(this);
                if($this.val() == 'none'){
                    errorStr += "*" + $this.attr("aria-describedby") + " isn't selected\n";
                    isValid = false;
                }
            })
            if(!isValid){
                sAlert(errorStr, false);
            }
            return isValid;
        }
    }
}

function dataRecover(){
    $.each(components, function(index, component){
        if(component.sn == currentComponent.sn){
            component = JSON.parse(JSON.stringify(currentComponent));
            return false;
        }
    });
    overrideStatus = false;
    currentComponent = new Object();
}

function setMaterialHTML($span, materialName, materialAmount, materialUnit){
    $span.append($('<span>', {text:materialName, 'class':'w-50 text-left'}))
        .append($('<span>', {text:":", 'class':'w-50 text-center'}))
        .append($('<span>', {text:materialAmount + getUnitForMaterial(materialUnit), 'class':'w-50 text-right'}))
        .append($('<br>'));
}

function checkDetail($checkBtn){
    isPurchase = false;
    var $detailTemplate = $(".detailTemplate").clone(true);
    $detailTemplate.show();
    $detailTemplate.find('.notPurchase').hide();
    $detailTemplate.find('.purchase').hide();
    $.each(historyDataList, function(index, historyData){
        if(historyData.sn == $checkBtn.val()){
            for (var property in historyData) {
                if (historyData.hasOwnProperty(property)) {
                    $detailTemplate.find('.' + property).text(historyData[property]);
                }
            }
            if(historyData['materials'].length == 0){
                isPurchase = true;
            }
            $detailTemplate.find('.purchase_fee').text(historyData['purchase_fee'] + "NTD");
            $detailTemplate.find('.amount').text(historyData['amount'] + "");
            $detailTemplate.find('.identifier').text(historyData['component']['identifier']);
            $detailTemplate.find('.name').text(historyData['component']['name']);
            $.each(historyData['materials'], function(index, material){
                setMaterialHTML($detailTemplate.find('.materialName'), material['name'],
                    FloatNumber.mul(material['element_amount'], historyData['amount']), material['unit']);
            });
            $.each(historyData['usedComponents'], function(index, usedComponent){
                setMaterialHTML($detailTemplate.find('.componentName'), usedComponent['name'],
                    FloatNumber.mul(usedComponent['element_amount'], historyData['amount']), "");
            });
            $detailTemplate.find('.processName').text(historyData['processCategory']['name']);
            $detailTemplate.find('.remain').text(historyData['remain'] + "");
            if(!isPurchase){
                $detailTemplate.find('.notPurchase').show();
            }else{
                $detailTemplate.find('.purchase').show();
            }
            return false;
        }
    });
    sHTMLAlertWithNoCancel(getSwalTextSpan("Detail"), $detailTemplate);
}
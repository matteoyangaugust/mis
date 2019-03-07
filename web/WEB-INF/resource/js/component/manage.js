var currentProcess;
var currentComponentSn;
var selectedMaterials;
var selectedComponents;
$(function(){
    $(".toModify").bind('click', function(){
        toModify($(this));
    });
    $(".toDelete").bind('click', function(){
        toDelete($(this));
    });
    $(".selectMaterial").bind('click', function(){
        selectMaterial($(this));
    });
    $(".selectComponent").bind('click', function(){
        selectComponent($(this));
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
        $(".componentForm").show();
    }else{
        $('.componentForm').each(function(){
            var $componentForm = $(this);
            if($componentForm.find('.name').text().toLowerCase().indexOf(str.toLowerCase()) >= 0){
                $componentForm.show();
            }else{
                $componentForm.hide();
            }
        });
    }
}



function setSelectedElements(componentSn){
    $.each(components, function(index, component){
        if(componentSn == component.sn){
            selectedMaterials = JSON.parse(JSON.stringify(component['materials']));
            selectedComponents = JSON.parse(JSON.stringify(component['components']));
        }
    });
}

function selectComponent($btn){
    currentComponentSn = $btn.val();
    setSelectedElements(currentComponentSn);
    var $selectComponentTemplate = $("#template").find(".selectComponentTemplate").clone(true);
    //製造已選擇列
    $.each(selectedComponents, function(index, selectedComponent){
        buildSelected(selectedComponent, $selectComponentTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = openCallback;
    sweetAlertAttr.confirmCallback = confirmCallback;
    sHTMLAlert(getSwalTextSpan("Select Component"), $selectComponentTemplate, sweetAlertAttr);

    function getComponent($component){
        var usedComponentSn = $component.next('.sn').val();
        if(usedComponentSn == currentComponentSn){
            return false;
        }
        var isSelected = false;
        var componentObj;
        $.each(components, function(index, component){
            if(usedComponentSn == component['sn']){
                componentObj = component;
                componentObj.componentQuantity = 0;
            }
        });
        $.each(selectedComponents, function(index, selectedComponent){
            if(componentObj['sn'] == selectedComponent['sn']){
                isSelected = true;
                return false;
            }
        });
        if(!isSelected){
            var $swalContent = $('.mySwal');
            var $selectedBlock = $swalContent.find("#selectedBlock");
            buildSelected(componentObj, $selectedBlock);
            selectedComponents.push(componentObj);
        }
    }

    function openCallback(){
        $swal.find('.materialBtn').bind('click', function(){
            getComponent($(this));
        });
        swalEvent($swal);
    }

    function confirmCallback(){
        var formData = new Object();
        formData['component_sn'] = currentComponentSn;
        formData['selectedComponentsJSON'] = JSON.stringify(selectedComponents);
        ajax(ctxPath + 'component/manage/changeUsedComponents.do', formData, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            });
        });
    }

    function buildSelected(selectedMaterial, $selectedBlock){
        var $selectedComponent = $("#template").find('.selectedComponent').clone(true);
        $selectedComponent.find('.component_sn').val(selectedMaterial['sn']);
        $selectedComponent.find('.componentName').text(selectedMaterial['name']);
        $selectedComponent.find('.componentQuantity').val(selectedMaterial['element_amount']);
        swalEvent($selectedComponent);
        $selectedBlock.append($selectedComponent)
    }

    function swalEvent($obj){
        $obj.find('.componentQuantity').bind('keyup', function(){
            recordQuantity($(this));
        });
        $obj.find('.componentQuantity').bind('change', function(){
            recordQuantity($(this));
        });
        $obj.find('.componentQuantity').bind('mousedown', function(){
            recordQuantity($(this));
        });
        $obj.find('.deleteComponent').bind('click', function(){
            deleteComponent($(this));
        });
    }

    function recordQuantity($input){
        var quantity = $input.val();
        var component_sn = $input.siblings('.component_sn').val();
        $.each(selectedComponents, function(index, selectedComponent){
            if(component_sn == selectedComponent['sn']){
                selectedComponent['element_amount'] = quantity;
            }
        });
    }

    function deleteComponent($deleteBtn) {
        var componentSn = $deleteBtn.siblings('.component_sn').val();
        var $selectedComponent = $deleteBtn.closest('.selectedComponent');
        $selectedComponent.remove();
        var indexToDelete;
        $.each(selectedComponents, function(index, selectedComponent){
            if(componentSn == selectedComponent['sn']){
                indexToDelete = index;
                return false;
            }
        });
        selectedComponents.splice(indexToDelete, 1)
    }
}

function selectMaterial($btn){
    currentComponentSn = $btn.val();
    setSelectedElements(currentComponentSn);
    var $selectMaterialTemplate = $("#template").find(".selectMaterialTemplate").clone(true);
    //製造已選擇列
    $.each(selectedMaterials, function(index, selectedMaterial){
        buildSelected(selectedMaterial, $selectMaterialTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = openCallback;
    sweetAlertAttr.confirmCallback = confirmCallback;
    sHTMLAlert(getSwalTextSpan("Select Material"), $selectMaterialTemplate, sweetAlertAttr);

    function getMaterial($material){
        var isSelected = false;
        var materialObj;
        $.each(materials, function(index, material){
            if($material.next('.sn').val() == material['sn']){
                materialObj = material;
                materialObj.quantity = 0;
            }
        });
        $.each(selectedMaterials, function(index, selectedMaterial){
            if(materialObj['sn'] == selectedMaterial['sn']){
                isSelected = true;
                return false;
            }
        });
        if(!isSelected){
            var $swalContent = $('.mySwal');
            var $selectedBlock = $swalContent.find("#selectedBlock");
            buildSelected(materialObj, $selectedBlock);
            selectedMaterials.push(materialObj);
        }
    }

    function openCallback(){
        $swal.find('.materialBtn').bind('click', function(){
            getMaterial($(this));
        });
        swalEvent($swal);
    }

    function confirmCallback(){
        var formData = new Object();
        formData['component_sn'] = currentComponentSn;
        formData['selectedMaterialsJSON'] = JSON.stringify(selectedMaterials);
        ajax(ctxPath + 'component/manage/changeMaterials.do', formData, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            });
        });
    }

    function buildSelected(selectedMaterial, $selectedBlock){
        var $selectedMaterial = $("#template").find('.selectedMaterial').clone(true);
        $selectedMaterial.find('.material_sn').val(selectedMaterial['sn']);
        $selectedMaterial.find('.materialName').text(selectedMaterial['name']);
        $selectedMaterial.find('.materialQuantity').val(selectedMaterial['element_amount']);
        swalEvent($selectedMaterial);
        $selectedBlock.append($selectedMaterial)
    }

    function swalEvent($obj){
        $obj.find('.materialQuantity').bind('keyup', function(){
            recordQuantity($(this));
        });
        $obj.find('.materialQuantity').bind('change', function(){
            recordQuantity($(this));
        });
        $obj.find('.materialQuantity').bind('mousedown', function(){
            recordQuantity($(this));
        });
        $obj.find('.deleteMaterial').bind('click', function(){
            deleteMaterial($(this));
        });
    }

    function recordQuantity($input){
        var quantity = $input.val();
        var material_sn = $input.siblings('.material_sn').val();
        $.each(selectedMaterials, function(index, selectedMaterial){
            if(material_sn == selectedMaterial['sn']){
                selectedMaterial['element_amount'] = quantity;
            }
        });
    }

    function deleteMaterial($deleteBtn) {
        var materialSn = $deleteBtn.siblings('.material_sn').val();
        var $selectedMaterial = $deleteBtn.closest('.selectedMaterial');
        $selectedMaterial.remove();
        var indexToDelete;
        $.each(selectedMaterials, function(index, selectedMaterial){
            if(materialSn == selectedMaterial['sn']){
                indexToDelete = index;
                return false;
            }
        });
        selectedMaterials.splice(indexToDelete, 1)
    }
}
function toDelete($deleteBtn) {
    var sn = $deleteBtn.val();
    sConfirm("<span class='dangerText'>Are you sure to delete?</span>", function () {
        ajax(ctxPath + 'component/manage/delete.do', {sn: sn}, function (result) {
            sAlert(result['msg'], result['success'], function () {
                if (result['success']) {
                    reloadView();
                }
            });
        });
    });
}
function toModify($modifyBtn){
    timePurpose = "total";
    var sn = $modifyBtn.val();
    var chosenComponent;
    var $toModifyTemplate = $(".toModifyTemplate").clone(true);
    $toModifyTemplate.find('.purchaseToggle').hide();
    fillValueToTemplate();
    $toModifyTemplate.show();
    sweetAlertAttr.confirmCallback = confirmCallback;
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = openCallback;
    sHTMLAlert(getSwalTextSpan('Modify Component'), $toModifyTemplate, sweetAlertAttr);
    function cancelCallback(){
        currentProcess = undefined;
    }
    function openCallback(){
        $swal.find("#material_sn").bind('change', function(){
            changeMaterial();
        });
        $swal.find('.timeNumber').bind('change', function(){
            countProcessDuration();
        });
        $swal.find('.timeNumber').bind('keyup', function(){
            countProcessDuration();
        });
        $swal.find('.timeNumber').bind('mousedown', function(){
            countProcessDuration();
        });
        $swal.find('#process_category_sn').bind('change', function(){
            purchaseToggle();
        });
        $swal.find('#material_sn').trigger('change');
    }

    function purchaseToggle(){
        $.each(processCategories, function(index, category){
            if(category.sn == $swal.find("#process_category_sn").val()){
                currentProcess = category;
            }
        });
        if(currentProcess['relation'].indexOf('ROLE_PURCHASE') != -1){
            $swal.find('.purchaseToggle').hide();
            $swal.find('.purchaseFeeBlock').show();
            $swal.find('#material_sn').val(0);
            $swal.find('#material_amount').val(0);
            $swal.find('#process_yield').val(0);
            $swal.find('.timeNumber').val(0);
            $swal.find('.process_duration').val(0);
        }else{
            $swal.find('.purchaseToggle').show();
            $swal.find('.purchaseFeeBlock').hide();
            $swal.find('#purchase_fee').val(0);
        }
    }

    function countProcessDuration(){
        var dayToSecond = parseInt($swal.find('#time_took_of_day').val()) * 24 * 60 * 60;
        var hourToSecond = parseInt($swal.find('#time_took_of_hour').val()) * 60 * 60;
        var minuteToSecond = parseInt($swal.find('#time_took_of_minute').val()) * 60;
        var second = parseInt($swal.find('#time_took_of_second').val());
        var process_duration = dayToSecond + hourToSecond + minuteToSecond + second;
        $swal.find("#process_duration").val(process_duration);
    }

    function confirmCallback(){
        if(isValid()){
            sConfirm("<span class='dangerText'>Are you sure to modify?</span>", function(){
                ajax(ctxPath + 'component/manage/modifying.do', $swal.find('.toModifyTemplate').serializeArray(), function(result){
                    sAlert(result['msg'], result['success'], function(){
                        if(result['success']){
                            reloadView();
                        }
                    });
                });
            });
        }

        function isValid(){
            var isValid = true;
            var errorStr = '';
            $swal.find(".notEmpty").each(function(){
                var $this = $(this);
                if(isNothingStrValue($this.val())){
                    errorStr += "*" + $this.attr("aria-describedby") + " not be empty\n";
                    isValid = false;
                }
            });
            $swal.find('.numInput').each(function(){
                var $this = $(this);
                if(!$.isNumeric($this.val())){
                    errorStr += "*" + $this.attr("aria-describedby") + " must be a number\n";
                    isValid = false;
                }
            });

            if($swal.find("#process_yield").val() == 0 && currentProcess['relation'].indexOf('ROLE_PURCHASE') == -1){
                errorStr += "*" + $("#process_yield").attr("aria-describedby") + 'not be 0\n';
                isValid = false;
            }
            if(!isValid){
                sAlert(errorStr, false);
            }
            return isValid;
        }
    }

    function fillValueToTemplate(){
        var chosenComponent;
        $.each(components, function(index, component){
            chosenComponent = component;
            if(sn == component.sn){
                $.each(processCategories, function(index, processCategory){
                    if(chosenComponent['process_category_sn'] == processCategory.sn){
                        currentProcess = processCategory;
                        return false;
                    }
                });
                return false;
            }
        });
        for (var property in chosenComponent) {
            if (chosenComponent.hasOwnProperty(property)) {
                $toModifyTemplate.find('#' + property).val(chosenComponent[property]);
                if(property == 'material_sn' || property == 'process_category_sn'){
                    $toModifyTemplate.find('#' + property).find('option[value="' + chosenComponent[property] + '"]')
                        .attr('selected', 'selected');
                }
            }
        }
        if(currentProcess['relation'].indexOf('ROLE_PURCHASE') != -1){
            $toModifyTemplate.find('.purchaseFeeBlock').show();
        }else{
            $toModifyTemplate.find('.purchaseToggle').show();
            $toModifyTemplate.find('.purchaseFeeBlock').hide();
        }
        $toModifyTemplate.find('#time_took_of_day').val(chosenComponent['totalTimeBean']['day']);
        $toModifyTemplate.find('#time_took_of_hour').val(chosenComponent['totalTimeBean']['hour']);
        $toModifyTemplate.find('#time_took_of_minute').val(chosenComponent['totalTimeBean']['minute']);
        $toModifyTemplate.find('#time_took_of_second').val(chosenComponent['totalTimeBean']['second']);
        $toModifyTemplate.find(".component_sn").val(chosenComponent['sn']);
    }
}
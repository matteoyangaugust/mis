var selectedMaterials = new Array();
var selectedComponents = new Array();
$(function(){
    $("#submit").bind('click', function(){
        createComponent();
    });
    $("#reload").bind('click', function(){
        reloadView();
    });
    $("#material_sn").bind('change', function(){
        changeMaterial();
    });
    $('.timeNumber').bind('change', function(){
        countProcessDuration();
    });
    $('.timeNumber').bind('keyup', function(){
        countProcessDuration();
    });
    $('.timeNumber').bind('mousedown', function(){
        countProcessDuration();
    });
    $("#selectMaterial").bind('click', function(){
        selectMaterial();
    });
    $("#selectComponent").bind('click', function(){
        selectComponent();
    });
});

function selectComponent(){
    var originalSelected = JSON.parse(JSON.stringify(selectedComponents));
    var $selectComponentTemplate = $("#template").find(".selectComponentTemplate").clone(true);
    //製造已選擇列
    $.each(selectedComponents, function(index, selectedComponent){
        buildSelected(selectedComponent, $selectComponentTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = openCallback;
    sweetAlertAttr.cancelCallBack = cancelCallback;
    sweetAlertAttr.confirmCallback = confirmCallback;
    sHTMLAlert(getSwalTextSpan("Select Component"), $selectComponentTemplate, sweetAlertAttr);

    function buildSelected(selectedComponent, $selectedBlock){
        var $selectedComponent = $("#template").find('.selectedComponent').clone(true);
        $selectedComponent.find('.component_sn').val(selectedComponent['sn']);
        $selectedComponent.find('.componentName').text(selectedComponent['name']);
        $selectedComponent.find('.componentQuantity').val(selectedComponent['componentQuantity']);
        swalEvent($selectedComponent);
        $selectedBlock.append($selectedComponent)
    }

    function getComponent($component){
        var isSelected = false;
        var componentObj;
        $.each(components, function(index, component){
            if($component.next('.sn').val() == component['sn']){
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

    function cancelCallback(){
        selectedComponents = JSON.parse(JSON.stringify(originalSelected));
        confirmCallback();
    }

    function confirmCallback(){
        //製造選擇列表
        var $components = $("#components");
        $components.empty();
        $.each(selectedComponents, function(index, selectedComponent){
            var $row = $('<div>', {'class':'row'});
            var $label = $('<label>', {'class':'labelText col-sm-4', text:selectedComponent.name + "："});
            var $span = $('<span>', {'class':'col-sm-4', text:selectedComponent.componentQuantity + '件'});
            $row.append($label).append($span);
            $components.append($row);
        });
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

    function recordQuantity($input){
        var quantity = $input.val();
        var component_sn = $input.siblings('.component_sn').val();
        $.each(selectedComponents, function(index, selectedComponent){
            if(component_sn == selectedComponent['sn']){
                selectedComponent.componentQuantity = quantity;
            }
        });
    }
}

function selectMaterial(){
    var originalSelected = JSON.parse(JSON.stringify(selectedMaterials));
    var $selectMaterialTemplate = $("#template").find(".selectMaterialTemplate").clone(true);
    //製造已選擇列
    $.each(selectedMaterials, function(index, selectedMaterial){
        buildSelected(selectedMaterial, $selectMaterialTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.openCallback = openCallback;
    sweetAlertAttr.cancelCallBack = cancelCallback;
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

    function cancelCallback(){
        selectedMaterials = JSON.parse(JSON.stringify(originalSelected));
        confirmCallback();
    }

    function confirmCallback(){
        //製造選擇列表
        var $materials = $("#materials");
        $materials.empty();
        $.each(selectedMaterials, function(index, selectedMaterial){
            var $row = $('<div>', {'class':'row'});
            var $label = $('<label>', {'class':'labelText col-sm-4', text:selectedMaterial.name + "："});
            var $span = $('<span>', {'class':'col-sm-4', text:selectedMaterial.quantity + getUnitForMaterial(selectedMaterial.unit)});
            $row.append($label).append($span);
            $materials.append($row);
        });
    }

    function buildSelected(selectedMaterial, $selectedBlock){
        var $selectedMaterial = $("#template").find('.selectedMaterial').clone(true);
        $selectedMaterial.find('.material_sn').val(selectedMaterial['sn']);
        $selectedMaterial.find('.materialName').text(selectedMaterial['name']);
        $selectedMaterial.find('.materialQuantity').val(selectedMaterial['quantity']);
        swalEvent($selectedMaterial);
        $selectedBlock.append($selectedMaterial);
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

    function recordQuantity($input){
        var quantity = $input.val();
        var material_sn = $input.siblings('.material_sn').val();
        $.each(selectedMaterials, function(index, selectedMaterial){
            if(material_sn == selectedMaterial['sn']){
                selectedMaterial.quantity = quantity;
            }
        });
    }
}



function changeMaterial(){
    var material_sn = $("#material_sn").val();
    if(material_sn != 'none'){
        $.each(materials, function(index, material){
            if(material_sn == material.sn){
                $("#unit").text(getUnitForMaterial(material.unit));
                return false;
            }
        });
    }else{
        $("#unit").empty();
    }
}

function countProcessDuration(){
    var dayToSecond = parseInt($('#time_took_of_day').val()) * 24 * 60 * 60;
    var hourToSecond = parseInt($('#time_took_of_hour').val()) * 60 * 60;
    var minuteToSecond = parseInt($('#time_took_of_minute').val()) * 60;
    var second = parseInt($('#time_took_of_second').val());
    var process_duration = dayToSecond + hourToSecond + minuteToSecond + second;
    $("#process_duration").val(process_duration);
}

function createComponent(){
    if(isValid()){
        var formData = transToObj();
        ajax(ctxPath + 'component/create/creating.do', formData, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    reloadView();
                }
            });
        });
    }

    function transToObj(){
        var formData = $("#componentForm").serializeArray();
        var formObj = new Object();
        $.each(formData, function(index, eachData){
            formObj[eachData['name']] = eachData['value'];
        });
        formObj['selectedMaterialsJSON'] = JSON.stringify(selectedMaterials);
        formObj['selectedComponentsJSON'] = JSON.stringify(selectedComponents);
        return formObj;
    }

    function isValid(){
        var errorStr = "";
        var isValid = true;
        $(".notEmpty").each(function(){
            var $this = $(this);
            if(isNothingStrValue($this.val())){
                errorStr += "* " + $this.attr("aria-describedby") + " not allowed Empty.\n";
                isValid = false;
            }
        });
        $(".selector").each(function(){
            var $selector = $(this);
            if($selector.val() == 'none'){
                errorStr += "* " + $selector.attr("aria-describedby") + " not allowed Empty.\n";
                isValid = false;
            }
        });
        $('.timeNumber').each(function(){
            var $this = $(this);
            if(parseInt($this.val()) > parseInt($this.attr('max'))){
                errorStr += "* " + $("#time_took").attr("aria-describedby") + '-' + $this.attr("aria-describedby") + "do not greater than" + $this.attr('max') + '.\n';
                isValid = false;
            }
        });
        if($("#process_yield").val() == 0){
            errorStr += "* " + $("#process_yield").attr("aria-describedby") + ' isn\'t allowed 0.\n';
            isValid = false;
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}
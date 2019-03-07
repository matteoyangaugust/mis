var selectedComponents = new Array();

$(function(){
    $("#reload").bind('click', function(){
        reloadView();
    });
    $("#selectComponent").bind('click', function(){
        selectComponent();
    });
    $("#submit").bind('click', function(){
        creatingProduct();
    });
});


function creatingProduct(){
    if(isValid()){
        var product = new Object();
        var identifier = $('#identifier').val();
        var name = $("#name").val();
        var remain = $("#remain").val();
        ajax(ctxPath + 'product/create/creating.do',
            {
                identifier:identifier,
                remain:remain,
                name:name ,
                selectedComponentsJason : JSON.stringify(selectedComponents)
            }, function(result){
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
        if(selectedComponents.length == 0){
            errorStr += "*" + "Component isn't selected\n";
            isValid = false;
        }
        if(!isValid){
            sAlert(errorStr, false);
        }
        return isValid;
    }
}

function recordQuantity(input){
    var $input = $(input);
    var quantity = $input.val();
    var component_sn = $input.siblings('.component_sn').val();
    $.each(selectedComponents, function(index, selectedComponent){
        if(component_sn == selectedComponent['sn']){
            selectedComponent.quantity = quantity;
        }
    });
}

function selectComponent(){
    var originalSelected = JSON.parse(JSON.stringify(selectedComponents));;
    var $selectComponentTemplate = $(".selectComponentTemplate").clone(true);
    $selectComponentTemplate.show();
    //製造已選擇列
    $.each(selectedComponents, function(index, selectedComponent){
        buildSelected(selectedComponent, $selectComponentTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.width = '600px';
    sweetAlertAttr.cancelCallback = cancelCallback;
    sweetAlertAttr.confirmCallback = confirmCallback;
    sHTMLAlert(getSwalTextSpan("Select Component"), $selectComponentTemplate, sweetAlertAttr);

    function cancelCallback(){
        selectedComponents = originalSelected;
    }

    function confirmCallback(){
        //製造選擇列表
        var $components = $("#components");
        $components.empty();
        $.each(selectedComponents, function(index, selectedComponent){
            var $row = $('<div>', {'class':'row'});
            var $label = $('<label>', {'class':'labelText col-sm-4', text:selectedComponent.name + "："});
            var $span = $('<span>', {'class':'col-sm-4', text:selectedComponent.quantity});
            $row.append($label).append($span);
            $components.append($row);
        });
    }
}

function getComponent(componentBtn){
    var $componentBtn = $(componentBtn);
    var componentObj;
    var isSelected = false;
    $.each(components, function(index, component){
        if($componentBtn.next('.sn').val() == component['sn']){
            componentObj = component;
            componentObj.quantity = 0;
        }
    });
    $.each(selectedComponents, function(index, selectedComponent){
        if(componentObj['sn'] == selectedComponent['sn']){
            isSelected = true;
            return false;
        }
    });
    if(!isSelected){
        var $swalContent = $('.swal2-content');
        var $selectedBlock = $swalContent.find("#selectedBlock");
        buildSelected(componentObj, $selectedBlock);
        selectedComponents.push(componentObj);
    }
}

function buildSelected(componentObj, $selectedBlock){
    var $selectedComponent = $("#template").find('.selectedComponent').clone(true);
    $selectedComponent.find('.component_sn').val(componentObj['sn']);
    $selectedComponent.find('.componentName').text(componentObj['name']);
    $selectedComponent.find('.componentQuantity').val(componentObj['quantity']);
    $selectedComponent.show();
    $selectedBlock.append($selectedComponent)
}

function deleteComponent(deleteBtn){
    var $deleteBtn = $(deleteBtn);
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
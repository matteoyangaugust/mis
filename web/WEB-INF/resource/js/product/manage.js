var selectedComponents = new Array();
$(function(){
    $(".toModify").bind('click', function(){
        toModify($(this));
    });

    $(".toDelete").bind('click', function(){
        toDelete($(this));
    });

    $(".manageComponents").bind('click', function(){
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
        $(".productForm").show();
    }else{
        $('.productForm').each(function(){
            var $productForm = $(this);
            if($productForm.find('.name').val().toLowerCase().indexOf(str.toLowerCase()) >= 0){
                $productForm.show();
            }else{
                $productForm.hide();
            }
        });
    }
}

function selectComponent($manageBtn){
    var $form = $manageBtn.closest('.productForm');
    var productSn = $manageBtn.val();
    $.each(products, function(index, product){
        if(product.sn == productSn){
            selectedComponents = product['components'];
        }
    });
    var originalSelected = JSON.parse(JSON.stringify(selectedComponents));;
    var $selectComponentTemplate = $(".selectComponentTemplate").clone(true);
    $selectComponentTemplate.show();
    //製造已選擇列
    $.each(selectedComponents, function(index, component){
        buildSelected(component, $selectComponentTemplate.find('#selectedBlock'));
    });
    sweetAlertAttr.cancelCallback = cancelCallback;
    sweetAlertAttr.confirmCallback = confirmCallback;
    sweetAlertAttr.width = '600px';
    sHTMLAlert(getSwalTextSpan("Select Component"), $selectComponentTemplate, sweetAlertAttr);

    function cancelCallback(){
        $.each(products, function(index, product){
            if(product.sn == productSn){
                product['components'] = originalSelected;
            }
        });
    }

    function confirmCallback(){
        ajax(ctxPath + 'product/manage/updating.do', {
            selectedComponentsJson : JSON.stringify(selectedComponents),
            product_sn : productSn
        }, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    var $componentList = $form.find('.componentList');
                    $componentList.empty();
                    $.each(selectedComponents, function(index, selectedComponent){
                        $componentList.append($('<div>', {
                            'class':'labelText text-center mb-2',
                            text:selectedComponent['name'] + '(' + selectedComponent['identifier'] + ')  X  '
                            + selectedComponent['quantity']
                        }));
                    });
                }
            });
        });
    }
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

function buildSelected(componentObj, $selectedBlock){
    var $selectedComponent = $("#template").find('.selectedComponent').clone(true);
    $selectedComponent.find('.component_sn').val(componentObj['sn']);
    $selectedComponent.find('.componentName').text(componentObj['name']);
    $selectedComponent.find('.componentQuantity').val(componentObj['quantity']);
    $selectedComponent.show();
    $selectedBlock.append($selectedComponent)
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

function toDelete($deleteBtn){
    sConfirm(getSwalTextSpan("Are you sure to delete?", "dangerText"), function(){
        var productToDelete;
        $.each(products, function(index, product){
            if($deleteBtn.val() == product.sn){
                productToDelete = product;
            }
        });
        ajax(ctxPath + 'product/manage/delete.do', productToDelete, function(result){
            sAlert(result['msg'], result['success'], function(){
                if(result['success']){
                    $deleteBtn.closest('.productForm').remove();
                }
            });
        });
    });
}

function toModify($modifyBtn) {
    var $form = $modifyBtn.closest('.productForm');
    if(isValid()){
        ajax(ctxPath + 'product/manage/modifying.do', $form.serializeArray(), function(result){
            sAlert(result['msg'], result['success']);
        });
    }

    function isValid(){
        var errorStr = "";
        var isValid = true;
        $form.find(".notEmpty").each(function(){
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


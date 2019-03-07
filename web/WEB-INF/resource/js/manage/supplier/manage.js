$(function(){
    $(".delete").bind('click', function(){
        deleteSupplier($(this));
    });
    $(".save").bind('click', function(){
        save($(this));
    });
});

function save($saveBtn){
    ajax(ctxPath + 'manage/supplier/save.do', $saveBtn.closest('.supplierForm').serializeArray(), function(result){
        sAlert(result['msg'], result['success']);
    });
}

function deleteSupplier($deleteBtn){
    ajax(ctxPath + 'manage/supplier/delete.do', {
        sn : $deleteBtn.val()
    }, function(result){
        sAlert(result['msg'], result['success'], function(){
            if(result['success']){
                $deleteBtn.closest('.supplierForm').remove();
            }
        });
    });
}
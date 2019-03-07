/**
 * sweetAlert - alert
 * str 顯示訊息
 * isSuccess 成功或失敗
 */
function sAlert(str, isSuccess, callback){
    var typeName = '';
    if(isSuccess){
        typeName = 'success';
    }else{
        typeName = 'error';
    }
    swal({'text':str, customClass:'swalLabelText', allowOutsideClick: false, type:typeName}).then(function(){
        if(typeof callback != 'undefined'){
            callback();
        }
    });
}

/**
 *
 * @param str 顯示字串
 */
function sNumberAlert(str, callback){
    swal({
        title: str,
        input: 'number',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        allowOutsideClick: false
    }).then(function(number) {
        if(typeof callback != 'undefined'){
            callback(number);
        }
    })
}

/**
 *
 * @param str 顯示字串
 */
function sHTMLAlert(str, $html, sweetAlertAttr){
    swal({
        title: str,
        html: $html,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        allowOutsideClick: false,
        customClass: 'mySwal',
        onOpen : function(){
            $swal = $(".mySwal");
            $swal.find('.numInput').unbind('change').bind('change', function(){
                numberTagProcess($(this));
            });
            $swal.find('.numInput').unbind('keyup').bind('keyup', function(){
                numberTagProcess($(this));
            });
            $swal.find('.numInput').unbind('mousedown').bind('mousedown', function(){
                numberTagProcess($(this));
            });
            $swal.find(".floatInput").bind('change', function(){
                floatTagProcess($(this));
            });
            $swal.find(".floatInput").bind('keyup', function(){
                floatTagProcess($(this));
            });
            $swal.find(".floatInput").bind('mousedown', function(){
                floatTagProcess($(this));
            });
            if(typeof sweetAlertAttr.openCallback != 'undefined'){
                sweetAlertAttr.openCallback();
            }
        },
        width : sweetAlertAttr.width
    }).then(function(isConfirm) {
        if(isConfirm == true){
            if(typeof sweetAlertAttr.confirmCallback != 'undefined'){
                sweetAlertAttr.confirmCallback();
            }
        }
        sweetAlertAttr = new SweetAlertAttribute();
    }, function(dismiss){
        if (dismiss === 'cancel') {
            if(typeof sweetAlertAttr.cancelCallBack != 'undefined'){
                sweetAlertAttr.cancelCallBack();
            }
        } else {
            throw dismiss;
        }
        sweetAlertAttr = new SweetAlertAttribute();
    });
}

/**
 *
 * @param str 顯示字串
 */
function sHTMLAlertWithNoCancel(str, html, callback){

    swal({
        title: str,
        html: html,
        width : 600,
        confirmButtonText: 'Confirm',
        allowOutsideClick: false
    }).then(function() {
        if(typeof callback != 'undefined'){
            callback();
        }
    })
}

/**
 *
 * @param str 顯示字串
 */
function sTextAlert(str, html, callback){

    swal({
        title: str,
        text: html,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        allowOutsideClick: false
    }).then(function() {
        if(typeof callback != 'undefined'){
            callback();
        }
    })
}

/**
 *
 * @param str
 * @param callback
 */
function sConfirm(str, callback){
    swal({
        title: str,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        allowOutsideClick: false
    }).then(function() {
        callback();
    })
}

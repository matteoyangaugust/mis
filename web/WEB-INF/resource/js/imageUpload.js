function createImageUploader($appender){
    var $holder = $('<div>', {'class':'image-holder'})
            .append($('<div>', {'class':'addImage'})
                .append($('<i>', {'class':'addImagePlus fa fa-plus'})));
    $appender.append($('<input>',{type:'file', name:'image', 'class':'photo custom-file-input', style:'display:none;'}));
    $appender.append($('<input>',{type:'hidden', 'class':'photoSn custom-file-input', value:0}));
    $appender.append($holder);
    imageHover($holder);
    imageEvent($holder, $appender);
    return $holder;
}

function imageEvent($holder, $appender){
    $holder.bind('click', function(){
        $appender.find(".photo").trigger('click');
    });
    $appender.find(".photo").bind('change', function(){
        previewImageProcessing($(this), $appender);
    });
}

function imageHover($holder){
    $holder.hover(function(){
        $holder.css('border', '2px dotted #000');
        $holder.find(".addImage").css('border', '3px dotted #000');
        $holder.find(".addImagePlus").css('color', '#000');
    }, function(){
        var $holder = $(this)
        $holder.css('border', '2px dotted #595959');
        $holder.find(".addImage").css('border', '3px dotted #636c72');
        $holder.find(".addImagePlus").css('color', '#636c72');
    });
}

function previewImageProcessing($imageInput, $appender){
    $imageInput.attr('name', 'image');
    $imageInput.siblings('.photoSn').val(0);
    var $image_holder = $appender.find('.image-holder');
    var extension = getFileExtension(getFileName($imageInput)).toLowerCase();
    if(extension != 'png' && extension != 'jpg'
        && extension != 'jpeg' && extension != 'bmp'
        && extension != 'pcx'
        && extension != 'gif'){
        swal({'title':'格式錯誤'});
        return;
    }
    if (typeof (FileReader) != "undefined") {
        $image_holder.find('.tempImage').remove();
        var reader = new FileReader();
        reader.onload = function (e) {
            $image_holder.append($("<img />", {
                "src": e.target.result,
                "class": "thumb-image tempImage"
            }));
        }
        if(typeof $imageInput[0].files[0] != 'undefined'){
            $image_holder.find(".addImage").hide();
        }else{
            $image_holder.find(".addImage").show();
        }
        $image_holder.show();
        reader.readAsDataURL($imageInput[0].files[0]);
    } else {
        swal({title:"This browser does not support FileReader."});
    }
}
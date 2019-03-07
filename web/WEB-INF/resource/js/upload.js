var deletedImage = new Array();
$(function(){
    $("#addHolder").bind('click', function(){
        addWrapper();
    });
    $("#sendImages").bind('click', function(){
        sendImages();
    });
    $("#cancelImage").bind('click', function(){
        $("#imageUploadModal").modal('hide');
        $(".removeWrapper").remove();
        $(".wrapper").remove();
        $("input[name='deletedImageSn']").val('');
    });
    if(afterUploading){
        swal({title:result['msg']});
    }
    initData();
});

function sendImages(){
    $(".photo").each(function(){
        var $this = $(this);
        var photoSn = $this.siblings('.photoSn').val();
        if(photoSn == 0 && isNothingStrValue($this.val())){
            $this.closest('.wrapper').prev('.removeWrapper').remove();
            $this.closest('.wrapper').remove();
        }
    });
    $("input[name='deletedImageSn']").val(deletedImage.toString());
    $("#imageForm").submit();
}

function initData(){
    if(courses.length == 0){
        swal({title:'您目前沒有課程可供上傳'});
    }else{
        var $displayTable = $("#courseList");
        var $wrapper = $("#templateTable").find('.courseWrapper').clone();
        courses.forEach(function(course, index){
            $wrapper.find('.index').text(index + 1);
            $wrapper.find('.courseName').text(course['name']);
            $wrapper.find('.date').text(dateFormat(course['course_begin_date']));
            $wrapper.find('.teacherName').text(course['teacherName']);
            $wrapper.find(".upload").bind('click', function(){
                showUploadModal(course)
            });
            $displayTable.append($wrapper);
        });
    }
}

function showUploadModal(course){
    $('input[name="course_sn"]').val(course['sn']);
    ajaxasy(ctxPath + 'manage/course/findPhotoOfCourse.do', {
        course_sn : course['sn']
    }, false, function(images){
        $(".removeWrapper").remove();
        $(".wrapper").remove();
        initStoredImages(images, course['sn']);
        $("#imageUploadModal").modal('show');
    });
}

function addWrapper(){
    var $removeWrapper = $("<button>", {type:'button', 'class':'btn btn-danger mb-0 w-25 removeWrapper'}).append($('<i>',{'class':'fa fa-trash-o fa-3x'}));
    $("#wrapperParent").append($removeWrapper);
    var $wrapper = $('<div>', {'class':'wrapper mt-0 mb-4 col-sm-8', style:'opacity:0'});
    $wrapper.appendTo($("#wrapperParent")).fadeIn('slow');
    createImageUploader($wrapper);
    $wrapper.animate({
        opacity: 1,
    }, 1000);
    $removeWrapper.bind('click', function(){
        if($wrapper.find('.photoSn').val() != 0){
            deletedImage.push($wrapper.find('.photoSn').val());
        }
        $wrapper.remove();
        $removeWrapper.remove();
    });
    return $wrapper;
}

function initStoredImages(images, courseSn){
    images.forEach(function(image, index){
        var $wrapper = addWrapper();
        var $image_holder = $wrapper.find(".image-holder");
        var $fileInput = $wrapper.find('input[name="image"]');
        $fileInput.bind('change', function(){
            deletedImage.push(image['sn']);
        });
        $fileInput.attr('name', 'notUpload');
        $wrapper.find('.photoSn').val(image['sn']);
        $image_holder.find('.tempImage').remove();
        $image_holder.append($("<img />", {
            "src": ctxPath + 'images/course/' + courseSn + "/" + image['imageName'],
            "class": "thumb-image tempImage"
        }));
        $image_holder.find(".addImage").hide();
        $image_holder.show();
    });
}
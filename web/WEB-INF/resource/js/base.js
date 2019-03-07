$(function(){
    initialFloatInput();
    $('.subLink').bind('click', function(){
        submitViewBySubMenuLink($(this));
    }) ;
    $('.linkBtn').bind('click', function(){
        submitViewByMainMenuLink($(this));
    });
    $("#reload").bind('click', function(){
        reloadView();
    });
    $(".numInput").bind('keyup', function(){
        numberTagProcess($(this));
    });
    $(".numInput").bind('change', function(){
        numberTagProcess($(this));
    });
    $(".numInput").bind('mousedown', function(){
        numberTagProcess($(this));
    });
    $(".floatInput").bind('keyup', function(){
        floatTagProcess($(this));
    });
    $(".floatInput").bind('change', function(){
        floatTagProcess($(this));
    });
    $(".floatInput").bind('mousedown', function(){
        floatTagProcess($(this));
    });
});

function initialFloatInput(){
    $('.floatInput').each(function(){
        var $this = $(this);
        $this.val(Number($this.val()));
    });
}

/**
 * 將浮點數去掉小數點後不需要的0;
 */
function initialDoubleNumber($doubleInput){
    if($doubleInput.length != 0){
        $doubleInput.each(function(){
            var $this = $(this);
            if(parseInt($this.val()) == $this.val()){
                $this.val(parseInt($this.val()));
            }
        });
    }
}

/**
 * 將浮點數去掉小數點後不需要的0;
 */
function initialDoubleNumberText($doubleInput){
    if($doubleInput.length != 0){
        $doubleInput.each(function(){
            var $this = $(this);
            if(parseInt($this.text()) == $this.text()){
                $this.text(parseInt($this.text()));
            }
        });
    }
}

function swalNumInputEvent(){
    $('.mySwal').find(".numInput").bind('keyup', function(){
        numberTagProcess($(this));
    });
    $('.mySwal').find(".numInput").bind('change', function(){
        numberTagProcess($(this));
    });
}

function swalNumInputCheck(numInput){
    numberTagProcess($(numInput));
}

function reloadView(){
    var $redirectForm = $("#redirectForm");
    var url = menuRecord['subMenu']['url'].replace('/', '');
    $redirectForm.find('#mainMenuSn').val(menuRecord['mainMenu']['sn']);
    $redirectForm.find('#subMenuSn').val(menuRecord['subMenu']['sn']);
    $redirectForm.attr('action', ctxPath + url);
    $redirectForm.submit();
}

function submitViewBySubMenuLink($subLinkBtn){
    var $redirectForm = $("#redirectForm");
    var url = $subLinkBtn.siblings('.subUrl').val().replace('/', '');
    $redirectForm.find('#mainMenuSn').val(menuRecord['mainMenu']['sn']);
    $redirectForm.find('#subMenuSn').val($subLinkBtn.val());
    $redirectForm.attr('action', ctxPath + url);
    $redirectForm.submit();
}

function submitViewByMainMenuLink($mainLinkBtn){
    var $redirectForm = $("#redirectForm");
    var url = $mainLinkBtn.find('.url').val().replace('/', '');
    $redirectForm.find('#mainMenuSn').val($mainLinkBtn.find('.mainMenuSn').val());
    $redirectForm.find('#subMenuSn').val($mainLinkBtn.find('.subMenuSn').val());
    $redirectForm.attr('action', ctxPath + url);
    $redirectForm.submit();
}

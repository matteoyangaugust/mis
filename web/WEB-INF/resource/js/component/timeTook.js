var timePurpose = "total";


function setTimeTookEvent(){
    $swal.find(".timeNumber").bind('change', function(){
        countDuration();
    });
    $swal.find(".timeNumber").bind('mousedown', function(){
        countDuration();
    });
    $swal.find(".timeNumber").bind('keyup', function(){
        countDuration();
    });
    $swal.find(".timePurposeBlock").bind('click', function(){
        setTimePurpose($(this));
        changeTimePurpose($(this));
    });
}

function setTimePurpose($btn){
    timePurpose = $btn.find('.timePurpose').val();
}

function countDuration(){
    // var timePurpose = $swal.find('.timeTook').find('.active').find('.timePurpose').val();
    var dayToSecond = parseInt($swal.find('#time_took_of_day').val()) * 24 * 60 * 60;
    var hourToSecond = parseInt($swal.find('#time_took_of_hour').val()) * 60 * 60;
    var minuteToSecond = parseInt($swal.find('#time_took_of_minute').val()) * 60;
    var second = parseInt($swal.find('#time_took_of_second').val())
    var timeTook = dayToSecond + hourToSecond + minuteToSecond + second;
    var amount = parseInt($swal.find('#amount').val());
    var process_amount = parseInt($swal.find('#process_amount').val());
    var process_yield = parseInt(currentComponent['process_yield']);
    if(timePurpose == 'total'){
        $swal.find('#total_duration').val(timeTook);
    }else{
        $swal.find('#total_duration').val(timeTook * process_amount);
    }
}

function changeTimePurpose($timePurposeBtn) {
    var purpose = $timePurposeBtn.find('.timePurpose').val();
    if (typeof components != 'undefined') {
        var componentSn = $timePurposeBtn.find('.component_sn').val();
        $.each(components, function (index, component) {
            if (componentSn == component['sn']) {
                fillingTimeTook(component, purpose);
                return false;
            }
        });
    }
    countDuration();
}

/**
 * 填裝程序時間或總需時間
 * @param component
 * @param purpose 時間種類 ? 總需時間(total) : 單製程時間(single)
 * @param from 是葉面呼叫還是sweetAlert呼叫 ? html : swal
 */
function fillingTimeTook(component, purpose){
    var processAmount = parseInt($swal.find('#process_amount').val());
    var timeBean;
    if(purpose == 'total'){
        timeBean = timeCovert(parseInt(component['process_duration']) * processAmount);
    }else{
        timeBean = timeCovert(parseInt(component['process_duration']));
    }
    $swal.find('#time_took_of_day').val(timeBean['day']);
    $swal.find('#time_took_of_hour').val(timeBean['hour']);
    $swal.find('#time_took_of_minute').val(timeBean['minute']);
    $swal.find('#time_took_of_second').val(timeBean['second']);
    $swal.find(".component_sn").val(component['sn']);
}


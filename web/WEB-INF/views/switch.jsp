
<style>
    /* Switch button */
    .btn-default.btn-on.active{background-color: #5BB75B;color: white;}
    .btn-default.btn-off.active{background-color: #DA4F49;color: white;}

    .btn-default.btn-on-1.active{background-color: #006FFC;color: white;}
    .btn-default.btn-off-1.active{background-color: #DA4F49;color: white;}

    .btn-default.btn-on-2.active{background-color: #00D590;color: white;}
    .btn-default.btn-off-2.active{background-color: #A7A7A7;color: white;}

    .btn-default.btn-on-3.active{color: #5BB75B;font-weight:bolder;}
    .btn-default.btn-off-3.active{color: #DA4F49;font-weight:bolder;}

    .btn-default.btn-on-4.active{background-color: #006FFC;color: #5BB75B;}
    .btn-default.btn-off-4.active{background-color: #DA4F49;color: #DA4F49;}
    #switch input{  display: none;  }
</style>
<div class="btn-group" id="switch" data-toggle="buttons">
    <label class="btn btn-default btn-on-1 btn-sm isOverrideTimeLabel">
        <input type="radio" class="isOverrideTime" value="true">Override</label>
    <label class="btn btn-default btn-off-1 btn-sm isOverrideTimeLabel active">
        <input type="radio" class="isOverrideTime" value="false" checked="checked">Default</label>
</div>
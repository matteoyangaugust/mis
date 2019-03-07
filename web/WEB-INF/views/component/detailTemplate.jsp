<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row detailTemplate swalLabelText" style="display: none">
    <div class="swalLabelText col-sm-12">
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Update Time：</span>
            <span class="text-left col-sm-5 updateTimeWithUnit text-left"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Component Code：</span>
            <span class="text-left col-sm-5 identifier"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Component Name：</span>
            <span class="text-left col-sm-5 name"></span>
        </div>
        <div class="form-group form-inline mt-3 notPurchase">
            <span class="col-sm-5 text-muted labelDivText">Used Material：</span>
            <span class="text-left col-sm-5 materialName"></span>
        </div>
        <div class="form-group form-inline mt-3 notPurchase">
            <span class="col-sm-5 text-muted labelDivText">Used Component：</span>
            <span class="text-left col-sm-5 componentName"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Procedure：</span>
            <span class="text-left col-sm-5 processName"></span>
        </div>
        <div class="form-group form-inline mt-3 purchase">
            <span class="col-sm-5 text-muted labelDivText">Price：</span>
            <span class="text-left col-sm-5 purchase_fee"></span>
        </div>
        <div class="form-group form-inline mt-3 notPurchase">
            <span class="col-sm-5 text-muted labelDivText">Duration：</span>
            <span class="text-left col-sm-5 timeTookInHour"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Building Quantity：</span>
            <span class="text-left col-sm-5 amount"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="col-sm-5 text-muted labelDivText">Stock：</span>
            <span class="text-left col-sm-5 remain"></span>
        </div>
    </div>
</form>
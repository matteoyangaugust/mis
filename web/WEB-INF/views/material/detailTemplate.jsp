<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row detailTemplate swalLabelText">
    <div class="swalLabelText col-sm-12">
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Update Time：</span>
            <span class="text-left col-sm-5 updateTimeWithUnit"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Material Code：</span>
            <span class="text-left col-sm-5 identifier"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Material Name：</span>
            <span class="text-left col-sm-5 name"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Supplier：</span>
            <span class="text-left col-sm-5 supplierName"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Buying Time：</span>
            <span class="text-left col-sm-5 buyingTimeWithUnit"></span>
        </div>
        <div class="form-group form-inline mt-3 needDiv">
            <span class="labelDivText col-sm-5 text-muted">Total Price：</span>
            <span class="text-left col-sm-5 fee"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Buying Quantity：</span>
            <span class="text-left col-sm-5 quantity"></span>
            <span class="text-left col-sm-2 unit"></span>
        </div>
        <div class="form-group form-inline mt-3">
            <span class="labelDivText col-sm-5 text-muted">Total Quantity：</span>
            <span class="text-left col-sm-5 remain"></span>
            <span class="text-left col-sm-2 unit"></span>
        </div>
    </div>
</form>
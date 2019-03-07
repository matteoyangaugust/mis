<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row addTemplate swalLabelText">
    <div class="swalLabelText col-sm-12">
        <span class="mt-3 form-group form-inline row">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">原料</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <select class="selector custom-select material_sn notEmpty inputW50w100 " name="material_sn" id="material_sn" aria-describedby="原料">
                    <option value="none">請選擇</option>
                    <c:forEach var="material" items="${materials}">
                        <c:if test="${material.active eq 1}">
                            <option value="${material.sn}">${material.identifier}-${material.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">廠商</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <select class="selector custom-select supplier_sn notEmpty inputW50w100" name="supplier_sn" id="supplier_sn" aria-describedby="廠商">
                    <option value="none">請選擇</option>
                    <c:forEach var="supplier" items="${suppliers}">
                        <option value="${supplier.sn}">${supplier.name}</option>
                    </c:forEach>
                </select>
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">進貨日期</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="date" class="form-control notEmpty inputW50w100 buying_time" name="buying_time" id="buying_time" aria-describedby="進貨日期">
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">進貨量</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="text" class="floatInput form-control notEmpty inputW50w100 quantity" name="quantity" id="quantity" value="0" aria-describedby="進貨量">
                <span id="unit" class="greyText"></span>
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">進貨總價</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="number" class="floatInput form-control notEmpty inputW50w100 fee" name="fee" id="fee" value="0" aria-describedby="進貨總價">
            </span>
        </span>
    </div>
</form>
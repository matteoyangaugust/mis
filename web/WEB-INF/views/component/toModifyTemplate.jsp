<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row toModifyTemplate swalLabelText" style="display: none">
    <div class="col-sm-12 text-center">
        <input type="hidden" class="sn" name="sn" id="sn" value="">
        <input type="hidden" class="active" name="active" id="active" value="">
        <div class="form-group form-inline mt-3 row">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Component Code</div>
            </span>
            <div class="col-sm-8 text-left pl-0 pr-0">
                <input type="text" class="form-control notEmpty inputW50w100 identifier" name="identifier" id="identifier" value="" aria-describedby="Component Code">
            </div>
        </div>
        <div class="form-group form-inline mt-3 row">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Component Name</div>
            </span>
            <span class="col-sm-8 text-left pl-0 pr-0">
                <input type="text" class="form-control notEmpty inputW50w100 name" name="name" value="" id="name" aria-describedby="Component Name">
            </span>
        </div>
        <div class="mt-3 form-group form-inline row">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Procedure</div>
            </span>
            <span class="col-sm-8 text-left pl-0 pr-0">
                <select class="custom-select notEmpty inputW50w100" name="process_category_sn" id="process_category_sn" aria-describedby="Procedure">
                    <c:forEach var="processCategory" items="${processCategories}">
                        <option value="${processCategory.sn}">${processCategory.name}</option>
                    </c:forEach>
                </select>
            </span>
        </div>
        <div class="form-group form-inline mt-3 row purchaseToggle purchaseFeeBlock">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Price</div>
            </span>
            <span class="col-sm-8 text-left pl-0 pr-0">
                <input type="text" class="numInput form-control notEmpty inputW50w100" name="purchase_fee" value="0" id="purchase_fee" aria-describedby="Price">
            </span>
        </div>
        <div class="form-group form-inline mt-3 row purchaseToggle">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Quantity per Procedure</div>
            </span>
            <span class="col-sm-8 text-left pl-0 pr-0">
                <input type="text" class="numInput form-control notEmpty inputW50w100" name="process_yield" value="" id="process_yield" aria-describedby="Quantity per Procedure">
            </span>
        </div>
        <div class="form-group form-inline mt-1 row purchaseToggle">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Duration per Procedure</div>
            </span>
            <span class="col-sm-8 text-left timeTook pl-0 pr-0">
                <div class="form-inline form-group">
                    <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" name="time_took_of_day" id="time_took_of_day" aria-describedby="day">
                    <span class="text-muted timeUnit">Day</span>
                    <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="23" name="time_took_of_hour" id="time_took_of_hour" aria-describedby="hour">
                    <span class="text-muted timeUnit">Hour</span>
                    <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_minute" id="time_took_of_minute" aria-describedby="minute">
                    <span class="text-muted timeUnit">Minute</span>
                    <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_second" id="time_took_of_second" aria-describedby="second">
                    <span class="text-muted timeUnit">Second</span>
                    <input type="hidden" name="process_duration" id="process_duration" value="0" aria-describedby="Duration per Procedure">
                </div>
            </span>
        </div>
        <div class="form-group form-inline mt-3 row">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Stock</div>
            </span>
            <span class="col-sm-8 text-left pl-0 pr-0">
                <input type="text" class="numInput form-control notEmpty inputW50w100 remain" name="remain" value="" id="remain" aria-describedby="Stock">
            </span>
        </div>
    </div>
</form>
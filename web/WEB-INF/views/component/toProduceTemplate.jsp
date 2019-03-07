<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row toProduceTemplate swalLabelText" style="display: none">
    <div class="col-sm-12 text-center">
        <span class="mt-3 form-group form-inline row">
            <span class="col-sm-4 text-right">
                <div for="component_sn" class="swalLabelText">Component</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <select class="selector custom-select component_sn col-sm-7" name="component_sn" id="component_sn" aria-describedby="Component">
                    <option value="none">Select One</option>
                    <c:forEach var="component" items="${components}">
                        <c:if test="${component.active eq 1}">
                            <c:choose>
                                <c:when test="${component.materialActive eq false}">
                                    <option value="${component.sn}" disabled>${component.identifier}-${component.name}(Material Disabled)</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${component.sn}">${component.identifier}-${component.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </select>
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether" style="display: none">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText">Procedure</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <span class="greyText" id="process_category_name"></span>
                <input type="hidden" class="process_category_sn" name="process_category_sn" id="process_category_sn" value="">
            </span>
        </span>
        <span class="mt-3 form-group form-inline row showTogether nonePurchaseDiv" style="display: none">
            <span class="col-sm-4 text-right">
                <div for="process_amount" class="swalLabelText">Amount of Procedure</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="number" class="numInput form-control notEmpty valueInput process_amount" name="process_amount" id="process_amount" value="0" aria-describedby="Amount of Procedure">
            </span>
        </span>
        <span class="form-group form-inline mt-3 row showTogether" style="display: none">
            <span class="col-sm-4 text-right">
                <div for="amount" class="swalLabelText">Quantity</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="number" class="numInput form-control notEmpty valueInput amount" name="amount" id="amount" value="0" aria-describedby="Quantity">
            </span>
        </span>
        <span class="form-group form-inline mt-3 purchaseDiv row" style="display: none">
            <span class="col-sm-4 text-right">
                <div for="purchase_fee" class="swalLabelText">Price</div>
            </span>
            <span class="text-left col-sm-8 pl-0 pr-0">
                <input type="number" class="numInput form-control notEmpty valueInput purchase_fee" name="purchase_fee" id="purchase_fee" value="0" aria-describedby="Price">
            </span>
        </span>
        <div class="form-group form-inline mt-3 row showTogether nonePurchaseDiv" style="display: none">
            <span class="col-sm-4 text-right">
                <div class="swalLabelText labelDivText">Duration</div>
                <div class="col-sm-12 text-right pr-0">
                    <c:import url="../switch.jsp">
                        <c:param name="callback" value="isOverride"></c:param>
                    </c:import>
                </div>
            </span>
            <span class="col-sm-8 text-left timeTook pl-0 pr-0">
                <c:import url="timeTook.jsp" charEncoding="UTF-8"></c:import>
            </span>
        </div>
    </div>
</form>
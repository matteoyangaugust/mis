<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row toProduceTemplate swalLabelText">
    <div class="col-sm-12 text-center">
        <span class="mt-3 form-group form-inline">
            <label for="product_sn" class="swalLabelText col-sm-4">Product</label>
            <select class="selector custom-select col-sm-6 product_sn" name="product_sn" id="product_sn" aria-describedby="Product">
                <option value="none">Select One</option>
                <c:forEach var="product" items="${products}">
                    <c:if test="${product.active eq 1}">
                        <c:choose>
                            <c:when test="${product.componentsActive}">
                                <option value="${product.sn}">${product.identifier}-${product.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${product.sn}" disabled>${product.identifier}-${product.name}(Component Disabled)</option>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </select>
        </span>
        <div class="form-group form-inline mt-3">
            <label for="amount" class="swalLabelText col-sm-4">Quantity</label>
            <input type="number" class="numInput form-control notEmpty col-sm-6 amount" name="amount" id="amount" value="0" aria-describedby="Quantity">
        </div>
        <div class="form-group form-inline mt-3">
            <label for="amount" class="swalLabelText col-sm-4">Sell/Build</label>
            <div class="col-sm-6 text-left pl-0 pr-0 form-inline" data-toggle="buttons">
                <label class="btn btn-outline-primary active inputW50w100 reportTypeLabel">
                    <input type="radio" name="action" class="action" id="produce" value="0" autocomplete="off" checked>Build
                </label>
                <label class="btn btn-outline-primary inputW50w100 reportTypeLabel">
                    <input type="radio" name="action" class="action" id="sell" value="1" autocomplete="off">Sell
                </label>
            </div>
        </div>
        <input type="hidden" class="remain" name="remain" value="">
    </div>
</form>
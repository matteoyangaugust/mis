
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/material/toManage.js"/>'></script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-1 h5">Code</span>
                                    <span class="warningText col-sm-2 h5">Material Name</span>
                                    <span class="warningText col-sm-3 h5">Quantity</span>
                                    <span class="warningText col-sm-2 h5">Supplier</span>
                                    <span class="warningText col-sm-2 h5">Price</span>
                                    <span class="warningText col-sm-2 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText mb-5">
                                <span class="col-sm-12 row">
                                    <span class="col-sm-4 text-right">
                                        <button type="button" class="btn btn-outline-info" id="pageSearchBtn">Name Search</button>
                                    </span>
                                    <span class="col-sm-8 text-left pl-0 pr-0">
                                        <input type="text" class="form-control notEmpty inputW50w100" id="pageSearch">
                                    </span>
                                </span>
                                <c:forEach var="material" items="${materials}" varStatus="counter">
                                    <c:if test="${material.active eq 1}">
                                        <form class="col-sm-12 form-group form-inline materialGroup text-center" id="materialForm">
                                            <c:if test="${counter.index ne 0}">
                                                <hr class="col-sm-12 style-sbs labelText">
                                            </c:if>
                                            <input type="hidden" class="sn" name="sn" value="${material.sn}">
                                            <span class="col-sm-1 p-0">
                                                <input type="text" class="form-control notEmpty col-sm-12 identifier" name="identifier" value="${material.identifier}" aria-describedby="原料品號" placeholder="Must be less than 20 characters">
                                            </span>
                                            <span class="col-sm-2">
                                                <input type="text" class="form-control notEmpty col-sm-12 name" name="name" value="${material.name}" aria-describedby="原料名稱" placeholder="Must be less than 30 characters">
                                            </span>
                                            <span class="col-sm-3">
                                                <div class="row text-center">
                                                    <input type="text" class="floatInput form-control notEmpty col-sm-6 quantity" name="quantity" value="${material.quantity}" aria-describedby="單位量">
                                                    <input type="hidden" class="originalQuantity" value="${material.quantity}">
                                                    <select class="custom-select col-sm-6" name="unit" id="unit">
                                                        <option value="1" <c:if test="${material.unit eq 1}">selected</c:if>>公克(g)</option>
                                                        <option value="2" <c:if test="${material.unit eq 2}">selected</c:if>>公斤(kg)</option>
                                                        <option value="3" <c:if test="${material.unit eq 3}">selected</c:if>>片(piece)</option>
                                                    </select>
                                                </div>
                                            </span>
                                            <span class="col-sm-2">
                                                <select class="custom-select col-sm-12" name="supplier" id="supplier">
                                                    <c:forEach var="supplier" items="${suppliers}">
                                                        <option value="${supplier.sn}"
                                                                <c:if test="${material.supplier eq supplier.sn}">
                                                                    selected
                                                                </c:if>>
                                                                ${supplier.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </span>
                                            <span class="col-sm-2">
                                                <input type="number" class="floatInput form-control notEmpty col-sm-8 price" name="price" value="${material.price}" aria-describedby="單價">
                                                <span class="greyText">NTD</span>
                                            </span>
                                            <span class="col-sm-2 btn-group-vertical">
                                                <button type="button" value='${material.sn}' class="save btn btn-outline-success mb-2">Modify</button>
                                                <button type="button" value='${material.sn}' class="delete btn btn-outline-danger">Delete</button>
                                            </span>
                                        </form>
                                    </c:if>
                                </c:forEach>
                                <hr class="col-sm-12 style14 labelText">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
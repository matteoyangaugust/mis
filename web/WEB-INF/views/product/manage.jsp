
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/product/manage.js"/>'></script>
        <script>
            var products = ${products};
            var components = ${components};
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div id="template">
                            <c:import url="selectComponentTemplate.jsp"></c:import>
                            <c:import url="selectedComponentTemplate.jsp"></c:import>
                        </div>
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-2 h5">Product Code</span>
                                    <span class="warningText col-sm-3 h5">Product Name</span>
                                    <span class="warningText col-sm-2 h5">Stock</span>
                                    <span class="warningText col-sm-3 h5">Used Component</span>
                                    <span class="warningText col-sm-2 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText">
                                <span class="col-sm-12 row">
                                    <span class="col-sm-4 text-right">
                                        <button type="button" class="btn btn-outline-info" id="pageSearchBtn">Name Search</button>
                                    </span>
                                    <span class="col-sm-8 text-left pl-0 pr-0">
                                        <input type="text" class="form-control notEmpty inputW50w100" id="pageSearch">
                                    </span>
                                </span>
                                <c:forEach var="product" items="${products}" varStatus="counter">
                                    <c:if test="${product.active eq 1}">
                                        <form class="col-sm-12 mb-3 form-group form-inline mb-4 text-center productForm">
                                            <c:if test="${counter.index ne 0}">
                                                <hr class="col-sm-12 style-sbs labelText">
                                            </c:if>
                                            <input type="hidden" name="sn" value="${product.sn}">
                                            <span class="col-sm-2">
                                                <input type="text" class="form-control notEmpty col-sm-12 identifier" name="identifier" value="${product.identifier}" aria-describedby="Product Code">
                                            </span>
                                            <span class="col-sm-3">
                                                <input type="text" class="form-control notEmpty col-sm-12 name" name="name" value="${product.name}" aria-describedby="Product Name">
                                            </span>
                                            <span class="col-sm-2">
                                                <input type="number" class="form-control notEmpty col-sm-12 remain" name="remain" value="${product.remain}" aria-describedby="Stock">
                                            </span>
                                            <span class="labelText col-sm-3 componentList">
                                                <c:forEach var="component" items="${product.components}">
                                                    <c:choose>
                                                        <c:when test="${component.active eq 0}">
                                                            <div class="greyText text-center mb-2">${component.name}(${component.identifier})&nbsp;X&nbsp;${component.quantity}(Disabled)</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="labelText text-center mb-2">${component.name}(${component.identifier})&nbsp;X&nbsp;${component.quantity}</div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </span>
                                            <span class="col-sm-2 p-0 btn-group-vertical">
                                                <button type="button" value='${product.sn}' class="toModify btn btn-outline-success mb-2">Modify</button>
                                                <button type="button" value='${product.sn}' class="manageComponents btn btn-outline-info mb-2">Used Component</button>
                                                <button type="button" value='${product.sn}' class="toDelete btn btn-outline-danger">Delete</button>
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/product/produce.js"/>'></script>
        <script>
            var products = ${products};
            var histories = ${histories};
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div id="template" style="display: none">
                            <c:import url="toProduceTemplate.jsp"></c:import>
                        </div>
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-1 h5">Index</span>
                                    <span class="warningText col-sm-1 h5">Sell/Build</span>
                                    <span class="warningText col-sm-2 h5">Product Code</span>
                                    <span class="warningText col-sm-3 h5">Product Name(Quantity/Stock)</span>
                                    <span class="warningText col-sm-3 h5">Update Time</span>
                                    <span class="warningText col-sm-2 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText">
                                <div class="col-sm-12">
                                    <button id="toProduce" class="btn btn-outline-secondary w-25 pb-sm-2 pt-sm-3"><span class="fa fa-plus fa-2x"></span></button>
                                </div>
                                <c:forEach var="history" items="${histories}" varStatus="counter">
                                    <form class="col-sm-12 mb-3 form-group form-inline mb-4 text-center historyForm">
                                        <hr class="col-sm-12 style-sbs labelText">
                                        <input type="hidden" name="sn" value="${history.sn}">
                                        <span class="secondary-text col-sm-1">${counter.count}</span>
                                        <span class="labelText col-sm-1">
                                            <c:choose>
                                                <c:when test="${history.action eq 0}">Build</c:when>
                                                <c:otherwise>Sell</c:otherwise>
                                            </c:choose>
                                        </span>
                                        <span class="labelText col-sm-2">${history.product.identifier}</span>
                                        <span class="labelText col-sm-3">${history.product.name}(${history.amount}/${history.remain})</span>
                                        <span class="labelText col-sm-3">${history.updateTimeWithUnit}</span>
                                        <span class="col-sm-2 p-0 btn-group-vertical">
                                            <c:choose>
                                                <c:when test="${history.product.active eq 1}">
                                                    <button type="button" value='${history.sn}' class="toDelete btn btn-outline-danger">Undo</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" value='${history.sn}' class="toDelete btn btn-outline-danger" disabled>Disabled</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </form>
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/material/produce.js"/>'></script>
        <script>
            var materials = ${materials};
            var histories = ${histories};
            var suppliers = ${suppliers};
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div style="display: none">
            <c:import url="detailTemplate.jsp"></c:import>
            <c:import url="addMaterial.jsp"></c:import>
        </div>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-1 h5">Index</span>
                                    <span class="warningText col-sm-2 h5">Code</span>
                                    <span class="warningText col-sm-3 h5">Material Name<br>(Buying/After Buying)</span>
                                    <span class="warningText col-sm-3 h5">Buying Time</span>
                                    <span class="warningText col-sm-3 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText">
                                <div class="col-sm-12">
                                    <button id="toProduce" class="btn btn-outline-secondary w-25 pb-sm-2 pt-sm-3"><span class="fa fa-plus fa-2x"></span></button>
                                </div>
                                <c:forEach var="history" items="${histories}" varStatus="counter">
                                    <form class="col-sm-12 mb-3 form-group form-inline mb-4 text-center historyForm">
                                        <hr class="col-sm-12 style-sbs labelText">
                                        <span class="secondary-text col-sm-1">${counter.count}</span>
                                        <span class="labelText col-sm-2">${history.materialIdentifier}</span>
                                        <span class="labelText col-sm-3">
                                            ${history.materialName}<br>
                                                (
                                            <span class="doubleNumber">${history.quantity}</span>${history.unitName}
                                                /
                                            <span class="doubleNumber">${history.remain}</span>${history.unitName}
                                                )
                                        </span>
                                        <span class="labelText col-sm-3">${history.buyingTimeWithUnit}</span>
                                        <span class="col-sm-3 p-0">
                                            <button type="button" value='${history.sn}' class="checkDetail btn btn-outline-success col-sm-5">Detail</button>
                                            <button type="button" value='${history.sn}' class="col-sm-5 toDelete btn btn-outline-danger">Undo</button>
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
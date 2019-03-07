
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/component/manage.js"/>'></script>
        <script>
            var components = ${components};
            var materials = ${materials};
            var processCategories = ${processCategories};
        </script>
    </jsp:attribute>
    <jsp:attribute name="style">
    </jsp:attribute>
    <jsp:body>
        <div id="template" style="display: none">
            <c:import url="selectMaterialTemplate.jsp"></c:import>
            <c:import url="selectComponentTemplate.jsp"></c:import>
            <c:import url="selectedMaterialTemplate.jsp"></c:import>
            <c:import url="selectedComponentTemplate.jsp"></c:import>
        </div>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <c:import url="toModifyTemplate.jsp"></c:import>
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-3 h5">Component Code</span>
                                    <span class="warningText col-sm-3 h5">Component Name</span>
                                    <span class="warningText col-sm-3 h5">Stock</span>
                                    <span class="warningText col-sm-3 h5">Action</span>
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
                                <c:forEach var="component" items="${components}" varStatus="counter">
                                    <form class="col-sm-12 mb-3 form-group form-inline mb-4 text-center componentForm">
                                        <c:if test="${counter.index ne 0}">
                                            <hr class="col-sm-12 style-sbs labelText">
                                        </c:if>
                                        <span class="labelText col-sm-3">${component.identifier}</span>
                                        <span class="labelText col-sm-3 name">${component.name}</span>
                                        <span class="labelText col-sm-3">${component.remain}</span>
                                        <span class="col-sm-3 p-0 btn-group-vertical">
                                            <button type="button" value='${component.sn}' class="toModify btn btn-outline-success">Modify</button>
                                            <button type="button" value='${component.sn}' class="selectMaterial btn btn-outline-info">Used Material</button>
                                            <button type="button" value='${component.sn}' class="selectComponent btn btn-outline-info">Used Component</button>
                                            <button type="button" value='${component.sn}' class="toDelete btn btn-outline-danger">Delete</button>
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
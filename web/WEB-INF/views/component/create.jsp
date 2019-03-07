
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/component/create.js"/>'></script>
        <script>
            var processCategories = ${processCategories};
            var materials = ${materials};
            var components = ${components};
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
                        <div class="container">
                            <div class="row text-center">
                                <form class="col-sm-12" id="componentForm">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Component Code</div>
                                        </span>
                                        <div class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 identifier" name="identifier" id="identifier" value="" aria-describedby="Component Code">
                                        </div>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Component Name</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 name" name="name" value="" id="name" aria-describedby="Component Name">
                                        </span>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Used Material</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <button type="button" class="btn btn-outline-info inputW50w100" id="selectMaterial">Select Material</button>
                                        </span>
                                        <div class="offset-4 col-sm-4 text-center" id="materials"></div>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Used Component</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <button type="button" class="btn btn-outline-info inputW50w100" id="selectComponent">Select Component</button>
                                        </span>
                                        <div class="offset-4 col-sm-4 text-center" id="components"></div>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Procedure</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <select class="selector custom-select notEmpty inputW50w100" name="process_category_sn" id="process_category_sn" aria-describedby="Procedure">
                                                <option value="none">Select One</option>
                                                <c:forEach var="processCategory" items="${processCategories}">
                                                    <option value="${processCategory.sn}">${processCategory.name}</option>
                                                </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Price</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <%--<span class="col-sm-8 text-left pl-0 pr-0">--%>
                                            <input type="text" class="floatInput form-control notEmpty inputW50w100" name="purchase_fee" value="0" id="purchase_fee" aria-describedby="Price">
                                        </span>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Quantity per Procedure</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="numInput form-control notEmpty inputW50w100" name="process_yield" value="0" id="process_yield" aria-describedby="Quantity per Procedure">
                                        </span>
                                    </div>
                                    <div class="form-group form-inline mt-1 row">
                                        <span class="col-sm-4 text-center">
                                            <div class="labelText labelDivText">Duration per Procedure</div>
                                        </span>
                                        <span class="col-sm-8 text-left timeTook pl-0 pr-0">
                                            <div class="form-inline form-group">
                                                <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" name="time_took_of_day" id="time_took_of_day" aria-describedby="Day">
                                                <span class="text-muted timeUnit">Day</span>
                                                <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="23" name="time_took_of_hour" id="time_took_of_hour" aria-describedby="Hour">
                                                <span class="text-muted timeUnit">Hour</span>
                                                <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_minute" id="time_took_of_minute" aria-describedby="Minute">
                                                <span class="text-muted timeUnit">Minute</span>
                                                <input type="number" class="numInput form-control timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_second" id="time_took_of_second" aria-describedby="Second">
                                                <span class="text-muted timeUnit">Second</span>
                                                <input type="hidden" name="process_duration" id="process_duration" value="0" aria-describedby="Duration per Procedure">
                                            </div>
                                        </span>
                                    </div>
                                </form>
                                <div class="col-sm-12 mt-5 mb-5" align="center">
                                    <button type="button" id="reload" class="btn btn-outline-danger">Restore</button>
                                    <button type="button" id="submit" class="btn btn-outline-success">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
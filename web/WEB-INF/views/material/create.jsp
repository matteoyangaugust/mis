
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/material/create.js"/>'></script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-sm-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row">
                                <form class="col-sm-12" id="materialForm">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Material Code</div>
                                        </span>
                                        <div class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 identifier" name="identifier" id="identifier" value="" aria-describedby="Material Code">
                                        </div>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Material Name</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 name" name="name" value="" id="name" aria-describedby="Material Name">
                                        </span>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Quantity</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="floatInput form-control notEmpty inputW50w100 quantity" name="quantity" value="0" id="quantity" aria-describedby="Quantity">
                                        </span>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Unit Name</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <select class="selector custom-select notEmpty inputW50w100" name="Unit" id="Unit" aria-describedby="Unit Name">
                                                <option value="none">Select One</option>
                                                <option value="1">公克(g)</option>
                                                <option value="2">公斤(kg)</option>
                                                <option value="3">片(piece)</option>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Price</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="floatInput form-control notEmpty inputW50w100 price" name="price" value="0" id="price" aria-describedby="Price">
                                        </span>
                                    </div>
                                    <div class="mt-3 form-group form-inline row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Supplier</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <select class="selector custom-select notEmpty inputW50w100" name="supplier" id="supplier" aria-describedby="Supplier">
                                                <option value="none">Select One</option>
                                                <c:forEach var="supplier" items="${suppliers}">
                                                    <option value="${supplier.sn}">${supplier.name}</option>
                                                </c:forEach>
                                            </select>
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
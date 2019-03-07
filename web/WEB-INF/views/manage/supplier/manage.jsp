
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/manage/supplier/manage.js"/>'></script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-sm-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-2 h5">Index</span>
                                    <span class="warningText col-sm-6 h5">Supplier Name</span>
                                    <span class="warningText col-sm-4 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText">
                                <c:forEach var="supplier" items="${suppliers}" varStatus="counter">
                                    <form class="col-sm-12 mb-3 form-group form-inline mb-4 materialGroup text-center supplierForm">
                                        <span class="col-sm-2 p-0">${counter.count}</span>
                                        <span class="col-sm-6 p-0">
                                            <input type="text" class="form-control notEmpty name" name="name" value="${supplier.name}" aria-describedby="Supplier Name" placeholder="Must be less than 20 characters">
                                            <input type="hidden" name="sn" value="${supplier.sn}">
                                        </span>
                                        <span class="col-sm-4 p-0">
                                            <button type="button" value='${supplier.sn}' class="save btn btn-outline-success">Modify</button>
                                            <button type="button" value='${supplier.sn}' class="delete btn btn-outline-danger">Delete</button>
                                        </span>
                                    </form>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
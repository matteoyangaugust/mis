
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/manage/supplier/create.js"/>'></script>
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
                                    <div class="form-group form-inline mt-3">
                                        <label for="name" class="labelText col-sm-4">Supplier Name</label>
                                        <input type="text" class="form-control notEmpty col-sm-4" name="name" id="name" aria-describedby="Supplier Name">
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